package models;

public class User {
    private String username;
    private String password;
    private Portfolio portfolio;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.portfolio = new Portfolio();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void addInvestment() {

    }

    public void removeInvestment() {

    }
}
