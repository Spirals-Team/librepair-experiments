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
public class EvenNumber {
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
	public boolean readInputStreamAsInt(InputStream in) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			try {
				if (Integer.valueOf(IOUtils.toString(reader)) % 2 == 0) {
					result = true;
				} else {
					result = false;
				}
			} catch (NumberFormatException e) {
        		result = false;
    		}
		}
		return result;
	}
}
