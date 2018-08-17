package ru.job4j.wait;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class ParallelSearchTest {
    private String text;
    private String root = "FolderForTests";
    private List<String> ext = Arrays.asList("txt", "doc", "docx");

    /**
     * Method for testing size.
     * @throws InterruptedException exception
     */
    @Test
    public void whenFoundFourMatchesInFilesThenAddedFourFilesInList() throws InterruptedException {
        text = "Hello";
        ParallelSearch ps = new ParallelSearch(root, text, ext);
        ps.init();
        int result = 2;
        System.out.println(ps.result().size());
        assertThat(result, is(ps.result().size()));
    }
}
