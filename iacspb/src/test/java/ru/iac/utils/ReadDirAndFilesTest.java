package ru.iac.utils;

import org.junit.Test;
import ru.iac.domain.PathTable;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ReadDirAndFilesTest {
    ReadDirAndFiles disk = new ReadDirAndFiles();
    File file = new File(getClass().getClassLoader().getResource("templates").getFile());
    String root = file.getAbsolutePath();

    @Test
    public void whetTestIsDir() {
        assertTrue(disk.isDir(root));
        assertFalse(disk.isDir("Test"));
    }

    @Test
    public void getAll() {
        assertThat(disk.getAll(new PathTable(root)).get(0).getName(), is("main.html"));
        assertThat(disk.getAll(new PathTable(root)).get(1).getName(), is("TestDir"));
    }
}