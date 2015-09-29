package hr.fer.zemris.java.filechecking.lexical;

/**
 * Token used to compile file checking program.
 * 
 * @author Jan Tomljanović
 */
public class FileCheckingToken {

	private FileCheckingTokenType type;
	private Object value;

	/**
	 * Constructs a token of a given type and value
	 * 
	 * @param type
	 *            type of the token
	 * @param value
	 *            of the token - can be null
	 * @throws IllegalArgumentException
	 *             if type is null
	 */
	public FileCheckingToken(FileCheckingTokenType type, Object value) {
		if (type == null) {
			throw new IllegalArgumentException();
		}
		this.type = type;
		this.value = value;
	}


	/**
	 * Method that gets the type of the token
	 * 
	 * @return type of the token
	 */
	public FileCheckingTokenType getType() {
		return type;
	}


	/**
	 * Method that returns the value of the token
	 * 
	 * @return value of the token
	 * 
	 */
	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		if (this.value != null && this.value instanceof String) {
			return (String) value;
		}
		return type.toString();
	}


}
