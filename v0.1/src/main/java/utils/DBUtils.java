package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {
    private static Connection con;

    private static DBUtils instance;

    private static DBUtils getInstance() {
        if (instance == null) {
            //noinspection InstantiationOfUtilityClass
            instance = new DBUtils();
        }
        return instance;
    }

    private DBUtils() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:5505/ShopBackend",
                    "root",
                    "Guansheng2023"
            );
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (con == null) {
            var connection = DBUtils.getInstance();
        }
        return con;
    }
}
