package exampleAlphaBetaPrunning;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import examples.Example;

public class ExampleAlphaBetaWrapper extends Example {
	@Override
	public String getName() {
		return "AlphaBetaTree";
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		  
		return    ""
				+ "This example does not use aiBase or aiGen as the package contains\n "
				+ "example implementation of AlphaBetaPrunning. The algorithm and its tree is inspiried by \n"
				+ "lecture http://web.cs.ucla.edu/~rosen/161/notes/alphabeta.html . The source code is of own\n"
				+ "production and does not contain any code from UCLA.\n\n"
				+ "In the example tree is analyzed and inappropriate branches are skipped as in alpha-beta pruning.\n"
				+ "The tree does not analyze possible operators or possible moves for each of the representation\n"
				+ "as tree for analysis is given upfront.\n"
				+ "The tree:\n"
				+ "                                                                                   (   )   ROOT\n"
				+ "                                                                             __ /        \\ __\n"
				+ "                                                                        __ /                  \\\n"
				+ "                                                                   __ /                        \\ __\n"
				+ "                                                              __ /                                  \\ __\n"
				+ "                                                        ___ /                                             \\__\n"
				+ "                                                      /                                                        \\     \n"
				+ "                                    PATH: [0]  (  )                                                                (   ) PATH: [1]\n"
				+ "                                             /      \\                                                             /     \\\n"
				+ "                                          /            \\                                                         /       \\\n"
				+ "                                        /                 \\                                                     /         \\\n"
				+ "                                      /                      \\                                                 /           \\\n"
				+ "                                   /                           \\                                              /             \\\n"
				+ "                                /                                 \\                                          /               \\\n"
				+ "                             /                                       \\                                      /                 \\\n"
				+ "              PATH:[0,0]   (  )                         PATH: [0,1]  (   )                   PATH: [1,0](  )                  (  ) PATH[1,1]\n"
				+ "                         /     \\                                      / \\                                / \\                    \\\n"
				+ "                        /       \\                                    /   \\                              /   \\                    \\\n"
				+ "                       /         \\                                  /     \\                            /     \\                    \\\n"                                                         
				+ "                      /           \\                                /       \\                          /       \\                    \\\n"                
				+ "                     /             \\                              /         \\                        /         \\                    \\\n"        
				+ "                    /               \\                            /           \\                     /            \\                    \\\n"
				+ " PATH: [0,0,0]   ( )       P:[0,0,1] ( )           P: [0,1,0] ( )    [0,1,1]( )          [1,0,0]( )            ( )[1,0,1]           ( ) P:[1,1,1]\n"
				+ "                /    \\             /      \\                   /            /    \\              /   \\              \\                /    \\\n"
				+ "               /      \\           /        \\                 /            /       \\            /      \\             \\             /        \\\n"
				+ "              3        17        2          12              15          25          0        2         5             3           2          14\n"
				+ "       [0,0,0,0]  [0,0,0,1]  [0,0,1,0]    [0,0,1,1]   [0,1,0,0]    [0,1,0,1]   [0,1,1,0] [1,0,0,0] [1,0,0,1]      [1,0,1,0]    [1,1,1,0]  [1,1,1,1]\n";
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
