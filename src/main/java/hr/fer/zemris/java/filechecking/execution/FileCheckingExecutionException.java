package hr.fer.zemris.java.filechecking.execution;

import hr.fer.zemris.java.filechecking.FileCheckingException;

/**Exception that is thrown during runtime of the file checking program
 * 
 * @author Jan Tomljanović
 */
public class FileCheckingExecutionException extends FileCheckingException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that takes no agruments
	 */
	public FileCheckingExecutionException() {
		super();
	}

	
	/**
	 * Constructor that takes a message as an agrument
	 * 
	 * @param message
	 *            exception message
	 */
	public FileCheckingExecutionException(String message) {
		super(message);
	}

	
}
