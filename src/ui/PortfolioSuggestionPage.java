package ui;

import data.PortfolioManager;
import data.StockAPIClient;
import models.User;
import models.UserInvestment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        User user = User.getInstance();
        Object[][] rowData = getSuggestedPortfolio(user, investmentAmount);

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
            user.setInvestmentAmount(investmentAmount);
            user.setUsingTemplate(false);

            List<UserInvestment> adjustedPortfolio = generateScaledPortfolio(user, investmentAmount);
            user.setPortfolio(adjustedPortfolio);

            user.updateBalance(new StockAPIClient()); // ✅ THIS IS CRITICAL

            HomePage homePage = ((MainFrame) screenManager).getHomePage();
            homePage.setup(user);
            screenManager.switchTo("Home Page");
        });

        investIndependentlyButton.addActionListener(e -> {
            // Go to manual investment screen or show form
            JOptionPane.showMessageDialog(this, "Switching to independent investment mode...");
            HomePage homePage = ((MainFrame) screenManager).getHomePage();
            user.setUsingTemplate(false);  // or false
            homePage.setup(user);
            screenManager.switchTo("Home Page");
        });

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private List<UserInvestment> generateScaledPortfolio(User user, double totalInvestment) {
        List<UserInvestment> template = switch (user.getRiskProfile()) {
            case "Conservative Investor" -> PortfolioManager.getConservativeTemplate();
            case "Balanced Investor" -> PortfolioManager.getBalancedTemplate();
            case "Aggressive Investor" -> PortfolioManager.getAggressiveTemplate();
            case "Speculative Investor" -> PortfolioManager.getSpeculativeTemplate();
            default -> List.of();
        };

        double portion = 1.0 / template.size(); // Even split
        List<UserInvestment> scaled = new ArrayList<>();

        for (UserInvestment inv : template) {
            double allocated = totalInvestment * portion;
            double shares = allocated / inv.buyPrice;

            // Create new investment with scaled quantity
            UserInvestment adjusted = new UserInvestment(inv.symbol, inv.type, shares, inv.buyPrice, "");
            scaled.add(adjusted);
        }

        return scaled;
    }

    private Object[][] getSuggestedPortfolio(User user, double totalInvestment) {
        List<UserInvestment> template = switch (user.getRiskProfile()) {
            case "Conservative Investor" -> PortfolioManager.getConservativeTemplate();
            case "Balanced Investor" -> PortfolioManager.getBalancedTemplate();
            case "Aggressive Investor" -> PortfolioManager.getAggressiveTemplate();
            case "Speculative Investor" -> PortfolioManager.getSpeculativeTemplate();
            default -> List.of(); // fallback
        };

        Object[][] data = new Object[template.size()][5];

        double portion = 1.0 / template.size(); // Even split

        for (int i = 0; i < template.size(); i++) {
            UserInvestment inv = template.get(i);
            double price = inv.buyPrice;
            double allocated = totalInvestment * portion;
            double shares = allocated / price;

            data[i][0] = inv.symbol;
            data[i][1] = inv.type;
            data[i][2] = price;
            data[i][3] = String.format("%.4f", shares);
            data[i][4] = String.format("%.2f%%", portion * 100);
        }

        return data;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
