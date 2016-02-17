package exampleGeneticMaze1;

import java.util.List;



public class MazeLineDrawer {
	public final Maze maze;
	public MazeLineDrawer(Maze m) {
		maze = m;
	}
	
	public void addLine(Lines l) throws MazeLineDrawerException {
		// TODO: sanity check first (does the line fit ok?)
		if (!l.lineFitsRectagle(0, 0, maze.height-1, maze.width-1)) {
			throw new MazeLineDrawerException();
		}
		
		if (l.isHorizontal)
			for (int i=0; i < l.length; i++) {
				maze.data[l.j][i]++;
			}
		else
			for (int j=0; j < l.length; j++) {
				maze.data[j][l.i]++;
			}
	}
	
	public void addLines(List<Lines> ll) throws MazeLineDrawerException {
		for (Lines l : ll)
			addLine(l);
	}
}