package nz.ac.cjlu.vocabmaster.dao;

import nz.ac.cjlu.vocabmaster.model.UserWord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 用户-单词关系数据访问对象（DAO）
 * 负责所有跟 USER_WORDS 表相关的数据库操作
 */
public class UserWordDAO {
    private static final String DB_URL = "jdbc:derby:vocabmaster_db;create=true";

    /** 根据用户ID和单词ID查找学习状态 */
    public UserWord findByUserAndWord(int userId, int wordId) {
        String sql = "SELECT * FROM USER_WORDS WHERE USER_ID = ? AND WORD_ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, wordId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserWord uw = new UserWord();
                uw.setUserId(rs.getInt("USER_ID"));
                uw.setWordId(rs.getInt("WORD_ID"));
                uw.setKnown(rs.getBoolean("IS_KNOWN"));
                uw.setLastReviewDate(rs.getDate("LAST_REVIEW_DATE"));
                return uw;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 插入一条用户-单词记录（新用户注册时批量插入） */
    public void insert(UserWord uw) {
        String sql = "INSERT INTO USER_WORDS (USER_ID, WORD_ID, IS_KNOWN, LAST_REVIEW_DATE) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, uw.getUserId());
            stmt.setInt(2, uw.getWordId());
            stmt.setBoolean(3, uw.isKnown());
            stmt.setDate(4, uw.getLastReviewDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 更新学习状态（我知道/我不知道 时调用） */
    public void update(UserWord uw) {
        String sql = "UPDATE USER_WORDS SET IS_KNOWN = ?, LAST_REVIEW_DATE = ? WHERE USER_ID = ? AND WORD_ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, uw.isKnown());
            stmt.setDate(2, uw.getLastReviewDate());
            stmt.setInt(3, uw.getUserId());
            stmt.setInt(4, uw.getWordId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机获取一个该用户还没掌握的单词ID
     * Derby 没有 RANDOM() 函数，所以先查出所有未知单词ID，再在Java中随机选
     */
    public int getRandomUnknownWordId(int userId) {
        List<Integer> unknownIds = new ArrayList<>();
        String sql = "SELECT WORD_ID FROM USER_WORDS WHERE USER_ID = ? AND IS_KNOWN = FALSE";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                unknownIds.add(rs.getInt("WORD_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (unknownIds.isEmpty()) return -1;  // 没有未知单词了（全掌握）
        Random rand = new Random();
        return unknownIds.get(rand.nextInt(unknownIds.size()));
    }

    /** 随机获取一个昨天标记为“不会”的单词（用于每日复习） */
    public int getRandomYesterdayUnknownWordId(int userId, Date yesterday) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT WORD_ID FROM USER_WORDS WHERE USER_ID = ? AND IS_KNOWN = FALSE AND LAST_REVIEW_DATE = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, yesterday);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("WORD_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (ids.isEmpty()) return -1;
        Random rand = new Random();
        return ids.get(rand.nextInt(ids.size()));
    }

    /** 统计用户已掌握的单词数量 */
    public int countKnown(int userId) {
        String sql = "SELECT COUNT(*) FROM USER_WORDS WHERE USER_ID = ? AND IS_KNOWN = TRUE";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** 统计昨天标记为不会、今天需要复习的单词数量 */
    public int countYesterdayUnknown(int userId) {
        String sql = "SELECT COUNT(*) FROM USER_WORDS WHERE USER_ID = ? AND IS_KNOWN = FALSE AND LAST_REVIEW_DATE = CURRENT_DATE - 1";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}