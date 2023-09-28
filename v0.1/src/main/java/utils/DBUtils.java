package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            System.out.println("没有对应的数据库连接方式");
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:5505/ShopBackend",
                    "root",
                    "Guansheng2023"
            );
        } catch (java.sql.SQLException e) {
            System.out.println("连接不到数据库");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (con == null) {
            var connection = DBUtils.getInstance();
        }

        try {
            PreparedStatement ps = con.prepareStatement("SET time_zone = 'Asia/Shanghai'");
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.out.println("无法连接到数据库");
            throw new RuntimeException(e);
        }
        return con;
    }
}
