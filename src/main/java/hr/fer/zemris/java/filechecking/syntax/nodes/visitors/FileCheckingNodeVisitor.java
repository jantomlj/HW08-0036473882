package hr.fer.zemris.java.filechecking.syntax.nodes.visitors;

import hr.fer.zemris.java.filechecking.syntax.nodes.*;

public interface FileCheckingNodeVisitor {

	/**
	 * Visits {@link ProgramNode} and executes its commands
	 * 
	 * @param node
	 *            that will be visited
	 * @return false if program should terminate, true otherwise
	 */
	public boolean visit(ProgramNode node);


	/**
	 * Visits {@link DefNode} and executes def command
	 * 
	 * @param node
	 *            that will be visited
	 * @return true
	 */
	public boolean visit(DefNode node);


	/**
	 * Visits {@link ExistsNode} and executes exists command
	 * 
	 * @param node
	 *            that will be visited
	 * @return false if program should terminate, true otherwise
	 */
	public boolean visit(ExistsNode node);


	/**
	 * Visits {@link FilenameNode} and executes filename command
	 * 
	 * @param node
	 *            that will be visited
	 * @return false if program should terminate, true otherwise
	 */
	public boolean visit(FilenameNode node);


	/**
	 * Visits {@link FormatNode} and executes format command
	 * 
	 * @param node
	 *            that will be visited
	 * @return false if program should terminate, true otherwise
	 */
	public boolean visit(FormatNode node);


	/**
	 * Visits {@link TerminateNode} and executes terminate command
	 * 
	 * @param node
	 *            that will be visited
	 * @return false
	 */
	public boolean visit(TerminateNode node);


	/**
	 * Visits {@link FailNode} and executes FailNode command
	 * 
	 * @param node
	 *            that will be visited
	 * @return false if program should terminate, true otherwise
	 */
	public boolean visit(FailNode node);
}
