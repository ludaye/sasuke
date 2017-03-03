package sasuke.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.uiDesigner.core.GridConstraints;
import sasuke.SasukeSettings;
import sasuke.Template;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SasukeConfiguration {
    private JPanel mainPanel;
    private JPanel jdbcPanel;
    private JButton addButton;
    private JTable templateTable;
    private JButton removeButton;
    private JPanel templatePanel;
    private DefaultTableModel model = new DefaultTableModel();
    private Editor jdbcEditor;
    private Editor templateEditor;
    private List<Template> temp = new ArrayList<>();
    private String tempJdbc = "";


    public SasukeConfiguration(SasukeSettings sasukeSettings) {
        if (sasukeSettings.getTemplates() != null) temp.addAll(sasukeSettings.getTemplates());
        if (sasukeSettings.getJdbc() != null) tempJdbc = sasukeSettings.getJdbc();
        initJdbcEditor();
        initTable();
        initTemplateEditor();
        initButtonEvent();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void initTable() {
        model.addColumn("enabled");
        model.addColumn("name");
        model.addColumn("extension");
        model.addColumn("suffix");
        templateTable.setModel(model);
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

        ListSelectionModel selectionModel = templateTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
            }
        });

        for (Template template : temp) {
            model.addRow(new Object[]{template.getEnabled(), template.getName()});
        }
    }

    public void initJdbcEditor() {
        EditorFactory factory = EditorFactory.getInstance();
        Document jdbc = factory.createDocument(tempJdbc);
        jdbcEditor = factory.createEditor(jdbc, null, FileTypeManager.getInstance()
                .getFileTypeByExtension("properties"), false);
        GridConstraints constraints = new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 130), null, 0, true);
        jdbcPanel.add(jdbcEditor.getComponent(), constraints);
    }

    public void initTemplateEditor() {
        EditorFactory factory = EditorFactory.getInstance();
        Document template = factory.createDocument("");
        templateEditor = factory.createEditor(template, null, FileTypeManager.getInstance()
                .getFileTypeByExtension("ftl"), false);
        GridConstraints constraints = new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 300), new Dimension(-1, 300), new Dimension(-1, -1), 0, true);
        templatePanel.add(templateEditor.getComponent(), constraints);
    }

    public void initButtonEvent() {
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.addRow(new Object[]{true, "undefined", "java", "Entity"});
                temp.add(new Template());
                int rowCount = templateTable.getRowCount() - 1;
                templateTable.setRowSelectionInterval(rowCount, rowCount);
            }
        });

        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = templateTable.getSelectedRow();
                if (selectedRow > -1) {
                    model.removeRow(selectedRow);
                    temp.remove(selectedRow);
                }
            }
        });
    }

    public List<Template> getTemp() {
        return temp;
    }

    public void setTemp(List<Template> temp) {
        this.temp = temp;
    }

    public String getTempJdbc() {
        return tempJdbc;
    }

    public void setTempJdbc(String tempJdbc) {
        this.tempJdbc = tempJdbc;
    }

}
