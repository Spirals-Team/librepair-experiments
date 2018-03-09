package ru.aserdyuchenko.testQuestions;

import java.time.LocalTime;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TimeOfBankWorkingTest {
  @Test
  public void whenCreatNewTimeAndCheckCountVisitorsIsNull() {
    TimeOfBankWorking time = new TimeOfBankWorking(LocalTime.of(8, 0));
    int countVisitors = time.getCountVisitors();
    assertThat(countVisitors, is(0));
  }
  @Test
  public void whenCreatNewTimeAddNewVisitorAndCheckCountVisitorsIsOne() {
    TimeOfBankWorking time = new TimeOfBankWorking(LocalTime.of(8, 0));
    time.addNewVisitorInCount();
    int countVisitors = time.getCountVisitors();
    assertThat(countVisitors, is(1));
  }
}
