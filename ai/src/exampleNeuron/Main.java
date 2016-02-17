package exampleNeuron;

import java.util.Arrays;

import aiBase.AiNeuron;

public class Main {
	
	public Main() {
		AiNeuron n = new AiNeuron(new double[] { 2.0, 3.0 }, 1);
		System.out.println("Neuron at the begining: " + n + "\n");		
		
		boolean learn = false;
		int cntLimit = 0;
		// HERE: LEARNING TO FINISH:
		do {
			learn = false;
			learn |= n.updateWeights(new double[] {0,  0}, 1, 0.0);
			learn |= n.updateWeights(new double[] {0,  1}, 1, 0.0);
			learn |= n.updateWeights(new double[] {1,  0}, 1, 1.0);
			learn |= n.updateWeights(new double[] {1,  1}, 1, 1.0);
			System.out.println("-------");
		} while (learn && cntLimit++ < 20);
		
		if (learn) {
			System.out.println("After 20 iterations neuron has still not learnt...");
		} else {
			System.out.println("Neuron at the end: " + n + "\n");
			
			System.out.println(String.format("Inputs [%.2f,%.2f] -> %.2f", 0., 0., n.getOutput(new double[] {0, 0})) );
			System.out.println(String.format("Inputs [%.2f,%.2f] -> %.2f", 0., 1., n.getOutput(new double[] {0, 1})));
			System.out.println(String.format("Inputs [%.2f,%.2f] -> %.2f", 1., 0., n.getOutput(new double[] {1, 0})));
			System.out.println(String.format("Inputs [%.2f,%.2f] -> %.2f", 1., 1., n.getOutput(new double[] {1, 1})));

			//doubleArrayToString
		}		
	}
	
	public static void main(String args[]) {
		new Main();
	}
}
