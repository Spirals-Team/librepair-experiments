package io.joaopinheiro.userservice.util;

import java.util.*;

public class SuggestionBuilder {

    public static final class Suggestion{

        private final String text;

        public Suggestion(String text) {
            this.text = text;
        }

        public String toString() {
            return text;
        }
    }

    /** The maximum amount of words which can be combined to a suggestion */

    private final static int MAX_COMBINED_TOKENS = 3;

    public static final String[] TOKENS  =  {"The", "beautiful", "girl", "from", "the", "farmers", "market", ".", "I", "like", "chewing", "gum", "." };

    public static final String[] STOP_WORDS = {"is", "can", "the"};

    public static final Set<String> PUNCTUATION = new HashSet<>(Arrays.asList(".", ":", "?"));

    private static Set<Suggestion> buildSuggestionsFromTokenFromString(Iterator<String> tokens, Set<String> stopWords) {

        List<String> resultList = new LinkedList<>();

        int depth = 0;

        while(tokens.hasNext()){
            String token = tokens.next().toLowerCase();

            if(stopWords.contains(token) || PUNCTUATION.contains(token) || token.trim().length()==1){
                depth = 0;
                continue;
            }

            List<String> toAppend = retrieveLastN(resultList, depth);
            resultList.add(token);
            for(String s: toAppend){
                resultList.add(s+" "+token);
            }

            if(depth < MAX_COMBINED_TOKENS)
                depth++;
        }

        Set<Suggestion> result = new HashSet<>();

        for(String s: resultList)
            result.add(new Suggestion(s));

        return result;
    }

    /**
     * Auxiliary method to get the last N elements of a list.
     *
     * @param list The list from which we want to get the last n elements
     * @param n The number of elements we want to get from the list. Must be less than or equal to the size of the list
     * @return
     */
    public static List<String> retrieveLastN(List<String> list, int n){
        if(list.size()<n)
            throw new IllegalArgumentException("The list must have at least "+n+" members");

        List<String> result = new ArrayList<String>();
        int lastPos = list.size()-1;
        if(lastPos<0)
            return result;


        for(int i = n-1; i >=0 ; i--){
            result.add(list.get(lastPos-i));
        }

        return result;
    }

    public static Set<Suggestion> buildSuggestionsPublicWrapper(Iterator<String> tokens, Set<String> stopWords){
        return buildSuggestionsFromTokenFromString(tokens, stopWords);
    }
/**
 Method "buildSuggestionsFromTokenFromString" derives a list of suggestions from the given token stream. The given list
 of tokens reflect a sorted list of tokens of a text. Each token reflects either a single word or a punctuation mark like :.?

 A suggestion is either a single word or a combination of following words (delimited by a single space) and does not
 include any stopWord or a single character. Combined word suggestions can maximal include MAX_COMBINED_TOKENS of
 following words. shoud iterate over tokens and create all possible combinations of tokens considering
 As a example, let's consider data mentioned above, so tokens are

 "The", "beautiful", "girl", "from", "the", "farmers", "market", ".", "I", "like", "chewing", "gum", ".",
 and stop words are "is", "can" and "the", so result will be the following set (one entry per row):

 "beautiful",
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
 "gum" */
}
