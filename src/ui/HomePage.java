package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class HomePage extends JPanel {
    public HomePage() {
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Investment Buddy!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(welcomeLabel, BorderLayout.NORTH);

        List<Double> fakeData = getHourlyPrices();
        LineChartGenerator chartGen = new LineChartGenerator();
        JPanel chartPanel = chartGen.createLineChart(fakeData);

        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setPreferredSize(new Dimension(800, 200));
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        contentPanel.add(chartContainer, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
    }
    private List<Double> getHourlyPrices() {
        List<Double> prices = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            prices.add(100 + Math.random() * 10);
        }
        return prices;
    }
}
