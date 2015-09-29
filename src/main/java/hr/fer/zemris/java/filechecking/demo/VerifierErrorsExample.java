package hr.fer.zemris.java.filechecking.demo;

import hr.fer.zemris.java.filechecking.execution.FCFileVerifier;
import hr.fer.zemris.java.filechecking.syntax.FCProgramChecker;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**Example that uses the zip archive in the project directroy.
 */
public class VerifierErrorsExample {

	public static void main(String[] args) {
		String program =
				  "def src \"${var}/src\" \n"
				+ "exists di \"${src}\" @\"Src doesn't exist\" {\n"
				+ "exists d \"${src}/main/javaaaa\"\n"
				+ "exists f \"${var}/buil.xml\"\n @\"File buil.xml is not present in the archive\" "
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
		for (String err : verifier.errors()) {
			System.out.println(err);
		}
	}
}
