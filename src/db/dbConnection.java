package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    private static String url = "jdbc:mysql://localhost:3306/proyectojardines";
    private static String user = "usuarioAplicacionVivero";
    private static String password = "password";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
