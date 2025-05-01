package models;

public class PriceEntry {
    public String timestamp;
    public double price;

    public PriceEntry (String timestamp, double price) {
        this.timestamp = timestamp;
        this.price = price;
    }
}
