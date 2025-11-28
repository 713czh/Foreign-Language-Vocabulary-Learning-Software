package nz.ac.cjlu.vocabmaster.view;

import javax.swing.*;
import java.awt.*;

// DashboardPanel.java - View for statistics
public class DashboardPanel extends JPanel {
    private JLabel totalLabel = new JLabel();
    private JLabel knownLabel = new JLabel();
    private JLabel unknownLabel = new JLabel();
    private JLabel reviewLabel = new JLabel();

    public DashboardPanel() {
        setLayout(new GridLayout(4, 1));
        add(totalLabel);
        add(knownLabel);
        add(unknownLabel);
        add(reviewLabel);
    }

    public void updateStats(int total, int known, int unknown, int review) {
        totalLabel.setText("Total Words: " + total);
        knownLabel.setText("Known Words: " + known);
        unknownLabel.setText("Unknown Words: " + unknown);
        reviewLabel.setText("Words to Review Today: " + review);
    }
}