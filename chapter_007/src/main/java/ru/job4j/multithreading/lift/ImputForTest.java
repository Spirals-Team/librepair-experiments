package ru.job4j.multithreading.lift;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 12.01.18;
 * @version $Id$
 * @since 0.1
 */
public class ImputForTest implements Input {

    private ArrayBlockingQueue<Integer> inside;
    private ArrayBlockingQueue<Integer> ext;
    private String[]answers;
    private int floor;
    private int count;
    private String ln = System.getProperty("line.separator");

    public ImputForTest(ArrayBlockingQueue<Integer> inside, ArrayBlockingQueue<Integer> ext, String args, String[]answers) {
        this.inside = inside;
        this.ext = ext;
        this.floor = Integer.parseInt(args);
        this.answers = answers;
    }

    @Override
    public boolean checkInsideOrOutside() {
        boolean work = false;
        System.out.println(String.format("Введите: %s P если вызов из подъезда, %s L если вызов из лифта: ", ln, ln));
        while (true) {
            try {
                String line = answers[count++];
                if (line.equalsIgnoreCase("P")) {
                    int callLevel = askPerson("Введите этаж вызова: ");
                    if (!ext.contains(callLevel)) {
                        ext.put(callLevel);
                        work = true;
                    }
                    break;
                } else if (line.equalsIgnoreCase("L")) {
                    int callLevel = askPerson("Введите этаж на который хотите переместиться :");
                    if (!inside.contains(callLevel)) {
                        inside.put(callLevel);
                        work = true;
                    }
                    break;
                } else if (line.equals("0")) {
                    ext.put(0);
                    work = false;
                    break;
                } else {
                    System.out.println("Введенные данные не коректны, повторите ввод: ");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return work;
    }

    @Override
    public int askPerson(String s) {
        return Integer.parseInt(answers[count++]);
    }



    @Override
    public void run() {
        boolean work;
        int callLevel = askPerson("Введите этаж вызова или 0 для выхода из программы: ");
        try {
            ext.put(callLevel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        do {
            work = checkInsideOrOutside();
        } while (work);
    }
}
