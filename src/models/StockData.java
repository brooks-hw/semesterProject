package models;

import java.util.List;

public class StockData {
    public String name;
    public List<PriceEntry> recentPrices;
    public List<PriceEntry> historicalPrices;

    public StockData(String name, List<PriceEntry> recentPrices, List<PriceEntry> historicalPrices) {
        this.name = name;
        this.recentPrices = recentPrices;
        this.historicalPrices = historicalPrices;
    }

}
