package hr.fer.zemris.java.filechecking.syntax;

import hr.fer.zemris.java.filechecking.FileCheckingException;

/**
 * Exception that is thrown during parsing of the program
 * 
 * @author Jan Tomljanović
 */
public class FileCheckingSyntaxException extends FileCheckingException {

	private static final long serialVersionUID = 1L;
	private int listPosition;

	/**
	 * Constructor that takes no agruments
	 */
	public FileCheckingSyntaxException() {
		super();
	}

	/**
	 * Constructor that take a message and the list position of the token on which parsing
	 * failed
	 * 
	 * @param message
	 * @param listPosition
	 *            position of the token in the token list where parsing failed
	 */
	public FileCheckingSyntaxException(String message, int listPosition) {
		super(message);
		this.listPosition = listPosition;
	}


	/**
	 * Method that gets position in the token list on which parsing failed.
	 * 
	 * @return list position where parsing failed
	 */
	public int getListPosition() {
		return listPosition;
	}


	/**
	 * Constructor that takes a message as an agrument
	 * 
	 * @param message
	 *            exception message
	 */
	public FileCheckingSyntaxException(String message) {
		super(message);
	}

}
