package com.hedvig.productPricing.service.web.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class ActivateRequestDTO {
    LocalDate activationDate;
}
