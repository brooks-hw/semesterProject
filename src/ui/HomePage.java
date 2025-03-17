package ui;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {
    public HomePage() {
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Investment Buddy!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        add(welcomeLabel, BorderLayout.CENTER);
    }
}
