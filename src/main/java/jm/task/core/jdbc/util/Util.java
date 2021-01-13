package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection() {
        String host = "localhost";
        String port = "3306";
        String dbName = "MyDb";
        String user = "root";
        String pass = "root";

        String coonectionString = "jdbc:mysql://" + host + ":" + port + "/"
                + dbName;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(coonectionString, user, pass);
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            System.out.println("Failed to establish a connection to the database!!!");
        }
        return connection;
    }
}
