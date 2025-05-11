/*
 * Some portions of this file were generated with the assistance of ChatGPT.
 * we used ChatGPT to help with refinement, debugging, and specific enhancements such as [e.g., chart creation, repaint logic, UI layout, etc.].
 * The original prompt used for assistance was: "Can you help me write a Java Swing panel that show's the user their portfolio and prompts
 * them to remove an investment"
 * we then modified and integrated the generated code to fit the needs of the overall project.
 */

package ui;

import models.User;
import models.UserInvestment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

// Panel that displays the user's current portfolio and allows them to remove investments
public class RemoveInvestmentPage extends JPanel {
    private Image backgroundImage;              // Background image
    private iScreenManager screenManager;       // Interface to control screen transitions
    private JTable portfolioTable;              // Table displaying user's portfolio
    private JTextField symbolField, quantityField; // Input fields for removing investment

    // Constructor sets up the layout and components
    public RemoveInvestmentPage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        this.backgroundImage = new ImageIcon(getClass().getResource("/images/image2.jpg")).getImage();
        setLayout(new BorderLayout());
        setOpaque(false);

        // === Title ===
        JLabel title = new JLabel("Remove Investment (your current portfolio)");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // === Portfolio Table ===
        String[] columns = {"Symbol", "Type", "Quantity", "Buy Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        portfolioTable = new JTable(model);
        portfolioTable.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(portfolioTable);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        populateTable(model); // Fill the table with user data
        add(scrollPane, BorderLayout.CENTER);

        // === Form Inputs and Buttons ===
        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.setOpaque(false);

        symbolField = new JTextField(10);
        quantityField = new JTextField(5);

        // Button to remove investment
        JButton confirmButton = new JButton("Remove Investment");
        confirmButton.setBackground(Color.RED);
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(this::handleRemoveInvestment);

        // Button to go back to home page
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(200, 200, 200));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.addActionListener(ev -> screenManager.switchTo("Home Page"));

        // Labels and input fields
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

    // Populates the table with the current user's portfolio
    private void populateTable(DefaultTableModel model) {
        User user = User.getInstance();
        for (UserInvestment inv : user.getPortfolio()) {
            model.addRow(new Object[]{inv.symbol, inv.type, inv.quantity, inv.buyPrice});
        }
    }

    // Logic for when the user presses "Remove Investment"
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

            // On success, update the home page
            JOptionPane.showMessageDialog(this, "Investment removed successfully.");
            ((MainFrame) screenManager).getHomePage().setup(user);
            screenManager.switchTo("Home Page");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.");
        }
    }

    // Draws the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
