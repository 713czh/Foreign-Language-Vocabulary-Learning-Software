// 包: nz.ac.cjlu.vocabmaster.controller
// 注意: 此包包含处理用户交互的控制器，将业务逻辑委托给服务，并更新视图。
// 所有控制器遵循SOLID原则: 单一责任（处理特定UI事件）、依赖倒转（依赖于服务和DAO等抽象）。

package nz.ac.cjlu.vocabmaster.controller;

import nz.ac.cjlu.vocabmaster.model.User;
import nz.ac.cjlu.vocabmaster.dao.UserDAO;
import nz.ac.cjlu.vocabmaster.view.LoginPanel;
import nz.ac.cjlu.vocabmaster.view.RegisterPanel;
import nz.ac.cjlu.vocabmaster.view.MainFrame;
import nz.ac.cjlu.vocabmaster.service.AuthService;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// AuthController.java
// 处理认证逻辑: 注册和登录。
// 将复杂业务规则（例如密码哈希、唯一性检查）委托给AuthService。
// 通过ActionListener实现演示多态。
public class AuthController {
    // 登录和注册的视图组件。
    private LoginPanel loginView;
    private RegisterPanel registerView;
    // 主应用程序框架，用于面板切换。
    private MainFrame mainFrame;
    // 认证逻辑的服务。
    private AuthService authService;
    // 用户数据访问的DAO。
    private UserDAO userDAO;

    // 构造函数: 初始化视图、DAO、服务，并添加事件监听器。
    // 遵循依赖倒转，通过注入依赖。
    public AuthController(LoginPanel loginView, RegisterPanel registerView, MainFrame mainFrame) {
        this.loginView = loginView;
        this.registerView = registerView;
        this.mainFrame = mainFrame;
        this.userDAO = new UserDAO();
        this.authService = new AuthService(userDAO);

        // 为登录按钮添加监听器。
        loginView.addLoginListener(new LoginListener());
        // 为注册按钮添加监听器。
        registerView.addRegisterListener(new RegisterListener());
    }

    // 内部类: 登录监听器，实现ActionListener接口。
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取用户名和密码。
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            try {
                // 调用服务进行登录验证。
                User user = authService.login(username, password);
                if (user != null) {
                    // 登录成功，切换到仪表盘并显示消息。
                    mainFrame.showDashboard(user.getId());
                    JOptionPane.showMessageDialog(mainFrame, "登录成功!");
                } else {
                    // 无效凭证，显示错误消息。
                    JOptionPane.showMessageDialog(mainFrame, "无效凭证。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                // 登录失败，显示异常消息。
                JOptionPane.showMessageDialog(mainFrame, "登录失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 内部类: 注册监听器，实现ActionListener接口。
    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取用户名和密码。
            String username = registerView.getUsername();
            String password = registerView.getPassword();
            try {
                // 调用服务进行注册。
                User user = authService.register(username, password);
                // 注册成功，显示消息并切换到登录。
                JOptionPane.showMessageDialog(mainFrame, "注册成功! 用户ID: " + user.getId());
                mainFrame.showLogin();
            } catch (Exception ex) {
                // 注册失败，显示异常消息。
                JOptionPane.showMessageDialog(mainFrame, "注册失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}