package exampleMiniMax;

import aiBase.*;

public class TicTacToe_op extends AiOperator {
	//static List<TicTacToe_op> allOperators; 
	int j;
	int i;
	
	public boolean equals(Object o)
	{
		if (o instanceof TicTacToe_op) {
			TicTacToe_op op = (TicTacToe_op) o;
			return (this.i == op.i && this.j == op.j);
		}
		throw new UnsupportedOperationException("toString not implemented");
	}	

	public TicTacToe_op(int j, int i) {		
		assert (j >= 0 && j < 3);
		assert (i >= 0 && i < 3);
		this.j = j;
		this.i = i;
	}

	@Override public String toString() {
		return new String("op (j="+j+" i="+i+")");
	}		
}	

