package nz.ac.cjlu.vocabmaster.model;

import java.sql.Date;

// UserWord.java - Model for user-word relationship
// Encapsulates learning status (OO encapsulation)
public class UserWord {
    private int userId;
    private int wordId;
    private boolean known;
    private Date lastReviewDate;

    public UserWord() {}

    public UserWord(int userId, int wordId, boolean known, Date lastReviewDate) {
        this.userId = userId;
        this.wordId = wordId;
        this.known = known;
        this.lastReviewDate = lastReviewDate;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public Date getLastReviewDate() {
        return lastReviewDate;
    }

    public void setLastReviewDate(Date lastReviewDate) {
        this.lastReviewDate = lastReviewDate;
    }
}