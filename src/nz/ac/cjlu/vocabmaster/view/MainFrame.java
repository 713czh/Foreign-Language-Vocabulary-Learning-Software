// 包: nz.ac.cjlu.vocabmaster.view
// MainFrame.java - 主窗口，使用CardLayout进行面板切换。

package nz.ac.cjlu.vocabmaster.view;

import javax.swing.*;
import java.awt.*;

// MainFrame.java - 主窗口，使用CardLayout进行面板切换
public class MainFrame extends JFrame {
    // CardLayout布局。
    private CardLayout cardLayout = new CardLayout();
    // 主面板。
    private JPanel mainPanel = new JPanel(cardLayout);

    // 构造函数: 设置标题、大小和关闭操作。
    public MainFrame() {
        setTitle("VocabMaster");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 添加主面板（控制器中初始化面板）。
        add(mainPanel);
    }

    // 方法: 添加面板。
    public void addPanel(String name, JPanel panel) {
        mainPanel.add(panel, name);
    }

    // 方法: 显示面板。
    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    // 示例方法: 切换到登录。
    public void showLogin() {
        showPanel("login");
    }

    // 示例方法: 切换到仪表盘。
    public void showDashboard(int userId) {
        // 初始化并显示仪表盘面板。
        DashboardPanel dashboard = new DashboardPanel();
        addPanel("dashboard", dashboard);
        new nz.ac.cjlu.vocabmaster.controller.DashboardController(dashboard, userId);
        showPanel("dashboard");
    }
    // 类似用于学习和回顾。
}