package ru.job4j.threads;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class InterruptedRunnerTest {

    @Test
    public void countSymbolsInThreads() {
        List<String> regexps = Arrays.asList("\\s", "\\w+");
        InterruptedRunner runner = new InterruptedRunner(regexps);
        runner.runWithWait(text, 10L);
    }

    private String text = "Distinctio incidunt error voluptas quam nam pariatur"
        + " veritatis perspiciatis. Consequatur recusandae esse maiores error"
        + " natus. Aut impedit aspernatur necessitatibus necessitatibus iste."
        + " Saepe blanditiis temporibus cupiditate assumenda et sequi a rem."
        + " Sed et eius corrupti esse sint recusandae mollitia.";
}