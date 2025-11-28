package nz.ac.cjlu.vocabmaster.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// LoginPanel.java - View for login UI
public class LoginPanel extends JPanel {
    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton loginButton = new JButton("Login");

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

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
}