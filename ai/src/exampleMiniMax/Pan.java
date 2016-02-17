package exampleMiniMax;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;

public class Pan extends JPanel {
	
	class ImagePanel extends JPanel implements MouseListener {
		
		public ImagePanel() {
			super();
			addMouseListener(this);
		}
		
		private void drawCircle(Graphics g, int j, int i) { 
			// HERE: DRAWING CIRCLE AT A PARTICULAR PLACE:
			int width  = this.getWidth(); 
			int height = this.getHeight();
			int xoffset = width / 6;
			int yoffset = height / 6;
			
			g.drawOval(i * width/3 + (xoffset - xoffset/2), j * height/3 + (yoffset - yoffset/2), width/5, height/5);				
		}
		
		private void drawCross(Graphics g, int j, int i) {
			int width  = this.getWidth(); 
			int height = this.getHeight();
			int xoffset = width / 6;
			int yoffset = height / 6;				
			
			g.drawLine(i * width/3 + (xoffset - xoffset/2), j * height/3 + (yoffset - yoffset/2), i * width/3 + (xoffset - xoffset/2) + xoffset, j * height/3 + (yoffset - yoffset/2 + yoffset));
			g.drawLine(i * width/3 + (xoffset - xoffset/2) + xoffset, j * height/3 + (yoffset - yoffset/2), i * width/3 + (xoffset - xoffset/2), j * height/3 + (yoffset - yoffset/2 + yoffset));
			
		}
		
		private void clear(Graphics g) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(getForeground());
		}
		
		TicTacToe t = null;
		public void setBoard(TicTacToe t) {
			this.t = t;
			//this.paintComponent(this.getGraphics());
			this.repaint();
		}
		
		private void drawPanel(Graphics g) {
			clear(g);
			drawLines(g);
					
			System.out.println("drawing..." + t);
			
			if (t != null)
				for (int j=0; j<3; j++)
					for (int i=0; i<3; i++)
					{
						char c = t.get(j,i);
						switch (c) {
						case 'x': drawCross(g, j, i); break;
						case 'o': drawCircle(g, j, i); break;
						case ' ': break;
						default: throw new NullPointerException("invalid value returned by TicTacToe");
						}
					}
			else {
				System.out.println("  t was null...");
			}
		}
		
		private void drawLines(Graphics g) {
			int width  = this.getWidth(); 
			int height = this.getHeight();
			g.drawLine(0, height * 1 / 3, width, height * 1 / 3);
			g.drawLine(0, height * 2 / 3, width, height * 2 / 3);
			
			g.drawLine(width * 1 / 3, 0, width * 1 / 3, height);
			g.drawLine(width * 2 / 3, 0, width * 2 / 3, height);				
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// two horiz, two vert:
			
			this.drawPanel(g);

			// mid of grids:
			/*this.drawLines(g);
			this.drawCircle(g, 2, 2);
			this.drawCircle(g, 1, 1);
			this.drawCross(g, 0, 0);
			this.drawCross(g, 2, 0);*/
		}
		
		TicTacToe_op lastClick = null;

		@Override
		public void mouseClicked(MouseEvent e) {
			
			int width = getWidth();
			int height = getHeight();
			int x = e.getX();
			int y = e.getY();
			
			int i = 0;
			int j = 0;
			
			if (x < width / 3) {
				i = 0;
			} else if (x < 2 * width / 3) {
				i = 1;
			} else {
				i = 2;
			}
			
			if (y < height / 3) {
				j = 0;
			} else if (y < 2 * height / 3) {
				j = 1;
			} else {
				j = 2;
			}
			
			System.out.println( "Click@:" + e.getX() + "x" + e.getY() );
			System.out.println( "   i:" + i + " j:" + j );
			
			lastClick = new TicTacToe_op(j,i);
			System.out.println(lastClick);
		};
		
		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
		// TODO: This should be a separate thread or something.
		public synchronized TicTacToe_op getClick() {
			return lastClick;
		}		
		
		public synchronized void resetClick() {
			lastClick = null;
		}
	}

	ImagePanel imagePanel; 
	JTextArea scoreTextArea;
	JTextArea debugTextArea;
	
	
	/**
	 * Create the panel.
	 */
	
	public Pan() {
		// OLD PANEL:
		//JPanel imagePanel = new JPanel();
		
		/*imagePanel = new ImagePanel();
		imagePanel.setBackground(new Color(255, 255, 255));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(43)
					.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(356, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(98)
					.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(199, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

		//run();*/
		
		// NEW PANEL
		
		JLabel lblUserInterface = new JLabel("User Interface:");
		
		imagePanel = new ImagePanel();
		imagePanel.setBackground(new Color(255, 255, 255));
		
		JLabel lblScore = new JLabel("Score:");
		
		scoreTextArea = new JTextArea();
		scoreTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		scoreTextArea.setRows(4);
		scoreTextArea.setColumns(40);
		
		JLabel lblDebugInterface = new JLabel("Debug Interface:");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(imagePanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
								.addComponent(lblUserInterface, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblScore, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
								.addComponent(scoreTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblDebugInterface, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUserInterface)
						.addComponent(lblScore))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
						.addComponent(scoreTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDebugInterface)
					.addGap(9)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		debugTextArea = new JTextArea();
		scrollPane.setViewportView(debugTextArea);
		setLayout(groupLayout);
		
	}
	
	
	public void run() {

		
		(new Thread (new Runnable() {
			public void run() {
				TicTacToe board = new TicTacToe();
				imagePanel.setBoard(board);
				
				TicTacToe_op op; 				
				while (true) {

					op = null;
					
					while (op ==  null) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						op = imagePanel.getClick(); // FIXME HOW TO DO THIS AS A BLOCKING FLOW IN A NON-BLOCKING CONTEXT(?)
						imagePanel.resetClick();
					}
					
					board = (TicTacToe) board.applyOperator(op);
					imagePanel.setBoard(board);

				}
			}
		})).start();

	}
	
	
	static private void createAndShowGUI() {
		JFrame frame = new JFrame("");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel newContentPane = new JPanel();
		//Pan newContentPane = new Pan();
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);

		frame.pack();
		frame.setVisible(true);		
	}

	static public void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
