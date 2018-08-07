package com.hedvig.productPricing.service.web.dto;

import java.util.List;
import lombok.Value;

@Value
public class ProductStatusDTO {

  List<String> safetyIncreasers;
  String insuranceStatus;
}
