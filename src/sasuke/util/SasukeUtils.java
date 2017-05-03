package sasuke.util;

import com.google.common.collect.*;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.SourceFolder;
import com.yourkit.util.Strings;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import org.jetbrains.jps.model.java.JavaResourceRootType;
import org.jetbrains.jps.model.java.JavaSourceRootType;
import org.jetbrains.jps.model.module.JpsModuleSourceRootType;

import sasuke.*;
import sasuke.Table;
import sasuke.common.Constants;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SasukeUtils {
    private static final String URL_PREFIX = "file://";

    public static Properties stringToProperties(String string) {
        Properties result = new Properties();
        try {
            InputStream inputStream = new ByteArrayInputStream(string.getBytes());
            result.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("解析出错 " + e.getMessage(), e);
        }
        return result;
    }

    public static ProjectModules findProjectModulePaths(Project project, Module currentModule) {
        ProjectModules projectModules = new ProjectModules();
        Module[] modules = ModuleManager.getInstance(project).getModules();
        for (Module module : modules) {
            ContentEntry[] contentEntries = ModuleRootManager.getInstance(module).getContentEntries();
            for (ContentEntry entry : contentEntries) {
                for (SourceFolder folder : entry.getSourceFolders()) {
                    JpsModuleSourceRootType<?> rootType = folder.getRootType();
                    if (JavaSourceRootType.SOURCE.equals(rootType)) {
                        if (module.equals(currentModule)) {
                            projectModules.getSourcePaths().addFirst(folder.getUrl().replace(URL_PREFIX, ""));
                        } else {
                            projectModules.getSourcePaths().addLast(folder.getUrl().replace(URL_PREFIX, ""));
                        }
                    } else if (JavaResourceRootType.RESOURCE.equals(rootType)) {
                        if (module.equals(currentModule)) {
                            projectModules.getResourcePaths().addFirst(folder.getUrl().replace(URL_PREFIX, ""));
                        } else {
                            projectModules.getResourcePaths().addLast(folder.getUrl().replace(URL_PREFIX, ""));
                        }
                    }
                }
            }
        }
        return projectModules;
    }

    public static String getPath(Properties properties, ProjectModules projectModules, Template template) {
        String name = template.getName();
        String absolutePath = name + ".absolutePath";
        if (properties.getProperty(absolutePath) != null) {
            return properties.getProperty(absolutePath);
        }
        String pathPattern = name + ".pathPattern";
        if (properties.getProperty(pathPattern) == null) {
            return "";
        }
        String extension = template.getExtension();
        List<String> list = null;
        if ("java".equals(extension)) {
            list = projectModules.getSourcePaths();
        } else {
            list = projectModules.getResourcePaths();
        }
        for (String str : list) {
            if (str.matches(properties.getProperty(pathPattern))) {
                String packagePath = properties.getProperty(name + ".packagePath");
                if (packagePath != null) {
                    if (packagePath.startsWith("/") || packagePath.startsWith("\\")) {
                        str = str + packagePath;
                    } else {
                        str = str + "/" + packagePath;
                    }
                }
                return str;
            }
        }
        return "";
    }

    public static TaskResult generateFile(List<WillDoTemplate> templates, List<Table> tables,
            ProjectModules projectModules) {
        Writer out = null;
        TaskResult result = new TaskResult();
        String path = "";
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        StringTemplateLoader loader = new StringTemplateLoader();
        templates.forEach(t -> loader.putTemplate(t.getName(), t.getContent()));
        HashBasedTable<String, String, TemplateProperty> templateTable = templateTable(templates, tables, projectModules);
        configuration.setTemplateLoader(loader);
        for (Table table : tables) {
            for (WillDoTemplate willDoTemplate : templates) {
                try {
                    freemarker.template.Template template = configuration.getTemplate(willDoTemplate.getName());
                    template.setEncoding("UTF-8");
                    String folderPath = willDoTemplate.getPath();
                    File folder = new File(folderPath);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }
                    path = folderPath + File.separator + table.getUpCamelName() +
                            willDoTemplate.getSuffix() + "." + willDoTemplate.getExtension();
                    File file = new File(path);
                    if (file.exists()) {
                        result.getSkip().add(path);
                        continue;
                    }
                    out = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
                    Map<String, Object> root = getDateModel(willDoTemplate, table, templateTable);
                    template.process(root, out);
                    out.close();
                    result.getSuccess().add(path);
                    path = "";
                } catch (TemplateException | IOException e) {
                    if (!Strings.isNullOrEmpty(path)) {
                        result.getFailure().add(path);
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    private static Map<String, Object> getDateModel(WillDoTemplate template, Table table,
            HashBasedTable<String, String, TemplateProperty> templateTable) {
        Map<String, Object> result = new HashMap<>();
        TemplateProperty property = templateTable.get(table.getName(), template.getName());
        Map<String, TemplateProperty> map = templateTable.rowMap().get(table.getName());
        result.put("properties", ColumnToClassProperty(table.getColumns()));
        result.put("templates", map);
        result.put("schema", table.getSchema());
        result.put("table", table.getName());
        if ("java".equals(template.getExtension())) {
            result.put("className", property.getFileName());
            result.put("packageName", property.getPackageName());
            result.put("user", System.getProperty("user.name"));
            result.put("remark", table.getRemark());
        }
        return result;
    }

    private static HashBasedTable<String, String, TemplateProperty> templateTable(List<WillDoTemplate> templates,
            List<Table> tables, ProjectModules projectModules) {
        HashBasedTable<String, String, TemplateProperty> result = HashBasedTable.create();
        for (Table table : tables) {
            for (WillDoTemplate template : templates) {
                TemplateProperty property = new TemplateProperty();
                property.setFileName(table.getUpCamelName() + template.getSuffix());
                if ("java".equals(template.getExtension())) {
                    property.setPackageName(getPackageName(projectModules, template.getPath()));
                }
                property.setExtension(template.getExtension());
                result.put(table.getName(), template.getName(), property);
            }
        }
        return result;
    }

    private static String getPackageName(ProjectModules projectModules, String path) {
        LinkedList<String> sourcePaths = projectModules.getSourcePaths();
        for (String str : sourcePaths) {
            if (path.startsWith(str)) {
                String packageName = path.replace(str, "");
                packageName = packageName.replaceAll("/|\\\\", "\\.");
                if (packageName.startsWith(".")) {
                    packageName = packageName.substring(1, packageName.length());
                }
                return packageName;
            }
        }
        return null;
    }

    public static String getResultStr(TaskResult result) {
        StringBuilder builder = new StringBuilder();
        builder.append("成功").append(result.getSuccess().size()).append("个");
        if (result.getSuccess().size() > 0) {
            builder.append("：");
        }
        builder.append("\n");
        result.getSuccess().forEach(str -> builder.append(str).append("\n"));

        builder.append("失败").append(result.getFailure().size()).append("个");
        if (result.getFailure().size() > 0) {
            builder.append("：");
        }
        builder.append("\n");
        result.getFailure().forEach(str -> builder.append(str).append("\n"));

        builder.append("跳过").append(result.getSkip().size()).append("个");
        if (result.getSkip().size() > 0) {
            builder.append("：");
        }
        builder.append("\n");
        result.getSkip().forEach(str -> builder.append(str).append("\n"));
        return builder.toString();
    }

    private static List<ClassProperty> ColumnToClassProperty(List<Column> columns) {
        return columns.stream().map(column -> {
            ClassProperty property = new ClassProperty();
            property.setRemark(column.getRemark());
            property.setColumnName(column.getName());
            property.setLowCamelName(column.getLowCamelName());
            property.setUpCamelName(column.getUpCamelName());
            property.setColumnType(column.getType().toUpperCase());
            property.setFullNameType(Constants.MAP.getOrDefault(column.getType(), "java.lang.String"));
            String fullNameType = property.getFullNameType();
            property.setType(fullNameType.substring(fullNameType.lastIndexOf(".") + 1, fullNameType.length()));
            return property;
        }).collect(Collectors.toList());
    }
}
