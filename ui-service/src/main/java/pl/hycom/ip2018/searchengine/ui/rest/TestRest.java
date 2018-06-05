package pl.hycom.ip2018.searchengine.ui.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.ui.rest.inner.TestResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class TestRest {

    @RequestMapping(value = "test", method = GET)
    public int test(@RequestParam("period") String period) {
        if ("7".equals(period)) {
            return 1234;
        } else if ("30".equals(period)) {
            return 12345;
        } else if ("90".equals(period)) {
            return 123456;
        } else {
            return 0;
        }
    }

    @RequestMapping(value = "statistics", method = GET)
    public List<TestResult> statistics(@RequestParam("period") String period) {
        if ("7".equals(period)) {
            return Arrays.asList(
                    new TestResult("tiger", 50, 50f),
                    new TestResult("t-34", 50, 50f));
        } else if ("30".equals(period)) {
            return Arrays.asList(
                    new TestResult("eliza", 33, 33.33f),
                    new TestResult("marie", 33, 33.33f),
                    new TestResult("lindsey", 33, 33.33f));
        } else if ("90".equals(period)) {
            return Arrays.asList(
                    new TestResult("studio", 6, 2.34f),
                    new TestResult("color", 5, 1.95f),
                    new TestResult("portrait", 4, 1.56f),
                    new TestResult("hair", 3, 1.17f),
                    new TestResult("leaves", 3, 1.17f),
                    new TestResult("night", 3, 1.17f),
                    new TestResult("weeding", 3, 1.17f),
                    new TestResult("young", 3, 1.17f),
                    new TestResult("aerial view", 2, 0.78f));
        } else {
            return null;
        }
    }

    @RequestMapping(value = "historyMock", method = GET)
    public List<String> history() {
        return Stream.of("The Celtic Holocaust", "The Destroyer of Worlds", "Blueprint for Armageddon",
                "Painfotainment", "A Basic Look At Post-Modernism", "Structuralism and Mythology",
                "The Frankfurt School", "Schopenhauer", "Henry David Thoreau's views on the individual, society and civil disobedience"
        ).collect(Collectors.toList());
    }
}
