package exampleGeneticPolynom2;

import java.util.List;

import aiGen.Genome;

/**
 * This function overrides original GenomeHandler so:
 * Representation is:
 * (x-a)(x-b)(x-c)(x-d)(x-e)(x-f)(x-g)
 *  
 * rather than (ax^7 + bx^6 + cx^5 + dx^4 + e^x3 + f^x2 + g^x + h.).
 * 
 * @author Witold Kaczurba
 *  
 */
public class GenomeHandler2 extends GenomeHandler {


	@Override
	public float calculateValue(Genome<Integer> g, float x) {
		List<Float> floatValues = getFloatValues(g);
		int flCount = floatValues.size();
		float result; // 
		
		result = 1.0f; // has to be 1.0 as it is multiplication: 
		for (int i=0; i<flCount; i++) {
			result *= (x - floatValues.get(i));
		}
		return result;
	}	
	
	@Override
	public void print(Genome<Integer> g) {
		List<Float> floatValues = getFloatValues(g);
		int flCount = floatValues.size();
		
		StringBuilder sb = new StringBuilder();

		for (int i=0; i<flCount; i++) {
			sb.append(String.format("(x - %.3e)", floatValues.get(i))  );
		}
		System.out.println("  " + sb.toString());
	}	
}


