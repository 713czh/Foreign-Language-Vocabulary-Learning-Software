// 包: nz.ac.cjlu.vocabmaster.view
// DashboardPanel.java - 统计的视图。

package nz.ac.cjlu.vocabmaster.view;

import javax.swing.*;
import java.awt.*;

// DashboardPanel.java - 统计的视图
public class DashboardPanel extends JPanel {
    // 总词标签。
    private JLabel totalLabel = new JLabel();
    // 已知词标签。
    private JLabel knownLabel = new JLabel();
    // 未知词标签。
    private JLabel unknownLabel = new JLabel();
    // 回顾词标签。
    private JLabel reviewLabel = new JLabel();

    // 构造函数: 设置布局和添加标签。
    public DashboardPanel() {
        setLayout(new GridLayout(4, 1));
        add(totalLabel);
        add(knownLabel);
        add(unknownLabel);
        add(reviewLabel);
    }

    // 方法: 更新统计。
    public void updateStats(int total, int known, int unknown, int review) {
        totalLabel.setText("总词数: " + total);
        knownLabel.setText("已知词数: " + known);
        unknownLabel.setText("未知词数: " + unknown);
        reviewLabel.setText("今日回顾词数: " + review);
    }
}