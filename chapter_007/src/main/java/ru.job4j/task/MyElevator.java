package ru.job4j.task;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class MyElevator {

    NavigableSet<Integer> calls = new ConcurrentSkipListSet<>();
    int floor = 5;

    private int numFloors;
    private int heightOfOneFloor;
    private int speedOfElevator;
    private int timeTheDoorsOpen;

    public MyElevator(int numFloors, int heightOfOneFloor, int speedOfElevator, int timeTheDoorsOpen) {
        this.numFloors = numFloors;
        this.heightOfOneFloor = heightOfOneFloor;
        this.speedOfElevator = speedOfElevator;
        this.timeTheDoorsOpen = timeTheDoorsOpen;
    }

    public static void main(String[] args) {

        MyElevator e = new MyElevator(15, 3, 1, 3);

        new Thread(new Controller(e.calls)).start();

        new Thread(new Lift(e.calls, e.floor, e)).start();
    }

    private static boolean isValidFloorNumber(String s) {
        return (s != null) && s.matches("\\d{1,2}") && Integer.parseInt(s) <= 20 && Integer.parseInt(s) >= 5;
    }

    static class Lift implements Runnable {

        private final NavigableSet<Integer> calls;

        int floor;

        private MyElevator e;

        Lift(NavigableSet<Integer> calls, int floor, MyElevator e) {
            this.calls = calls;
            this.floor = floor;
            this.e = e;
        }

        @Override
        public void run() {

            for (;;) {
                try {
                    Thread.sleep(100);
                    while (calls.higher(floor) != null) {
                        move(+1);
                    }
                    while (calls.lower(floor) != null) {
                        move(-1);
                    }
                } catch (InterruptedException e) {
                }
            }

        }

        void move(int n) throws InterruptedException {

            floor += n;
            System.out.println("Moving to " + floor);
            Thread.sleep(1000 * e.heightOfOneFloor / e.speedOfElevator);
            if (calls.remove(floor)) {
                System.out.println("the doors are open");
                Thread.sleep(1000 * e.timeTheDoorsOpen);
                System.out.println("the doors are closed");
            }
        }
    }

    static class Controller implements Runnable {

        private final NavigableSet<Integer> calls;

        Controller(NavigableSet<Integer> calls) {
            this.calls = calls;
        }

        @Override
        public void run() {

            while (true) {
                String floorNumberStr = null;
                try {
                    // Read input from console
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    floorNumberStr = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (isValidFloorNumber(floorNumberStr)) {
                    System.out.println("User Pressed : " + floorNumberStr);
                    calls.add(Integer.parseInt(floorNumberStr));

                } else {
                    System.out.println("Floor Request Invalid : " + floorNumberStr);
                }
            }
        }
    }
}