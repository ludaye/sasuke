package sasuke.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.module.Module;
import sasuke.Icons;
import sasuke.MysqlLink;
import sasuke.ui.GenerateDialog;

import java.sql.SQLException;

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
            if (module != null) {
                String moduleFilePath = module.getModuleFilePath();
                // ModuleProperties sourceFolder = SasukeUtils.getSourceFolder(moduleFilePath);
            }
            GenerateDialog generateDialog = new GenerateDialog(e.getProject(), mysqlLink);
            generateDialog.show();
        } catch (SQLException | ClassNotFoundException e1) {
            throw new RuntimeException(e1.getMessage(), e1);
        } catch (Exception e1) {
            throw new RuntimeException(e1.getMessage(), e1);
        }
    }
}
