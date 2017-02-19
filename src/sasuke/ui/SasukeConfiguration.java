package sasuke.ui;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.uiDesigner.core.GridConstraints;
import sasuke.SasukeSettings;
import sasuke.Template;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
        if (sasukeSettings.getTemplates() != null) {
            temp.addAll(sasukeSettings.getTemplates());
        }
        if (sasukeSettings.getJdbc() != null) {
            tempJdbc = sasukeSettings.getJdbc();
        }
        initJdbcEditor();
        initTable();
        initTemplateEditor();
        initButtonEvent();

//        templateTable.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                int row = ((JTable) e.getSource()).getSelectedRow();
//                System.out.println(row);
//                System.out.println(((JTable) e.getSource()).getValueAt(row, 0));
//                System.out.println(((JTable) e.getSource()).getValueAt(row, 1));
//            }
//        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void initTable() {
        model.addColumn("enabled");
        model.addColumn("templateName");
        templateTable.setModel(model);
        TableColumn firsetColumn = templateTable.getColumnModel().getColumn(0);
        firsetColumn.setCellEditor(templateTable.getDefaultEditor(Boolean.class));
        firsetColumn.setCellRenderer(templateTable.getDefaultRenderer(Boolean.class));
        firsetColumn.setPreferredWidth(50);

        ListSelectionModel selectionModel = templateTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                System.out.println(templateTable.getSelectedRow());
            }
        });

        for (Template template : temp) {
            model.addRow(new Object[]{template.isEnabled(), template.getTemplateName()});
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
                model.addRow(new Object[]{true, "undefined"});
            }
        });

        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(templateTable.getSelectedRow());
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
