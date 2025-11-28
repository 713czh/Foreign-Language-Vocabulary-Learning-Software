// Package: nz.ac.cjlu.vocabmaster.controller
// Note: This package contains the controllers that handle user interactions, delegate to services for business logic, and update views.
// All controllers follow SOLID principles: Single Responsibility (handle specific UI events), Dependency Inversion (depend on abstractions like services and DAOs).

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
// Handles authentication logic: registration and login.
// Delegates complex business rules (e.g., password hashing, uniqueness check) to AuthService.
public class AuthController {
    private LoginPanel loginView;
    private RegisterPanel registerView;
    private MainFrame mainFrame;
    private AuthService authService;
    private UserDAO userDAO;

    public AuthController(LoginPanel loginView, RegisterPanel registerView, MainFrame mainFrame) {
        this.loginView = loginView;
        this.registerView = registerView;
        this.mainFrame = mainFrame;
        this.userDAO = new UserDAO();
        this.authService = new AuthService(userDAO);

        loginView.addLoginListener(new LoginListener());
        registerView.addRegisterListener(new RegisterListener());
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            try {
                User user = authService.login(username, password);
                if (user != null) {
                    mainFrame.showDashboard(user.getId());
                    JOptionPane.showMessageDialog(mainFrame, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Login failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = registerView.getUsername();
            String password = registerView.getPassword();
            try {
                User user = authService.register(username, password);
                JOptionPane.showMessageDialog(mainFrame, "Registration successful! User ID: " + user.getId());
                mainFrame.showLogin();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Registration failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}