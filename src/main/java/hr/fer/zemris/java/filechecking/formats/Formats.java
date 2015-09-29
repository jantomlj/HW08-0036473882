package hr.fer.zemris.java.filechecking.formats;

import java.util.HashMap;
import java.util.Map;

/**
 * Class containg static methods that work with file formats.
 * 
 * @author Jan Tomljanović
 */
public class Formats {

	/** Map that contains extensions for formats */
	private static Map<String, String> extensions = new HashMap<>();

	private Formats() {
	}

	// all lowercase format names
	// format, extension
	static {
		extensions.put("zip", "zip");
		extensions.put("rar", "rar");
	}

	/**
	 * Method that checkes whether the given path represents a file in the given format
	 * 
	 * @param path
	 *            string that represent a file path
	 * @param format
	 *            file format
	 * @return true if the file represented by the path is in the given format, false otherwise
	 * @throws UnsupportedOperationException
	 *             if the format is unknown
	 */
	public static boolean matches(String path, String format) {
		if (path == null || format == null) {
			throw new IllegalArgumentException();
		}
		String ext = extensions.get(format.toLowerCase());
		if (ext == null) {
			throw new UnsupportedOperationException("Dont know what is the extension for " + format);
		}
		int index = path.lastIndexOf('.');
		return path.substring(index + 1).equals(ext);
	}

}
