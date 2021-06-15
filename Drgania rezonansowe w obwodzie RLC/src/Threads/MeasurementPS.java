package Threads;
import GUI.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MeasurementPS extends JFrame implements Runnable
{
	public MeasurementPS(GraphPS graph)
	{
		this.graphPS = graph;
		master = graphPS.master;
		setSize(300, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(Menu.labels.getString("MeasurementsPSTitle"));
		setLocationRelativeTo(graphPS);
		setResizable(false);
		
		finished = false;
		
	    menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menu = new JMenu(Menu.buttons.getString("MasterPSMeasurementsMenu"));
		export = new JMenuItem(Menu.buttons.getString("MasterMeasurementsExport"));
		ActionListener exportListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if(finished)
				{
					File dirPath = new File(System.getProperty("user.dir"));	//analogiczne procedury co przy wczytaniu
			        JFileChooser jChooser = new JFileChooser(dirPath);
			        jChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
			        jChooser.setAcceptAllFileFilterUsed(false);	
			        int returnVal = jChooser.showSaveDialog(null);
			        if (returnVal==JFileChooser.APPROVE_OPTION) 
			        {
			        	try 
			            {
			                 	File outputFile;
			                 	
			        			if(jChooser.getSelectedFile().getName().contains(".txt"))
			        			{
			        				outputFile = jChooser.getSelectedFile();
			        			}
			        			else
			        			{
			        				outputFile = new File(jChooser.getSelectedFile()+".txt");
			        				
			        			}			        			
			                 	PrintWriter osw = new PrintWriter(outputFile, Charset.forName("UTF-8"));
			                 	
			                 	osw.print(head[0]);
			                 	osw.print("\t");
			                 	osw.print(head[1]);
			                 	osw.print("\t");
			                 	osw.print(head[2]);
			                 	osw.print("\r\n");
			                 	
			                 	for(int i=0; i<=amount; i++)
			                 	{
			                 		osw.print(values[i][0]);
				                 	osw.print("\t");
				                 	osw.print(values[i][1]);
				                 	osw.print("\t");
				                 	osw.print(values[i][2]);
				                 	osw.print("\r\n");
			                 	}
			                    osw.close();
			                } 
			                
			                catch (FileNotFoundException e) 
			                {
			                    e.printStackTrace();
			                } 
			                
			                catch (IOException e) 
			                {
			                    e.printStackTrace();
			                }
			            }
				}
				
				else
				{
					JOptionPane.showMessageDialog(MeasurementPS.this, Menu.joptionpanes.getString("MeasurementsExportErrorMain"),Menu.joptionpanes.getString("MeasurementsExportErrorTitle"),JOptionPane.ERROR_MESSAGE);
				}
				
			
			}	
		};
		export.addActionListener(exportListener);
		menu.add(export);
		menuBar.add(export);
		
		head = new String[3];				//tablica na naglowek
		head[0] = "Lp.";
		head[1] = "f [kHz]";
		head[2] = (char) (0x03D5) + "[rad]";
	}

	@Override
	public void run() 
	{
		f=master.fMin;			//poczatkowa wartosc f
		amount = (int) ((master.fMax-master.fMin)/master.fDelta);		//liczba punktow
		values = new Object[amount+1][3];		//tablice na wartosci punktow
		int j=1;
		
		while(f<=master.fMax)
		{
			if(master.choose == 1) 	//szeregowy
			{			
				phi = -Math.atan((2*Math.PI*f*master.L-1/(2*Math.PI*f*master.C))/master.R);
			}	
		
			else // rownolegle
			{
				phi = -Math.atan(master.R*(2*Math.PI*master.C*f-1/(2*Math.PI*f*master.L)));
			}			
						
			values[j-1][0] = j;		//wypelnienie tablic
									//dla j=1 zerowy wiersz i zerowa kolumna
			values[j-1][1] = f/1000;
			
			values[j-1][2] = Master.round(phi,3);
						    
	    	j++;
			f+=master.fDelta;
		
			table = new JTable(values, head);		//utworzenie tabeli
			table.setEnabled(false);				//zablokowanie zmian
			pane = new JScrollPane(table);			//dodanie scrolla
			add(pane, BorderLayout.CENTER);
	    
			repaint();
			
			try 
			{			
				Thread.sleep(500);			//odczekanie
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}

		}
	    master.on = false;
		finished = true;
		
	}
	
	GraphPS graphPS;
	Master master;
	String[] head;
	double f, phi;
	int amount;
	boolean finished;
	Object[][] values;
	JTable table;
	JScrollPane pane;
	JMenu menu;
	JMenuBar menuBar;
	JMenuItem export;
}
