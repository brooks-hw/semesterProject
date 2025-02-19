package data;

import models.Investment;
import models.Portfolio;
import models.Stock;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class InvestmentDataStorage {

    public static void main(String[] args) {
        System.out.println("Hello");
    }

    //@Purpose: Reads and load each of the user's investments into the portfolio
    public static void initPortfolio(Portfolio portfolio) throws IOException {

        //Opens file userData.csv to parse each line
        try (BufferedReader reader = new BufferedReader(new FileReader("userData.csv"))) {
            String line;
            //Obtain next line from .csv file and send it to createInvestment() for investment instantiation
            while ((line = reader.readLine()) != null) {
                Investment investment = createInvestment(line);
                //Check if investment is properly formatted
                if (investment == null) {
                    System.err.println("Warning: invalid investment in userData.csv: " + line);
                    continue;       //Continue past the invalid line
                }
                else portfolio.addInvestment(investment);
            }
        }
        catch (IOException e) {
            throw new IOException("Error reading userData.csv: " + e.getMessage());
        }
    }

    // DATA FORMAT (in userData.csv) 1: String name, 2: String symbol, 3: double numShares, 4: double priceBought, 5: LocalDate dateBought, 6: LocalTime timeBought
    // @Purpose: Creates a new Investment object to be added to portfolio
    public static Investment createInvestment(String line) {
        int numVars = 6;       //There are 10 variables that hold information about the investment

        // Create array of parts, each are separated by a comma
        String[] parts = line.split(",");

        if (parts.length == numVars) {    //Ensure data is properly formatted
            //Information about the stock
            String name = parts[0];
            String symbol = parts[1];
            double price = StockAPIClient.getStockPrice(name, symbol);
            Stock stock = createStock(name, symbol, price);

            //Remaining info about the investment
            double numShares = Double.parseDouble(parts[2]);
            double priceBought = Double.parseDouble(parts[3]);
            double totalCost = numShares * priceBought;
            LocalDate dateBought = LocalDate.parse(parts[4]);
            LocalTime timeBought = LocalTime.parse(parts[5]);
            double currentValue = price * numShares;
            double percentChange = ((currentValue - priceBought) / priceBought) * 100;
            double profit = currentValue - totalCost;

            //Use constructor for loaded investments
            return new Investment(stock, numShares, priceBought, totalCost, dateBought, timeBought, currentValue, percentChange, profit);
        }

        //If the investment is formatted incorrectly, a nullptr is returned to signify error
        return null;
    }

    // @Purpose: Creates a new Stock object to be added to investment
    public static Stock createStock(String name, String symbol, double price) {
        return new Stock(name, symbol, price);
    }

    /*
    Dummy Methods for Generic File I/O
    public static void appendToFile(String filename, String data) {
        // Creates a PrintWriter object instantiated with a fileWriter to append lines to the .csv file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(data);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeToFile(String filename) {
        // Creates a BufferedWriter object instantiated with a fileWriter to write the complete .csv file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Test data3\n");
            writer.write("Test data4\n");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void readFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
     */

}
