package examples;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class MainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private FrameControl frameControl;
	private JButton btnRun;
	private JButton btnInfo;
	private JButton btnClose;
	
	private JList list;
	private JPanel controlPanel;
	private JPanel outputPanel;
	private JLabel lblDemos;
	
	private AbstractMap<String, Example> examples = new TreeMap<String, Example>();
	private JTextArea textArea;
	private JScrollPane scrollPane;
	
	/**
	 * Create the frame.
	 */
	public MainFrame(FrameControl control)  {
		this.frameControl = control;
		
		/* BUTTONS */
		btnRun = new JButton("Run");
		btnInfo = new JButton("Info");
		btnClose = new JButton("Close");
		
		btnRun.setActionCommand("run");
		btnInfo.setActionCommand("info");
		btnClose.setActionCommand("close");
		
		btnRun.addActionListener(this);
		btnInfo.addActionListener(this);
		btnClose.addActionListener(this);
		
		
		// END OF BUTTON'S STUFF 
		for (Example e : frameControl.getListOfExamples()) {
			examples.put(e.getName(), e);
		}
		
		//String items[] = ; 
		list = new JList(examples.keySet().toArray()); 

		
		controlPanel = new JPanel();
		outputPanel = new JPanel();
		lblDemos = new JLabel("Demos");
		


		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 645);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		controlPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		outputPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(outputPanel, GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
				.addComponent(outputPanel, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
		);
		
		//outputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		//scrollPane = new JScrollPane();
		//scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		//outputPanel.add(scrollPane);
		
		
		/*JTextArea textArea = new JTextArea(5,40);
		JScrollPane scrollPane = new JScrollPane(textArea);
		outputPanel.add(scrollPane);*/		
		
		

		
		GroupLayout gl_panel = new GroupLayout(controlPanel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(list, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
						.addComponent(lblDemos)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnInfo, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
						.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDemos)
					.addGap(8)
					.addComponent(list, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnInfo)
						.addComponent(btnRun))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClose)
					.addContainerGap())
		);
		controlPanel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("run")) {
			
			Example selectedExample = examples.get( list.getSelectedValue() );
			frameControl.run(selectedExample, outputPanel);
		}
		if (e.getActionCommand().equals("info")) {
			Example selectedExample = examples.get( list.getSelectedValue() );
			frameControl.info(selectedExample);
		}
		if (e.getActionCommand().equals("close")) {
			frameControl.close(this);
		}
			
		
	}
}
