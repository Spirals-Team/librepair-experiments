package com.hedvig.productPricing.service.service;

import com.hedvig.productPricing.service.query.ProductEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class InsuranceBillingInfo {
  String memberId;
  LocalDate activeFrom;
  LocalDate activeTo;
  BigDecimal price;

  private static Logger log = LoggerFactory.getLogger(InsuranceBillingInfo.class);

  public InsuranceBillingInfo(ProductEntity product) {
    this.memberId = product.member.id;
    this.activeFrom = product.activeFrom == null ? null : product.activeFrom.toLocalDate();
    this.activeTo = product.activeTo == null ? null : product.activeTo.toLocalDate();
    this.price = product.currentTotalPrice == null ? BigDecimal.ZERO : product.currentTotalPrice;
  }

    BigDecimal CalculatePricePerMonth(
        YearMonth period) {

      long numberOfActiveDaysInMonth;
      long numberOfDaysInMonth = period.lengthOfMonth();

      if (activeFrom == null) {
        numberOfActiveDaysInMonth = 0;
      } else {
        if (activeFrom.isAfter(period.atEndOfMonth())) {
          numberOfActiveDaysInMonth = 0;
        } else if (activeFrom.isBefore(period.atDay(1))
            && (activeTo == null || activeTo.isAfter(period.atEndOfMonth()))) {
          numberOfActiveDaysInMonth = numberOfDaysInMonth;
        } else if (activeTo != null
            && activeTo.isBefore(period.atEndOfMonth())
            && !activeTo.isBefore(period.atDay(1))) {
          if (activeFrom.isBefore(period.atDay(1))) {
            numberOfActiveDaysInMonth = ChronoUnit.DAYS.between(period.atDay(1), activeTo) + 1;
          }
          else if (activeFrom.equals(activeTo)){
              numberOfActiveDaysInMonth = 0;
          }else {
            numberOfActiveDaysInMonth = ChronoUnit.DAYS.between(activeFrom, activeTo) + 1;
          }

        } else if (activeTo != null && activeTo.isBefore(period.atDay(1))) {
          numberOfActiveDaysInMonth = 0;
        } else {
          numberOfActiveDaysInMonth = ChronoUnit.DAYS.between(activeFrom, period.atEndOfMonth()) + 1;
        }
      }

      BigDecimal percentage =
          BigDecimal.valueOf((float) numberOfActiveDaysInMonth / numberOfDaysInMonth);

      BigDecimal finalPrice = price.multiply(percentage).setScale(0, RoundingMode.HALF_EVEN);

      if (finalPrice.compareTo(BigDecimal.ZERO) < 0){
          log.error("CalculatePricePerMonth: Negative price for month {}, price: {}, memberId: {}", period.toString(), finalPrice, memberId);
      }

      return finalPrice;
    }
}
