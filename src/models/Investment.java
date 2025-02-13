package models;

import java.time.LocalDate;

public class Investment {

    private Stock stock;
    private double numShares;
    private double priceBought;
    private double totalCost;
    private LocalDate dateBought;

    private double currentValue;
    private double percentChange;
    private double profit;

    // constructor
    public Investment(Stock stock, double numShares, double priceBought, LocalDate dateBought) {
        this.stock = stock;
        this.numShares = numShares;
        this.priceBought = priceBought;
        this.dateBought = dateBought;
        this.totalCost = numShares * priceBought;
        this.currentValue = totalCost;  // initial value is equal to the cost paid for investment
        this.percentChange = 0;   // initially no changes made
        this.profit = 0;   // profit starts at 0 and updates over time
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public double getCurrentValue() {
        return this.currentValue;
    }

    public double getPercentChange () {
        return this.percentChange;
    }

    public double getProfit() {
        return this.profit;
    }

    public void updateStockPrice(double newPrice) {
        this.currentValue = newPrice * numShares;   // reflect the newPrice on all owned shares
        this.profit = currentValue - totalCost;
        this.percentChange = ((currentValue - totalCost) / totalCost) * 100;    //update percentage to reflect change
    }

    public void printInfo() {
        System.out.println("Stock: " + stock.getSymbol() + ", Num Shares Owned: " + numShares + ", Price bought at: " + priceBought + ", Current Value: " + currentValue );
    }

    public static void main(String[] args) {

    }
}
