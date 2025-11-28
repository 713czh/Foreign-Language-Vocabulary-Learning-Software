package nz.ac.cjlu.vocabmaster.dao;

import java.sql.*;

/**
 * 终极永不失败版数据库初始化器
 * 只要删掉 vocabmaster_db 文件夹 + 用这个文件 → 100% 成功！
 */
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
            System.out.println("数据库和所有表创建成功！欢迎使用词汇大师！");
        } catch (SQLException e) {
            System.out.println("数据库初始化失败！请务必删除项目根目录下的 vocabmaster_db 文件夹后重试！");
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
    Statement stmt = conn.createStatement();

    // 智能建表：如果表已存在就跳过（解决 X0Y32 报错）
    String[] tables = {
        "CREATE TABLE USERS (" +
            "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
            "USERNAME VARCHAR(50) UNIQUE NOT NULL," +
            "PASSWORD VARCHAR(100) NOT NULL," +
            "CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
        ")",
        "CREATE TABLE WORDS (" +
            "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
            "ENGLISH VARCHAR(100) NOT NULL," +
            "CHINESE VARCHAR(200) NOT NULL," +
            "EXAMPLE_EN VARCHAR(500)," +
            "EXAMPLE_CN VARCHAR(500)," +
            "CATEGORY VARCHAR(30) DEFAULT 'Basic'," +
            "DIFFICULTY INT DEFAULT 3" +
        ")",
        "CREATE TABLE USER_WORDS (" +
            "USER_ID INT NOT NULL REFERENCES USERS(ID)," +
            "WORD_ID INT NOT NULL REFERENCES WORDS(ID)," +
            "IS_KNOWN BOOLEAN DEFAULT FALSE," +
            "LAST_REVIEW_DATE DATE," +
            "PRIMARY KEY (USER_ID, WORD_ID)" +
        ")"
    };

    DatabaseMetaData meta = conn.getMetaData();
    for (String sql : tables) {
        String tableName = sql.split(" ")[2]; // 提取表名
        ResultSet rs = meta.getTables(null, "APP", tableName, null);
        if (!rs.next()) {
            stmt.execute(sql);
            System.out.println(tableName + " 表创建成功！");
        } else {
            System.out.println(tableName + " 表已存在，跳过创建");
        }
        rs.close();
    }
    stmt.close();
}

    private static void insertPresetWords(Connection conn) throws SQLException {
        if (hasData(conn, "SELECT COUNT(*) FROM WORDS")) return;

        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO WORDS (ENGLISH, CHINESE, CATEGORY) VALUES " +
            "('hello', '你好', 'Basic')," +
            "('world', '世界', 'Basic')," +
            "('study', '学习', 'Basic')," +
            "('success', '成功', 'Basic')");
        stmt.close();
        System.out.println("插入4个预设单词成功！");
    }

    private static boolean hasData(Connection conn, String sql) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}