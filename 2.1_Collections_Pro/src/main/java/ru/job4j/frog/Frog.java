package ru.job4j.frog;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Class Frog.
 * Our frog can jump five places:
 * A (x = 1, y = 2)
 * B (x = 2, y = 1)
 * C (x = 3, y = 0)
 * D (x = 2, y = -1)
 * E (x = 1, y = -2)
 */
public class Frog {
    /**
     * @param START start position
     */
    private static final Point START = new Point(10, 6);
    /**
     * @param FINISH finish position
     */
    private static final Point FINISH = new Point(8, 9);
    /**
     * @param FTREE first tree
     */
    private static final Point FTREE = new Point(13, 8);
    /**
     * @param STREE second tree
     */
    private static final Point STREE = new Point(4, 7);
    /**
     * @param XA X for jump A
     * @param XB X for jump B
     * @param XC X for jump C
     * @param XD X for jump D
     * @param XE X for jump E
     */
    private static final int XA = 1, XB = 2, XC = 3, XD = 2, XE = 1;
    /**
     * @param YA Y for jump A
     * @param YB Y for jump A
     * @param YC Y for jump A
     * @param YD Y for jump A
     * @param YE Y for jump A
     */
    private static final int YA = 2, YB = 1, YC = 0, YD = -1, YE = -2;
    /**
     * @param WALL value for wall point
     */
    private static final int WALL = -2;
    /**
     * @param END value for end point
     */
    private static final int END = -1;
    /**
     * @param BEGIN value for start point
     */
    private static final int BEGIN = 1;
    /**
     * @param MAXX max X for our board
     */
    private static final int MAXX = 16;
    /**
     * @param MINY min Y for our board
     */
    private static final int MINY = 0;
    /**
     * @param MAXY max Y for our board
     */
    private static final int MAXY = 10;
    /**
     *
     */
    private int count;
    /**
     * @param board our board
     */
    private int[][] board = new int[MAXY][MAXX];
    /**
     * @param list list for Point.
     */
    private LinkedList<Point> list = new LinkedList<>();

    /**
     * Start work.
     */
    public void init() {
        board[FINISH.y][FINISH.x] = END; //finish
        board[FTREE.y][FTREE.x] = WALL; //first tree
        board[STREE.y][STREE.x] = WALL; //second tree
        board[START.y][START.x] = BEGIN; //start point
        this.startJump();
        if (this.findWay()) {
            this.buildWay();
            this.printWay();
        }
    }

    /**
     * Select first jump.
     */
    public void startJump() {
        int x = 0;
        int y = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Укажите стартовый прыжок");
        String choice = sc.nextLine();
        if (choice.equals("A")) {
            x = START.x + XA;
            y = START.y + YA;
        } else if (choice.equals("B")) {
            x = START.x + XB;
            y = START.y + YB;
        } else if (choice.equals("C")) {
            x = START.x + XC;
            y = START.y + YC;
        } else if (choice.equals("D")) {
            x = START.x + XD;
            y = START.y + YD;
        } else if (choice.equals("E")) {
            x = START.x + XE;
            y = START.y + YE;
        }
        board[y][x] = board[START.y][START.x] + 1;
        list.add(new Point(x, y));
    }

    /**
     * Method find way.
     * @return result
     */
    public boolean findWay() {
        boolean result = false;
        Point temp;
        while (!list.isEmpty()) {
            temp = list.removeFirst();
            count = board[temp.y][temp.x];
            if (temp.equals(FINISH)) {
                break;
            }
            wave(temp.x, temp.y, XA, YA, count);
            wave(temp.x, temp.y, XB, YB, count);
            wave(temp.x, temp.y, XC, YC, count);
            wave(temp.x, temp.y, XD, YD, count);
            wave(temp.x, temp.y, XE, YE, count);
        }

        if (board[FINISH.y][FINISH.x] == END) {
            System.out.println("Путь не найден");
        } else {
            System.out.println("Путь найден и состовляет " + (count - 1) + " прыжков");
            list.clear();
            result = true;
        }
        return result;
    }

    /**
     * Method for build way.
     */
    public void buildWay() {
        int x = FINISH.x;
        int y = FINISH.y;
        int temp = board[y][x];
        list.add(FINISH);
        while (x != START.x || y != START.y) {
            if (back(x, y, XA, YA, temp)) {
                x = (x - XA + 16) % 16;
                y = y - YA;
            }
            if (back(x, y, XB, YB, temp)) {
                x = (x - XB + 16) % 16;
                y = y - YB;
            }
            if (back(x, y, XC, YC, temp)) {
                x = (x - XC + 16) % 16;
                y = y - YC;
            }
            if (back(x, y, XD, YD, temp)) {
                x = (x - XD + 16) % 16;
                y = y - YD;
            }
            if (back(x, y, XE, YE, temp)) {
                x = (x - XE + 16) % 16;
                y = y - YE;
            }
            if (x != START.x || y != START.y) {
                temp = temp - 1;
                list.addFirst(new Point(x, y));
            }
        }
    }

    /**
     * Method print way.
     */
    public void printWay() {
        int temp = 1;
        for (Point p : list) {
            System.out.println(String.format("Координаты прыжка №%d: x=%d y=%d", temp, p.x + 1, p.y + 1));
            temp++;
        }
    }

    /**
     * Method launch wave.
     * @param xCurr x for current position
     * @param yCurr y for current position
     * @param xJump x for change current position
     * @param yJump y for change current position
     * @param count number by jump
     */
    public void wave(int xCurr, int yCurr, int xJump, int yJump, int count) {
        int tempX = (xCurr + xJump) % 16;
        int tempY = yCurr + yJump;
        if (!(tempY >= MAXY || tempY < MINY)) {
            if (board[tempY][tempX] == 0 || board[tempY][tempX] == END) {
                board[tempY][tempX] = count + 1;
                list.add(new Point(tempX, tempY));
            }
        }
    }

    /**
     * Method for find correct position
     * @param xCurr x for current position
     * @param yCurr y for current position
     * @param xJump x for change current position
     * @param yJump y for change current position
     * @param count number by jump
     * @return result
     */
    public boolean back(int xCurr, int yCurr, int xJump, int yJump, int count) {
        boolean result = false;
        int tempX = (xCurr - xJump + 16) % 16;
        int tempY = yCurr - yJump;
        if (!(tempY >= MAXY || tempY < MINY)) {

            if (board[tempY][tempX] == count - 1) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Class Point.
     */
    static class Point {
        private int x;
        private int y;

        /**
         * Constructor
         * @param x coord x
         * @param y coord y
         */
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Point point = (Point) o;

            if (x != point.x) {
                return false;
            }
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    /**
     * Method main.
     * @param args args
     */
    public static void main(String[] args) {
        Frog frog = new Frog();
        frog.init();
    }
}
