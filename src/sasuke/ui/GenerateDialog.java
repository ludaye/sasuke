package sasuke.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GenerateDialog extends DialogWrapper {

    private JPanel mainPanel;
    private JTable table1;
    private JComboBox comboBox1;
    private JTable table2;
    private JButton OKButton;

    public GenerateDialog(@Nullable Project project) {
        super(project);
        getPeer().setContentPane(createCenterPanel());
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return mainPanel;
    }
}
