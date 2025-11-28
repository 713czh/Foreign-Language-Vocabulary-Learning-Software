// 包: nz.ac.cjlu.vocabmaster.controller
// StudyController.java
// 学习模式的核心业务逻辑。
// 将算法委托给StudyService: 随机词选择、状态更新。
// 实现指定的精确3阶段工作流。
// 由ChatGPT生成按钮逻辑和阶段过渡（经过小修改以集成）。

package nz.ac.cjlu.vocabmaster.controller;

import nz.ac.cjlu.vocabmaster.model.Word;
import nz.ac.cjlu.vocabmaster.dao.UserWordDAO;
import nz.ac.cjlu.vocabmaster.dao.WordDAO;
import nz.ac.cjlu.vocabmaster.view.StudyPanel;
import nz.ac.cjlu.vocabmaster.service.StudyService;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class StudyController {
    // 学习视图。
    private StudyPanel view;
    // 学习服务。
    private StudyService service;
    // 用户ID。
    private int userId;
    // 当前词。
    private Word currentWord;

    // 构造函数: 初始化视图、服务、用户ID，并加载下一个词。
    public StudyController(StudyPanel view, int userId) {
        this.view = view;
        this.userId = userId;
        this.service = new StudyService(new UserWordDAO(), new WordDAO());

        // 初始加载: 第1阶段。
        loadNextWord();  

        // 添加监听器。
        view.addListeners(
            e -> showStage2(),  // 显示例句（仅英语）
            e -> handleKnown(true),  // 我知道了
            e -> handleKnown(false),  // 我还不会
            e -> loadNextWord()  // 下一个词
        );
    }

    // 私有方法: 加载下一个词。
    private void loadNextWord() {
        // 获取随机未知词。
        currentWord = service.getRandomUnknownWord(userId);
        if (currentWord == null) {
            // 所有词已掌握，显示消息。
            JOptionPane.showMessageDialog(view, "所有词已掌握! 做得好!");
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