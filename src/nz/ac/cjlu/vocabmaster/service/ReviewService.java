// 包: nz.ac.cjlu.vocabmaster.service
// ReviewService.java
// 扩展类似逻辑，但带有日期过滤。
// 算法: 过滤昨日日期以回顾（复杂日期计算）。

package nz.ac.cjlu.vocabmaster.service;

import nz.ac.cjlu.vocabmaster.model.Word;
import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;
import java.sql.Date;
import java.util.Calendar;

// ReviewService.java
// 扩展类似逻辑，但带有日期过滤。
// 算法: 过滤昨日日期以回顾（复杂日期计算）。
public class ReviewService {
    // 用户词DAO。
    private UserWordDAO userWordDAO;
    // 词DAO。
    private WordDAO wordDAO;

    // 构造函数: 初始化DAO。
    public ReviewService(UserWordDAO userWordDAO, WordDAO wordDAO) {
        this.userWordDAO = userWordDAO;
        this.wordDAO = wordDAO;
    }

    // 方法: 获取下一个昨日未知词。
    public Word getNextYesterdayUnknownWord(int userId) {
        // 计算昨日日期。
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = new Date(cal.getTime().getTime());
        // 获取随机昨日未知词ID。
        int wordId = userWordDAO.getRandomYesterdayUnknownWordId(userId, yesterday);
        if (wordId == -1) return null;
        // 根据ID查找词。
        return wordDAO.findById(wordId);
    }

    // 方法: 更新词状态。
    public void updateWordStatus(int userId, int wordId, boolean isKnown) {
        // 复用StudyService逻辑以保持一致性（组合优于继承）。
        new StudyService(userWordDAO, wordDAO).updateWordStatus(userId, wordId, isKnown);
    }
}