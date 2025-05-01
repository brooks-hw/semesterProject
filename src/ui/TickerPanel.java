package ui;

import data.StockAPIClient;
import models.InvestmentData;

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
    private Timer updateTimer;

    public TickerPanel(StockAPIClient APIClient) {
        this.APIClient = APIClient;
        initSymbols();
        setText(APIClient);

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
        this.symbols.add("AAPL");
        this.symbols.add("MSFT");
        this.symbols.add("GOOGL");
        this.symbols.add("TSLA");
        this.symbols.add("JPM");
        this.symbols.add("LLY");
        this.symbols.add("NFLX");
        this.symbols.add("ABBV");
        this.symbols.add("PEP");
    }

    public void setText(StockAPIClient APIClient) {
        this.text = "     ";

        //Stop one before to avoid strange spacing
        for (int i = 0; i < symbols.size(); ++i) {
            //First add the symbol and a space for the price
            this.text += symbols.get(i);
            this.text += " - ";

            //Get the price of the current stock
            InvestmentData currentData = APIClient.getDataFromSymbol(symbols.get(i));
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
        InvestmentData currentData = APIClient.getDataFromSymbol(symbol);
        //Get today's and yesterday's prices
        double currentPrice = currentData.recentPrices.get(0).price;
        double prevPrice = currentData.recentPrices.get(1).price;

        //Return true if price has gone up
        if (currentPrice > prevPrice) return true;
        else return false;
    }

    public void stop() {
        if (updateTimer != null) {
            updateTimer.stop();
        }
    }
}
