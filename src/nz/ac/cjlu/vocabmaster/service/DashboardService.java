// 包: nz.ac.cjlu.vocabmaster.service
// DashboardService.java
// 核心计算: 使用SQL查询聚合统计以提高效率。

package nz.ac.cjlu.vocabmaster.service;

import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;

// DashboardService.java
// 核心计算: 使用SQL查询聚合统计以提高效率。
public class DashboardService {
    // 用户词DAO。
    private UserWordDAO userWordDAO;
    // 词DAO。
    private WordDAO wordDAO;

    // 构造函数: 初始化DAO。
    public DashboardService(UserWordDAO userWordDAO, WordDAO wordDAO) {
        this.userWordDAO = userWordDAO;
        this.wordDAO = wordDAO;
    }

    // 方法: 获取总词数。
    public int getTotalWords() {
        return wordDAO.countAll();
    }

    // 方法: 获取已知词数。
    public int getKnownWords(int userId) {
        return userWordDAO.countKnown(userId);
    }

    // 方法: 获取每日回顾数。
    public int getDailyReviewCount(int userId) {
        return userWordDAO.countYesterdayUnknown(userId);
    }
}