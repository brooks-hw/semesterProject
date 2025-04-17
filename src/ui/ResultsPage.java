package ui;

import javax.swing.*;
import java.awt.*;

public class ResultsPage extends JPanel {
    public ResultsPage() {
        setOpaque(false);
        JLabel label = new JLabel("Your Results Go Here", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        add(label);
    }
}
