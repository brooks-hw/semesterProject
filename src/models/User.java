package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static User instance; // Singleton instance

    private String username;
    private String password;
    private String name;
    private List<UserInvestment> portfolio = new ArrayList<>();
    private double balance;

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

    public static void logOutInstance() {
        instance = null;
    }

    public void addInvestment(UserInvestment investment) {
        portfolio.add(investment);
    }

    public List<UserInvestment> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(List<UserInvestment> portfolio) {
        this.portfolio = portfolio;
    }

    // getters and setters

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

    public String getRiskProfile() {
        return riskProfile;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setRiskProfile(String riskProfile) {
        this.riskProfile = riskProfile;
    }

    public void addInvestment() {
        // implementation
    }

    public void removeInvestment() {
        // implementation
    }
}
