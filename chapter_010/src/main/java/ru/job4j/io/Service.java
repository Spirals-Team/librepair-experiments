package ru.job4j.io;

import java.io.*;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 12.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Service {
    /**
     * Метод проверяе является ли число в потоке четным.
     * @param is входной поток.
     * @return результат.
     */
    public boolean isNumber(InputStream is) {
        boolean even = false;
        try {
            byte[] buffer = new byte[is.available()];
            is.read(buffer, 0, is.available());
            Integer integer = new Integer(new String(buffer));
            if (integer % 2 == 0) {
                even = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try  {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return even;
    }

    /**
     * Метод производит фильтрацию слов.
     * @param in входной поток.
     * @param out исходящий поток.
     * @param abuse слова паразиты.
     */
    void dropAbuses(InputStream in, OutputStream out, String[] abuse) {
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            while (reader.ready()) {
                line = reader.readLine();
                String[]words = line.split("\\s");
                boolean found = false;
                for (String word : words) {
                    for (String ab : abuse) {
                        if (ab.equalsIgnoreCase(word)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        writer.write(word + " ");
                    }
                }
                writer.append(System.lineSeparator());
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Метод производит фильтрацию слов.
     * @param in входной поток.
     * @param out исходящий поток.
     * @param abuse слова паразиты.
     */
    void dropAbuses2(InputStream in, OutputStream out, String[] abuse) {
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            while (reader.ready()) {
                line = reader.readLine();
                for (String ab : abuse) {
                    if (line.contains(ab)) {
                        line = line.replace(ab, "");
                    }
                }
                writer.write(line + System.lineSeparator());
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
