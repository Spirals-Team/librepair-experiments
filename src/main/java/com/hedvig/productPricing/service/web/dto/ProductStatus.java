package com.hedvig.productPricing.service.web.dto;

import com.hedvig.productPricing.service.aggregates.ProductStates;
import lombok.Value;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public enum ProductStatus {
    PENDING, //Quote
    ACTIVE,  //Signed and within fromDate and toDate
    INACTIVE, //Signed waiting for old insurance termination;
    INACTIVE_WITH_START_DATE, //Siged with known start date
    TERMINATED; //Insurance is not active


    @Value
    public static class Status {
        ProductStatus status;
        String description;
    }

    public static Status createStatus(Clock clock, ProductStates state, LocalDateTime fromDate, LocalDateTime toDate){
        final boolean fromDateHasPassed = fromDate != null && fromDate.compareTo(LocalDateTime.now(clock)) <= 0;
        final boolean toDateHasPassed = toDate != null && toDate.compareTo(LocalDateTime.now(clock)) <= 0;

        if(state == ProductStates.SIGNED) {
            if(fromDate!=null ){
                if(fromDateHasPassed && !toDateHasPassed){
                   return new Status(ProductStatus.ACTIVE, "Aktiv");
               }else if(!toDateHasPassed)
               {
                   return new Status(INACTIVE_WITH_START_DATE, "Aktiveras " + fromDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
               }else
                {
                    return new Status(ProductStatus.INACTIVE, "Avaktiverad");
                }
            }else {
                return new Status(INACTIVE, "Aktiveras snart");
            }
        }
        else if(state == ProductStates.TERMINATED || toDateHasPassed ) {
            return new Status(ProductStatus.INACTIVE, "Avaktiverad");
        }
        else {
            return new Status(ProductStatus.PENDING, "");
        }
    }
}
