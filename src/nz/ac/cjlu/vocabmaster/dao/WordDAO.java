// 包: nz.ac.cjlu.vocabmaster.dao
// WordDAO.java - 词操作的DAO。

package nz.ac.cjlu.vocabmaster.dao;

import nz.ac.cjlu.vocabmaster.model.Word;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// WordDAO.java - 词操作的DAO
public class WordDAO {
    // 数据库URL。
    private static final String DB_URL = "jdbc:derby:vocabmaster_db;create=true";

    // 方法: 获取所有词。
    // 返回词列表。
    public List<Word> getAllWords() {
        List<Word> words = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM WORDS")) {
            while (rs.next()) {
                // 创建Word对象并设置属性。
                Word word = new Word();
                word.setId(rs.getInt("ID"));
                word.setEnglish(rs.getString("ENGLISH"));
                word.setChinese(rs.getString("CHINESE"));
                word.setExampleEn(rs.getString("EXAMPLE_EN"));
                word.setExampleCn(rs.getString("EXAMPLE_CN"));
                words.add(word);
            }
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
        return words;
    }

    // 方法: 根据ID查找词。
    // 返回Word对象或null。
    public Word findById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM WORDS WHERE ID = ?")) {
            // 设置参数。
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // 创建Word对象并设置属性。
                Word word = new Word();
                word.setId(rs.getInt("ID"));
                word.setEnglish(rs.getString("ENGLISH"));
                word.setChinese(rs.getString("CHINESE"));
                word.setExampleEn(rs.getString("EXAMPLE_EN"));
                word.setExampleCn(rs.getString("EXAMPLE_CN"));
                return word;
            }
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
        return null;
    }

    // 方法: 计数所有词。
    // 返回词数。
    public int countAll() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM WORDS")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
        return 0;
    }

    // 方法: 插入预设词（由初始化器调用）。
    public void insert(Word word) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO WORDS (ENGLISH, CHINESE, EXAMPLE_EN, EXAMPLE_CN) VALUES (?, ?, ?, ?)")) {
            // 设置参数。
            stmt.setString(1, word.getEnglish());
            stmt.setString(2, word.getChinese());
            stmt.setString(3, word.getExampleEn());
            stmt.setString(4, word.getExampleCn());
            stmt.executeUpdate();
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
    }
}