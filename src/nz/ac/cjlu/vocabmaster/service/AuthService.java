// 包: nz.ac.cjlu.vocabmaster.service
// 服务层: 包含核心业务逻辑、算法和复杂规则。
// 与控制器分离以遵循SOLID（单一责任: 服务处理逻辑，控制器处理UI事件）。
// 封装OO概念: 继承（如果需要BaseService）、多态（可覆盖方法）。

package nz.ac.cjlu.vocabmaster.service;

import nz.ac.cjlu.vocabmaster.model.User;
import nz.ac.cjlu.vocabmaster.model.Word;
import nz.ac.cjlu.vocabmaster.model.UserWord;
import nz.ac.cjlu.vocabmaster.dao.UserDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;
import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import java.security.MessageDigest;
import java.util.Base64;

// AuthService.java
// 核心算法: 使用MD5密码哈希（课程简单；在实际中使用BCrypt）。
// 业务规则: 检查用户名唯一性，哈希密码。
public class AuthService {
    // 用户DAO。
    private UserDAO userDAO;

    // 构造函数: 初始化DAO。
    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // 方法: 注册用户。
    // 抛出异常如果失败。
    public User register(String username, String password) throws Exception {
        // 检查用户名是否存在。
        if (userDAO.findByUsername(username) != null) {
            throw new Exception("用户名已存在。");
        }
        // 哈希密码。
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword);
        // 插入用户。
        int userId = userDAO.insert(user);
        user.setId(userId);
        // 业务规则: 首次注册时初始化用户词（全部未知）。
        initializeUserWords(userId);
        return user;
    }

    // 方法: 登录用户。
    // 返回User或null。
    public User login(String username, String password) throws Exception {
        User user = userDAO.findByUsername(username);
        // 检查密码匹配。
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            return user;
        }
        return null;
    }

    // 私有方法: 哈希密码。
    private String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    // 私有方法: 初始化用户词。
    private void initializeUserWords(int userId) {
        // 复杂规则: 获取所有词并标记为未知。
        WordDAO wordDAO = new WordDAO();
        UserWordDAO userWordDAO = new UserWordDAO();
        for (Word word : wordDAO.getAllWords()) {
            UserWord userWord = new UserWord(userId, word.getId(), false, null);
            userWordDAO.insert(userWord);
        }
    }
}