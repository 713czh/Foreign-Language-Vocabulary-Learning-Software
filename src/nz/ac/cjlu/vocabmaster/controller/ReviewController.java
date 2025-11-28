// 包: nz.ac.cjlu.vocabmaster.controller
// ReviewController.java
// 处理每日回顾模式，类似于学习但过滤昨日未知词。
// 将过滤算法委托给ReviewService（基于日期查询）。

package nz.ac.cjlu.vocabmaster.controller;

import nz.ac.cjlu.vocabmaster.model.Word;
import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;
import nz.ac.cjlu.vocabmaster.view.DailyReviewPanel;
import nz.ac.cjlu.vocabmaster.service.ReviewService;
import javax.swing.JOptionPane;

public class ReviewController {
    // 每日回顾视图（复用StudyPanel逻辑）。
    private DailyReviewPanel view;  
    // 回顾服务。
    private ReviewService service;
    // 用户ID。
    private int userId;
    // 当前词。
    private Word currentWord;

    // 构造函数: 初始化视图、服务、用户ID，并加载下一个回顾词。
    public ReviewController(DailyReviewPanel view, int userId) {
        this.view = view;
        this.userId = userId;
        this.service = new ReviewService(new UserWordDAO(), new WordDAO());

        // 加载下一个回顾词。
        loadNextReviewWord();

        // 添加监听器。
        view.addListeners(
            e -> showStage2(),
            e -> handleKnown(true),
            e -> handleKnown(false),
            e -> loadNextReviewWord()
        );
    }

    // 私有方法: 加载下一个回顾词。
    private void loadNextReviewWord() {
        // 获取下一个昨日未知词。
        currentWord = service.getNextYesterdayUnknownWord(userId);
        if (currentWord == null) {
            // 今日无词回顾，显示消息。
            JOptionPane.showMessageDialog(view, "今日无词回顾!");
            return;
        }
        // 重置视图到第1阶段。
        view.resetStage1(currentWord);
    }

    // 私有方法: 显示第2阶段。
    private void showStage2() {
        // 显示英语例句。
        view.showStage2(currentWord.getExampleEn());
    }

    // 私有方法: 处理已知/未知。
    private void handleKnown(boolean isKnown) {
        // 更新词状态。
        service.updateWordStatus(userId, currentWord.getId(), isKnown);
        // 显示第3阶段。
        view.showStage3(currentWord.getExampleCn(), currentWord.getChinese(), isKnown);
    }
}