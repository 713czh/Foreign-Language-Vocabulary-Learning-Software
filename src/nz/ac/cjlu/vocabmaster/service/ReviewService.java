package nz.ac.cjlu.vocabmaster.service;

import nz.ac.cjlu.vocabmaster.model.Word;
import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;
import java.sql.Date;
import java.util.Calendar;

// ReviewService.java
// Extends similar logic but with date filter.
// Algorithm: Filter by yesterday's date for review (complex date calculation).
public class ReviewService {
    private UserWordDAO userWordDAO;
    private WordDAO wordDAO;

    public ReviewService(UserWordDAO userWordDAO, WordDAO wordDAO) {
        this.userWordDAO = userWordDAO;
        this.wordDAO = wordDAO;
    }

    public Word getNextYesterdayUnknownWord(int userId) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = new Date(cal.getTime().getTime());
        int wordId = userWordDAO.getRandomYesterdayUnknownWordId(userId, yesterday);
        if (wordId == -1) return null;
        return wordDAO.findById(wordId);
    }

    public void updateWordStatus(int userId, int wordId, boolean isKnown) {
        // Reuse StudyService logic for consistency (composition over inheritance)
        new StudyService(userWordDAO, wordDAO).updateWordStatus(userId, wordId, isKnown);
    }
}