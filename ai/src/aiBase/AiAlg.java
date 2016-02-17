package aiBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The class contains basic Tree Search algorithms. 
 * 
 * @author Witold Kaczurba 
 */
public class AiAlg {
	private AiAlg() { } // do not call the constructor
	
	static public String print_indent(int indent, String s) {
		String s1 = new String();
		for (int i = 0; i<indent; i++)
			s1 += "  ";
		System.out.println(s1+s);
		return s1 + s;
	}
	
	static public void dbg_MiniMax_printHeuristicTree(AiTree root) {
		List<AiTree> nodes = new ArrayList<AiTree>();
		nodes.add(root);
		while (nodes.size() > 0) {
			AiTree node = nodes.remove(0);

			print_indent(node.getDepth()*2, ((Integer)node.data.getHeuristic()).toString() );
			
			if (node.children.size() != 0)
				for (AiTree n : node.children.values())
					nodes.add(0,n);
		}

	}
 
	// dbg_verbosity: 0 = silent; 1=basic; 2=full;
	static public int getMiniMax(AiTree root, Boolean getmax, int max_depth, int dbg_verbosity) {
		if (root.getDepth() == 1)
			dbg_MiniMax_printHeuristicTree(root);
		
		if (dbg_verbosity>0) System.out.println("Minimax -> depth=" + root.getDepth() + " root: \n" + root);
		
		if (root.getDepth() == max_depth || root.children.size() == 0) {
			return root.data.getHeuristic();
		} else {
			// find max or min -> of subnodes;
			List <Integer> sub_node_heuristics = new ArrayList<Integer>();
			for (AiTree child : root.children.values()) {
				sub_node_heuristics.add(getMiniMax(child, !getmax, max_depth, dbg_verbosity));
			}
			if (dbg_verbosity>0) System.out.println("Minimax -> depth=" + root.getDepth() + " root: \n" + root);

			if (dbg_verbosity == 2) System.out.println(sub_node_heuristics);
			Collections.sort(sub_node_heuristics);
			if (!getmax) {
				if (dbg_verbosity==2) System.out.println("  min -> chosen " + sub_node_heuristics.get(0));
				return sub_node_heuristics.get(0);
			}
			else
			{
				if (dbg_verbosity==2) System.out.println("  max -> chosen " + sub_node_heuristics.get(sub_node_heuristics.size()-1));
				return sub_node_heuristics.get(sub_node_heuristics.size()-1);
			}
		}
	}

	static public void breadthFirstSearch(AiTree root, AiObject goal) {
		List<AiTree> open = new ArrayList<AiTree>();
		List<AiTree> closed = new ArrayList<AiTree>();
		List<AiObject> checked_solutions = new ArrayList<AiObject>(); // TODO: To be removed
		
		open.add(root);		
		checked_solutions.add(root.data); // NEWLY ADDED.
		
		int iter = 0;
		boolean found_solution = false;
		while (open.size() > 0)// && (number_of_iter-- > 0))
		{
			AiTree X = open.remove(0); // remove leftmost state from open call it X
			if (X.data.equals(goal)) {
				found_solution = true; 
				System.out.println("Found solution! "+iter+" iteration \n" + X.data);
				System.out.println("Path: " + X.getPath() + "\n");
				
				System.out.println("Solution: Start:\n");
				AiObject r = X.root().data;
				System.out.println("Start:\n" + r);
				for (AiOperator op : X.getPath())
				{
					r = r.applyOperator(op);
					System.out.println("Op: " + op + ":\n" + r + "\n");
				}
				
				break;
			} else {
				// generate children of X;
				@SuppressWarnings("unchecked")
				List<AiOperator> possible_op = (List<AiOperator>)(List<?>)X.data.getAllPossibleOperators();

				for (AiOperator op : possible_op) {
					AiObject new_puzzle = X.data.applyOperator(op);
					boolean already_processed = false;
					
					/* Addition */
					for (AiObject p1 : checked_solutions)
						if (p1.equals(new_puzzle)) {
							already_processed = true;
							break;
						}
					
					if (!already_processed) {
						X.add(new_puzzle, op);
						checked_solutions.add(new_puzzle);
					}
				}
				closed.add(X);

				for (AiTree childnode : X.children.values()) {
					boolean already_processed = false; // indicates whether child is on open or closed list;
				
					// discard children of X if already on open or closed
					// put remaining children on right end of open.
				
					for (AiTree open_el : open)
					{
						if (open_el.data.equals(childnode.data)) {
							already_processed  = true;
							break;
						}
					}
					if (already_processed == false) {
						for (AiTree closed_el : closed)
						{
							if (closed_el.data.equals(childnode.data)) {
								already_processed = true;
								break;
							}
						}
						if (already_processed == false) {
							open.add(childnode);
						}
					}
				}		
			}
			iter++;
		}
		if (found_solution == false) {
			System.out.println("Could not find solution.");
		}		
	}
	
