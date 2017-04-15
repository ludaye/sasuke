package sasuke.util;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.SourceFolder;

import org.jetbrains.jps.model.java.JavaResourceRootType;
import org.jetbrains.jps.model.java.JavaSourceRootType;
import org.jetbrains.jps.model.module.JpsModuleSourceRootType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import sasuke.ProjectModules;
import sasuke.SasukeSettings;
import sasuke.Template;

public class SasukeUtils {

    public static final Properties PROPERTIES = stringToProperties(ServiceManager.getService(
            SasukeSettings.class).getProperties());
    private static final String URL_PREFIX = "file://";

    private static Properties stringToProperties(String string) {
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


    public static String getPath(ProjectModules projectModules, Template template) {
        String name = template.getName();
        String absolutePath = name + ".absolutePath";
        if (PROPERTIES.getProperty(absolutePath) != null) {
            return PROPERTIES.getProperty(absolutePath);
        }
        String pathPattern = name + ".pathPattern";
        if (PROPERTIES.getProperty(pathPattern) == null) {
            return "";
        }
        String extension = template.getExtension();
        if ("java".equals(extension)) {
            for (String str : projectModules.getSourcePaths()) {

            }
        } else {

        }
        return null;
    }
}
