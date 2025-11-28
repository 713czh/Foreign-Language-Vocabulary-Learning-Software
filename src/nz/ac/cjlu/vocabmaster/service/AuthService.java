// Package: nz.ac.cjlu.vocabmaster.service
// Service layer: Contains core business logic, algorithms, and complex rules.
// Separated from controllers for SOLID (Single Responsibility: services handle logic, controllers handle UI events).
// Encapsulates OO concepts: inheritance (BaseService if needed), polymorphism (overridable methods).

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
// Core algorithm: Password hashing using MD5 (simple for course; in real-world use BCrypt).
// Business rule: Check username uniqueness, hash password.
public class AuthService {
    private UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User register(String username, String password) throws Exception {
        if (userDAO.findByUsername(username) != null) {
            throw new Exception("Username already exists.");
        }
        String hashedPassword = hashPassword(password);
        User user = new User(username, hashedPassword);
        int userId = userDAO.insert(user);
        user.setId(userId);
        // Business rule: On first registration, initialize user words (all unknown)
        initializeUserWords(userId);
        return user;
    }

    public User login(String username, String password) throws Exception {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            return user;
        }
        return null;
    }

    private String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    private void initializeUserWords(int userId) {
        // Complex rule: Fetch all words and mark as unknown for new user
        WordDAO wordDAO = new WordDAO();
        UserWordDAO userWordDAO = new UserWordDAO();
        for (Word word : wordDAO.getAllWords()) {
            UserWord userWord = new UserWord(userId, word.getId(), false, null);
            userWordDAO.insert(userWord);
        }
    }
}