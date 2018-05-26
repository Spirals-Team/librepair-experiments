package spoon.test.prettyprinter.testclasses;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Rule {
    public enum Language {
        DTD, WSDL, OTHER;}

    public static final class Phoneme implements Rule.PhonemeExpr {
        public interface Bidule {}

        public static final Comparator<Rule.Phoneme> COMPARATOR = new Comparator<Rule.Phoneme>() {
            @Override
            public int compare(final Rule.Phoneme o1, final Rule.Phoneme o2) {
                for (int i = 0; i < (o1.phonemeText.length()); i++) {
                    if (i >= (o2.phonemeText.length())) {
                        return +1;
                    }
                    final int c = (o1.phonemeText.charAt(i)) - (o2.phonemeText.charAt(i));
                    if (c != 0) {
                        return c;
                    }
                }
                if ((o1.phonemeText.length()) < (o2.phonemeText.length())) {
                    return -1;
                }
                return 0;
            }
        };

        private final StringBuilder phonemeText;

        private final Rule.Language language;

        public Phoneme(final CharSequence phonemeText, final Rule.Language language) {
            this.phonemeText = new StringBuilder(phonemeText);
            this.language = language;
        }

        @Override
        public Iterable<Rule.Phoneme> getPhonemes() {
            return Collections.singleton(this);
        }
    }

    public interface PhonemeExpr {
        Iterable<Rule.Phoneme> getPhonemes();
    }

    public static final class PhonemeList implements Rule.PhonemeExpr {
        private final List<Rule.Phoneme> phonemes;

        public PhonemeList(final List<Rule.Phoneme> phonemes) {
            this.phonemes = phonemes;
        }

        @Override
        public List<Rule.Phoneme> getPhonemes() {
            return this.phonemes;
        }
    }

    public static final String ALL = "ALL";

    private static final String DOUBLE_QUOTE = "\"";

    private static final String HASH_INCLUDE = "#include";

    private static Rule.Phoneme parsePhoneme(final String ph) {
        final int open = ph.indexOf("[");
        if (open >= 0) {
            if (!(ph.endsWith("]"))) {
                throw new IllegalArgumentException("Phoneme expression contains a '[' but does not end in ']'");
            }
            final String before = ph.substring(0, open);
            final String in = ph.substring((open + 1), ((ph.length()) - 1));
            final Set<String> langs = new HashSet<String>(Arrays.asList(in.split("[+]")));
            return new Rule.Phoneme(before, Rule.Language.DTD);
        }
        return new Rule.Phoneme(ph, Rule.Language.WSDL);
    }
}

