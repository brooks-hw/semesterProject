package models;

public class User {
    private static User instance; // Singleton instance

    private String username;
    private String password;
    private String name;
    private Portfolio portfolio;
    private int totalScore;
    private String riskProfile;

    // Private constructor — prevents "new User()" outside this class
    private User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.portfolio = new Portfolio();
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