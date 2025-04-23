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