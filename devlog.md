**2/21/2025**

__Brooks:__
 * Finished logic for addInvestment() in which the investment being added is looked up via the stock symbol. The user is presented with the current price of the stock followed by being asked how many shares they’d like to purchase.
 * Added the removeInvestment() function that removes a given investment from the user’s portfolio.
The investment is looked up via the stock’s symbol.
 * Added a new function updateStockPrices() to InvestingApp.java that correctly updates and reflects the current values of the user’s stocks before displaying what their investments are worth.
The function uses StockAPIClient to dynamically update the prices before display
 * Added savePortfolio() that runs every time the program closes, this ensures that any changes made during runtime are accurately reflected.
