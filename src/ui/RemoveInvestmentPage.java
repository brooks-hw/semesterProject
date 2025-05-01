package ui;

import models.User;
import models.UserInvestment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RemoveInvestmentPage extends JPanel {
    private Image backgroundImage;
    private iScreenManager screenManager;
    private JTable portfolioTable;
    private JTextField symbolField, quantityField;

    public RemoveInvestmentPage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        this.backgroundImage = new ImageIcon("images/image2.jpg").getImage();
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel title = new JLabel("Remove Investment (your current portfolio)");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Symbol", "Type", "Quantity", "Buy Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        portfolioTable = new JTable(model);
        portfolioTable.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(portfolioTable);
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



        JButton confirmButton = new JButton("Remove Investment");
        confirmButton.setBackground(Color.RED);
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(this::handleRemoveInvestment);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(200, 200, 200)); // Light gray or tweak to match your theme
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.addActionListener(ev -> screenManager.switchTo("Home Page"));

        JLabel symbolLabel = new JLabel("Symbol:");
        symbolLabel.setForeground(Color.WHITE);
        formPanel.add(symbolLabel);
        formPanel.add(symbolField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.WHITE);
        formPanel.add(quantityLabel);
        formPanel.add(quantityField);

        formPanel.add(confirmButton);
        formPanel.add(backButton);

        add(formPanel, BorderLayout.SOUTH);
    }

    private void populateTable(DefaultTableModel model) {
        User user = User.getInstance();
        for (UserInvestment inv : user.getPortfolio()) {
            model.addRow(new Object[]{inv.symbol, inv.type, inv.quantity, inv.buyPrice});
        }
    }

    private void handleRemoveInvestment(ActionEvent e) {
        String symbol = symbolField.getText().toUpperCase().trim();
        String quantityText = quantityField.getText().trim();

        if (symbol.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both symbol and quantity.");
            return;
        }

        try {
            double quantity = Double.parseDouble(quantityText);
            User user = User.getInstance();

            boolean success = user.removeInvestment(symbol, quantity);
            if (!success) {
                JOptionPane.showMessageDialog(this, "Investment not found or insufficient quantity.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Investment removed successfully.");
            ((MainFrame) screenManager).getHomePage().setup(user);
            screenManager.switchTo("Home Page");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
