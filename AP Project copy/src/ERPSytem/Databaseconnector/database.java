package ERPSytem.Databaseconnector;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    private static final String URL = "jdbc:mysql://localhost:3306/collegeerp";
    private static final String USER = "erpuser";
    private static final String PASSWORD = "password123";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}



