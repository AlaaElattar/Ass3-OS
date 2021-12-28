import javax.swing.*;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

import java.awt.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GanttChart extends JFrame
{
    GanttChart(String title, ArrayList<Process> p)
    {
        super(title);

        IntervalCategoryDataset dataSet = getCategoryDataset(p);

        JFreeChart chart = ChartFactory.createGanttChart(
                ("Gantt Chart " + title), // Chart title
                "Process", // X-Axis Label
                "Time(ms)", // Y-Axis Label
                dataSet);

        CategoryPlot plot = chart.getCategoryPlot();
        CategoryItemRenderer renderer = plot.getRenderer();
        for(int i =0; i<dataSet.getRowCount(); i++){
            Color color;
            try {
                Field field = Class.forName("java.awt.Color").getField(p.get(i).getColor());
                color = (Color)field.get(null);
            } catch (Exception e) {
                color = null; // Not defined
            }
            renderer.setSeriesPaint(i, color);
        }
        DateAxis axis = (DateAxis) plot.getRangeAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("SS"));

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);

        SwingUtilities.invokeLater(() -> {
            this.setSize(800, 600);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setVisible(true);
        });
    }

    private IntervalCategoryDataset getCategoryDataset(ArrayList<Process> q)
    {
        TaskSeriesCollection dataSet = new TaskSeriesCollection();

        for (int i = 0; i < q.size(); i++)
        {
            final TaskSeries series = new TaskSeries(q.get(i).getName());
            Process p = q.get(i);
            final Task t = new Task(p.getName(), new SimpleTimePeriod(p.start.get(0), p.end.get(p.end.size()-1)));

            for(int j = 0; j < q.get(i).start.size(); j = j + 1)
            {
                final Task subTask = new Task(String.valueOf(j), new SimpleTimePeriod(q.get(i).start.get(j), q.get(i).end.get(j )));
                t.addSubtask(subTask);
            }

            series.add(t);
            dataSet.add(series);
        }
        return dataSet;
    }
}