package sasuke;

import com.google.common.base.CaseFormat;

import java.sql.*;
import java.util.*;

public class MysqlLink implements AutoCloseable {
    private Connection con;
    private DatabaseMetaData dmd;

    public MysqlLink(String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("remarks", "true");
        props.setProperty("useInformationSchema", "true");
        con = DriverManager.getConnection(url, props);
        dmd = con.getMetaData();
    }

    public List<String> findSchemas() throws SQLException {
        List<String> list = new ArrayList<>();
        ResultSet rs = dmd.getCatalogs();
        while (rs.next()) {
            list.add(rs.getString("TABLE_CAT"));
        }
        rs.close();
        return list;
    }

    public Map<String, Table> findTables(String schema) throws SQLException {
        Map<String, Table> map = new HashMap<>();
        ResultSet rs = dmd.getTables(schema, "%", "%", new String[]{"TABLE"});
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            String remarks = rs.getString("REMARKS");
            Table table = new Table(tableName, CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName),
                    remarks, null);
            map.put(tableName, table);
        }
        rs.close();
        return map;
    }

    public Table getTable(String schema, Table table) throws SQLException {
        List<Column> columns = new ArrayList<>();
        ResultSet rs = dmd.getColumns(schema, null, table.getName(), null);
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            String low = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName);
            String up = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, columnName);
            Column column = new Column(columnName, low, up, rs.getString("TYPE_NAME"), rs.getString("REMARKS"));
            columns.add(column);
        }
        table.setColumns(columns);
        rs.close();
        return table;
    }


    @Override
    public void close() throws Exception {
        if (con != null) {
            con.close();
        }
    }
}
