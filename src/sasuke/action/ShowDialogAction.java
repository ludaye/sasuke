package sasuke.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

import java.sql.SQLException;

import sasuke.Icons;
import sasuke.MysqlLink;
import sasuke.ProjectModules;
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
            Project project = e.getProject();
            ProjectModules projectModules = SasukeUtils.findProjectModulePaths(project);
            GenerateDialog generateDialog = new GenerateDialog(project, mysqlLink, projectModules);
            generateDialog.show();
        } catch (SQLException | ClassNotFoundException e1) {
            throw new RuntimeException(e1.getMessage(), e1);
        } catch (Exception e1) {
            throw new RuntimeException(e1.getMessage(), e1);
        }
    }
}
