package models;

public class Stock {

    private String name;
    private String symbol;
    private double price;

    // constructor
    public Stock(String name, String symbol, double price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public void printInfo() {
        System.out.println("Name: " + name + " Symbol: " + symbol + " Price: " + price + "\n");
    }


}
