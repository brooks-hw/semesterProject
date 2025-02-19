package data;

import models.Investment;
import models.Portfolio;

import java.io.*;

public class InvestmentDataStorage {

    public static void main(String[] args) {
        System.out.println("Hello");
    }

    //@Purpose: Reads and load each of the user's investments into the portfolio
    public static void initPortfolio(Portfolio portfolio) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader("userData.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                // Investment investment = createInvestment(line);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Stock stock,double numShares,double priceBought,double totalCost,LocalDate dateBought,double currentValue,double percentChange,double profit
    public static Investment createInvestment(String line) {
        int numVars = 8;

        // Create array of parts, each are separated by a comma
        String[] parts = line.split(",");

        return new Investment();
    }

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

    // DATA FORMAT:
    // Stock stock,double numShares,double priceBought,double totalCost,LocalDate dateBought,double currentValue,double percentChange,double profit
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
}
