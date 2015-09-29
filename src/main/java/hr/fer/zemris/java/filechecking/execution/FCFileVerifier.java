package hr.fer.zemris.java.filechecking.execution;

import hr.fer.zemris.java.filechecking.syntax.FCProgramChecker;
import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.FCProgramExecutorVisitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that executes given program in order to see whether some given zip archive has content
 * in line with expectations described in the program.
 * 
 * @author Jan Tomljanović
 */
public class FCFileVerifier {

	private List<String> errors;


	/**
	 * Creates an instance of the class that immediately compiles and executes the program. The
	 * program should have no mistakes otherwise excption will be thrown.
	 * 
	 * @param file
	 *            zip archive to be checked
	 * @param name
	 *            of the archive
	 * @param program
	 *            to be executed
	 * @param initialData
	 *            for the program. Map of initial variables.
	 */
	public FCFileVerifier(File file, String fileName, String program, Map<String, String> initialData) {
		FCProgramExecutorVisitor executor = new FCProgramExecutorVisitor(file.getAbsolutePath(), fileName, initialData);
		FCProgramChecker checker = new FCProgramChecker(program);
		checker.getProgramNode().accept(executor);
		errors = executor.getErrors();
	}


	/**
	 * Checks to see if File Verifier has found errors in the file
	 * 
	 * @return true if there are errors in the zip archive, false otherwise
	 */
	public boolean hasErrors() {
		return errors.size() > 0;
	}


	/**
	 * Method that returns a list of errors found in the archive
	 * 
	 * @return list of errors
	 */
	public List<String> errors() {
		return new ArrayList<>(errors);
	}
}
