package foreign.language.vocabulary;

import nz.ac.cjlu.vocabmaster.dao.DatabaseInitializer;
import nz.ac.cjlu.vocabmaster.view.MainFrame;
import nz.ac.cjlu.vocabmaster.view.LoginPanel;
import nz.ac.cjlu.vocabmaster.view.RegisterPanel;
import nz.ac.cjlu.vocabmaster.controller.AuthController;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) {
        // 1. 初始化数据库（自动建表）
        DatabaseInitializer.initialize();

        // 2. 创建主窗口
        MainFrame frame = new MainFrame();

        // 3. 创建 CardLayout 和容器面板
        JPanel cardPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // 4. 创建登录和注册面板（关键！传 lambda 控制页面跳转）
        LoginPanel loginPanel = new LoginPanel(() -> cardLayout.show(cardPanel, "register"));
        RegisterPanel registerPanel = new RegisterPanel(() -> cardLayout.show(cardPanel, "login"));

        // 5. 把两个面板加入 CardLayout
        cardPanel.add(loginPanel, "login");
        cardPanel.add(registerPanel, "register");

        // 6. 把 cardPanel 加到 MainFrame 中
        frame.add(cardPanel);
        frame.setVisible(true);

        // 7. 创建 AuthController，绑定事件（这句必须在面板创建后！）
        new AuthController(loginPanel, registerPanel, frame);

        // 8. 默认显示登录页
        cardLayout.show(cardPanel, "login");

        System.out.println("词汇大师启动成功！现在可以注册 admin 了！");
    }
}