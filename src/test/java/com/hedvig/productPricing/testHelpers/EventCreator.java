package com.hedvig.productPricing.testHelpers;

import com.hedvig.productPricing.service.aggregates.SafetyIncreaser;
import com.hedvig.productPricing.service.events.MemberCreatedEvent;
import com.hedvig.productPricing.testHelpers.MemberFake;

import java.util.stream.Collectors;

public class EventCreator {

    public static MemberCreatedEvent MemberCreated(MemberFake fake) {
        return new MemberCreatedEvent(
                fake.getMemberId(),
                fake.getSsn(),
                fake.getFirstName(),
                fake.getLastName(),
                fake.getBirthDate(),
                fake.getAddress(),
                fake.getLivingSpace(),
                fake.getHouseType(),
                fake.getCurrentInsurer(),
                fake.getPersonsInHouseHold(),
                fake.getSafetyIncreasers().stream().map(x -> new SafetyIncreaser(x.name())).collect(Collectors.toList()));
    }
}
