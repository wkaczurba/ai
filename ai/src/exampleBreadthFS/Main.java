package exampleBreadthFS;

import java.util.Hashtable;

import aiBase.*;

public class Main {
	
	public Main() {
		AiTree root;

		//  This one works ok with BreadthFirstSearch, Best First Search:
		Puzzle3x3 p = new Puzzle3x3(  
				new int[][]{
					{1,3,8},
					{0,6,2},
					{5,4,7}});
			
		Puzzle3x3 goal = new Puzzle3x3(
				new int[][]{
					{1,2,3},
					{4,0,5},
					{6,7,8}});
			

	        root = new AiTree(p); //<Puzzle3x3, Puzzle3x3_op>(p);
			
			System.out.println("p = \n" + p);
			System.out.println("possible moves = " + p.getAllPossibleOperators().toString());
			System.out.println("heuristic:" + p.getHeuristic());
					
		    AiAlg.breadthFirstSearch(root, goal);
		    //AiAlg.DepthFirstSearch_to_export(root, goal);
		    //AiAlg.BestFirstSearch(root, goal);	 		
	}
	
	public static void main(String[] args) {
		new Main();
	}
			
}

