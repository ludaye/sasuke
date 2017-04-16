package sasuke;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class MyDocumentListener implements DocumentListener {

    private DefaultTableModel templateTableModel;
    private JTextField moduleName;

    public MyDocumentListener(DefaultTableModel templateTableModel, JTextField moduleName) {
        this.templateTableModel = templateTableModel;
        this.moduleName = moduleName;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        int rowCount = templateTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String at = (String) templateTableModel.getValueAt(i, 4);
            String text = moduleName.getText();
            String result = "";
            if (text.length() == 1) {
                result = at + "/" + text;
            } else {
                result = at.substring(0, at.length() - text.length() + 1) + text;
            }
            templateTableModel.setValueAt(result, i, 4);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        int rowCount = templateTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String at = (String) templateTableModel.getValueAt(i, 4);
            String text = moduleName.getText();
            String result = "";
            if (text.length() == 0) {
                result = at.substring(0, at.length() - 2);
            } else {
                result = at.substring(0, at.length() - text.length() - 1) + text;
            }
            templateTableModel.setValueAt(result, i, 4);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
