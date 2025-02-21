package data;

public class StockAPIClient {

    // @Purpose: Retrieve the most up to date price of the stock looked up
    // @param: Stock's name and symbol
    public static double getStockPrice(String symbol) {

        // NOTE: all this code is temporary for debugging
        char firstChar = symbol.charAt(0);
        firstChar += 32;    //add 32 to the char to make it lowercase

        if (firstChar >= 'a' && firstChar <= 'g') {
            return 210.25;
        }
        else if (firstChar >= 'h' && firstChar <= 'n') {
            return 26.0;
        }
        else if (firstChar >= 'o' && firstChar <= 's') {
            return 189.98;
        }
        else {
            return 95.39;
        }

        // pseudocode for data retrieval
        // APIKey.retrieveStockInfo(name, symbol)
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
