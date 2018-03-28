package ru.job4j.waitNotify;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class DirectoryInspector {
    public void walkFileTree() {
        Path startPath = Paths.get("\\FilePath\\");
        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String firstLine = Files.newBufferedReader(file, Charset.defaultCharset()).readLine();
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}