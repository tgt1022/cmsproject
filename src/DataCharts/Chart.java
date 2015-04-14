/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataCharts;


import Data.MonthData;
import Data.Location;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class Chart {    

    private Canvas canvas;

    public Canvas getCanvas(ArrayList<Location> locs, String type) {
        try {
            if(locs != null || locs.isEmpty()) {
                switch (type) {
                    case "line":
                        this.canvas = new ChartCanvas(new LineGraph().createChart(createXYDataset(locs)));
                        break;
                    case "pie":
                        this.canvas = new ChartCanvas(new PieGraph().createChart(createPieDataset(locs)));
                        break;
                    case "bar":
                        this.canvas = new ChartCanvas(new BarGraph().createChart(createCategoryDataset(locs)));
                        break;
                    default:
                        this.canvas = new ChartCanvas(new BarGraph().createChart(createCategoryDataset(locs)));
                        break;
                }
            }
            else
            {
                this.canvas = new FailedChart();
            }
        } catch(Exception ex) {
            this.canvas = new FailedChart();
            Logger.getLogger(Chart.class.getName()).log(Level.SEVERE, null, ex);
        }    
            canvas.setHeight(400);
            canvas.setWidth(600);
            canvas.getWidth();
            return canvas;
        
    }
    public Canvas getCanvas() {
        return canvas;
    }

    static class ChartCanvas extends Canvas { 
 
        JFreeChart chart;    
        
        private FXGraphics2D g2;
        
        public ChartCanvas(JFreeChart chart) {
            this.chart = chart;
            this.g2 = new FXGraphics2D(getGraphicsContext2D());
            // Redraw canvas when size changes. 
            widthProperty().addListener(evt -> draw()); 
            heightProperty().addListener(evt -> draw()); 
        }  
        
        private void draw() { 
            double width = getWidth(); 
            double height = getHeight();
            getGraphicsContext2D().clearRect(0, 0, width, height);
            this.chart.draw(this.g2, new Rectangle2D.Double(0, 0, width, 
                    height));
        } 
        
        @Override 
        public boolean isResizable() { 
            return true;
        }  
        
        @Override 
        public double prefWidth(double height) { return getWidth(); }  
        
        @Override 
        public double prefHeight(double width) { return getHeight(); } 
    }

    /**
     * Creates a dataset, consisting customers per area code
     * Methods are for display purposes at this point and may be refactored and 
     * modified based on necessity
     */
    private static XYDataset createXYDataset(ArrayList<Location> locs) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for(int x = 0; x < locs.size(); x++) {
            TimeSeries time = new TimeSeries("" + locs.get(x).getAreaCode());
            ArrayList<MonthData> months = locs.get(x).getMonths();
            for(int y =0; y < months.size(); y++) {
                time.add(months.get(y).getMonth(), months.get(y).getNumCustomers());
            }
        dataset.addSeries(time);
        }
        return dataset;
    }

    private static PieDataset createPieDataset(ArrayList<Location> locs) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for(int x = 0; x < locs.size(); x++) {         
            dataset.setValue("" +locs.get(x).getAreaCode(), locs.get(x).getCustomers());
        }
        return dataset;
    }
    
    private CategoryDataset createCategoryDataset(ArrayList<Location> locs) {
       
        final String series2 = "Technicians";
        final String series1 = "Customers";

        ArrayList<String> category = new ArrayList<String>();
        for(int x = 0; x < locs.size(); x++) {         
            category.add("" + locs.get(x).getAreaCode());
        }
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for(int x = 0; x < category.size(); x++) {         
            dataset.addValue(locs.get(x).getCustomers(), series1, category.get(x));
            dataset.addValue(locs.get(x).getWorkers().size(), series2, category.get(x));
        }  
        return dataset;
        
    }
}
