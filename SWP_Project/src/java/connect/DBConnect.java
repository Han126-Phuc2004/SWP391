package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.InputStream;
import java.util.Properties;

public class DBConnect {
    private String serverName;
    private String dbName;
    private String portNumber;
    private String userID;
    private String password;

    public DBConnect() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            serverName = prop.getProperty("db.server");
            dbName = prop.getProperty("db.name");
            portNumber = prop.getProperty("db.port");
            userID = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            System.out.println("serverName=" + serverName);
            System.out.println("dbName=" + dbName);
            System.out.println("portNumber=" + portNumber);
            System.out.println("userID=" + userID);
            System.out.println("password=" + password);
        } catch (Exception e) {
            throw new RuntimeException("Không thể đọc file cấu hình database!", e);
        }
    }

    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName + ";encrypt=false;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }
     public static void main(String[] args) {
        try {
            System.out.println(new DBConnect().getConnection());
        } catch (Exception e) {
        }
    }
}