package sasuke.ui;


import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.uiDesigner.core.GridConstraints;

import sasuke.SasukeSettings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

}
