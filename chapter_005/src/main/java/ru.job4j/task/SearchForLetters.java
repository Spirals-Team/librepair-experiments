package ru.job4j.task;

import java.util.*;
import java.util.function.BiConsumer;

//Сделать задачу поиска всех букв слова в другом слове с повторяющимися буквами
//        К примеру нужно, чтобы все буквы слова мама в количестве - 2 буквы м и - 2 буквы а - присутствовали во втором слове.
//        Примерное решение - использовать структуру с хэш-таблицей в которой можно хранить дублирующиеся значения.
//        Подумать какие сущности хранить в таблице и какой коллекцией воспользоваться.
public class SearchForLetters {

    boolean allLettersAreInTheRightAmount(String searchWord, String testWord) {

        char[] searchChars = searchWord.toCharArray();

        LinkedList searchLinkedList = new LinkedList();

        for (int i = 0; i < searchChars.length; i++) {
            searchLinkedList.add(searchChars[i]);
        }

        char[] testChars = testWord.toCharArray();

        LinkedList testWordLinkedList = new LinkedList();

        for (int i = 0; i < testChars.length; i++) {
            testWordLinkedList.add(testChars[i]);
        }

        Iterator testWordIterator = testWordLinkedList.iterator();

        while (testWordIterator.hasNext()) {

            Character currCharTestWord = (Character) testWordIterator.next();

            searchLinkedList.remove(currCharTestWord);

            if (searchLinkedList.size() == 0) {
                return true;
            }
        }

        return false;
    }

    boolean fastHashMapAlgorithmAllLettersAreInTheRightAmount(String searchWord, String testWord) {

        char[] searchChars = searchWord.toCharArray();

        HashMap searchHashMap = fromArrayToHasMapWithValuesNumberOfRepeatedChars(searchChars);

        char[] testChars = testWord.toCharArray();

        HashMap testHashMap = fromArrayToHasMapWithValuesNumberOfRepeatedChars(testChars);

        Set keySet = searchHashMap.keySet();

        Iterator it = keySet.iterator();

        while (it.hasNext()) {
            char currKey = (char) it.next();

            int numberOfSuchLetterInSearch = (int) searchHashMap.get(currKey);
            int numberOfSuchLetterInTest = (int) testHashMap.get(currKey);

            if (numberOfSuchLetterInSearch > numberOfSuchLetterInTest) {
                return false;
            }
        }

        return true;
    }

    public HashMap fromArrayToHasMapWithValuesNumberOfRepeatedChars(char[] charArray) {

        HashMap charHashMap = new HashMap();

        for (int i = 0; i < charArray.length; i++) {

            char keySearch = charArray[i];
            int valueSearch = 1;

            if (charHashMap.containsKey(keySearch)) {
                valueSearch = (int) charHashMap.get(keySearch);
                valueSearch++;
            }

            charHashMap.put(keySearch, valueSearch);
        }

        return charHashMap;

    }
}