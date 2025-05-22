package Main;

import Entities.Query_Entity;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class test {

    private XYSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public test() {
        dataset = new XYSeriesCollection();
        chart = ChartFactory.createXYLineChart(
                "Medicine Used Stock Over Time",  // Chart title
                "Update Date",  // X-axis label
                "Used Stock",  // Y-axis label
                dataset,  // Dataset to use
                PlotOrientation.VERTICAL,
                true, true, false
        );
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
    }

    public JPanel plotMedicineStockOverTime(ArrayList<ArrayList<Query_Entity>> data) {
        // Iterate through each group (ArrayList<Query_Entity>) and plot it
        int groupIndex = 0;
        for (ArrayList<Query_Entity> group : data) {
            XYSeries series = new XYSeries("Group " + groupIndex);

            for (Query_Entity q : group) {
                // Extract LastUpdated as date
                String dateStr = q.getLastUpdated();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Format of date in the entity
                Date date = null;
                try {
                    date = sdf.parse(dateStr);  // Parse the date string into a Date object
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Use the date object as the X value, and the UsedStock as the Y value
                if (date != null) {
                    series.add(date.getTime(), q.getUsedStock());  // Add data point to the series
                }
            }

            dataset.addSeries(series);  // Add the series to the dataset
            groupIndex++;  // Increment for next group
        }

        // Create panel to hold chart and return it
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        // Dummy data to test the chart with ArrayList of ArrayList
        ArrayList<Query_Entity> group1 = new ArrayList<>();
        group1.add(new Query_Entity(1, "Medicine A", 100, 120, "2022-01-01"));
        group1.add(new Query_Entity(1, "Medicine A", 110, 130, "2022-02-01"));

        ArrayList<Query_Entity> group2 = new ArrayList<>();
        group2.add(new Query_Entity(2, "Medicine B", 150, 170, "2022-01-01"));
        group2.add(new Query_Entity(2, "Medicine B", 160, 180, "2022-02-01"));

        ArrayList<ArrayList<Query_Entity>> allGroups = new ArrayList<>();
        allGroups.add(group1);
        allGroups.add(group2);

        // Create the plotter
        test plotter = new test();
        JPanel panel = plotter.plotMedicineStockOverTime(allGroups);

        // Display in JFrame
        JFrame frame = new JFrame("Chart with ArrayList of ArrayLists");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
