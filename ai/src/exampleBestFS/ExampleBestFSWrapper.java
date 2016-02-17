package exampleBestFS;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import examples.Example;

public class ExampleBestFSWrapper extends Example {
	@Override
	public String getName() {
		return "BestFS";
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return ""
		+ "The algorithm uses Best First Search to find a solution for 3x3 puzzle.\n"
		+ "The example uses aiBase +aiAlg classes implementing the algorithm of search itself.\n"
		+ "\n"
		+ "Source code:\n"
		+ "  Puzzle3x3op - list of possible operators\n"
		+ "  Puzzle3x3 - defines a representation for 3x3 puzzle along with calculation of heuristic function.\n"
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
