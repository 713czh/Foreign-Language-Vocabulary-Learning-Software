package nz.ac.cjlu.vocabmaster.view;

import javax.swing.*;
import java.awt.*;

// MainFrame.java - Main window using CardLayout for panel switching
public class MainFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    public MainFrame() {
        setTitle("VocabMaster");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add panels (initialized in controllers)
        add(mainPanel);
    }

    public void addPanel(String name, JPanel panel) {
        mainPanel.add(panel, name);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    // Example methods for switching
    public void showLogin() {
        showPanel("login");
    }

    public void showDashboard(int userId) {
        // Initialize and show dashboard panel
        DashboardPanel dashboard = new DashboardPanel();
        addPanel("dashboard", dashboard);
        new nz.ac.cjlu.vocabmaster.controller.DashboardController(dashboard, userId);
        showPanel("dashboard");
    }

    // Similar for study and review
}