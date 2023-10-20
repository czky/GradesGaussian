import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.function.Function2D;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public class GaussianCurve {

    public static void main(String[] args) {
        // Prompt user for grades input
        String input = JOptionPane.showInputDialog("Enter grades separated by commas (e.g., 70.0, 75.5, 80.0):");

        // Parse user input into a list of grades
        List<Double> grades = new ArrayList<>();
        String[] gradeStrings = input.split(",");
        for (String gradeString : gradeStrings) {
            try {
                double grade = Double.parseDouble(gradeString.trim());
                grades.add(grade);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numeric grades.");
                return;
            }
        }

        // Calculate mean and standard deviation from grades
        double mean = calculateMean(grades);
        double standardDeviation = Math.sqrt(grades.stream().mapToDouble(grade -> Math.pow(grade - mean, 2)).sum() / grades.size());

        // Create a dataset for the Gaussian curve
        Function2D normalDistribution = new NormalDistributionFunction2D(mean, standardDeviation);
        XYSeries series = new XYSeries("Gaussian Curve");
        for (double x = 60; x <= 100; x += 0.1) { // Grades from 60 to 100
            series.add(x, normalDistribution.getValue(x));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Create a chart based on the dataset
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Gaussian Curve", // Chart title
                "Grades", // X-axis label
                "Y", // Y-axis label
                dataset, // Dataset
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Set chart background color
        chart.setBackgroundPaint(Color.white);

        // Customize the plot to display the curve
        XYPlot plot = chart.getXYPlot();
        plot.getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        plot.setDomainPannable(true);
        plot.setRangePannable(true);

        // Create a panel to display the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        // Create and configure the frame to display the chart
        JFrame frame = new JFrame("Gaussian Bell Curve");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    // Calculate mean of a list of numbers
    private static double calculateMean(List<Double> numbers) {
        double sum = 0;
        for (Double number : numbers) {
            sum += number;
        }
        return sum / numbers.size();
    }
}
