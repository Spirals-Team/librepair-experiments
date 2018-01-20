package staticanalysis;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static staticanalysis.FileHelpers.listDirectory;

//TODO use to create other static anaylsis tests and study
public class SQLConventionsTest {

    private static final Pattern SELECT_STAR_PATTERN = Pattern.compile(".*SELECT.*\\*.*");

    @Test
    public void neverUseSelectStar() throws IOException {
        assertThat(javaFilesThatUseSelectStar())
                .describedAs("The following files used 'SELECT *'")
                .isEmpty();
    }

    private List<Path> javaFilesThatUseSelectStar() {
        return listDirectory(Paths.get("./src/main/java"))
                    .stream()
                    .filter(this::isJavaFile)
                    .filter(this::selectStarIsUsed)
                    .collect(toList());
    }

    private boolean selectStarIsUsed(Path file) {
        return readAllLines(file).anyMatch(this::selectStarIsUsed);
    }

    private boolean selectStarIsUsed(String line) {
        return SELECT_STAR_PATTERN.matcher(line).matches();
    }

    private static Stream<String> readAllLines(Path file) {
        try {
            return Files.readAllLines(file).stream();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean isJavaFile(Path file) {
        return file.toString().endsWith("java");
    }
}
