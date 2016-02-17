package aiBase;

/**
 * The class contains interface for operator representation used by AIBase package.
 * 
 * @author Witold Kaczurba 
 */
abstract public class AiOperator {
	public boolean equals(Object o)
	{
		throw new UnsupportedOperationException("equals(Object) not implemented");
	}

	@Override public String toString() {
		throw new UnsupportedOperationException("toString not implemented");
	}	
}
