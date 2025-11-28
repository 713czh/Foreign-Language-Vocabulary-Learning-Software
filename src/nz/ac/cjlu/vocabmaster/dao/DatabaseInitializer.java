// 包: nz.ac.cjlu.vocabmaster.dao
// DatabaseInitializer.java - 自动初始化Derby DB（无需手动配置）。

package nz.ac.cjlu.vocabmaster.dao;

import nz.ac.cjlu.vocabmaster.model.Word;
import java.sql.*;

// DatabaseInitializer.java - 自动初始化Derby DB（无需手动配置）
public class DatabaseInitializer {
    // 数据库URL。
    private static final String DB_URL = "jdbc:derby:vocabmaster_db;create=true";
    // 初始化标志。
    private static boolean initialized = false;

    // 静态块: 调用初始化。
    static {
        initialize();
    }

    // 公共方法: 初始化数据库。
    public static void initialize() {
        if (initialized) return;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // 创建表。
            createTables(conn);
            // 插入预设词。
            insertPresetWords(conn);
            initialized = true;
        } catch (SQLException e) {
            // 打印异常栈。
            e.printStackTrace();
        }
    }

    // 私有方法: 创建表。
    private static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        // USERS表。
        stmt.execute("CREATE TABLE IF NOT EXISTS USERS (" +
                "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "USERNAME VARCHAR(50) UNIQUE NOT NULL, " +
                "PASSWORD VARCHAR(100) NOT NULL, " +
                "CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

        // WORDS表。
        stmt.execute("CREATE TABLE IF NOT EXISTS WORDS (" +
                "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "ENGLISH VARCHAR(100) NOT NULL, " +
                "CHINESE VARCHAR(100) NOT NULL, " +
                "EXAMPLE_EN VARCHAR(400), " +
                "EXAMPLE_CN VARCHAR(400))");

        // USER_WORDS表。
        stmt.execute("CREATE TABLE IF NOT EXISTS USER_WORDS (" +
                "USER_ID INT REFERENCES USERS(ID), " +
                "WORD_ID INT REFERENCES WORDS(ID), " +
                "IS_KNOWN BOOLEAN DEFAULT FALSE, " +
                "LAST_REVIEW_DATE DATE, " +
                "PRIMARY KEY (USER_ID, WORD_ID))");
    }

    // 私有方法: 插入预设词。
    private static void insertPresetWords(Connection conn) throws SQLException {
        if (hasWords(conn)) return;  // 避免重复插入

        WordDAO wordDAO = new WordDAO();
        // 180个预设词（这里样例5个；扩展真实数据）。
        String[][] presetData = {
                {"apple", "苹果", "She eats an apple every day.", "她每天都吃一个苹果。"},
                {"book", "书", "I read a book yesterday.", "我昨天读了一本书。"},
                {"cat", "猫", "The cat is sleeping.", "猫在睡觉。"},
                {"dog", "狗", "The dog barks loudly.", "狗大声叫。"},
                {"house", "房子", "We live in a big house.", "我们住在一个大房子里。"}
                // 添加175个更多高频词...
        };

        // 循环插入词。
        for (String[] data : presetData) {
            Word word = new Word(data[0], data[1], data[2], data[3]);
            wordDAO.insert(word);
        }
    }

    // 私有方法: 检查是否有词。
    private static boolean hasWords(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM WORDS");
        rs.next();
        return rs.getInt(1) > 0;
    }
}