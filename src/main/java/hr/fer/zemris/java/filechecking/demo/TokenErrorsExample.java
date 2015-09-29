package hr.fer.zemris.java.filechecking.demo;

import hr.fer.zemris.java.filechecking.syntax.FCProgramChecker;

import java.util.HashMap;
import java.util.Map;

/**Example that uses the zip archive in the project directroy.
 */
public class TokenErrorsExample {

	public static void main(String[] args) {
		String program =
				  "def src \"\" \n"
				+ "exists di \"${src}\" @\"Src doesn't exist\" {\n"
				+ "format zip\n"
				+ "exists f \"${var}/build.xml\"\n"
				+ "}\n"
				+ "terminate\n"
				+ "fail @\"This will never happen ";
		
		Map<String, String> initialData = new HashMap<>();
		initialData.put("var", "homework04");
		
		FCProgramChecker checker = new FCProgramChecker(program);
		System.out.println("Has errors: " + checker.hasErrors());
		for (String err : checker.errors()) {
			System.out.println(err);
		}
	}
}
