package io.joaopinheiro.userservice.unit;

import io.joaopinheiro.userservice.util.SuggestionBuilder;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SuggestionBuilderTest {

    @Test
    public void retrieveLastNTest(){
        List<String> initial = Arrays.asList("beautiful",
                "beautiful girl",
                "beautiful girl from",
                "girl",
                "girl from",
                "from",
                "farmers",
                "farmers market");

        List<String> expected_ONE = Arrays.asList(
                "farmers market");

        List<String> expected_TWO = Arrays.asList(
                "farmers",
                "farmers market");

        List<String> expected_THREE = Arrays.asList(
                "from",
                "farmers",
                "farmers market");

        assertEquals(SuggestionBuilder.retrieveLastN(Collections.emptyList(), 0), Collections.emptyList());
        assertEquals(SuggestionBuilder.retrieveLastN(initial, 0), Collections.emptyList());
        assertEquals(SuggestionBuilder.retrieveLastN(initial, 1), expected_ONE);
        assertEquals(SuggestionBuilder.retrieveLastN(initial, 2), expected_TWO);
        assertEquals(SuggestionBuilder.retrieveLastN(initial, 3), expected_THREE);
    }

    @Test (expected = IllegalArgumentException.class)
    public void retrieveLastNIllegalArgument(){
        SuggestionBuilder.retrieveLastN(Collections.emptyList(), 1);
    }

    @Test
    public void buildSuggestionsTest(){
        Iterator<String> iterator = Arrays.asList(SuggestionBuilder.TOKENS).iterator();
        Set<String> stopWords = new HashSet<>(Arrays.asList(SuggestionBuilder.STOP_WORDS));
        Set<String> resultString = new HashSet<>(Arrays.asList("beautiful",
                "beautiful girl",
                "beautiful girl from",
                "girl",
                "girl from",
                "from",
                "farmers",
                "farmers market",
                "market",
                "like",
                "like chewing",
                "like chewing gum",
                "chewing",
                "chewing gum",
                "gum"));

        for(SuggestionBuilder.Suggestion s: SuggestionBuilder.buildSuggestionsPublicWrapper(iterator, stopWords))
            assertTrue(resultString.contains(s.toString()));
    }
}
