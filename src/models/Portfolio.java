package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    // implements each Portfolio as an array list
    private List<Investment> investments = new ArrayList<>();

    public Portfolio() {}

    public void addInvestment(Investment investment) {
        this.investments.add(investment);
    }

    public void removeInvestment(Investment investment) {
        this.investments.remove(investment);
    }

    public void displayPortfolio() {
        System.out.println("Investments in this Portfolio:");
        for (int i = 0; i < investments.size(); ++i) {
            Investment current = investments.get(i);
            current.printInfo();
        }
    }

    // CURRENT ENTRY POINT
    public static void main(String[] args) {
        Portfolio myPort = new Portfolio();

        Stock tesla = new Stock("Tesla Inc.", "TSLA", 173.95);
        Stock zayas = new Stock("Zayas inc.", "ZYAS", 343.24);

        Investment investment1 = new Investment(tesla, 12, tesla.getPrice(), LocalDate.now());
        Investment investment2 = new Investment(zayas, 100, zayas.getPrice(), LocalDate.now());

        myPort.addInvestment(investment1);
        myPort.addInvestment(investment2);

        myPort.displayPortfolio();

        myPort.removeInvestment(investment2);
        myPort.displayPortfolio();
    }
}
