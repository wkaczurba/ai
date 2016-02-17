package exampleGeneticMaze1;

// Consider generic maze (Object type)
public class Maze extends Object {
	// array + some functions.
	public Integer[][] data; 	// TODO: public -> might be a bit dangerous. (?)
	public final int width;
	public final int height;
	
	public Maze(int width, int height) {
		this.height = height;
		this.width  = width;
		data = new Integer[height][width];
		
		// Reset values:
		for (int j=0; j<height; j++)
			for (int i=0; i<width; i++)
				data[j][i] = 0;
	}
	
	public class Statistics {
		public final int crossings;
		public final int deadEnds;
		public final int totalLength;
		public final int parallels;
		
		public Statistics(Maze m) {
			int deadEnds = 0;
			int crossings = 0;
			int totalLength = 0;
			int parallels = 0;
			
			for (int j=0; j<height; j++)
				for (int i=0; i<width; i++) {
					if (isZero(j,i))
						continue;
					else
						totalLength++;
					
					if (isDeadEnd(j, i))
						deadEnds++;
					
					if (isParallel(j, i))
						parallels++;
					else {					
						if (isCrossing(j, i) ) // only non-Parallel are count as crossings; 
							crossings++;
					}
				}

			this.deadEnds = deadEnds;		
			this.crossings = crossings;
			this.totalLength = totalLength;
			this.parallels = parallels;
		}
		
		public String toString() {
			String s;
			s  = "deadEnds  = "   + deadEnds    + "\n";
			s += "crossings = "   + crossings   + "\n";
			s += "totalLength = " + totalLength + "\n";
			s += "parallels = "   + parallels   + "\n";
			
			return s;
		}
	}
		
	public String toString() {
		String s = "";
		
		for (int j=0; j<height; j++) {
			s += " |";
			for (int i=0; i<width; i++) {
				if (data[j][i] == 0)
					s += " X";
				else
					//s += " " + data[j][i];
					s += "  ";
			}
			s += " |\n";
		}
		
		return s;
	}
	
	boolean isValidLocation(int j, int i) {
		if (j < 0 || i < 0 || j >= this.height || i >= this.width)
			return false;
		return true;
	}
	
	public int countNeighbors(int j, int i, boolean includeDiagonal) {
		int n = 0;
		
			if (isValidLocation(j-1,i) && data[j-1][i] != 0) n++; // UP
			if (isValidLocation(j+1,i) && data[j+1][i] != 0) n++; // DOWN
			if (isValidLocation(j,i+1) && data[j][i+1] != 0) n++; // RIGHT
			if (isValidLocation(j,i-1) && data[j][i-1] != 0) n++; // LEFT 
		
		if (includeDiagonal) {
			if (isValidLocation(j-1,i-1) && data[j-1][i-1] != 0) n++; // UP-LEFT 
			if (isValidLocation(j-1,i+1) && data[j-1][i+1] != 0) n++;// UP-RIGHT
			if (isValidLocation(j+1,i-1) && data[j+1][i-1] != 0) n++; // DOWN-LEFT
			if (isValidLocation(j+1,i+1) && data[j+1][i+1] != 0) n++; // DOWN-RIGHT
		}
		return n;
	}	
	
	public int countNeighborsOLD(int j, int i, boolean includeDiagonal) {
		int n = 0;
		
		try { 
			if (data[j-1][i] != 0) n++; // UP
		} catch (IndexOutOfBoundsException e) { };
		try {  
			if (data[j+1][i] != 0) n++; // DOWN
		} catch (IndexOutOfBoundsException e) { };
		try { 
			if (data[j][i+1] != 0) n++; // RIGHT
		} catch (IndexOutOfBoundsException e) { };
		try { 
			if (data[j][i-1] != 0) n++; // LEFT 
		} catch (IndexOutOfBoundsException e) { };
	
		if (includeDiagonal) {
			try { 
				if (data[j-1][i-1] != 0) n++; // UP-LEFT 
			} catch (IndexOutOfBoundsException e) { };
			try { 
				if (data[j-1][i+1] != 0) n++;// UP-RIGHT
			} catch (IndexOutOfBoundsException e) { };			
			try { 
				if (data[j+1][i-1] != 0) n++; // DOWN-LEFT
			} catch (IndexOutOfBoundsException e) { };			
			try { 
				if (data[j+1][i+1] != 0) n++; // DOWN-RIGHT
			} catch (IndexOutOfBoundsException e) { };			
		}
		return n;
	}
	
	public Statistics getStats() {
		return new Statistics(this);
	}
	
	public boolean isZero(int j, int i) {
		return data[j][i] == 0;
	}
	
	public boolean isDeadEnd(int j, int i) {
		if (data[j][i] == 0) {
			return false; // this is wall [X], not a dead-end;
		}
		return ( countNeighbors(j,i,false) <= 1 ); 
	}
	
	public boolean isCrossing(int j, int i) {
		if (data[j][i] == 0) {
			return false;
		}
		return ( countNeighbors(j,i,false) > 2 );
	}
	
	public boolean isParallel(int j, int i) {
		if (data[j][i] == 0) {
			return false;
		}
		
		 
				
		//try { // LEFT-UP CORNER
			if (isValidLocation(j-1,i-1) && data[j-1][i-1] != 0 && 
				isValidLocation(j-1,i) && data[j-1][i] != 0 && 
				isValidLocation(j,i-1) && data[j][i-1] != 0) return true;
		//} catch (IndexOutOfBoundsException e) {};
		//try { // RIGHT-UP CORNER
			if (isValidLocation(j-1,i+1) && data[j-1][i+1] != 0 &&
				isValidLocation(j-1,i) && data[j-1][i] != 0 && 
				isValidLocation(j,i+1) && data[j][i+1] != 0) return true;
		//	} catch (IndexOutOfBoundsException e) {};
		//try { // LEFT-DOWN CORNER
		      if (isValidLocation(j+1,i-1) && data[j+1][i-1] != 0 && 
		          isValidLocation(j+1,i) && data[j+1][i] != 0  &&
		          isValidLocation(j,i-1) && data[j][i-1] != 0) return true;
			//} catch (IndexOutOfBoundsException e) {};
		//try { // RIGHT-DOWN CORNER
		      if (isValidLocation(j+1,i+1) && data[j+1][i+1] != 0 && 
		    	  isValidLocation(j+1,i) && data[j+1][i] != 0 && 
		    	  isValidLocation(j,i+1) && data[j][i+1] != 0) return true;
			//} catch (IndexOutOfBoundsException e) {};
		return false;
	}
}
