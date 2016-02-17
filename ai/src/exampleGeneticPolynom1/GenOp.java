package exampleGeneticPolynom1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import aiGen.GenPool;
import aiGen.Genome;
import aiGen.GenomeOperators;

public class GenOp implements GenomeOperators<Integer> {
	final int length = 8; // TODO: Change the value to "4" expressed in duals (ie. 4 means 4*16 bits => 64 bits;
	final int MaxIntValue = 100;
	final Random rand = new Random();
	
	public Genome<Integer> old_mutate(Genome<Integer> g) {
		// TODO Auto-generated method stub
		int i = rand.nextInt(16);
		
		List<Integer> newcode = new ArrayList<Integer>(g.data);
		
		Integer old_value = newcode.get(i);
		Integer new_value = rand.nextInt(4);
		
		while (old_value.equals(new_value)) 
			new_value = rand.nextInt(4);
		newcode.set(i, new_value);

		//System.out.println("new dualList: " + dualList);
		
		//Float new_result = dc.dualListToFloat(dualList);
		//System.out.println("new result:   " + new_result);

		return new Genome<Integer>(this, newcode);
	}

	@Override
	public Genome<Integer> mutate(Genome<Integer> g) {
		// TODO Auto-generated method stub
		
		//final int nGenesToMutate = 40; // 3, 8 gave good results.: was 16
		final int nGenesToMutate = rand.nextInt(g.data.size());
		//final int nGenesToMutate = rand.nextInt(48);
		
		List<Integer> newcode = new ArrayList<Integer>(g.data);
		
		for (int n=0; n < nGenesToMutate; n++) { // n-genes to be mutated: (was 3 before)
			
			int i = rand.nextInt( newcode.size() );
			Integer old_value = newcode.get(i);
			Integer new_value = rand.nextInt(4);
			
			while (old_value.equals(new_value)) 
				new_value = rand.nextInt(4);
			newcode.set(i, new_value);
		}

		//System.out.println("new dualList: " + dualList);
		
		//Float new_result = dc.dualListToFloat(dualList);
		//System.out.println("new result:   " + new_result);

		return new Genome<Integer>(this, newcode);
	}

	
	//@Override
	public List<Genome<Integer>> OLD_crossOver(Genome<Integer> g1, Genome<Integer> g2) {
		// TODO Auto-generated method stub
		final int dualBitLength = 16;
		final int cutoff = 0; // CUTOFF POINT;
		
		List<Integer> genA = g1.data;
		List<Integer> genB = g2.data;
		List<Integer> newA = new ArrayList<Integer>();
		List<Integer> newB = new ArrayList<Integer>();
		
		Integer toIndex     = rand.nextInt(dualBitLength * this.length - cutoff) + cutoff + 1; // exclusive ie.  <fromIndex; toIndex)
		Integer fromIndex   = rand.nextInt(toIndex - cutoff) + cutoff; // inclusive ie. <fromIndex; toIndex)
		
		List<Integer> cutA = genA.subList(fromIndex, toIndex);
		List<Integer> cutB = genB.subList(fromIndex, toIndex);
		
		//System.out.println("crossover :<"+fromIndex+";"+toIndex+")");
		
		// Creating new GENES:
		newA.addAll(genA.subList(0, fromIndex)); // old piece;
		newA.addAll(cutB);
		newA.addAll(genA.subList(toIndex, dualBitLength * this.length));
		
		newB.addAll(genB.subList(0, fromIndex));
		newB.addAll(cutA);
		newB.addAll(genB.subList(toIndex, dualBitLength * this.length));
		
		return new ArrayList<Genome<Integer>>(
				Arrays.asList(
						new Genome<Integer>(this, newA),
						new Genome<Integer>(this, newB)
				));
	}
	
	@Override 
	public List<Genome<Integer>> crossOver(Genome<Integer> g1, Genome<Integer> g2) {
		//final int dualBitLength = 16;
		//final int cutoff = 0; // CUTOFF POINT;
		
		List<Integer> genA = g1.data;
		List<Integer> genB = g2.data;
		List<Integer> newA = new ArrayList<Integer>();
		List<Integer> newB = new ArrayList<Integer>();
		
		if (genA.size() != genB.size()) {
			System.out.println("genA.size != genB.size");
			System.exit(-1);
		}

		for (int i=0; i < genA.size(); i++) {
			double decision = rand.nextDouble();
			if (decision < 0.5) {
				newA.add(new Integer(genA.get(i)));
			    newB.add(new Integer(genB.get(i)));
			}
			else {
				newA.add(new Integer(genB.get(i)));
				newB.add(new Integer(genA.get(i)));
			}
		}
		
		/*if (dbg) {
			System.out.println("   A: " + genA);
			System.out.println("   B: " + genB);
			System.out.println("newA: " + newA);
			System.out.println("newB: " + newB);
			// System.exit(-1);
		}*/
		
		return new ArrayList<Genome<Integer>>(
				Arrays.asList(
						new Genome<Integer>(this, newA),
						new Genome<Integer>(this, newB)
				));
	}	

	@Override
	public Genome<Integer> tournamentSelection(GenPool<Integer> pool) {
		final int participants = 4;
		Genome<Integer> retval = null;
		Genome<Integer> tmp    = null;
		
		// Gets most fit out of four:
		for (int i = 0; i < participants; i++) {
			tmp = pool.getRandom();
			
			// Find minimum:
			if (retval == null || retval.fitness > tmp.fitness) 
				retval = tmp;
		}

		return new Genome<Integer>(this, retval.data); // create new one.
	}
	
	@Override
	public List<Integer> randomGenomeData(Genome<Integer> g) {
		List<Integer> l = new ArrayList<Integer>();

		for (int i=0; i<length * 16; i++) // dual size is 16;
			l.add( rand.nextInt(4) );
		return l;
	}

	@Override
	public double getFitness(Genome<Integer> g) {
		GenomeHandler gh = new GenomeHandler();
		
		// Calculates error between polynomial and reference function. 
		// The function returns values <0; Inf>. Where 0=No error
		
		//double errorValue = (double) gh.calculate_fitness(g, 0, 0.2f, 63);
		double errorValue = (double) gh.calculate_fitness(g, (float) (Math.PI * 2), 0.1f, 63);
		
		return errorValue;
	}
	
}

