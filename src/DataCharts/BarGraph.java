/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataCharts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;


/**
 *
 * Customization of Bar Graph goes here
 */
public class BarGraph {
    public static JFreeChart createChart(CategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createBarChart( "Customers in 2015", 
            "Month", "Amount", dataset, PlotOrientation.VERTICAL,
                true, true, false 
        );
        return chart;
    }

}
