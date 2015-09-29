package hr.fer.zemris.java.filechecking.syntax.nodes.visitors;

import hr.fer.zemris.java.filechecking.execution.FileCheckingExecutionException;
import hr.fer.zemris.java.filechecking.formats.Formats;
import hr.fer.zemris.java.filechecking.syntax.nodes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Visitor that can execute command nodes.
 * 
 * @author Jan Tomljanović
 */
public class FCProgramExecutorVisitor implements FileCheckingNodeVisitor {

	private Map<String, String> variables;

	private List<String> errors = new ArrayList<>();

	private ZipFile zipFile;

	private String name;

	/**
	 * Contructs visitor with given path to the zip archive, name of the archive, and a map of
	 * initial variables
	 * 
	 * @param basePath
	 *            path to the zip archive
	 * @param name
	 *            of the archive
	 * @param initialData
	 *            map of initial variables
	 * @throws IllegalArgumentException
	 *             if basePath or name are null
	 */
	public FCProgramExecutorVisitor(String basePath, String name, Map<String, String> initialData) {
		if (basePath == null || name == null) {
			throw new IllegalArgumentException();
		}
		try {
			this.zipFile = new ZipFile(basePath);
		} catch (IOException e) {
			throw new IllegalArgumentException("Path couldn't be used as a zip file");
		}
		this.name = name;
		if (initialData != null) {
			variables = new HashMap<>(initialData);
		} else {
			variables = new HashMap<>();
		}
	}


	/**
	 * Method that return list of errors that the program found while checking the archive
	 * 
	 * @return list of errors
	 */
	public List<String> getErrors() {
		return new ArrayList<>(errors);
	}


	@Override
	public boolean visit(ProgramNode programNode) {
		for (FileCheckingNode node : programNode.getCommands()) {
			boolean contin = node.accept(this);
			if (!contin) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean visit(TerminateNode node) {
		return false;
	}


	@Override
	public boolean visit(FailNode node) {
		return doAction(node, node.isReverse(), "Fail test failed");
	}


	@Override
	public boolean visit(DefNode node) {
		String varName = node.getVarName();
		String path = handlePath(node.getPath());
		variables.put(varName, path);
		return true;
	}

	@Override
	public boolean visit(FormatNode node) {
		String format = node.getFormat();
		boolean res;
		try {
			res = Formats.matches(name, format);
		} catch (UnsupportedOperationException e) {
			throw new FileCheckingExecutionException("Given format not known");
		}
		res = node.isReverse() ? !res : res;
		return doAction(node, res, "Format test didn't pass on format " + format);
	}

	@Override
	public boolean visit(FilenameNode node) {
		String name = node.getPath();
		boolean same;
		if (name.charAt(0) == 'i') {
			same = handlePath(name).trim().equalsIgnoreCase(this.name);
		} else {
			same = handlePath(name).trim().equals(this.name);
		}
		same = node.isReverse() ? !same : same;
		return doAction(node, same, "Filename test didn't pass on name " + name);
	}


	@Override
	public boolean visit(ExistsNode node) {
		String path = handlePath(node.getPath());
		boolean dir = node.isDirectory();
		boolean res = checkIfExistsInZip(path, dir);
		res = node.isReverse() ? !res : res;
		return doAction(node, res, "Exists test didn't pass on path " + path);
	}


	/**
	 * Method that checks whether the given path exists in the zip and that it is a
	 * directory/file depending on the flag dir.
	 * 
	 * @param path
	 *            file/directory that is looked for in the archive
	 * @param dir
	 *            if true it is method looks for a directory, if false for a file
	 * @return true if the given file/directory path exists in the archive
	 */
	@SuppressWarnings("unchecked")
	private boolean checkIfExistsInZip(String path, boolean dir) {
		if (!dir) {
			return zipFile.getEntry(path) != null;
		}

		Enumeration<ZipEntry> enumeration = (Enumeration<ZipEntry>) zipFile.entries();
		while (enumeration.hasMoreElements()) {
			if (enumeration.nextElement().getName().startsWith(path + "/")) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Method that does an action after it is known that the test failed or passed. If test
	 * passed existing commands will be run, otherwise error message will be added to the error
	 * list.
	 * 
	 * @param node
	 *            test node that ran the test
	 * @param res
	 *            flag that is true if test passed, false otherwise
	 * @param message
	 *            message that will be added to the error list if the test failed
	 * @return false if program should terminate, true otherwise
	 */
	private boolean doAction(TestNode node, boolean res, String message) {
		if (res) {
			return runPossibleCommands(node);
		} else {
			String mess = node.getMessage();
			if (mess != null) {
				errors.add(handlePath(mess));
			} else {
				errors.add(message);
			}
			return true;
		}
	}


	/**
	 * Method that runs commands saved in the test node if they exist.
	 * 
	 * @param node
	 *            test node whose commands will be run
	 * @return false if program should terminate, true otherwise
	 */
	private boolean runPossibleCommands(TestNode node) {
		List<FileCheckingNode> nodes = node.getCommands();
		if (nodes != null) {
			return new ProgramNode(nodes).accept(this);
		}
		return true;
	}


	/**
	 * Method that converts package contained in a path to a legit path.
	 * 
	 * @param path
	 *            that contains package
	 * @return converted path so that package is now a path
	 */
	private String convertPackage(String path) {
		if (path.contains(":")) {
			int index = path.indexOf(':');
			String sub = path.substring(index);
			sub = sub.replaceAll("\\.|:", "/");
			path = path.substring(0, index) + sub;
		}
		return path;
	}


	/**
	 * Method that supstitutes variables contained in a path to a legit path.
	 * 
	 * @param path
	 *            that containes variables
	 * @return path in which variables are supstituted by the path the variable contains
	 */
	private String variableSupstitution(String path) {
		int start;
		int end = 0;
		while (true) {
			start = path.indexOf("${", end + 1);
			end = path.indexOf("}", end + 1);
			if (start == -1 || end == -1) {
				break;
			}
			String var = path.substring(start + 2, end).trim();
			String realPath = variables.get(var);
			if (realPath == null) {
				throw new FileCheckingExecutionException("Dereferencing non-existant variable");
			}
			path = path.substring(0, start) + realPath + path.substring(end + 1);
		}
		return path;
	}


	/**
	 * Method that will supstitute variables, convert package and extract text from quotes from
	 * the given path
	 * 
	 * @param path
	 *            to be handled
	 * @return handled path
	 * @throws IllegalArgumentException
	 *             if path is null
	 */
	private String handlePath(String path) {
		if (path == null) {
			throw new IllegalArgumentException();
		}
		return extractImportant(variableSupstitution(convertPackage(path)));
	}


	/**
	 * Takes text out of the qoutes
	 * 
	 * @param s
	 *            text in quotes
	 * @return text without the quoutes
	 * @throws IllegalArgumentException
	 *             if s is null
	 */
	private String extractImportant(String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		return s.substring(s.indexOf('"') + 1, s.lastIndexOf('"'));
	}

}