	@SuppressWarnings("unchecked")
	public static List<AiOperator> depthFirstSearch(AiTree root, AiObject goal) {
		List<AiTree> open = new ArrayList<AiTree>();
		List<AiTree> closed = new ArrayList<AiTree>();
		
		//List<AiTree> analysed_cases = new ArrayList<AiTree>();
		List<AiObject> checked_solutions = new ArrayList<AiObject>();
		AiTree X;
				
		open.add(root);
		checked_solutions.add(root.data);
		while (open.size() != 0) {
			// remove leftmost state from open.
			X = open.remove(0);
			System.out.println("Analysing level: \n" + X.getDepth() + " ops:" + X.getPath());
			System.out.println(X.data);
			
			if (X.data.equals(goal)) {
				System.out.println("Found solution!");
				System.out.println("Path: " + X.getPath());
				return X.getPath(); 
			} else {
				// generate children of X
				
				List<AiOperator> possible_op = (List<AiOperator>)(List<?>)X.data.getAllPossibleOperators();
				for (AiOperator op : possible_op) {
					AiObject newchild = X.data.applyOperator(op);
					X.add(newchild, op);
				}

				// put X on closed;
				closed.add(X);
				
				// discard children of X that are already on open or closed % loop check
				for (AiTree child : X.children.values()) {
					boolean alreadyProcessed = false;
					
					for (AiTree o : open) {
						if (o.equals(child))
							alreadyProcessed = true;
					}
					for (AiTree c : closed) {
						if (c.equals(child)) {
							alreadyProcessed = true;
						}
					}
					for (AiObject p1 : checked_solutions) {
						if (p1.equals(child.data))
							alreadyProcessed = true;
					}
					// put remaining children on left end of open %stack%
					if (!alreadyProcessed) {
						open.add(0,  child); // FOR DFS: open.add(0,  child);  FOR BFS: open.add(child);  
						checked_solutions.add(child.data);
					}
				}
			}
		}
		System.out.println("Solution not found...");
		return null;
	}	
	
