package exampleAlphaBetaPrunning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The example demonstrates AlphaBetaPrunning.
 * It uses the following tree structure:
 * 
 * <PRE>
 *                                                                                   (   )   ROOT   
 *                                                                             __ /        \ __
 *                                                                        __ /                  \
 *                                                                   __ /                        \ __
 *                                                              __ /                                  \ __
 *                                                        ___ /                                             \__
 *                                                      /                                                        \     
 *                                    PATH: [0]  (  )                                                                (   ) PATH: [1]
 *                                             /      \                                                             /     \
 *                                          /            \                                                         /       \
 *                                        /                 \                                                     /         \
 *                                      /                      \                                                 /           \
 *                                   /                           \                                              /             \
 *                                /                                 \                                          /               \
 *                             /                                       \                                      /                 \
 *              PATH:[0,0]   (  )                         PATH: [0,1]  (   )                   PATH: [1,0](  )                  (  ) PATH[1,1]
 *                         /     \                                      / \                                / \                    \
 *                        /       \                                    /   \                              /   \                    \
 *                       /         \                                  /     \                            /     \                    \                                                         
 *                      /           \                                /       \                          /       \                    \                
 *                     /             \                              /         \                        /         \                    \        
 *                    /               \                            /           \                     /            \                    \
 * PATH: [0,0,0]   ( )       P:[0,0,1] ( )           P: [0,1,0] ( )    [0,1,1]( )          [1,0,0]( )            ( )[1,0,1]           ( ) P:[1,1,1]
 *                /    \             /      \                   /            /    \              /   \              \                /    \
 *               /      \           /        \                 /            /       \            /      \             \             /        \
 *              3        17        2          12              15          25          0        2         5             3           2          14
 *       [0,0,0,0]  [0,0,0,1]  [0,0,1,0]    [0,0,1,1]   [0,1,0,0]    [0,1,0,1]   [0,1,1,0] [1,0,0,0] [1,0,0,1]      [1,0,1,0]    [1,1,1,0]  [1,1,1,1]
 *  </PRE>
 * <p>
 * Tree structure taken from: http://web.cs.ucla.edu/~rosen/161/notes/alphabeta.html
 * You can refer to that webpage to see how it is build.
 * </p>
 * 
 * @author Witold Kaczurba
 */
public class Main {
    
	// 
	// 
	// 
	
    /* This function builds following tree:
                                                                                   (   )   ROOT   
                                                                             __ /        \ __
                                                                        __ /                  \
                                                                   __ /                        \ __
                                                              __ /                                  \ __
                                                        ___ /                                             \__
                                                      /                                                        \     
                                    PATH: [0]  (  )                                                                (   ) PATH: [1]
                                             /      \                                                             /     \
                                          /            \                                                         /       \
                                        /                 \                                                     /         \
                                      /                      \                                                 /           \
                                   /                           \                                              /             \
                                /                                 \                                          /               \
                             /                                       \                                      /                 \
              PATH:[0,0]   (  )                         PATH: [0,1]  (   )                   PATH: [1,0](  )                  (  ) PATH[1,1]
                         /     \                                      / \                                / \                    \
                        /       \                                    /   \                              /   \                    \
                       /         \                                  /     \                            /     \                    \                                                         
                      /           \                                /       \                          /       \                    \                
                     /             \                              /         \                        /         \                    \        
                    /               \                            /           \                     /            \                    \
 PATH: [0,0,0]   ( )       P:[0,0,1] ( )           P: [0,1,0] ( )    [0,1,1]( )          [1,0,0]( )            ( )[1,0,1]           ( ) P:[1,1,1]
                /    \             /      \                   /            /    \              /   \              \                /    \
               /      \           /        \                 /            /       \            /      \             \             /        \
              3        17        2          12              15          25          0        2         5             3           2          14
       [0,0,0,0]  [0,0,0,1]  [0,0,1,0]    [0,0,1,1]   [0,1,0,0]    [0,1,0,1]   [0,1,1,0] [1,0,0,0] [1,0,0,1]      [1,0,1,0]    [1,1,1,0]  [1,1,1,1]
*/                
	
	private AlphaBetaTree getTree() {
		AlphaBetaTree left = new AlphaBetaTree (
				Arrays.asList( new AlphaBetaTree(Arrays.asList(new AlphaBetaTree(AlphaBetaTree.fromArray(Arrays.asList(3,17))), new AlphaBetaTree(AlphaBetaTree.fromArray(Arrays.asList(2,12))))),
				               new AlphaBetaTree(Arrays.asList(new AlphaBetaTree(AlphaBetaTree.fromArray(Arrays.asList(15))), new AlphaBetaTree(AlphaBetaTree.fromArray(Arrays.asList(25,0))))))
					);
			// right (((2 5) (3)) ((2 14)))
			AlphaBetaTree right = new AlphaBetaTree (
					Arrays.asList(new AlphaBetaTree (Arrays.asList(new AlphaBetaTree(AlphaBetaTree.fromArray(Arrays.asList(2, 5))), new AlphaBetaTree(AlphaBetaTree.fromArray(Arrays.asList(3))) )),
							      new AlphaBetaTree (Arrays.asList(new AlphaBetaTree(AlphaBetaTree.fromArray(Arrays.asList(2,14))))) )
				);
			AlphaBetaTree root = new AlphaBetaTree(Arrays.asList(left, right));
		return root;
	}
	
	public Main() {
		AlphaBetaTree root = getTree();
		
		System.out.println(root);
		
		Double res = alphabeta2(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

		System.out.println("\n Finally... Resulting value: " + res);
	}

	
	public Double alphabeta2(AlphaBetaTree root, Double alpha, Double beta) { 
		
		if (!root.hasChildren()) {
			System.out.println(root.getPath() + " = " + root.value);
			return root.value;
		}
		
		List <Double> values = new ArrayList<Double>(); 
		for (AlphaBetaTree child : root.children) {
			Double v = alphabeta2(child, alpha, beta);
			if (root.isMaxNode()) {
			    alpha = Math.max(alpha,  v);
			} else {
				beta = Math.min(beta,  v);
			}			

			// breaking here. 
			if (v < alpha || v > beta) {
				values.add(v);
				System.out.println("[REJECTING PATH NOT METING ALPHA-BETA ] -> " + child.getPath());
				break;
			}
			
			values.add(v);
		}
		
		Double retval;
		if (root.isMaxNode())
			retval = Collections.max(values);
		else 
			retval = Collections.min(values);

		System.out.println(root.getPath() + " = " + retval);
		return retval;
		
	}
	
	public static void main(String[] args) {
		new Main();
	}	
}

