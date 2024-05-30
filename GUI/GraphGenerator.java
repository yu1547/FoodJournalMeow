import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.*;

import org.apache.poi.ss.usermodel.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class GraphGenerator extends JFrame{
    private String choose;
    public GraphGenerator(String title, String choose) {
        super(title);
        this.choose = choose;

        // Create the time series for price and volume
        TimeSeries heightSeries = new TimeSeries("Height (cm)");
        TimeSeries weightSeries = new TimeSeries("Weight (kg)");

        // Read data from Excel file using ExcelDataReader
        ExcelDataReader reader = new ExcelDataReader(choose);
        reader.readData(heightSeries, weightSeries);
        double minHeight = heightSeries.getMinY();
        double maxHeight = heightSeries.getMaxY();
        double minWeight = weightSeries.getMinY();
        double maxWeight = weightSeries.getMaxY();

        // Create datasets
        TimeSeriesCollection heightDataset = new TimeSeriesCollection(heightSeries);
        TimeSeriesCollection weightDataset = new TimeSeriesCollection(weightSeries);

        // Create the chart
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Height and weight change chart",
                "Date",
                "Height",
                heightDataset,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();


        if(maxHeight == minHeight)
        {
            NumberAxis heightAxis = (NumberAxis) plot.getRangeAxis(0);
            heightAxis.setRange(minHeight - 2, maxHeight + 2);
            heightAxis.setTickUnit(new NumberTickUnit(1));
        }
        // Configure the price renderer
        XYLineAndShapeRenderer heightRenderer = new XYLineAndShapeRenderer();
        heightRenderer.setSeriesPaint(0, java.awt.Color.RED);
        plot.setRenderer(0, heightRenderer);

        // Create a secondary axis for the volume data
        NumberAxis weightAxis = new NumberAxis("Weight");
        weightAxis.setRange(minWeight-2, maxWeight+2);
        weightAxis.setNumberFormatOverride(new java.text.DecimalFormat("###,##0.00"));
        if(reader.getMaxWeightGap() >= 1)
        {
            weightAxis.setTickUnit(new NumberTickUnit(1));
        }
        else if(reader.getMaxWeightGap() < 1)
        {
            weightAxis.setTickUnit(new NumberTickUnit(0.5));
        }
        plot.setRangeAxis(1, weightAxis);
        plot.setDataset(1, weightDataset);
        plot.mapDatasetToRangeAxis(1, 1);

        // Configure the volume renderer
        XYBarRenderer weightRenderer = new XYBarRenderer(0.20);
        weightRenderer.setSeriesPaint(0, java.awt.Color.BLUE);
        weightRenderer.setShadowVisible(false);
        plot.setRenderer(1, weightRenderer);

        // Ensure the price (line) is rendered on top of the volume (bar)
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);

        // Customizing the date format on the domain axis
        DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
        if(choose.equals("week"))
        {
            domainAxis.setVerticalTickLabels(false);
        }
        else if(choose.equals("month"))
        {
            domainAxis.setVerticalTickLabels(true);
        }
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(reader.getLastDate());
        endCalendar.add(Calendar.DAY_OF_YEAR, 1);
        domainAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
        domainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
        domainAxis.setRange(reader.getStartDate(), endCalendar.getTime());
        domainAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());
        domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1));

        // Add the chart to a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    public ChartPanel getChartPanel() {
        return (ChartPanel) getContentPane();
    }

}
