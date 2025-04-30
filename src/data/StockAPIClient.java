package data;

import models.PriceEntry;
import models.StockData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockAPIClient {

    private Map<String, StockData> stockDataMap;

    public StockAPIClient() {
        this.stockDataMap = loadStockDataFromJson("data/stock_data.json");
    }

    public StockData getStockData(String symbol) {
        return stockDataMap.get(symbol);
    }

    public Map<String, StockData> loadStockDataFromJson(String filepath) {
        Map<String, StockData> map = new HashMap<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(filepath)));
            JSONObject root = new JSONObject(content);

            for (String ticker : root.keySet()) {
                JSONObject stockObj = root.getJSONObject(ticker);

                String name = stockObj.getString("name");

                List<PriceEntry> recentPrices = parsePriceArray(stockObj.getJSONArray("recent_prices"));
                List<PriceEntry> historicalPrices = parsePriceArray(stockObj.getJSONArray("historical_prices"));

                StockData stockData = new StockData(name, recentPrices, historicalPrices);
                map.put(ticker, stockData);
            }

        } catch (IOException e) {
            System.err.println("Error loading stock data: " + e.getMessage());
        }

        return map;
    }

    private List<PriceEntry> parsePriceArray(JSONArray array) {
        List<PriceEntry> list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String date = obj.getString("date");
            double price = obj.getDouble("price");
            list.add(new PriceEntry(date, price));
        }

        return list;
    }
}
