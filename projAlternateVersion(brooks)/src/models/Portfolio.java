package models;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    // implements each Portfolio as an array list
    private List<Investment> investments = new ArrayList<>();

    public Portfolio() {}

    public void addInvestment(Investment investment) {
        this.investments.add(investment);
    }

    public void removeInvestment(String symbol) {
        for (int i = 0; i < investments.size(); ++i) {
            Investment investment = investments.get(i);
            System.out.println("SYMBOL: " + investment.getSymbol());

            if (investment.getSymbol().equals(symbol)) {
                investments.remove(investment);
                System.out.println("Investment successfully removed\n");
                return;
            }
        }

        //If function reaches this point, the investment does not exist
        System.out.println("Error locating inputted investment\n");
    }

    public void displayPortfolio() {
        System.out.println("Investments in this Portfolio:");
        for (int i = 0; i < investments.size(); ++i) {
            Investment current = investments.get(i);
            current.printInfo();
        }
    }

    public List<Investment> getInvestments() {
        return this.investments;
    }
}
