package ru.job4j.wordindex;

import org.apache.log4j.Logger;
import java.io.*;
import java.util.Set;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class WordIndex {
    private final static Logger LOG = Logger.getLogger(WordIndex.class);
    private final Trie trie = new Trie();

    //Загрузка данных из файла и построение индекса.
    public void loadFile(String filename) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
            int data = 0;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while ((data = inputStream.read()) != -1) {
                outputStream.write(data);
            }
            byte[] bytes = outputStream.toByteArray();
            int start = 0;
            boolean flagStart = false;
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] != 32) {
                    if (!flagStart) {
                        start = i;
                        flagStart = true;
                        trie.addNew(bytes[i]);
                    } else {
                        trie.add(bytes[i]);
                        if (i == bytes.length - 1) {
                            trie.addEnd(start);
                        }
                    }
                } else {
                    if (flagStart) {
                        flagStart = false;
                        trie.addEnd(start);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LOG.error(String.format("File %s not found: ", filename), e);
        } catch (IOException e) {
            LOG.error("Exception IO: ", e);
        }
    }

    //Возвращает список позиций слова в файле. Если данного слова в файле нет, то возвращается null.
    public Set<Integer> getIndexes4Word(String searchWord) {
        Set<Integer> sarchSet = null;
        if (trie.getSizeWord() != 0) {
            sarchSet = trie.find(searchWord);
        }
        return sarchSet;
    }
}
