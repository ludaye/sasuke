package sasuke.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.components.ServiceManager;

import java.sql.SQLException;
import java.util.Properties;

import sasuke.Icons;
import sasuke.MysqlLink;
import sasuke.SasukeSettings;
import sasuke.ui.GenerateDialog;
import sasuke.util.PropertiesConvertUtils;

public class ShowDialogAction extends AnAction {

    public ShowDialogAction() {
        super(Icons.SUSAKE);
        setEnabledInModalContext(true);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        SasukeSettings settings = ServiceManager.getService(SasukeSettings.class);
        Properties properties = PropertiesConvertUtils.stringToProperties(settings.getJdbc());
        try (MysqlLink mysqlLink = new MysqlLink(properties.getProperty("url"), properties.getProperty("user"),
                properties.getProperty("password"))) {
            GenerateDialog generateDialog = new GenerateDialog(e.getProject(), settings, mysqlLink);
            generateDialog.show();
        } catch (SQLException | ClassNotFoundException e1) {
            throw new RuntimeException("链接失败", e1);
        } catch (Exception e1) {
            throw new RuntimeException("链接失败", e1);
        }
        DataContext dataContext = e.getDataContext();
        System.out.println(e.getProject().getProjectFile().getName());
    }
}
