package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.FileCheckingNodeVisitor;

/**
 * Node that represents {@code def} command.
 * 
 * @author Jan Tomljanović
 */
public class DefNode implements FileCheckingNode {

	private String varName;
	private String path;

	/**
	 * Constructs a DefNode with the provided variable name and path that is the content of the
	 * variable.
	 * 
	 * @param varName
	 *            name of the variable
	 * @param path
	 *            content of the variable
	 * @throws IllegalArgumentException
	 *             if any argument is null
	 */
	public DefNode(String varName, String path) {
		if (varName == null || path == null) {
			throw new IllegalArgumentException();
		}
		this.varName = varName;
		this.path = path;
	}


	/**
	 * Method that returns variable name
	 * 
	 * @return variable name
	 */
	public String getVarName() {
		return varName;
	}


	/**
	 * Method that returns the content of the variable
	 * 
	 * @return content of the variable
	 */
	public String getPath() {
		return path;
	}


	@Override
	public boolean accept(FileCheckingNodeVisitor visitor) {
		return visitor.visit(this);
	}

}
