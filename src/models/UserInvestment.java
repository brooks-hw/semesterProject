package models;

public class UserInvestment {
    public String symbol;
    public double quantity;           // e.g., 2.5 ETH
    public double buyPrice;           // price per unit at purchase time
    public String purchaseDate;       // optional, e.g., "2025-04-30"

    public UserInvestment(String symbol, double quantity, double buyPrice, String purchaseDate) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.purchaseDate = purchaseDate;
    }
}
