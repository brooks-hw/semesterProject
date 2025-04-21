package ui;

import org.jfree.chart.*;
import org.jfree.data.time.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HomePage extends JPanel {
    private iScreenManager screenManager;
    private DefaultTableModel tableModel;

    public HomePage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        setLayout(new BorderLayout());

        add(createTickerPanel(), BorderLayout.NORTH);
        add(createChartPanel(), BorderLayout.CENTER);
        add(createRightPanel(), BorderLayout.EAST);
    }

    private JPanel createTickerPanel() {
        JPanel tickerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tickerPanel.setBackground(Color.BLACK);
        JLabel tickerLabel = new JLabel("AAPL 145.23 ▲   TSLA 234.50 ▼   GOOG 2890.10 ▲");
        tickerLabel.setForeground(Color.GREEN);
        tickerPanel.add(tickerLabel);
        tickerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return tickerPanel;
    }

    private JPanel createChartPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());

        // === 1. User Info Box (Top of chart panel) ===
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        userInfoPanel.setBackground(new Color(245, 245, 245));
        userInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel nameLabel = new JLabel("Welcome, John Doe");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);

        JLabel investmentLabel = new JLabel("Total Invested: $25,430.75");
        investmentLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        investmentLabel.setForeground(Color.WHITE);

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(Box.createHorizontalStrut(30)); // space between labels
        userInfoPanel.add(investmentLabel);

        userInfoPanel.setBackground(new Color(20, 30, 70));

        leftPanel.add(userInfoPanel, BorderLayout.NORTH);

        // === 2. Chart ===
        JFreeChart chart = createDummyChart();
        ChartPanel chartComponent = new ChartPanel(chart);
        leftPanel.add(chartComponent, BorderLayout.CENTER);

        // === 3. Time Buttons + Logout ===
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(0, 50));
        buttonPanel.setBackground(new Color(20, 30, 70));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.BLACK);
        buttonPanel.add(logoutButton, BorderLayout.WEST);

        JPanel timeButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        timeButtons.setBackground(new Color(20, 30, 70));

        String[] ranges = {"1D", "1W", "1M", "1Y", "Max"};
        for (String label : ranges) {
            JButton button = new JButton(label);
            button.setBackground(new Color(255, 140, 0));
            button.setForeground(Color.BLACK);
            timeButtons.add(button);
        }

        buttonPanel.add(timeButtons, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(350, 0));
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Table Column Names
        String[] columnNames = {"NAME", "HOLDING", "PRICE", "GAIN"};
        Object[][] data = {};
        tableModel = new DefaultTableModel(data, columnNames);
        JTable stockTable = new JTable(tableModel);
        stockTable.setRowHeight(30);

        // Table Column Preferences
        stockTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        stockTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        stockTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        stockTable.getColumnModel().getColumn(3).setPreferredWidth(60);

        JScrollPane scrollPane = new JScrollPane(stockTable);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Dummy data
        tableModel.addRow(new Object[]{"AAPL", 10, 145.23, 3.5});
        tableModel.addRow(new Object[]{"TSA", 10, 135.23, 3.5});
        tableModel.addRow(new Object[]{"AMZN", 101, 142.23, 3.5});
        tableModel.addRow(new Object[]{"NVDA", 10, 145.23, 90.5});

        // Buttons
        JPanel userButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        userButtonPanel.setPreferredSize(new Dimension(0, 50));
        userButtonPanel.setBackground(new Color(20, 30, 70));
        userButtonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton addButton = new JButton("Add Stock");
        JButton removeButton = new JButton("Remove Stock");

        addButton.setBackground(new Color(255, 140, 0));
        addButton.setForeground(Color.BLACK);
        removeButton.setBackground(new Color(255, 140, 0));
        removeButton.setForeground(Color.BLACK);

        userButtonPanel.add(addButton);
        userButtonPanel.add(removeButton);

        rightPanel.add(userButtonPanel, BorderLayout.SOUTH);

        return rightPanel;
    }

    private JFreeChart createDummyChart() {
        TimeSeries series = new TimeSeries("Investment Portfolio");
        series.add(new Day(1, 4, 2025), 100);
        series.add(new Day(2, 4, 2025), 102);
        series.add(new Day(3, 4, 2025), 105);
        series.add(new Day(4, 4, 2025), 103);
        series.add(new Day(5, 4, 2025), 110);
        series.add(new Day(6, 4, 2025), 112);
        series.add(new Day(7, 4, 2025), 114);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        return ChartFactory.createTimeSeriesChart("User Portfolio", "Date", "Price", dataset, false, false, false);
    }
}
