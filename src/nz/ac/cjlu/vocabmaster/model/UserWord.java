// 包: nz.ac.cjlu.vocabmaster.model
// UserWord.java - 用户词关系的模型。
// 封装学习状态（OO封装）。

package nz.ac.cjlu.vocabmaster.model;

import java.sql.Date;

// UserWord.java - 用户词关系的模型
// 封装学习状态（OO封装）
public class UserWord {
    // 用户ID。
    private int userId;
    // 词ID。
    private int wordId;
    // 是否已知。
    private boolean known;
    // 上次回顾日期。
    private Date lastReviewDate;

    // 默认构造函数。
    public UserWord() {}

    // 带参数构造函数。
    public UserWord(int userId, int wordId, boolean known, Date lastReviewDate) {
        this.userId = userId;
        this.wordId = wordId;
        this.known = known;
        this.lastReviewDate = lastReviewDate;
    }

    // Getter和Setter
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