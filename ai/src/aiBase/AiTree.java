package aiBase;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * AiTree is a tree that can be handled by AiAlg algorithms for searching trees. 
 * 
 * @author Witold Kaczurba 
 */
public class AiTree {
	AiTree parent;
	public AiObject data;
	public Hashtable<AiOperator, AiTree> children = new Hashtable<AiOperator, AiTree>();
	
	public AiTree(AiObject data) {
		this.data = data;
	}
	
	public AiTree add(AiObject data, AiOperator op) {
		AiTree node = new AiTree(data);
				
		if (children.containsKey(op))
			throw new UnsupportedOperationException("Node already has children with key named: " + op);
		children.put(op, node);
		node.parent = this;
		
		return node;		
	}
	
	public int generateChildren(int depth) {
		int count = 0;
		
		if (depth <= 0) {
			throw new UnsupportedOperationException("generateChildren(depth == " + depth + " <= 0)");
		}
		
		List<AiTree> nodes = new ArrayList<AiTree>();
		nodes.add(this);
		while (nodes.size() > 0) {
			AiTree node = nodes.remove(0);
			if (node.getDepth() == this.getDepth() + depth)
				continue;
			if (node.children.size() > 0)
				continue;
			
			node.generateChildren();
			for (AiTree newnodes : node.children.values()) {
				nodes.add(newnodes);
				count++;
			}
		}
		return count;
	}
	
	public int generateChildren() {
		int retval = 0;
		
		if (children.size() > 0) // Children already generated.
			return retval;
		
		@SuppressWarnings("unchecked")
		List <AiOperator> possible_moves = (List <AiOperator>)(List <?>) data.getAllPossibleOperators();
		for (AiOperator op : possible_moves) {
			this.add(data.applyOperator(op), op);
			retval++;
		}
		return retval;
	}
	
	public boolean is_root() { return parent==null;	}
	
	public AiTree root() {
		AiTree node = this;
		while (!node.is_root()) 
			node = node.parent;
		return node;
	}
	
	public int getDepth() {
		int depth = 0;
		AiTree node = this;
		while (!node.is_root()) {
			node = node.parent;
			depth++;
		}
		return depth;
	}
	
	// getOperator
	static public AiOperator getOperatorByNode(AiTree node) throws AiTreeException
	{
		if (node.is_root())
			return null;
		
		for (AiOperator op : node.parent.children.keySet())
		{
			if (node.parent.children.get(op) == node)
				return op;
		}	
		throw new AiTreeException("Invalid connection between parent and child.");
		
		//return null; // TODO: This should throw an exception (child connected to parent but not the other way around): 
		             //       e.g. throw new AiTreeException("Invalid connection between parent and child.");
	}
	
	// NOT USED SO FAR:
	public AiOperator getOperatorByValue(AiObject value) throws AiTreeException
	{
		for (AiOperator op : this.children.keySet())
		{
			if (this.children.get(op).data == value)
				return op;
		}	
		return null; // No such value;
	}

	public List<AiOperator> getPath()
	{ 
		List <AiOperator> path = new ArrayList<AiOperator>(); 
		AiTree node = this;
		
		while (!node.is_root()) {
			try {
				AiOperator op = AiTree.getOperatorByNode(node);
				path.add(0, op);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return path;
			}
			node = node.parent;
		}
		return path;
    }
	
	public String toString() {
		String s = new String();
		
		if (children.isEmpty()) {
			return this.data.toString();
		} else {
			s = this.data.toString();
			for (AiTree childNode : children.values()) {
				s = s + "\n    " + childNode.toString().replaceAll("\n", "\n    ");
			}
		}	
		return s;
	}
}
