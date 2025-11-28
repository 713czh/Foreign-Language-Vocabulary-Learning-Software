package nz.ac.cjlu.vocabmaster.view;

import nz.ac.cjlu.vocabmaster.model.Word;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// StudyPanel.java - Core learning view with 3-stage workflow
public class StudyPanel extends JPanel {
    private JLabel wordLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel exampleEnLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel exampleCnLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel chineseLabel = new JLabel("", SwingConstants.CENTER);
    
    private JButton showExampleBtn = new JButton("显示例句");
    private JButton knownBtn = new JButton("我知道了");
    private JButton notKnownBtn = new JButton("我还不会");
    private JButton nextBtn = new JButton("下一个单词 →");
    
    public StudyPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        wordLabel.setFont(new Font("Serif", Font.BOLD, 48));
        add(wordLabel, gbc);
        add(exampleEnLabel, gbc);
        add(exampleCnLabel, gbc);
        add(chineseLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(showExampleBtn);
        buttonPanel.add(knownBtn);
        buttonPanel.add(notKnownBtn);
        add(buttonPanel, gbc);

        add(nextBtn, gbc);
        nextBtn.setVisible(false);
        notKnownBtn.requestFocus();  // Default highlight
    }

    public void resetStage1(Word word) {
        wordLabel.setText(word.getEnglish());
        exampleEnLabel.setText("");
        exampleCnLabel.setText("");
        chineseLabel.setText("");
        showExampleBtn.setEnabled(true);
        knownBtn.setEnabled(true);
        notKnownBtn.setEnabled(true);
        knownBtn.setBackground(null);
        notKnownBtn.setBackground(null);
        nextBtn.setVisible(false);
    }

    public void showStage2(String exampleEn) {
        exampleEnLabel.setText(exampleEn);
        showExampleBtn.setEnabled(false);
    }

    public void showStage3(String exampleCn, String chinese, boolean isKnown) {
        exampleCnLabel.setText(exampleCn);
        chineseLabel.setText(chinese);
        knownBtn.setEnabled(false);
        notKnownBtn.setEnabled(false);
        knownBtn.setBackground(isKnown ? Color.GREEN : null);
        notKnownBtn.setBackground(isKnown ? null : Color.RED);
        nextBtn.setVisible(true);
    }

    public void addListeners(ActionListener showExample, ActionListener known, ActionListener notKnown, ActionListener next) {
        showExampleBtn.addActionListener(showExample);
        knownBtn.addActionListener(known);
        notKnownBtn.addActionListener(notKnown);
        nextBtn.addActionListener(next);
    }
}