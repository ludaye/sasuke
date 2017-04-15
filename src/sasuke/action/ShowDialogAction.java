package sasuke.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.module.Module;

import org.jetbrains.jps.model.serialization.PathMacroUtil;

import java.sql.SQLException;

import sasuke.Icons;
import sasuke.MysqlLink;
import sasuke.ui.GenerateDialog;
import sasuke.util.SasukeUtils;

import static sasuke.util.SasukeUtils.PROPERTIES;

public class ShowDialogAction extends AnAction {

    public ShowDialogAction() {
        super(Icons.SUSAKE);
        setEnabledInModalContext(true);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        try (MysqlLink mysqlLink = new MysqlLink(PROPERTIES.getProperty("url"), PROPERTIES.getProperty("user"),
                PROPERTIES.getProperty("password"))) {
            DataContext dataContext = e.getDataContext();
            Module module = DataKeys.MODULE.getData(dataContext);
            ModuleProperties moduleProperties = null;
            if (module != null) {
                String moduleFilePath = module.getModuleFilePath();
                String moduleDir = PathMacroUtil.getModuleDir(moduleFilePath);
                moduleProperties = SasukeUtils.getSourceFolder(moduleFilePath, moduleDir);
            }
            GenerateDialog generateDialog = new GenerateDialog(e.getProject(), mysqlLink, moduleProperties);
            generateDialog.show();
        } catch (SQLException | ClassNotFoundException e1) {
            throw new RuntimeException(e1.getMessage(), e1);
        } catch (Exception e1) {
            throw new RuntimeException(e1.getMessage(), e1);
        }
    }
}

/**
 * // Module m = ModuleManager.getInstance(project).findModuleByName(name);
 * for (ContentEntry entry : ModuleRootManager.getInstance(module).getContentEntries()) {
 * for (SourceFolder folder : entry.getSourceFolders()) {
 * if (root.equals(folder.getFile())) {
 * return folder;
 * }
 * }
 * }
 */
