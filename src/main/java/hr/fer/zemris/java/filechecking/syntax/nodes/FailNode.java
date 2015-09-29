package hr.fer.zemris.java.filechecking.syntax.nodes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.FileCheckingNodeVisitor;

/**
 * Node that represent the {@code fail} command.
 * 
 * @author Jan Tomljanović
 */
public class FailNode implements FileCheckingNode, TestNode {

	private String message;
	private boolean reverse;
	private List<FileCheckingNode> commands;

	/**
	 * Constructs a new FailNode
	 * 
	 * @param message
	 *            that will be printed if test fails
	 * @param commands
	 *            list of commands that will be executed if test passes
	 * @param reverse
	 *            flag that determines if test result should be reversed
	 */
	public FailNode(String message, List<FileCheckingNode> commands, boolean reverse) {
		this.message = message;
		this.reverse = reverse;
		if (commands != null) {
			this.commands = new ArrayList<>(commands);
		}
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public List<FileCheckingNode> getCommands() {
		if (commands != null) {
			return new ArrayList<>(commands);
		}
		return null;
	}

	/**
	 * Method that returns whether the test result should be reversed
	 * 
	 * @return true if the test should be reversed, false otherwise
	 */
	public boolean isReverse() {
		return reverse;
	}


	@Override
	public boolean accept(FileCheckingNodeVisitor visitor) {
		return visitor.visit(this);
	}

}
