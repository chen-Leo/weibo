package dao;

import java.sql.*;

public class DataConner {

    static { // 加载JDBC 驱动程序
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/weibo"
                            + "?serverTimezone=GMT%2B8",
                    "root",
                    "0405duanQWER789");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void close(ResultSet rs, PreparedStatement prestatement, Connection con) { //关闭数据库
        try {
            if (rs != null) rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            if (prestatement != null) prestatement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            if (con != null) con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
















