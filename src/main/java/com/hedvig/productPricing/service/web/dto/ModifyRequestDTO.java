package com.hedvig.productPricing.service.web.dto;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class ModifyRequestDTO {

    public UUID insuranceIdToBeReplaced;
    public ZonedDateTime TerminationDate;
    public UUID InsuranceIdToReplace;
    public ZonedDateTime ActivationDate;
    public String memberId;
}
