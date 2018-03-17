package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 05.01.18
 * @ version: java_kurs_standart
 */
public class ProblemJMM {
    /**
     * Поле дата.
     */
    private int data = 0;
    /**
     * Поле работаем или нет.
     * Должно быть volatile.
     */
    private boolean work = false;
    /**
     * Нить увеличивает дату на 1 через 1 секунду после запуска.
     */
    private Thread dataThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                data++;
                work = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    /**
     * Нить проверяет - работаем или нет и выводит data на экран.
     */
    private Thread cycleThread = new Thread(new Runnable() {
        @Override
        public void run() {
            int i = 0;
            while (!work) {
                i++;
                i--;
            }
            System.out.println(data);
        }
    });

    /**
     * Запуск приложения.
     *
     * @param args массив строк.
     * @throws InterruptedException исключение.
     */
    public static void main(String[] args) throws InterruptedException {

        ProblemJMM problemJMM = new ProblemJMM();

        problemJMM.dataThread.start();
        problemJMM.cycleThread.start();

    }
}
