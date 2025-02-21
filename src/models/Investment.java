package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Investment {

    private Stock stock;
    private double numShares;       //How many shares did the user buy
    private double priceBought;     //At what price were the shares bought at
    private double totalCost;       //What was the original totalCost of the investment
    private LocalDate dateBought;   //On what day was the investment purchased
    private LocalTime timeBought;   //At what time was the investment purchased

    private double currentValue;    //What is the investment currently worth (DYNAMIC)
    private double percentChange;   //What is the percent change on the investment (DYNAMIC)
    private double profit;          //How much profit has the user made? (DYNAMIC)

    // blank constructor
    public Investment() {

    }

    // @Purpose: Constructor for Investments that are created during runtime
    public Investment (Stock stock, double numShares, double priceBought, LocalDate dateBought, LocalTime timeBought) {
        this.stock = stock;
        this.numShares = numShares;
        this.priceBought = priceBought;
        this.dateBought = dateBought;
        this.timeBought = timeBought;
        this.totalCost = numShares * priceBought;
        this.currentValue = totalCost;    // currentValue initially starts at the price paid
        this.percentChange = 0;   // initially no changes made
        this.profit = 0;   // profit starts at 0 and updates over time
    }

    // @Purpose: Constructor for Investments that are loaded from userData.csv
    public Investment(Stock stock, double numShares, double priceBought, double totalCost, LocalDate dateBought, LocalTime timeBought, double currentValue, double percentChange, double profit) {
        this.stock = stock;
        this.numShares = numShares;
        this.priceBought = priceBought;
        this.dateBought = dateBought;
        this.timeBought = timeBought;
        this.totalCost = totalCost;
        this.currentValue = currentValue;
        this.percentChange = percentChange;
        this.profit = profit;
    }

    public String getName() {
        return this.stock.getName();
    }

    public String getSymbol() {
        return this.stock.getSymbol();
    }

    public double getNumShares() {
        return this.numShares;
    }

    public double getPriceBought() {
        return this.priceBought;
    }

    public LocalDate getDateBought() {
        return this.dateBought;
    }

    public LocalTime getTimeBought() {
        return this.timeBought;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    //TODO: Change this to reflect the actual current value of the stock given by the StockAPIClient, account for date and time bought
    // WHEN: after StockAPIClient is functional
    public double getCurrentValue() {
        //get latest price from StockAPIClient
        double latestPrice = data.StockAPIClient.getStockPrice(stock.getSymbol());

        //update stock's price
        stock.setPrice(latestPrice);

        //compute current value based on updated stock price and number of shares
        return latestPrice * numShares;

    }

    public double getPercentChange () {
        return this.percentChange;
    }

    //TODO: Update this to base the profit on the current value given by getCurrentValue()
    // WHEN: after getCurrentValue() method has been properly finished
    public double getProfit() {
        //profit is the difference between current value and total cost
        return getCurrentValue() - totalCost;
    }

    public void updateStockPrice(double newPrice) {
        this.currentValue = newPrice * numShares;   // reflect the newPrice on all owned shares
        this.profit = currentValue - totalCost;
        this.percentChange = ((currentValue - totalCost) / totalCost) * 100;    //update percentage to reflect change
    }

    /*
    //TODO: call getCurrentValue() when printing stock info instead of just printing it
    // WHEN: after getCurrentValue() method has been properly finished
    public void printInfo() {
        System.out.println("Stock: " + stock.getSymbol() + ", Num Shares Owned: " + numShares + ", Price bought at: " + priceBought + ", Current Value: " + currentValue );
    }
     */

    public void printInfo() {
        System.out.println("Stock Name: " + stock.getName() + ", Stock Symbol: " + stock.getSymbol() + ", Stock's Current Price: " + stock.getPrice());
        System.out.println("Shares Bought: " + numShares + ", Price Bought at: " + priceBought + ", Total Initial Cost: " + totalCost);
        System.out.println("Date Bought: " + dateBought + ", Time Bought: " + timeBought);
        System.out.println("Current Investment Value: " + currentValue + ", Total Profit: " + profit + ", Percent Change: " + percentChange + "\n");
    }

    public static void main(String[] args) {

    }
}
