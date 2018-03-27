package ru.job4j.multithreading.bomber;

import ru.job4j.multithreading.bomber.console.Output;
import ru.job4j.multithreading.bomber.console.RandomOutput;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Главный метод программы.
 * @author Hincu Andrei (andreih1981@gmail.com)on 20.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Start {
    /**
     * Помошник в случайных числах.
     */
    private Output randomOutput;
    /**
     * Игровое поле.
     */
    private final  ReentrantLock[][] board;

    public static void main(String[] args) {
        Start start = new Start(new RandomOutput());
        start.setBlok();
        NpsGuys bomber = new NpsGuys(start, Names.Bomber);
        Thread t = new Thread(bomber, String.valueOf(Names.Bomber));
        t.start();
        int countofMonsters = start.getRandomInt(5, 10);
        System.out.println(String.format("Колличество монстров : %d", countofMonsters));
        //создаем монстров в заданном колличестве
        for (int i = 0; i < countofMonsters; i++) {
            NpsGuys monstr = new NpsGuys(start, Names.Monstr);
            Thread thread = new Thread(monstr, String.valueOf(Names.Monstr));
            thread.setDaemon(true);
            thread.start();
        }
    }

    /**
     * Метод устанавливает блоки куда нельзя ходить.
     */
    private void setBlok() {
        //случайное колличество блоков от 10 до 20 штук
        int countOfBlok = this.randomOutput.getRandomInt(10, 20);
        System.out.println(String.format("Блоков установлено %d", countOfBlok));
        for (int i = 0; i < countOfBlok;) {
            int x = this.randomOutput.getRandomInt(0, board.length);
            int y = this.randomOutput.getRandomInt(0, board.length);
            ReentrantLock lock = board[y][x];
            boolean locked = lock.tryLock();
            if (locked) {
                i++;
            }
        }
    }

    Start(Output output) {
        this.randomOutput = output;
        //устанавливаем случайный размер поля от шириной и высотой от 5 до 10 ячеек.
        int xY = this.randomOutput.getRandomInt(5, 10);
        board = new ReentrantLock[xY][xY];
        for (int i = 0; i < xY; i++) {
            for (int j = 0; j < xY; j++) {
                board[i][j] = new ReentrantLock();
            }
        }
        System.out.println(String.format("Размер поля установлен %d на %d", xY, xY));
    }
    public ReentrantLock[][] getBoard() {
        return board;
    }
    public int getRandomInt(int from, int to) {
        return this.randomOutput.getRandomInt(from, to);
    }
}
