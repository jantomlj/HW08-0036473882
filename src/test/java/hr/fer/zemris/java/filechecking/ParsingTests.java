package hr.fer.zemris.java.filechecking;

import hr.fer.zemris.java.filechecking.syntax.FCProgramChecker;
import hr.fer.zemris.java.filechecking.syntax.nodes.DefNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.ExistsNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FailNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FilenameNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FormatNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;

import org.junit.Assert;
import org.junit.Test;

public class ParsingTests {

	@Test
	public void defNodeTest () {
		FCProgramChecker ch = new FCProgramChecker("def name \"string\"");
		Assert.assertFalse(ch.hasErrors());
		ProgramNode pn = ch.getProgramNode();
		Assert.assertEquals("Samo jedna naredba", 1, pn.getCommands().size());
		Assert.assertTrue(pn.getCommands().get(0) instanceof DefNode);
		DefNode dn = (DefNode) pn.getCommands().get(0);
		Assert.assertEquals("name", dn.getVarName());
		Assert.assertEquals("\"string\"", dn.getPath());
	}
	
	@Test
	public void existsNodeTest () {
		FCProgramChecker ch = new FCProgramChecker("exists f \"string\" @\"message\" { def name \"string\" }");
		Assert.assertFalse(ch.hasErrors());
		ProgramNode pn = ch.getProgramNode();
		Assert.assertEquals("Samo jedna naredba", 1, pn.getCommands().size());
		Assert.assertTrue(pn.getCommands().get(0) instanceof ExistsNode);
		ExistsNode dn = (ExistsNode) pn.getCommands().get(0);
		Assert.assertEquals(false, dn.isDirectory());
		Assert.assertEquals("\"string\"", dn.getPath());
		Assert.assertEquals("@\"message\"", dn.getMessage());
		Assert.assertEquals(1, dn.getCommands().size());
	}
	
	
	
	@Test
	public void failNodeTest () {
		FCProgramChecker ch = new FCProgramChecker("fail @\"message\"");
		Assert.assertFalse(ch.hasErrors());
		ProgramNode pn = ch.getProgramNode();
		Assert.assertEquals("Samo jedna naredba", 1, pn.getCommands().size());
		Assert.assertTrue(pn.getCommands().get(0) instanceof FailNode);
		FailNode dn = (FailNode) pn.getCommands().get(0);
		Assert.assertEquals("@\"message\"", dn.getMessage());
		Assert.assertEquals(null, dn.getCommands());
	}
	
	
	@Test
	public void filenameNodeTest () {
		FCProgramChecker ch = new FCProgramChecker("filename \"string\" @\"message\" { def name \"string\" }");
		Assert.assertFalse(ch.hasErrors());
		ProgramNode pn = ch.getProgramNode();
		Assert.assertEquals("Samo jedna naredba", 1, pn.getCommands().size());
		Assert.assertTrue(pn.getCommands().get(0) instanceof FilenameNode);
		FilenameNode dn = (FilenameNode) pn.getCommands().get(0);
		Assert.assertEquals("\"string\"", dn.getPath());
		Assert.assertEquals("@\"message\"", dn.getMessage());
		Assert.assertEquals(1, dn.getCommands().size());
	}
	
	
	
	@Test
	public void formatNodeTest () {
		FCProgramChecker ch = new FCProgramChecker("format zip { def name \"string\" }");
		Assert.assertFalse(ch.hasErrors());
		ProgramNode pn = ch.getProgramNode();
		Assert.assertEquals("Samo jedna naredba", 1, pn.getCommands().size());
		Assert.assertTrue(pn.getCommands().get(0) instanceof FormatNode);
		FormatNode dn = (FormatNode) pn.getCommands().get(0);
		Assert.assertEquals("zip", dn.getFormat());
		Assert.assertEquals(null, dn.getMessage());
		Assert.assertEquals(1, dn.getCommands().size());
	}
	
	@Test
	public void errorOnArgumentTest () {
		FCProgramChecker ch = new FCProgramChecker("exists fe \"string\" @\"message\" { def name \"string\" }");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test
	public void errorsOnKeywordTest () {
		FCProgramChecker ch = new FCProgramChecker("exissts f \"string\" @\"message\" { def name \"string\" }");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test
	public void errorOnStringTest () {
		FCProgramChecker ch = new FCProgramChecker("exists f \"str}ing\" @\"message\" { def name \"string\" }");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	@Test
	public void errorOnString2Test () {
		FCProgramChecker ch = new FCProgramChecker("exists f \"str{ing\" @\"message\" { def name \"string\" }");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test
	public void errorOnString3Test () {
		FCProgramChecker ch = new FCProgramChecker("exists f \"str$ing\" @\"message\" { def name \"string\" }");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test
	public void errorOnString4Test () {
		FCProgramChecker ch = new FCProgramChecker("exists f \"str\\ning\" @\"message\" { def name \"string\" }");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test
	public void errorOnBrackets1Test () {
		FCProgramChecker ch = new FCProgramChecker("exists f \"string\" @\"message\" { def name \"string\"");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test
	public void errorOnBrackets2Test () {
		FCProgramChecker ch = new FCProgramChecker("exists f \"string\" @\"message\" } def name \"string\"");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test
	public void errorOnPathTest () {
		FCProgramChecker ch = new FCProgramChecker("exists f \"string\" @\"message\" def name path");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test
	public void errorOnArgument2Test () {
		FCProgramChecker ch = new FCProgramChecker("exists f \"string\" @\"message\" def \"name\" path");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test
	public void errorOnFilenameArgumentTest () {
		FCProgramChecker ch = new FCProgramChecker("filename ama");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}

	@Test
	public void errorOnFormatArgumentTest () {
		FCProgramChecker ch = new FCProgramChecker("format \"ama\"");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test
	public void illegalVariableNameTest () {
		FCProgramChecker ch = new FCProgramChecker("exists dir \"${ variable name}\" ");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test
	public void illegalNotTest () {
		FCProgramChecker ch = new FCProgramChecker(" !def a \"a\" ");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(1, ch.errors().size());
	}
	
	@Test (expected = IllegalStateException.class)
	public void invalidProgramNodeTest () {
		FCProgramChecker ch = new FCProgramChecker(" !def a \"a\" ");
		ch.getProgramNode();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nullProgramTest () {
		new FCProgramChecker(null);
	}
	
	@Test
	public void lotsOfErrorsTest () {
		FCProgramChecker ch = new FCProgramChecker("exists \"hm\2 \"string\" @\"message\" def \"name\" path "
				+ "fail \"o no\"");
		Assert.assertTrue(ch.hasErrors());
		Assert.assertEquals(3, ch.errors().size());
	}	
}
