package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PortfolioSuggestionPage extends JPanel {
    private Image backgroundImage;
    iScreenManager screenManager;

    public PortfolioSuggestionPage(double investmentAmount, iScreenManager screenManager) {
        this.screenManager = screenManager;
        this.backgroundImage = new ImageIcon("images/image2.jpg").getImage();
        setLayout(new BorderLayout());
        setOpaque(false);

        // Top label
        JLabel headerLabel = new JLabel("Based on your results, we suggest this portfolio:");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Portfolio table
        String[] columnNames = {
                "Name", "Type", "Current Price", "Num Shares", "Percent of Total Invested"
        };

        Object[][] rowData = getSuggestedPortfolio(investmentAmount);

        JTable table = new JTable(new DefaultTableModel(rowData, columnNames));
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setOpaque(false);
        tableScrollPane.getViewport().setOpaque(false);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));
        add(tableScrollPane, BorderLayout.CENTER);

        // Bottom button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20)); // 40px gap
        buttonPanel.setOpaque(false); // Transparent

        JButton useOutlineButton = new JButton("Use This Outline");
        useOutlineButton.setFont(new Font("Arial", Font.BOLD, 16));
        useOutlineButton.setBackground(new Color(137, 207, 240));
        useOutlineButton.setForeground(Color.BLACK);
        useOutlineButton.setFocusPainted(false);
        useOutlineButton.setPreferredSize(new Dimension(180, 40));

        JButton investIndependentlyButton = new JButton("Invest on my Own");
        investIndependentlyButton.setFont(new Font("Arial", Font.BOLD, 16));
        investIndependentlyButton.setBackground(new Color(240, 180, 137));
        investIndependentlyButton.setForeground(Color.BLACK);
        investIndependentlyButton.setFocusPainted(false);
        investIndependentlyButton.setPreferredSize(new Dimension(180, 40));

        buttonPanel.add(useOutlineButton);
        buttonPanel.add(investIndependentlyButton);

        useOutlineButton.addActionListener(e -> {
            // Implement logic to finalize portfolio
            JOptionPane.showMessageDialog(this, "Portfolio outline selected!");
            HomePage homePage = ((MainFrame) screenManager).getHomePage();
            homePage.setUsingTemplate(true);  // or false
            screenManager.switchTo("Home Page");
        });

        investIndependentlyButton.addActionListener(e -> {
            // Go to manual investment screen or show form
            JOptionPane.showMessageDialog(this, "Switching to independent investment mode...");
            HomePage homePage = ((MainFrame) screenManager).getHomePage();
            homePage.setUsingTemplate(false);  // or false
            screenManager.switchTo("Home Page");
        });

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private Object[][] getSuggestedPortfolio(double totalInvestment) {
        // Dummy data: real data would likely come from a backend or calculation
        Object[][] data = {
                {"Apple", "Stock", 175.00, 0, 0},
                {"Tesla", "Stock", 250.00, 0, 0},
                {"US Bonds", "Bond", 100.00, 0, 0},
                {"Bitcoin", "Crypto", 30000.00, 0, 0},
        };

        double[] weights = {0.35, 0.25, 0.20, 0.20}; // 35%, 25%, etc.

        for (int i = 0; i < data.length; i++) {
            double price = (double) data[i][2];
            double allocated = totalInvestment * weights[i];
            int numShares = (int) (allocated / price);
            double percent = weights[i] * 100;

            data[i][3] = numShares;
            data[i][4] = String.format("%.2f%%", percent);
        }

        return data;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
