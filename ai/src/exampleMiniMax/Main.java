package exampleMiniMax;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import aiBase.AiAlg;
import aiBase.AiTree;

/**
 * Simple demo of MiniMax
 * 
 * @author Witold Kaczurba
 *
 */
public class Main {
	
	interface UserInterface {
		enum Result {
			DRAW, WON, LOST
		};
		
		public void displayBoard(TicTacToe data);
		public void displayGameFinished(Result result);
		public void displayInvalidMove(List<TicTacToe_op> possibleMoves);

		public TicTacToe_op userMove() throws InterruptedException;
	};
	
	interface DebugInterface {
		public void println(String s);
	}
	
	DebugInterface di;
	UserInterface ui;
	
	public Main(UserInterface ui, DebugInterface di) {
		this.ui = ui;
		this.di = di;
		play();
	}
	
	public void play() {
		TicTacToe tttempty = new TicTacToe("   " +
                "   " +
                "   ");		
		
		AiTree tttroot = new AiTree(tttempty);
		AiTree node = tttroot;
		Boolean computer_turn = false;
	
		// FIXME: gameOver should be synonymous with getAllPossibleOperators() == 0
		while (!((TicTacToe) (node.data)).gameOver() && node.data.getAllPossibleOperators().size()>0) {
			
			//WAS: System.out.println(node.data); // IF: DISPLAY. 
			ui.displayBoard((TicTacToe) node.data);
		
			if (computer_turn == false)
			{
				Boolean moved = false;
				while (!moved) {
					//WAS: TicTacToe_op op = getInputJI(); // IF: GET_INPUT.
					TicTacToe_op op;
					try {
						op = ui.userMove();
					} catch (InterruptedException e) {
						return; // UserMove was interrutped -> terminating...
					}
				
					List <TicTacToe_op> possible = ((TicTacToe) (node.data)).getAllEmpty();
					for (TicTacToe_op p : possible) {
						if (p.equals(op)) {
							node = node.add((TicTacToe)(node.data).applyOperator(op), op);
							moved = true;
						}
					}
					if (!moved) {
						ui.displayInvalidMove(possible); // WAS: System.out.println("Not moved...; all possible=" + possible); // IF: DISPLAY INVALID MOVE
					}
				}
			}
			
			if (computer_turn) {
				TicTacToe_op computer_op = computerMove(new AiTree(node.data));
				
				node = node.add(node.data.applyOperator(computer_op), computer_op);
						
				// System.out.println("TODO: Computer move here...\n");
			}
			
			if (((TicTacToe)(node.data)).gameOver()){
				
				ui.displayBoard((TicTacToe) node.data); //WAS: System.out.println(node.data); // IF: DISPLAY NODE
				
				if (computer_turn)
					ui.displayGameFinished(UserInterface.Result.LOST); // WAS: System.out.println("you lost..."); // WIN OR LOST IF
				else
					ui.displayGameFinished(UserInterface.Result.WON); // WAS: System.out.println("you won...");
				
			} else if (node.data.getAllPossibleOperators().size() == 0) {
				
				// Not game over but no possible moves.
				ui.displayBoard((TicTacToe) node.data); // WAS: System.out.println(node.data);
				ui.displayGameFinished(UserInterface.Result.DRAW); // WAS: System.out.println("draw..."); // OR DRAW.
			}

		computer_turn = !computer_turn;
		}					
	}
	
	static private void test_minimax() {
		TicTacToe tttempty = new TicTacToe("   " +
                " o " +
                "   ");		
		
		AiTree tttroot = new AiTree(tttempty);
		tttroot.generateChildren(3);
		//System.out.println(tttroot);
		
		int x = AiAlg.getMiniMax(tttroot, true, 2, 0); // top is max;
		System.out.println("Minimax_value="+x);
	}

	public static void main(String[] args) {
		DebugInterface di = new DebugInterface() {
			@Override
			public void println(String s) {
				System.out.println(s);
			}
		};
		
		UserInterface ui = new UserInterface() {

			@Override
			public void displayBoard(TicTacToe data) {
				System.out.println(data);
			}

			@Override
			public void displayGameFinished(Result result) {
				switch (result) {
				case LOST: System.out.println("you lost..."); break;
				case WON:  System.out.println("you won..."); break;
				case DRAW: System.out.println("draw..."); break;
				default: break;
				}
			}

			@Override
			public TicTacToe_op userMove() {
				Scanner reader = new Scanner(System.in);
				System.out.println("Which location (y,x)??");
				String s[] = reader.next().split(",");
				//reader.close();
				return new TicTacToe_op(new Integer(s[0]), new Integer(s[1]));
			}

			@Override
			public void displayInvalidMove(List<TicTacToe_op> possibleMoves) {
				System.out.println("Not moved...; all possible=" + possibleMoves);
			}
			
		};		
		
		new Main(ui, di);
		//test_minimax();
	}
	
	private TicTacToe_op computerMove(final AiTree node) 
	{
		List <TicTacToe_op> possible_moves = ((TicTacToe) (node.data)).getAllPossibleOperators();

		// For each of the possible moves -> get_min_max();
		di.println("Considering the moves: "); // WAS: System.out.println("Considering the moves: ");
		
		final boolean isLookingForMax = false; // Computer should be  looking for minimum.
		final int plys = 2;
		
		// TODO: Change this so it is more clearly visible.
		// begin of NEW ADDITION:
		Hashtable<TicTacToe_op, Integer> options = new Hashtable<TicTacToe_op, Integer>(); // Opeartor, Heuristic Value
		for (TicTacToe_op op : possible_moves)
		{
			AiTree subtree = new AiTree(node.data.applyOperator(op));
			subtree.generateChildren(plys);
					
			Integer value = AiAlg.getMiniMax(subtree, !isLookingForMax, plys, 0);
			options.put(op, value);
		}
		di.println(options.toString()); //WAS: System.out.println(options);
		
		
		int pick;
		if (isLookingForMax) {
			pick = Collections.max(options.values());
			di.println("isLookingForMax = " + isLookingForMax + " ; pick=" + pick); // WAS: System.out.println("isLookingForMax = " + isLookingForMax + " ; pick=" + pick);
		} else {
			pick = Collections.min(options.values());
			di.println("isLookingForMax = " + isLookingForMax + " ; pick=" + pick); // WAS: System.out.println("isLookingForMax = " + isLookingForMax + " ; pick=" + pick);
		}
		for (TicTacToe_op op : options.keySet())
			if (options.get(op) == pick) {
				di.println("Picked: " + op); // WAS: System.out.println("Picked: " + op);
				return op;
			}
		throw new ArrayIndexOutOfBoundsException("Problem with Hashtable...");
	}
}


