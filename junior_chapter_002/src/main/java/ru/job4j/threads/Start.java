package ru.job4j.threads;

public class Start {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?");
        sb.append("Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?");
        sb.append("Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?");
        sb.append("Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?");
        sb.append("Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?");
        sb.append("Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?");
        sb.append("Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?");
        sb.append("Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?");
        sb.append("Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?Привет как дела ?");
        Thread countChar = new Thread(new CountChar(sb.toString()));
        Thread time = new Thread(new Time(1, countChar));
        countChar.start();
        time.start();
        try {
            countChar.join();
            time.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Программа завершена!!!");
    }
}
