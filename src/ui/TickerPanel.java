/*
 * Some portions of this file were generated with the assistance of ChatGPT.
 * we used ChatGPT to help with refinement, debugging, and specific enhancements such as [e.g., scrolling behavior, repaint logic, UI layout, etc.].
 * The original prompt used for assistance was: "Can you help me write a Java Swing panel that scrolls stock ticker text horizontally?"
 * we then modified and integrated the generated code to fit the needs of the overall project.
 */

package ui;

import data.StockAPIClient;
import models.InvestmentData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// TickerPanel is a custom JPanel that displays a scrolling ticker of stock prices.
public class TickerPanel extends JPanel {
    private ArrayList<String> symbols = new ArrayList<>(); // List of stock symbols to display
    private String text; // The text string shown in the ticker
    private StockAPIClient APIClient; // Client used to fetch stock price data

    private int xPosition; // X-coordinate for the scrolling text
    private final int scrollSpeed = 10; // Timer delay; smaller = faster scroll
    private Timer timer; // Controls the animation (scrolling)
    private Timer updateTimer; // (Optional) Could be used to periodically refresh prices

    // Constructor initializes the panel, loads data, and starts the animation
    public TickerPanel(StockAPIClient APIClient) {
        this.APIClient = APIClient;
        initSymbols();           // Load stock symbols
        setText(APIClient);      // Generate the text string to display

        // UI settings for the ticker panel
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 30));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        xPosition = getWidth();  // Start ticker text just off the right edge

        // Timer to scroll the ticker text from right to left
        timer = new Timer(scrollSpeed, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xPosition -= 2; // Move text left

                int textWidth = getFontMetrics(getFont()).stringWidth(text);
                // If both text copies are fully off-screen, reset to start position
                if (xPosition + textWidth * 2 < 0) {
                    xPosition = getWidth();
                }

                repaint(); // Request the panel to repaint with updated position
            }
        });
        timer.start(); // Start scrolling
    }

    // Custom drawing logic for ticker text
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        int textWidth = g.getFontMetrics().stringWidth(text);

        // Draw the primary text string
        g.drawString(text, xPosition, 20);

        // If the first instance is mostly off-screen, draw another after it for seamless looping
        if (xPosition + textWidth < getWidth()) {
            g.drawString(text, xPosition + textWidth + 50, 20); // Add a gap between loops
        }
    }

    // Hardcoded list of popular stock tickers
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

    // Builds the ticker text string with stock prices and up/down indicators
    public void setText(StockAPIClient APIClient) {
        this.text = "     "; // Padding before first ticker entry

        for (int i = 0; i < symbols.size(); ++i) {
            this.text += symbols.get(i) + " - ";

            // Get current price
            InvestmentData currentData = APIClient.getDataFromSymbol(symbols.get(i));
            double stockPrice = currentData.recentPrices.get(0).price;

            // Append price to ticker
            this.text = this.text + stockPrice + "  ";

            // Append ^ or v depending on whether price went up or down
            boolean goneUp = hasGoneUp(APIClient, symbols.get(i));
            if (goneUp) {
                this.text += " ^    ";
            } else {
                this.text += " v    ";
            }
        }

        xPosition = getWidth(); // Reset position when text is updated
    }

    // Helper method to determine if a stock has increased in price since yesterday
    public boolean hasGoneUp(StockAPIClient APIClient, String symbol) {
        InvestmentData currentData = APIClient.getDataFromSymbol(symbol);
        double currentPrice = currentData.recentPrices.get(0).price;
        double prevPrice = currentData.recentPrices.get(1).price;

        return currentPrice > prevPrice;
    }

    // Stops the update timer (currently unused but useful if implemented later)
    public void stop() {
        if (updateTimer != null) {
            updateTimer.stop();
        }
    }
}
