package ui;

import javax.swing.*;
import java.awt.*;

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
    }
}