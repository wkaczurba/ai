package aiBase;
import java.util.List;

/**
 * The class contains interface for state representation used by AIBase package.
 * 
 * @author Witold Kaczurba 
 */
public abstract class AiObject extends Object { // TODO: Should be an interface.
	public AiObject() { super(); }
	public AiObject (AiObject o) { throw new UnsupportedOperationException("AiObject.equals(obj) not implemented"); }
	public boolean equals(Object obj) { throw new UnsupportedOperationException("AiObject.equals(obj) not implemented"); }
	public List<?> getAllPossibleOperators() { throw new UnsupportedOperationException("AiObject.getAllPossibleOperators(obj) not implemented"); }
	public AiObject applyOperator(AiOperator op) { throw new UnsupportedOperationException("applyOperator not implemented"); } //new AiEl<AiOp>(); }
	public String toString() { throw new UnsupportedOperationException("toString not implemented"); }
	public int getHeuristic() { return 0; }
}

