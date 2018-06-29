package com.hedvig.productPricing.service.events;

import java.time.LocalDate;
import java.util.List;

import com.hedvig.productPricing.service.serviceIntegration.botService.dto.Address;
import org.axonframework.commandhandling.model.AggregateIdentifier;

import lombok.Value;

@Value
public class UserUpdatedEvent {

	@AggregateIdentifier
    private String memberId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address address;
    private Float livingSpace;
    private String houseType;
    private String currentInsurer;
    private Integer personsInHouseHold;
    private List<String> goodToHaveItems;

}
