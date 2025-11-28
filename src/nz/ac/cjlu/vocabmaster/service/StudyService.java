// 包: nz.ac.cjlu.vocabmaster.service
// StudyService.java
// 核心算法: 未知词的随机选择（使用SQL RANDOM()以提高效率）。
// 业务规则: 更新状态，封装日期处理。

package nz.ac.cjlu.vocabmaster.service;

import nz.ac.cjlu.vocabmaster.model.Word;
import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;
import nz.ac.cjlu.vocabmaster.model.UserWord;
import java.sql.Date;
import java.util.Calendar;

// StudyService.java
// 核心算法: 未知词的随机选择（使用SQL RANDOM()以提高效率）。
// 业务规则: 更新状态，封装日期处理。
public class StudyService {
    // 用户词DAO。
    private UserWordDAO userWordDAO;
    // 词DAO。
    private WordDAO wordDAO;

    // 构造函数: 初始化DAO。
    public StudyService(UserWordDAO userWordDAO, WordDAO wordDAO) {
        this.userWordDAO = userWordDAO;
        this.wordDAO = wordDAO;
    }

    // 方法: 获取随机未知词。
    public Word getRandomUnknownWord(int userId) {
        // 获取随机未知词ID。
        int wordId = userWordDAO.getRandomUnknownWordId(userId);
        if (wordId == -1) return null;
        // 根据ID查找词。
        return wordDAO.findById(wordId);
    }

    // 方法: 更新词状态。
    public void updateWordStatus(int userId, int wordId, boolean isKnown) {
        // 获取今日日期。
        Date today = new Date(Calendar.getInstance().getTime().getTime());
        // 查找UserWord。
        UserWord userWord = userWordDAO.findByUserAndWord(userId, wordId);
        // 设置已知状态。
        userWord.setKnown(isKnown);
        if (!isKnown) {
            // 如果未知，更新日期。
            userWord.setLastReviewDate(today);  
        }
        // 更新数据库。
        userWordDAO.update(userWord);
    }
}