package com.hedvig.productPricing.testHelpers;

import com.hedvig.productPricing.service.aggregates.Product;
import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.aggregates.SafetyIncreaser;
import com.hedvig.productPricing.service.events.ContractSignedEvent;
import com.hedvig.productPricing.service.events.MemberCreatedEvent;
import com.hedvig.productPricing.service.events.ProductCreatedEvent;
import com.hedvig.productPricing.service.events.QuoteCalculatedEvent;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.AbstractEventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TestTool {
    private final AbstractEventStore engine;
    private final Map<String, Long> sequenceNumberDirectory = new HashMap<>();
    private final List<DomainEventMessage<?>> createdDomainMessages = new ArrayList<>();

    @Autowired
    public TestTool(AbstractEventStore engine) {
        this.engine = engine;
    }

    public List<DomainEventMessage> commit() {
        engine.publish(createdDomainMessages); //appendEvents(createdDomainMessages);
        List<DomainEventMessage> committedMessages = Collections.unmodifiableList(new ArrayList<>(createdDomainMessages));
        createdDomainMessages.clear();
        sequenceNumberDirectory.clear();
        return committedMessages;
    }

    public TestTool MemberCreated(MemberFake member) {
        createdDomainMessages.add(
                createDomainEventMessage(member.getMemberId(), new MemberCreatedEvent(
                        member.getMemberId(),
                        member.getSsn(),
                        member.getFirstName(),
                        member.getLastName(),
                        member.getBirthDate(),
                        member.getAddress(),
                        member.getLivingSpace(),
                        member.getHouseType(),
                        member.getCurrentInsurer(),
                        member.getPersonsInHouseHold(),
                        member.getSafetyIncreasers().stream().map(x -> new SafetyIncreaser(x.name())).collect(Collectors.toList())))
        );

        return this;
    }

    public TestTool ProductCreated(String memberId, UUID productId, ArrayList<PerilDTO> perilDTOS, double v, ProductStates pending) {
        createdDomainMessages.add(
                createDomainEventMessage(memberId, new ProductCreatedEvent(productId, memberId, perilDTOS, BigDecimal.valueOf(v), Product.ProductTypes.RENT, false)));

        return this;
    }

    public TestTool ContractSigned(String memberId) {
        createdDomainMessages.add(
                createDomainEventMessage(memberId, new ContractSignedEvent(memberId, UUID.randomUUID(), "referenceToken", null, null))
        );

        return this;
    }

    public TestTool QuoteCreated(UUID productId, List<PerilDTO> perils, double newPrice) {
        createdDomainMessages.add(
                createDomainEventMessage(productId.toString(), new QuoteCalculatedEvent(productId, perils, BigDecimal.valueOf(newPrice)))
        );

        return this;
    }

    private <P> DomainEventMessage<P> createDomainEventMessage(String aggregateId, P payload) {
        return new GenericDomainEventMessage<>(
                payload.getClass().getName(),
                aggregateId,
                getNextSequenceNumber(aggregateId),
                payload
        );
    }


    private long getNextSequenceNumber(String aggregateId) {
        if (sequenceNumberDirectory.get(aggregateId) == null) {
            if (engine != null) {
                sequenceNumberDirectory.put(aggregateId, Optional.ofNullable(engine.readEvents(aggregateId).getLastSequenceNumber()).orElse(0L));
            } else {
                sequenceNumberDirectory.put(aggregateId, 0L);
            }
        }

        return sequenceNumberDirectory.put(aggregateId, sequenceNumberDirectory.get(aggregateId) + 1);
    }
}
