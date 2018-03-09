package ru.aserdyuchenko;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

/**
 * Class InputOutput.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class FiveLetters {
/**
 * @param result - result.
 */
	private boolean result = false;
/**
 * convertStreamToInt method.
 * @param in - Scanner.
 * @throws IOException - IOException.
 * @return result - result checking.
 */
	public boolean checkWord(InputStream in) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			try {
				char[] array = (IOUtils.toString(reader)).toCharArray();
				for (int x = array.length - 1; x >= 0; x--) {
					for (int y = 0; y <= array.length - 1; y++) {
						if (Character.toUpperCase(array[x]) == Character.toUpperCase(array[y])) {
							result = true;
						} else {
							result = false;
						}
						x--;
					}
				}
			} catch (NumberFormatException e) {
        		result = false;
    		}
		}
		return result;
	}

/**
 * @param value - word for checking.
 * @return result - result checking.
 */
	public boolean isPalidrom(String value) {
		boolean result = false;
		char[] array = value.toCharArray();
		for (int index = 0; index < (array.length - 1); index++) {
			if (Character.toUpperCase(array[index]) == Character.toUpperCase(array[array.length - index - 1])) {
				result = true;
			} else {
				result = false;
				break;
			}
		}
		return result;
	}
}
