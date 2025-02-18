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
}
