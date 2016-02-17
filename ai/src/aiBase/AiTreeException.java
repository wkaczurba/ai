package aiBase;

/**
 * Exception when things go wrong.
 * 
 * @author Witold Kaczurba 
 */
public class AiTreeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4777850584445149784L;
	String message;
	public AiTreeException(String s) { message = new String(s); System.out.println(s); }
	public String getMessage() { return message; }
}

