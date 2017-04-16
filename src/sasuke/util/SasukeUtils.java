package sasuke.util;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.SourceFolder;
import org.jetbrains.jps.model.java.JavaResourceRootType;
import org.jetbrains.jps.model.java.JavaSourceRootType;
import org.jetbrains.jps.model.module.JpsModuleSourceRootType;
import sasuke.ProjectModules;
import sasuke.Template;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

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

    public static ProjectModules findProjectModulePaths(Project project) {
        ProjectModules projectModules = new ProjectModules();
        Module[] modules = ModuleManager.getInstance(project).getModules();
        for (Module module : modules) {
            ContentEntry[] contentEntries = ModuleRootManager.getInstance(module).getContentEntries();
            for (ContentEntry entry : contentEntries) {
                for (SourceFolder folder : entry.getSourceFolders()) {
                    JpsModuleSourceRootType<?> rootType = folder.getRootType();
                    if (JavaSourceRootType.SOURCE.equals(rootType)) {
                        projectModules.getSourcePaths().add(folder.getUrl().replace(URL_PREFIX, ""));
                    } else if (JavaResourceRootType.RESOURCE.equals(rootType)) {
                        projectModules.getResourcePaths().add(folder.getUrl().replace(URL_PREFIX, ""));
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
}
