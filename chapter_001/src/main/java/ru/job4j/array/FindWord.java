package ru.job4j.array;
/**
 * checking that one word is in another word.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 13.10.2017
 */
public class FindWord {

    /**
     * Method FindSubWordInWord find sub word in word.
     * @param word is a word in which need to find another word.
     * @param subWord is a word what need to find in word.
     * @return true then the word is find in another word.
     */
    public boolean findSubWordInWord(String word, String subWord) {

        char[] arrayWord = myTransformWordToArray(word);
        char[] arraySubWord = myTransformWordToArray(subWord);

        for (int i = 0; i < arrayWord.length - arraySubWord.length + 1; i++) {
            for (int j = 0; j < arraySubWord.length; j++) {
                if (arraySubWord[j] != arrayWord[i + j]) {
                    break;
                }
                if (j == arraySubWord.length - 1) {
                    return true;
                }
            }
        }
        return  false;
    }

    /**
     * Method TransformWordToArray transforms String into array.
     * @param word is a string type variable.
     * @return array from string.
     */
    private char[] myTransformWordToArray(String word) {

        int lengthWord = word.length();
        char[] arrayWord = new char[lengthWord];
        for (int i = 0; i < lengthWord; i++) {
            arrayWord[i] = word.charAt(i);
        }
        return arrayWord;
    }
}