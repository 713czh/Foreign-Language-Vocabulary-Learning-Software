package nz.ac.cjlu.vocabmaster.view;

import nz.ac.cjlu.vocabmaster.service.AuthService;
import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;
    private AuthService authService = new AuthService();

    public RegisterPanel(Runnable onBackToLogin) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel title = new JLabel("创建新账号", JLabel.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 30));
        gbc.gridwidth = 2; gbc.gridx = 0; gbc.gridy = 0;
        add(title, gbc);

        add(new JLabel("用户名："), gbc(0, 1));
        usernameField = new JTextField(20);
        add(usernameField, gbc(1, 1));

        add(new JLabel("密码："), gbc(0, 2));
        passwordField = new JPasswordField(20);
        add(passwordField, gbc(1, 2));

        registerButton = new JButton("立即注册");
        registerButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        registerButton.setBackground(new Color(46, 204, 113));
        registerButton.setForeground(Color.WHITE);
        gbc.gridwidth = 2; gbc.gridy = 3;
        add(registerButton, gbc);

        backButton = new JButton("← 返回登录");
        gbc.gridy = 4;
        add(backButton, gbc);

        registerButton.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写完整！");
                return;
            }
            if (authService.register(user, pass)) {
                JOptionPane.showMessageDialog(this, "注册成功！请登录");
                usernameField.setText("");
                passwordField.setText("");
                onBackToLogin.run();
            } else {
                JOptionPane.showMessageDialog(this, "注册失败，用户名已存在！");
            }
        });

        backButton.addActionListener(e -> onBackToLogin.run());
    }

    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = x; g.gridy = y; g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        return g;
    }

    public String getUsername() { return usernameField.getText().trim(); }
    public String getPassword() { return new String(passwordField.getPassword()); }
    public void addRegisterListener(java.awt.event.ActionListener l) {
        registerButton.addActionListener(l);
    }
}