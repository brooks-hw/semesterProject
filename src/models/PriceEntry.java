package models;

// Entry class used to hold archived data entries
// holds the time of the current entry and the given price at that time
public class PriceEntry {
    public String timestamp;
    public double price;

    public PriceEntry (String timestamp, double price) {
        this.timestamp = timestamp;
        this.price = price;
    }
}
