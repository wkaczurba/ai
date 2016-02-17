package exampleGeneticMaze1;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import examples.Example;

public class ExampleGeneticMaze1Wrapper extends Example {
	@Override
	public String getName() {
		return "GeneticMaze1";
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return ""
		   + "This folder contains algorithm that genetically generates algorithm for a maze generation.\n"
		   + "The maze has configurable knobs and the goal that is to minimize number of dead ends,\n"
		   + "maximize number of crossings between corridors in Maze and reduce number of corridors\n"
		   + "which are thicker than 1 block.\n"
		   + "\n"
		   + "   Desired lines (thickness = 1 block)\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + " | X X X X X           X X X X X X X X X X |\n"
		   + " | X X X X X X X X X   X X X X X X X X X X |\n"
		   + " | X X X X X X X X X   X X X X X X X X X X |\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + "\n"
		   + "   Example of line (corridor) thicker than one block (undesired)\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + " | X X X X X       X X X X X X X X X X X X |\n"
		   + " | X X X X X       X X X X X X X X X X X X |\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + "\n"
		   + "   Example of lines with dead-ends (undesired):\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + " | X X X X X X X X X X X X X X   X X X X X |\n"
		   + " | X X X X X X X X X X X X X X   X X X X X |\n"
		   + " | X X X X X             X X X   X X X X X |\n"
		   + " | X X X X X X X X X X X X X X   X X X X X |\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + "\n"
		   + "\n"
		   + "Result: as a result we usually get a Maze that looks like a grid. \n"
		   + "The grid is shifted to upper-left corner, it is not equally distributed. \n"
		   + "Example result:\n"
		   + "\n"
		   + "  -----------------------------------------\n"
		   + " |                                   X X X |\n"
		   + " |   X   X X   X   X   X X   X   X   X X X |\n"
		   + " |   X   X X   X   X   X X   X   X   X X X |\n"
		   + " |                               X   X X X |\n"
		   + " |   X   X X   X   X   X X   X   X   X X X |\n"
		   + " |                               X   X X X |\n"
		   + " |   X   X X   X   X   X X   X   X   X X X |\n"
		   + " |                               X   X X X |\n"
		   + " |   X   X X   X   X   X X   X   X   X X X |\n"
		   + " |   X   X X   X   X   X X   X   X   X X X |\n"
		   + " |                               X   X X X |\n"
		   + " |   X   X X   X   X   X X   X   X   X X X |\n"
		   + " |                               X   X X X |\n"
		   + " |   X   X X   X   X   X X   X   X   X X X |\n"
		   + " |   X   X X   X   X   X X   X   X   X X X |\n"
		   + " |                               X X X X X |\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + " | X X X X X X X X X X X X X X X X X X X X |\n"
		   + "  -----------------------------------------\n"
		   + "\n"
		   + "\n"
		   + "This result is because of the used representation.\n"
		   + "Each of the Genome is a Line that has a:\n"
		   + "  - start point (X, Y)\n"
		   + "  - length\n"
		   + "  - orientation (line drawn to right or line drawn downwards)\n"
		   + "\n"
		   + "The lines are drawn either in the right direction (horizontal lines) or downward direction \n"
		   + "(vertical lines). Lines of length that exceed Maze dimensions are treated as invalid and rejected. \n"
		   + "It makes then more likely for an invalid line to happen on the right and bottom side.\n"
		   + "Therefore the maze is usually shiften towards top-left corner. To solve this problem - we can use\n"
		   + "different representation (e.g. orientation that can be set in 4 directions.)\n"
		   + "\n"
		   + "\n"
		   + "Learning: \n"
		   + "\n"
		   + "This example shows that statistics of representation (combinations that are valid vs. combinations\n"
		   + "that are invalid) are crucial in getting desired outcomes in genetic algorithms. While deciding\n"
		   + "for a representation it is crucial to understand what random configurations are invalid (and will\n"
		   + "be rejected) vs. what configurations are valid. What is distribution of probability of\n"
		   + "valid vs. invalid combinations. The distribution will affect the outcome of a genetic algorithm.\n";
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
