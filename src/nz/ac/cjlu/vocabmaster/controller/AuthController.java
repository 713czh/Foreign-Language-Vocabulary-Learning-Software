  package nz.ac.cjlu.vocabmaster.controller;

import nz.ac.cjlu.vocabmaster.model.User;
import nz.ac.cjlu.vocabmaster.service.AuthService;
import nz.ac.cjlu.vocabmaster.view.LoginPanel;
import nz.ac.cjlu.vocabmaster.view.RegisterPanel;
import nz.ac.cjlu.vocabmaster.view.MainFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthController {
    private LoginPanel loginView;
    private RegisterPanel registerView;
    private MainFrame mainFrame;
    private AuthService authService;

    // 关键：这个构造函数必须和 Main.java 里 new AuthController(...) 完全匹配！
    public AuthController(LoginPanel loginView, RegisterPanel registerView, MainFrame mainFrame) {
        this.loginView = loginView;
        this.registerView = registerView;
        this.mainFrame = mainFrame;
        this.authService = new AuthService();

        // 绑定登录按钮事件
        loginView.addLoginListener(new LoginListener());

        // 绑定注册按钮事件（RegisterPanel 里你自己也写了注册逻辑，这里再加一层保险）
        registerView.addRegisterListener(new RegisterListener());
    }

    // 登录
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername().trim();
            String password = loginView.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "请填写用户名和密码！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = authService.login(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(mainFrame, "登录成功！欢迎 " + username + "！");
                mainFrame.showDashboard(user.getId());
            } else {
                JOptionPane.showMessageDialog(mainFrame, "用户名或密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 注册（你 RegisterPanel 里已经写了，这里再加一层也可以用）
    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = registerView.getUsername().trim();
            String password = registerView.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "请填写完整信息！");
                return;
            }

            if (authService.register(username, password)) {
                JOptionPane.showMessageDialog(mainFrame, "注册成功！请登录", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "注册失败，用户名已存在！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}