package aiGen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class is a representation of a Genome&lt;T&gt; where &lt;T&gt; is any Object type.
 * Genome&lt;T&gt; is used in conjunction with <b>GenomeOperators&lt;T&gt;</b>
 *   
 * <p>Objects of this type should be final and never modified again.
 * Genetic functions (e.g. crossOver, tournamentSelection, mutation) must return either a clone or a new Genome&lt;T&gt;.
 * Otherwise functions such as <b>genPool.remove()</b> will not work properly and will end up removing too many or too few genomes from its pool.
 * </p>  
 * 
 * @see GenomeOperators
 * 
 * @author Witold Kaczurba 
 */
public class Genome<T> {	
	final public List<T>  data; // unmodifiable
	final public GenomeOperators<T> genOp;
	final public double fitness;
	
	public Genome(GenomeOperators<T> genOp) {
		super();
		
		List<T> data = new ArrayList<T>();
		this.genOp = genOp;
		data = this.genOp.randomGenomeData(this);
		this.data = Collections.unmodifiableList(data);
		this.fitness = genOp.getFitness(this);
	}
	
	public Genome(GenomeOperators<T> genOp, List<T> data) {
		super();
		
		this.genOp = genOp;
		this.data = Collections.unmodifiableList( new ArrayList<T>(data) ); // copy + make unmodifable		
		this.fitness = genOp.getFitness(this);
	}

	public String toString() {
		String s = " fitness= " + fitness + "\t" + data.toString(); // + genOp.getFitness(this);

		return s;  
	}
	
	public boolean equals(Object ref) {		
		if (ref == null) {
			return false;
		}
		
		if (! (ref instanceof Genome)) {		
			return false;
		}
		
		@SuppressWarnings("unchecked")
		Genome<T> o = (Genome<T>) ref;
		
		if (this.fitness != o.fitness) {
			// System.out.println("Genomes have different fitness");
			return false;
		}
		
		if (! this.data.equals(o.data)) {
			// System.out.println("Genomes have different data");
			if (this.data.size() != o.data.size()) {
				// System.out.println("Genomes have different lengths");
				return false;
			} else {
				// System.out.println("Genomes have Same size->" + this.data.size() );
			}
			for (int i=0; i<data.size(); i++)
			{
				if (! data.get(i).equals(o.data.get(i)) ) {
					// THE PROBLEM LIES HERE:
					
					//System.out.println("Data not equal:");
					//System.out.println("  A("+i+"): " + this.data.get(i));
					//System.out.println("  B("+i+"): " + o.data.get(i));
					
					//Lines acast = (Lines) this.data.get(i);
					//Lines bcast = (Lines) o.data.get(i);
					//System.out.println("  acast.equals(bcast)? => " + acast.equals(bcast) );
					
					return false;
				}
			}
			// System.out.println("Genomes appear not to have same content!");
			return false;
		} else  {
			//System.out.println("Genomes appear to have same content!");			
			return true;
		}
	}
}

