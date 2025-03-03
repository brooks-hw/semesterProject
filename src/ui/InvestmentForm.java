package ui;

import javax.swing.*;
import java.awt.*;

public class InvestmentForm extends JPanel {
    public InvestmentForm() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Investment Form", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));

        add(label, BorderLayout.CENTER);
    }
}
