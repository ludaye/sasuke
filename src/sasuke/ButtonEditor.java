package sasuke;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {

    private JButton button;
    private ActionListener actionListener;


    public ButtonEditor(JTextField checkBox) {
        super(checkBox);
        this.setClickCountToStart(1);
        button = new JButton();
        button.setOpaque(true);
    }

    public Component getTableCellEditorComponent(final JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
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
}
