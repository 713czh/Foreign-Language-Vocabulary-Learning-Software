package nz.ac.cjlu.vocabmaster.dao;

import nz.ac.cjlu.vocabmaster.model.User;
import java.sql.*;

public class UserDAO {
    private static final String DB_URL = "jdbc:derby:vocabmaster_db";

    // 注册用户 - 返回 boolean（成功true，失败false）
    public boolean register(User user) {
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // 明文存储！不加密！

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                // 获取生成的ID
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }
                System.out.println("注册成功：" + user.getUsername());
                return true;
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("用户名已存在！");
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 通过用户名查找用户
    public User findByUsername(String username) {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD")); // 明文
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 登录验证（明文比对）
    public User validateUser(String username, String password) {
        User user = findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}