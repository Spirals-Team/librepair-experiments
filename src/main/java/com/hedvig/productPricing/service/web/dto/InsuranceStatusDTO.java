package com.hedvig.productPricing.service.web.dto;

import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.query.ProductEntity;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
public class InsuranceStatusDTO {

  String productId;
  String memberId;
  String memberFirstName;
  String memberLastName;

  List<String> safetyIncreasers;
  ProductStatus insuranceStatus;
  ProductStates insuranceState;
  int personsInHouseHold;

  BigDecimal currentTotalPrice;
  BigDecimal newTotalPrice;
  Boolean insuredAtOtherCompany;
  String insuranceType;
  LocalDateTime insuranceActiveFrom;
  LocalDateTime insuranceActiveTo;
  boolean certificateUploaded;
  boolean cancellationEmailSent;
  Instant signedOn;
  String certificateUrl;

  public InsuranceStatusDTO(ProductEntity productEntity) {
    this.productId = productEntity.id.toString();

    if (productEntity.member != null) {
      this.memberId = productEntity.member.id;

      this.memberFirstName = productEntity.member.firstName;
      this.memberLastName = productEntity.member.lastName;
      this.personsInHouseHold =
          productEntity.personsInHouseHold != null
              ? productEntity.personsInHouseHold
              : productEntity.member.personsInHouseHold;

      this.signedOn = productEntity.member.signedOn;

    } else {
      this.memberId = null;
      this.memberFirstName = null;
      this.memberLastName = null;
      this.personsInHouseHold = 0;
      this.signedOn = null;
    }

    this.safetyIncreasers = GetGoodToHaveItems(productEntity);

    this.insuranceStatus =
        ProductStatus.createStatus(
            Clock.systemDefaultZone(),
            productEntity.state,
            productEntity.activeFrom,
            productEntity.activeTo);
    this.insuranceState = productEntity.state;
    this.currentTotalPrice = productEntity.currentTotalPrice;
    this.newTotalPrice = productEntity.newPrice;
    this.insuredAtOtherCompany = productEntity.insuredAtOtherCompany;
    this.insuranceType = productEntity.houseType.toString();
    this.insuranceActiveFrom = productEntity.activeFrom;
    this.insuranceActiveTo = productEntity.activeTo;

    this.certificateUploaded =
        productEntity.certificateBucket != null && productEntity.certificateKey != null;
    this.certificateUrl = null;
    this.cancellationEmailSent = productEntity.cancellationEmailSentAt != null;
  }

  public InsuranceStatusDTO(ProductEntity productEntity, String certificateUrl) {
    this.productId = productEntity.id.toString();

    if (productEntity.member != null) {
      this.memberId = productEntity.member.id;

      this.memberFirstName = productEntity.member.firstName;
      this.memberLastName = productEntity.member.lastName;
      this.personsInHouseHold =
          productEntity.personsInHouseHold != null
              ? productEntity.personsInHouseHold
              : productEntity.member.personsInHouseHold;

      this.signedOn = productEntity.member.signedOn;

    } else {
      this.memberId = null;
      this.memberFirstName = null;
      this.memberLastName = null;
      this.personsInHouseHold = 0;
      this.signedOn = null;
    }

    this.safetyIncreasers = GetGoodToHaveItems(productEntity);
    this.insuranceStatus =
        ProductStatus.createStatus(
            Clock.systemDefaultZone(),
            productEntity.state,
            productEntity.activeFrom,
            productEntity.activeTo);
    this.insuranceState = productEntity.state;
    this.currentTotalPrice = productEntity.currentTotalPrice;
    this.newTotalPrice = productEntity.newPrice;
    this.insuredAtOtherCompany = productEntity.insuredAtOtherCompany;
    this.insuranceType = productEntity.houseType.toString();
    this.insuranceActiveFrom = productEntity.activeFrom;
    this.insuranceActiveTo = productEntity.activeTo;

    this.certificateUploaded =
        productEntity.certificateBucket != null && productEntity.certificateKey != null;
    this.certificateUrl = certificateUrl;
    this.cancellationEmailSent = productEntity.cancellationEmailSentAt != null;
  }

  private List<String> GetGoodToHaveItems(ProductEntity p) {

    if (p.goodToHaveItems != null) {
      java.util.Collections.sort(p.goodToHaveItems);
      return p.goodToHaveItems;
    }
    if (p.member.goodToHaveItems != null) {
      java.util.Collections.sort(p.member.goodToHaveItems);
      return p.member.goodToHaveItems;
    }
    return new ArrayList<>();
  }
}
