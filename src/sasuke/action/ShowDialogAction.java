package sasuke.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
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
            GenerateDialog generateDialog = new GenerateDialog(e.getProject(), mysqlLink);
            generateDialog.show();
        } catch (SQLException | ClassNotFoundException e1) {
            throw new RuntimeException("链接失败 " + e1.getMessage(), e1);
        } catch (Exception e1) {
            throw new RuntimeException("链接失败 " + e1.getMessage(), e1);
        }
        DataContext dataContext = e.getDataContext();
        System.out.println(e.getProject().getProjectFile().getName());
    }
}
