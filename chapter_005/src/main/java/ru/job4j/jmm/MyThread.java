package ru.job4j.jmm;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class MyThread implements Runnable {
    private Text masage;
    private String str;

    public MyThread(Text msg, String str) {
        this.masage = msg;
        this.str = str;
        new Thread(this).start();
    }

    @Override
    public void run() {
        masage.print(str);
    }
}
