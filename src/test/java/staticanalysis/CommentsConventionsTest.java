package staticanalysis;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static staticanalysis.FileHelpers.listDirectory;

// TODO use to create other static anaylsis tests and study
public class CommentsConventionsTest {

    private static final Pattern COMMENTS_PATTERN = Pattern.compile("// TODO");

    @Test
    @Ignore
    public void neverUseSelectStar() throws IOException {
        assertThat(javaFilesThatUseSelectStar())
                .describedAs("The following files used '// TODO'")
                .isEmpty();
    }

    private List<Path> javaFilesThatUseSelectStar() {
        List<Path> collect = listDirectory(Paths.get("./src/main/java"))
                .stream()
                .filter(this::isJavaFile)
                .filter(this::selectCommentIsUsed)
                .collect(toList());
        System.out.println("collect = " + collect);
        return collect;
    }


    private boolean selectCommentIsUsed(Path file) {
        // TODO match any line that has a // TODO
        Stream<String> stringStream = readAllLines(file);
        Stream<String> stringStream1 = stringStream.filter(x -> x.contains("//") && !x.contains("// TODO"));
        stringStream1.forEach(System.out::println);
//        System.out.println("s = " + s);
        boolean b = readAllLines(file).anyMatch(line -> COMMENTS_PATTERN.matcher(line).matches());
        System.out.println("b = " + b);
        return b;
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
