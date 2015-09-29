package hr.fer.zemris.java.filechecking.demo;

import hr.fer.zemris.java.filechecking.syntax.FCProgramChecker;

import java.util.HashMap;
import java.util.Map;

/**Example that uses the zip archive in the project directroy.
 */
public class SyntaxErrorsExample {

	public static void main(String[] args) {
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
		System.out.println("Has errors: " + checker.hasErrors());
		for (String err : checker.errors()) {
			System.out.println(err);
		}
		
	}
}
