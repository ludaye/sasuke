package sasuke;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlLink implements AutoCloseable {
    private Connection con;
    private DatabaseMetaData dmd;

    public MysqlLink(String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, user, password);
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

    public List<String> findTables(String schema) throws SQLException {
        List<String> list = new ArrayList<>();
        ResultSet rs = dmd.getTables(schema, null, "%", new String[]{"TABLE"});
        while (rs.next()) {
            list.add(rs.getString("TABLE_NAME"));
        }
        rs.close();
        return list;
    }

    public Table getTable(String schema, String table) throws SQLException {
        List<Column> columns = new ArrayList<>();
        ResultSet rs = dmd.getColumns(schema, null, table, null);
        while (rs.next()) {
            Column column = new Column(rs.getString("COLUMN_NAME"), rs.getString("TYPE_NAME"), rs.getString("REMARKS"));
            columns.add(column);
        }
        rs.close();
        return new Table(table, columns);
    }


    @Override
    public void close() throws Exception {
        if (con != null) {
            con.close();
        }
    }
}
