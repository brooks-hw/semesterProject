package ui;

import javax.swing.*;
import java.awt.*;

public class StartPage extends JPanel {

    private iScreenManager screenManager;
    private Image backgroundImage;

    // Constructor, builds the StartPage. ScreenManager interface is passed to handle switches
    StartPage(iScreenManager screenManager) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));
        this.backgroundImage = new ImageIcon("images/image2.jpg").getImage();
        this.screenManager = screenManager;


        JPanel leftAlignPanel = createLeftAlignPanel();
        leftAlignPanel.add(createTitle("Investment"));
        leftAlignPanel.add(createTitle("Buddy"));
        leftAlignPanel.add(Box.createVerticalStrut(20));
        leftAlignPanel.add(createSeparator());
        leftAlignPanel.add(Box.createVerticalStrut(20));
        leftAlignPanel.add(createStartButton());

        leftAlignPanel.setOpaque(false);
        this.add(leftAlignPanel);
        this.setOpaque(false);
    }

    private static JPanel createLeftAlignPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private static JLabel createTitle(String text) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Arial", Font.BOLD, 60));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setForeground(Color.WHITE);
        return title;
    }

    private static JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(325, 8));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        separator.setBackground(new Color(192, 192, 192));
        return separator;
    }

    private JButton createStartButton() {
        JButton startButton = new JButton("  start  ");
        startButton.setFont(new Font("Arial", Font.PLAIN, 60));
        startButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        startButton.setBackground(new Color(184, 134, 11));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);


        startButton.addActionListener(e -> {
            this.screenManager.switchTo("Login Page");
        });

        return startButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
