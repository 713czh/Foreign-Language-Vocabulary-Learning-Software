// 包: nz.ac.cjlu.vocabmaster.view
// LoginPanel.java - 登录UI的视图。

package nz.ac.cjlu.vocabmaster.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// LoginPanel.java - 登录UI的视图
public class LoginPanel extends JPanel {
    // 用户名字段。
    private JTextField usernameField = new JTextField(20);
    // 密码字段。
    private JPasswordField passwordField = new JPasswordField(20);
    // 登录按钮。
    private JButton loginButton = new JButton("Login");

    // 构造函数: 设置布局和组件。
    public LoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        add(new JLabel("Username:"), gbc);
        gbc.gridy = 1;
        add(usernameField, gbc);
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        gbc.gridy = 3;
        add(passwordField, gbc);
        gbc.gridy = 4;
        add(loginButton, gbc);
    }

    // 方法: 获取用户名。
    public String getUsername() {
        return usernameField.getText();
    }

    // 方法: 获取密码。
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    // 方法: 添加登录监听器。
    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
}