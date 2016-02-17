package examples;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import exampleAlphaBetaPrunning.ExampleAlphaBetaWrapper;
import exampleBestFS.ExampleBestFSWrapper;
import exampleBreadthFS.ExampleBreadthFSWrapper;
import exampleDepthFS.ExampleDepthFSWrapper;
import exampleGeneticMaze1.ExampleGeneticMaze1Wrapper;
import exampleGeneticPolynom1.ExampleGeneticPolynom1Wrapper;
import exampleGeneticPolynom2.ExampleGeneticPolynom2Wrapper;
import exampleMiniMax.ExampleMiniMaxWrapper;
import exampleNeuron.ExampleNeuronWrapper;

public class MainControl implements FrameControl {
	
	
	//TODO: TODO: As a factory/builder
	private List<Example> examples = new ArrayList<Example>(Arrays.asList(
				new ExampleAlphaBetaWrapper(),
				new ExampleBestFSWrapper(),    // OK.
				new ExampleBreadthFSWrapper(), // (NICE TO): 
				new ExampleDepthFSWrapper(), // TODO: requires rework. (long output) -> LIMIT AFTER TIME
				new ExampleGeneticMaze1Wrapper(), 
				new ExampleGeneticPolynom1Wrapper(), // TODO: Needs refreshing (better refreshing)
				new ExampleGeneticPolynom2Wrapper(), // 
				new ExampleMiniMaxWrapper(), // TODO: Needs a separate panel / or input iface or something.
				new ExampleNeuronWrapper()
			));

	@Override
	public List<Example> getListOfExamples() {
		return examples;
	}

	@Override
	public void info(Example example) {
		// TODO Auto-generated method stub
		
		// FIXME: This try...catch should be a part of mainFrame
		try {
			//System.out.println("Displainyg: " + example.getName() + " " + example.getInfo());
			new InfoFrame(example.getName(), example.getInfo());
			
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Please select example");
		}
		
	}
	
	@Override
	public void clean(JPanel panel) {
		panel.removeAll();
		
		panel.setLayout( new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel.repaint();
		panel.revalidate();
		panel.validate();
		
	}

	Thread lastRun = null;
	Thread panelRefreshing = null;

	private void startPanelRefreshingThread(JPanel panel) {
		if (panelRefreshing == null) {
			
			Runnable refresher = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
						try {
							while(true) {
								panel.repaint();
								panel.revalidate();
								panel.validate();							
								Thread.sleep(500);
							}
						} catch (InterruptedException e) {
							return;
							// Finishing...
							//e.printStackTrace();
						}

				}
			};
			
			panelRefreshing = new Thread(refresher);
			panelRefreshing.start();
		}
	}
	
	@Override
	public void run(Example example, JPanel panel) {
		// TODO Auto-generated method stub
		
		//startPanelRefreshingThread(panel);
		
		// KILL ANY EXISTING 
		if (lastRun != null && lastRun.isAlive()) {
			lastRun.interrupt(); // finish.

			while (lastRun.isAlive()); // wait until it is finished.
		}
		clean(panel);		
		
		// TODO: Check this one: http://java.sun.com/docs/books/tutorial/uiswing/concurrency/index.html
		// TODO: Read this one: http://stackoverflow.com/questions/629315/dynamically-refresh-jtextarea-as-processing-occurs
		SwingUtilities.invokeLater( new Runnable() {

			@Override
			public void run() {
				
				// FIXME: This try...catch should be a part of mainFrame
				try {					
					// RUN NEW ONE:
					example.setPanel(panel);
					lastRun = new Thread(example);
					lastRun.start();
					
				} catch (NullPointerException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(panel, "Please select example");
				}
			}
		});
	}

	@Override
	public void close(JFrame frame) {
			frame.dispose();
	}

}
