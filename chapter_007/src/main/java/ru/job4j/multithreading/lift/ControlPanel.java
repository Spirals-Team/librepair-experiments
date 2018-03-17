package ru.job4j.multithreading.lift;

import net.jcip.annotations.ThreadSafe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 05.01.18;
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class ControlPanel implements Input {

    private ArrayBlockingQueue<Integer> inside;
    private ArrayBlockingQueue<Integer> ext;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private int floor;
    private static final String P = "P";
    private static final String L = "L";
    private String ln = System.getProperty("line.separator");


    public ControlPanel(ArrayBlockingQueue<Integer> ext, ArrayBlockingQueue<Integer> inside, String args) {
        this.inside = inside;
        this.ext = ext;
        this.floor = Integer.parseInt(args);
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

    /**
     * Метод запрашивает пользователя от куда будет осуществлятся ввод из лифта L или подъезда P
     * и добавляет запросы в соответствующие очереди.
     */
    @Override
    public boolean checkInsideOrOutside() {
        boolean work = false;
        System.out.println(String.format("Введите: %s P если вызов из подъезда, %s L если вызов из лифта: ", ln, ln));
        while (true) {
            try {
                String line = reader.readLine();
                if (line.equalsIgnoreCase(P)) {
                    int callLevel = askPerson("Введите этаж вызова: ");
                    if (!ext.contains(callLevel)) {
                        ext.put(callLevel);
                        work = true;
                    }
                    break;
                } else if (line.equalsIgnoreCase(L)) {
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
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return work;
    }
    /**
     * Метод для обшения с пользователем.
     * @param s вопрос пользователю.
     * @return ответ пользователя.
     */
    @Override
    public int askPerson(String s) {
        int level;
        System.out.println(s);
        while (true) {
            try {
                String line = reader.readLine();
                try {
                    level = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("Введенные данные не коректны, повторите ввод: ");
                    continue;
                }
                if (level > 0 && level <= floor) {
                    break;
                } else {
                    System.out.println("Данного этажа нет в этом доме повторите ввод :");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return level;
    }
}
