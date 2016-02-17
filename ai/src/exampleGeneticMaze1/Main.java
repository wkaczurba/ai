package exampleGeneticMaze1;

import java.util.ArrayList;
import java.util.Random;

//import Maze.Maze;
import aiGen.*;

public class Main {
	static Random rand = new Random();
	final int mazeHeight = 20;
	final int mazeWidth = 20;
	
	Maze m = new Maze(mazeHeight, mazeWidth);
	GenOp         rules = new GenOp(40, mazeHeight, mazeWidth); // was:25
	GenPool<Lines>   pool  = new GenPool<Lines>(rules);	
	
	public void produceNextGeneration(int populationSize, int generationCount, double mp, double cp) throws GenomeFitnessException {
		// Initial POPULATION:
		pool.createRandom(populationSize * 3);
		// pool.fitnessCheckTest(); // GenPool : SELF-TEST.
		System.out.println(pool);
		
		// GENETIC ALGORITH - TOP:
		GenPool<Lines>  nextPool;
		nextPool = new GenPool<Lines>(rules);
		pool.sortByFitness(true);
		nextPool.add(pool.genomes.subList(0, populationSize));
		pool = nextPool;
		// END OF INITIAL SELECTION.
		
		int generation = 0;
		ArrayList<Genome<Lines>> bestFitness = new ArrayList<Genome<Lines>>();
		
		// Creating next generation:
		while (generation < generationCount) {
			nextPool = pool.runNextGeneration(mp, cp);
			
			bestFitness.add(nextPool.getMostFit(true));
			pool = nextPool;
			
			nextPool = pool.runTournamentSelection();
			pool = nextPool;

			generation++;
		}
	
		for (int i=0; i<bestFitness.size(); i++)
			System.out.println("Best fit ("+i+"): " + bestFitness.get(i).fitness);
		
		// PRINTING LAST BEST:
		Maze m = new Maze(mazeHeight, mazeWidth);
		Genome<Lines> g = bestFitness.get(bestFitness.size() - 1); // last genome
		applyGenomeToMaze(m, g);
		
		System.out.println("pool (last generation): \n" + m + "\n" + m.getStats());
	}
	
	public void applyGenomeToMaze(Maze m, Genome<Lines> g)
	{
		MazeLineDrawer md = new MazeLineDrawer(m);
		
		try {
			md.addLines(g.data);
		} catch (MazeLineDrawerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public Main() {
		try {
		produceNextGeneration(100, 500, 15.0, 25.0);
		} catch (GenomeFitnessException e) {
			System.out.println("Error" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}

}



