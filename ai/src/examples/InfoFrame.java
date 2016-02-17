package examples;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;

public class InfoFrame {

	private JFrame frame;
	private JPanel contentPane;
	private JTextArea txtrSomeText;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfoFrame frame = new InfoFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * 
	 * @param name name of the exmaplep
	 * @param text information about the stuff...
	 */
	public void build(String name, String text) {
		frame = new JFrame(name + " - info about the example");
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		
		txtrSomeText = new JTextArea();
		txtrSomeText.setText(text);
		txtrSomeText.setBackground(new Color(255, 255, 153));
		txtrSomeText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		contentPane.add(txtrSomeText, BorderLayout.CENTER);		
	}
	
	/**
	 * Create the frame.
	 */
	public InfoFrame(String name, String info) {
		this.build(name, info);
	}

}
