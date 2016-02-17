package exampleAlphaBetaPrunning;

import java.util.ArrayList;
import java.util.List;

import aiGen.GenomeOperators;

/**
 * alphaBetaTree - simplified algorithm.
 * 
 * @author Witold Kaczurba 
 */
class AlphaBetaTree {
	AlphaBetaTree parent = null;
	Double value = null; // value;
	List <AlphaBetaTree> children = new ArrayList <AlphaBetaTree>();

	double alpha = Double.NEGATIVE_INFINITY;
	double beta = Double.POSITIVE_INFINITY;
	
	public boolean isMaxNode() {
		return (getDepth() % 2) == 0;
	}
	
	public void setAlpha(Double alpha) {
		if (alpha == null || Double.isNaN(alpha)) {
				System.out.println("   setAlpha skipped " + getPath());
				return; // invalid.
		}
		
		if (alpha > this.alpha ) {
			System.out.println("-- setting alpha="+alpha+" for " + getPath());
			this.alpha = alpha;
		}
	}
	public void setBeta(Double beta) {
		if (beta == null || Double.isNaN(beta))
		{
                System.out.println("   setBeta skipped " + getPath());
				return; // invalid.
		}
		if (beta < this.beta ) {
			System.out.println("-- setting beta="+beta+" for " + getPath());
			this.beta = beta;
		}
	}
	public Boolean isAlphaBetaValid() {
		if (alpha > beta) {
			return false;
		}
		return true;
	}
	
	public AlphaBetaTree() {
	}
	private AlphaBetaTree(Double value) {
		this.value = value;
	}
	public AlphaBetaTree(List <AlphaBetaTree> children) {
		for (AlphaBetaTree child : children) {
			this.add(child);
		    child.parent = this;
		}
	}
	static public List<AlphaBetaTree> fromArray(List<Integer> values) {
		List <AlphaBetaTree> arr = new ArrayList<AlphaBetaTree>();
		for (Integer value : values)
			arr.add(new AlphaBetaTree((double) value));
		return arr;
	}
	public Boolean isRoot() {
		return parent == null;
	}
	public int getDepth() {
		AlphaBetaTree node = this;
		int depth = 0;
		while ((node = node.parent) != null)
			depth++;
		return depth;
	}
	public void add(AlphaBetaTree child) {
		this.children.add(child);
		child.parent = this;
	}
	public String toString() {
		String s = "";
		
		for (int i=0; i<this.getDepth(); i++)
			s += " ";
		
		if (value != null)
			s += value.toString() + "\n";
		else
			s += "x\n";
		
		for (AlphaBetaTree child : children)
		    //s = s + " " + child.toString().replaceAll("\n", "\n ");
			s = s + child.toString();//.replaceAll("\n", "\n ");
		return s;
	}
	public List<Integer> getPath() {
		List <Integer> l = new ArrayList<Integer>();
		AlphaBetaTree node = this;
		while (node.parent != null) {
			l.add(0, node.parent.children.indexOf(node));
			node = node.parent;
		}
		return l;
	}
	public AlphaBetaTree nextSibling() {
		if (parent == null)
			return null;
		
		int thisChild = parent.children.indexOf(this); 
		if ( thisChild + 1 < parent.children.size() ) {
			return parent.children.get(thisChild+1);
		}
		return null;
	}
	public AlphaBetaTree getDeepestFirst() {
		AlphaBetaTree node = this;
		while (node.children.size() > 0)
			node = node.children.get(0);
		return node;
	}
	public AlphaBetaTree up() {
		return parent;
	}
	public boolean meetingAlphaBeta() {
		if (alpha <= value && value <= beta)
			return true;
		return false;
	}
	
	public AlphaBetaTree next() {
		//AlphaBetaTree node = this;
		if (parent == null)
			return null; // or getDeepestFirst();

		if (nextSibling() != null)
			return nextSibling().getDeepestFirst();
		else
			return parent;
	}
	
	public List<Double>getChildrenValues() {
		List<Double> l = new ArrayList<Double>();
		for (AlphaBetaTree d : children) {
			if (d.value != null)
				l.add(d.value);
		}
		return l;
	}
	public boolean hasChildren() {
		return children.size() > 0;
	}
}
