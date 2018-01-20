package staticanalysis;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileHelpers {
    public static final LoadingCache<Path, List<Path>> LISTING_CACHE = CacheBuilder.<String, String>newBuilder()
            .build(new CacheLoader<Path, List<Path>>() {
                @Override
                public List<Path> load(@Nonnull Path directory) throws Exception {
                    return listDirectoryInternal(directory);
                }
            });

    public static List<Path> listDirectory(Path directory) {
        return LISTING_CACHE.getUnchecked(directory);
    }

    public static List<Path> listDirectoryInternal(Path directory) throws IOException {
        List<Path> files = new ArrayList<Path>();
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes fileAttributes) throws IOException {
                if (!fileAttributes.isDirectory()) {
                    files.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return files;
    }
}