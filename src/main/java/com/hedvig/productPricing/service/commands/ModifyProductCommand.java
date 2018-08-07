package com.hedvig.productPricing.service.commands;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class ModifyProductCommand {
  private static Logger log = LoggerFactory.getLogger(ModifyProductCommand.class);

  public UUID insuranceIdToBeReplaced;
  public UUID InsuranceIdToReplace;

  @TargetAggregateIdentifier
  public String memberId;

  public LocalDate TerminationDate;
  public LocalDate ActivationDate;

    public ModifyProductCommand(UUID insuranceIdToBeReplaced, UUID insuranceIdToReplace,
        String memberId, LocalDate terminationDate, LocalDate activationDate) {
        this.insuranceIdToBeReplaced = insuranceIdToBeReplaced;
        InsuranceIdToReplace = insuranceIdToReplace;
        this.memberId = memberId;
        TerminationDate = terminationDate;
        ActivationDate = activationDate;
    }
}
