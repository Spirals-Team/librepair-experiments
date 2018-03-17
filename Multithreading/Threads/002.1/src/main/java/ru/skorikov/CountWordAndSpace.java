package ru.skorikov;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 22.12.17
 * @ version: java_kurs_standart
 */
public class CountWordAndSpace {
    /**
     * Нить считаест количество пробелов в тексте.
     */
    private Thread spaceCount = new Thread(new Runnable() {
        @Override
        public void run() {
            String path = new File("").getAbsolutePath();
            File file = new File(path, "file.txt");
            int count = 0;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String str;
                while ((str = reader.readLine()) != null) {
                    int strLength = str.length();
                    int strNewLength = 0;
                    str = str.replaceAll(" ", "");
                    strNewLength = str.length();
                    count += (strLength - strNewLength);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Space count = " + count);
        }
    });
    /**
     * Нить считает количество слов в тексте.
     */
    private Thread wordCount = new Thread(new Runnable() {
        @Override
        public void run() {
            String path = new File("").getAbsolutePath();
            File file = new File(path, "file.txt");
            int count = 0;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String str;
                while ((str = reader.readLine()) != null) {
                    int tmp = 0;
                    for (int i = 0; i < str.length();) {
                        if (str.charAt(i) == ' ') {
                            i++;
                        } else {
                            tmp++;
                            for (int j = i++; j < str.length();) {
                                if (str.charAt(j) != ' ') {
                                    i++;
                                    j++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    count += tmp;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Word count = " + count);
        }
    });

    /**
     *
     * @param args массив строк.
     * @throws IOException исключение.
     */
    public static void main(String[] args) throws IOException {
        CountWordAndSpace countWordAndSpace = new CountWordAndSpace();
        countWordAndSpace.spaceCount.start();
        countWordAndSpace.wordCount.start();
    }
}
