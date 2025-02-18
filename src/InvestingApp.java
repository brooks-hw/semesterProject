import data.InvestmentDataStorage;
import data.StockAPIClient;
import models.Portfolio;

import java.util.Scanner;

// This class should handle the event loop
public class InvestingApp {

    Portfolio portfolio;       //represents user portfolio
    InvestmentDataStorage dataStore;      //object to deal with loading/storing data
    StockAPIClient stockClient;     //client to deal with stock price functionality

    // Load portfolio from storage
    void loadPortfolio() {}

    void savePortfolio() {}


    void addInvestment() {}
    void removeInvestment() {}
    void updateStockPrices() {}
    void displayPortfolio() {}


    // Main event loop
    void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Which option would you like");
        displayMenu();

        int choice = scanner.nextInt();
        switch (choice) {
            case 0: break;
            case 1: addInvestment();
            case 2: removeInvestment();
            case 3: displayPortfolio();
        }
    }

    // Function to unload assets and exit
    void cleanup() {}

    void displayMenu() {
        System.out.println("1. Add an investment");
        System.out.println("2. Remove an investment");
        System.out.println("3. Display portfolio");
        System.out.println("Enter: ");

    }

    public void main(String[] args) {

    }
}
