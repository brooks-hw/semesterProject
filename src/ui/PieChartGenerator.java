package ui;

/*
used ChatGPT assistance to develop pieChart specifications
ChatGPT assistance in developing riskProfile cases
ChatGPT recommended data variation
 */

import models.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class PieChartGenerator {

    public JPanel createPieChart(String title) {
        String riskProfile = User.getInstance().getRiskProfile();  // Make sure riskProfile is set

        DefaultPieDataset dataset = getDatasetForProfile(riskProfile);

        JFreeChart pieChart = ChartFactory.createPieChart(
                title,
                dataset,
                false, true, false);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(400, 450));

        pieChart.setBackgroundPaint(new Color(0, 0, 0, 0));
        pieChart.getPlot().setBackgroundPaint(new Color(0, 0, 0, 0));
        pieChart.getPlot().setOutlineVisible(false);

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 18));
        plot.setLabelBackgroundPaint(new Color(255, 140, 0));
        plot.setLabelPaint(Color.BLACK);

        return chartPanel;
    }

    private DefaultPieDataset getDatasetForProfile(String riskProfile) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        switch (riskProfile) {
            case "Conservative Investor":
                dataset.setValue("Bonds", 50);
                dataset.setValue("Cash", 30);
                dataset.setValue("Stocks", 15);
                dataset.setValue("Crypto", 5);
                break;
            case "Balanced Investor":
                dataset.setValue("Stocks", 40);
                dataset.setValue("Bonds", 30);
                dataset.setValue("Crypto", 15);
                dataset.setValue("Cash", 15);
                break;
            case "Aggressive Investor":
                dataset.setValue("Stocks", 60);
                dataset.setValue("Crypto", 30);
                dataset.setValue("Bonds", 5);
                dataset.setValue("Cash", 5);
                break;
            case "Speculative Investor":
                dataset.setValue("Crypto", 50);
                dataset.setValue("Stocks", 40);
                dataset.setValue("Bonds", 5);
                dataset.setValue("Cash", 5);
                break;
            default:
                dataset.setValue("Stocks", 40);
                dataset.setValue("Bonds", 20);
                dataset.setValue("Crypto", 25);
                dataset.setValue("Cash", 15);
                break;
        }

        return dataset;
    }
}