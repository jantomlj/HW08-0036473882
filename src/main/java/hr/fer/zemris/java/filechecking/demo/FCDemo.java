package hr.fer.zemris.java.filechecking.demo;

import hr.fer.zemris.java.filechecking.execution.FCFileVerifier;
import hr.fer.zemris.java.filechecking.syntax.FCProgramChecker;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Program that demonstrates the functionality of {@link FCProgramChecker} and
 * {@link FCFileVerifier} classes. It compiles and executes a program that is checking whether
 * the content of some zip file matches the expectations
 * 
 * @author Jan Tomljanović
 */
public class FCDemo {

	/**
	 * Main function
	 * 
	 * @param args
	 *            Command line arguments. First argument is the path to the zip archive that
	 *            will be checked, second is the name of the archive and third is the path to
	 *            the program that does the checking
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			throw new IllegalArgumentException("Argumenti komandne linije su redom: staza do zip datoteke, "
					+ "naziv datoteke i staza do datoteke s programom koji testira zip");
		}

		File file = new File(args[0]); // zadaj neku ZIP arhivu
		String program = ucitaj(args[2]); // učitaj program iz datoteke
		String fileName = args[1]; // definiraj stvarno ime arhive
		FCProgramChecker checker = new FCProgramChecker(program);
		if (checker.hasErrors()) {
			System.out.println("Predani program sadrži pogreške!");
			for (String error : checker.errors()) {
				System.out.println(error);
			}
			System.out.println("Odustajem od daljnjeg rada.");
			System.exit(0);
		}
		Map<String, String> initialData = new HashMap<>();
		initialData.put("jmbag", "0012345678");
		initialData.put("lastName", "Perić");
		initialData.put("firstName", "Pero");
		FCFileVerifier verifier = new FCFileVerifier(file, fileName, program, initialData);
		if (!verifier.hasErrors()) {
			System.out.println("Yes! Uspjeh! Ovakav upload bi bio prihvaćen.");
		} else {
			System.out.println("Ups! Ovaj upload se odbija! Što nam još ne valja?");
			for (String error : verifier.errors()) {
				System.out.println(error);
			}
		}
	}

	/**
	 * Method that loads the content of the file into a string and returns it
	 * 
	 * @param fileName
	 *            name of the file whose content is taken
	 * @return The content of the file as a string
	 * @throws IOException
	 */
	private static String ucitaj(String fileName) throws IOException {
		File f = new File(fileName);
		if (f.isDirectory()) {
			throw new IllegalArgumentException("Taken path doesnt point to a directory");
		}
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(f)), StandardCharsets.UTF_8));
		while (true) {
			String chunk = reader.readLine();
			if (chunk == null) {
				reader.close();
				break;
			}
			chunk += "\n";
			sb.append(chunk);
		}
		return sb.toString();
	}
}
