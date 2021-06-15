package GUI;
import Threads.*;
import javax.swing.SwingUtilities;

public class Main 
{
	
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable()
	{
		public void run() 
		{
			Language language = new Language();
			language.setLocationRelativeTo(null);	//okno wyskakuje na srodku
			language.setVisible(true);	
		}
	});
		
	}
	
}
