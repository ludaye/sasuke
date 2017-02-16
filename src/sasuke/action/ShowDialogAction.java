package sasuke.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import sasuke.Icons;
import sasuke.ui.GenerateDialog;

public class ShowDialogAction extends AnAction {

    public ShowDialogAction() {
        super(Icons.susake);
        setEnabledInModalContext(true);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        GenerateDialog generateDialog = new GenerateDialog(e.getProject());
        //       generateDialog.show();
        DataContext dataContext = e.getDataContext();
        System.out.println(e.getProject().getProjectFile().getName());
    }
}
