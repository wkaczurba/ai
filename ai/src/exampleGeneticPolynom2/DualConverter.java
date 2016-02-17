package exampleGeneticPolynom2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DualConverter {
	//public DualConverter() { }
	
	private String floatToPaddedBitString(Float value) {
		// FLOAT->BITS
		Integer intbits = Float.floatToIntBits(value);
		String s = Integer.toBinaryString(intbits); 
		//DBG: System.out.println( "binaryString:          " + s );
		
		// PADDING:
		String padding = "00000000000000000000000000000000";
		String padded_s = padding.substring(0, 32-s.length()) + s;
		//DBG: System.out.println( "[padded] binaryString: " + padded_s );

		return padded_s;
	}
	
	private String paddedBitStringToDuals(String paddedBitString) {
		// TURNING TWO-BITS INTO DUALS:
		StringBuilder dualsSB = new StringBuilder();
		for (int i=0; i<32; i+=2)
			dualsSB.append(Integer.parseInt(paddedBitString.substring(i,i+2), 2));
		String dualsString = dualsSB.toString();
		
		return dualsString;
	}

	private List<Integer> paddedDualsToDualList(String paddedDualsString) {
		// INPUT: PADDED_DUALS
		List<Integer> dualList = new ArrayList<Integer>();
		for (int i=0; i<16; i++) {
			String dual_character = paddedDualsString.substring(i, i+1);
			Integer dual_value = Integer.parseInt(dual_character);
			dualList.add(dual_value);
		}
		// DBG: System.out.println("dualList: "+dualList);
		
		return dualList;
		// OUTPUT: dualLIST
	}
	
	private String dualsToPaddedDualsString(List<Integer> dualList) {
		// INPUT: dualList
		StringBuilder sb = new StringBuilder(); 
		for (Integer q : dualList) {
			sb.append(q.toString());
		}
		
		String padded_duals = sb.toString();
		return padded_duals;
		// OUTPUT: padded_duals2
	}	
	
	private String dualsToBitString(String dualsString) {
		
		// TURNING DUALS INTO BITS:
		StringBuilder bitsSB = new StringBuilder();
		for (int i=0; i<16; i++) {
			// FIXME: This does not output 00 but only 0!!!
			//bitsSB.append( Integer.toString( (Integer.parseInt(duals.substring(i, i+1))), 2) );
			if (dualsString.substring(i, i+1).equals("0"))
				bitsSB.append("00");
			else if (dualsString.substring(i, i+1).equals("1"))
				bitsSB.append("01");
			else if (dualsString.substring(i, i+1).equals("2"))
				bitsSB.append("10");
			else bitsSB.append("11");
		}

		String bitString = bitsSB.toString();
		return bitString;
	}
	
	private Float bitStringToFloat(String bitString) {

		// BITS->FLOAT
		Integer rcvd_intbits = Integer.parseUnsignedInt(bitString, 2);
		Float recoveredFloat =  Float.intBitsToFloat(rcvd_intbits);
		
		//System.out.println( "Recovered: " + recoveredFloat );
		
		return recoveredFloat;
	}
	
	// High-Level:
	public List<Integer> floatToDualList(Float value) {
		String padded_s = floatToPaddedBitString(value);
		String padded_duals =  paddedBitStringToDuals(padded_s);
		
		// Encoding + Decoding (padded_duals -> quadList -> padded_duals)  
		List<Integer> dualList = paddedDualsToDualList(padded_duals);
		return dualList;
	}
	
	// High-Level:
	public Float dualListToFloat(List<Integer> dualList) {
		String padded_duals2 = dualsToPaddedDualsString(dualList);
		
		// Reversing:
		String bitString = dualsToBitString(padded_duals2);
		Float float_out = bitStringToFloat(bitString);
		
		return float_out;
	}
		
	public boolean selfTest() {
		Random rand = new Random();
		int n;
		
		for (n=0; n<2000000; n++) {
			//Float float_in = -121234133.4567f;
			Float f = rand.nextFloat();

			// Converison:
			List<Integer> dualList = floatToDualList(f);
			Float result = dualListToFloat(dualList);
			
			if (!f.equals(result)) {
				System.out.println("Error -> Mismatch: ");
				System.out.println("float_in:    " + f);
				System.out.println("dualList:    " + dualList);
				System.out.println("float_out:   " + result);
				return false;
			}
		}
		
		System.out.println("Passed: " + n + " times");
		return true;
	}
}