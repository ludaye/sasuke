package sasuke.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import sasuke.Icons;
import sasuke.ui.GenerateDialog;

public class GenerateAction extends AnAction {

    public GenerateAction() {
        super(Icons.susake);
        setEnabledInModalContext(true);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        GenerateDialog generateDialog = new GenerateDialog(e.getProject());
        generateDialog.show();
    }
}
