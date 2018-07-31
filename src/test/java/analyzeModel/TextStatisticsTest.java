package analyzeModel;

import org.junit.Before;
import org.junit.Test;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.*;

public class TextStatisticsTest {

    private int expectedTotalChar;
    private int expectedTotalCharWithoutSpaces;
    private TreeMap<String, Integer> expectedNumberOfWords;
    private TreeMap<Character, Integer> expectedNumberOfCharacters;
    private TextStatistics textStatistics;

    @Before
    public void initialize() {

        expectedTotalChar =  2;
        expectedTotalCharWithoutSpaces =  3;
        expectedNumberOfWords =  new TreeMap<>();
        expectedNumberOfWords.put("слово", 4);
        expectedNumberOfCharacters =  new TreeMap<>();
        expectedNumberOfCharacters.put('а', 1);
        expectedNumberOfCharacters.put('б', 3);
        expectedNumberOfCharacters.put('в', 1);

        textStatistics = new TextStatistics(expectedTotalChar, expectedTotalCharWithoutSpaces,
                                                                 expectedNumberOfWords, expectedNumberOfCharacters);
    }


    @Test
    public void getTotalChar() {
        assertThat(textStatistics.getTotalChar()).isEqualTo(expectedTotalChar);
    }

    @Test
    public void getTotalCharWithoutSpaces() {
        assertThat(textStatistics.getTotalCharWithoutSpaces()).isEqualTo(expectedTotalCharWithoutSpaces);
    }

    @Test
    public void getNumberOfCharacters() {
        assertThat(textStatistics.getNumberOfCharacters()).isEqualTo(expectedNumberOfCharacters);
    }

    @Test
    public void getNumberOfWords() {
        assertThat(textStatistics.getNumberOfWords()).isEqualTo(expectedNumberOfWords);
    }
}