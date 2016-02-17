package aiBase;

import java.util.Arrays;

/**
 * The class contains completely: Basic and dull neuron without any additional fun. 
 * 
 * @author Witold Kaczurba 
 */
public class AiNeuron {
	public double weights[];
	public double threshold;
	final boolean normalizedOutput; // If true: output values are [0, 1]; otherwise [0, weightsVector*inputsVector ] 
	final int inputCount;
	boolean verbose = true; // show learning when true;
	
	// inputCount -> number of connected inputs;
	public AiNeuron(double[] weights, double threshold) {
		
		this.inputCount = weights.length;
		//this.weights = new double[inputCount]( 2.0, 3.0 );
		//this.weights = new double[]{ 2.0, 3.0 };
		this.weights = new double[inputCount];
		this.threshold = threshold;
		
		System.arraycopy(weights, 0, this.weights, 0, this.inputCount);
		normalizedOutput = true;
	}
	
	public double getOutput(double inputs[]) {
		double value = 0.0; // pre-threshold value;
		
		if (inputs.length != inputCount)
			throw new ArrayIndexOutOfBoundsException();
		
		for (int i=0; i < inputCount; i++)
			value += inputs[i] * weights[i];
		
		if (value > threshold) {
			// TRUE:
			if (normalizedOutput)
				return 1;
			else
				return value;
		} else {
			return 0.0;
		}
	}
	
	// Returns: false for no learning/no error; true for learning/error
	// 		    Learnign factor => used to magnify (set it to 0 by default);
	public boolean updateWeights(double inputs[], double learningFactor, double expectedOutput) {
		StringBuilder sb = new StringBuilder();
		double output = getOutput(inputs);		
		
		if (expectedOutput == output) {
			sb.append(String.format("In: %s; Wghts: %s; output:%f Expected out=%f; no learning", doubleArrayToString(inputs), doubleArrayToString(weights), output, expectedOutput));
			if (verbose)
				System.out.println(sb.toString());			
			return false;
		} else {  
			// decrease weights:
			sb.append(String.format("In: %s; Wghts: %s; output:%f; Expected out=%f; LEARNING: ", doubleArrayToString(inputs), doubleArrayToString(weights), output, expectedOutput ));
			//sb.append(String.format("updateWeights: output=%f ; expected output: %f ; old weights: %s ", output, expectedOutput, doubleArrayToString(weights)));
			if (output > expectedOutput) 
				for (int i=0; i < inputCount; i++)
					weights[i] -= inputs[i] * learningFactor;
			else // if (output < expectedOutput)
				for (int i=0; i < inputCount; i++)
					weights[i] += inputs[i] * learningFactor;
				
			sb.append("; new weights: " + doubleArrayToString(weights) );
			
			if (verbose)
				System.out.println(sb.toString());
			return true;
		} 
	}
	
	static public String doubleArrayToString(double [] doubleArr) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		
		sb.append("[");
		//for (i=0; i<inputCount-1; i++)
		for (i=0; i<doubleArr.length - 1; i++)
			sb.append(doubleArr[i] + ",");
		sb.append(doubleArr[i] + "]");
		
		return sb.toString();
	}
	
	public String toString() {
		int i = 0;
		StringBuilder sb = new StringBuilder();
		
		sb.append("weights: " + doubleArrayToString(this.weights));
		sb.append(String.format(" threshold: %.2f ; normalized output: %s", this.threshold, this.normalizedOutput));
		
		return sb.toString();
	}
}

