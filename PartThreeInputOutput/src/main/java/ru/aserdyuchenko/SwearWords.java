package ru.aserdyuchenko;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class InputOutput.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version 1
 * @since 15.12.2016
 */
public class SwearWords {
/**
 * dropAbuses method.
 * @param in - InputStream.
 * @param out - OutputStream.
 * @param abuse - String[].
 * @throws IOException - IOException.
 */
	public void dropAbuses(InputStream in, OutputStream out, String[] abuse) throws IOException {
		try (Scanner reader = new Scanner(new BufferedReader(new InputStreamReader(in)))) {
			while (reader.hasNext()) {
				String word = reader.next();
				if (checkWord(word, abuse)) {
					out.write(word.getBytes());
				}
			}
		}
	}
/**
 * checkWord method.
 * @param wordForCheck - String.
 * @param abuseForCheck - String[].
 * @return result - result.
 */
	public boolean checkWord(String wordForCheck, String[] abuseForCheck) {
		boolean result = true;
		for (String swearWord : abuseForCheck) {
			if (wordForCheck.equals(swearWord)) {
				result = false;
			}
		}
		return result;
	}
}
