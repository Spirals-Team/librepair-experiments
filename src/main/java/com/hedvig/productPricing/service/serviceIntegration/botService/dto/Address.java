package com.hedvig.productPricing.service.serviceIntegration.botService.dto;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Address {

  private String street;
  private String city;
  private String zipCode;
  private Integer floor;

  public Integer getFloor() {
    if (floor == null) {
      return 0;
    }
    return floor;
  }
}
