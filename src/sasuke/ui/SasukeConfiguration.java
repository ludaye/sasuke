package sasuke.ui;


import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.uiDesigner.core.GridConstraints;
import sasuke.SasukeSettings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * Created by Administrator on 2017/2/11.
 */
public class SasukeConfiguration {
    private JPanel mainPanel;
    private JPanel jdbcPanel;
    private JButton addButton;
    private JTable templateTable;
    private JButton removeButton;
    private JPanel templatePanel;
    private DefaultTableModel model;

    public SasukeConfiguration(SasukeSettings sasukeSettings) {
        init();
        model = new DefaultTableModel();
        model.addColumn("enabled");
        model.addColumn("templateName");
        templateTable.setModel(model);
        TableColumn firsetColumn = templateTable.getColumnModel().getColumn(0);
        firsetColumn.setCellEditor(templateTable.getDefaultEditor(Boolean.class));
        firsetColumn.setCellRenderer(templateTable.getDefaultRenderer(Boolean.class));

        templateTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
        templateTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor(new JTextField()));
        firsetColumn.setPreferredWidth(30);
        firsetColumn.setMaxWidth(30);
        firsetColumn.setMinWidth(30);


        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JCheckBox jCheckBox = new JCheckBox();
                model.addRow(new Object[]{false, "35",});
            }
        });

        templateTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = ((JTable) e.getSource()).getSelectedRow();
                System.out.println(row);
                System.out.println(((JTable) e.getSource()).getValueAt(row, 0));
                System.out.println(((JTable) e.getSource()).getValueAt(row, 1));
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void init() {
        EditorFactory factory = EditorFactory.getInstance();
        Document velocityTemplate = factory.createDocument("");
        Editor editor = factory.createEditor(velocityTemplate, null, FileTypeManager.getInstance()
                .getFileTypeByExtension("properties"), false);
        GridConstraints constraints = new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(130, 130), null, 0, true);
        jdbcPanel.add(editor.getComponent(), constraints);
    }

    public void init2() {
        EditorFactory factory = EditorFactory.getInstance();
        Document velocityTemplate = factory.createDocument("");
        Editor editor = factory.createEditor(velocityTemplate, null, FileTypeManager.getInstance()
                .getFileTypeByExtension("ftl"), false);
        GridConstraints constraints = new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 390), new Dimension(-1, 390), new Dimension(-1, -1), 0, true);
        templatePanel.add(editor.getComponent(), constraints);
    }

    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JTextField checkBox) {
            super(checkBox);
            this.setClickCountToStart(1);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });

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
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(table.getSelectedRow());
                    System.out.println(table.getSelectedColumn());
                }
            });
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                //
                //
                // JOptionPane.showMessageDialog(button, label + ": Ouch!");
                // System.out.println(label + ": Ouch!");
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            System.out.println(1);
            return super.shouldSelectCell(anEvent);
        }

    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("UIManager"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
}
