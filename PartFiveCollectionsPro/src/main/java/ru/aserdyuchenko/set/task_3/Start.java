package ru.aserdyuchenko.set.task_3;

/**
 * @author anton.
 */
class Start {

     private MyHashTable<String> hashTable = new MyHashTable<>();
     private SimpleSet<String> simpleSet = new SimpleSet<>();

     private String generateRandomeString(String symbols) {
        StringBuilder randString = new StringBuilder();
        int count = (int)(Math.random()*5);
        for (int i = 0; i <= count; i++) {
            randString.append(symbols.charAt((int)(Math.random()*symbols.length())));
        }
        return randString.toString();
    }

     long addInHashTable(String line, int amount) {
        long start = System.currentTimeMillis();
        for (int index = 0; index <= amount; index++) {
            hashTable.add(generateRandomeString(line));
        }
        long finish = System.currentTimeMillis();
        return finish - start;
    }

     long addInSimpleSet(String line, int amount) {
        long start = System.currentTimeMillis();
        for (int index = 0; index <= amount; index++) {
            simpleSet.add(generateRandomeString(line));
        }
        long finish = System.currentTimeMillis();
        return finish - start;
    }
}
