package ui;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;

public class ResultsPage extends JPanel {
    private iScreenManager screenManager;
    private Image backgroundImage;

    public ResultsPage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        this.backgroundImage = new ImageIcon("images/image2.jpg").getImage();
        setOpaque(false);
        setLayout(new BorderLayout()); // Use a layout to place components properly

        // Title label
        JLabel label = new JLabel("Your Results", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.NORTH);

        // Create pie chart and add it
        PieChartGenerator chartGenerator = new PieChartGenerator();
        ChartPanel pieChartPanel = (ChartPanel) chartGenerator.createPieChart("Investment Breakdown");
        pieChartPanel.setOpaque(false); // So background shows through
        pieChartPanel.setBackground(new Color(0, 0, 0, 0)); // Transparent background

        add(pieChartPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}