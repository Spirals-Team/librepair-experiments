package ru.skorikov;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 15.01.18
 * @ version: java_kurs_standart
 * <p>
 * Осуществлять обход файловой системы и поиск заданного текста в файловой системе.
 */
@ThreadSafe
public class ParallerSearch {
    /**
     * Начальная директория.
     */
    private String root;
    /**
     * Искомый текст.
     */
    private String text;
    /**
     * Лист расширений.
     */
    private List<String> exts;

    /**
     * Конструктор.
     *
     * @param root стартовая директория.
     * @param text текст.
     * @param exts лист расширений.
     */
    public ParallerSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
        threadsSize = new AtomicInteger(0);
    }

    //Лист результатов
    /**
     * Лист с результатами.
     */
    @GuardedBy("itself")
    private final List<String> rezult = new ArrayList<>();
    /**
     * Подсчет количества запущенных процессов.
     */
    private AtomicInteger threadsSize;

    /**
     * Поиск текста в файле.
     *
     * @param file входной файл.
     */
    private void searchText(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String s;
            while ((s = reader.readLine()) != null) {
                if (s.contains(text)) {
                    synchronized (rezult) {
                        rezult.add(file.getAbsolutePath());
                    }
                    reader.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Поиск файла.
     * Запуск несколькиих потоков.
     *
     * @param file входной файл.
     */
    private void search(File file) {
        //список директории
        File[] filesInDirectory = file.listFiles();

        //проход по текущей директории
        if (filesInDirectory != null) {
            //ищем файлы в текущей директории
            for (File f : filesInDirectory) {
                threadsSize.incrementAndGet();
                //если директория новый поток
                if (f.isDirectory()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            search(f);
                            threadsSize.decrementAndGet();
                        }
                    }).start();
                } else {
                    //если это файл поток поиска.
                    String str = f.getAbsolutePath();
                    int index = str.lastIndexOf('.');
                    if (index != -1) {
                        str = str.substring(index);
                        if (exts.contains(str)) {
                            searchText(f);
                            threadsSize.decrementAndGet();
                        } else {
                            threadsSize.decrementAndGet();
                        }
                    }
                }
            }
        }
    }

    /**
     * Запуск приложения.
     *
     * @return лист результатов.
     */
    List<String> result() {
        search(new File(root));
        boolean isEnd = false;
        //Дожидаемся завершения поиска.
        while (!isEnd) {
            if (threadsSize.get() == 0) {
                isEnd = true;
            }
        }
        return rezult;
    }

    /**
     * Печать листа результатов.
     */
    public void print() {
        for (String s : rezult) {
            System.out.println(s);
        }
    }

}
