package models;

import data.StockAPIClient;

import java.util.ArrayList;
import java.util.List;

//used ChatGPT to partially develop singleton getInstance(), logOutInstance()

public class User {
    private static User instance; // Singleton instance

    private String username;
    private String password;
    private String name;
    private List<UserInvestment> portfolio = new ArrayList<>();
    private double balance;
    private double investmentAmount;
    boolean usingTemplate;

    private int totalScore;
    private String riskProfile;

    // Private constructor — prevents "new User()" outside this class
    private User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.totalScore = 0;
        this.riskProfile = "";
    }

    // getters and setters

    // Public method to get the instance
    public static User getInstance(String name, String username, String password) {
        if (instance == null) {
            instance = new User(name, username, password);
        }
        return instance;
    }

    // Overloaded method to just get existing instance (no username/password)
    public static User getInstance() {
        if (instance == null) {
            throw new IllegalStateException("User has not been created yet!");
        }
        return instance;
    }

    //empties instance
    public static void logOutInstance() {
        instance = null;
    }

    public List<UserInvestment> getPortfolio() {
        return portfolio;
    }

    public void addInvestment(UserInvestment investment) {
        for (UserInvestment existing : portfolio) {
            if (existing.symbol.equalsIgnoreCase(investment.symbol)) {
                // Combine quantities and update price to average buy price
                double totalQuantity = existing.quantity + investment.quantity;
                double totalCost = (existing.quantity * existing.buyPrice) + (investment.quantity * investment.buyPrice);
                existing.quantity = totalQuantity;
                existing.buyPrice = totalCost / totalQuantity;
                return;
            }
        }
        // If not found, add as new investment
        portfolio.add(investment);
    }

    public boolean removeInvestment(String symbol, double quantityToRemove) {
        for (UserInvestment inv : portfolio) {
            if (inv.symbol.equalsIgnoreCase(symbol)) {
                if (inv.quantity < quantityToRemove) return false;
                inv.quantity -= quantityToRemove;
                if (inv.quantity == 0) {
                    portfolio.remove(inv);
                }
                return true;
            }
        }
        return false;
    }

    public void setPortfolio(List<UserInvestment> portfolio) {
        this.portfolio = new ArrayList<>(portfolio);
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public double getBalance() {
        return this.balance;
    }

    public void updateBalance(StockAPIClient apiClient) {
        double total = 0.0;
        for (UserInvestment inv : portfolio) {
            double price = inv.buyPrice; // or use API if needed
            total += inv.quantity * price;
        }
        this.balance = Math.round(total * 100.0) / 100.0;
    }

    public void setBalance(double amount) {
        this.balance = amount;
    }

    public String getRiskProfile() {
        return riskProfile;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setRiskProfile(String riskProfile) {
        this.riskProfile = riskProfile;
    }

    public void setInvestmentAmount(double amount) {
        this.investmentAmount = amount;
    }

    public double getInvestmentAmount() {
        return investmentAmount;
    }

    public void setUsingTemplate(boolean usingTemplate) {
        this.usingTemplate = usingTemplate;
    }

    public boolean getUsingTemplate() {
        return this.usingTemplate;
    }

}
