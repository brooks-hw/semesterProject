package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginPage extends JPanel{
    private CardLayout cardLayout;
    private JPanel loginPanel;
    public ArrayList<String> usernames;
    public ArrayList<String> passwords;
    private iScreenManager screenManager;

    public LoginPage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        // for questionnaire
        // Add InvestmentForm with multiple questions

        InvestmentForm investmentForm = new InvestmentForm(screenManager);
        HomePage homePage = new HomePage();

        usernames = new ArrayList<>();
        passwords = new ArrayList<>();

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
        loginPanel.add(homePage, "homePage");
        loginPanel.add(login2Page(), "login2");
        loginPanel.add(create2Page(), "create2");
    }

    private JPanel loginPage() {
        JPanel login = new JPanel();
        login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
        login.setBackground(new Color(14, 42, 83));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setOpaque(false);
        labelPanel.setBackground(Color.WHITE);
        //labelPanel.add(loginLabel);
        //login label
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setFont(new Font("Book Antiqua", Font.BOLD, 80));
        loginLabel.setForeground(Color.WHITE);

        labelPanel.add(loginLabel);

        login.add(Box.createVerticalStrut(90));
        login.add(labelPanel);
        login.add(Box.createVerticalStrut(50));

        //text box to enter username and password
        JPanel userTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userTextPanel.setOpaque(false);
        userTextPanel.setBackground(Color.WHITE);
        userTextPanel.setPreferredSize(new Dimension(100, 1));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);
        JTextField username = new JTextField(16);
        username.setPreferredSize(new Dimension(20, 26));

        userTextPanel.add(userLabel);
        userTextPanel.add(username);
        login.add(userTextPanel);

        JPanel passTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passTextPanel.setOpaque(false);
        passTextPanel.setPreferredSize(new Dimension(100, 8));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        passLabel.setForeground(Color.WHITE);
        JTextField password = new JTextField(16);
        password.setPreferredSize(new Dimension(25, 26));

        passTextPanel.add(passLabel);
        passTextPanel.add(password);
        login.add(passTextPanel);

        //buttons to either log in or create an account
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Ariel", Font.BOLD, 20));
        loginButton.setBackground(new Color(137, 207, 240));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setPreferredSize(new Dimension(188, 33));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String user = username.getText();
                String pass = password.getText();
                int size = usernames.size();
                boolean found = false;

                if (size == 0)
                {
                    cardLayout.show(loginPanel, "login2");
                }

                for(int i = 0; i < size; i++)
                {
                    if(usernames.get(i).equals(user))
                    {
                        if(passwords.get(i).equals(pass))
                        {
                            found = true;
                            cardLayout.show(loginPanel, "prompt");

                        }
                    }
                    if (!found)
                    {
                        cardLayout.show(loginPanel, "login2");
                    }
                }
                username.setText("");
                password.setText("");

            }
        });

        buttonPanel.add(Box.createVerticalStrut(48));
        buttonPanel.add(loginButton);

        JButton createAccount = new JButton("Create an Account");
        createAccount.setFont(new Font("Calibri", Font.BOLD, 20));
        createAccount.setBackground(new Color(137, 207, 240));
        createAccount.setForeground(Color.WHITE);
        createAccount.setFocusPainted(false);
        createAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccount.setPreferredSize(new Dimension(195, 33));

        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                username.setText("");
                password.setText("");
                cardLayout.show(loginPanel, "create");
            }
        });

        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(createAccount);
        buttonPanel.add(Box.createVerticalStrut(80));

        JButton testLoginButton = new JButton("test login");
        testLoginButton.setFont(new Font("Ariel", Font.BOLD, 12));
        testLoginButton.setBackground(new Color(14, 42, 83));
        testLoginButton.setForeground(Color.WHITE);
        testLoginButton.setFocusPainted(false);
        testLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        testLoginButton.setPreferredSize(new Dimension(42, 15));

        testLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(loginPanel, "prompt");
            }
        });

        login.add(buttonPanel);
        login.add(testLoginButton);
        return login;
    }

    private JPanel login2Page() {
        JPanel login2 = new JPanel();
        login2.setLayout(new BoxLayout(login2, BoxLayout.Y_AXIS));
        login2.setBackground(new Color(14, 42, 83));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setOpaque(false);
        labelPanel.setBackground(Color.WHITE);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setFont(new Font("Book Antiqua", Font.BOLD, 80));
        loginLabel.setForeground(Color.WHITE);

        labelPanel.add(loginLabel);

        JLabel errorLabel = new JLabel("Error: Incorrect username or password!");
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setFont(new Font("Book Antiqua", Font.BOLD, 18));
        errorLabel.setForeground(Color.RED);

        login2.add(Box.createVerticalStrut(90));
        login2.add(labelPanel);
        login2.add(Box.createVerticalStrut(20));
        login2.add(errorLabel);
        login2.add(Box.createVerticalStrut(12));

        //text box to enter username and password
        JPanel userTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userTextPanel.setOpaque(false);
        userTextPanel.setBackground(Color.WHITE);
        userTextPanel.setPreferredSize(new Dimension(100, 1));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);
        JTextField username = new JTextField(16);
        username.setPreferredSize(new Dimension(20, 26));

        userTextPanel.add(userLabel);
        userTextPanel.add(username);
        login2.add(userTextPanel);

        JPanel passTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passTextPanel.setOpaque(false);
        passTextPanel.setPreferredSize(new Dimension(100, 8));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        passLabel.setForeground(Color.WHITE);
        JTextField password = new JTextField(16);
        password.setPreferredSize(new Dimension(25, 26));

        passTextPanel.add(passLabel);
        passTextPanel.add(password);
        login2.add(passTextPanel);

        //buttons to either log in or create an account
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Ariel", Font.BOLD, 20));
        loginButton.setBackground(new Color(137, 207, 240));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setPreferredSize(new Dimension(188, 33));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String user = username.getText();
                String pass = password.getText();
                int size = usernames.size();

                for(int i = 0; i < size; i++)
                {
                    if(usernames.get(i).equals(user))
                    {
                        if(passwords.get(i).equals(pass))
                        {
                            cardLayout.show(loginPanel, "prompt");
                        }
                    }
                }
                username.setText("");
                password.setText("");
            }
        });

        buttonPanel.add(Box.createVerticalStrut(48));
        buttonPanel.add(loginButton);

        JButton createAccount = new JButton("Create an Account");
        createAccount.setFont(new Font("Calibri", Font.BOLD, 20));
        createAccount.setBackground(new Color(137, 207, 240));
        createAccount.setForeground(Color.WHITE);
        createAccount.setFocusPainted(false);
        createAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccount.setPreferredSize(new Dimension(195, 33));

        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                username.setText("");
                password.setText("");
                cardLayout.show(loginPanel, "create");
            }
        });

        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(createAccount);
        buttonPanel.add(Box.createVerticalStrut(80));

        JButton testLoginButton = new JButton("test login");
        testLoginButton.setFont(new Font("Ariel", Font.BOLD, 12));
        testLoginButton.setBackground(new Color(14, 42, 83));
        testLoginButton.setForeground(Color.WHITE);
        testLoginButton.setFocusPainted(false);
        testLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        testLoginButton.setPreferredSize(new Dimension(42, 15));

        testLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(loginPanel, "prompt");
            }
        });

        login2.add(buttonPanel);
        login2.add(testLoginButton);
        return login2;
    }

    private JPanel createPage() {
        JPanel create = new JPanel();
        create.setLayout(new BoxLayout(create, BoxLayout.Y_AXIS));
        create.setBackground(new Color(14, 42, 83));

        //create account label
        JLabel loginLabel = new JLabel("Create your Account:", JLabel.CENTER);
        loginLabel.setFont(new Font("Book Antiqua", Font.BOLD, 50));
        loginLabel.setForeground(Color.WHITE);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setOpaque(false);
        labelPanel.add(loginLabel);

        create.add(Box.createVerticalStrut(100));
        create.add(labelPanel);
        create.add(Box.createVerticalStrut(60));

        //text box to enter username and password
        JPanel userTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userTextPanel.setOpaque(false);

        JLabel userLabel = new JLabel("New username:");
        userLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);
        JTextField username = new JTextField(16);
        username.setPreferredSize(new Dimension(20, 26));

        userTextPanel.add(userLabel);
        userTextPanel.add(username);
        create.add(userTextPanel);

        JPanel passTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passTextPanel.setOpaque(false);

        JLabel passLabel = new JLabel("New password:");
        passLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        passLabel.setForeground(Color.WHITE);
        JTextField password = new JTextField(16);
        password.setPreferredSize(new Dimension(20, 26));

        passTextPanel.add(passLabel);
        passTextPanel.add(password);
        create.add(passTextPanel);

        //button to create account
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JButton createButton = new JButton("SUBMIT");
        createButton.setFont(new Font("Ariel", Font.BOLD, 25));
        createButton.setBackground(new Color(137, 207, 240));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.setPreferredSize(new Dimension(135, 39));

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String user = username.getText();
                int size = usernames.size();
                boolean found = false;
                if(user.isEmpty())
                {
                    found = true;
                    username.setText("");
                    password.setText("");
                    cardLayout.show(loginPanel, "create2");
                }
                for(int i = 0; i < size; i++)
                {
                    if(usernames.get(i).equals(user))
                    {
                        found = true;
                        username.setText("");
                        password.setText("");
                        cardLayout.show(loginPanel, "create2");
                    }
                }
                if(!found)
                {
                    usernames.add(username.getText());
                    passwords.add(password.getText());
                    System.out.println(usernames);
                    System.out.println(passwords);
                    username.setText("");
                    password.setText("");
                    cardLayout.show(loginPanel, "login");
                }
            }
        });

        buttonPanel.add(Box.createVerticalStrut(90));
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createVerticalStrut(100));

        create.add(buttonPanel);
        return create;
    }

    private JPanel create2Page() {
        JPanel create2 = new JPanel();
        create2.setLayout(new BoxLayout(create2, BoxLayout.Y_AXIS));
        create2.setBackground(new Color(14, 42, 83));

        //create account label
        JLabel loginLabel = new JLabel("Create your Account:", JLabel.CENTER);
        loginLabel.setFont(new Font("Book Antiqua", Font.BOLD, 50));
        loginLabel.setForeground(Color.WHITE);

        JLabel errorLabel = new JLabel("Error: That username is taken! Try another.");
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setFont(new Font("Book Antiqua", Font.BOLD, 18));
        errorLabel.setForeground(Color.RED);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setOpaque(false);
        labelPanel.add(loginLabel);

        create2.add(Box.createVerticalStrut(100));
        create2.add(labelPanel);
        create2.add(Box.createVerticalStrut(30));
        create2.add(errorLabel);
        create2.add(Box.createVerticalStrut(12));

        //text box to enter username and password
        JPanel userTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userTextPanel.setOpaque(false);

        JLabel userLabel = new JLabel("New username:");
        userLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);
        JTextField username = new JTextField(16);
        username.setPreferredSize(new Dimension(20, 26));

        userTextPanel.add(userLabel);
        userTextPanel.add(username);
        create2.add(userTextPanel);

        JPanel passTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passTextPanel.setOpaque(false);

        JLabel passLabel = new JLabel("New password:");
        passLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        passLabel.setForeground(Color.WHITE);
        JTextField password = new JTextField(16);
        password.setPreferredSize(new Dimension(20, 26));

        passTextPanel.add(passLabel);
        passTextPanel.add(password);
        create2.add(passTextPanel);

        //button to create account
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JButton createButton = new JButton("SUBMIT");
        createButton.setFont(new Font("Ariel", Font.BOLD, 25));
        createButton.setBackground(new Color(137, 207, 240));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.setPreferredSize(new Dimension(135, 39));

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String user = username.getText();
                int size = usernames.size();
                boolean found = false;
                if(user.isEmpty())
                {
                    found = true;
                    username.setText("");
                    password.setText("");
                }
                for(int i = 0; i < size; i++)
                {
                    if(usernames.get(i).equals(user))
                    {
                        found = true;
                        username.setText("");
                        password.setText("");
                    }
                }
                if(!found)
                {
                    usernames.add(username.getText());
                    passwords.add(password.getText());
                    System.out.println(usernames);
                    System.out.println(passwords);
                    username.setText("");
                    password.setText("");
                    cardLayout.show(loginPanel, "login");
                }
            }
        });

        buttonPanel.add(Box.createVerticalStrut(90));
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createVerticalStrut(100));

        create2.add(buttonPanel);
        return create2;
    }

    private JPanel promptPage() {
        JPanel prompt = new JPanel();
        prompt.setLayout(new BoxLayout(prompt, BoxLayout.Y_AXIS));
        prompt.setBackground(new Color(14, 42, 83));

        //question label
        JLabel promptLabel = new JLabel("<html><div style='text-align: center;'>Would you like to complete the questionnaire or go to your homepage?<html>", JLabel.CENTER);
        promptLabel.setFont(new Font("Book Antiqua", Font.BOLD, 35));
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

        JButton question = new JButton("Questionnaire");
        question.setFont(new Font("Calibri", Font.BOLD, 25));
        question.setBackground(new Color(137, 207, 240));
        question.setForeground(Color.WHITE);
        question.setFocusPainted(false);
        question.setAlignmentX(Component.CENTER_ALIGNMENT);
        question.setPreferredSize(new Dimension(220, 45));

        question.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(loginPanel, "InvestmentForm");
            }
        });

        buttonPanel.add(Box.createVerticalStrut(130));
        buttonPanel.add(question);

        JButton home = new JButton("Homepage");
        home.setFont(new Font("Calibri", Font.BOLD, 25));
        home.setBackground(new Color(137, 207, 240));
        home.setForeground(Color.WHITE);
        home.setFocusPainted(false);
        home.setAlignmentX(Component.CENTER_ALIGNMENT);
        home.setPreferredSize(new Dimension(220, 45));

        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(loginPanel, "homePage");
            }
        });

        buttonPanel.add(Box.createHorizontalStrut(150));
        buttonPanel.add(home);
        buttonPanel.add(Box.createVerticalStrut(300));


        prompt.add(buttonPanel);
        return prompt;
    }
}

