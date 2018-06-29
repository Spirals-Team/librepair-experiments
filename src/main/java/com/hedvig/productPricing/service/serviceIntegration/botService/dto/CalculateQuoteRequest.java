package com.hedvig.productPricing.service.serviceIntegration.botService.dto;

import com.hedvig.productPricing.service.aggregates.Product;
import com.hedvig.productPricing.service.web.dto.SafetyIncreaserType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CalculateQuoteRequest {

    private String memberId;
    private String ssn;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    private Boolean student;
    
    private Address address;

    private Float livingSpace;
    private Product.ProductTypes houseType;
    private String currentInsurer;
    private Integer personsInHouseHold;

    private List<SafetyIncreaserType> safetyIncreasers;
}