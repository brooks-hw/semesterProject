package models;

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
