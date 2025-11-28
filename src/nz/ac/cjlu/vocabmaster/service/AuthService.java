package nz.ac.cjlu.vocabmaster.service;

import nz.ac.cjlu.vocabmaster.model.User;
import nz.ac.cjlu.vocabmaster.model.Word;
import nz.ac.cjlu.vocabmaster.model.UserWord;
import nz.ac.cjlu.vocabmaster.dao.UserDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;
import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;

public class AuthService {
    private UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    // 注册 - 返回 boolean（成功true，失败false）
    public boolean register(String username, String password) {
        if (username == null || username.trim().isEmpty() || password.isEmpty()) {
            return false;
        }
        if (userDAO.findByUsername(username) != null) {
            return false; // 用户名已存在
        }

        User user = new User(username, password); // 明文密码
        boolean success = userDAO.register(user);
        if (success) {
            initializeUserWords(user.getId()); // 初始化用户单词表
        }
        return success;
    }

    // 登录 - 返回 User 或 null
    public User login(String username, String password) {
        return userDAO.validateUser(username, password);
    }

    // 初始化用户单词表（每个单词都是“未知”）
    private void initializeUserWords(int userId) {
        WordDAO wordDAO = new WordDAO();
        UserWordDAO userWordDAO = new UserWordDAO();
        for (Word word : wordDAO.getAllWords()) {
            UserWord uw = new UserWord(userId, word.getId(), false, null);
            userWordDAO.insert(uw);
        }
    }
}