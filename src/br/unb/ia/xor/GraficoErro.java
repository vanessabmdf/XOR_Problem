package br.unb.ia.xor;

import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 * A simple demonstration application showing how to create a line chart using
 * data from an {@link XYDataset}.
 *
 */
public class GraficoErro extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Double> listaErros;

	/**
	 * Creates a new demo.
	 *
	 * @param title
	 *            the frame title.
	 */
	public GraficoErro(final String title, ArrayList<Double> listaErros) {

		super(title);
		this.listaErros = listaErros;

		final XYDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);

	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return a sample dataset.
	 */
	private XYDataset createDataset() {
		final XYSeries serieErros = new XYSeries("Entrada 1");
		final XYSeries serieErros2 = new XYSeries("Entrada 2");
		final XYSeries serieErros3 = new XYSeries("Entrada 3");
		final XYSeries serieErros4 = new XYSeries("Entrada 4");

		for (int i = 0; i < listaErros.size(); i += 4) {
			serieErros.add(i/4, listaErros.get(i));
			serieErros2.add(i/4, listaErros.get(i + 1));
			serieErros3.add(i/4, listaErros.get(i + 2));
			serieErros4.add(i/4, listaErros.get(i + 3));
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(serieErros);
		dataset.addSeries(serieErros2);
		dataset.addSeries(serieErros3);
		dataset.addSeries(serieErros4);

		return dataset;

	}

	private JFreeChart createChart(final XYDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createXYLineChart(
				"Line Chart Demo 6", // chart title
				"Epocas", // x axis label
				"Erros", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.setBackgroundPaint(Color.white);

		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);

		// get a reference to the plot for further customisation...
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		// plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(1, true);
		renderer.setSeriesShapesVisible(1, false);
		plot.setRenderer(renderer);

		// change the auto tick unit selection to integer units only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;

	}

	// ****************************************************************************
	// * JFREECHART DEVELOPER GUIDE *
	// * The JFreeChart Developer Guide, written by David Gilbert, is available
	// *
	// * to purchase from Object Refinery Limited: *
	// * *
	// * http://www.object-refinery.com/jfreechart/guide.html *
	// * *
	// * Sales are used to provide funding for the JFreeChart project - please *
	// * support us so that we can continue developing free software. *
	// ****************************************************************************

	/**
	 * Starting point for the demonstration application.
	 *
	 * @param args
	 *            ignored.
	 */

}