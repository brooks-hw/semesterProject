package data;

import models.User;
import models.UserInvestment;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PortfolioManager {

    public static void loadFromTemplate(User user) {
        String profile = user.getRiskProfile();
        List<UserInvestment> template;

        switch (profile) {
            case "Conservative Investor":
                template = getConservativeTemplate();
                break;
            case "Balanced Investor":
                template = getBalancedTemplate();
                break;
            case "Aggressive Investor":
                template = getAggressiveTemplate();
                break;
            case "Speculative Investor":
                template = getSpeculativeTemplate();
                break;
            default:
                template = new ArrayList<>();
        }

        user.setPortfolio(template);
        saveToFile(user); // Save immediately after setting
    }

    public static void saveToFile(User user) {
        JSONArray jsonArray = new JSONArray();
        for (UserInvestment inv : user.getPortfolio()) {
            JSONObject obj = new JSONObject();
            obj.put("symbol", inv.symbol);
            obj.put("type", inv.type);
            obj.put("quantity", inv.quantity);
            obj.put("buyPrice", inv.buyPrice);
            jsonArray.put(obj);
        }

        String filename = "portfolios/" + user.getUsername() + "_portfolio.json";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(jsonArray.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<UserInvestment> loadFromFile(String username) {
        String filename = "portfolios/" + username + "_portfolio.json";
        File file = new File(filename);
        List<UserInvestment> portfolio = new ArrayList<>();

        if (!file.exists()) return portfolio;

        try (FileReader reader = new FileReader(filename)) {
            JSONArray array = new JSONArray(new JSONTokener(reader));
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String symbol = obj.getString("symbol");
                String type = obj.getString("type");
                double quantity = obj.getDouble("quantity");
                double buyPrice = obj.getDouble("buyPrice");
                portfolio.add(new UserInvestment(symbol, type, quantity, buyPrice, ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return portfolio;
    }

    // Templates with dummy quantities
    public static List<UserInvestment> getConservativeTemplate() {
        return List.of(
                new UserInvestment("US10Y", "Bond", 10, 100.0, ""),
                new UserInvestment("CORPAA", "Bond", 10, 102.5, ""),
                new UserInvestment("MUNI", "Bond", 10, 98.75, ""),
                new UserInvestment("US30Y", "Bond", 10, 99.25, ""),
                new UserInvestment("TIPS", "Bond", 10, 101.0, ""),
                new UserInvestment("JNJ", "Stock", 5, 160.0, ""),
                new UserInvestment("KO", "Stock", 5, 60.0, ""),
                new UserInvestment("BTC", "Crypto", 0.01, 94314.0, ""),
                new UserInvestment("ETH", "Crypto", 1, 1047.0, ""),
                new UserInvestment("CASH", "Cash", 1, 100.0, "")
        );
    }

    public static List<UserInvestment> getBalancedTemplate() {
        return List.of(
                new UserInvestment("AAPL", "Stock", 5, 180.0, ""),
                new UserInvestment("GOOGL", "Stock", 1, 2700.0, ""),
                new UserInvestment("MSFT", "Stock", 2, 320.0, ""),
                new UserInvestment("AVGO", "Stock", 1, 890.0, ""),
                new UserInvestment("CORPAA", "Bond", 5, 102.5, ""),
                new UserInvestment("US10Y", "Bond", 5, 100.0, ""),
                new UserInvestment("MUNI", "Bond", 5, 98.75, ""),
                new UserInvestment("BTC", "Crypto", 0.01, 94314.0, ""),
                new UserInvestment("ETH", "Crypto", 0.1, 1795.0, ""),
                new UserInvestment("CASH", "Cash", 1, 100.0, "")
        );
    }

    public static List<UserInvestment> getAggressiveTemplate() {
        return List.of(
                new UserInvestment("TSLA", "Stock", 3, 720.0, ""),
                new UserInvestment("AMZN", "Stock", 1, 3300.0, ""),
                new UserInvestment("META", "Stock", 4, 250.0, ""),
                new UserInvestment("NVDA", "Stock", 4, 290.0, ""),
                new UserInvestment("NFLX", "Stock", 2, 430.0, ""),
                new UserInvestment("GOOGL", "Stock", 1, 2700.0, ""),
                new UserInvestment("BTC", "Crypto", 0.01, 94314.0, ""),
                new UserInvestment("ETH", "Crypto", 0.1, 1795.0, ""),
                new UserInvestment("DOGE", "Crypto", 100, 0.17, ""),
                new UserInvestment("US30Y", "Bond", 2, 99.25, "")
        );
    }

    public static List<UserInvestment> getSpeculativeTemplate() {
        return List.of(
                new UserInvestment("PEPE", "Crypto", 10000, 0.0009, ""),
                new UserInvestment("DOGE", "Crypto", 500, 0.17, ""),
                new UserInvestment("LUNR", "Crypto", 1000, 0.0042, ""),
                new UserInvestment("X", "Crypto", 1000, 0.02, ""),
                new UserInvestment("VIBE", "Crypto", 100, 5.5, ""),
                new UserInvestment("ETH", "Crypto", 0.5, 1795.0, ""),
                new UserInvestment("BTC", "Crypto", 0.01, 94314.0, ""),
                new UserInvestment("TSLA", "Stock", 2, 720.0, ""),
                new UserInvestment("NVDA", "Stock", 3, 290.0, ""),
                new UserInvestment("US10Y", "Bond", 2, 100.0, "")
        );
    }
}
