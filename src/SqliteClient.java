import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
public class SqliteClient {
    private Connection
            connection = null;

    public SqliteClient(String dbName)
            throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:"
                + dbName);
    }

    public boolean tableExists(String tableName)
            throws SQLException {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "';";
        return executeQuery(query).size() >
                0;
    }

    public void executeStatement(String sqlStatement)
            throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
// set timeout to 30 sec.
        statement.executeUpdate(sqlStatement);

    }

    public ArrayList<HashMap<String,
            String>> executeQuery(String sqlQuery)
            throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
// set timeout to 30 sec.

        ResultSet rs = statement.executeQuery(sqlQuery);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        ArrayList<HashMap<String,
                String>> result = new ArrayList<HashMap<String,
                String>>();
        while (rs.next()) {
            HashMap<String, String> map =
                    new HashMap<String, String>();
            for (int
                 i = 1;
                 i <= columns; i++) {
                String value = rs.getString(i);
                String key = rsmd.getColumnName(i);
                map.put(key, value);
            }
            result.add(map);
        }
        return result;
    }
}
