package ru.job4j.last;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class WordIndex {
	private TrieMap indexes = new TrieMap();

	public void loadFile(String filename) throws FileNotFoundException {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));
			int count = 0;
			while (scanner.hasNext()) {
				String cur = scanner.next().split("\\W")[0].toLowerCase();
				indexes.insert(cur, count);
				count += cur.length() + 1;
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	public Set<Integer> getIndexes4Word(String searchWord) {
		return indexes.getPosition(searchWord);
	}
}
