package com.hedvig.productPricing.service.web.dto;

import com.hedvig.productPricing.service.aggregates.ProductStates;

import java.time.Clock;
import java.time.LocalDateTime;

public enum ProductStatus {
    PENDING,
    ACTIVE,
    INACTIVE;

    public static ProductStatus createStatus(Clock clock, ProductStates state, LocalDateTime fromDate, LocalDateTime toDate){
        if(state == ProductStates.SIGNED) {
            if(fromDate!=null){
               if(fromDate.compareTo(LocalDateTime.now(clock)) <= 0){
                   return ProductStatus.ACTIVE;
               }else
               {
                   return  INACTIVE;
               }
            }else {
                return  INACTIVE;
            }
        }
        else if(state == ProductStates.TERMINATED || toDate != null && (toDate.compareTo(LocalDateTime.now(clock)) > 0) ) {
            return ProductStatus.INACTIVE;
        }
        else {
            return ProductStatus.PENDING;
        }
    }
}
