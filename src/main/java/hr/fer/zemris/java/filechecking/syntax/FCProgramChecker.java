package hr.fer.zemris.java.filechecking.syntax;

import hr.fer.zemris.java.filechecking.lexical.FileCheckingLexicalException;
import hr.fer.zemris.java.filechecking.lexical.FileCheckingTokenizer;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that can check whether the file checking program has correct syntax. If errors exists,
 * it finds and stores them
 * 
 * @author Jan Tomljanović
 */
public class FCProgramChecker {

	private String program;
	private List<String> errors = new ArrayList<>();
	private FileCheckingParser parser;
	private FileCheckingTokenizer tokenizer;


	/**
	 * Constructs file checking program checker that will find errors within the program given
	 * as an argument.
	 * 
	 * @param program
	 *            to be checked
	 * @throws IllegalArgumentException
	 *             if program is null
	 */
	public FCProgramChecker(String program) {
		if (program == null) {
			throw new IllegalArgumentException("ProgramChecker requires program");
		}
		this.program = program;

		generateErrors();
	}


	/**
	 * Method that searches for errors in the program and stores them into private list of
	 * errors
	 */
	private void generateErrors() {
		generateTokenErrors();
		if (hasErrors()) {
			return;
		}
		generateParseErrors();
	}


	/**
	 * Method that searches for errors that occur during tokenization
	 */
	private void generateTokenErrors() {
		tokenizer = new FileCheckingTokenizer(program);
		try {
			tokenizer.tokenize();
		} catch (FileCheckingLexicalException e) {
			errors.add(e.getMessage());
		}
	}


	/**
	 * Method that searches for errors that occur during parsing. <br>
	 * When it finds an errors it doesn't stop but jumps to the next program keyword and
	 * continues searching for more errors
	 */
	private void generateParseErrors() {
		int index = 0;
		parser = new FileCheckingParser(tokenizer);
		int numUnclosedBrackets = 0;

		while (true) {
			try {
				parser.parseFrom(index, false);

			} catch (FileCheckingSyntaxException e) {
				index = e.getListPosition();
				numUnclosedBrackets += parser.numOfUnclosedBrackets();

				if (e.getMessage().equals("Closed bracket not expected") && numUnclosedBrackets > 0) {
					index++;
					continue;
				}
				errors.add("At token " + (index + 1) + " : " + e.getMessage());
				int nindex = parser.findNextKeywordFrom(index);
				if (nindex < 0) {
					break;
				}

				numUnclosedBrackets += parser.bracketsAppearBetween(index, nindex);
				index = nindex;
				continue;
			}
			break;
		}
	}


	/**
	 * Method that says whether the program given in the constructor contains errors
	 * 
	 * @return true if program has errors, false otherwise
	 */
	public boolean hasErrors() {
		return errors.size() > 0;
	}


	/**
	 * Method that return list of errors found in the program
	 * 
	 * @return list of program errors
	 */
	public List<String> errors() {
		return new ArrayList<>(errors);
	}


	/**
	 * Method that returns the program node of the program, actually the compiled program. If
	 * program has errors it throws an exception
	 * 
	 * @return program node of the program
	 * @throws IllegalStateException if program contains errors
	 */
	public ProgramNode getProgramNode() {
		if (hasErrors()) {
			throw new IllegalStateException("Access to program node denied while it has errors");
		}
		return parser.getProgramNode();
	}


}
