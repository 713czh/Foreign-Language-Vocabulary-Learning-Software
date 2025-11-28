// 包: nz.ac.cjlu.vocabmaster.dao
// UserWordDAO.java - 用户词状态的DAO。

package nz.ac.cjlu.vocabmaster.dao;

import nz.ac.cjlu.vocabmaster.model.UserWord;
import java.sql.*;
import java.util.Random;

// UserWordDAO.java - 用户词状态的DAO
public class UserWordDAO {
    // 数据库URL。
    private static final String DB_URL = "jdbc:derby:vocabmaster_db;create=true";

    // 方法: 根据用户和词ID查找UserWord。
    // 返回UserWord对象或null。
    public UserWord findByUserAndWord(int userId, int wordId) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USER_WORDS WHERE USER_ID = ? AND WORD_ID = ?")) {
            // 设置参数。
            stmt.setInt(1, userId);
            stmt.setInt(2, wordId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // 创建UserWord对象并设置属性。
                UserWord uw = new UserWord();
                uw.setUserId(rs.getInt("USER_ID"));
                uw.setWordId(rs.getInt("WORD_ID"));
                uw.setKnown(rs.getBoolean("IS_KNOWN"));
                uw.setLastReviewDate(rs.getDate("LAST_REVIEW_DATE"));
                return uw;
            }
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
        return null;
    }

    // 方法: 插入UserWord。
    public void insert(UserWord uw) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO USER_WORDS (USER_ID, WORD_ID, IS_KNOWN, LAST_REVIEW_DATE) VALUES (?, ?, ?, ?)")) {
            // 设置参数。
            stmt.setInt(1, uw.getUserId());
            stmt.setInt(2, uw.getWordId());
            stmt.setBoolean(3, uw.isKnown());
            stmt.setDate(4, uw.getLastReviewDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
    }

    // 方法: 更新UserWord。
    public void update(UserWord uw) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("UPDATE USER_WORDS SET IS_KNOWN = ?, LAST_REVIEW_DATE = ? WHERE USER_ID = ? AND WORD_ID = ?")) {
            // 设置参数。
            stmt.setBoolean(1, uw.isKnown());
            stmt.setDate(2, uw.getLastReviewDate());
            stmt.setInt(3, uw.getUserId());
            stmt.setInt(4, uw.getWordId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
    }

    // 方法: 获取随机未知词ID。
    // 返回词ID或-1如果没有。
    public int getRandomUnknownWordId(int userId) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT WORD_ID FROM USER_WORDS WHERE USER_ID = " + userId + " AND IS_KNOWN = FALSE ORDER BY RANDOM() FETCH FIRST 1 ROW ONLY")) {
            if (rs.next()) {
                return rs.getInt("WORD_ID");
            }
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
        return -1;
    }

    // 方法: 获取随机昨日未知词ID。
    // 返回词ID或-1如果没有。
    public int getRandomYesterdayUnknownWordId(int userId, Date yesterday) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT WORD_ID FROM USER_WORDS WHERE USER_ID = ? AND IS_KNOWN = FALSE AND LAST_REVIEW_DATE = ? ORDER BY RANDOM() FETCH FIRST 1 ROW ONLY")) {
            // 设置参数。
            stmt.setInt(1, userId);
            stmt.setDate(2, yesterday);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("WORD_ID");
            }
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
        return -1;
    }

    // 方法: 计数已知词。
    // 返回数量。
    public int countKnown(int userId) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM USER_WORDS WHERE USER_ID = ? AND IS_KNOWN = TRUE")) {
            // 设置参数。
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
        return 0;
    }

    // 方法: 计数昨日未知词。
    // 返回数量。
    public int countYesterdayUnknown(int userId) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM USER_WORDS WHERE USER_ID = ? AND IS_KNOWN = FALSE AND LAST_REVIEW_DATE = CURRENT_DATE - 1")) {
            // 设置参数。
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
        return 0;
    }
}