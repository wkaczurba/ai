/**
 * 
 */
package aiGen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;

/**
 * GenPool is a pool that contains list of Genome<T>.
 * The class handles:
 * <ul>
 *   <li>gentic operations on the pool of Genomes (e.g. mutation, crossover) - runNextGeneration
 *   <li>tournament selection - runTournamentSelection
 * </ul> 
 *
 * @author  Witold Kaczurba
 */

public class GenPool<T> {
	
	public ArrayList<Genome<T>> genomes;
	GenomeOperators<T> genOp;       
	private Random rand = new Random();
	
	public GenPool(GenomeOperators<T> genOp) {
		super();
		genomes = new ArrayList<Genome<T>> ();
		this.genOp = genOp;
	}
	
	// TODO: This funciton is only for testing.
	public void fitnessCheckTest() throws GenomeFitnessException {
		int n = 0;
		for (Genome<T> g : genomes) {
			if (g.fitness != genOp.getFitness(g))
				throw new GenomeFitnessException();
			n++;
		}
		System.out.println("fitnessCheckTest: checked "+n+" genome(s) ");
	}
	
	// This is like from collections:
	/* ****************** */
	public void add(Genome<T> gen) {
		genomes.add(gen);
	}
	
	public void add(List<Genome<T>> l) {
		for (Genome<T> g : l)
			add(g);
	}
	
	/**
	 *  creates and adds random genomes
	 * @param count - number of elements
	 */
	public void createRandom(int count) {
		for (int i=0; i<count; i++)
			genomes.add(new Genome<T>(genOp));
	}
	
	/**
	 *  creates and adds random genomes; same as createRandom but this one is interruptible / thread-safe
	 * @param count - number of elements
	 * @throws InterruptedException 
	 */
	public void createRandomInterruptible(int count) throws InterruptedException {
		for (int i=0; i<count; i++) {
			if (Thread.interrupted())
				throw new InterruptedException();
			genomes.add(new Genome<T>(genOp));
		}
	}	

	@SuppressWarnings("unchecked")
	public boolean remove(Genome<T> g) {
		
		// TODO: Need to ensure that only one genome is removed at the time.
		boolean result = genomes.remove(g);
		
		// This has to be fixed.!
		if (result == false) {
			System.out.println("Error while removing stuff; could not remove: " + g);
			
			// It will be here :
			System.out.println(this);
			
			for (Genome<T> b : genomes) {
				if (b.fitness == g.fitness) {
					System.out.println("matching from genomes [that should be removed]: " + b);
					System.out.println("  b.equals(g)? => " + ( (Genome<T>)b ).equals( (Genome<T>) (g) ));
				}
				
			}
			
			System.exit(-1);
		} else {
			//System.out.println("Removing ok" + g);
		}
		
		return result;
	}
	
	public Genome<T> getRandom() {
		if (genomes.size() > 0)
			return genomes.get(rand.nextInt(genomes.size()));
		return null; 
	}
	
	public void sortByFitness(boolean maxFirst) {
		
		Collections.sort(genomes, new Comparator<Genome<T>>() {
			public int compare(Genome<T> g1, Genome<T> g2) {
				int retval = 0;
				
				if (g1.fitness > g2.fitness)
					retval = 1;
				else if (g1.fitness < g2.fitness)
					retval = -1;
				else // equal
					retval = 0;
				
				if (maxFirst)
					retval = -retval; // Inverts result. 
				
				return retval; // (g1.fitness == g2.fitness)				
			}
		});
	}
	
	public Genome<T> getMostFit(boolean findMax) { // TODO: Make enum (findMax/findMin)
		Genome<T> mostFit;
		
		mostFit = genomes.get(0);
		for (Genome<T> g : genomes) {
			if (findMax) {
				if (mostFit.fitness < g.fitness)
					mostFit = g;
			} else {
				if (mostFit.fitness > g.fitness)
					mostFit = g;
			}
		}
		return mostFit;
	}
	
	/* ---------------- GA-specific methods: ------------------ */ 
	public ArrayList<Genome<T>> mutateAndRemove() {		
		Genome<T> g = getRandom();
		Genome<T> newg;

		newg = genOp.mutate(g);
		remove(g);
		
		return new ArrayList<Genome<T>>(Arrays.asList(newg));
	}
	
	public int countGenome(Genome<T> g) {
		int count = 0;
		for (Genome<T> gt : genomes) {
			if (gt.equals(g)) 
				count++;
		}
		return count;
	}
	
