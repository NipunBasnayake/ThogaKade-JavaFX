package db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter

public class DBConnection {
    private Connection connection;
    private static DBConnection dbConnection;

    private  DBConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/thogaKade";
        final String user = "root";
        final String password = "1234";
        connection = DriverManager.getConnection(url, user, password);
    }
    public static DBConnection getInstance() throws SQLException {
        return dbConnection==null?dbConnection=new DBConnection():dbConnection;
    }
}
