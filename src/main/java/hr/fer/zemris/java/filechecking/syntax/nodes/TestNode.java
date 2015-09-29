package hr.fer.zemris.java.filechecking.syntax.nodes;

import java.util.List;

/**
 * Interface that contains methods which nodes that Test something should have
 * 
 * @author Jan Tomljanović
 */
public interface TestNode {


	/**
	 * Method that returns list of commands that node has. If node has no commands it returns
	 * null.
	 * 
	 * @return list of commands
	 */
	List<FileCheckingNode> getCommands();


	/**
	 * Mehtod that returns error message that node has. If node has no message it returns null.
	 * 
	 * @return message
	 */
	String getMessage();
}
