package sasuke.ui;

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
    private JPanel propertiesPanel;
    private JButton addButton;
    private JTable templateTable;
    private JButton removeButton;
    private JPanel templatePanel;
    private DefaultTableModel model = new DefaultTableModel();
    private Editor propertiesEditor;
    private List<Editor> templateEditorList = new ArrayList<>();
    private GridConstraints templateConstraints;
    private EditorFactory factory = EditorFactory.getInstance();
    private FileTypeManager fileTypeManager = FileTypeManager.getInstance();


    public SasukeConfiguration(SasukeSettings sasukeSettings) {
        initTable();
        /**
         * properties编辑器
         */
        String propertiesStr = sasukeSettings.getProperties() == null ? "" : sasukeSettings.getProperties();
        Document properties = factory.createDocument(propertiesStr);
        propertiesEditor = factory.createEditor(properties, null, fileTypeManager.getFileTypeByExtension("properties"), false);
        GridConstraints propertiesConstraints = new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 130), null, 0, true);
        propertiesPanel.add(propertiesEditor.getComponent(), propertiesConstraints);
        /**
         * 模板
         */
        templateConstraints = new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 300),
                new Dimension(-1, 300), new Dimension(-1, -1), 0, true);
        if (sasukeSettings.getTemplates() != null) {
            sasukeSettings.getTemplates().forEach(e -> {
                String templateStr = e.getContent() == null ? "" : e.getContent();
                addEditor(templateStr);
                model.addRow(new Object[]{e.getEnabled(), e.getName(), e.getExtension(), e.getSuffix()});
            });
        }
        initButtonEvent();

        if (templateTable.getRowCount() > 0) {
            setSelected(0);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void initTable() {
        model.addColumn("enabled");
        model.addColumn("name");
        model.addColumn("extension");
        model.addColumn("suffix");
        templateTable.setModel(model);
        templateTable.getTableHeader().setReorderingAllowed(false);
        templateTable.setRowHeight(20);
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
                int selectedRow = templateTable.getSelectedRow();
                if (selectedRow > -1) {
                    for (int i = 0, len = templateEditorList.size(); i < len; i++) {
                        templateEditorList.get(i).getComponent().setVisible(i == selectedRow);
                    }
                }
            }
        });
    }

    private void initButtonEvent() {
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.addRow(new Object[]{true, "undefined", "java", "Entity"});
                addEditor("");
                int rowIndex = templateTable.getRowCount() - 1;
                if (rowIndex > -1) {
                    setSelected(rowIndex);
                }
            }
        });

        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = templateTable.getSelectedRow();
                if (selectedRow > -1) {
                    model.removeRow(selectedRow);
                    templateEditorList.get(selectedRow).getComponent().setVisible(false);
                    templateEditorList.remove(selectedRow);
                    if (templateEditorList.size() > 0) {
                        setSelected(0);
                    }
                }
            }
        });
    }

    /**
     * 选择某个模板
     *
     * @param row 行数
     */
    private void setSelected(int row) {
        templateTable.setRowSelectionInterval(row, row);
        for (int i = 0, len = templateEditorList.size(); i < len; i++) {
            templateEditorList.get(i).getComponent().setVisible(i == row);
        }
    }

    /**
     * 新增一个模板编辑器
     */
    private void addEditor(String templateStr) {
        Document template = factory.createDocument(templateStr);
        Editor templateEditor = factory.createEditor(template, null, fileTypeManager.getFileTypeByExtension("ftl"), false);
        templatePanel.add(templateEditor.getComponent(), templateConstraints);
        templateEditor.getComponent().setVisible(false);
        templateEditorList.add(templateEditor);
    }

    public String getProperties() {
        return propertiesEditor.getDocument().getText();
    }

    public List<Template> getTemplates() {
        List<Template> templateList = new ArrayList<>();
        for (int i = 0, len = templateTable.getRowCount(); i < len; i++) {
            String content = templateEditorList.get(i).getDocument().getText();
            Boolean enable = (Boolean) templateTable.getValueAt(i, 0);
            String name = (String) templateTable.getValueAt(i, 1);
            String suffix = (String) templateTable.getValueAt(i, 2);
            String extension = (String) templateTable.getValueAt(i, 3);
            templateList.add(new Template(enable, name, content, suffix, extension));
        }
        return templateList;
    }
}
