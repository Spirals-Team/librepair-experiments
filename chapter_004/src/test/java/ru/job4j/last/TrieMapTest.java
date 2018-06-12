package ru.job4j.last;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class TrieMapTest {
	private String path = Objects.requireNonNull(getClass()
			.getClassLoader()
			.getResource("test.txt"))
			.getPath();
	private WordIndex index;

	@Before
	public void setUp() throws FileNotFoundException {
		index = new WordIndex();
		index.loadFile(path);
	}

	//Текст в файлу в строку
	private String convertFileToString() throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(path));
		byte[] bytes = new byte[dis.available()];
		dis.readFully(bytes);
		return new String(bytes, 0, bytes.length);
	}

	//получаем все вхождения subStr в тексте text с помощью метода indexOf()
	private Set<Integer> getAllSubString(String subStr) throws IOException {
		String text = convertFileToString();
		Set<Integer> result = new TreeSet<>();
		int lastIndex = 0;
		while (lastIndex != -1) {
			lastIndex = text.indexOf(subStr, lastIndex);
			if (lastIndex != -1) {
				result.add(lastIndex);
				lastIndex++;
			}
		}
		if (result.size() == 0) {
			result = null;
		}
		return result;
	}

	@Test
	public void whenSearchForWordWhichConsistsOfOneLatterAndExistsInLoadedText() throws IOException {
		String search = "a";
		assertEquals(index.getIndexes4Word(search), getAllSubString(search));
	}

	@Test
	public void whenSearchForWordWhichConsistsOfOneLatterAndDoesNotExistInLoadedText() {
		assertNull(index.getIndexes4Word("z"));
	}

	@Test
	public void whenSearchForWordWhichExistsInLoadedTextThenReturnsSetOfPositionThisWordInTheText() throws IOException {
		String search = "washes";
		assertEquals(index.getIndexes4Word(search), getAllSubString(search));
	}

	@Test
	public void whenSearchForWordWhichDoesNotExistInLoadedText() {
		assertNull(index.getIndexes4Word("Zoo"));
	}
}
