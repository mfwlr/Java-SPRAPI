package sprites;

public class SprapiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8927633924755583620L;

	public SprapiException() {
		super("An unknown exception has occured with SPRAPI");
	}
	
	public SprapiException(String s) {
		super(s);
	}
	
}
