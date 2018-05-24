package spoon.test.prettyprinter.testclasses;


public class Rule {
    public enum Language {
        DTD, WSDL, OTHER;}

    public static final class Phoneme implements spoon.test.prettyprinter.testclasses.Rule.PhonemeExpr {
        public interface Bidule {}

        public static final java.util.Comparator<spoon.test.prettyprinter.testclasses.Rule.Phoneme> COMPARATOR = new java.util.Comparator<spoon.test.prettyprinter.testclasses.Rule.Phoneme>() {
            @java.lang.Override
            public int compare(final spoon.test.prettyprinter.testclasses.Rule.Phoneme o1, final spoon.test.prettyprinter.testclasses.Rule.Phoneme o2) {
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

        private final java.lang.StringBuilder phonemeText;

        private final spoon.test.prettyprinter.testclasses.Rule.Language language;

        public Phoneme(final java.lang.CharSequence phonemeText, final spoon.test.prettyprinter.testclasses.Rule.Language language) {
            this.phonemeText = new java.lang.StringBuilder(phonemeText);
            this.language = language;
        }

        @java.lang.Override
        public java.lang.Iterable<spoon.test.prettyprinter.testclasses.Rule.Phoneme> getPhonemes() {
            return java.util.Collections.singleton(this);
        }
    }

    public interface PhonemeExpr {
        java.lang.Iterable<spoon.test.prettyprinter.testclasses.Rule.Phoneme> getPhonemes();
    }

    public static final class PhonemeList implements spoon.test.prettyprinter.testclasses.Rule.PhonemeExpr {
        private final java.util.List<spoon.test.prettyprinter.testclasses.Rule.Phoneme> phonemes;

        public PhonemeList(final java.util.List<spoon.test.prettyprinter.testclasses.Rule.Phoneme> phonemes) {
            this.phonemes = phonemes;
        }

        @java.lang.Override
        public java.util.List<spoon.test.prettyprinter.testclasses.Rule.Phoneme> getPhonemes() {
            return this.phonemes;
        }
    }

    public static final java.lang.String ALL = "ALL";

    private static final java.lang.String DOUBLE_QUOTE = "\"";

    private static final java.lang.String HASH_INCLUDE = "#include";

    private static spoon.test.prettyprinter.testclasses.Rule.Phoneme parsePhoneme(final java.lang.String ph) {
        final int open = ph.indexOf("[");
        if (open >= 0) {
            if (!(ph.endsWith("]"))) {
                throw new java.lang.IllegalArgumentException("Phoneme expression contains a '[' but does not end in ']'");
            }
            final java.lang.String before = ph.substring(0, open);
            final java.lang.String in = ph.substring((open + 1), ((ph.length()) - 1));
            final java.util.Set<java.lang.String> langs = new java.util.HashSet<java.lang.String>(java.util.Arrays.asList(in.split("[+]")));
            return new spoon.test.prettyprinter.testclasses.Rule.Phoneme(before, spoon.test.prettyprinter.testclasses.Rule.Language.DTD);
        }
        return new spoon.test.prettyprinter.testclasses.Rule.Phoneme(ph, spoon.test.prettyprinter.testclasses.Rule.Language.WSDL);
    }
}

