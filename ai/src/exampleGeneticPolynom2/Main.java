package exampleGeneticPolynom2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import aiGen.GenPool;
import aiGen.Genome;
import aiGen.GenomeOperators;

public class Main {
	Character s;
	
	GenOp            rules = new GenOp(); // was:25
	GenPool<Integer> pool  = new GenPool<Integer>(rules);
	GenomeHandler2     gh2 = new GenomeHandler2(); // for calculation of fitness + printing stuff out.
	
	public void go() {
		final Integer population_size = 100; // FIXME: It was: 4000 change to 500 later on. -> best: 7.6
		final Double mp = 7.5; // was 10: -> 20 -> 10
		final Double cp = 27.0; // was 25:
		
		final Integer generationCount = 400; // was: 400
		
		System.out.println("You have started a very long example. Please make yourself a coffee.");
		
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

		
		// Creating next generation:
		while (generation < generationCount) {
			System.out.println("calculating generation no: " + generation);
			
			//nextPool = pool.runNextGeneration(mp, cp);
			try {
				nextPool = pool.runNextGenerationInterruptible(mp, cp);
			} catch (InterruptedException e) {
				return; // Thread interrupted.
			}			
			
			Genome<Integer> theMostFit = nextPool.getMostFit(false); // the smaller the better.
			bestFitness.add(theMostFit);
			gh2.print(theMostFit);
			pool = nextPool;
			
			System.out.println("  The most fit: " + theMostFit.fitness);
			
			nextPool = pool.runTournamentSelection();
			pool = nextPool;

			generation++;
		}		
		
		for (int i=0; i<bestFitness.size(); i++)
			System.out.println("Best fit ("+i+"): " + bestFitness.get(i).fitness);
		
		Genome<Integer> g = bestFitness.get(bestFitness.size() - 1);
		

		gh2.dbg = true;
		gh2.calculate_fitness(g, (float) (Math.PI * 2), 0.1f, 63);
		
		gh2.print(g);
	}
		
	public Main() {
		go();		
	}

	public static void main(String[] args) {
		new Main();		
	}

}

