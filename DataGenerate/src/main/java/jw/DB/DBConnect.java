package jw.DB;

import java.sql.*;

public class DBConnect {
    private static String url = "jdbc:mysql://localhost:3306/stuData?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false";  //url中的status为数据库库名
    private static String user = "root";  //数据库登陆账号
    private static String password = "database123";  //数据库登陆密码
    private static Connection con = null;

    //获取连接
    public static Connection getConn() {
        try {
            // 1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2.获取连接
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    //关闭连接（有结果集）
    public static void closeConn(Connection conn, Statement stmt, ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt = null;
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

    //关闭连接（无结果集）
    public static void closeConn(Connection conn, Statement stmt){
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt = null;
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

    //测试连接数据库
    public static void main(String[] args) {
        System.out.print(getConn());
    }
}
