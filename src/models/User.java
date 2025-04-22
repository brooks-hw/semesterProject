package models;

public class User {
    private String username;
    private Portfolio portfolio;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.portfolio = new Portfolio();
    }

    public void addInvestment() {

    }

    public void removeInvestment() {

    }
}
