package com.hedvig.productPricing.testHelpers;

import com.hedvig.productPricing.service.aggregates.Address;
import com.hedvig.productPricing.service.aggregates.Product;
import com.hedvig.productPricing.service.web.dto.SafetyIncreaserType;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Value
public class MemberFake {
    private String memberId;
    private String ssn;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address address;
    private Float livingSpace;
    private Product.ProductTypes houseType;
    private String currentInsurer;
    private Integer personsInHouseHold;
    private Boolean student;
    private List<SafetyIncreaserType> safetyIncreasers;


    public MemberFake addSafetyIncreaser(SafetyIncreaserType safetyIncreaser) {

        List<SafetyIncreaserType> newSafetyIncreasers = safetyIncreasers == null ? new ArrayList<>(): safetyIncreasers;
        newSafetyIncreasers.add(safetyIncreaser);

        return new MemberFake(
                memberId,
                ssn,
                firstName,
                lastName,
                birthDate,
                address,
                livingSpace,
                houseType,
                currentInsurer,
                personsInHouseHold,
                student,
                newSafetyIncreasers
        );
    }
}
