package data;

import models.InvestmentData;
import models.PriceEntry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StockAPIClient is responsible for loading and providing access to stock data
 * from a local JSON file. It parses historical, recent, and daily (today) price data
 * for each stock and stores it in a structured format.
 * Worked through with ChatGPT and formatted by Brooks
 */
public class StockAPIClient {

    // Holds all parsed stock data, keyed by ticker symbol (e.g., "GOOGL")
    private Map<String, InvestmentData> stockDataMap;
    private Map<String, InvestmentData> cryptoDataMap;
    private Map<String, InvestmentData> bondDataMap;

    /**
     * Constructor automatically loads stock data from the default file path.
     */
    public StockAPIClient() {
        this.stockDataMap = loadStockDataFromJson("data/stock_data.json");
        this.cryptoDataMap = loadStockDataFromJson("data/crypto_data.json");
        this.bondDataMap = loadStockDataFromJson("data/bond_data.json");
    }

    /**
     * Returns the StockData object for a given symbol (e.g., "AAPL").
     */
    public InvestmentData getStockData(String symbol) {
        return stockDataMap.get(symbol);
    }

    public Map<String, InvestmentData> getStockDataMap() {
        return this.stockDataMap;
    }

    public InvestmentData getCryptoData(String symbol) {
        return cryptoDataMap.get(symbol);
    }

    public Map<String, InvestmentData> getCryptoDataMap() {
        return this.cryptoDataMap;
    }

    public InvestmentData getBondData(String symbol) {
        return stockDataMap.get(symbol);
    }

    /**
     * Loads and parses the stock data JSON file into memory.
     *
     * @param filepath Path to the JSON file containing stock data
     * @return A map of ticker symbols to their parsed StockData
     */
    public Map<String, InvestmentData> loadStockDataFromJson(String filepath) {
        Map<String, InvestmentData> map = new HashMap<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(filepath)));
            JSONObject root = new JSONObject(content);

            for (String ticker : root.keySet()) {
                try {
                    JSONObject stockObj = root.getJSONObject(ticker);
                    String name = stockObj.getString("name");

                    List<PriceEntry> recentPrices = parsePriceArray(stockObj.getJSONArray("recent_prices"), "date");
                    List<PriceEntry> historicalPrices = parsePriceArray(stockObj.getJSONArray("historical_prices"), "date");

                    List<PriceEntry> todayPrices = new ArrayList<>();
                    if (stockObj.has("today_prices")) {
                        todayPrices = parsePriceArray(stockObj.getJSONArray("today_prices"), "time");
                    }

                    InvestmentData data = new InvestmentData(name, recentPrices, historicalPrices, todayPrices);
                    map.put(ticker, data);

                } catch (Exception e) {
                    System.err.println("❌ Failed to parse data for symbol: " + ticker);
                    e.printStackTrace(); // Show the real reason
                }
            }

        } catch (IOException | JSONException e) {
            System.err.println("❌ Error loading or parsing file: " + filepath);
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Parses a JSON array of price entries into a list of PriceEntry objects.
     *
     * @param array     The JSONArray containing price objects
     * @param fieldName The name of the timestamp field to read ("date" or "time")
     * @return A list of PriceEntry objects
     */
    private List<PriceEntry> parsePriceArray(JSONArray array, String fieldName) {
        List<PriceEntry> list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);

            // Get the timestamp (date or time string)
            String timestamp = obj.getString(fieldName);

            // Get the associated price
            double price = obj.getDouble("price");

            // Create and add the price entry
            list.add(new PriceEntry(timestamp, price));
        }

        return list;
    }
}
