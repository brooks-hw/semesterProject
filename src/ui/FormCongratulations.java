package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormCongratulations extends JPanel {
    public FormCongratulations() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        JLabel congratsLabel = new JLabel("Congratulations!", SwingConstants.CENTER);
        congratsLabel.setFont(new Font("Arial", Font.BOLD, 36));
        congratsLabel.setForeground(new Color(50, 205, 50)); // LimeGreen

        JLabel messageLabel = new JLabel("You’ve completed the Risk Profile Analysis.", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        messageLabel.setForeground(Color.WHITE);

        JPanel labelPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        labelPanel.setOpaque(false);
        labelPanel.add(congratsLabel);
        labelPanel.add(messageLabel);

        add(Box.createVerticalGlue());
        add(labelPanel);
        add(Box.createVerticalGlue());

        //button options
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);

        JButton results = new JButton("View Results");
        results.setFont(new Font("Arial", Font.BOLD, 25));
        results.setBackground(new Color(184, 134, 11));
        results.setForeground(Color.WHITE);
        results.setFocusPainted(false);
        results.setAlignmentX(Component.CENTER_ALIGNMENT);
        results.setPreferredSize(new Dimension(220, 45));

        results.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cardLayout.show(loginPanel, "InvestmentForm");
            }
        });

        buttonPanel.add(Box.createVerticalStrut(130));
        buttonPanel.add(results);

        JButton home = new JButton("Homepage");
        home.setFont(new Font("Arial", Font.BOLD, 25));
        home.setBackground(new Color(184, 134, 11));
        home.setForeground(Color.WHITE);
        home.setFocusPainted(false);
        home.setAlignmentX(Component.CENTER_ALIGNMENT);
        home.setPreferredSize(new Dimension(220, 45));

        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cardLayout.show(loginPanel, "homePage");
            }
        });

        buttonPanel.add(Box.createHorizontalStrut(150));
        buttonPanel.add(home);
        buttonPanel.add(Box.createVerticalStrut(300));


        add(buttonPanel);
    }
}