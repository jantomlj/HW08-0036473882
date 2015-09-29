package hr.fer.zemris.java.filechecking;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.filechecking.execution.FCFileVerifier;
import hr.fer.zemris.java.filechecking.execution.FileCheckingExecutionException;

public class VerifyTests {

	private static final File file = new File("0012345678-dz1.zip");
	private static final String fileName = "0012345678-dz1.zip";
	
	
	@Test
	public void notExistTest () {
		String program = "exists dir \"homework04/exit\" ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	@Test
	public void notExists2Test () {
		String program = "exists file \"homework04/build.apache\" ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	@Test
	public void notExist3Test () {
		String program = "exists dir \"homework04/build.xml\" ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	@Test
	public void notExist4Test () {
		String program = "!exists fi \"homework04/build.xml\" ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	@Test
	public void notExist5Test () {
		String program = "!exists fi \"homework04/build.xml\" @\"This is a message\" ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	@Test
	public void wrongFilenameTest () {
		String program = "filename i\"00123456789-dz1.zip\" @\"This is a message\" {} ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	
	@Test
	public void wrongFilenameCaseSensitiveTest () {
		String program = "filename \"0012345678-Dz1.zip\" @\"This is a message\" {} ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	@Test
	public void wrongFilenameCaseSensitive2Test () {
		String program = "!filename \"0012345678-dz1.zip\" @\"This is a message\" {} ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	@Test
	public void wrongFilenameCaseSensitive3Test () {
		String program = "!filename \"0012345678-dz1.zip\" ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	@Test
	public void wrongFormatTest () {
		String program = "format rar ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	@Test
	public void wrongFormat2Test () {
		String program = "!format zip ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	
	@Test
	public void failedTest () {
		String program = "fail ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	@Test
	public void failed2Test () {
		String program = "!fail { !fail { !fail { fail @\"I just have failed\" } } } ";
		FCFileVerifier v = new FCFileVerifier(file, fileName, program, null);
		Assert.assertTrue(v.hasErrors());
		Assert.assertEquals(1, v.errors().size());
	}
	
	@Test (expected = FileCheckingExecutionException.class)
	public void runtimeExVariableTest () {
		String program = "def var \"${variable}\" ";
		new FCFileVerifier(file, fileName, program, null);
	}

	@Test (expected = FileCheckingExecutionException.class)
	public void runtimeExFormatTest () {
		String program = "format dad ";
		new FCFileVerifier(file, fileName, program, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void runtimePathTest () {
		String program = "format dad ";
		new FCFileVerifier(new File("./src"), fileName, program, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nullVerifyTest () {
		new FCFileVerifier(file, null, null, null);
	}

}
