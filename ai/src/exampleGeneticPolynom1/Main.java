package exampleGeneticPolynom1;

import java.util.ArrayList;
import aiGen.GenPool;
import aiGen.Genome;

// TODO: Change mutation so it changes up to 3 genes.

public class Main {
	Character s;
	
	GenOp            rules = new GenOp(); // was:25
	GenPool<Integer> pool  = new GenPool<Integer>(rules);
	
	public void go() {
		final Integer population_size = 100; // change to 500 later on. -> best: 7.6
		final Double mp = 7.5; // was 10: -> 20 -> 10
		final Double cp = 27.0; // was 25:
		//final Integer generationCount = 3200;
		final Integer generationCount = 400; // was: 400
		
		System.out.println("You have started a very long example. Please make yourself a coffee.");
		
		// pool.createRandom(population_size * 20); // 200 * 10 genomes generated initially.
		try {
			pool.createRandomInterruptible(population_size * 20); // 200 * 10 genomes generated initially.
		} catch (InterruptedException e1) {
			return; // thread interrupted.
		} 
		
		pool.sortByFitness(false);
		System.out.println(pool);
		
		GenPool<Integer> nextPool  = new GenPool<Integer>(rules);
		nextPool.add(pool.genomes.subList(0, population_size)); // 200 of the most fit genomes. 
		
		int generation = 0;
		ArrayList<Genome<Integer>> bestFitness = new ArrayList<Genome<Integer>>();

		GenomeHandler gh = new GenomeHandler();
		
		// Creating next generation:
		while (generation < generationCount) {
			System.out.println("calculating generation no: " + generation);
			
			// DBG: System.out.println("pool.genomes.size="+pool.genomes.size());
			// nextPool = pool.runNextGeneration(mp, cp);
			try {
				nextPool = pool.runNextGenerationInterruptible(mp, cp);
			} catch (InterruptedException e) {
				return; // Thread interrupted.
			}
			
			// DBG: System.out.println("nextPool.genomes.size="+nextPool.genomes.size());
			// begining:
			
			Genome<Integer> theMostFit = nextPool.getMostFit(false); // the smaller the better.
			bestFitness.add(theMostFit);
			gh.print(theMostFit);
			pool = nextPool;
			
			System.out.println("  The most fit: " + theMostFit.fitness);
			
			nextPool = pool.runTournamentSelection();
			pool = nextPool;

/*			// ---- PRINT STUFF:				
			pool.sortByFitness(true);
			List<Genome<Lines>> l = pool.genomes.subList(0, 5);
			
			System.out.println("Generation: "+generation+" ; mostFit = "+ l.get(0).fitness + "; total_count: "+ pool.genomes.size() +" top 5:");
			for (int j=0; j < l.size(); j++)
				System.out.println("   " + j + "\tfitness=" + l.get(j).fitness+ "\t:" + l.get(j));
			// ----- END OF PRINT.				
*/

			generation++;
		}		
		
		for (int i=0; i<bestFitness.size(); i++)
			System.out.println("Best fit ("+i+"): " + bestFitness.get(i).fitness);
		
		Genome<Integer> g = bestFitness.get(bestFitness.size() - 1);
		

		gh.dbg = true;
		//gh.calculate_fitness(g, 0f, 0.1f, 63);
		gh.calculate_fitness(g, (float) (Math.PI * 2), 0.1f, 63);
		
		gh.print(g);
	}
		
	public Main() {
		go();		
	}

	public static void main(String[] args) {
		new Main();		
	}

}

