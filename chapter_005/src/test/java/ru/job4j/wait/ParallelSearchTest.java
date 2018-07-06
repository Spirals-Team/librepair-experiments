package ru.job4j.wait;

import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class ParallelSearchTest {
	private String path = Paths.get(
			"src", "test", "resources").toString();

	@Test
	public void test() throws InterruptedException {
		ParallelSearch search = new ParallelSearch(
				path, "yes", new String[] {".txt", ".java"});
		search.init();
		search.getRead().join();
		String[] expected = {
				"src/test/resources/folder1/yes.java",
				"src/test/resources/folder1/yes.txt",
				"src/test/resources/folder2/folder3/yes.txt",
				"src/test/resources/folder2/yes.txt",
		};
		assertEquals(Arrays.asList(expected), search.result());
	}
}