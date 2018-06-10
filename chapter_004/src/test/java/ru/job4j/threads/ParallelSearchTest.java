package ru.job4j.threads;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParallelSearchTest {

    @Test
    public void whenSearceFilesWithWordJavaThenReturnTwoFiles() {
        List<String> list = new ArrayList<>();
        list.add(".txt");
        ParallelSearch parallelSearch = new ParallelSearch("C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\home", "java", list);
        Assert.assertThat(parallelSearch.result().get(0), is("C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\home\\aum\\myjobs\\234.txt"));
        Assert.assertThat(parallelSearch.result().get(1), is("C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\home\\aum\\myjobs\\new\\345.txt"));
    }

}