	// FIXME: This most likely fails to remove:
	public ArrayList<Genome<T>> crossAndRemove() {		
		Genome<T> g1 = genOp.tournamentSelection(this);
		Genome<T> g2 = genOp.tournamentSelection(this);
		
		if (genomes.size() < 2)
			return null;		

		while (g1.equals(g2) && ( countGenome(g1)==1) )
			g2 = this.getRandom();
		
		List<Genome<T>> newGenomes = genOp.crossOver(g1, g2);
		
		/*if (g1.equals(g2)) {
			System.out.println("   g1 = " + g1);
			System.out.println("   g2 = " + g2);
			// System.out.println(this);
		}*/
		remove(g1); // FIXME: This fails as tournamentSelection creates new object. (TRY adding Genome.java -> public boolean equals(Object obj))
		remove(g2); // FIXME: This fails as tournamentSelection creates new object.
		
		// System.out.println("Count after crossover: " + this.genomes.size());
		
		return new ArrayList<Genome<T>>(newGenomes);
	}
	
	public ArrayList<Genome<T>> selectAndRemove() {
		Genome<T> g = getRandom();
		Genome<T> newGenome;
		
		newGenome = new Genome<T>(g.genOp, g.data);
		remove(g);
		
		return new ArrayList<Genome<T>>(Arrays.asList(newGenome));
	}
	
	/* ---------------- GA-specific methods: ------------------ */ 	
	
	public Genome<T> remove() {
		return genomes.remove(genomes.size()-1);
	}
	
	public String toString() {
		String s = "GenPool -> List of Genomes ("+this.genomes.size()+"):\n";
		for (Genome<T> g : genomes)
			s += g + "\n";
		return s;
	}
	
	/**
	 * runNextGeneration - runs next generation; creates new GenPool<T>.
	 * it successively removes genomoes from current (this) GenPool to the new one.
	 * it returns new GenPool leaving the current one (this GenPool) empty
	 * 
	 * @param mp - probability of mutation
	 * @param cp - probability of cross-over
	 * @return
	 */
	public GenPool<T> runNextGeneration(Double mp, Double cp)
	{
		GenPool<T> nextPool = new GenPool<T>(genOp);
		
		// begining:
		while (genomes.size() > 0) {
			//int decision = rand.nextInt(100);
			Double decision = (double) rand.nextInt(100);
			String s;
						
			if (decision < mp) { // mutate;
				nextPool.add( mutateAndRemove() );

			} else if (decision < mp + cp ) { // crossover;
				//System.out.println("GenPool.runNextGeneration -> crossover start..");
				if (genomes.size() < 2) { // need at least 2 genomes in the pool for matching.
					//System.out.println("   ... skipped");
					continue;
				}
				nextPool.add( crossAndRemove() );
				
			} else { // selection; 
				nextPool.add( selectAndRemove() );
			}
		}	
		return nextPool;
	}
	
	/**
	 * Same as runNextGeneration but this one is interruptible for thread-safety.
	 * runNextGeneration() function is a heavy crunching operation.
	 * 
	 * @param mp - probability of mutation
	 * @param cp - probability of cross-over
	 * @return
	 * @throws InterruptedException 
	 */
	public GenPool<T> runNextGenerationInterruptible(Double mp, Double cp) throws InterruptedException
	{
		GenPool<T> nextPool = new GenPool<T>(genOp);
		
		// begining:
		while (genomes.size() > 0) {
			if (Thread.interrupted())
				throw new InterruptedException();
			
			//int decision = rand.nextInt(100);
			Double decision = (double) rand.nextInt(100);
			String s;
						
			if (decision < mp) { // mutate;
				nextPool.add( mutateAndRemove() );

			} else if (decision < mp + cp ) { // crossover;
				//System.out.println("GenPool.runNextGeneration -> crossover start..");
				if (genomes.size() < 2) { // need at least 2 genomes in the pool for matching.
					//System.out.println("   ... skipped");
					continue;
				}
				nextPool.add( crossAndRemove() );
				
			} else { // selection; 
				nextPool.add( selectAndRemove() );
			}
		}	
		return nextPool;
	}	
	
	// TODO: Consider adding this to an interface.	
	public GenPool<T> runTournamentSelection()
	{
		int count = this.genomes.size();
		//int count = 100;
		GenPool<T> nextPool = new GenPool<T>(genOp);

		//nextPool = new GenPool<Lines>(rules);
		for (int i = 0; i < count; i++) 
			nextPool.add(genOp.tournamentSelection(this));
		return nextPool;
	}
}
