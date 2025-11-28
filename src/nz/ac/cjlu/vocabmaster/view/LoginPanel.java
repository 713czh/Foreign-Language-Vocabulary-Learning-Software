package nz.ac.cjlu.vocabmaster.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginPanel(Runnable onRegisterClick) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 标题
        JLabel title = new JLabel("词汇大师", JLabel.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 36));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        // 用户名
        JLabel userLabel = new JLabel("用户名：");
        userLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(userLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        gbc.gridx = 1;
        add(usernameField, gbc);

        // 密码
        JLabel passLabel = new JLabel("密码：");
        passLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        gbc.gridx = 0; gbc.gridy = 2;
        add(passLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        gbc.gridx = 1;
        add(passwordField, gbc);

        // 登录按钮
        loginButton = new JButton("登录");
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        loginButton.setBackground(new Color(0, 122, 255));
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(loginButton, gbc);

        // 注册按钮
        registerButton = new JButton("没有账号？点这里注册");
        registerButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        gbc.gridy = 4;
        add(registerButton, gbc);

        // 注册按钮点击 → 跳转注册页
        registerButton.addActionListener(e -> onRegisterClick.run());
    }

    public String getUsername() { return usernameField.getText(); }
    public String getPassword() { return new String(passwordField.getPassword()); }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
}