	/**
	 * Same as depthFirstSearch but this one is with limit; THe (safetyLimit) limits the depth of tree exploration.
	 * 
	 * @param root
	 * @param goal
	 * @param safetyDepthLimit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<AiOperator> depthFirstSearchWithLimit(AiTree root, AiObject goal, int safetyDepthLimit) {
		List<AiTree> open = new ArrayList<AiTree>();
		List<AiTree> closed = new ArrayList<AiTree>();
		
		//List<AiTree> analysed_cases = new ArrayList<AiTree>();
		List<AiObject> checked_solutions = new ArrayList<AiObject>();
		AiTree X;
				
		open.add(root);
		checked_solutions.add(root.data);
		while (open.size() != 0) {
			// remove leftmost state from open.
			X = open.remove(0);
			
			// FIXME: This does not work
			if (X.getDepth() > safetyDepthLimit - 1) {
				System.out.println("Algorithm has already reached depth: " + safetyDepthLimit + " - terminating...");
				break;
			}
			
			System.out.println("Analysing level: \n" + X.getDepth() + " ops:" + X.getPath());
			System.out.println(X.data);
			
			if (X.data.equals(goal)) {
				System.out.println("Found solution!");
				System.out.println("Path: " + X.getPath());
				return X.getPath(); 
			} else {
				// generate children of X
				
				List<AiOperator> possible_op = (List<AiOperator>)(List<?>)X.data.getAllPossibleOperators();
				for (AiOperator op : possible_op) {
					AiObject newchild = X.data.applyOperator(op);
					X.add(newchild, op);
				}

				// put X on closed;
				closed.add(X);
				
				// discard children of X that are already on open or closed % loop check
				for (AiTree child : X.children.values()) {
					boolean alreadyProcessed = false;
					
					for (AiTree o : open) {
						if (o.equals(child))
							alreadyProcessed = true;
					}
					for (AiTree c : closed) {
						if (c.equals(child)) {
							alreadyProcessed = true;
						}
					}
					for (AiObject p1 : checked_solutions) {
						if (p1.equals(child.data))
							alreadyProcessed = true;
					}
					// put remaining children on left end of open %stack%
					if (!alreadyProcessed) {
						open.add(0,  child); // FOR DFS: open.add(0,  child);  FOR BFS: open.add(child);  
						checked_solutions.add(child.data);
					}
				}
			}
		}
		System.out.println("Solution not found...");
		return null;
	}		
	
	
	public static List<AiOperator> bestFirstSearch(AiTree root, AiObject goal) {
		List<AiTree> open = new ArrayList<AiTree>();
		List<AiTree> closed = new ArrayList<AiTree>();
		
		open.add(root);		
		
		AiTree X;
		int limit = 200;
		
		while (open.size() > 0 && limit-- > 0) {
			X = open.remove(0);
			
			System.out.println("Analysing: \n" + X.data + " depth=" + X.getDepth() + " Path=" + X.getPath());
			if (X.data.equals(goal)) {
				System.out.println("Found solution: " + X.getPath());
				return X.getPath();
			} else {
				// Generate children of X:
				AiObject valid_child = null;
				
				@SuppressWarnings("unchecked")
				List<AiOperator> possible_op = (List<AiOperator>)(List<?>)X.data.getAllPossibleOperators();

				for (AiOperator op : possible_op) {				
					if (X.children.containsKey(op))
						break; // the children are already generated for this node

					valid_child = X.data.applyOperator(op);

					AiTree OnOpen = null;
					for (AiTree o : open) {
						if (o.data.equals(valid_child)) {
							OnOpen = o; break;
						}
					}
					AiTree OnClosed = null;					
					for (AiTree c : closed) {
						if (c.data.equals(valid_child)) {
							OnClosed = c; break;
						}
					}
					
					// Analysis of 3 cases:
					if (OnOpen==null && OnClosed==null) {
						// { assign heuristic value, add to open }
						//X.add(valid_child, op); // NEED TO GET OP for that possible_child.
						open.add(X.add(valid_child, op));
						
					} else if (OnOpen != null) {
						// if the child was reached by a shorter path then give the state on open shorter papth
						if (OnOpen.getDepth() > X.getDepth() + 1) {
							open.set(open.indexOf(OnOpen),  X.add(valid_child, op)); // Swap -> put X instead of OnOpen in the Open list;
						}
					} else if (OnClosed != null) {
						// if the child was reached earlier -> remove it from close;
						// add shorter one to open.
						if (OnClosed.getDepth() > X.getDepth() + 1) {
							closed.remove(closed.indexOf(OnClosed));
							open.add(X.add(valid_child, op));
						}

					}					
				}
				
			}
			// SORT BY HEURISTIC MERIT.
			Collections.sort(open, new Comparator< AiTree >(){
			    public int compare(AiTree a, AiTree b) {
			    	return (int)( (a.data.getHeuristic() + a.getDepth()) - (b.data.getHeuristic() + b.getDepth()) ); // HERE WAS THE BUG! (+a.getDepth(); +b.getDepth() were missing)
			    }
			});			
		}
		return null;
	}	
}

