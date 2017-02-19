package sasuke;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (!isSelected) {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        } else {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        }
        return this;
    }
}
