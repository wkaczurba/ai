package exampleMiniMax;

import java.util.ArrayList;
import java.util.List;
import aiBase.*;

public class TicTacToe extends AiObject {
	String data = ("   " +
                   "   " +
                   "   ");
	public TicTacToe() { 
		isValid();		
	}
	public TicTacToe(String data) {
		this.data = data.toLowerCase();
		isValid();
	}
	public TicTacToe(TicTacToe a)
	{
		this.data = new String(a.data.toLowerCase());
	}	
	protected char get(int j, int i) {
		char value;
		assert (j >= 0 && j < 3);
		assert (i >= 0 && i < 3);
		value = data.charAt(j*3+i);
		assert (value=='o' || value == 'x' || value==' ');
		return value;
	}
	public int getCount(char x_or_o) {
		int cnt = 0;
		for (char c : data.toCharArray()) {
			if (c == x_or_o)
				cnt++;
		}
		return cnt;
	}
	public void isValid() {
		int x=getCount('x');
		int o=getCount('o');
		int empty=getCount(' ');
		
		if (x+o+empty != 9) {
			throw new UnsupportedOperationException("Invalid set");
		} else if (Math.abs(x-o) > 1) {
			throw new UnsupportedOperationException("Invalid set");
		}
	}
	
	// Checks if list contains duplicates: 
	static public List<TicTacToe> findDuplicates(List<TicTacToe> l ) {
        // ***********************************
        // The below one removes duplicates:
        List<TicTacToe> duplicates = new ArrayList<TicTacToe>();

        for (int i=0; i<l.size(); i++) {
        	for (int j=i+1; j<l.size();j++) {
        		TicTacToe t1 = l.get(i);
        		TicTacToe t2 = l.get(j);
        		if (t1.equals(t2)) {
        			// THINGS DO EQUAL
        			duplicates.add(t1);
        		}
        	}
        }
        return duplicates; // -> can use: l.removeAll(duplicates); to get rid of the duplicates; 
	}

	public List<TicTacToe_op> getAllEmpty() {
		List<TicTacToe_op> possible = new ArrayList<TicTacToe_op>();
		
		if (gameOver())
			return possible; // returns empty list;
		
		for (int j=0; j<3; j++)
			for (int i=0; i<3; i++) 
			{
				if (get(j,i) == ' ')
					{
						possible.add(new TicTacToe_op(j,i));
					}
			}		
		return possible;
	}
	
	public List<TicTacToe_op> getAllPossibleOperators() { // public List<AiOperator> getAllPossibleOperators() {
		List<TicTacToe_op> possible = getAllEmpty();
		
		// Eliminate symmetrical results: (TRICKY)
		int i = 0;
		while (i < possible.size()) {
			int j = i+1;
			while (j < possible.size()) {
				TicTacToe t1 = (TicTacToe) applyOperator(possible.get(i));
				TicTacToe t2 = (TicTacToe) applyOperator(possible.get(j));
				if (t1.equals(t2) && i!=j) {
					//DBG: System.out.println("Two operators produce similar results ("+possible.get(i)+"; "+possible.get(j)+") -> deleting:\n" + t1 + "\n" + t2);
					possible.remove(j);
				} else {
					j++;
				}
			}
			i++;
		}
		return possible; //return (List<AiOperator>)(List<?>) possible;
	}
	
	char whoIsNext() {
		if (getCount('x') > getCount('o'))
			return 'o';
		return 'x';
	}

	// NEW IMPLEMENTATION: 
	public AiObject applyOperator(AiOperator op) {
		char x_or_o = whoIsNext();
		TicTacToe_op t_op = (TicTacToe_op) op;
		
		assert (data.charAt(t_op.j*3+t_op.i) == ' ');
		assert (x_or_o == 'o' || x_or_o == 'x');
		
		// Setting the value
		String newdata = new String(data.substring(0, t_op.j*3+t_op.i) + x_or_o);
		if (t_op.j*3+t_op.i+1 < 9) // retval.data
			newdata += data.substring(t_op.j*3+t_op.i+1, 9);

		return new TicTacToe(newdata);				
		//return retval;
	}	
	
	public Boolean gameOver() {
		return (getHeuristic() == -1000 || getHeuristic() == 1000);
	}

