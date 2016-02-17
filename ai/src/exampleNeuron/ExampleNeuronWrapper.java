package exampleNeuron;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import examples.Example;

public class ExampleNeuronWrapper extends Example {
	@Override
	public String getName() {
		return "Neuron";
	}

	@Override
	public String getInfo() {
		return "" 
	    + "Simple example showing learning of a single neuron.\n"
		+ "This example can be tweaked to show that single neuron cannot"
		+ "solve more complex tasks and can end-up being unstable.";

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
