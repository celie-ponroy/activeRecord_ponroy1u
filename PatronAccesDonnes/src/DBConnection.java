import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;
    private static String userName, password, serverName, portNumber;
    private static String dbName = "testpersonne";

    private DBConnection() throws SQLException {
        // variables a modifier en fonction de la base
        String userName = "root";
        String password = "";
        String serverName = "localhost";
        //Attention, sous MAMP, le port est 8889
        String portNumber = "3306";

        // iL faut une base nommee testPersonne !
        String dbName = DBConnection.dbName;

        // creation de la connection
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        DBConnection.connection = DriverManager.getConnection(urlDB, connectionProps);
    }
    public static synchronized Connection getConnection() throws SQLException {
            if (DBConnection.connection == null)
                new DBConnection();

            return DBConnection.connection;
    }
    public static synchronized void setNomDB(String nomDB) throws SQLException {
        if(nomDB!=null && !nomDB.equals(DBConnection.dbName)){
            DBConnection.dbName=nomDB;
            DBConnection.connection=null;
        }
    }

    public static String getDbName() {
        return DBConnection.dbName;
    }
}
