package ui;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultsPage extends JPanel {
    private iScreenManager screenManager;
    private Image backgroundImage;

    public ResultsPage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        this.backgroundImage = new ImageIcon("images/image2.jpg").getImage();
        setOpaque(false);
        setLayout(new BorderLayout()); // Use a layout to place components properly

        // Title label
        JLabel label = new JLabel(" Your Results:", SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.NORTH);

        // Create result text (you are a balanced investor) and add it to the right
        JPanel resultPanel = new JPanel();
        resultPanel.setOpaque(false);  // Transparent background
        resultPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        resultPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));// Right-aligned

        // White text part
        JLabel resultText = new JLabel("You are a ");
        resultText.setFont(new Font("Arial", Font.BOLD, 34));
        resultText.setForeground(Color.WHITE); // White color
        resultPanel.add(resultText);

        // Gold text part
        JLabel balancedInvestor = new JLabel("balanced investor");
        balancedInvestor.setFont(new Font("Arial", Font.BOLD, 34));
        balancedInvestor.setForeground(Color.YELLOW); // Gold color
        resultPanel.add(balancedInvestor);

        // Add resultPanel to the RIGHT of the layout
        add(resultPanel, BorderLayout.EAST); // Place result text on the right

        // Create pie chart and add it
        PieChartGenerator chartGenerator = new PieChartGenerator();
        ChartPanel pieChartPanel = (ChartPanel) chartGenerator.createPieChart("");

        pieChartPanel.setOpaque(false); // So background shows through
        pieChartPanel.setBackground(new Color(0, 0, 0, 0)); // Transparent background

        JPanel chartContainer = new JPanel();
        chartContainer.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align left
        chartContainer.setOpaque(false); // Make the container transparent
        chartContainer.add(pieChartPanel); // Add the pie chart to the container

        // Add the chart container to the center of the layout
        add(chartContainer, BorderLayout.CENTER); // Center the chart

        // Homepage button
        JButton loginButton = new JButton("HOMEPAGE");
        loginButton.setFont(new Font("Ariel", Font.BOLD, 20));
        loginButton.setBackground(new Color(137, 207, 240));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setPreferredSize(new Dimension(188, 33));
        add(loginButton, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((MainFrame) screenManager).loginPage.returnLogin();
                screenManager.switchTo("Home Page");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
