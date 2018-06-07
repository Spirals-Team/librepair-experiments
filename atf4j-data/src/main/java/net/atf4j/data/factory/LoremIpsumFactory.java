/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.data.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.atf4j.core.Text;

/**
 * Lorem Ipsum text generator.
 */
public final class LoremIpsumFactory extends AbstractDataFactory {

    /** LOREM_IPSUM_TXT. */
    private static final String LOREM_IPSUM_TXT = "lorem-ipsum.txt";

    /** The instance. */
    private static LoremIpsumFactory factory = null;

    /** The rows. */
    private String[] rows = null;

    /** The words. */
    private final List<String> words = new ArrayList<String>();

    /** The bounds. */
    private int bounds;

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public static LoremIpsumFactory getInstance() {
        if (LoremIpsumFactory.factory == null) {
            LoremIpsumFactory.factory = new LoremIpsumFactory();
        }
        return LoremIpsumFactory.factory;
    }

    /**
     * Private constructor prevents wild instantiation.
     */
    private LoremIpsumFactory() {
        super();
        initialise();
    }

    /**
     * Initialise.
     */
    protected void initialise() {
        this.rows = load(LOREM_IPSUM_TXT);
        for (final String row : this.rows) {
            final String sanitised = row.replace(",", "").replace(".", "").replace("?", "");
            if (sanitised.length() > 0) {
                final String[] words = sanitised.split("\\W");
                this.log.debug(Arrays.toString(words));
                for (final String word : words) {
                    this.words.add(word.trim().toLowerCase());
                }
            }
        }
        this.bounds = this.words.size();
        this.log.debug(Arrays.toString(this.words.toArray()));
    }

    /**
     * Lorem Ipsum rext.
     *
     * @return the string
     */
    public static String text() {
        return getInstance().getText();
    }

    /**
     * Get the original Lorem Ipsum text.
     *
     * @return the text as String.
     */
    protected String getText() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final String row : this.rows) {
            stringBuilder.append(row);
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    /**
     * Get a single Paragraph.
     *
     * @return the paragraph as a string.
     */
    public static String paragraph() {
        return getInstance().getParagraphs(1);
    }

    /**
     * Gets N paragraphs. Sentences punctuated normally. Paragraphs punctuated
     * with \n
     *
     * @param count the required number of paragraphs.
     * @return the paragraphs as a string.
     */
    public static String paragraphs(final int count) {
        return getInstance().getParagraphs(count);
    }

    /**
     * Gets N paragraphs. Sentences punctuated normally. Paragraphs punctuated
     * with \n
     *
     * @param count the required number of paragraphs.
     * @return the paragraphs as a string.
     */
    private String getParagraphs(final int count) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            final int length = random.nextInt(12) + 4;
            final String sentences = getSentences(length);
            stringBuilder.append(sentences);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Get a single sentence.
     *
     * @return the sentences as a string.
     */
    public static String sentence() {
        return getInstance().getSentences(1);
    }

    /**
     * Get N sentences.
     *
     * @param count the number of sentences required.
     * @return the sentences as a string.
     */
    public static String sentences(final int count) {
        return getInstance().getSentences(count);
    }

    /**
     * Get N sentences.
     *
     * @param count the number of sentences required.
     * @return the sentences as a string.
     */
    private String getSentences(final int count) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            final int length = random.nextInt(12) + 4;
            final String sentence = getInstance().getWords(length);
            stringBuilder.append(sentenceCase(sentence));
        }
        return stringBuilder.toString();
    }

    /**
     * get a single random word.
     *
     * @return the word as a string
     */
    public static String word() {
        return getInstance().getWords(1);
    }

    /**
     * Get N words.
     *
     * @param count the number of words to return.
     * @return the words as a space seperated string.
     */
    public static String words(final int count) {
        return getInstance().getWords(count);
    }

    /**
     * Get N words.
     *
     * @param count the number of words to return.
     * @return the words as a space seperated string.
     */
    private String getWords(final int count) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            final int nextInt = random.nextInt(this.bounds);
            final String word = this.words.get(nextInt);
            stringBuilder.append(word);
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }

    /**
     * Convert string to Sentence case.
     *
     * @param original the original sentence.
     * @return the string as a sentence.
     */
    public String sentenceCase(final String original) {
        final char punctuation = '.';
        return String.format("%s%c", Text.capitalise(original), punctuation);
    }

}
