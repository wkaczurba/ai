package exampleGeneticMaze1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import aiGen.GenPool;
import aiGen.Genome;
import aiGen.GenomeOperators;

public class GenOp implements GenomeOperators<Lines> {
	final Integer length; // Length of a genome
	Random rand = new Random();
	final int mazeHeight; // Maze-specific constraints
	final int mazeWidth; // Maze-specific constraint
	
	public GenOp(int genomeLength, int mazeHeight, int mazeWidth) {
		length = genomeLength;
		
		this.mazeHeight = mazeHeight;
		this.mazeWidth = mazeWidth;
	}

	@Override
	public Genome<Lines> mutate(Genome<Lines> g) {
		ArrayList<Lines> data = new ArrayList<Lines>(g.data);		
		int n = rand.nextInt(length);
		
		Lines old_l = data.get(n);
		Lines new_l; 
		
		do {
			int 	newJ = rand.nextInt(mazeHeight);
			int 	newI = rand.nextInt(mazeWidth);
			
			int 	newLength = Math.min( 3, old_l.length + rand.nextInt(mazeWidth + 5) - 5 );
			boolean newIsHorizontal = !old_l.isHorizontal;
			
			// TODO: Sort out the numbers so they use single parameter
			switch (rand.nextInt(4)) {
				case 0: new_l = new Lines(newJ, old_l.i, old_l.length, old_l.isHorizontal); break;
				case 1: new_l = new Lines(old_l.j, newI, old_l.length, old_l.isHorizontal); break;
				case 2: new_l = new Lines(old_l.j, old_l.i, newLength, old_l.isHorizontal); break;
				case 3:
		       default: new_l = new Lines(old_l.j, old_l.i, old_l.length, newIsHorizontal); break;
			}			
		} while (! new_l.lineFitsRectagle(0, 0, mazeHeight-1, mazeWidth-1));
		
		data.set(n, new_l);
		
		return new Genome<Lines>(this, data);
	}

	//@Override
	// This crossover changes randomly single code between two genomes as it is more efficient than changing exchanging strings
	// It has only left cutoff point 
	public List<Genome<Lines>> crossOver(Genome<Lines> g1, Genome<Lines> g2) {
		List<Lines> data1 = new ArrayList<Lines>(g1.data);
		List<Lines> data2 = new ArrayList<Lines>(g2.data);
		
		final int cutoff = 3; 
		int n, m;
		
		// Two random numbers:
		do {
			n = rand.nextInt(length);
		} while ( n < cutoff );

		do { 
			m = rand.nextInt(length); 
		} while ( m == n && m < cutoff );
			
		// swapping: (it swaps only one line (??))
		Lines a = data1.get(n);
		Lines b = data2.get(m);
		data1.set(n, b);
		data2.set(m, a);
		
		Genome<Lines> newG1 = new Genome<Lines>(this, data1);
		Genome<Lines> newG2 = new Genome<Lines>(this, data2);
		
		return new ArrayList<Genome<Lines>>(Arrays.asList(newG1, newG2));
	}

	@Override
	public double getFitness(Genome<Lines> g) {
		double retval;
		
		Maze m = new Maze(mazeHeight, mazeWidth);
		MazeLineDrawer md = new MazeLineDrawer(m);
		
		try {
			md.addLines(g.data);
		} catch (MazeLineDrawerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Maze.Statistics s = m.getStats();
	
		retval = s.totalLength - 10 * s.deadEnds - 20 * s.parallels; //retval = (s.totalLength + s.crossings * 3 - s.deadEnds * 10 - s.parallels * 20);	
		
		return retval;
	}

	@Override
	public List<Lines> randomGenomeData(Genome<Lines> g) {
		ArrayList<Lines> retval = new ArrayList<Lines>();
		for (int i=0; i<this.length; i++) {
			Lines l = new Lines(rand.nextInt(mazeHeight), rand.nextInt(mazeWidth), rand.nextInt(mazeWidth-3), rand.nextBoolean());
			
			while (!l.lineFitsRectagle(0, 0, mazeHeight-1, mazeWidth-1)) {
				l = new Lines(rand.nextInt(mazeHeight), rand.nextInt(mazeWidth), rand.nextInt(mazeWidth-3), rand.nextBoolean());
			}
			retval.add(l);			
		}
		
		return retval;
	}

	@Override
	public Genome<Lines> tournamentSelection(GenPool<Lines> pool) {
		Genome<Lines> retval = null;
		Genome<Lines> tmp    = null;
		
		// Gets most fit out of four:
		for (int i = 0; i < 4; i++) {
			tmp = pool.getRandom();
			
			if (retval == null || retval.fitness < tmp.fitness) 
				retval = tmp;
		}

		return new Genome<Lines>(this, retval.data); // create new one.
	}
}
