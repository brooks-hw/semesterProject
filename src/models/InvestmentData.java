package models;

import java.util.List;

// Used by stockAPIClient, holds various data regarding a specific stock, including historical prices
public class InvestmentData {
    public String name;
    public String type;
    public List<PriceEntry> todayPrices;
    public List<PriceEntry> recentPrices;
    public List<PriceEntry> historicalPrices;

    public InvestmentData(String name, String type, List<PriceEntry> todayPrices, List<PriceEntry> recentPrices, List<PriceEntry> historicalPrices) {
        this.name = name;
        this.type = type;
        this.todayPrices = todayPrices;
        this.recentPrices = recentPrices;
        this.historicalPrices = historicalPrices;
    }
}
