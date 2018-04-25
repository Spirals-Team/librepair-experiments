package ru.aserdyuchenko.testQuestions;

import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BankVisitorTest {
  @Test
  public void whenCreatNewVisitorAndGetTimeStay() {
    BankVisitor visitor = new BankVisitor(LocalTime.of(8, 0), LocalTime.of(8, 3));
    ArrayList<LocalTime> timeStay = visitor.getTimeStay();
    assertThat(timeStay.toString(), is("[08:00, 08:01, 08:02, 08:03]"));
  }
}
