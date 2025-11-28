package nz.ac.cjlu.vocabmaster.controller;

import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;
import nz.ac.cjlu.vocabmaster.view.DashboardPanel;
import nz.ac.cjlu.vocabmaster.service.DashboardService;

// DashboardController.java
// Handles dashboard statistics display.
// Delegates to DashboardService for complex calculations (e.g., counting known/unknown words).
public class DashboardController {
    private DashboardPanel view;
    private DashboardService service;
    private int userId;

    public DashboardController(DashboardPanel view, int userId) {
        this.view = view;
        this.userId = userId;
        this.service = new DashboardService(new UserWordDAO(), new WordDAO());

        loadStats();
    }

    private void loadStats() {
        int totalWords = service.getTotalWords();
        int knownWords = service.getKnownWords(userId);
        int unknownWords = totalWords - knownWords;
        int reviewWords = service.getDailyReviewCount(userId);

        view.updateStats(totalWords, knownWords, unknownWords, reviewWords);
    }
}