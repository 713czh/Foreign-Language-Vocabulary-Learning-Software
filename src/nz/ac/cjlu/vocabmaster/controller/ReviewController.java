package nz.ac.cjlu.vocabmaster.controller;

import nz.ac.cjlu.vocabmaster.model.Word;
import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;
import nz.ac.cjlu.vocabmaster.view.DailyReviewPanel;
import nz.ac.cjlu.vocabmaster.service.ReviewService;
import javax.swing.JOptionPane;

// ReviewController.java
// Handles daily review mode, similar to study but filtered for yesterday's unknown words.
// Delegates to ReviewService for filtering algorithm (date-based query).
public class ReviewController {
    private DailyReviewPanel view;  // Reuses StudyPanel logic internally
    private ReviewService service;
    private int userId;
    private Word currentWord;

    public ReviewController(DailyReviewPanel view, int userId) {
        this.view = view;
        this.userId = userId;
        this.service = new ReviewService(new UserWordDAO(), new WordDAO());

        loadNextReviewWord();

        view.addListeners(
            e -> showStage2(),
            e -> handleKnown(true),
            e -> handleKnown(false),
            e -> loadNextReviewWord()
        );
    }

    private void loadNextReviewWord() {
        currentWord = service.getNextYesterdayUnknownWord(userId);
        if (currentWord == null) {
            JOptionPane.showMessageDialog(view, "No words to review today!");
            return;
        }
        view.resetStage1(currentWord);
    }

    private void showStage2() {
        view.showStage2(currentWord.getExampleEn());
    }

    private void handleKnown(boolean isKnown) {
        service.updateWordStatus(userId, currentWord.getId(), isKnown);
        view.showStage3(currentWord.getExampleCn(), currentWord.getChinese(), isKnown);
    }
}