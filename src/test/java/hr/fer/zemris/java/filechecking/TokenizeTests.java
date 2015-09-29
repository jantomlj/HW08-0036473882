package hr.fer.zemris.java.filechecking;

import hr.fer.zemris.java.filechecking.lexical.FileCheckingLexicalException;
import hr.fer.zemris.java.filechecking.lexical.FileCheckingTokenType;
import hr.fer.zemris.java.filechecking.lexical.FileCheckingTokenizer;

import org.junit.Assert;
import org.junit.Test;

public class TokenizeTests {

	
	@Test
	public void eofTest () {
		String program = "";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
		Assert.assertEquals(FileCheckingTokenType.EOF, tokenizer.nextToken().getType());
	}
	
	
	@Test
	public void keywordTest () {
		String program = "\n exists \n";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
		Assert.assertEquals(FileCheckingTokenType.KEYWORD, tokenizer.nextToken().getType());
		tokenizer.oneTokenBack();
		Assert.assertEquals("exists", tokenizer.nextToken().getValue().toString());
		Assert.assertEquals(FileCheckingTokenType.EOF, tokenizer.nextToken().getType());
	}
	
	@Test
	public void argumentTest () {
		String program = "anything";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
		Assert.assertEquals(FileCheckingTokenType.ARGUMENT, tokenizer.nextToken().getType());
		Assert.assertEquals(FileCheckingTokenType.EOF, tokenizer.nextToken().getType());
	}	
	
	
	@Test
	public void StringTest () {
		String program = " \"This is a nice string \"";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
		Assert.assertEquals(FileCheckingTokenType.STRING, tokenizer.nextToken().getType());
		Assert.assertEquals(FileCheckingTokenType.EOF, tokenizer.nextToken().getType());
	}
	
	@Test
	public void openCurlyTest () {
		String program = " {";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
		Assert.assertEquals(FileCheckingTokenType.OPEN_CURLY_BRACKETS, tokenizer.nextToken().getType());
		Assert.assertEquals(FileCheckingTokenType.EOF, tokenizer.nextToken().getType());
	}
	
	
	@Test
	public void closeCurlyTest () {
		String program = " } ";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
		Assert.assertEquals(FileCheckingTokenType.CLOSE_CURLY_BRACKETS, tokenizer.nextToken().getType());
		Assert.assertEquals(FileCheckingTokenType.EOF, tokenizer.nextToken().getType());
	}
	
	@Test
	public void notTest () {
		String program = " ! ";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
		Assert.assertEquals(FileCheckingTokenType.NOT, tokenizer.nextToken().getType());
		Assert.assertEquals(FileCheckingTokenType.EOF, tokenizer.nextToken().getType());
	}
	
	@Test (expected = FileCheckingLexicalException.class)
	public void unclosedStringTest () {
		String program = " \" This is an unclosed string ";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
	}
	
	@Test (expected = FileCheckingLexicalException.class)
	public void emptyStringTest () {
		String program = "\"\"";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
	}
	
	@Test
	public void startgettingTest () {
		String program = " ! terminate ";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
		tokenizer.startGettingTokensFrom(2);
		Assert.assertEquals(FileCheckingTokenType.EOF, tokenizer.nextToken().getType());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nullTokenizerTest () {
		new FileCheckingTokenizer(null);
	}
	
	@Test
	public void toString1Test () {
		String program = "anything";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
		Assert.assertEquals("anything", tokenizer.nextToken().toString());
	}
	
	@Test
	public void toString2Test () {
		String program = " ";
		FileCheckingTokenizer tokenizer = new FileCheckingTokenizer(program);
		tokenizer.tokenize();
		Assert.assertEquals("EOF", tokenizer.nextToken().toString());
	}
	
}
