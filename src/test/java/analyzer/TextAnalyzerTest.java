package analyzer;

import analyzeModel.TextStatistics;
import org.junit.Test;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.*;

public class TextAnalyzerTest {

    private int expectedTotalChar;
    private int expectedTotalCharWithoutSpaces;
    private TreeMap<String, Integer> expectedNumberOfWords;
    private TreeMap<Character, Integer> expectedNumberOfCharacters;
    private TextStatistics textStatistics;
    private String textForAnalyze;

    @Test
    public void textParser() {
        textForAnalyze = "раз, два, раз, три-четыре!";

        textStatistics = new TextAnalyzer().getTextStatistics(textForAnalyze);

        expectedTotalChar =  26;
        expectedTotalCharWithoutSpaces =  23;

        expectedNumberOfWords =  new TreeMap<>();
        expectedNumberOfWords.put("раз", 2);
        expectedNumberOfWords.put("два", 1);
        expectedNumberOfWords.put("три", 1);
        expectedNumberOfWords.put("четыре", 1);

        expectedNumberOfCharacters =  new TreeMap<>();
        expectedNumberOfCharacters.put('р', 4);
        expectedNumberOfCharacters.put('д', 1);
        expectedNumberOfCharacters.put('а', 3);
        expectedNumberOfCharacters.put('в', 1);
        expectedNumberOfCharacters.put('з', 2);
        expectedNumberOfCharacters.put('т', 2);
        expectedNumberOfCharacters.put('и', 1);
        expectedNumberOfCharacters.put('ч', 1);
        expectedNumberOfCharacters.put('е', 2);
        expectedNumberOfCharacters.put('ы', 1);

        assertThat(textStatistics.getTotalChar()).isEqualTo(expectedTotalChar);
        assertThat(textStatistics.getTotalCharWithoutSpaces()).isEqualTo(expectedTotalCharWithoutSpaces);
        assertThat(textStatistics.getNumberOfWords()).isEqualTo(expectedNumberOfWords);
        assertThat(textStatistics.getNumberOfCharacters()).isEqualTo(expectedNumberOfCharacters);
    }
}