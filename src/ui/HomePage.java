package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class HomePage extends JPanel {
    private iScreenManager screenManager;
    private Image backgroundImage = new ImageIcon("src/ui/image5.jpg").getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public HomePage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        // TOP BAR
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(20, 30, 70)); // Dark blue

        // Welcome text (LEFT)
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false); // Keep background see-through
        JLabel welcomeLabel = new JLabel("Welcome, John Doe");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        leftPanel.add(welcomeLabel);
        topBar.add(leftPanel, BorderLayout.WEST);

        // Ticker (RIGHT)
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        JLabel tickerLabel = new JLabel("AAPL 172.45 ▲ 1.4%   |   AMZN 128.32 ▼ 0.6%");
        tickerLabel.setForeground(Color.GREEN);
        tickerLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        rightPanel.add(tickerLabel);
        topBar.add(rightPanel, BorderLayout.EAST);

        // Add bar to top
        add(topBar, BorderLayout.NORTH);

        // CENTER PANEL — Chart
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // LEFT: Chart panel
        List<Double> fakeData = getHourlyPrices();
        LineChartGenerator chartGen = new LineChartGenerator();
        JPanel chartPanel = chartGen.createLineChart(fakeData);

        // Time filter buttons
        JPanel timeFilterPanel = new JPanel();
        timeFilterPanel.setBackground(Color.WHITE); // Match chart background

        String[] filters = {"1D", "1W", "1M", "1Y", "5Y", "MAX"};

        for (String label : filters) {
            JButton button = new JButton(label);
            button.setFocusPainted(false);
            button.setBackground(new Color(255, 140, 0)); // Orange
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setPreferredSize(new Dimension(60, 30));
            timeFilterPanel.add(button);
        }


        // Create portfolio value box
        JLabel valueLabel = new JLabel("$35,435.39", SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 22));
        valueLabel.setForeground(Color.BLACK);
        valueLabel.setBackground(Color.WHITE);
        valueLabel.setOpaque(true);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Wrap chart and value label vertically
        JPanel chartContainer = new JPanel();
        chartContainer.setLayout(new BorderLayout());
        chartContainer.setPreferredSize(new Dimension(600, 400));
        chartContainer.add(valueLabel, BorderLayout.NORTH);
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        chartContainer.add(timeFilterPanel, BorderLayout.SOUTH);
        chartContainer.setPreferredSize(new Dimension(600, 400));  // You can adjust size later

        // RIGHT: Placeholder for stock list
        // Column headers
        String[] columnNames = {"Name", "Position", "Price", "Gain", "Rating"};

        // Sample data
        Object[][] stockData = {
                {"AAPL", "15 shares", "$172.45", "+1.4%", "Strong Buy"},
                {"AMZN", "10 shares", "$128.32", "-0.6%", "Hold"},
                {"MSFT", "8 shares", "$312.20", "+0.9%", "Buy"},
        };

        // Table model
        DefaultTableModel model = new DefaultTableModel(stockData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Table
        JTable stockTable = new JTable(model);
        stockTable.setDefaultRenderer(Object.class, new StockTableCellRenderer());
        stockTable.setRowHeight(28);
        stockTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        stockTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        stockTable.setFillsViewportHeight(true);

        // Put table in scroll pane
        JScrollPane scrollPane = new JScrollPane(stockTable);

        // Panel for the table
        JPanel stockPanel = new JPanel(new BorderLayout());
        stockPanel.setPreferredSize(new Dimension(300, 400));
        stockPanel.add(scrollPane, BorderLayout.CENTER);

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chartContainer, stockPanel);
        splitPane.setResizeWeight(0.7); // 70% chart, 30% stock list
        splitPane.setDividerSize(5);

        add(splitPane, BorderLayout.CENTER);

        // BOTTOM BUTTON BAR
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Color.LIGHT_GRAY);
        bottomBar.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));  // spacing between buttons

        // LOGOUT Button
        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(100, 35));
        bottomBar.add(logoutButton);

        // Add Investment Button
        JButton addButton = new JButton("Add Investment");
        addButton.setBackground(new Color(255, 140, 0));  // Orange
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(150, 35));
        bottomBar.add(addButton);

        // Remove Investment Button
        JButton removeButton = new JButton("Remove Investment");
        removeButton.setBackground(new Color(255, 140, 0));  // Orange
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.setPreferredSize(new Dimension(170, 35));
        bottomBar.add(removeButton);

        // Add the bottom bar to the screen
        add(bottomBar, BorderLayout.SOUTH);

        // --- Button Actions ---
        logoutButton.addActionListener(e -> {
            screenManager.switchTo("Login Page");
        });

        addButton.addActionListener(e -> {
            screenManager.switchTo("Investment Form");
        });

        removeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Remove Investment feature coming soon!");
        });






    }

    private List<Double> getHourlyPrices() {
        List<Double> prices = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            prices.add(100 + Math.random() * 10);
        }
        return prices;
    }


    private static class StockTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Reset colors
            c.setForeground(Color.BLACK);

            // Gain/Loss column
            if (column == 3 && value instanceof String) {
                String val = (String) value;
                if (val.contains("+")) {
                    c.setForeground(new Color(0, 128, 0)); // Green
                } else if (val.contains("-")) {
                    c.setForeground(Color.RED); // Red
                }
            }

            // Rating column
            if (column == 4 && value instanceof String) {
                String val = (String) value;
                if (val.equalsIgnoreCase("Strong Buy")) {
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                    c.setForeground(new Color(0, 102, 255)); // Blue
                } else if (val.equalsIgnoreCase("Hold")) {
                    c.setForeground(new Color(150, 150, 0)); // Yellow-ish
                }
            }

            return c;
        }
    }

}