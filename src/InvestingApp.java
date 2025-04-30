// This class should handle the event loop
public class InvestingApp {

    /*
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
            System.err.println("Error loading portfolio data: " + e.getMessage());
        }
    }

    void savePortfolio() {
        try {
            InvestmentDataStorage.savePortfolio(this.portfolio);
        }
        catch (Exception e) {
            System.err.println("Error saving portfolio data: " + e.getMessage());
        }
    }

    // @Purpose: add an investment to the user's portfolio, does so via the stock symbol
    void addInvestment() {
        Scanner scanner = new Scanner(System.in);

        //Retrieve the name and currentPrice of the stock based on its symbol
        // TODO: this logic will stay the same even when integrated with the UI
        //  we only need to update how we retrieve the symbol
        System.out.println("Add investment\n\nWhat is the symbol of the stock to purchase?\nEnter: ");
        String symbol = scanner.nextLine();
        String name = StockAPIClient.nameFromSym(symbol);
        double currentPrice = StockAPIClient.getStockPrice(symbol);

        //Inform user of the trading price and prompt how many shares to purchase
        System.out.println("Stock: " + name + " is currently trading at: " + currentPrice);
        System.out.println("How many shares would you like to purchase?\nEnter: ");
        double numShares = scanner.nextDouble();

        //Create a new Stock and subsequent Investment with the gathered information
        Stock newStock = new Stock(name, symbol, currentPrice);
        Investment newInvestment = new Investment(newStock, numShares, newStock.getPrice(), LocalDate.now(), LocalTime.now());

        //Add the Investment to the user's portfolio
        this.portfolio.addInvestment(newInvestment);
    }

    void updateStockPrices(List<Investment> investments) {
        for (int i = 0; i < investments.size(); ++i) {
            //Get current investment object
            Investment investment = investments.get(i);

            //Use StockAPIClient to get the most up-to-date price
            String symbol = investment.getSymbol();
            double updatedPrice = StockAPIClient.getStockPrice(symbol);

            investment.updateStockPrice(updatedPrice);
        }
    }

    // @Purpose: remove an investment from the user's portfolio
    // TODO: This logic also stays the same regardless of how the UI is implemented
    //          we only need to update how the symbol is retrieved
    void removeInvestment() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Remove investment\nWhat is the symbol of the stock you want to remove?\n");
        String symbol = scanner.nextLine();

        this.portfolio.removeInvestment(symbol);

    }

    //Purpose: display all the investments in the user's current portfolio
    // TODO: add error handling for updateStockPrices()
    void displayPortfolio() {
        System.out.println("Display Portfolio\n");

        //Ensure all stock prices are up-to-date before displaying to the user
        updateStockPrices(this.portfolio.getInvestments());

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


        /*
        //Create an instance of the app to run
        InvestingApp investingApp = new InvestingApp();

        //Load the user's portfolio into given app instance
        investingApp.loadPortfolio();

        //Run the event loop of the program
        investingApp.run();

        //Save the contents of the current portfolio before exiting
        investingApp.savePortfolio();

    } */
}
