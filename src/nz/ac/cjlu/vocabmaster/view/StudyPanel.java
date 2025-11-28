// 包: nz.ac.cjlu.vocabmaster.view
// StudyPanel.java - 核心学习视图，具有3阶段工作流。

package nz.ac.cjlu.vocabmaster.view;

import nz.ac.cjlu.vocabmaster.model.Word;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// StudyPanel.java - 核心学习视图，具有3阶段工作流
public class StudyPanel extends JPanel {
    // 词标签。
    private JLabel wordLabel = new JLabel("", SwingConstants.CENTER);
    // 英语例句标签。
    private JLabel exampleEnLabel = new JLabel("", SwingConstants.CENTER);
    // 中文例句标签。
    private JLabel exampleCnLabel = new JLabel("", SwingConstants.CENTER);
    // 中文标签。
    private JLabel chineseLabel = new JLabel("", SwingConstants.CENTER);
    
    // 显示例句按钮。
    private JButton showExampleBtn = new JButton("显示例句");
    // 我知道了按钮。
    private JButton knownBtn = new JButton("我知道了");
    // 我还不会按钮。
    private JButton notKnownBtn = new JButton("我还不会");
    // 下一个词按钮。
    private JButton nextBtn = new JButton("下一个单词 →");
    
    // 构造函数: 设置布局和组件。
    public StudyPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // 设置词标签字体。
        wordLabel.setFont(new Font("Serif", Font.BOLD, 48));
        add(wordLabel, gbc);
        add(exampleEnLabel, gbc);
        add(exampleCnLabel, gbc);
        add(chineseLabel, gbc);

        // 按钮面板。
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(showExampleBtn);
        buttonPanel.add(knownBtn);
        buttonPanel.add(notKnownBtn);
        add(buttonPanel, gbc);

        add(nextBtn, gbc);
        // 默认隐藏下一个按钮。
        nextBtn.setVisible(false);
        // 默认焦点在“我还不会”按钮。
        notKnownBtn.requestFocus();  
    }

    // 方法: 重置到第1阶段。
    public void resetStage1(Word word) {
        // 设置词文本。
        wordLabel.setText(word.getEnglish());
        exampleEnLabel.setText("");
        exampleCnLabel.setText("");
        chineseLabel.setText("");
        // 启用按钮。
        showExampleBtn.setEnabled(true);
        knownBtn.setEnabled(true);
        notKnownBtn.setEnabled(true);
        knownBtn.setBackground(null);
        notKnownBtn.setBackground(null);
        // 隐藏下一个按钮。
        nextBtn.setVisible(false);
    }

    // 方法: 显示第2阶段。
    public void showStage2(String exampleEn) {
        // 设置英语例句。
        exampleEnLabel.setText(exampleEn);
        // 禁用显示例句按钮。
        showExampleBtn.setEnabled(false);
    }

    // 方法: 显示第3阶段。
    public void showStage3(String exampleCn, String chinese, boolean isKnown) {
        // 设置中文例句和翻译。
        exampleCnLabel.setText(exampleCn);
        chineseLabel.setText(chinese);
        // 禁用已知/未知按钮。
        knownBtn.setEnabled(false);
        notKnownBtn.setEnabled(false);
        // 设置背景颜色反馈。
        knownBtn.setBackground(isKnown ? Color.GREEN : null);
        notKnownBtn.setBackground(isKnown ? null : Color.RED);
        // 显示下一个按钮。
        nextBtn.setVisible(true);
    }

    // 方法: 添加监听器。
    public void addListeners(ActionListener showExample, ActionListener known, ActionListener notKnown, ActionListener next) {
        showExampleBtn.addActionListener(showExample);
        knownBtn.addActionListener(known);
        notKnownBtn.addActionListener(notKnown);
        nextBtn.addActionListener(next);
    }
}