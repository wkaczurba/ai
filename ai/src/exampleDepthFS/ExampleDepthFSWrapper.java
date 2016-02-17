package exampleDepthFS;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import examples.Example;

public class ExampleDepthFSWrapper extends Example {
	@Override
	public String getName() {
		return "DepthFS";
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "Demo shows Depth First Search Algorithm.\n\n"
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
        + "                         {6,7,8}});\n\n"
		+ "This case is similar to BestFS and to BreadthFS. Nevertheless this example shows that depthFS can go wrong\n"
        + "as it goes very deep very fast. The algorithm of DFS does not implement checking of already opened states\n"
        + "and therefore it may end up looping (e.g. blank tile keeps turning right or left around in 3x3 Puzzle).\n\n"
        + "This example shows how important it is to generate all potential tree moves upfront (or have an algorithm\n" 
        + "determining possible moves) to eliminate risk of looping the algorithm. Generaiton of potential combinations\n"
        + "takes on the other hand time and it suggests that IDDFS (Iterartive Deepening Depth-First Search) might be a\n"
        + "solution to the problem.\n";
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
