package ui;

import data.StockAPIClient;

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
        this.symbols.add("MSFT");
        this.symbols.add("GOOG");
        this.symbols.add("NVDA");
        this.symbols.add("TSLA");
        this.symbols.add("AAPL");
        this.symbols.add("AMZN");
        this.symbols.add("SNAP");
        this.symbols.add("META");
        this.symbols.add("NFLX");
        this.symbols.add("DIS");
        this.symbols.add("SBUX");
        this.symbols.add("BABA");
    }

    public void setText() {
        this.text = "     ";

        //Stop one before to avoid strange spacing
        for (int i = 0; i < symbols.size() - 1; ++i) {
            //First add the symbol and a space for the price
            this.text += symbols.get(i);
            this.text += "   ";

            //Get the price of the current stock
            double stockPrice = APIClient.getStockPrice(symbols.get(i));
            this.text = this.text + stockPrice + "  ";

            //Check if the stock has gone up or down since yesterday
            if(APIClient.getStockChange(symbols.get(i))) {
                this.text += " ^    ";
            }
            else {
                this.text += " v    ";
            }

        }

        xPosition = getWidth(); // Reset position on new text
    }



}
