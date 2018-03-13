package ru.job4j.monitoresynchronizy.examples;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {

    private static String kbdTxt;
    private static ExecutorService pool;

    public static void main(String[] args) {

        System.out.println("Текст для поиска: ");
        kbdTxt = getKbdString();

        pool = Executors.newFixedThreadPool(100);

        // Пробегаем по логическим дискам
        for (File f : File.listRoots()) {
            System.out.println(f);
            list(f.getPath());
        }
    }

    static void list(String szDir) {

        File f = new File(szDir);
        String[] sDirList = f.list();

        // Если папка пуста то не заходим
        if (f.list() != null) {
            for (int i = 0; i < sDirList.length; i++) {
                File f1 = new File(szDir + File.separator + sDirList[i]);

                if (f1.isFile()) {
                    pool.submit(new TaskFind(kbdTxt, szDir + File.separator + sDirList[i]));
                } else {
                    list(szDir + File.separator + sDirList[i]);
                }
            }
        }
    }

    static public String getKbdString() {

        byte[] bKbd = new byte[256];
        int iCnt = 0;
        String szStr = "";

        try {
            iCnt = System.in.read(bKbd);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        szStr = new String(bKbd, 0, iCnt);
        szStr = szStr.trim();
        return szStr;
    }

    public static class TaskFind implements Runnable {

        String szName,
                findTxt;

        public TaskFind(String findTxt, String szName) {

            this.szName = szName;
            this.findTxt = findTxt;

        }

        public void run() {

            String s;

            try {

                LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(szName)));

                while (true) {
                    s = lnr.readLine();

                    if (s == null) {
                        break;
                    }

                    if (s.indexOf(findTxt) != -1) {
                        System.out.println(szName);
                        break;
                    }
                }

                lnr.close();

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

}
