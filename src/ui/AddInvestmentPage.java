package ui;

import data.StockAPIClient;
import models.InvestmentData;
import models.User;
import models.UserInvestment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class AddInvestmentPage extends JPanel {
    private Image backgroundImage;
    private StockAPIClient apiClient;
    private iScreenManager screenManager;
    private JTable investmentTable;
    private JTextField symbolField, quantityField;

    public AddInvestmentPage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        this.apiClient = new StockAPIClient();
        this.backgroundImage = new ImageIcon("images/image2.jpg").getImage();
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel title = new JLabel("Add Investment (available investments)");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Name", "Type", "Current Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        investmentTable = new JTable(model);
        investmentTable.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(investmentTable);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        populateTable(model);
        add(scrollPane, BorderLayout.CENTER);

        // Input form
        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.setOpaque(false);

        symbolField = new JTextField(10);
        quantityField = new JTextField(5);

        JButton confirmButton = new JButton("Confirm Investment");
        confirmButton.setBackground(new Color(255, 140, 0));       // Button background
        confirmButton.setForeground(Color.BLACK);        // Button text color (optional)
        confirmButton.setFocusPainted(false);            // Optional: removes focus border
        confirmButton.addActionListener(this::handleAddInvestment);

        JLabel symbolLabel = new JLabel("Symbol:");
        symbolLabel.setForeground(Color.WHITE); // Or any other color
        formPanel.add(symbolLabel);
        formPanel.add(symbolField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.WHITE);
        formPanel.add(quantityLabel);
        formPanel.add(quantityField);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(200, 200, 200)); // Light gray or adjust as needed
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.addActionListener(ev -> {
            screenManager.switchTo("Home Page");
        });

        formPanel.add(confirmButton);
        formPanel.add(backButton);

        add(formPanel, BorderLayout.SOUTH);
    }

    private void populateTable(DefaultTableModel model) {
        Map<String, InvestmentData> dataMap = apiClient.getInvestmentMap();
        for (Map.Entry<String, InvestmentData> entry : dataMap.entrySet()) {
            String symbol = entry.getKey();
            InvestmentData data = entry.getValue();
            double price = data.recentPrices != null && !data.recentPrices.isEmpty()
                    ? data.recentPrices.get(data.recentPrices.size() - 1).price
                    : 0.0;
            model.addRow(new Object[]{symbol, data.type, price});
        }
    }

    private void handleAddInvestment(ActionEvent e) {
        String symbol = symbolField.getText().toUpperCase().trim();
        String quantityText = quantityField.getText().trim();

        if (symbol.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both symbol and quantity.");
            return;
        }

        try {
            double quantity = Double.parseDouble(quantityText);
            InvestmentData data = apiClient.getDataFromSymbol(symbol);

            if (data == null) {
                JOptionPane.showMessageDialog(this, "Invalid symbol.");
                return;
            }

            double latestPrice = data.recentPrices.get(data.recentPrices.size() - 1).price;
            User user = User.getInstance();
            UserInvestment newInv = new UserInvestment(symbol, data.type, quantity, latestPrice, "");
            user.addInvestment(newInv);

            user.updateBalance(new StockAPIClient());
            ((MainFrame) screenManager).getHomePage().setup(user);

            JOptionPane.showMessageDialog(this, "Investment added successfully!");
            screenManager.switchTo("Home Page");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unexpected error occurred.");
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
