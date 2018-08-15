package ru.job4j.threads;

/**
 * Class Check
 */
public class CharCheck {

    /**
     * Method check.
     * @param string string for check
     */
    public static void check(String string) {
        Thread cc = new Thread(new CountChar(string));
        Thread time = new Thread(new Time(cc));

        cc.start();
        time.start();

        try {
            time.join();
            cc.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Программа завершила работу");
    }

    /**
     * Method main.
     * @param args args
     */
    public static void main(String[] args) {
        CharCheck ch = new CharCheck();
        String str = "Растущие возможности операторов сетей LTE";
        ch.check(str);
    }
}
