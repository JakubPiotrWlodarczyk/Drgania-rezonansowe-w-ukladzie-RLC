
package Threads;
import GUI.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;


public class Middle extends JPanel implements Runnable
{
	public Middle(Master m)
	{	
		master=m;
		setBackground(Color.white);
		setLayout(new GridLayout(1,1));
		setSize(getPreferredSize());
		painted = false;			//wykres nie zostal narysowany
		
		//******************************************WYKRES***********************************************
		
		series = new XYSeries("");	//seria danych
		dataset = new XYSeriesCollection();	//zbior serii
		dataset.addSeries(series);
		chart = ChartFactory.createScatterPlot("", Menu.labels.getString("MiddleChartX"), Menu.labels.getString("MiddleChartY"), dataset, PlotOrientation.VERTICAL, false, true, false);
		plot = (XYPlot) chart.getPlot();		//upiekszanie wykresu
		plot.setDomainGridlinePaint(Color.lightGray);
	    plot.setRangeGridlinePaint(Color.lightGray);
	    plot.setBackgroundPaint(Color.white);
	    plot.getRenderer().setSeriesPaint(0, new Color(204, 153, 255));
	    
	    panel = new ChartPanel(chart);		//panel do wykresu 
	    panel.setPopupMenu(null);
		panel.setSize(new Dimension(getPreferredSize()));
		this.add(panel);
	}
	
	@Override
	public void run() 
	{
		f=master.fMin;		//pobranie wartosci poczatkowej
		while(f<=master.fMax)
		{	
			if(master.choose == 1) 	//szeregowy
			{			
				I = master.A/(Math.sqrt(Math.pow(master.R, 2)+Math.pow(2*Math.PI*f*master.L-1/(2*Math.PI*f*master.C),2)));
				series.add(f/1000,I*1000);		//dodanie punktu do serii
			}
			
			else // rownolegle
			{
				I = master.A*Math.sqrt(Math.pow(1/master.R,2)+Math.pow(2*Math.PI*f*master.C-1/(2*Math.PI*master.L*f), 2));
				series.add(f/1000,I*1000);		//dodanie punktu do serii
			}
			f+=master.fDelta;	//zwiekszenie f o delta f
			
			try 
			{			
				Thread.sleep(500);		//odczekanie
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		master.on = false;			//gdy petla sie skonczyla automatycznie on==false
		painted = true;				//wykres narysowany
	}

	Master master;
	JFreeChart chart;
	ChartPanel panel;
	XYPlot plot;
	double f,I;
	XYSeries series;
	XYSeriesCollection dataset;
	public boolean painted;
}
