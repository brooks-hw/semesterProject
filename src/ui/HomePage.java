/*
 * Some portions of this file were generated with the assistance of ChatGPT.
 * we used ChatGPT to help with refinement, debugging, and specific enhancements such as [e.g., repainting, user authentication, and graph display].
 * The original prompt used for assistance was complex for this one and tweaked significantly to add specific features
 * We modified and integrated the generated code to fit the needs of the overall project.
 */

package ui;

import data.PortfolioManager;
import data.StockAPIClient;
import models.InvestmentData;
import models.PriceEntry;
import models.User;
import models.UserInvestment;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class HomePage extends JPanel {

    // Core components of the HomePage
    private User user;                           // The currently authenticated user
    private iScreenManager screenManager;        // Interface for switching between screens
    private DefaultTableModel tableModel;        // Table model for portfolio display
    private StockAPIClient APIClient;            // Stock API client for retrieving price data
    private TickerPanel tickerPanel;             // Scrolling ticker component

    private ChartPanel chartComponent;           // Panel to display JFreeChart
    private JFreeChart currentChart;             // Currently displayed chart

    // Constructor for pre-authentication state; only stores reference to screenManager
    public HomePage(iScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    // Called after login to build and display the full HomePage UI with user data
    public void setup(User user) {
        this.user = user;
        this.APIClient = new StockAPIClient();

        // If user is using a portfolio template and has no investments yet, load it
        if (user.getPortfolio().isEmpty() && user.getUsingTemplate()) {
            PortfolioManager.loadFromTemplate(user);
        }

        // Update user's investment balance from the latest prices
        user.updateBalance(new StockAPIClient());

        // Clean up any previous UI components (especially important if revisiting HomePage)
        removeAll();
        revalidate();
        repaint();

        // Set up main layout and add key panels
        setLayout(new BorderLayout());
        tickerPanel = new TickerPanel(APIClient);
        add(tickerPanel, BorderLayout.NORTH);
        add(createChartPanel(user), BorderLayout.CENTER);
        add(createRightPanel(), BorderLayout.EAST);

    }

    // Creates the left panel containing user info, chart, and control buttons
    private JPanel createChartPanel(User user) {
        JPanel leftPanel = new JPanel(new BorderLayout());

        // === 1. User Info Banner ===
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        userInfoPanel.setBackground(new Color(20, 30, 70));
        userInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel nameLabel = new JLabel("Welcome, " + user.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        nameLabel.setForeground(Color.WHITE);

        JLabel investmentLabel = new JLabel("Total Invested: $" + user.getBalance());
        investmentLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        investmentLabel.setForeground(Color.WHITE);

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(Box.createHorizontalStrut(30));
        userInfoPanel.add(investmentLabel);

        leftPanel.add(userInfoPanel, BorderLayout.NORTH);

        // === 2. Portfolio Value Chart ===
        currentChart = createPortfolioChart("1W"); // Default time range
        chartComponent = new ChartPanel(currentChart);
        leftPanel.add(chartComponent, BorderLayout.CENTER);

        // === 3. Time Range Buttons & Logout ===
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(0, 50));
        buttonPanel.setBackground(new Color(20, 30, 70));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Logout button logic
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.BLACK);
        buttonPanel.add(logoutButton, BorderLayout.WEST);

        logoutButton.addActionListener(e -> {
            PortfolioManager.saveToFile(user);
            User.logOutInstance();
            ((MainFrame) screenManager).loginPage.returnLogin();
            screenManager.switchTo("Login Page");
        });

        // Time range buttons for switching chart data
        JPanel timeButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        timeButtons.setBackground(new Color(20, 30, 70));

        String[] ranges = {"1D", "1W", "1M", "1Y"};
        for (String label : ranges) {
            JButton button = new JButton(label);
            button.setBackground(new Color(255, 140, 0));
            button.setForeground(Color.BLACK);
            timeButtons.add(button);

            button.addActionListener(e -> {
                JFreeChart newChart = createPortfolioChart(label);
                chartComponent.setChart(newChart);
            });
        }

        buttonPanel.add(timeButtons, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        return leftPanel;
    }

    // Creates the right panel with a portfolio table and navigation buttons
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(350, 0));
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // === Portfolio Table ===
        String[] columnNames = {"NAME", "TYPE", "HOLDING", "PRICE", "GAIN"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent editing
            }
        };
        JTable stockTable = new JTable(tableModel);
        stockTable.setRowHeight(30);

        // Set column widths
        stockTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        stockTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        stockTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        stockTable.getColumnModel().getColumn(3).setPreferredWidth(60);

        // Add data to table from user portfolio
        JScrollPane scrollPane = new JScrollPane(stockTable);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        for (UserInvestment inv : user.getPortfolio()) {
            String symbol = inv.symbol;
            String type = inv.type;
            double quantity = inv.quantity;
            double buyPrice = inv.buyPrice;

            InvestmentData data = APIClient.getInvestmentMap().get(symbol);
            double latestPrice = buyPrice;
            if (data != null && data.recentPrices != null && !data.recentPrices.isEmpty()) {
                PriceEntry lastEntry = data.recentPrices.get(data.recentPrices.size() - 1);
                latestPrice = lastEntry.price;
            }

            double gain = ((latestPrice - buyPrice) / buyPrice) * 100.0;

            // Format data
            String displayQty = type.equalsIgnoreCase("Crypto")
                    ? String.format("%.8f", quantity)
                    : String.format("%.2f", quantity);
            String displayPrice = String.format("%.2f", latestPrice);
            String displayGain = String.format("%.2f", gain);

            tableModel.addRow(new Object[]{symbol, type, displayQty, displayPrice, displayGain});
        }

        // === Navigation Buttons ===
        JPanel userButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        userButtonPanel.setPreferredSize(new Dimension(0, 50));
        userButtonPanel.setBackground(new Color(20, 30, 70));
        userButtonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton addButton = new JButton("Add Stock");
        JButton removeButton = new JButton("Remove Stock");
        JButton resultsButton = new JButton("Risk Profile");

        addButton.setBackground(new Color(255, 140, 0));
        removeButton.setBackground(new Color(255, 140, 0));
        resultsButton.setBackground(new Color(144, 238, 144));

        addButton.setForeground(Color.BLACK);
        removeButton.setForeground(Color.BLACK);
        resultsButton.setForeground(Color.BLACK);

        addButton.addActionListener(e -> screenManager.switchTo("Add Investment"));
        removeButton.addActionListener(e -> ((MainFrame) screenManager).showRemoveInvestmentPage());
        resultsButton.addActionListener(e -> screenManager.switchTo("Results Page"));

        userButtonPanel.add(addButton);
        userButtonPanel.add(removeButton);
        userButtonPanel.add(resultsButton);

        rightPanel.add(userButtonPanel, BorderLayout.SOUTH);

        return rightPanel;
    }

    // Optional: allows setting user manually if needed
    public void setUser(User user) {
        this.user = user;
    }

    // Creates and returns a JFreeChart based on a time range selection
    private JFreeChart createPortfolioChart(String range) {
        TimeSeries series = new TimeSeries("Total Portfolio Value");
        Map<String, InvestmentData> dataMap = APIClient.getInvestmentMap();

        if (range.equals("1D")) {
            // Hourly portfolio values for current day
            Map<Integer, Double> hourToValue = new TreeMap<>();

            for (UserInvestment inv : user.getPortfolio()) {
                InvestmentData data = dataMap.get(inv.symbol);
                if (data == null || data.todayPrices == null) continue;

                for (PriceEntry entry : data.todayPrices) {
                    try {
                        int hour = Integer.parseInt(entry.timestamp.substring(0, 2));
                        double value = inv.quantity * entry.price;
                        hourToValue.put(hour, hourToValue.getOrDefault(hour, 0.0) + value);
                    } catch (Exception e) {
                        System.err.println("Invalid timestamp: " + entry.timestamp);
                    }
                }
            }

            for (Map.Entry<Integer, Double> entry : hourToValue.entrySet()) {
                series.add(new Hour(entry.getKey(), new Day()), entry.getValue());
            }

            return ChartFactory.createTimeSeriesChart(
                    "Portfolio Value (1D)", "Hour", "USD",
                    new TimeSeriesCollection(series),
                    false, false, false
            );
        }

        // Date-based chart for 1W, 1M, 1Y
        Map<LocalDate, Double> dateToValue = new TreeMap<>();

        for (UserInvestment inv : user.getPortfolio()) {
            InvestmentData data = dataMap.get(inv.symbol);
            if (data == null) continue;

            List<PriceEntry> prices = switch (range) {
                case "1W" -> data.recentPrices.subList(Math.max(0, data.recentPrices.size() - 7), data.recentPrices.size());
                case "1M" -> data.recentPrices;
                case "1Y" -> data.historicalPrices;
                default -> List.of();
            };

            for (PriceEntry entry : prices) {
                try {
                    LocalDate date = LocalDate.parse(entry.timestamp);
                    double value = inv.quantity * entry.price;
                    dateToValue.put(date, dateToValue.getOrDefault(date, 0.0) + value);
                } catch (Exception e) {
                    System.err.println("Invalid date format: " + entry.timestamp);
                }
            }
        }

        for (Map.Entry<LocalDate, Double> entry : dateToValue.entrySet()) {
            LocalDate date = entry.getKey();
            series.add(new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()), entry.getValue());
        }

        return ChartFactory.createTimeSeriesChart(
                "Portfolio Value (" + range + ")", "Date", "USD",
                new TimeSeriesCollection(series),
                false, false, false
        );
    }
}
