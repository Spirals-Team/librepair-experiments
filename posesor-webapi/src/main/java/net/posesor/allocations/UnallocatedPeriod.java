package net.posesor.allocations;

import lombok.Data;
import lombok.NonNull;

import java.time.YearMonth;

@Data
public class UnallocatedPeriod {
  @NonNull
  private String subjectName;
  @NonNull
  private String paymentTitle;
  @NonNull
  private YearMonth[] periods;
}
