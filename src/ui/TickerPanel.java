package ui;

import data.StockAPIClient;
import models.StockData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TickerPanel extends JPanel {
    private ArrayList<String> symbols = new ArrayList<>();
    private String text;
    private StockAPIClient APIClient;

    private int xPosition;
    private final int scrollSpeed = 10; // Lower is faster
    private Timer timer;

    public TickerPanel() {
        initSymbols();
        setText();

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 30));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        xPosition = getWidth();
        timer = new Timer(scrollSpeed, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xPosition -= 2;

                // Reset xPosition *after* both instances have scrolled off
                int textWidth = getFontMetrics(getFont()).stringWidth(text);
                if (xPosition + textWidth * 2 < 0) {
                    xPosition = getWidth();
                }

                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        int textWidth = g.getFontMetrics().stringWidth(text);

        // Draw the main text
        g.drawString(text, xPosition, 20);

        // If the text has moved far enough to the left, draw a second instance to the right
        if (xPosition + textWidth < getWidth()) {
            g.drawString(text, xPosition + textWidth + 50, 20); // +50 = spacing between repeats
        }
    }


    public void initSymbols() {
        //List of all stock Symbols
        this.symbols.add("GOOGL");
        this.symbols.add("AAPL");
        this.symbols.add("AVGO");
        this.symbols.add("JPM");
        this.symbols.add("JNJ");
        this.symbols.add("MRK");
        this.symbols.add("UNH");
        this.symbols.add("MSFT");
        this.symbols.add("COST");
        this.symbols.add("TSLA");
        this.symbols.add("MA");
        this.symbols.add("V");
        this.symbols.add("LLY");
        this.symbols.add("NFLX");
        this.symbols.add("NVDA");
        this.symbols.add("META");
        this.symbols.add("KO");
        this.symbols.add("BAC");
        this.symbols.add("ORCL");
        this.symbols.add("ABBV");
        this.symbols.add("DIS");
        this.symbols.add("MCD");
        this.symbols.add("XOM");
        this.symbols.add("PG");
        this.symbols.add("ADBE");
        this.symbols.add("PEP");
        this.symbols.add("HD");
        this.symbols.add("AMZN");
        this.symbols.add("WMT");
        this.symbols.add("CRM");
    }

    public void setText() {
        this.text = "     ";

        //Stop one before to avoid strange spacing
        for (int i = 0; i < symbols.size() - 1; ++i) {
            //First add the symbol and a space for the price
            this.text += symbols.get(i);
            this.text += " - ";


            this.APIClient = new StockAPIClient();

            //Get the price of the current stock
            StockData currentData = APIClient.getStockData(symbols.get(i));
            double stockPrice = currentData.recentPrices.get(0).price;


            this.text = this.text + stockPrice + "  ";

            //Check if the stock has gone up or down since yesterday
            boolean goneUp = hasGoneUp(APIClient, symbols.get(i));
            if(goneUp) {
                this.text += " ^    ";
            }
            else {
                this.text += " v    ";
            }

        }

        xPosition = getWidth(); // Reset position on new text
    }

    public boolean hasGoneUp(StockAPIClient APIClient, String symbol) {
        StockData currentData = APIClient.getStockData(symbol);
        //Get today's and yesterday's prices
        double currentPrice = currentData.recentPrices.get(0).price;
        double prevPrice = currentData.recentPrices.get(1).price;

        //Return true if price has gone up
        if (currentPrice > prevPrice) return true;
        else return false;
    }

}
