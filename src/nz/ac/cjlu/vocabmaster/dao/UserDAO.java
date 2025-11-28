// 包: nz.ac.cjlu.vocabmaster.dao
// UserDAO.java - 用户操作的DAO。

package nz.ac.cjlu.vocabmaster.dao;

import nz.ac.cjlu.vocabmaster.model.User;
import java.sql.*;

// UserDAO.java - 用户操作的DAO
public class UserDAO {
    // 数据库URL。
    private static final String DB_URL = "jdbc:derby:vocabmaster_db;create=true";

    // 方法: 根据用户名查找用户。
    // 返回User对象或null。
    public User findByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USERS WHERE USERNAME = ?")) {
            // 设置参数。
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // 创建User对象并设置属性。
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                return user;
            }
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
        return null;
    }

    // 方法: 插入用户。
    // 返回生成的ID或-1如果失败。
    public int insert(User user) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO USERS (USERNAME, PASSWORD) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            // 设置参数。
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
        return -1;
    }
}