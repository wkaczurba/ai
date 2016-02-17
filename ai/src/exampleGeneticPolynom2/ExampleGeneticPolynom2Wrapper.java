package exampleGeneticPolynom2;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import examples.Example;

public class ExampleGeneticPolynom2Wrapper extends Example {
	@Override
	public String getName() {
		return "GeneticPolynom2";
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return ""
		   + "The example is similar to the previous one so refer to GeneticPolynom1 first\n"
		   + "The main difference is with expression; Instead of representing polynomial as\n"
		   + "A*x^7 + B*x^6 + C*x^5 + D*x^4 + E*x^3 + F*x^2 + G*x + H.\n"
		   + "\n"
		   + "It is expressed as: (x - A)(x - B)(x - C)(x - D)(x - E)(x - F)(x - G).\n"
		   + "Where A…G = float values represented as \"Duals\"\n"
		   + "The example produces worse results than exampleGeneticPolynom1.\n";		
	}
		
	public void run() {
		if (panel == null)
			return;
		
		emulateConsoleAndRedirectStream();

		
		new Main();

		// REFRESH+RESTORE:
		this.refreshPanel();
		this.restoreSystemOutPrintStream();
	}
}
