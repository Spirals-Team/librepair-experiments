package ru.aserdyuchenko.testQuestions;

import java.time.LocalTime;
import java.util.ArrayList;

public class TimeOfBankWorking {
  /**
   * @param time - момент времени.
   */
  private LocalTime time;
  /**
   * @param countVisitors - количество посетителей в данный момент времени.
   */
  private int countVisitors;
  public TimeOfBankWorking(LocalTime time) {
    this.time = time;
    this.countVisitors = 0;
  }
  /**
   * Увеличение количества посетителей в данный момент времени.
   */
   public void addNewVisitorInCount() {
     this.countVisitors++;
   }
   /**
    * Возвращение количества посетителей в банке.
    */
   public int getCountVisitors() {
     return this.countVisitors;
   }
   /**
    * Возвращение данного момента времени.
    */
   public LocalTime getTime() {
     return this.time;
   }
}
