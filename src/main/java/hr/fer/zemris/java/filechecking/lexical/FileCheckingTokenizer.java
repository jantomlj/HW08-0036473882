package hr.fer.zemris.java.filechecking.lexical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that is used to tokenize the file checking program in order to compile it.
 * 
 * @author Jan Tomljanović
 */
public class FileCheckingTokenizer {

	/** program content */
	private char[] data;
	/** cursor */
	private int curPos;

	/** list of generatet tokens */
	private List<FileCheckingToken> tokens;
	/** last generated token */
	private FileCheckingToken lastToken;

	/** position in the token list */
	private int listPos;


	private static final Map<Character, FileCheckingTokenType> mapper;

	static {
		mapper = new HashMap<Character, FileCheckingTokenType>();
		mapper.put('{', FileCheckingTokenType.OPEN_CURLY_BRACKETS);
		mapper.put('}', FileCheckingTokenType.CLOSE_CURLY_BRACKETS);
		mapper.put('!', FileCheckingTokenType.NOT);
	}


	private static final Set<String> keywords;

	static {
		keywords = new HashSet<>();
		keywords.add("def");
		keywords.add("terminate");
		keywords.add("filename");
		keywords.add("format");
		keywords.add("exists");
		keywords.add("fail");
	}

	/**
	 * Constructors a tokenizer that can tokenize given program
	 * 
	 * @param program
	 *            to be tokenized
	 * @throws IllegalArgumentException
	 *             if program is null
	 */
	public FileCheckingTokenizer(String program) {
		if (program == null) {
			throw new IllegalArgumentException();
		}
		program = clearComments(program);
		data = program.toCharArray();
		curPos = 0;
		tokens = new ArrayList<>();
	}

	/**
	 * Method that tokenizes the program and places generated tokens into a list
	 */
	public void tokenize() {
		while (lastToken == null || lastToken.getType() != FileCheckingTokenType.EOF) {
			tokens.add(getNextToken());
		}
	}

	/**
	 * Method that can determine from which position in the token list will tokens start being
	 * read
	 * 
	 * @param index
	 *            the position in the list (starting from 0)
	 */
	public void startGettingTokensFrom(int index) {
		listPos = index;
	}

	/**
	 * Method that reads next token in the token list
	 * 
	 * @return next token from the list
	 * @throws IndexOutOfBoundsException
	 *             if there is no more tokens
	 */
	public FileCheckingToken nextToken() {
		if (listPos >= tokens.size()) {
			throw new IndexOutOfBoundsException("No more tokens");
		}
		return tokens.get(listPos++);
	}


	/**
	 * Method that takes token list pointer and decreases it by one
	 */
	public void oneTokenBack() {
		if (listPos > 0) {
			listPos--;
		}
	}

	/**
	 * Method that tells where was the token list pointer one step in the past. Formally it
	 * gives current pointer position minus one, or if pointer points to 0, then it returns 0
	 * 
	 * @return past position of the token list pointer
	 */
	public int getPastListPos() {
		if (listPos == 0) {
			return 0;
		}
		return listPos - 1;
	}


	/**
	 * Method that extracts next token from the program during tokenization and returns it
	 * 
	 * @return next token
	 * @throws FileCheckingLexicalException
	 */
	private FileCheckingToken getNextToken() {
		if (lastToken != null && lastToken.getType() == FileCheckingTokenType.EOF) {
			throw new FileCheckingLexicalException("No more tokens to read");                // already ended
		}
		eatWhitespace();

		if (curPos >= data.length) {
			lastToken = new FileCheckingToken(FileCheckingTokenType.EOF, null);             // ends now
			return lastToken;
		}

		FileCheckingTokenType mappedType = mapper.get(data[curPos]);
		if (mappedType != null) {
			lastToken = new FileCheckingToken(mappedType, null);                           // some character from map
			curPos++;
			return lastToken;
		}

		if (data[curPos] == '"' || (curPos + 1 < data.length && data[curPos + 1] == '"')) {         // string
			if (data[curPos] == '"' && (curPos + 1 < data.length && data[curPos + 1] == '"')
					|| (curPos + 1 < data.length && data[curPos + 1] == '"')
					&& (curPos + 2 < data.length && data[curPos + 2] == '"')) {
				throw new FileCheckingLexicalException("Unable to tokenize. Empty String found. pos:" + curPos);
			}
			int index = curPos + 2;
			while (true) {
				if (index >= data.length) {
					throw new FileCheckingLexicalException("Unable to tokenize. String not finished. pos:" + curPos);
				}
				if (data[index] == '"') {
					break;
				}
				index++;
			}
			String stringToken = new String(data, curPos, index + 1 - curPos);
			lastToken = new FileCheckingToken(FileCheckingTokenType.STRING, stringToken);
			curPos = index + 1;
			return lastToken;
		}

		int index = curPos + 1;                                                     // something else - argument or keyword
		while (true) {
			if (index >= data.length || Character.isWhitespace(data[index])) {
				break;
			}
			index++;
		}

		String someToken = new String(data, curPos, index - curPos);
		curPos = index;
		if (keywords.contains(someToken)) {
			lastToken = new FileCheckingToken(FileCheckingTokenType.KEYWORD, someToken);
			return lastToken;
		}

		lastToken = new FileCheckingToken(FileCheckingTokenType.ARGUMENT, someToken);
		return lastToken;
	}


	/**
	 * Method that allows cursor to skip whitespace
	 */
	private void eatWhitespace() {
		while (curPos < data.length && Character.isWhitespace(data[curPos])) {
			curPos++;
		}
	}


	/**
	 * Method that cleans the program by removing all comments.
	 * 
	 * @param program
	 *            to be cleaned
	 * @returns program without comments
	 */
	private String clearComments(String program) {
		String[] linije = program.split("\n");
		StringBuilder sb = new StringBuilder();

		for (String line : linije) {
			line = line.trim();
			for (Character c : line.toCharArray()) {
				if (c == '#') {
					break;
				}
				sb.append(c);
			}
			sb.append("\n");
		}
		return sb.toString().trim();
	}
}
