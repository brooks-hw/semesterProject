package ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;

public class PieChartGenerator {
    public JPanel createPieChart(String title){
        // Create dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Stocks", 40);
        dataset.setValue("Bonds", 20);
        dataset.setValue("Crypto", 25);
        dataset.setValue("Cash", 15);

        // Create chart
        JFreeChart pieChart = ChartFactory.createPieChart(
                title,
                dataset,
                true, true, false);

        // Create Panel and return
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
        return chartPanel;
    }
}
