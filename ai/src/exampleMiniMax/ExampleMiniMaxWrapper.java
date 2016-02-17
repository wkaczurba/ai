package exampleMiniMax;

import java.awt.Font;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;

import exampleMiniMax.Main.DebugInterface;
import exampleMiniMax.Main.UserInterface;
import exampleMiniMax.Main.UserInterface.Result;
import examples.Example;

// TODO: Move interfaces into GUI Pan's frame.
public class ExampleMiniMaxWrapper extends Example {
	
	Pan internalPanel;
		
	// FIXME: This has to be redone for the GUI.
	DebugInterface di = new DebugInterface() {
		@Override
		public void println(String s) {
			internalPanel.debugTextArea.append(s + "\n"); //System.out.println(s);
		}
	};
	
	// FIXME: This has to be redone for the GUI.
	UserInterface ui = new UserInterface() {

		@Override
		public void displayBoard(TicTacToe data) {
			//System.out.println(data);
			
			internalPanel.imagePanel.setBoard(data); // FIXME: Direct call is not proper
		}

		@Override
		public void displayGameFinished(Result result) {
			String s = null;
			switch (result) {
			case LOST: s = "you lost..."; break;
			case WON:  s = "you won..."; break;
			case DRAW: s = "draw..."; break;
			default: break;
			}
			internalPanel.scoreTextArea.append(s + "\n");
		}

		@Override
		public TicTacToe_op userMove() throws InterruptedException {
			/*Scanner reader = new Scanner(System.in);
			System.out.println("Which location (y,x)??");
			String s[] = reader.next().split(",");
			return new TicTacToe_op(new Integer(s[0]), new Integer(s[1]));*/
			
			TicTacToe_op op = null;
			
			//System.out.println("Waiting for the click...");
			
			internalPanel.imagePanel.resetClick();
			op = internalPanel.imagePanel.getClick();
			while (op == null) {
				try {
					Thread.sleep(200);
					op = internalPanel.imagePanel.getClick();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("ExampleMiniMaxWrapper.userMove->got interrupted - terminating.");
					return null;
				}
			}
			return op;
		}

		@Override
		public void displayInvalidMove(List<TicTacToe_op> possibleMoves) {
			internalPanel.scoreTextArea.append("Not moved...; all possible=" + possibleMoves + "\n");
			//System.out.println("Not moved...; all possible=" + possibleMoves);
		}
	};
	
	@Override
	public String getName() {
		return "MiniMax";
	}

	@Override
	public String getInfo() {
		return "Simple game showing MiniMax algorithm - tic-tac-toe game.";
	}
		
	/*public void run() {
		if (panel == null)
			return;
		
		emulateConsoleAndRedirectStream();

		new Main(ui, di);

		// REFRESH+RESTORE:
		this.refreshPanel();
		this.restoreSystemOutPrintStream();
	}*/
	
	public void run() {
		if (panel == null)
			return;
		
		internalPanel = new Pan();

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(internalPanel, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(internalPanel, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
		);
		
		panel.setLayout(gl_panel);		
		
		//emulateConsoleAndRedirectStream();
		
		/* *******
		JTextArea textArea = new JTextArea(32, 80);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setAlignmentX(0);
		
		// addition:
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
		);
		
		panel.setLayout(gl_panel);
		
		panel.revalidate();		 
		 */

		

		// REFRESH+RESTORE:
		this.refreshPanel();
		//this.restoreSystemOutPrintStream();
		
		new Main(ui, di);
		
	}	
}
