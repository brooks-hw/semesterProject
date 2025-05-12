package ui;

/*
ChatGPT assistance in developing UI
helped with formatting spaces and overall layout of buttons and labels
helped with HTML format in descriptionLabel
 */

import org.jfree.chart.ChartPanel;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultsPage extends JPanel {
    private iScreenManager screenManager;
    private Image backgroundImage;

    public ResultsPage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        User user = User.getInstance(); // Get the singleton User
        String riskProfile = user.getRiskProfile(); // Fetch risk profile
        int totalScore = user.getTotalScore();      // Fetch total score
        this.backgroundImage = new ImageIcon(getClass().getResource("/images/image2.jpg")).getImage();
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 40, 5, 40));

        JLabel titleLabel = new JLabel("Your Results:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Small gap

        //"You are a _____ investor" line
        JPanel resultTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        resultTextPanel.setOpaque(false);

        JLabel resultText = new JLabel("You are a ");
        resultText.setFont(new Font("Arial", Font.BOLD, 32));
        resultText.setForeground(Color.WHITE);

        JLabel balancedInvestor = new JLabel(riskProfile);
        balancedInvestor.setFont(new Font("Arial", Font.BOLD, 32));
        balancedInvestor.setForeground(Color.GREEN);

        resultTextPanel.add(resultText);
        resultTextPanel.add(balancedInvestor);
        mainPanel.add(resultTextPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        String descriptionText = "";
        switch (riskProfile) {
            case "Conservative Investor":
                descriptionText = "A conservative investor prioritizes low-risk, steady returns.";
                break;
            case "Balanced Investor":
                descriptionText = "A balanced investor takes moderate risks for balanced growth.";
                break;
            case "Aggressive Investor":
                descriptionText = "An aggressive investor seeks high returns and accepts higher risks.";
                break;
            case "Speculative Investor":
                descriptionText = "A speculative investor embraces very high risks for potentially very high rewards.";
                break;
            default:
                descriptionText = "Investor profile not identified.";
        }

        JLabel descriptionLabel = new JLabel(
                "<html><div style='text-align: center;'>" + descriptionText + "</div></html>"
        );
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        descriptionLabel.setForeground(Color.LIGHT_GRAY);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(descriptionLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Moderate gap

        //Pie Chart
        PieChartGenerator chartGenerator = new PieChartGenerator();
        ChartPanel pieChartPanel = (ChartPanel) chartGenerator.createPieChart("");
        pieChartPanel.setOpaque(false);
        pieChartPanel.setBackground(new Color(0, 0, 0, 0));
        pieChartPanel.setPreferredSize(new Dimension(450, 350)); // Chart size

        JPanel pieChartContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pieChartContainer.setOpaque(false);
        pieChartContainer.add(pieChartPanel);

        mainPanel.add(pieChartContainer);

        add(mainPanel, BorderLayout.CENTER);

        //Bottom button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(0, 60));
        buttonPanel.setOpaque(false); // Transparent background

        //Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 18));
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(logoutButton, BorderLayout.WEST);

        //Homepage button
        JButton homepageButton = new JButton("Homepage");
        homepageButton.setFont(new Font("Arial", Font.BOLD, 18));
        homepageButton.setBackground(new Color(255, 140, 0));
        homepageButton.setForeground(Color.BLACK);
        homepageButton.setFocusPainted(false);
        homepageButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(homepageButton, BorderLayout.EAST);

        JPanel centerButtonPanel = new JPanel();
        centerButtonPanel.setOpaque(false);
        centerButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        JButton retakeButton = new JButton("Retake Questionnaire");
        retakeButton.setFont(new Font("Arial", Font.BOLD, 16));
        retakeButton.setBackground(new Color(137, 207, 240));
        retakeButton.setForeground(Color.BLACK);
        retakeButton.setFocusPainted(false);

        JButton importButton = new JButton("Import Results");
        importButton.setFont(new Font("Arial", Font.BOLD, 16));
        importButton.setBackground(new Color(137, 207, 240));
        importButton.setForeground(Color.BLACK);
        importButton.setFocusPainted(false);


        centerButtonPanel.add(retakeButton);
        centerButtonPanel.add(importButton);

        buttonPanel.add(centerButtonPanel, BorderLayout.CENTER);

        //Add button panel to bottom
        add(buttonPanel, BorderLayout.SOUTH);

        //Action Listeners
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User.logOutInstance();
                ((MainFrame) screenManager).loginPage.returnLogin();
                screenManager.switchTo("Login Page");
            }
        });

        homepageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.switchTo("Home Page");
            }
        });

        retakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.switchTo("Investment Form");
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.switchTo("Investment Amount Page");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
