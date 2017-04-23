package sasuke;


import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private JButton button;
    private ActionListener actionListener;
    private Project project;
    private JTextField moduleName;

    public ButtonEditor(Project project, JTextField moduleName) {
        this.project = project;
        this.moduleName = moduleName;
        button = new JButton(Icons.MENU_OPEN);
        button.setOpaque(false);
        button.setBorder(null);
    }

    public Component getTableCellEditorComponent(final JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (actionListener == null) {
            actionListener = e -> {
                fireEditingStopped();
                FileChooserDescriptor fcd = FileChooserDescriptorFactory.createSingleFolderDescriptor();
                fcd.setShowFileSystemRoots(true);
                fcd.setTitle("Select Directory");
                VirtualFile virtualFile = FileChooser.chooseFile(fcd, project, null);
                if (virtualFile != null) {
                    String path = virtualFile.getPath();
                    String text = moduleName.getText();
                    if (text.length() > 0) {
                        path = path + "/" + text;
                    }
                    table.setValueAt(path, table.getSelectedRow(), table.getSelectedColumn() - 1);
                }
            };
        }
        button.removeActionListener(actionListener);
        button.addActionListener(actionListener);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
