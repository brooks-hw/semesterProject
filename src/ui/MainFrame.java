//NOTE: FORMATTED & ELEMENTS CREATED USING AI: CHATGPT

package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private static JFrame frame;
    private static CardLayout cardLayout = new CardLayout();
    private static JPanel cardPanel;

    // Constants for colors
    private static final Color TITLE_COLOR = new Color(240, 240, 240); // Light Gray
    private static final Color SEPARATOR_COLOR = new Color(192, 192, 192); // Silver
    private static final Color BUTTON_COLOR = new Color(184, 134, 11); // Dark Gold
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    public static void main(String[] args) {
        createWindow();
    }

    public static void createWindow() {
        frame = new JFrame("Investment Buddy");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Center window

        // Background panel with image
        BackgroundPanel backgroundImagePanel = new BackgroundPanel("images/image2.jpg"); // Load image
        backgroundImagePanel.setLayout(new BorderLayout()); // Allow child components

        // Create card panel for switching slides
        JPanel cardPanel = new JPanel(new CardLayout());
        cardPanel.setOpaque(false); // Make sure it doesn't block the background

        // Create and add both slides
        JPanel startMenu = startMenu();
        startMenu.setOpaque(false); // Ensure transparency

        InvestmentForm investmentForm = new InvestmentForm();
        investmentForm.setOpaque(false); // Ensure transparency

        cardPanel.add(startMenu, "StartMenu");
        cardPanel.add(investmentForm, "InvestmentForm");

        // Add card panel on top of the background
        backgroundImagePanel.add(cardPanel, BorderLayout.CENTER);

        // Set the frame content and refresh UI
        frame.setContentPane(backgroundImagePanel);
        frame.revalidate();
        frame.repaint();

        frame.setVisible(true);
    }

    public static JPanel startMenu() {

        //FORMATING LEFT JUSTIFIED
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout
        panel.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0)); // Adjusts vertical positioning

        // Left-aligned panel
        JPanel leftAlignPanel = createLeftAlignPanel();

        // Add components to the left-aligned panel
        leftAlignPanel.add(createTitle("Investment"));
        leftAlignPanel.add(createTitle("Buddy"));
        leftAlignPanel.add(Box.createVerticalStrut(20)); // Space between title and separator
        leftAlignPanel.add(createSeparator());
        leftAlignPanel.add(Box.createVerticalStrut(20)); // Space between separator and button
        leftAlignPanel.add(createStartButton());

        // Set transparency for background
        leftAlignPanel.setOpaque(false);

        panel.add(leftAlignPanel);
        panel.setOpaque(false);
        return panel;
    }

    // Create left-aligned panel with margin
    private static JPanel createLeftAlignPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0)); // Moves content 40px from left
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); // Ensure full left alignment
        return panel;
    }

    // Create title label with provided text
    private static JLabel createTitle(String text) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Arial", Font.BOLD, 60));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setForeground(TITLE_COLOR);
        return title;
    }

    // Create separator line
    private static JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(325, 8)); // Limit width to align better
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        separator.setBackground(SEPARATOR_COLOR);
        return separator;
    }

    // Create start button
    private static JButton createStartButton() {
        JButton startButton = new JButton("  start  ");
        startButton.setFont(new Font("Arial", Font.PLAIN, 60));
        startButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        startButton.setBackground(BUTTON_COLOR);
        startButton.setForeground(BUTTON_TEXT_COLOR);
        startButton.setFocusPainted(false);

        startButton.addActionListener(e -> {
            // Get the CardLayout instance from the cardPanel
            CardLayout cardLayout = (CardLayout) ((JPanel) frame.getContentPane().getComponent(0)).getLayout();
            cardLayout.show((JPanel) frame.getContentPane().getComponent(0), "InvestmentForm");
        });

        return startButton;
    }
}