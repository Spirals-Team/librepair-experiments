package com.hedvig.productPricing.service.aggregates;

import lombok.Value;

@Value
public class Address {
  private String street;
  private String city;
  private String zipCode;
}
