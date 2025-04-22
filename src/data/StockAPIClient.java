package data;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StockAPIClient {

    private static final String API_KEY = "";
    private static final String BASE_URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE";

    public static double getStockPrice(String symbol) {
        try {
            JSONObject quote = fetchQuote(symbol);
            return Double.parseDouble(quote.getString("05. price"));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean getStockChange(String symbol) {
        try {
            JSONObject quote = fetchQuote(symbol);
            double price = Double.parseDouble(quote.getString("05. price"));
            double previousClose = Double.parseDouble(quote.getString("08. previous close"));
            return price >= previousClose;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // default to down if error
        }
    }
    private static JSONObject fetchQuote(String symbol) throws Exception {
        String urlStr = BASE_URL + "&symbol=" + symbol + "&apikey=" + API_KEY;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());
        if (json.has("Global Quote")) {
            return json.getJSONObject("Global Quote");
        } else {
            throw new Exception("Invalid API response: " + json.toString());
        }
    }




    // TODO: integrate with actual API for real lookups
    public static String nameFromSym(String symbol) {

        // NOTE: all this code is temporary for debugging
        char firstChar = symbol.charAt(0);
        firstChar += 32;    //add 32 to the char to make it lowercase

        if (firstChar >= 'a' && firstChar <= 'g') {
            return "stockName1";
        }
        else if (firstChar >= 'h' && firstChar <= 'n') {
            return "stockName2";
        }
        else if (firstChar >= 'o' && firstChar <= 's') {
            return "stockName3";
        }
        else {
            return "stockName4";
        }

        // pseudocode to lookup the stock name from symbol
        // String stockName = APIKey.getStockName(symbol);
        // return stockName;
    }

    public static void main(String[] args) {

    }
}
