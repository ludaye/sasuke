package sasuke;


import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableCellEditor;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private JButton button;
    private ActionListener actionListener;
    private Project project;

    public ButtonEditor(Project project) {
        this.project = project;
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
                    table.setValueAt(virtualFile.getPath(), table.getSelectedRow(), table.getSelectedColumn() - 1);
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
