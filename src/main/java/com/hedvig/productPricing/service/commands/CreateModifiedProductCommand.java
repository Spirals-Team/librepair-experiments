package com.hedvig.productPricing.service.commands;

import com.hedvig.productPricing.service.aggregates.Address;
import com.hedvig.productPricing.service.aggregates.Product.ProductTypes;
import com.hedvig.productPricing.service.web.dto.SafetyIncreaserType;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class CreateModifiedProductCommand {
    private static Logger log = LoggerFactory.getLogger(CreateModifiedProductCommand.class);

    public UUID updatedId;

    @TargetAggregateIdentifier
    public String memberId;

    public HashMap<String, String> perils;
    public double currentTotalPrice;

    public Boolean student;

    public Address address;
    public Float livingSpace;
    public ProductTypes houseType;

    public String currentInsurer;
    public Instant cancellationEmailSentAt;
    public Integer personsInHouseHold;
    public List<SafetyIncreaserType> safetyIncreasers;

    public CreateModifiedProductCommand(String memberId,
        HashMap<String, String> perils, double currentTotalPrice, Boolean student,
        Address address, Float livingSpace,
        ProductTypes houseType, String currentInsurer, Integer personsInHouseHold,
        List<SafetyIncreaserType> safetyIncreasers, Instant cancellationEmailSentAt) {
        this.updatedId = UUID.randomUUID();
        this.memberId = memberId;
        this.perils = perils;
        this.currentTotalPrice = currentTotalPrice;
        this.student = student;
        this.address = address;
        this.livingSpace = livingSpace;
        this.houseType = houseType;
        this.currentInsurer = currentInsurer;
        this.personsInHouseHold = personsInHouseHold;
        this.safetyIncreasers = safetyIncreasers;
        this.cancellationEmailSentAt = cancellationEmailSentAt;
    }
}
