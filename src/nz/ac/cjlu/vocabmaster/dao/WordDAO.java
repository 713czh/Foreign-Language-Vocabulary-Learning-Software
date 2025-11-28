package nz.ac.cjlu.vocabmaster.dao;

import nz.ac.cjlu.vocabmaster.model.Word;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// WordDAO.java - DAO for word operations
public class WordDAO {
    private static final String DB_URL = "jdbc:derby:vocabmaster_db;create=true";

    public List<Word> getAllWords() {
        List<Word> words = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM WORDS")) {
            while (rs.next()) {
                Word word = new Word();
                word.setId(rs.getInt("ID"));
                word.setEnglish(rs.getString("ENGLISH"));
                word.setChinese(rs.getString("CHINESE"));
                word.setExampleEn(rs.getString("EXAMPLE_EN"));
                word.setExampleCn(rs.getString("EXAMPLE_CN"));
                words.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public Word findById(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM WORDS WHERE ID = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Word word = new Word();
                word.setId(rs.getInt("ID"));
                word.setEnglish(rs.getString("ENGLISH"));
                word.setChinese(rs.getString("CHINESE"));
                word.setExampleEn(rs.getString("EXAMPLE_EN"));
                word.setExampleCn(rs.getString("EXAMPLE_CN"));
                return word;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countAll() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM WORDS")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Method to insert pre-set words (called by initializer)
    public void insert(Word word) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO WORDS (ENGLISH, CHINESE, EXAMPLE_EN, EXAMPLE_CN) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, word.getEnglish());
            stmt.setString(2, word.getChinese());
            stmt.setString(3, word.getExampleEn());
            stmt.setString(4, word.getExampleCn());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}