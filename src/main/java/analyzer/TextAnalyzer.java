package analyzer;

import analyzeModel.TextStatistics;
import java.util.*;

public class TextAnalyzer {

    /**
     * Анализатор текста.
     * На вход принимает текст и анализирует его.
     * Возвращает структуру проделанного анализа(class analyzeModel.TextStatistics)
     *
     * @param text текст для анализа.
     * @return структура проделанного анализа, содержит итоги анализа.
     */
    public TextStatistics getTextStatistics(final String text) {
        final int totalChar = totalChar(text);
        final int totalCharWithoutSpaces = totalCharWithoutSpaces(text);
        final TreeMap<Character, Integer> numberOfCharacters = numberOfCharacters(text);
        final TreeMap<String, Integer> numberOfWords = numberOfWords(text);

        return new TextStatistics(totalChar, totalCharWithoutSpaces, numberOfWords, numberOfCharacters);
    }

    /**
     * Анализатор текста.
     * На вход принимает текст и анализирует его.
     * Возвращает структуру проделанного анализа "посимвольно"(class analyzeModel.TextStatistics)
     *
     * @param text текст для анализа.
     * @return структура проделанного анализа, содержит итоги анализа.
     */
    public TextStatistics getCharTextStatistics(final String text) {
        final int totalChar = totalChar(text);
        final int totalCharWithoutSpaces = totalCharWithoutSpaces(text);

        return new TextStatistics(totalChar, totalCharWithoutSpaces);
    }

    /**
     * Анализатор текста.
     * На вход принимает текст и анализирует его.
     * Возвращает структуру проделанного анализа использованных слов и букв(class analyzeModel.TextStatistics)
     *
     * @param text текст для анализа.
     * @return структура проделанного анализа, содержит итоги анализа.
     */
    public TextStatistics getWordTextStatistics(final String text) {
        final TreeMap<Character, Integer> numberOfCharacters = numberOfCharacters(text);
        final TreeMap<String, Integer> numberOfWords = numberOfWords(text);

        return new TextStatistics(numberOfWords, numberOfCharacters);
    }

    /**
     * Метод на вход принимает текст, возвращает кол-во символов без пробелов
     * @param text
     * @return int
     */
    public static int totalCharWithoutSpaces(String text) {
        String[] arr = text.split("");
        int totalCharWithoutSpaces = 0;
        for (String item : arr) {
            if (!" ".equals(item)) {
                totalCharWithoutSpaces += 1;
            }
        }
        return totalCharWithoutSpaces;
    }

    /**
     * Метод принимает на вход текст и подсчитывает общее число символов в нем.
     * @param text
     * @return int
     */
    public static int totalChar(String text) {
        final int totalChar = text.length();
        return totalChar;
    }

    /**
     * Метод на вход принимает техт, возвращает сортированный по ключам map.
     * Метод подсчитывает колличество букв, в тексте, заглавные или сторчные как одна.
     * @param text
     * @return сортированный map
     */
    public static TreeMap<Character, Integer> numberOfCharacters(String text) {
        char[] textArr = text.toCharArray();
        List<Character> charArr = new ArrayList<Character>();
        for (char i : textArr) {
            if (Character.isLetter(i)) {
                charArr.add(Character.toLowerCase(i));
            }
        }
        TreeMap<Character, Integer> charUnique = new TreeMap<Character, Integer>();

        for (char i : charArr) {
            if (charUnique.containsKey(i)) {
                Integer value = charUnique.get(i);
                charUnique.put(i, ++value);
                continue;
            }
            charUnique.put(i, 1);
        }
        return charUnique;
    }

    /**
     * Метод принимает на вход текст, подсчитывает кол-во каждого слова в тексте.
     * Метод возвращает сортированный map. Слово и кол-во этого слова в тексте.
     * @param text
     * @return сортированный map
     */
    public static TreeMap<String, Integer> numberOfWords(String text) {
        char[] textArr = text.toCharArray();
        List<Character> charArr = new ArrayList<Character>();
        for (char i : textArr) {
            if (Character.isLetter(i) || Character.isWhitespace(i)) {
                charArr.add(Character.toLowerCase(i));
            } else {
                charArr.add(' ');
            }
        }

        String str = "";
        for (int i = 0; i < charArr.size(); i++) {
            str += charArr.toString().join("", String.valueOf(charArr.get(i)));

        }
        String[] strArr = str.split(" ");

        TreeMap<String, Integer> wordUnique = new TreeMap<String, Integer>();

        for (String i : strArr) {
            if (i.isEmpty()) continue;
            if (wordUnique.containsKey(i)) {
                Integer value = wordUnique.get(i);
                wordUnique.put(i, ++value);
                continue;
            }
            wordUnique.put(i, 1);
        }
        return wordUnique;
    }
}