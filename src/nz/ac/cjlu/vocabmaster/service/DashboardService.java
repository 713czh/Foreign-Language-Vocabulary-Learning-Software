package nz.ac.cjlu.vocabmaster.service;

import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;

// DashboardService.java
// Core calculations: Aggregate stats using SQL queries for efficiency.
public class DashboardService {
    private UserWordDAO userWordDAO;
    private WordDAO wordDAO;

    public DashboardService(UserWordDAO userWordDAO, WordDAO wordDAO) {
        this.userWordDAO = userWordDAO;
        this.wordDAO = wordDAO;
    }

    public int getTotalWords() {
        return wordDAO.countAll();
    }

    public int getKnownWords(int userId) {
        return userWordDAO.countKnown(userId);
    }

    public int getDailyReviewCount(int userId) {
        return userWordDAO.countYesterdayUnknown(userId);
    }
}