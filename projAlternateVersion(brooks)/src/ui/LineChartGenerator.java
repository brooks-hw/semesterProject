package ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class LineChartGenerator {

    public JPanel createLineChart(List<Double> prices) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int hour = 0; hour < prices.size(); hour++) {
            String time = hour + ":00";
            dataset.addValue(prices.get(hour), "Stock Price", time);
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "24-Hour Stock Price",
                "Hour",
                "Price ($)",
                dataset
        );

        return new ChartPanel(lineChart);
    }
}
