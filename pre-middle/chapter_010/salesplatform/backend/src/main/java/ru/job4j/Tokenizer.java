package ru.job4j;

/**
 * Tokenizer class
 */
public class Tokenizer {

    /**
     * Code / encode token string
     * @param input String
     * @return token
     */
    public String codeEncodeToken(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
