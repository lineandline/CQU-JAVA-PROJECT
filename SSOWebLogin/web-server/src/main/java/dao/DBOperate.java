package dao;

import model.User;

import java.sql.*;

public class DBOperate {
    public User JudgeUser(String username,String password) {
        Connection conn = null;
        Statement stmt = null;
        User user = null;
        ResultSet rs;
        try {
            conn = DBConnect.getConn();
            String sql = "select username , password from userinfo where username = ? and password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);//将输入字符串同时在学号和姓名中查询
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String selUsername = rs.getString("username");
                String selPassword = rs.getString("password");
                user = new User(selUsername,selPassword);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConn(conn, stmt);
        }
        return user;
    }
}
