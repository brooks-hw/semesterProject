package models;

// Class to hold information about investments in a user's portfolio
// Separate from InvestmentData because user investments don't require historical data
public class UserInvestment {
    public String symbol;
    public String type;
    public double quantity;           // e.g., 2.5 ETH
    public double buyPrice;           // price per unit at purchase time

    public UserInvestment(String symbol, String type, double quantity, double buyPrice, String purchaseDate) {
        this.symbol = symbol;
        this.type = type;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
    }
}
