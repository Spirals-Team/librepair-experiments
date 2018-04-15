package ru.aserdyuchenko.testQuestions;

import java.time.LocalTime;
import java.util.Random;
import java.util.ArrayList;

public class BankVisitor {
  /**
   * Вычисление случайного времени.
   */
  private Random random = new Random();
  /**
   * @param timeIn - время входа в банк.
   */
  private LocalTime timeIn;
  /**
   * @param timeOut - время выхода из банка.
   */
  private LocalTime timeOut;
  /**
   * @param timeStay - время пребывания в банке.
   */
  private ArrayList<LocalTime> timeStay;
  /**
   * Если нет конкретного времени для посетителя банка,
   * то время входа и выхода берется случайное.
   */
  public BankVisitor() {
    this.timeIn = calculateRandomTime(LocalTime.of(random.nextInt(12) + 8, random.nextInt(60)));
    this.timeOut = calculateRandomTime(timeIn);
    this.timeStay = calculateTimeStay(timeIn, timeOut);
  }
  /**
   * Если есть конкретное время входа.
   */
  public BankVisitor(LocalTime timeIn) {
    this.timeIn = timeIn;
    this.timeOut = calculateRandomTime(timeIn);
    this.timeStay = calculateTimeStay(timeIn, timeOut);
  }
  /**
   * Если есть конкретное время входа и выхода.
   */
  public BankVisitor(LocalTime timeIn, LocalTime timeOut) {
    this.timeIn = timeIn;
    this.timeOut = timeOut;
    this.timeStay = calculateTimeStay(timeIn, timeOut);
  }
  /**
   * Расчет случайного времени.
   */
  private LocalTime calculateRandomTime(LocalTime timeIn) {
    LocalTime randomTime = LocalTime.of(random.nextInt(20 - timeIn.getHour()) + timeIn.getHour(), random.nextInt(60 - timeIn.getMinute()) + timeIn.getMinute());
    return randomTime;
  }
  /**
   * Расчет времени прибывания в банке.
   */
  private ArrayList<LocalTime> calculateTimeStay(LocalTime timeIn, LocalTime timeOut) {
    ArrayList<LocalTime> newArray = new ArrayList<LocalTime>();
    for (int hour = timeIn.getHour(); hour <= timeOut.getHour(); hour++) {
      for (int minute = timeIn.getMinute(); minute <= timeOut.getMinute(); minute++) {
        newArray.add(LocalTime.of(hour, minute));
      }
    }
    return newArray;
  }
  /**
   * Возвращение времени входа в банк.
   */
  public LocalTime getTimeIn() {
    return this.timeIn;
  }
  /**
   * Возвращение времени выхода из банка.
   */
  public LocalTime getTimeOut() {
    return this.timeOut;
  }
  /**
   * Возвращение времени прибывания в банке.
   */
  public ArrayList<LocalTime> getTimeStay() {
    return this.timeStay;
  }
}
