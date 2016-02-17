package aiGen;

/**
 * Exception that is thrown when Fitness calculation is done incorrectly. 
 * This usually happens when (Genome<T>) obj.fitness != GenomeOperators.getFitness(obj);
 *  
 * This may happen when objects have its "DNA" code modified what is not acceptable.   
 * 
 * @author Witold Kaczurba 
 */
public class GenomeFitnessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 337027645473782119L;
}
