package examples;

import java.awt.Font;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;

abstract public class Example implements Runnable {
	
	// INTERCEPTOR FROM: http://stackoverflow.com/questions/3228427/redirect-system-out-println
	private class Interceptor extends PrintStream
	{
		private JTextArea textArea;
		private JPanel panel;
	    public Interceptor(OutputStream out, JTextArea textArea, JPanel panel)
	    {
	        super(out, true);
	        this.textArea = textArea;
	        this.panel = panel;
	    }
	    
	    @Override
	    public void print(String s)
	    {//do what ever you like

	    	try {
		    	textArea.append(s);
		    	textArea.setCaretPosition(textArea.getText().length() - 1);
				//textArea.update(textArea.getGraphics()); // See: http://stackoverflow.com/questions/629315/dynamically-refresh-jtextarea-as-processing-occurs
				
				panel.validate();
				
				// REFRESH PANEL:
				panel.repaint();
				panel.revalidate();
				panel.validate();
	    	} catch (NullPointerException e) {
	    		//System.setOut(origOut);
	    		System.err.println("Example.java -> PANEL EXCEPTION - GRAPHICS/PANEL HAS CHANGED");
	    		//e.printStackTrace();
	    	}
			
			// *****************
	        //super.print(s);
	    }
	    
	    @Override
	    public void println(String s) {
	    	print(s + "\n");
	    }
	}	
	
	protected void refreshPanel() {
		panel.repaint();
		panel.revalidate();
		panel.validate();		
	}
	
	protected void restoreSystemOutPrintStream () {
		// Restore original System.Out.println():
		if (origOut != null)
			System.setOut(origOut);
		origOut = null;		
	}
	
	// FIXME: This could be done smarter.
	protected void closeConosole() {
		panel.removeAll();
		panel.revalidate();		
		panel.validate();
	}
	
	protected void emulateConsoleAndRedirectStream() {
		
		JTextArea textArea = new JTextArea(32, 80);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setAlignmentX(0);
		
		// addition:
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
		);
		
		panel.setLayout(gl_panel);
		
		// WAS:
		//panel.add(scrollPane);
		
		panel.revalidate();			
		
		// Sets interceptor for System.out and redirect to the panel
		// ------------------------------------ INTERCEPTOR ADDITION
	    PrintStream origOut = System.out; // TODO: This has to be saved somewhere!
	    PrintStream interceptor = new Interceptor(origOut, textArea, panel);
	    System.setOut(interceptor);// just add the interceptor
	    //System.out.println("Test of the interceptor");
	    // ------------------------------------			
	}
	
	
	PrintStream origOut = null;
	
	// FIXME: This could be done smarter.
	protected JPanel panel;
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	public boolean usesSystemConsole() {
		return true;
	}

	abstract public String getName();
	abstract public String getInfo();
	
	abstract public void run(); // from Runnable;
}

