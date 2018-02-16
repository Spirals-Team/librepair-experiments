package pl.sternik.ss.zadania.zad12;

import java.time.LocalDate;

public class Zad12 {

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();

        byte day = (byte) date.getDayOfMonth();
        byte year = (byte) date.getYear();
        float temp = 30.5f;

        short day2 = day;
        int year2 = year;
        double temp2 = temp;

        System.out.println(day == day2);
        System.out.println(year == year2);
        System.out.println(temp == temp2);

        Short dayObject = day2;
        Long yearObject = (long) year2;
        Double tempObject = temp2;

        System.out.println(dayObject == day2);
        System.out.println(yearObject == year2);
        System.out.println(tempObject == temp2);
        System.out.println(yearObject.equals(year2));
        System.out.println(tempObject.equals(temp2));

        String tekst = dayObject + " " + yearObject + " " + tempObject;
        System.out.println();
        System.out.println(tekst);

        Byte lByte = 12;
        Short lShort = 10;
        Integer lInteger = 100;
        Long lLong = 300l;
        Float lFloat = 10.5f;
        Double lDouble =5.5;
        Character lChar = 'a';
        Boolean bChar = true;

    }

}
