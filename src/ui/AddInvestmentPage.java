package ui;

import javax.swing.*;
import java.awt.*;

public class AddInvestmentPage extends JPanel {
    private final iScreenManager screenManager;

    private final JTextField symbolField = new JTextField(16);
    private final JTextField sharesField = new JTextField(16);
    private final JLabel messageLabel = new JLabel();

    public AddInvestmentPage(iScreenManager screenManager) {
        this.screenManager = screenManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(new Color(20, 30, 70));

        // Title
        JLabel title = new JLabel("Add Investment");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Book Antiqua", Font.BOLD, 42));
        title.setForeground(Color.WHITE);
        add(Box.createVerticalStrut(40));
        add(title);
        add(Box.createVerticalStrut(30));

        // Input fields
        add(createField("Stock Symbol:", symbolField));
        add(Box.createVerticalStrut(15));
        add(createField("How many shares?:", sharesField));
        add(Box.createVerticalStrut(15));

        // Message label
        messageLabel.setForeground(Color.GREEN);
        messageLabel.setVisible(false);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(messageLabel);

        // Submit button
        add(Box.createVerticalStrut(30));
        add(createButton("Enter", this::handleEnter));
        add(Box.createVerticalStrut(50));
    }

    private JPanel createField(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(14, 42, 83));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Calibri", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        field.setMaximumSize(new Dimension(300, 30));

        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(137, 207, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(200, 40));
        button.addActionListener(e -> action.run());
        return button;
    }

    private void handleEnter() {
        String symbol = symbolField.getText().trim().toUpperCase();
        String sharesText = sharesField.getText().trim();

        if (symbol.isEmpty() || sharesText.isEmpty()) {
            showMessage("Please fill in both fields.", Color.RED);
            return;
        }

        try {
            double shares = Double.parseDouble(sharesText);
            if (shares <= 0) throw new NumberFormatException();

            // TODO: Hook into portfolio saving system
            System.out.println("Adding investment: " + symbol + " x" + shares);
            showMessage("Investment added successfully!", Color.GREEN);
            clearFields();
            screenManager.switchTo("Home Page");
        } catch (NumberFormatException e) {
            showMessage("Invalid number of shares.", Color.RED);
        }
    }

    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setForeground(color);
        messageLabel.setVisible(true);
    }

    private void clearFields() {
        symbolField.setText("");
        sharesField.setText("");
        messageLabel.setVisible(false);
    }
}
