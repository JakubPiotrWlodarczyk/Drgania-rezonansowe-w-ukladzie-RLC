package Threads;
import GUI.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Measurement extends JFrame implements Runnable
{
	public Measurement(Master mas)
	{
		this.master = mas;
		setSize(300, 600);
		setMinimumSize(new Dimension(300, 600));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle(Menu.labels.getString("MeasurementsTitle"));
		setLocationRelativeTo(master);
		setLayout(new BorderLayout());
		
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
					JOptionPane.showMessageDialog(Measurement.this, Menu.joptionpanes.getString("MeasurementsExportErrorMain"),Menu.joptionpanes.getString("MeasurementsExportErrorTitle"),JOptionPane.ERROR_MESSAGE);
				}
				
			}	
		};
		export.addActionListener(exportListener);
		menu.add(export);
		menuBar.add(export);
		
		head = new String[3];		//tabela na naglowek
		head[0] = "Lp.";
		head[1] = "f [kHz]";
		head[2] = "I [mA]";
	}

	@Override
	public void run() 
	{
		f=master.fMin;		//poczatkowa wartosc f
		amount = (int) ((master.fMax-master.fMin)/master.fDelta);		//ilosc pomiarow
		values = new Object[amount+1][3];		//tablice na wartosci punktow
		int j=1;
		
		while(f<=master.fMax)
		{
			if(master.choose == 1) 	//szeregowy
			{			
				iMeasurements=master.A/(Math.sqrt(Math.pow(master.R, 2)+Math.pow(2*Math.PI*f*master.L-1/(2*Math.PI*f*master.C),2)));
				
			}
			
			else // rownolegle
			{
				iMeasurements=master.A*Math.sqrt(Math.pow(1/master.R,2)+Math.pow(2*Math.PI*f*master.C-1/(2*Math.PI*master.L*f), 2));			
			}			
			
			values[j-1][0] = j;		//wypelnienie tabeli
									//dla j=1 zerowy wiersz i zerowa kolumna
			values[j-1][1] = f/1000;
				
			values[j-1][2] = Master.round(1000*iMeasurements,3);
		
		    j++;
			f+=master.fDelta;
			
			table = new JTable(values, head);		//tworzenie tabeli
		    table.setEnabled(false);				//zablokowanie edycji tabeli
			pane = new JScrollPane(table);			//dodanie scrolla
		    add(pane, BorderLayout.CENTER);
		    
		    repaint();   
		    
		    try 
			{			
				Thread.sleep(500);		//odczekanie
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		master.on = false;
		finished = true;
	}
	
	Master master;
	double f, fMeasurements, iMeasurements;
	int amount;
	boolean finished;
	JTextField[] iTab, fTab, number;
	JTable table;
	JScrollPane pane;
	Object[][] values;
	String[] head;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem export;
}
