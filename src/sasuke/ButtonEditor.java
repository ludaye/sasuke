package sasuke;


import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private JButton button;
    private ActionListener actionListener;

    public ButtonEditor() {
        button = new JButton(Icons.MENU_OPEN);
        button.setOpaque(false);
        button.setBorder(null);
    }

    public Component getTableCellEditorComponent(final JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (actionListener == null) {
            actionListener = e -> {
                fireEditingStopped();
                System.out.println(table.getSelectedRow());
                System.out.println(table.getSelectedColumn());
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
