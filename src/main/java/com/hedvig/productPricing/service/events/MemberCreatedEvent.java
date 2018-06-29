package com.hedvig.productPricing.service.events;

import com.hedvig.productPricing.service.aggregates.Address;
import com.hedvig.productPricing.service.aggregates.Product;
import com.hedvig.productPricing.service.aggregates.SafetyIncreaser;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.axonframework.commandhandling.model.AggregateIdentifier;

import java.time.LocalDate;
import java.util.List;

@Value
@AllArgsConstructor
public class MemberCreatedEvent {

    @AggregateIdentifier
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
    private List<SafetyIncreaser> safetyIncreasers;

}
