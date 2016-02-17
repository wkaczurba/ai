package exampleBestFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aiBase.AiObject;
import aiBase.AiOperator;

/**
 * TODO: Put info
 * 
 * @author Witold Kaczurba 
 */
public class Puzzle3x3 extends AiObject {

	int tiles[][] = new int[][]{
		{1,2,3},
		{4,0,5},
		{6,7,8}};
	public Puzzle3x3() { 
		isValid();		
	}
	public Puzzle3x3(int tiles[][]) {
		for (int j=0; j<3; j++)
			for (int i=0; i<3; i++)
				this.tiles[j][i] = tiles[j][i];
		isValid();
	}
	//public Puzzle3x3(Puzzle3x3 a)
	public Puzzle3x3(AiObject obj)
	{
		if (obj instanceof Puzzle3x3) {
			Puzzle3x3 a = (Puzzle3x3) obj;
		
			for (int i=0; i<3;i++)
				for (int j=0; j<3; j++)
					tiles[j][i] = a.tiles[j][i];
		} else {
			throw new UnsupportedOperationException("Puzzle3x3(AiObject obj) provided with obj other than Puzzle3x3 type");
		}
		
		isValid();
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Puzzle3x3) {
			Puzzle3x3 a = (Puzzle3x3) obj;
			
			if ((this.tiles.length != a.tiles.length) || (this.tiles[0].length != a.tiles[0].length))
				return false;
			
			for (int j=0; j<3; j++)
				for (int i=0; i<3; i++)
					if (a.tiles[j][i] != this.tiles[j][i])
						return false;
			return true;
		} else {
			// throw new UnsupportedOperationException("AiObject.equals(obj) not implemented");
			return false;
		}
		
	};
	
	protected int[] find_blank() {
		for (int i=0; i<3; i++)
			for (int j=0; j<3; j++)
				if (tiles[j][i] == 0)
					return new int[]{j,i};
		return new int[]{-1,-1}; // ERROR?
	}
	public void isValid() {
		// Function to validate the tiles (0...8)
		Set<Integer> values = new HashSet<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8));
		for (int j=0; j<3; j++)
			for (int i=0; i<3; i++) {
				if (!values.contains(tiles[j][i]))
					throw new UnsupportedOperationException("Invalid tile set");
				values.remove(tiles[j][i]);
			}
		if (!values.isEmpty())
			throw new UnsupportedOperationException("Invalid tile set"); 
	}
	public List<?> getAllPossibleOperators() {
		int ji[] = find_blank();
		List<Puzzle3x3_op> m = new ArrayList<Puzzle3x3_op>();
		
		if (ji[0] != 0) // First row;
			m.add(new Puzzle3x3_op(Puzzle3x3_op.Data.UP)); // TODO: Think how to make it easier to use.
		if (ji[0] != 2) // Last row;
			m.add(new Puzzle3x3_op(Puzzle3x3_op.Data.DOWN));
		if (ji[1] != 0) // First col;
			m.add(new Puzzle3x3_op(Puzzle3x3_op.Data.LEFT));
		if (ji[1] != 2) // Last col;
			m.add(new Puzzle3x3_op(Puzzle3x3_op.Data.RIGHT));
		return m;
	}
	
	//public Puzzle3x3 applyOperator(Puzzle3x3_op op) {
	
	public AiObject applyOperator(AiOperator op) {
		Puzzle3x3 retval = new Puzzle3x3(this);
		
		int j = retval.find_blank()[0];
		int i = retval.find_blank()[1];
		int newi = i;
		int newj = j;
		
		if (op.equals(Puzzle3x3_op.Data.UP)) newj = j-1;
		if (op.equals(Puzzle3x3_op.Data.DOWN)) newj = j+1;
		if (op.equals(Puzzle3x3_op.Data.LEFT)) newi = i-1;
		if (op.equals(Puzzle3x3_op.Data.RIGHT)) newi = i+1;
		
		// Swapping tiles:
		retval.tiles[j][i] = retval.tiles[newj][newi];
		retval.tiles[newj][newi] = 0; // blank.
		return retval;
	}

	private int getHeuristic_tile_distance() {
		int h = 0;
		int goal[][] = new int[][]{ 
			{1,2,3},
			{4,0,5},
			{6,7,8}};
		
		for (int i=0; i<3; i++)
			for (int j=0; j<3; j++) {
				int tile_value = this.tiles[j][i];
				
				if (tile_value == 0) continue;
				else {
				
				// find the tile value in the goal:
				for (int y=0; y<3; y++)
					for (int x=0; x<3; x++)
						if (tile_value == goal[y][x])
							h += Math.abs(y-j) + Math.abs(x-i);  
				}
			}
						
		return h;
	}

	public int getHeuristic() {
		return getHeuristic_tile_distance();		
	}
	
	public String toString() {
		String s = new String();
		s =  tiles[0][0] + "," + tiles[0][1] + "," + tiles[0][2] + "\n";
		s += tiles[1][0] + "," + tiles[1][1] + "," + tiles[1][2] + "\n";
		s += tiles[2][0] + "," + tiles[2][1] + "," + tiles[2][2] + "\n";
		s += "heuristic: " + this.getHeuristic() + "\n";
		
		// FLATTEN:
		//s = s.replace("\n", ",");
		return s;
	}
}

