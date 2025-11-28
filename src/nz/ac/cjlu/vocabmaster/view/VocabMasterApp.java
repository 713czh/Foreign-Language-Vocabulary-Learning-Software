package nz.ac.cjlu.vocabmaster.view;

import nz.ac.cjlu.vocabmaster.dao.DatabaseInitializer;
import nz.ac.cjlu.vocabmaster.controller.AuthController;
import java.awt.CardLayout;
import javax.swing.*;

public class VocabMasterApp {
    public static void main(String[] args) {
        // 1. 初始化数据库
        DatabaseInitializer.initialize();

        // 2. 在 EDT 线程中启动界面
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // 忽略，美观而已
            }

            // 3. 创建主窗口
            MainFrame frame = new MainFrame();

            // 4. 创建 CardLayout 容器
            JPanel cardPanel = new JPanel();
            CardLayout cardLayout = new CardLayout();
            cardPanel.setLayout(cardLayout);

            // 5. 创建最新版登录和注册面板（必须传 lambda！）
            LoginPanel loginPanel = new LoginPanel(() -> cardLayout.show(cardPanel, "register"));
            RegisterPanel registerPanel = new RegisterPanel(() -> cardLayout.show(cardPanel, "login"));

            // 6. 添加面板
            cardPanel.add(loginPanel, "login");
            cardPanel.add(registerPanel, "register");

            // 7. 把 cardPanel 加到 frame
            frame.add(cardPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // 8. 创建控制器，绑定所有事件
            new AuthController(loginPanel, registerPanel, frame);

            // 9. 默认显示登录页
            cardLayout.show(cardPanel, "login");

            System.out.println("词汇大师启动成功！请注册 admin 账号！");
        });
    }
}