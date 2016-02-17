package exampleGeneticPolynom1;

import java.util.ArrayList;
import java.util.List;

import aiGen.Genome;

/**
 * THis class is a helper that calculates fitness of a Genome
 * and prints it out as a Polynomial    
 */

public class GenomeHandler {
	public boolean dbg = false;
	DualConverter dc = new DualConverter();
	
	// This function seems to be working ok.
	// It calculates: ax^7 + bx^6 + cx^5 + dx^4 + e^x3 + f^x2 + g^x + h.
	// Where a,b,c,d...h are values represented in genes.
	public float calculateValue(Genome<Integer> g, float x) {
		List<Float> floatValues = getFloatValues(g);
		int flCount = floatValues.size();
		float result = 0.0f;
		
		for (int i=0; i<flCount; i++) {
			int power = flCount - i - 1;
			
			result += floatValues.get(i) * Math.pow(x, power);
		}
		return result;
	}
	
	public float referenceFunction(float x) {
		return (float) Math.sin(x);	
	}
	
	public float calculate_fitness(Genome<Integer> g, float begin, float step, int stepCount) {
		final float error_cap = (float) Math.pow(10, 20); // 1E20 => cap
		float x = begin;
		Float err = 0f;
		int i = 0;
		
		// TODO: Checks
		
		if (dbg) System.out.println("calculate_fitness:");
			
		while (i < stepCount) {
			//err += new Float (Math.pow(calculateValue(g, x) - referenceFunction(x), 2)); 
			err += new Float (Math.abs(calculateValue(g, x) - referenceFunction(x)));
			
			if (dbg) System.out.format("\tx = %.3e \t calculateValue=%.3e \t referenceFunction=%.3e\n", x, calculateValue(g, x), referenceFunction(x) );
			
			x += step;
			i++;
		}

		if ( err.isNaN() || err.isInfinite() || (float) err > error_cap)
			err = error_cap;
		
		if (dbg) System.out.println("\terror="+err);
		return err;
	}

	// This function converts genome to list of float values [a,b,c,d,e...]
	// For further representaiton: e.g. ax^7 + bx^6 + cx^5 ... g.
	//
	public List<Float> getFloatValues(Genome<Integer> g) {
		int flCount = g.data.size() / 16;
		
		if (g.data.size() % 16 != 0) {
			System.out.println("Invalid data size (number of duals== " + g.data.size() + " not divadable by 16) ") ;
			System.exit(-1);
		}

		List <Float> values = new ArrayList<Float>();
		for (int i=0; i<flCount; i++) {
			List <Integer> dualList = g.data.subList(i*16, i*16+16);
			float value = dc.dualListToFloat(dualList);
			values.add(value);			
		}		
		
		return values;
	}
	
	public void print(Genome<Integer> g) {
		List<Float> floatValues = getFloatValues(g);
		int flCount = floatValues.size();
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<flCount; i++) {
			int power = flCount - i - 1;
			
			if (i != 0 && sb.length() > 0)
				sb.append(" + ");
			
			if (power == 0) // the last one:
				sb.append(String.format("%.3e", floatValues.get(i)) );
			else if (power == 1)
				sb.append(String.format("%.3e*x", floatValues.get(i)) );
			else
				sb.append(String.format("%.3e*x^%d", floatValues.get(i), power) );				
		}
		System.out.println("  " + sb.toString());
	}
}
