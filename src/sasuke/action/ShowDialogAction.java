package sasuke.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import sasuke.Icons;
import sasuke.MysqlLink;
import sasuke.ProjectModules;
import sasuke.SasukeSettings;
import sasuke.ui.GenerateDialog;
import sasuke.util.SasukeUtils;

import java.sql.SQLException;
import java.util.Properties;

public class ShowDialogAction extends AnAction {

    public ShowDialogAction() {
        super(Icons.SUSAKE);
        setEnabledInModalContext(true);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        SasukeSettings sasukeSettings = ServiceManager.getService(SasukeSettings.class);
        Properties properties = SasukeUtils.stringToProperties(sasukeSettings.getProperties());
        try (MysqlLink mysqlLink = new MysqlLink(properties.getProperty("url"), properties.getProperty("user"),
                properties.getProperty("password"))) {
            Project project = e.getProject();
            ProjectModules projectModules = SasukeUtils.findProjectModulePaths(project);
            GenerateDialog generateDialog = new GenerateDialog(project, mysqlLink, projectModules, sasukeSettings, properties);
            generateDialog.show();
        } catch (SQLException | ClassNotFoundException e1) {
            throw new RuntimeException(e1.getMessage(), e1);
        } catch (Exception e1) {
            throw new RuntimeException(e1.getMessage(), e1);
        }
    }
}
