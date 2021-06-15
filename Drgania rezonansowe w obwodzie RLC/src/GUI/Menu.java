package GUI;
import Threads.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

public class Menu extends JFrame 
{
	public Menu(String language)
	{	 
		lang = language;
		
		labels  = ResourceBundle.getBundle("Labels", new Locale(language));
		buttons  = ResourceBundle.getBundle("Buttons", new Locale(language));
		joptionpanes  = ResourceBundle.getBundle("JOptionPanes", new Locale(language));
		
		setSize(280, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setBackground(new Color(204, 153, 255));
		setTitle(labels.getString("MenuTitle"));
		setResizable(false);		//staly rozmiar okna
		
		
		
		
		//*********************************MENUBAR**************************
		
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(223,223,223));
		setJMenuBar(menuBar);
		
		author = new JMenu(buttons.getString("MenuAuthors"));
		authorItem = new JMenuItem(buttons.getString("MenuAuthorsItem"));
		ActionListener authorListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JOptionPane.showMessageDialog(Menu.this, "Natalia Grzybicka\n305 014\nJakub W³odarczyk\n305 064",buttons.getString("MenuAuthors"),JOptionPane.INFORMATION_MESSAGE);
			}
		};
		authorItem.addActionListener(authorListener);
		
		author.add(authorItem);
		menuBar.add(author);

		//*******************************************************************
		
		label = new JLabel(labels.getString("MenuHeadline"));
		label.setFont(label.getFont().deriveFont(Font.BOLD,12f)); //ustawienia czcionki (pogrubienie)
		this.getContentPane().setBackground(new Color(204, 153, 255));
		label.setPreferredSize(new Dimension(260, 75));
		
		circuitRLC = new JButton(buttons.getString("MenuCircuit"));
		circuitRLC.setBackground(new Color(223,223,223));
		circuitRLC.setPreferredSize(new Dimension(150,50));
		
		instruction = new JButton(buttons.getString("MenuInstruction"));
		instruction.setBackground(new Color(223,223,223));
		instruction.setPreferredSize(new Dimension(150,50));
		ActionListener instructionListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{

				Desktop d = java.awt.Desktop.getDesktop();	//Otwieranie pdf
				try 
				{
					d.open(new java.io.File("INSTRUKCJA_"+language+".PDF"));
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}	
		};
		instruction.addActionListener(instructionListener);
		
		exit = new JButton(buttons.getString("MenuExit"));
		exit.setBackground(new Color(223,223,223));
		exit.setPreferredSize(new Dimension(150,50));
		ActionListener exitListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				System.exit(0);	
			}	
		};
		exit.addActionListener(exitListener);
		
		master = new Master(this);	//wywolanie konstruktora klasy master 
		
		ActionListener masterListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{ 
				master.setVisible(true);	
				Menu.this.setVisible(false);
			}	
		};
		circuitRLC.addActionListener(masterListener);
		
		lNull1 = new JLabel();
		lNull1.setPreferredSize(new Dimension(this.getWidth(), 25));
		
		lNull2 = new JLabel();
		lNull2.setPreferredSize(new Dimension(this.getWidth(), 50));
		
		lNull3 = new JLabel();
		lNull3.setPreferredSize(new Dimension(this.getWidth(), 50));
		
		this.add(label);
		this.add(lNull1);
		this.add(circuitRLC);
		this.add(lNull2);
		this.add(instruction);
		this.add(lNull3);
		this.add(exit);
	}
	
	JButton circuitRLC, instruction, exit;
	JLabel label, lNull1, lNull2, lNull3;
	Master master;
	
	JMenuBar menuBar;
	JMenu author, language;
	JMenuItem authorItem, polish, english, german;
	
	String lang;
	
	public static ResourceBundle labels;
	public static ResourceBundle buttons; 
	public static ResourceBundle joptionpanes;

}

