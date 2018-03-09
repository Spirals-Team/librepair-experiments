package ru.aserdyuchenko.testQuestions;

import java.time.LocalTime;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TrackerTest {
  @Test
  public void whenSearchMaxNumberOfVisitors() {
    Tracker tracker = new Tracker(20, LocalTime.of(8, 0), LocalTime.of(8, 1));
    int maxCount = tracker.findMaxCountVisitorsInTimeOfBankWorking();
    assertThat(maxCount, is(20));
  }
  @Test
  public void whenSearchTimeWhenMaxNumberOfVisitors() {
    Tracker tracker = new Tracker(20, LocalTime.of(8, 10), LocalTime.of(8, 15));
    LocalTime time = tracker.findTimeWhenMaxCountOfVisitors();
    assertThat(time, is(LocalTime.of(8, 10)));
  }
}
