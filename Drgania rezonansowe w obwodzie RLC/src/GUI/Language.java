package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Language extends JFrame 
{
	public Language()
	{
		setSize(550, 250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setBackground(new Color(204, 153, 255));
		setResizable(false);
		setTitle("RLC");
		
		
		panel = new JPanel();
		panel.setBackground(new Color(204, 153, 255));
		panel.setLayout(new FlowLayout());
	
		
		try 
		{
			BufferedImage polishFile = ImageIO.read(new File("polska.png"));
			Image polishZip = polishFile.getScaledInstance(170,200,Image.SCALE_SMOOTH);	
			polish = new JButton(new ImageIcon(polishZip));
			polish.setBorder(null);
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		  				  
		 
		
		polish.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Menu menu = new Menu("pl");
				menu.setLocationRelativeTo(null);	//okno wyskakuje na srodku
				Language.this.setVisible(false);
				menu.setVisible(true);	
			}
		});
		
		try 
		{
			BufferedImage englishFile = ImageIO.read(new File("british.png"));
			Image englishZip = englishFile.getScaledInstance(170,200,Image.SCALE_SMOOTH);	
			english = new JButton(new ImageIcon(englishZip));
			english.setBorder(null);
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		english.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
			
				Menu menu = new Menu("en");
				menu.setLocationRelativeTo(null);	//okno wyskakuje na srodku
				Language.this.setVisible(false);
				menu.setVisible(true);	
			}
		});
		try 
		{
			BufferedImage deutschFile = ImageIO.read(new File("deutsch.png"));
			Image deutschZip = deutschFile.getScaledInstance(170,200,Image.SCALE_SMOOTH);	
			german = new JButton(new ImageIcon(deutschZip));
			german.setBorder(null);
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		german.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
			
				Menu menu = new Menu("de");
				menu.setLocationRelativeTo(null);	//okno wyskakuje na srodku
				Language.this.setVisible(false);
				menu.setVisible(true);	
			}
		});
		
		panel.add(polish);
		panel.add(english);
		panel.add(german);
		add(panel, BorderLayout.CENTER);
	
	}
	JButton polish, german, english;
	JPanel panel;
}
