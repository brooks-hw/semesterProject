package data;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APITest {

    private static final String API_KEY = "LF33F4KXJFDIH9VT"; // <-- replace with your key

    public static void main(String[] args) {
        String symbol = "TSLA"; // example: Apple
        String function = "TIME_SERIES_DAILY"; // daily time series
        String outputSize = "compact"; // "compact" = last 100 data points, "full" = full history

        try {
            String urlString = "https://www.alphavantage.co/query"
                    + "?function=" + function
                    + "&symbol=" + symbol
                    + "&outputsize=" + outputSize
                    + "&apikey=" + API_KEY;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String jsonResponse = response.toString();
                JSONObject json = new JSONObject(jsonResponse);

                // Get the "Time Series (Daily)" object
                JSONObject timeSeries = json.getJSONObject("Time Series (Daily)");

                // Iterate through the dates
                for (String date : timeSeries.keySet()) {
                    JSONObject dayData = timeSeries.getJSONObject(date);
                    double open = dayData.getDouble("1. open");
                    double close = dayData.getDouble("4. close");
                    System.out.println(date + ": Open=" + open + ", Close=" + close);
                }

                // Print raw JSON data
                System.out.println(response.toString());
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
