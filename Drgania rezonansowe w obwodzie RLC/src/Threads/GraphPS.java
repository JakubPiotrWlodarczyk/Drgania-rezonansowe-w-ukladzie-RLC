package Threads;
import GUI.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraphPS extends JFrame implements Runnable
{
	public GraphPS(Master mast)
	{
		this.master = mast;
		setSize(500, 400);
		setMinimumSize(new Dimension(500, 400));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(Menu.buttons.getString("MasterPSGraph"));
		setLocationRelativeTo(master);
		
		//***************************************MENU******************************************************
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menu = new JMenu(Menu.buttons.getString("MasterPSMeasurementsMenu"));
		measurements = new JMenuItem(Menu.buttons.getString("MasterPSMeasurementsMenuItem"));
		ActionListener measurementsListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{		
				if(master.runnable)
				{
					measurementsPS.setVisible(true);
				}
				
				else
				{
					JOptionPane.showMessageDialog(GraphPS.this, Menu.joptionpanes.getString("MasterNoDataErrorMain"),Menu.joptionpanes.getString("MasterNoDataErrorTitle"),JOptionPane.ERROR_MESSAGE);
				}			
			}	
		};
		measurements.addActionListener(measurementsListener);
		
		menu.add(measurements);
		menuBar.add(menu);
		
		//***************************************WYKRES*****************************************************
		series = new XYSeries("");		//seria danych
		dataset = new XYSeriesCollection();			//zbior serii
		dataset.addSeries(series);
		chart = ChartFactory.createScatterPlot("", Menu.buttons.getString("MasterPSMeasurementsChartX"), Menu.buttons.getString("MasterPSMeasurementsChartY"), dataset, PlotOrientation.VERTICAL, false, true, false);
		plot = (XYPlot) chart.getPlot();		//upiekszanie wykresu
		plot.setDomainGridlinePaint(Color.lightGray);
	    plot.setRangeGridlinePaint(Color.lightGray);
	    plot.setBackgroundPaint(Color.white);
	    plot.getRenderer().setSeriesPaint(0, new Color(204, 153, 255));
	    
	    panel = new ChartPanel(chart);			//panel do wykresu
	    panel.setPopupMenu(null);
		panel.setSize(getPreferredSize());
		this.add(panel);
		
	}
	
	@Override
	public void run() 
	{
		
		f=master.fMin;			//ustawienie wartosci poczatkowej
		while(f<=master.fMax)
		{	
			if(master.choose == 1) 	//szeregowy
			{			
				phi = -Math.atan((2*Math.PI*f*master.L-1/(2*Math.PI*f*master.C))/master.R);
				series.add(f/1000,phi);			//dodanie punktu do serii
			}
			
			else // rownolegle
			{
				phi = -Math.atan(master.R*(2*Math.PI*master.C*f-1/(2*Math.PI*f*master.L)));
				series.add(f/1000,phi);			//dodanie punktu do serii
			}
			f+=master.fDelta;
			
			try 
			{			
				Thread.sleep(500);			//odczekanie
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		master.on = false;			//wylaczenie przycisku
		
	}
	public MeasurementPS measurementsPS;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem measurements;
	Master master;
	JFreeChart chart;
	ChartPanel panel;
	XYPlot plot;
	double f,phi;
	XYSeries series;
	XYSeriesCollection dataset;	
}
