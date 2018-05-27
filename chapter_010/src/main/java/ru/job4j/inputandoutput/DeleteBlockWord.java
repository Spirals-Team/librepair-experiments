package ru.job4j.inputandoutput;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class DeleteBlockWord {
    private static final Logger LOG = Logger.getLogger(DeleteBlockWord.class);

    //задан символьным поток и символьный выходной поток. надо удалить все слова abuse.
    //Важно все преобразования нужно делать в потоке.
    //Ннельзя зачитать весь поток в память, удалить слова и потом записать.
    //Нужно все делать в потоке.
    public void dropAbuses(InputStream in, OutputStream out, String[] abuse) {
        try (InputStream inputStream = in;
        OutputStream outputStream = out
        ) {
            int read;
            List<Integer> list = new ArrayList<>();
            while ((read = inputStream.read()) != -1) {
                if (read == 32) {
                    work(list, outputStream, abuse);
                    outputStream.write(read);
                } else {
                    list.add(read);
                }
            }
            work(list, outputStream, abuse);
        } catch (IOException e) {
            LOG.error("Error: ", e);
        }
    }

    //если список не пустой, преобразует байты в слово и
    //записывает слово в выходной поток, если оно не в черном списке.
    public void work(List<Integer> list, OutputStream out, String[] blackList) throws IOException {
        if (!list.isEmpty()) {
            String word = getWord(list);
            if (isGoodWord(word, blackList)) {
                out.write(word.getBytes());
            }
            list.clear();
        }
    }

    //проверяет вхождения слова в черный список
    public boolean isGoodWord(String word, String[] blackList) {
        for (String blockWord: blackList) {
            if (blockWord.equals(word)) {
                return false;
            }
        }
        return true;
    }

    //метод перевода списка int в слово.
    public String getWord(List<Integer> list) {
        StringBuilder word = new StringBuilder();
        for (Integer value: list) {
            word.append(Character.toChars(value));
        }
        return word.toString();
    }
}
