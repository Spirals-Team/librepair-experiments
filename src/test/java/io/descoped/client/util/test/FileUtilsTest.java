package io.descoped.client.util.test;

import io.descoped.client.util.CommonUtil;
import io.descoped.client.util.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class FileUtilsTest {

    private final static Logger log = LoggerFactory.getLogger(FileUtilsTest.class);

    @Test
    public void testFileUtilsStaticMethods() throws Exception {
        assertThat(FileUtils.getCurrentPath()).isEqualTo(CommonUtil.getCurrentPath());

        OutputStream out = CommonUtil.newOutputStream();
        out.write("foo\n".getBytes());

        Path testPath = Paths.get("/tmp/test");
        FileUtils.createDirectories(testPath);
        assertThat(testPath).exists();

        Path testPathFile = testPath.resolve("1");
        FileUtils.writeTo(out, testPathFile);
        assertThat(testPathFile).exists();

        out = FileUtils.readFile(testPathFile);
        FileUtils.writeTo(out, testPath, "2");
        assertThat(testPath.resolve("2")).exists();

        out = FileUtils.readFile(testPath, Paths.get("2"));
        assertThat(out.toString()).isEqualTo("foo\n");

        assertThat(FileUtils.isRelativePathFormat("3")).isTrue();
        FileUtils.copy(testPathFile, testPath.resolve("3"));
        assertThat(testPath.resolve("3")).exists();

        FileUtils.move(testPathFile, testPath.resolve("4"));
        assertThat(testPath.resolve("1")).doesNotExist();
        assertThat(testPath.resolve("4")).exists();

        FileUtils.removeFile(testPath.resolve("2"));
        assertThat(testPath.resolve("2")).doesNotExist();

        FileUtils.removeFile(testPath, Paths.get("2"));
        assertThat(testPath.resolve("2")).doesNotExist();

        FileUtils.removeFile(testPath.resolve("4"));
        assertThat(testPath.resolve("4")).doesNotExist();
    }
}
