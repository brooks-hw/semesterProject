package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvestmentAmountPage extends JPanel {
    private iScreenManager screenManager;
    private Image backgroundImage;

    public InvestmentAmountPage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        this.backgroundImage = new ImageIcon("images/image2.jpg").getImage();
        setOpaque(false);
        setLayout(new BorderLayout());

        // Wrapper to help center content vertically
        JPanel verticalWrapper = new JPanel();
        verticalWrapper.setOpaque(false);
        verticalWrapper.setLayout(new BoxLayout(verticalWrapper, BoxLayout.Y_AXIS));
        verticalWrapper.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        verticalWrapper.add(Box.createVerticalGlue()); // Push content to vertical center

        // Title
        JLabel title = new JLabel("How much are you planning on investing?");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Text field
        JTextField amountField = new JTextField(15);
        amountField.setMaximumSize(new Dimension(300, 40));
        amountField.setFont(new Font("Arial", Font.PLAIN, 20));
        amountField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button
        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        verticalWrapper.add(title);
        verticalWrapper.add(Box.createRigidArea(new Dimension(0, 20)));
        verticalWrapper.add(amountField);
        verticalWrapper.add(Box.createRigidArea(new Dimension(0, 20)));
        verticalWrapper.add(nextButton);

        verticalWrapper.add(Box.createVerticalGlue()); // Push content to vertical center

        add(verticalWrapper, BorderLayout.CENTER);

        // Action listener
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    screenManager.switchToPortfolioPage(amount);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                }
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
