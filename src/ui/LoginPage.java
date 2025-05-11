package ui;

import data.PortfolioManager;
import models.User;
import models.UserInvestment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoginPage extends JPanel{
    private CardLayout cardLayout;
    private JPanel loginPanel;
    private JLabel errorLabel;
    public ArrayList<String> usernames;
    public ArrayList<String> passwords;
    public ArrayList<String> names;
    public ArrayList<Integer> scores;
    public ArrayList<String> risk;
    private final iScreenManager screenManager;


    public LoginPage(iScreenManager screenManager) {
        this.screenManager = screenManager;
        // for questionnaire
        // Add InvestmentForm with multiple questions

        InvestmentForm investmentForm = new InvestmentForm(screenManager);
        HomePage homePage = new HomePage(screenManager);

        usernames = new ArrayList<>();
        passwords = new ArrayList<>();
        names = new ArrayList<>();
        scores = new ArrayList<>();
        risk = new ArrayList<>();

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
    }

    public void returnLogin() {
        errorLabel.setVisible(false);
        cardLayout.show(loginPanel, "login");
    }

    private JPanel loginPage() {
        JPanel login = new JPanel();
        login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
        login.setBackground(new Color(14, 42, 83));


        JPanel quit = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        quit.setOpaque(false);
        quit.setBackground(Color.WHITE);
        JButton quitButton = new JButton("Quit");
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusPainted(false);
        quitButton.setBackground(new Color(220, 20, 60));

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Terminates the program
            }
        });

        quit.add(quitButton);
        login.add(quit, BorderLayout.EAST);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setOpaque(false);
        labelPanel.setBackground(Color.WHITE);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setFont(new Font("Book Antiqua", Font.BOLD, 80));
        loginLabel.setForeground(Color.WHITE);

        labelPanel.add(loginLabel);

        errorLabel = new JLabel("Error: Incorrect username or password!");
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setFont(new Font("Book Antiqua", Font.BOLD, 18));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        login.add(Box.createVerticalStrut(15));
        login.add(labelPanel);
        login.add(Box.createVerticalStrut(25));
        login.add(errorLabel);
        login.add(Box.createVerticalStrut(10));

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
                try {
                    getData("accData", usernames, passwords, names, scores, risk);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                int size = usernames.size();
                for(int i = 0; i < size; i++)
                {
                    if(usernames.get(i).equals(user))
                    {
                        if(passwords.get(i).equals(pass))
                        {
                            String name = names.get(i);
                            int score = scores.get(i);
                            String risk1 = risk.get(i);
                            User newUser = User.getInstance(name, user, pass);
                            // Load portfolio from file
                            List<UserInvestment> savedInvestments = PortfolioManager.loadFromFile(user); // JSON or CSV
                            //Check if user has a portfolio already
                            if (savedInvestments != null) {
                                newUser.setPortfolio(savedInvestments);
                            } else {
                                System.out.println("No portfolio yet");
                                newUser.setPortfolio(new ArrayList<>()); // Empty portfolio for new users
                            }
                            newUser.setPortfolio(savedInvestments);

                            ((MainFrame) screenManager).getHomePage().setup(newUser);
                            ((MainFrame) screenManager).getHomePage().setUser(newUser);

                            //Only prompt questionnaire if user hasn't done it yet
                            errorLabel.setVisible(false);
                            if(score == -1) {
                                screenManager.switchTo("Investment Form");
                                //cardLayout.show(loginPanel, "prompt");
                            }
                            else {
                                newUser.setTotalScore(score);
                                newUser.setRiskProfile(risk1);
                                screenManager.switchTo("Home Page");
                            }
                        }
                    }
                }
                errorLabel.setVisible(true);
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
                errorLabel.setVisible(false);
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
                String name = "John Doe";
                String user = "test";
                String pass = "test!";
                User newUser = User.getInstance(name, user, pass);
                ((MainFrame) screenManager).getHomePage().setup(newUser);
                errorLabel.setVisible(false);
                cardLayout.show(loginPanel, "prompt");
            }
        });

        login.add(buttonPanel);
        //login.add(testLoginButton);
        return login;
    }

    private JPanel createPage() {
        JPanel create = new JPanel();
        create.setLayout(new BoxLayout(create, BoxLayout.Y_AXIS));
        create.setBackground(new Color(14, 42, 83));

        //create account label
        JLabel loginLabel = new JLabel("Create your Account:", JLabel.CENTER);
        loginLabel.setFont(new Font("Book Antiqua", Font.BOLD, 50));
        loginLabel.setForeground(Color.WHITE);

        JLabel errorLabel = new JLabel();
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setFont(new Font("Book Antiqua", Font.BOLD, 18));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setOpaque(false);
        labelPanel.add(loginLabel);

        create.add(Box.createVerticalStrut(100));
        create.add(labelPanel);
        create.add(Box.createVerticalStrut(30));
        create.add(errorLabel);
        create.add(Box.createVerticalStrut(12));

        //text box to enter username and password
        JPanel nameTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nameTextPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        JTextField name = new JTextField(16);
        name.setPreferredSize(new Dimension(20, 26));

        nameTextPanel.add(nameLabel);
        nameTextPanel.add(name);
        create.add(nameTextPanel);

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
                String pass = password.getText();
                boolean found = false;
                try {
                    getData("accData", usernames, passwords, names, scores, risk);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                int size = usernames.size();
                if(user.isEmpty())
                {
                    found = true;
                    name.setText("");
                    username.setText("");
                    password.setText("");
                };
                for(int i = 0; i < size; i++)
                {
                    if(usernames.get(i).equals(user))
                    {
                        found = true;
                        errorLabel.setText("Error: That username is taken! Try another.");
                        errorLabel.setVisible(true);
                        name.setText("");
                        username.setText("");
                        password.setText("");
                    }
                }
                if(passReq(pass))
                {
                    found = true;
                    errorLabel.setText("Error: Password requires at least one special character! Try another.");
                    errorLabel.setVisible(true);
                    name.setText("");
                    username.setText("");
                    password.setText("");
                }
                if(!found)
                {
                    usernames.add(username.getText());
                    passwords.add(password.getText());
                    names.add(name.getText());
                    try {
                        saveData("accData", username.getText(), password.getText(), name.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    name.setText("");
                    username.setText("");
                    password.setText("");
                    errorLabel.setVisible(false);
                    cardLayout.show(loginPanel, "login");
                }
            }
        });

        buttonPanel.add(Box.createVerticalStrut(80));
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createVerticalStrut(100));

        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font("Ariel", Font.BOLD, 15));
        backButton.setBackground(new Color(14, 42, 83));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(18, 19));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(loginPanel, "login");
            }
        });

        create.add(buttonPanel);
        create.add(backButton);
        return create;
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
                screenManager.switchTo("Investment Form");
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
                screenManager.switchTo("Home Page");
            }
        });

        buttonPanel.add(Box.createHorizontalStrut(150));
        buttonPanel.add(home);
        buttonPanel.add(Box.createVerticalStrut(300));
        prompt.add(buttonPanel);
        return prompt;
    }

    public static boolean passReq(String pass)
    {
        return pass.matches("[a-zA-Z0-9]*");
    }

    public static void saveData(String filename, String user, String pass, String name) throws IOException {
        Path filePath = Paths.get("data", filename + ".csv");

        // Ensure parent directory exists
        Files.createDirectories(filePath.getParent());

        try (FileWriter writer = new FileWriter(filePath.toFile(), true)) {
            int quesStat = -1;
            String risk = ""; // don't use \0, it can mess with CSVs
            writer.write(user + "," + pass + "," + name + "," + quesStat + "," + risk + "\n");
        }
    }

    public static void getData(String filename, ArrayList<String> user, ArrayList<String> pass,
                               ArrayList<String> name, ArrayList<Integer> score, ArrayList<String> risk) throws IOException {
        Path filePath = Paths.get("data", filename + ".csv");

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            user.clear();
            pass.clear();
            name.clear();
            score.clear();
            risk.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    user.add(parts[0]);
                    pass.add(parts[1]);
                    name.add(parts[2]);
                    score.add(Integer.parseInt(parts[3]));
                    risk.add(parts[4]);
                }
                else if (parts.length >= 4) {
                    user.add(parts[0]);
                    pass.add(parts[1]);
                    name.add(parts[2]);
                    score.add(Integer.parseInt(parts[3]));
                    risk.add("");
                }
            }
        }
    }
}
