package aiGen;

import java.util.List;

/**
 * This interface is a core interface for users.
 * It should be used to describes basic genetic operations on Genomes,
 * calculation of fitness as well as creating random Genome.
 * 
 * @author WKaczurb
 *
 * @param <T> base most primitive object that is used in a Genome. 
 * Genome containst List&lt;T&gt; that is its "DNA".
 */
public interface GenomeOperators<T> {

	public Genome<T>       mutate(Genome<T> g);
	
	/**
	 * <b>Note:</b> crossOver must return List containing two new Genomes.
	 * 	CrossOver must not modify g1 and g2 genomes
	 * 
	 *  @param g1 Genome one
	 *  @param g2 Genome two
	 *  @return returns list containing two genomes
	 */	
	public List<Genome<T>> crossOver(Genome<T> g1, Genome<T> g2); // returns two genomes after cross-over
	
	/**
	 * <b>Note:</b> tournamentSelection must return cloned version of the selected Genome.
	 *         It must not return the selected Genome itself.
	 *         
	 *  @param pool GenPool on which tournamentSelection is to be done
	 *  @return returns selected Genome. 
	 */
	public Genome<T>       tournamentSelection(GenPool<T> pool);
	
	/**
	 * Generates code/data for Genome.
	 * The function is used by Genome constructor.
	 *  
	 *  @param g is normally not used / not handled;
	 *  @return ArrayList of &lt;T&gt; type that is copied into new Genome's code.
	 */
	public List<T>         randomGenomeData(Genome<T> g);
	
	/**
	 * Returns fitness of a Genome.
	 *  
	 *  @param g is Genome
	 *  @return fitness value that can be either the greater - the fitter; or the lesser - the fitter; 
	 *  This preference is handled by tournamentSelection that selects most fit.    
	 */	
	public double     	   getFitness(Genome<T> g);
	
}