	public int getHeuristic() {
		List<char[]> lines = new ArrayList<char[]>();
		// X starts;
		// one line with 2X, 0O => +3
		// one line with 1X, 0O => +1
		// one line with 0x, 1O => -1
		// one line with 0x, 2O => -3
		// TODO: What about lost or win?
		
		for (int j=0; j<3; j++) {  
			lines.add( new char[]{ get(j,0), get(j,1), get(j,2) } );
		}

		for (int i=0; i<3; i++) {
			lines.add( new char[]{ get(0,i), get(1,i), get(2,i) } );
		}
		
		lines.add( new char[]{ get(0,0), get(1,1), get(2,2) } );
		lines.add( new char[]{ get(2,0), get(1,1), get(0,2) } );

		int h = 0;
		for (char[] line : lines) {
			int x = 0;
			int o = 0;
			for (char c : line) {
				switch(c) {
					case 'x': x++; break;
					case 'o': o++; break;
					default: break;
				}
				if (x == 3) {
					//throw new UnsupportedOperationException("Node already has children with key named: ");
					//System.out.println("Is finished (3x)"); // This should be handled smarter;
					return 1000;
				}
				if (o == 3) {
					//throw new UnsupportedOperationException("Node already has children with key named: ");
					//System.out.println("Is finished (3o)"); // This should be handled smarter...
					return -1000;
				}
				if (x == 2 && o == 0) h+=3;
				if (x == 1 && o == 0) h+=1;
				if (x == 0 && o == 2) h-=3;
				if (x == 0 && o == 1) h-=1;
			}
		}
		return h;
	}
	
	public String toString() {
		String s = new String();
		s =  get(0,0) + "|" + get(0,1) + "|" + get(0,2) + "\n";
		s += "-+-+-\n";
		s += get(1,0) + "|" + get(1,1) + "|" + get(1,2) + "\n";
		s += "-+-+-\n";
		s += get(2,0) + "|" + get(2,1) + "|" + get(2,2) + "\n";
				
		s += "heuristic: " + this.getHeuristic() + "\n";
		
		// FLATTEN:
		//s = s.replace("\n", ",");
		return s;
	}
	
	public boolean isSymmetrical(TicTacToe o) {
		// symmetry across:
		//   \ / - |
		
		//          A
		//   \      xB
		//          xxC
		
		/*System.out.println(
		"(get(1,0) == " + this.get(1,0) +
		"== o.get(0,1) == " + o.get(0,1) + 
		")\n (get(2,0) == " + this.get(2,0) +
		"== o.get(0,2) == " + o.get(0,2) +  
		")\n (get(2,1) == " + this.get(2,1) +
		"== o.get(1,2) == " + o.get(1,2) +
		")\n");*/
		
		if  (  get(1,0) == o.get(0,1) &&   get(2,0) == o.get(0,2) && get(2,1)   == o.get(1,2) &&
		     o.get(1,0) == get(0,1)   && o.get(2,0) ==   get(0,2) && o.get(2,1) == get(1,2) &&
		       get(0,0) == o.get(0, 0)&&   get(1,1) == o.get(1, 1)&& get(2,2) == o.get(2, 2))

			return true;
 			
		//          xxC
		//   /      xB 
		//          A
		if (get  (0,0)   == o.get(2,2) &&   get(1,0) == o.get(2,1) &&   get(0,1) == o.get(1,2) &&
		    o.get(0,0) ==     get(2,2) && o.get(1,0) ==   get(2,1) && o.get(0,1) ==   get(1,2) &&
		    get(2,0)   ==   o.get(2,0)&&    get(1,1) == o.get(1,1) &&   get(0,2)  == o.get(0,2))
			return true;
		
		//   |

		if (get(0,0)   == o.get(0,2) &&   get(1,0) == o.get(1,2)  &&   get(2,0) == o.get(2,2) &&
		    o.get(0,0) == get(0,2)   && o.get(1,0) ==   get(1,2)  && o.get(2,0) ==   get(2,2) &&
		    get(0,1) == o.get(0, 1)  &&   get(1,1) == o.get(1, 1) &&   get(2,1) == o.get(2, 1))
			return true;
		
		// -
		if (  get(0,0) == o.get(2,0) &&   get(0,1) == o.get(2,1) &&   get(0,2) == o.get(2,2) &&
			o.get(0,0) ==   get(2,0) && o.get(0,1) ==   get(2,1) && o.get(0,2) ==   get(2,2) &&
			  get(1,0) == o.get(1,0) &&   get(1,1) == o.get(1,1) &&   get(1,2) == o.get(1,2))
			return true;
		return false;
	}
	
	
	public boolean equals(Object o) {
		if (o instanceof TicTacToe) {
			TicTacToe t = (TicTacToe) o;
			return (this.data == t.data || isSymmetrical(t));
		} else 
			return false;
    }
	
}
