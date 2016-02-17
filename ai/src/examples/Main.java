package examples;

import java.awt.EventQueue;

import javax.swing.SwingUtilities;

public class Main {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MainControl control = null;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {

					MainControl control = new MainControl();
					MainFrame frame = new MainFrame(control);
					frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}
}
