//MODIFIED USING AI

package ui;

import javax.swing.*;
import java.awt.*;

public class InvestmentForm extends JPanel {
    public InvestmentForm() {

        setLayout(new BorderLayout());

        // Create the small header for the top left corner
        JLabel smallHeader = new JLabel("Risk Profile Analysis: Q1", SwingConstants.LEFT);
        smallHeader.setFont(new Font("Arial", Font.PLAIN, 18)); // Smaller font for the header
        smallHeader.setForeground(Color.GRAY);

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);
        headerPanel.add(smallHeader);

        // Wrapper panel for centering content
        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setOpaque(false);

        // Create a panel for the question label
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setOpaque(false);

        // Main question label
        JLabel mainLabel = new JLabel("How would you react if your investments dropped by 20%?", SwingConstants.CENTER);
        mainLabel.setFont(new Font("Arial", Font.BOLD, 30));
        mainLabel.setForeground(Color.WHITE);

        labelPanel.add(mainLabel);

        // Add spacing (1 inch, 72 pixels) before buttons
        centerWrapper.add(Box.createVerticalStrut(72));
        centerWrapper.add(labelPanel);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // Add vertical spacing (1 inch, 72 pixels) before buttons
        buttonPanel.add(Box.createVerticalStrut(72));

        // Create option buttons
        String[] options = {
                "Sell all my investments.",
                "Sell some investments.",
                "Hold onto my investments.",
                "Buy more investments."
        };

        for (String option : options) {
            JButton button = new JButton(option);
            button.setFont(new Font("Arial", Font.BOLD, 30));
            button.setBackground(new Color(184, 134, 11));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setPreferredSize(new Dimension(500, 70));

            // Wrapper panel to center each button
            JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonWrapper.setOpaque(false);
            buttonWrapper.add(button);

            buttonPanel.add(buttonWrapper);
            buttonPanel.add(Box.createVerticalStrut(20));
        }

        // Add label and buttons into centerWrapper
        centerWrapper.add(buttonPanel);

        // Add everything to the main layout
        add(headerPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
    }
}