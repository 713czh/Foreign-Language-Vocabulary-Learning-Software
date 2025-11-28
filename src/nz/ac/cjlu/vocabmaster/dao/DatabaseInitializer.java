package nz.ac.cjlu.vocabmaster.dao;

import nz.ac.cjlu.vocabmaster.model.Word;
import java.sql.*;

// DatabaseInitializer.java - Initializes Derby DB automatically (no manual config)
public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:derby:vocabmaster_db;create=true";
    private static boolean initialized = false;

    static {
        initialize();
    }

    public static void initialize() {
        if (initialized) return;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            createTables(conn);
            insertPresetWords(conn);
            initialized = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        // USERS table
        stmt.execute("CREATE TABLE IF NOT EXISTS USERS (" +
                "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "USERNAME VARCHAR(50) UNIQUE NOT NULL, " +
                "PASSWORD VARCHAR(100) NOT NULL, " +
                "CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

        // WORDS table
        stmt.execute("CREATE TABLE IF NOT EXISTS WORDS (" +
                "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "ENGLISH VARCHAR(100) NOT NULL, " +
                "CHINESE VARCHAR(100) NOT NULL, " +
                "EXAMPLE_EN VARCHAR(400), " +
                "EXAMPLE_CN VARCHAR(400))");

        // USER_WORDS table
        stmt.execute("CREATE TABLE IF NOT EXISTS USER_WORDS (" +
                "USER_ID INT REFERENCES USERS(ID), " +
                "WORD_ID INT REFERENCES WORDS(ID), " +
                "IS_KNOWN BOOLEAN DEFAULT FALSE, " +
                "LAST_REVIEW_DATE DATE, " +
                "PRIMARY KEY (USER_ID, WORD_ID))");
    }

    private static void insertPresetWords(Connection conn) throws SQLException {
        if (hasWords(conn)) return;  // Avoid duplicate inserts

        WordDAO wordDAO = new WordDAO();
        // 180 preset words (sample 5 here; expand with real data)
        String[][] presetData = {
                {"apple", "苹果", "She eats an apple every day.", "她每天都吃一个苹果。"},
                {"book", "书", "I read a book yesterday.", "我昨天读了一本书。"},
                {"cat", "猫", "The cat is sleeping.", "猫在睡觉。"},
                {"dog", "狗", "The dog barks loudly.", "狗大声叫。"},
                {"house", "房子", "We live in a big house.", "我们住在一个大房子里。"}
                // Add 175 more high-frequency words...
        };

        for (String[] data : presetData) {
            Word word = new Word(data[0], data[1], data[2], data[3]);
            wordDAO.insert(word);
        }
    }

    private static boolean hasWords(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM WORDS");
        rs.next();
        return rs.getInt(1) > 0;
    }
}