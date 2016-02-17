package exampleBreadthFS;

import aiBase.AiOperator;

public class Puzzle3x3_op extends AiOperator {
	//String data = "LEFT";
	
	public enum Data {
		LEFT, RIGHT, UP, DOWN;
	};
	
	Data data;
	
	public Puzzle3x3_op(Data d) {
		this.data = d;
	}
		
	public boolean equals(Object o)
	{
		if (o instanceof Puzzle3x3_op) {
			Puzzle3x3_op op = (Puzzle3x3_op) o;
			return (this.data == op.data);
		} else if (o instanceof Data) {
			return (this.data == (Data) o);
		}
		throw new UnsupportedOperationException("toString not implemented");
	}	
	
	@Override public String toString() {
		return new String(data.name());
	}		
}
