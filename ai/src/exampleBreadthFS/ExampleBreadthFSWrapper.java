package exampleBreadthFS;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import examples.Example;

public class ExampleBreadthFSWrapper extends Example {
	@Override
	public String getName() {
		return "BreadthFS";		
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return ""
		+ "Breadth first search demo for 3x3 puzzle:\n"
		+ "\n"
		+ "Puzzle3x3 start = new Puzzle3x3(\n"  
		+ "                       new int[][]{\n"
		+ "                         {1,3,8},\n"
		+ "                         {0,6,2},\n"
	    + "                         {5,4,7}});\n"
	    + "\n"
        + "Puzzle3x3 goal = new Puzzle3x3(\n"
        + "                         new int[][]{\n"
        + "                         {1,2,3},\n"
        + "                         {4,0,5},\n"
        + "                         {6,7,8}});\n\n";		
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
