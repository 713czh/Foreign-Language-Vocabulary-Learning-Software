package nz.ac.cjlu.vocabmaster.service;

import nz.ac.cjlu.vocabmaster.model.Word;
import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;
import nz.ac.cjlu.vocabmaster.model.UserWord;
import java.sql.Date;
import java.util.Calendar;

// StudyService.java
// Core algorithm: Random selection of unknown words (using SQL RANDOM() for efficiency).
// Business rule: Update status, encapsulate date handling.
public class StudyService {
    private UserWordDAO userWordDAO;
    private WordDAO wordDAO;

    public StudyService(UserWordDAO userWordDAO, WordDAO wordDAO) {
        this.userWordDAO = userWordDAO;
        this.wordDAO = wordDAO;
    }

    public Word getRandomUnknownWord(int userId) {
        int wordId = userWordDAO.getRandomUnknownWordId(userId);
        if (wordId == -1) return null;
        return wordDAO.findById(wordId);
    }

    public void updateWordStatus(int userId, int wordId, boolean isKnown) {
        Date today = new Date(Calendar.getInstance().getTime().getTime());
        UserWord userWord = userWordDAO.findByUserAndWord(userId, wordId);
        userWord.setKnown(isKnown);
        if (!isKnown) {
            userWord.setLastReviewDate(today);  // Only update date if not known
        }
        userWordDAO.update(userWord);
    }
}