import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;
    private DBConnection() throws SQLException {
        // variables a modifier en fonction de la base
        String userName = "root";
        String password = "";
        String serverName = "localhost";
        //Attention, sous MAMP, le port est 8889
        String portNumber = "3306";

        // iL faut une base nommee testPersonne !
        String dbName = "testpersonne";

        // creation de la connection
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        DBConnection.connection = DriverManager.getConnection(urlDB, connectionProps);
    }
    public Connection getConncetion(){
        return this.connection;
    }
    public static Connection getInstance() throws SQLException {
            if (DBConnection.connection == null)
                new DBConnection();

            return DBConnection.connection;
        }

}
