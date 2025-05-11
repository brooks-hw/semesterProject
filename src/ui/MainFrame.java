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

        JPanel investmentForm = new InvestmentForm(this);
        investmentForm.setOpaque(false);
        panelManager.add(investmentForm, "Investment Form");

        JPanel investmentAmountPage = new InvestmentAmountPage(this);
        investmentAmountPage.setOpaque(false);
        panelManager.add(investmentAmountPage, "Investment Amount Page");

        AddInvestmentPage addInvestmentPage = new AddInvestmentPage(this);
        addInvestmentPage.setOpaque(false);
        panelManager.add(addInvestmentPage, "Add Investment");

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

    public void showRemoveInvestmentPage() {
        RemoveInvestmentPage page = new RemoveInvestmentPage(this);  // `this` is MainFrame, which is the screen manager
        page.setOpaque(false);
        panelManager.add(page, "Remove Investment");
        switchTo("Remove Investment");
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
    }
}
