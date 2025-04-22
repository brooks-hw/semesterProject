package ui;

import javax.swing.*;
import java.awt.*;

public class PostLoginPromptPage extends JPanel {
    private final iScreenManager screenManager;

    public PostLoginPromptPage(iScreenManager screenManager) {
        this.screenManager = screenManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(new Color(14, 42, 83));

        // Title
        JLabel title = new JLabel("Next Steps");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Book Antiqua", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        add(Box.createVerticalStrut(60));
        add(title);
        add(Box.createVerticalStrut(30));

        // Prompt
        JLabel question = new JLabel("<html><div style='text-align: center;'>Would you like to complete the questionnaire<br>or go to your homepage?</div></html>");
        question.setMaximumSize(new Dimension(600, 60));
        question.setHorizontalAlignment(SwingConstants.CENTER);
        question.setAlignmentX(Component.CENTER_ALIGNMENT);
        question.setFont(new Font("Calibri", Font.PLAIN, 22));
        question.setForeground(Color.WHITE);
        add(question);
        add(Box.createVerticalStrut(40));

        // Buttons
        add(createButton("Questionnaire", () -> screenManager.switchTo("Investment Form")));
        add(Box.createVerticalStrut(20));
        add(createButton("Homepage", () -> screenManager.switchTo("Home Page")));
        add(Box.createVerticalStrut(60));
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(137, 207, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(250, 45));
        button.addActionListener(e -> action.run());
        return button;
    }
}
