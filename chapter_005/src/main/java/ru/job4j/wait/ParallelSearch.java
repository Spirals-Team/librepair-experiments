package ru.job4j.wait;

import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
@ThreadSafe
public class ParallelSearch {
	private volatile boolean finish = false;
	private final String root;
	private final String text;
	private final List<String> exts;
	private final Queue<String> files = new ConcurrentLinkedQueue<>();
	private final List<String> paths = new CopyOnWriteArrayList<>();

	private Thread search = new Thread() {
		@Override
		public void run() {
			try {
				Files.walkFileTree(Paths.get(root), new FileTree(files));
			} catch (IOException e) {
				// log error
			} finally {
				finish = true;
			}
		}
	};

	private Thread read = new Thread() {
		@Override
		public void run() {
			while (!finish || !files.isEmpty()) {
				String path = files.poll();
				if (path != null) {
					try (BufferedReader reader = new BufferedReader(
							new InputStreamReader(
									new FileInputStream(path)))) {
						String line;
						while ((line = reader.readLine()) != null) {
							if (line.contains(text)) {
								paths.add(path);
								break;
							}
						}
					} catch (IOException e) {
						// log error
					}
				}
			}
		}
	};

	public ParallelSearch(String root, String text, String[] exts) {
		this.root = root;
		this.text = text;
		this.exts = Arrays.asList(exts);
	}

	public Thread getRead() {
		return read;
	}

	public void init() {
		search.start();
		read.start();
	}

	public List<String> result() {
		List<String> list = new ArrayList<>();
		for (String item : paths) {
			list.add(item.replace(File.separatorChar, '/'));
		}
		return list;
	}

	private class FileTree extends SimpleFileVisitor<Path> {
		private final Queue<String> files;

		FileTree(Queue<String> files) {
			this.files = files;
		}

		@Override
		public FileVisitResult visitFile(Path file,
										 BasicFileAttributes attrs) {
			String fileName = file.toFile().getPath();
			boolean correctType = false;
			for (String item : exts) {
				if (fileName.endsWith(item)) {
					correctType = true;
					break;
				}
			}
			if (correctType) {
				files.offer(fileName);
			}
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file,
											   IOException exc) {
			//log error
			return CONTINUE;
		}
	}
}
