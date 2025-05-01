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
    //Holds all the investments by type and symbol
    private Map<String, InvestmentData> investmentMap;

    //Constructor automatically loads stock data from the default file path.
    public StockAPIClient() {
        this.investmentMap = loadInvestmentData("data/investment_data.json");
        printInvestmentDataSummary();
    }

    public InvestmentData getDataFromSymbol(String symbol) {
        return investmentMap.get(symbol);
    }

    public Map<String, InvestmentData> getInvestmentMap() {
        return this.investmentMap;
    }

    public Map<String, InvestmentData> loadInvestmentData(String filepath) {
        Map<String, InvestmentData> map = new HashMap<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(filepath)));
            JSONObject root = new JSONObject(content);

            for (String symbol : root.keySet()) {
                JSONObject obj = root.getJSONObject(symbol);

                String name = obj.getString("name");
                String type = obj.getString("type");

                List<PriceEntry> todayPrices = parsePriceArray(obj.getJSONArray("today_prices"), "time");
                List<PriceEntry> historicalPrices = parsePriceArray(obj.getJSONArray("historical_prices"), "date");
                List<PriceEntry> recentPrices = parsePriceArray(obj.getJSONArray("recent_prices"), "date");

                InvestmentData data = new InvestmentData(name, type, todayPrices, recentPrices, historicalPrices);
                map.put(symbol, data);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return map;
    }

    public void printInvestmentDataSummary() {
        for (Map.Entry<String, InvestmentData> entry : investmentMap.entrySet()) {
            String symbol = entry.getKey();
            InvestmentData data = entry.getValue();

            System.out.println("🔹 " + symbol + " (" + data.type + "):");
            System.out.println("  Recent Prices: " + (data.recentPrices != null ? data.recentPrices.size() : 0));
            System.out.println("  Historical Prices: " + (data.historicalPrices != null ? data.historicalPrices.size() : 0));
            System.out.println("  Today Prices: " + (data.todayPrices != null ? data.todayPrices.size() : 0));
            System.out.println();
        }
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
