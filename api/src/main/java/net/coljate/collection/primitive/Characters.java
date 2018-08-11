package net.coljate.collection.primitive;

public enum Characters implements CharContainer {

    ALL(c -> true),
    DIGITS(Character::isDigit),
    LOWERCASE(Character::isLowerCase),
    UPPERCASE(Character::isUpperCase),
    WHITESPACE(Character::isWhitespace),
    CURRENCY_SYMBOL(c -> Character.getType(c) == Character.CURRENCY_SYMBOL),
    NONE(c -> false) {
        @Override
        public boolean isEmpty() {
            return true;
        }
    };

    private final CharacterPredicate predicate;

    Characters(final CharacterPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean contains(final char c) {
        return predicate.testChar(c);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public interface CharacterPredicate {

        boolean testChar(char c);

    }

}
