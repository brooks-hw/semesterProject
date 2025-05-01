package ui;

import models.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame implements iScreenManager {

    private User currentUser;
    private HomePage homePage;

    private static JFrame mainFrame;

    private JPanel panelManager;
    private CardLayout cardLayout;

    public LoginPage loginPage;

    public void switchTo(String screenName) {
        if (screenName.equals("Results Page")) {
            JPanel resultsPage = new ResultsPage(this);
            resultsPage.setOpaque(false);
            panelManager.add(resultsPage, "Results Page");
        }

        cardLayout.show(panelManager, screenName);
        System.out.println("Switched to: " + screenName);
    }

    // Instantiate member variables
    MainFrame() {
        mainFrame = new JFrame("Investment Buddy");
        mainFrame.setSize(900, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelManager = new JPanel(cardLayout);
        panelManager.setOpaque(true);
        panelManager.setBackground(new Color(14, 42, 83));

        JPanel startPage = new StartPage(this);
        startPage.setOpaque(false);
        panelManager.add(startPage, "Start Page");

        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();

        loginPage = new LoginPage(this);
        panelManager.add(loginPage, "Login Page");

        JPanel congratsPage = new FormCongratulations(this); // 'this' refers to MainFrame implementing iScreenManager
        loginPage.setOpaque(false);
        panelManager.add(congratsPage, "Congratulations");

        homePage = new HomePage(this);  // 'this' is screen manager
        homePage.setOpaque(false);
        panelManager.add(homePage, "Home Page");

        AddInvestmentPage addInvestmentPage = new AddInvestmentPage(this);
        addInvestmentPage.setOpaque(false);
        panelManager.add(addInvestmentPage, "Add Investment");

        JPanel investmentForm = new InvestmentForm(this);
        investmentForm.setOpaque(false);
        panelManager.add(investmentForm, "Investment Form");

        JPanel investmentAmountPage = new InvestmentAmountPage(this);
        investmentAmountPage.setOpaque(false);
        panelManager.add(investmentAmountPage, "Investment Amount Page");


        mainFrame.add(panelManager);
        mainFrame.setContentPane(panelManager);
        mainFrame.setVisible(true);


    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    @Override
    public void switchToPortfolioPage(double investmentAmount) {
        JPanel portfolioPage = new PortfolioSuggestionPage(investmentAmount, this);
        portfolioPage.setOpaque(false);
        panelManager.add(portfolioPage, "Portfolio Suggestion Page");
        cardLayout.show(panelManager, "Portfolio Suggestion Page");
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
    }
}
     /*
public static void createWindow() {
         frame = new JFrame("Investment Buddy");
         frame.setSize(900, 600);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setResizable(false);
         frame.setLocationRelativeTo(null);

         // Background panel with image
         BackgroundPanel backgroundImagePanel = new BackgroundPanel("images/image2.jpg");
         backgroundImagePanel.setLayout(new BorderLayout());

         // Main Card Layout Panel
         mainCardLayout = new CardLayout();
         mainCardPanel = new JPanel(mainCardLayout);
         mainCardPanel.setOpaque(false);

         // Add Start Menu
         JPanel startMenu = startMenu();
         startMenu.setOpaque(false);
         mainCardPanel.add(startMenu, "StartMenu");

         // Add InvestmentForm with multiple questions
         List<String> questions = Arrays.asList(
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
         mainCardPanel.add(investmentForm, "InvestmentForm");

         LoginPage loginPage = new LoginPage();
         mainCardPanel.add(loginPage, "LoginPage");

         HomePage homePage = new HomePage();
         mainCardPanel.add(homePage, "HomePage");

         backgroundImagePanel.add(mainCardPanel, BorderLayout.CENTER);
         frame.setContentPane(backgroundImagePanel);
         frame.setVisible(true);
     }
      */
