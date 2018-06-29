package com.hedvig.productPricing.service.events;

import lombok.Value;

@Value
public class MemberTerminatedEvent {
    String memberId;
}
