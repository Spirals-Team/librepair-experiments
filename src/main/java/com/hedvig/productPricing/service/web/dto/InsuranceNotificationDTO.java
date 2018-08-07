package com.hedvig.productPricing.service.web.dto;

import com.hedvig.productPricing.service.query.ProductEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Value;

@Value
public class InsuranceNotificationDTO {
  private UUID insuranceId;
  private String memberId;
  private LocalDateTime activationDate;

  public InsuranceNotificationDTO(ProductEntity p) {
    this.insuranceId = p.id;
    this.memberId = p.member.id;
    this.activationDate = p.activeFrom;
  }
}
