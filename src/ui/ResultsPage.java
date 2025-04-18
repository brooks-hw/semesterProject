package ui;

import javax.swing.*;
import java.awt.*;

public class ResultsPage extends JPanel {
    private iScreenManager screenManager;
    private Image backgroundImage;
    public ResultsPage(iScreenManager screenManager) {
        this.backgroundImage = new ImageIcon("images/image2.jpg").getImage();
        setOpaque(false);
        JLabel label = new JLabel("Your Results Go Here", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        add(label);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
