//NOTE: FORMATTED & ELEMENTS CREATED USING AI: CHATGPT
package ui;

import javax.swing.*;
import java.awt.*;

class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage(); // Load image
        setLayout(null); // Allow manual positioning if needed
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Scale image to fit panel
    }
}
