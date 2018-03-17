package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 25.12.17
 * @ version: java_kurs_standart
 */
public class Start {
    /**
     * Запуск приложения.
     * @param args массив строк.
     * @throws InterruptedException исключение.
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Программа начата.");
        Thread time = new Thread(new Time(5000));
        Thread countChar = new Thread(new CountChar());
        boolean isExit = false;

        time.start();
        countChar.start();

        while (!isExit) {
            if (!time.isAlive()) {
                if (countChar.isAlive()) {
                    countChar.interrupt();
                    isExit = true;
                }
            } else {
                if (!countChar.isAlive()) {
                    time.interrupt();
                    isExit = true;
                } else {
                    continue;
                }
            }
        }
        time.join();
        countChar.join();

        System.out.println("Программа закончила работу.");
    }
}
