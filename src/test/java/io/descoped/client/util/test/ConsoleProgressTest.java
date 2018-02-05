package io.descoped.client.util.test;

import io.descoped.client.util.CommonUtil;
import io.descoped.client.util.ConsoleProgress;
import io.descoped.client.util.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ConsoleProgressTest {

    @Test
    public void testConsoleProgress() throws Exception {
        Path tempPath = Paths.get("/tmp/test2");
        String fileName = UUID.randomUUID().toString();
        Path tempPathFile = tempPath.resolve(fileName);
        FileUtils.createDirectories(tempPath);
        assertThat(tempPathFile.toFile().createNewFile()).isTrue();
        File file = tempPathFile.toFile();
        Thread thread = ConsoleProgress.consoleProgressThread(file, "foobar".length());
        OutputStream out = CommonUtil.newOutputStream();
        out.write("foobar".getBytes());
        FileUtils.writeTo(out, tempPathFile);
        Thread.sleep(150);
        ConsoleProgress.interruptProgress(thread);
        assertThat(file.exists()).isTrue();
        FileUtils.removeFile(tempPathFile);
        assertThat(tempPathFile).doesNotExist();
    }

}
