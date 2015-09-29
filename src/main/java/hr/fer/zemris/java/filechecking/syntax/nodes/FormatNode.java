package hr.fer.zemris.java.filechecking.syntax.nodes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.FileCheckingNodeVisitor;

/**
 * Node that represents {@code format} command.
 * 
 * @author Jan Tomljanović
 */
public class FormatNode implements FileCheckingNode, TestNode {

	private String format;
	private String message;
	private boolean reverse;
	private List<FileCheckingNode> commands;


	/**
	 * Constructs new FormatNode.
	 * 
	 * @param format
	 *            excpected format of the archive
	 * @param message
	 *            that will be printed if test fails
	 * @param commands
	 *            list of commands that will be executed if test passes
	 * @param reverse
	 *            flag that determines if test result should be reversed
	 * @throws IllegalArgumentException
	 *             if format is null
	 */
	public FormatNode(String format, String message, List<FileCheckingNode> commands, boolean reverse) {
		if (format == null) {
			throw new IllegalArgumentException("Path is neccessary for Filename");
		}
		this.format = format;
		this.message = message;
		this.reverse = reverse;
		if (commands != null) {
			this.commands = new ArrayList<>(commands);
		}
	}


	/**
	 * Method that returns the expected format of the archive
	 * 
	 * @return expected format of the archive
	 */
	public String getFormat() {
		return format;
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
