package com.hedvig.productPricing.service.events;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.axonframework.commandhandling.model.AggregateIdentifier;

@Value
@AllArgsConstructor
public class MemberCreatedEventV2 {

  @AggregateIdentifier
  private String memberId;
  private String ssn;
  private String firstName;
  private String lastName;
  private LocalDate birthDate;
}
