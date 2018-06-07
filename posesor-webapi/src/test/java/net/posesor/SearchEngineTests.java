package net.posesor;

import lombok.val;
import net.posesor.SearchEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public final class SearchEngineTests {

    @Test
    public void shouldNotThrowAnExceptionWhenContentContainsEmptyData() {
        SearchEngine.filterToSearchDtoModel(Stream.of(""), it -> it, "some hint");
    }

    @Test
    public void AccountNameHintContains() {
        val source = Stream.of("ABC osiedle");
        val result = SearchEngine
                .filterToSearchDtoModel(source, it -> it, "ABC")
                .collect(Collectors.toList());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo("ABC osiedle");
    }

    @Test
    public void LowerAndUpperCases() {
        val source = Stream.of("Lutomierska 99", "Nie lubie Zgierzaków");
        val result = SearchEngine
                .filterToSearchDtoModel(source, it -> it, "lu")
                .collect(Collectors.toList());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo("Lutomierska 99");
        assertThat(result.get(1)).isEqualTo("Nie lubie Zgierzaków");
    }

    @Test
    public void OrderCheck() {
        val source = Stream.of(
                "Zgierz Piotrkowska 33/6",
                "Piotrkowska 53/5",
                "Skargi Piotra 3/5",
                "Albania Piotrkowska");

        val result = SearchEngine.filterToSearchDtoModel(source, it -> it, "Piotr");

        assertThat(result.count()).isEqualTo(4);
    }

    @Test
    public void EmptyList() {
        val source = Stream.<String>empty();
        val result = SearchEngine.filterToSearchDtoModel(source, it -> it, "Pierwszy Wpis");

        assertThat(result.count()).isEqualTo(0L);
    }
}
