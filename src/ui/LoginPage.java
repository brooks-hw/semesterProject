package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginPage extends JPanel{
    private CardLayout cardLayout;
    private JPanel loginPanel;
    private ArrayList<String> usernames;
    private ArrayList<String> passwords;

    public LoginPage() {
        // for questionarre
        // Add InvestmentForm with multiple questions
        java.util.List<String> questions = Arrays.asList(
                "How would you react if your investments dropped by 20%?",
                "How long are you willing to invest for?",
                "How do you feel about high-risk investments?",
                "What is your main investment goal?",
                "How much experience do you have with investing?",
                "What is your tolerance for losing money?",
                "What type of investments do you prefer?",
                "How do you feel about new & unfamiliar industries?",
                "How involved do you want to be in managing investments?",
                "What type of investor do you consider yourself to be?"
        );

        List<String[]> optionsList = Arrays.asList(
                new String[]{"Sell all my investments", "Sell some investments", "Hold onto my investments", "Buy more investments"},
                new String[]{"Less than a year", "1-5 years", "5-10 years", "More than 10 years"},
                new String[]{"Avoid them completely", "Take small risks", "Comfortable with high risks", "Seek high returns"},
                new String[]{"Wealth accumulation", "Retirement savings", "Short-term profit", "Steady income"},
                new String[]{"None, I am a beginner", "A little, but I'm still learning", "Moderate experience", "Extensive experience"},
                new String[]{"I can't afford to lose any money", "I can tolerate minor losses", "I can handle moderate losses", "I'm comfortable losing money"},
                new String[]{"Safe investments like bonds", "Balanced, some stocks & bonds", "Mostly stocks, with some risk", "High-growth assets like crypto"},
                new String[]{"Prefer known industries", "Willing to consider", "Open to experimenting", "Enjoy taking risks"},
                new String[]{"I want no involvement", "I monitor my investments", "I actively manage it", "I want to make all decisions"},
                new String[]{"A conservative investor", "A balanced investor", "An aggressive investor", "A speculative investor"}
        );

        InvestmentForm investmentForm = new InvestmentForm(questions, optionsList);

        setLayout(new BorderLayout());
        setOpaque(false);

        cardLayout = new CardLayout();
        loginPanel = new JPanel(cardLayout);
        loginPanel.setOpaque(false);

        add(loginPanel, BorderLayout.CENTER);


        loginPanel.add(loginPage(), "login");
        loginPanel.add(promptPage(), "prompt");
        loginPanel.add(createPage(), "create");
        loginPanel.add(investmentForm, "InvestmentForm");
    }

    private JPanel loginPage() {
        JPanel login = new JPanel();
        login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
        login.setBackground(new Color(14, 42, 83));

        //login label
        JLabel loginLabel = new JLabel("Login", JLabel.CENTER);
        loginLabel.setFont(new Font("Calibri", Font.BOLD, 80));
        loginLabel.setForeground(Color.WHITE);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setOpaque(false);
        labelPanel.add(loginLabel);

        login.add(Box.createVerticalStrut(50));
        login.add(labelPanel);


        //text box to enter username and password
        JPanel userTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userTextPanel.setOpaque(false);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        userLabel.setForeground(Color.WHITE);
        JTextField username = new JTextField(16);
        username.setPreferredSize(new Dimension(20, 30));

        userTextPanel.add(userLabel);
        userTextPanel.add(username);
        login.add(userTextPanel);


        JPanel passTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passTextPanel.setOpaque(false);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        passLabel.setForeground(Color.WHITE);
        JTextField password = new JTextField(16);
        password.setPreferredSize(new Dimension(20, 30));

        passTextPanel.add(passLabel);
        passTextPanel.add(password);
        login.add(passTextPanel);

        //buttons to either log in or create an account
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Calibri", Font.BOLD, 28));
        loginButton.setBackground(new Color(137, 207, 240));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setPreferredSize(new Dimension(200, 48));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(loginPanel, "prompt");
            }
        });

        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setLayout(new BoxLayout(buttonWrapper, BoxLayout.Y_AXIS));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(loginButton);

        buttonPanel.add(buttonWrapper);

        JButton createAccount = new JButton("Create an Account");
        createAccount.setFont(new Font("Calibri", Font.BOLD, 20));
        createAccount.setBackground(new Color(137, 207, 240));
        createAccount.setForeground(Color.WHITE);
        createAccount.setFocusPainted(false);
        createAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccount.setPreferredSize(new Dimension(200, 45));

        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(loginPanel, "create");
            }
        });

        buttonWrapper.add(Box.createVerticalStrut(30));
        buttonWrapper.add(createAccount);
        buttonPanel.add(Box.createVerticalStrut(45));

        login.add(buttonPanel);
        return login;
    }

    private JPanel createPage() {
        JPanel create = new JPanel();
        create.setLayout(new BoxLayout(create, BoxLayout.Y_AXIS));
        create.setBackground(new Color(14, 42, 83));

        //create account label
        JLabel loginLabel = new JLabel("Create your Account:", JLabel.CENTER);
        loginLabel.setFont(new Font("Calibri", Font.BOLD, 50));
        loginLabel.setForeground(Color.WHITE);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setOpaque(false);
        labelPanel.add(loginLabel);

        create.add(Box.createVerticalStrut(70));
        create.add(labelPanel);



        //text box to enter username and password
        JPanel userTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userTextPanel.setOpaque(false);

        JLabel userLabel = new JLabel("New username:");
        userLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        userLabel.setForeground(Color.WHITE);
        JTextField username = new JTextField(16);
        username.setPreferredSize(new Dimension(20, 30));

        userTextPanel.add(userLabel);
        userTextPanel.add(username);
        create.add(userTextPanel);


        JPanel passTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passTextPanel.setOpaque(false);

        JLabel passLabel = new JLabel("New password:");
        passLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        passLabel.setForeground(Color.WHITE);
        JTextField password = new JTextField(16);
        password.setPreferredSize(new Dimension(20, 30));

        passTextPanel.add(passLabel);
        passTextPanel.add(password);
        create.add(passTextPanel);

        //button to create account
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JButton createButton = new JButton("SUBMIT");
        createButton.setFont(new Font("Calibri", Font.BOLD, 30));
        createButton.setBackground(new Color(137, 207, 240));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.setPreferredSize(new Dimension(200, 48));

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
//                usernames.add(username.getText());
//                passwords.add(password.getText());
                cardLayout.show(loginPanel, "prompt");
            }
        });

        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(createButton);

        buttonPanel.add(buttonWrapper);
        buttonPanel.add(Box.createVerticalStrut(20));

        //create.add(username);
        //create.add(password);
        create.add(buttonPanel);
        return create;
    }

    private JPanel promptPage() {
        JPanel prompt = new JPanel();
        prompt.setLayout(new BoxLayout(prompt, BoxLayout.Y_AXIS));
        prompt.setBackground(new Color(14, 42, 83));

        //question label
        JLabel promptLabel = new JLabel("<html><div style='text-align: center;'>Would you like to complete the questionaire or go to your homepage?<html>", JLabel.CENTER);
        promptLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        promptLabel.setForeground(Color.WHITE);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        promptLabel.setPreferredSize(new Dimension(800, 400));
        labelPanel.setOpaque(false);
        labelPanel.add(promptLabel);

        prompt.add(labelPanel);

        //button options
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);

        JButton question = new JButton("Questionaire");
        question.setFont(new Font("Calibri", Font.BOLD, 30));
        question.setBackground(new Color(137, 207, 240));
        question.setForeground(Color.WHITE);
        question.setFocusPainted(false);
        question.setAlignmentX(Component.CENTER_ALIGNMENT);
        question.setPreferredSize(new Dimension(300, 50));

        question.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(loginPanel, "InvestmentForm");
            }
        });

//        JPanel buttonWrapper = new JPanel();
//        buttonWrapper.setLayout(new BoxLayout(buttonWrapper, BoxLayout.X_AXIS));
//        buttonWrapper.setOpaque(false);
        buttonPanel.add(Box.createVerticalStrut(150));
        buttonPanel.add(question);
        //buttonPanel.add(Box.createHorizontalStrut(20));
        //buttonPanel.add(buttonWrapper);
//
//        buttonPanel.add(Box.createHorizontalStrut(100));

        JButton home = new JButton("Homepage");
        home.setFont(new Font("Calibri", Font.BOLD, 30));
        home.setBackground(new Color(137, 207, 240));
        home.setForeground(Color.WHITE);
        home.setFocusPainted(false);
        home.setAlignmentX(Component.CENTER_ALIGNMENT);
        home.setPreferredSize(new Dimension(300, 50));

        //buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //buttonWrapper.setOpaque(false);
        buttonPanel.add(Box.createHorizontalStrut(150));
        buttonPanel.add(home);
        buttonPanel.add(Box.createVerticalStrut(300));

        //buttonPanel.add(buttonWrapper);

        prompt.add(buttonPanel);

        return prompt;
    }
}

