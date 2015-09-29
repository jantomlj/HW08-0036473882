package hr.fer.zemris.java.filechecking;

import hr.fer.zemris.java.filechecking.execution.FCFileVerifier;
import hr.fer.zemris.java.filechecking.syntax.FCProgramChecker;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class BigTests {

	@Test
	public void bigNoErrorOne () {
		String program =
				  "def src \"${var}/src\" \n"
				+ "exists di \"${src}\" @\"Src doesn't exist\" {\n"
				+ "format zip\n"
				+ "exists f \"${var}/build.xml\"\n"
				+ "}\n"
				+ "terminate\n"
				+ "fail @\"This will never happen\" ";
		
		Map<String, String> initialData = new HashMap<>();
		initialData.put("var", "homework04");
		
		FCProgramChecker checker = new FCProgramChecker(program);
		FCFileVerifier verifier = new FCFileVerifier(new File("0012345678-dz1.zip"),
				"0012345678-dz1.zip", program, initialData);
		
		Assert.assertFalse(checker.hasErrors());
		Assert.assertFalse(verifier.hasErrors());
	}
	
	@Test
	public void bigNoErrorTwo () {
		String program =
				  "def src \"${var}/src\" \n"
				+ "exists di \"${src}\" @\"Src doesn't exist\" {\n"
				+ "!fail\n"
				+ "!exists dir \"${var}/build.xml\"\n"
				+ "exists dir \"${src}/main/java:hr.fer.zemris.java.tecaj.hw5.crypto\""
				+ "}\n"
				+ "terminate\n"
				+ "fail @\"This will never happen\" ";
		
		Map<String, String> initialData = new HashMap<>();
		initialData.put("var", "homework04");
		
		
		FCProgramChecker checker = new FCProgramChecker(program);
		Assert.assertFalse(checker.hasErrors());
		FCFileVerifier verifier = new FCFileVerifier(new File("0012345678-dz1.zip"),
				"0012345678-dz1.zip", program, initialData);
		Assert.assertFalse(verifier.hasErrors());
	}
	
	@Test
	public void bigTokenErrorOne () {
		String program =
				  "def src \"\" \n"
				+ "exists di \"${src}\" @\"Src doesn't exist\" {\n"
				+ "format zip\n"
				+ "exists f \"${var}/build.xml\"\n"
				+ "}\n"
				+ "terminate\n"
				+ "fail @\"This will never happen\" ";
		
		Map<String, String> initialData = new HashMap<>();
		initialData.put("var", "homework04");
		
		FCProgramChecker checker = new FCProgramChecker(program);
		Assert.assertTrue(checker.hasErrors());
		Assert.assertEquals(1, checker.errors().size());
	}
	
	@Test
	public void bigSyntaxErrorsOne () {
		String program =
				  "def 1src \"${var}/src\" \n"
				+ "exists di \"${src}\" @\"Src doesn't exist\" {\n"
				+ "forrmat zip\n"
				+ "exists f \"${v ar}/build.xml\"\n"
				+ "}\n"
				+ "terminate\n"
				+ "fail @\"Th}is will never happen\" ";
		
		Map<String, String> initialData = new HashMap<>();
		initialData.put("var", "homework04");
		
		FCProgramChecker checker = new FCProgramChecker(program);
		Assert.assertTrue(checker.hasErrors());
		Assert.assertEquals(4, checker.errors().size());
	}

	
	@Test
	public void bigSyntaxErrorsTwo () {
		String program =
				  "def _src \"${var}/src\" \n"
				+ "exists di \"${src}\" @\"Src doesn't exist\" {\n"
				+ "format zip\n"
				+ "!!exists f \"${var}/build.xml\"\n"
				+ "}\n"
				+ ""
				+ "fail @\"This will happen\" ";
		
		Map<String, String> initialData = new HashMap<>();
		initialData.put("var", "homework04");
		
		FCProgramChecker checker = new FCProgramChecker(program);
		Assert.assertTrue(checker.hasErrors());
		Assert.assertEquals(1, checker.errors().size());
	}
}
