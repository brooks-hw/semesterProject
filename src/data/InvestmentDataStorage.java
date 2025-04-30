package data;

public class InvestmentDataStorage {

    /*
    StockAPIClient APIClient;

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
        int numVars = 6;       //There are 6 variables that hold information about the investment

        // Create array of parts, each are separated by a comma
        String[] parts = line.split(",");

        //Ensure data is properly formatted
        if (parts.length != 6) {
            System.err.println("Invalid data format: " + line);
            return null;
        }

        try {
                //Information about the stock
                String name = parts[0];
                String symbol = parts[1];
                double price = data.StockAPIClient.getStockPrice(symbol);
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
        } catch (Exception e) {
            System.err.println("Error processing line: " + line);
            //If the investment is formatted incorrectly, a nullptr is returned to signify error
            return null;
        }
        //Catches invalid numbers in numShares and priceBought fields.
        //Prints an error message with the exact invalid line.
        //Prevents program crashes from NumberFormatException.
        //Keeps everything else unchanged for simplicity.
    }

    // @Purpose: Convert a String into a double while handling invalid number formats.
    private static double parseDouble(String value, String line) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in: " + line);
            return -1; // Return -1 to indicate an error
        }
    }

    // @Purpose: Creates a new Stock object to be added to investment
    public static Stock createStock(String name, String symbol, double price) {
        return new Stock(name, symbol, price);
    }

    //DATA FORMAT (in userData.csv) 1: String name, 2: String symbol, 3: double numShares, 4: double priceBought, 5: LocalDate dateBought, 6: LocalTime timeBought
    // @Purpose: take all the current investments in the portfolio and write them to userData.csv
    public static void savePortfolio(Portfolio portfolio) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userData.csv"))) {

            List<Investment> investments = portfolio.getInvestments();
            for (int i = 0; i < investments.size(); ++i) {
                Investment investment = investments.get(i);
                String line = (investment.getName() + "," + investment.getSymbol() + "," + investment.getNumShares() + "," + investment.getPriceBought() + ","
                    + investment.getDateBought() + "," + investment.getTimeBought());

                //Add a newline character to each line of data except the last
                if (i != investments.size() - 1) {
                    line += "\n";
                }
                writer.write(line);
            }

        }
        catch (IOException e) {
            throw new IOException("Error reading userData.csv: " + e.getMessage());
        }
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
