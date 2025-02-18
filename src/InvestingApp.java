/*
Core functionality (app should be able to):
- Load the user's portfolio
- Save the user's portfolio after changes are made
- Add/Remove investments from the current portfolio
- Update stock prices periodically using StockAPIClient
- Display portfolio information and statistics
 */

import data.InvestmentDataStorage;
import data.StockAPIClient;
import models.Portfolio;
import models.Stock;

// This class should handle the event loop
public class InvestingApp {

    Portfolio portfolio;       //represents user portfolio
    InvestmentDataStorage dataStore;      //object to deal with loading/storing data
    StockAPIClient stockClient;     //client to deal with stock price functionality

    // Load portfolio from storage
    void loadPortfolio() {}

    void savePortfolio() {}


    void addInvestment(Stock stock, double shares, double price) {}
    void removeInvestment(String stockSymbol) {}
    void updateStockPrices() {}
    void displayPortfolio() {}


    // Main event loop
    void run() {}

    // Function to unload assets and exit
    void cleanup() {}

    public static void main(String[] args) {

    }
}
