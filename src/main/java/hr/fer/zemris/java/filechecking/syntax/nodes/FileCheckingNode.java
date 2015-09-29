package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.FileCheckingNodeVisitor;

/**
 * Interface that defines what method a node used in file checking program should have.
 * 
 * @author Jan Tomljanović
 */
public interface FileCheckingNode {

	/**
	 * Method that accepts {@link FileCheckingNodeVisitor} that will visit the node
	 * 
	 * @param visitor
	 * @return false if after the visit the program should terminate, true otherwise
	 */
	public boolean accept(FileCheckingNodeVisitor visitor);
}
