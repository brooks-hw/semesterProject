package models;

import java.util.List;

public class InvestmentData {
    public String name;
    public List<PriceEntry> todayPrices;
    public List<PriceEntry> recentPrices;
    public List<PriceEntry> historicalPrices;

    public InvestmentData(String name, List<PriceEntry> todayPrices, List<PriceEntry> recentPrices, List<PriceEntry> historicalPrices) {
        this.name = name;
        this.todayPrices = todayPrices;
        this.recentPrices = recentPrices;
        this.historicalPrices = historicalPrices;
    }
}
