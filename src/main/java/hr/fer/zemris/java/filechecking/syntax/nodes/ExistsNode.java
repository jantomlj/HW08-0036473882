package hr.fer.zemris.java.filechecking.syntax.nodes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.FileCheckingNodeVisitor;

/**
 * Node that represents {@code exists} command.
 * 
 * @author Jan Tomljanović
 */
public class ExistsNode implements FileCheckingNode, TestNode {

	private List<FileCheckingNode> commands;
	private boolean directory;
	private String message;
	private String path;
	private boolean reverse;


	/**
	 * Constructs a new ExistsNode.
	 * 
	 * @param directory
	 *            true if looking for a directory, false if looking for a file
	 * @param path
	 *            to be looked for
	 * @param message
	 *            that is printed if test fails
	 * @param commands
	 *            list of commands that will be executed if test passes
	 * @param reverse
	 *            should the result of the test be reversed
	 * @throws IllegalArgumentException
	 *             if path is null
	 */
	public ExistsNode(boolean directory, String path, String message, List<FileCheckingNode> commands, boolean reverse) {
		if (path == null) {
			throw new IllegalArgumentException("Path is needed for exists command");
		}
		this.directory = directory;
		this.path = path;
		this.message = message;
		this.reverse = reverse;
		if (commands != null) {
			this.commands = new ArrayList<>(commands);
		}
	}


	@Override
	public List<FileCheckingNode> getCommands() {
		if (commands != null) {
			return new ArrayList<>(commands);
		}
		return null;
	}


	/**
	 * Method that returns whether it should be looked for a directory.
	 * 
	 * @return true if looking for a directory, false if looking for file
	 */
	public boolean isDirectory() {
		return directory;
	}

	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * Method that returns the path that is looked for.
	 * 
	 * @return path that is looked for
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Method that returns whether the result of the test should be reversed.
	 * 
	 * @return true if result of the test should be reversed, false othewise
	 */
	public boolean isReverse() {
		return reverse;
	}


	@Override
	public boolean accept(FileCheckingNodeVisitor visitor) {
		return visitor.visit(this);
	}

}
