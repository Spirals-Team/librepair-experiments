package ru.aserdyuchenko.testQuestions;

import java.time.LocalTime;
import java.util.Random;
import java.util.ArrayList;

class Tracker {
  /**
   * Создание случайного(от 0 до 1000) количества посетителей.
   */
  private Random random = new Random();
  /**
   * @param arrayBankVisitors - массив посетителей банка.
   */
  private ArrayList<BankVisitor> arrayBankVisitors;
  /**
   * @param arrayTimeOfBankWorking - Массив времени, работы банка(c 8:00 до 20:00).
   */
  private ArrayList<TimeOfBankWorking> arrayTimeOfBankWorking;
  Tracker() {
    this.arrayTimeOfBankWorking = creatArrayTimeOfBankWorking();
    this.arrayBankVisitors = creatArrayBankVisitors();
    registrationVisitors();
  }
  Tracker(int countVisitorsInAllDay, LocalTime timeIn, LocalTime timeOut) {
    this.arrayTimeOfBankWorking = creatArrayTimeOfBankWorking();
    this.arrayBankVisitors = creatArrayBankVisitors(countVisitorsInAllDay, timeIn, timeOut);
    registrationVisitors();
  }
  /**
   * Создание массива времени работы банка(c 8:00 до 20:00).
   */
  public ArrayList<TimeOfBankWorking> creatArrayTimeOfBankWorking() {
    ArrayList<TimeOfBankWorking> newArray = new ArrayList<TimeOfBankWorking>();
    for (int hour = 8; hour < 20; hour++) {
      for (int minute = 0; minute < 60; minute++) {
        newArray.add(new TimeOfBankWorking(LocalTime.of(hour, minute)));
      }
    }
    return newArray;
  }
  /**
   * Создание случайного количества посетителей банка.
   */
   public ArrayList<BankVisitor> creatArrayBankVisitors() {
     ArrayList<BankVisitor> newArray = new ArrayList<BankVisitor>();
     for (int index = 0; index < random.nextInt(1000); index++) {
       newArray.add(new BankVisitor());
     }
     return newArray;
   }
   /**
    * Создание определенного количества посетителей.
    * Метод для тестирование. Т.е. ислючение случайных чисел.
    */
   public ArrayList<BankVisitor> creatArrayBankVisitors(int countVisitorsInAllDay, LocalTime timeIn, LocalTime timeOut) {
     ArrayList<BankVisitor> newArray = new ArrayList<BankVisitor>();
     for (int index = 0; index < countVisitorsInAllDay; index++) {
       newArray.add(new BankVisitor(timeIn, timeOut));
     }
     return newArray;
   }
   /**
    * Регистрация посетителей.
    */
   public void registrationVisitors() {
     for (TimeOfBankWorking bankTime : arrayTimeOfBankWorking) {
       for (BankVisitor visitor : arrayBankVisitors) {
         for (LocalTime visitorTime : visitor.getTimeStay()) {
           if (bankTime.getTime().equals(visitorTime)) {
             bankTime.addNewVisitorInCount();
           }
         }
       }
     }
   }
   /**
    * Поиск максимального количества посетителей в какой-то момент времени.
    */
   public int findMaxCountVisitorsInTimeOfBankWorking() {
     int maxCount = 0;
     for (TimeOfBankWorking bankTime : arrayTimeOfBankWorking) {
       if (bankTime.getCountVisitors() > maxCount) {
         maxCount = bankTime.getCountVisitors();
       }
     }
     return maxCount;
   }
   /**
    * Возвращение времени, когда было максимальное количество посетителей.
    */
   public LocalTime findTimeWhenMaxCountOfVisitors() {
     LocalTime time = LocalTime.of(8, 0);
     int maxCount = 0;
     for (TimeOfBankWorking bankTime : arrayTimeOfBankWorking) {
       if (bankTime.getCountVisitors() > maxCount) {
         maxCount = bankTime.getCountVisitors();
         time = bankTime.getTime();
       }
     }
     return time;
   }
}
