package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private static JFrame frame;

    public static void main(String[] args) {
        createWindow();
    }

    public static void createWindow() {
        frame = new JFrame("Investment Buddy");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Center window

        frame.setContentPane(menu());
        frame.setVisible(true);
    }

    public static JPanel menu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout
        panel.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0)); // Adjusts vertical positioning

        // Left-aligned panel (Everything inside will be left-aligned)
        JPanel leftAlignPanel = new JPanel();
        leftAlignPanel.setLayout(new BoxLayout(leftAlignPanel, BoxLayout.Y_AXIS));
        leftAlignPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0)); // Moves content 40px from left
        leftAlignPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Ensure full left alignment

        // Title labels
        JLabel title1 = new JLabel("investment");
        title1.setFont(new Font("Arial", Font.BOLD, 60));
        title1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title2 = new JLabel("buddy");
        title2.setFont(new Font("Arial", Font.BOLD, 60));
        title2.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Horizontal separator (aligned with the title)
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(340, 5)); // Limit width to align better
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Start button (aligned with the title)
        JButton startButton = new JButton("start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 60));
        startButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        startButton.setBackground(Color.YELLOW);

        // Add components to the left-aligned panel
        leftAlignPanel.add(title1);
        leftAlignPanel.add(title2);
        leftAlignPanel.add(Box.createVerticalStrut(20)); // Space between title and separator
        leftAlignPanel.add(separator);
        leftAlignPanel.add(Box.createVerticalStrut(20)); // Space between separator and button
        leftAlignPanel.add(startButton);

        // Add the left-aligned panel to the main panel
        panel.add(leftAlignPanel);

        return panel;
    }

    public static void promptQuestionnaire() {
    }
}
