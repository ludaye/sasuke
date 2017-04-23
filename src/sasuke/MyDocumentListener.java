package sasuke;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class MyDocumentListener implements DocumentListener {

    private DefaultTableModel templateTableModel;
    private JTextField moduleName;
    private String last;

    public MyDocumentListener(DefaultTableModel templateTableModel, JTextField moduleName) {
        this.templateTableModel = templateTableModel;
        this.moduleName = moduleName;
        last = moduleName.getText();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        int rowCount = templateTableModel.getRowCount();
        String text = moduleName.getText();
        for (int i = 0; i < rowCount; i++) {
            String at = (String) templateTableModel.getValueAt(i, 4);
            String result = "";
            if (last.length() == 0) {
                result = at + "/" + text;
            } else {
                result = at.substring(0, at.length() - last.length()) + text;
            }
            templateTableModel.setValueAt(result, i, 4);
        }
        last = text;
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        int rowCount = templateTableModel.getRowCount();
        String text = moduleName.getText();
        for (int i = 0; i < rowCount; i++) {
            String at = (String) templateTableModel.getValueAt(i, 4);
            String result = "";
            if (text.length() == 0) {
                result = at.substring(0, at.length() - last.length() - 1);
            } else {
                result = at.substring(0, at.length() - last.length()) + text;
            }
            templateTableModel.setValueAt(result, i, 4);
        }
        last = text;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
