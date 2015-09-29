package hr.fer.zemris.java.filechecking;

/**
 * Exception that is thrown by classes within the filechecking package.
 * 
 * @author Jan Tomljanović
 */
public class FileCheckingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that takes no agruments
	 */
	public FileCheckingException() {
		super();
	}

	/**
	 * Constructor that takes a message as an agrument
	 * 
	 * @param message
	 *            exception message
	 */
	public FileCheckingException(String message) {
		super(message);
	}


}
