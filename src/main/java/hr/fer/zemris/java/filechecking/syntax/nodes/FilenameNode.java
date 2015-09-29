package hr.fer.zemris.java.filechecking.syntax.nodes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.FileCheckingNodeVisitor;

/**
 * Node that represents {@code filename} command.
 * 
 * @author Jan Tomljanović
 */
public class FilenameNode implements FileCheckingNode, TestNode {

	private String path;
	private List<FileCheckingNode> commands;
	private String message;
	private boolean reverse;

	/**
	 * Constructs new FilenameNode.
	 * 
	 * @param path
	 *            excpected name of the archive
	 * @param message
	 *            that will be printed if test fails
	 * @param commands
	 *            list of commands that will be executed if test passes
	 * @param reverse
	 *            flag that determines if test result should be reversed
	 * @throws IllegalArgumentException
	 *             if path is null
	 */
	public FilenameNode(String path, String message, List<FileCheckingNode> commands, boolean reverse) {
		if (path == null) {
			throw new IllegalArgumentException("Path is neccessary for Filename");
		}
		this.path = path;
		this.message = message;
		this.reverse = reverse;
		if (commands != null) {
			this.commands = new ArrayList<>(commands);
		}
	}


	/**
	 * Method that returns path that is the expected name of the archive
	 * 
	 * @return expected name of the archive
	 */
	public String getPath() {
		return path;
	}

	@Override
	public List<FileCheckingNode> getCommands() {
		if (commands != null) {
			return new ArrayList<>(commands);
		}
		return null;
	}

	@Override
	public String getMessage() {
		return message;
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
