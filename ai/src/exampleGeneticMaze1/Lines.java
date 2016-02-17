package exampleGeneticMaze1;

public class Lines {
	final int j, i, length;
	final boolean isHorizontal;
	
	public Lines(int j, int i, int length, boolean isHorizontal) {
		this.j = j;
		this.i = i;
		this.length = length;
		this.isHorizontal = isHorizontal;
	}
	
	// { j0 = y1, i0 = x1 }
	// { j1 = y2, i1 = x2 } 
	public boolean lineFitsRectagle(int j0, int i0, int j1, int i1) {
		if (j < j0) return false;
		if (i < i0) return false;
		
		if (isHorizontal) {
			if (i + length > i1 )
				return false;
		} else // Vertical:
			if (j + length > j1 )
				return false;
		
		return true;
	}
	
	public String toString() {
		return "<j=" + j + " i=" + i + " length=" + length + " isHorizontal=" + isHorizontal + ">";
	}
	
	public boolean equals(Object o) {
		//System.out.println("Checking equality...");
		
		if (o == null) {
			//System.out.println("o == null");
			return false;
		}

		if (! (o instanceof Lines) ) { // THIS DOES NOT WORK: if (! ( o.getClass().isInstance(Lines.class) )) {
			//System.out.println("Different types: " + o.getClass() + " vs. " + this.getClass());
			return false;
		}
		
		Lines ref = (Lines) o;
		if (this.j == ref.j &&
			this.i == ref.i &&
			this.length == ref.length &&
			this.isHorizontal == ref.isHorizontal)
			return true;
			
		return false;
	}
}
