package sasuke.ui;

import com.google.common.base.Strings;
import com.intellij.openapi.progress.BackgroundTaskQueue;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.util.ui.AnimatedIcon;
import org.jetbrains.annotations.Nullable;
import sasuke.*;
import sasuke.util.SasukeUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class GenerateDialog extends DialogWrapper {
    private static final String CARD_MSG = "msg";
    private static final String CARD_PROCESS = "process";
    private static final String CARD_TABLE = "table";
    private JPanel mainPanel;
    private JTable templateTable;
    private JComboBox<String> schemaSelect;
    private JTable table;
    private JButton OKButton;
    private JTextField moduleName;
    private JPanel msgPanel;
    private JPanel processPanel;
    private AnimatedIcon animatedIcon;
    private JScrollPane tablePanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private SasukeSettings sasukeSettings;
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return !(column == 1);
        }
    };
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
    private Map<String, Template> tempalteMap = new HashMap<>();
    private Map<String, Table> tableMap = new HashMap<>();
    private List<WillDoTemplate> willDoTemplate = new ArrayList<>();
    private List<Table> willDoTable = new ArrayList<>();
    private ProjectModules projectModules;
    private Properties properties;

    public GenerateDialog(@Nullable Project project, MysqlLink mysqlLink, ProjectModules projectModules,
                          SasukeSettings sasukeSettings, Properties properties) throws SQLException {
        super(project);
        this.project = project;
        this.projectModules = projectModules;
        this.sasukeSettings = sasukeSettings;
        this.properties = properties;

        cardLayout = (CardLayout) cardPanel.getLayout();
        getPeer().setContentPane(createCenterPanel());
        initTemplateTable();
        initTable();
        cardLayout.show(cardPanel, CARD_MSG);

        schemaSelect.addItem(" ");
        List<String> schemas = mysqlLink.findSchemas();
        if (schemas != null && schemas.size() > 0) {
            schemas.forEach(schemaSelect::addItem);
        }

        schemaSelect.addItemListener(e -> {
            int stateChange = e.getStateChange();
            if (stateChange == ItemEvent.SELECTED) {
                cardLayout.show(cardPanel, CARD_PROCESS);
                animatedIcon.resume();
                String string = schemaSelect.getSelectedItem().toString();
                tableModel.setRowCount(0);
                if (string.trim().equals("")) {
                    cardLayout.show(cardPanel, CARD_MSG);
                    animatedIcon.suspend();
                    return;
                }
                try {
                    Map<String, Table> tables = mysqlLink.findTables(string);
                    if (tables != null && tables.size() > 0) {
                        cardLayout.show(cardPanel, CARD_TABLE);
                        animatedIcon.suspend();
                        tableMap = tables;
                        tables.forEach((s, t) -> tableModel.addRow(new Object[]{false, t.getName(), t.getUpCamelName()}));
                    } else {
                        cardLayout.show(cardPanel, CARD_MSG);
                        animatedIcon.suspend();
                    }
                } catch (SQLException e1) {
                    cardLayout.show(cardPanel, CARD_MSG);
                    animatedIcon.suspend();
                    throw new RuntimeException("查询表名失败\n" + e1.getMessage(), e1);
                }
            }
        });

        OKButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int templateTableModelRowCount = templateTableModel.getRowCount();
                if (templateTableModelRowCount == 0) {
                    throw new RuntimeException("请添加模板");
                }
                willDoTemplate.clear();
                for (int i = 0; i < templateTableModelRowCount; i++) {
                    boolean enabled = (boolean) templateTableModel.getValueAt(i, 0);
                    if (enabled) {
                        String path = (String) templateTableModel.getValueAt(i, 4);
                        if (Strings.isNullOrEmpty(path)) {
                            throw new RuntimeException("请选择模板路径");
                        }
                        String name = (String) templateTableModel.getValueAt(i, 1);
                        Template template = tempalteMap.get(name);
                        WillDoTemplate e1 = new WillDoTemplate(template, path);
                        willDoTemplate.add(e1);
                    }
                }
                int rowCount = tableModel.getRowCount();
                willDoTable.clear();
                for (int i = 0; i < rowCount; i++) {
                    boolean enabled = (boolean) tableModel.getValueAt(i, 0);
                    if (enabled) {
                        String entityName = (String) tableModel.getValueAt(i, 2);
                        if (Strings.isNullOrEmpty(entityName)) {
                            throw new RuntimeException("请填写实体名称");
                        }
                        String tableName = (String) tableModel.getValueAt(i, 1);
                        Table table = tableMap.get(tableName);
                        table.setUpCamelName(entityName);
                        try {
                            table = mysqlLink.getTable(schemaSelect.getSelectedItem().toString(), table);
                        } catch (SQLException e1) {
                            throw new RuntimeException(e1.getMessage(), e1);
                        }
                        willDoTable.add(table);
                    }
                }
                BackgroundTaskQueue myQueue = new BackgroundTaskQueue(project, "my task");
                SasukeTask task = new SasukeTask(project, "Generate Tasks", false, willDoTemplate, willDoTable,
                        projectModules);
                myQueue.run(task);
                close(0);
            }
        });
        moduleName.getDocument().addDocumentListener(new MyDocumentListener(templateTableModel, moduleName));
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return mainPanel;
    }

    private void initTemplateTable() {
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
        column_5.setCellEditor(new ButtonEditor(project, moduleName));

        List<Template> templates = sasukeSettings.getTemplates();
        if (templates != null && templates.size() > 0) {
            long count = templates.stream().map(Template::getName).distinct().count();
            if (count != templates.size()) {
                throw new RuntimeException("模板名不能重复");
            }
            templates.stream().filter(Template::getEnabled).forEach(t -> {
                        String path = SasukeUtils.getPath(properties, projectModules, t);
                        templateTableModel.addRow(new Object[]{true, t.getName(), t.getExtension(), t.getSuffix(), path});
                        tempalteMap.put(t.getName(), t);
                    }
            );
        }
    }

    private void initTable() {
        tableModel.addColumn("enabled");
        tableModel.addColumn("tableName");
        tableModel.addColumn("entityName");
        table.setModel(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableColumnModel columnModel = table.getColumnModel();
        TableColumn column_1 = columnModel.getColumn(0);
        column_1.setCellEditor(table.getDefaultEditor(Boolean.class));
        column_1.setCellRenderer(table.getDefaultRenderer(Boolean.class));
        column_1.setMinWidth(50);
        column_1.setPreferredWidth(50);
        column_1.setMaxWidth(50);

    }

    private void createUIComponents() {
        animatedIcon = new ProcessIcon();
    }
}
