/*
 * Some portions of this file were generated with the assistance of ChatGPT.
 * we used ChatGPT to help with refinement, debugging, and specific enhancements such as [e.g., chart creation, repaint logic, UI layout, etc.].
 * The original prompt used for assistance was: "Can you help me write a Java Swing panel that show's the user available investments and prompts
 * them to add an investment"
 * we then modified and integrated the generated code to fit the needs of the overall project.
 */

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

// Panel for displaying available investments and allowing user to add new ones to their portfolio
public class AddInvestmentPage extends JPanel {
    private Image backgroundImage;              // Background image
    private StockAPIClient apiClient;           // API client to retrieve investment data
    private iScreenManager screenManager;       // Interface to switch between screens
    private JTable investmentTable;             // Table displaying available investments
    private JTextField symbolField, quantityField; // Input fields for user entry

    // Constructor sets up UI layout, table, and input form
    public AddInvestmentPage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        this.apiClient = new StockAPIClient();
        this.backgroundImage = new ImageIcon(getClass().getResource("/images/image2.jpg")).getImage();

        setLayout(new BorderLayout());
        setOpaque(false);

        // === Title ===
        JLabel title = new JLabel("Add Investment (available investments)");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // === Investment Table ===
        String[] columns = {"Name", "Type", "Current Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        investmentTable = new JTable(model);
        investmentTable.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(investmentTable);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        populateTable(model); // Fill table with investment data
        add(scrollPane, BorderLayout.CENTER);

        // === Form Inputs and Buttons ===
        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.setOpaque(false);

        symbolField = new JTextField(10);
        quantityField = new JTextField(5);

        JButton confirmButton = new JButton("Confirm Investment");
        confirmButton.setBackground(new Color(255, 140, 0));
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(this::handleAddInvestment);

        JLabel symbolLabel = new JLabel("Symbol:");
        symbolLabel.setForeground(Color.WHITE);
        formPanel.add(symbolLabel);
        formPanel.add(symbolField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.WHITE);
        formPanel.add(quantityLabel);
        formPanel.add(quantityField);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(200, 200, 200));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.addActionListener(ev -> screenManager.switchTo("Home Page"));

        formPanel.add(confirmButton);
        formPanel.add(backButton);

        add(formPanel, BorderLayout.SOUTH);
    }

    // Populates the investment table with data from the API
    private void populateTable(DefaultTableModel model) {
        Map<String, InvestmentData> dataMap = apiClient.getInvestmentMap();

        for (Map.Entry<String, InvestmentData> entry : dataMap.entrySet()) {
            String symbol = entry.getKey();
            InvestmentData data = entry.getValue();

            double price = 0.0;
            if (data.recentPrices != null && !data.recentPrices.isEmpty()) {
                price = data.recentPrices.get(data.recentPrices.size() - 1).price;
            }

            model.addRow(new Object[]{symbol, data.type, price});
        }
    }

    // Handles adding an investment to the user's portfolio after validation
    private void handleAddInvestment(ActionEvent e) {
        String symbol = symbolField.getText().toUpperCase().trim();
        String quantityText = quantityField.getText().trim();

        // Input validation
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

            // Create and add new investment
            double latestPrice = data.recentPrices.get(data.recentPrices.size() - 1).price;
            User user = User.getInstance();
            UserInvestment newInv = new UserInvestment(symbol, data.type, quantity, latestPrice, "");
            user.addInvestment(newInv);

            // Update UI and balance
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

    // Paints the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
