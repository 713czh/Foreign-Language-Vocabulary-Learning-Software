// 包: nz.ac.cjlu.vocabmaster.controller
// DashboardController.java
// 处理仪表盘统计显示。
// 将复杂计算（例如计数已知/未知词）委托给DashboardService。

package nz.ac.cjlu.vocabmaster.controller;

import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;
import nz.ac.cjlu.vocabmaster.view.DashboardPanel;
import nz.ac.cjlu.vocabmaster.service.DashboardService;

public class DashboardController {
    // 仪表盘视图。
    private DashboardPanel view;
    // 仪表盘服务。
    private DashboardService service;
    // 用户ID。
    private int userId;

    // 构造函数: 初始化视图、服务和用户ID，并加载统计数据。
    public DashboardController(DashboardPanel view, int userId) {
        this.view = view;
        this.userId = userId;
        this.service = new DashboardService(new UserWordDAO(), new WordDAO());

        // 加载统计数据。
        loadStats();
    }

    // 私有方法: 加载统计数据并更新视图。
    private void loadStats() {
        // 获取总词数。
        int totalWords = service.getTotalWords();
        // 获取已知词数。
        int knownWords = service.getKnownWords(userId);
        // 计算未知词数。
        int unknownWords = totalWords - knownWords;
        // 获取每日回顾词数。
        int reviewWords = service.getDailyReviewCount(userId);

        // 更新视图统计。
        view.updateStats(totalWords, knownWords, unknownWords, reviewWords);
    }
}