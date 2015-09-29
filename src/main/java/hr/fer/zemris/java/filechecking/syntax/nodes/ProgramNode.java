package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.FileCheckingNodeVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Node that contains list of nodes that program generated. Using the program node file
 * checking program can be executed.
 * 
 * @author Jan Tomljanović
 */
public class ProgramNode implements FileCheckingNode {

	private List<FileCheckingNode> commands;

	/**
	 * Constructs new program node with the given list od nodes.
	 * 
	 * @param commands
	 *            list od nodes
	 * @throws IllegalArgumentException
	 *             if commands is null
	 */
	public ProgramNode(List<FileCheckingNode> commands) {
		if (commands == null) {
			throw new IllegalArgumentException("commands can't be null");
		}
		this.commands = new ArrayList<>(commands);
	}

	/**
	 * Method that returns a copy of private list of nodes
	 * 
	 * @return list of nodes
	 */
	public List<FileCheckingNode> getCommands() {
		return new ArrayList<>(commands);
	}

	@Override
	public boolean accept(FileCheckingNodeVisitor visitor) {
		return visitor.visit(this);
	}

}
