package com.hedvig.productPricing.service.web.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class InsuredAtOtherCompanyDTO {

    @NotNull
    private boolean insuredAtOtherCompany;

}
