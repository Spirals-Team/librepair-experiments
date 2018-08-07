package com.hedvig.productPricing.service.web.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
public class ModifyRequestDTO {

    public UUID insuranceIdToBeReplaced;
    public LocalDate terminationDate;
    public UUID insuranceIdToReplace;
    public LocalDate activationDate;
    public String memberId;
}
