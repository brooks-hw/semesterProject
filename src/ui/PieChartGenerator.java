package ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PiePlot;

import javax.swing.*;
import java.awt.*;

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
                false, true, false);

        // Create Panel and return
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 450));
        pieChart.setBackgroundPaint(new Color(0, 0, 0, 0));
        pieChart.getPlot().setBackgroundPaint(new Color(0, 0, 0, 0));
        pieChart.getPlot().setOutlineVisible(false);

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 18));
        plot.setLabelBackgroundPaint(new Color(255, 140, 0));
        plot.setLabelPaint(Color.BLACK);

        return chartPanel;
    }

}
