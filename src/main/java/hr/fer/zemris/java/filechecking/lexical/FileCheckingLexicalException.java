package hr.fer.zemris.java.filechecking.lexical;

import hr.fer.zemris.java.filechecking.FileCheckingException;

/**Exception thrown during tokenization of the file checking program.
 * 
 * @author Jan Tomljanović
 */
public class FileCheckingLexicalException extends FileCheckingException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor that takes no agruments
	 */
	public FileCheckingLexicalException() {
		super();
	}

	
	/**
	 * Constructor that takes a message as an agrument
	 * 
	 * @param message
	 *            exception message
	 */
	public FileCheckingLexicalException(String message) {
		super(message);
	}

	
}
