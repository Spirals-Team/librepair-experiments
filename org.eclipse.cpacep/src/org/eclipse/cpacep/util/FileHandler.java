package org.eclipse.cpacep.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileHandler {

	public static File createTempDirectory() throws IOException {
		return Files.createTempDirectory("cpacep").toFile();
	}

	public static ArrayList<String> fileMatcher(String glob, Path location) throws IOException {
		ArrayList<String> matchedList = new ArrayList<String>();
		final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob);

		Files.walkFileTree(location, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
				Path fileName = path.getFileName();
				if (pathMatcher.matches(fileName)) {
					String tempPath = location.relativize(path).toString();

					if (glob.contains(CPACEPConnector.SPEC_FILE_TYPE)) {
						matchedList.add(tempPath.replace(CPACEPConnector.SPEC_FILE_TYPE, ""));
					} else if (glob.contains(CPACEPConnector.CONFIG_FILE_TYPE)) {
						matchedList.add(tempPath.replace(CPACEPConnector.CONFIG_FILE_TYPE, ""));
					}
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}
		});
		Collections.sort(matchedList, String.CASE_INSENSITIVE_ORDER);
		return matchedList;
	}

	public static List<String> readFile(File file) throws IOException {
		return Files.readAllLines(file.toPath());

	}
}