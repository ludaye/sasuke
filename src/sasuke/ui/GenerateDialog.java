package sasuke.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;

import org.jetbrains.annotations.Nullable;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.List;

import sasuke.ButtonEditor;
import sasuke.ButtonRenderer;
import sasuke.MysqlLink;
import sasuke.SasukeSettings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class GenerateDialog extends DialogWrapper {

    private JPanel mainPanel;
    private JTable templateTable;
    private JComboBox schemaSelect;
    private JTable table;
    private JButton OKButton;
    private SasukeSettings sasukeSettings;
    private DefaultTableModel tableModel = new DefaultTableModel();
    private DefaultTableModel templateTableModel = new DefaultTableModel() {
        /**
         *1,2,3,4列不可以编辑
         */
        @Override
        public boolean isCellEditable(int row, int column) {
            return !(column == 1 || column == 2 || column == 3 || column == 4);
        }
    };
    private Project project;

    public GenerateDialog(@Nullable Project project, SasukeSettings sasukeSettings, MysqlLink mysqlLink) throws SQLException {
        super(project);
        this.project = project;
        getPeer().setContentPane(createCenterPanel());
        initTemplateTable(sasukeSettings);

        schemaSelect.addItem(" ");
        List<String> schemas = mysqlLink.findSchemas();
        if (schemas != null && schemas.size() > 0) {
            schemas.forEach(schemaSelect::addItem);
        }

        schemaSelect.addItemListener(e -> {
                    int stateChange = e.getStateChange();
                    if (stateChange == ItemEvent.SELECTED) {
                        String string = schemaSelect.getSelectedItem().toString();

                    }
                }
        );
    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return mainPanel;
    }

    private void initTemplateTable(SasukeSettings sasukeSettings) {
        templateTableModel.addColumn("enabled");
        templateTableModel.addColumn("name");
        templateTableModel.addColumn("extension");
        templateTableModel.addColumn("suffix");
        templateTableModel.addColumn("path");
        templateTableModel.addColumn("");
        templateTable.setModel(templateTableModel);
        templateTable.getTableHeader().setReorderingAllowed(false);
        templateTable.setRowHeight(25);
        templateTable.setBackground(null);
        templateTable.setForeground(null);
        templateTable.setSelectionBackground(null);
        templateTable.setSelectionForeground(null);

        TableColumnModel columnModel = templateTable.getColumnModel();
        TableColumn column_1 = columnModel.getColumn(0);
        column_1.setCellEditor(templateTable.getDefaultEditor(Boolean.class));
        column_1.setCellRenderer(templateTable.getDefaultRenderer(Boolean.class));
        column_1.setMinWidth(50);
        column_1.setPreferredWidth(50);
        column_1.setMaxWidth(50);

        TableColumn column_2 = columnModel.getColumn(1);
        column_2.setMinWidth(140);
        column_2.setPreferredWidth(140);
        column_2.setMaxWidth(140);

        TableColumn column_3 = columnModel.getColumn(2);
        column_3.setMinWidth(60);
        column_3.setPreferredWidth(60);
        column_3.setMaxWidth(60);

        TableColumn column_4 = columnModel.getColumn(3);
        column_4.setMinWidth(50);
        column_4.setPreferredWidth(50);
        column_4.setMaxWidth(50);

        TableColumn column_5 = columnModel.getColumn(5);
        column_5.setMinWidth(25);
        column_5.setPreferredWidth(25);
        column_5.setMaxWidth(25);
        column_5.setCellRenderer(new ButtonRenderer());
        column_5.setCellEditor(new ButtonEditor(project));

        templateTableModel.addRow(new Object[]{true, "test", "test", "test", "", ""});
        templateTableModel.addRow(new Object[]{true, "test", "test", "test", "", ""});
        templateTableModel.addRow(new Object[]{true, "test", "test", "test", "", ""});
        templateTableModel.addRow(new Object[]{true, "test", "test", "test", "", ""});
        templateTableModel.addRow(new Object[]{true, "test", "test", "test", "", ""});
        templateTableModel.addRow(new Object[]{true, "test", "test", "test", "", ""});
        templateTableModel.addRow(new Object[]{true, "test", "test", "test", "", ""});
    }
}
