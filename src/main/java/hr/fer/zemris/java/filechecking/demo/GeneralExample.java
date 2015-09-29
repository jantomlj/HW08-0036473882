package hr.fer.zemris.java.filechecking.demo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.filechecking.execution.FCFileVerifier;
import hr.fer.zemris.java.filechecking.syntax.FCProgramChecker;

/**Example that uses the zip archive in the project directroy.
 */
public class GeneralExample {

	public static void main(String[] args) {
		String program =
				  "def src \"${var}/src\" \n"
				+ "def bil \"build.xml\""
				+ "exists di \"${src}\" @\"Src doesn't exist\" {\n"
				+ "format zip\n"
				+ "exists f \"${var}/${bil}\"\n"
				+ "}\n"
				+ "terminate\n"
				+ "fail @\"This will never happen\" ";
		
		Map<String, String> initialData = new HashMap<>();
		initialData.put("var", "homework04");
		
		FCProgramChecker checker = new FCProgramChecker(program);
		System.out.println("Has errors: " + checker.hasErrors());
		FCFileVerifier verifier = new FCFileVerifier(new File("0012345678-dz1.zip"),
				"0012345678-dz1.zip", program, initialData);
		System.out.println("Has errors: " + verifier.hasErrors());
	}
}
