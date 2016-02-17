package examples;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public interface FrameControl {
	public List<Example> getListOfExamples();
	public void run(Example example, JPanel panel);
	public void info(Example example);
	public void close(JFrame frame);
	void clean(JPanel panel); // cleans the outputPanel / deletes old results.

}
