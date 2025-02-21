import data.InvestmentDataStorage;
import data.StockAPIClient;
import models.Investment;
import models.Portfolio;
import models.Stock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

// This class should handle the event loop
public class InvestingApp {

    Portfolio portfolio;       //represents user portfolio
    // InvestmentDataStorage dataStore;      //object to deal with loading/storing data
    StockAPIClient stockClient;     //client to deal with stock price functionality


    // @Purpose: Constructor, instantiates necessary objects
    public InvestingApp() {
        this.portfolio = new Portfolio();
        // this.dataStore = new InvestmentDataStorage();
        this.stockClient = new StockAPIClient();
    }

    // @Purpose: Load portfolio from storage
    void loadPortfolio() {
        try {
            //Initialize the user's portfolio from the userData.csv file
            InvestmentDataStorage.initPortfolio(this.portfolio);
        }
        catch (Exception e) {
            System.err.println("Error loading portfolio: " + e.getMessage());
        }
    }

    void savePortfolio() {
        try {
            InvestmentDataStorage.savePortfolio(this.portfolio);
        }
        catch (Exception e) {
            System.err.println("Error loading portfolio: " + e.getMessage());
        }
    }

    void addInvestment() {
        System.out.println("Add investment\n");
        Stock newStock = new Stock("Oracle", "ORCL", 179.9);
        Investment newInvestment = new Investment(newStock, 100, newStock.getPrice(), LocalDate.now(), LocalTime.now());

        //Add the newly created investment to the user's portfolio
        this.portfolio.addInvestment(newInvestment);
    }
    void removeInvestment() {
        System.out.println("Remove investment\n");
    }
    void updateStockPrices() {}

    void displayPortfolio() {
        System.out.println("Display Portfolio\n");
        portfolio.displayPortfolio();
    }


    // Main event loop
    void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while(isRunning) {
            System.out.println("Which option would you like");
            System.out.println("----------------------------");
            displayMenu();

            int choice = scanner.nextInt();
            switch (choice) {
                //Option to exit program
                case 0: {
                    System.out.println("Exiting program...");
                    isRunning = false;
                    break;
                }
                case 1: {
                    addInvestment();
                    break;
                }
                case 2: {
                    removeInvestment();
                    break;
                }
                case 3: {
                    displayPortfolio();
                    break;
                }
            }
        }
    }

    // Function to unload assets and exit
    void cleanup() {}

    void displayMenu() {
        System.out.println("1. Add an investment");
        System.out.println("2. Remove an investment");
        System.out.println("3. Display portfolio");
        System.out.println("0. Exit the program");
        System.out.println("Enter: ");

    }

    public static void main(String[] args) {
        //Create an instance of the app to run
        InvestingApp investingApp = new InvestingApp();

        //Load the user's portfolio into given app instance
        investingApp.loadPortfolio();

        //Run the event loop of the program
        investingApp.run();

        //Save the contents of the current portfolio before exiting
        investingApp.savePortfolio();
    }
}
