package com.hedvig.productPricing.service.web.dto;

import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.aggregates.SafetyIncreaser;
import com.hedvig.productPricing.service.query.ProductEntity;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class InsuranceStatusDTO {

  String productId;
  String memberId;
  String memberFirstName;
  String memberLastName;

  List<SafetyIncreaserType> safetyIncreasers;
  ProductStatus insuranceStatus;
  ProductStates insuranceState;

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
  String street;
  String city;
  String zipCode;
  Integer floor;
  Integer personsInHouseHold;
  Float livingSpace;

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

      this.livingSpace =
          productEntity.livingSpace == null
              ? productEntity.member.livingSpace
              : productEntity.livingSpace;

      if (productEntity.address != null) {
        this.street = productEntity.address.getStreet();
        this.city = productEntity.address.getCity();
        this.zipCode = productEntity.address.getZipCode();
        this.floor = productEntity.address.getFloor();
      } else {
        this.street = productEntity.member.address.getStreet();
        this.city = productEntity.member.address.getCity();
        this.zipCode = productEntity.member.address.getZipCode();
        this.floor = productEntity.member.address.getFloor();
      }

    } else {
      this.memberId = null;
      this.memberFirstName = null;
      this.memberLastName = null;
      this.personsInHouseHold = 0;
      this.signedOn = null;
      this.street = null;
      this.city = null;
      this.zipCode = null;
      this.floor = null;
      this.livingSpace = productEntity.livingSpace;
    }

    this.safetyIncreasers = GetGoodToHaveItems(productEntity);

    final ProductStatus.Status status = ProductStatus.createStatus(
      Clock.systemDefaultZone(),
      productEntity.state,
      productEntity.activeFrom,
      productEntity.activeTo);
    this.insuranceStatus = status.getStatus();

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

      this.livingSpace =
          productEntity.livingSpace == null
              ? productEntity.member.livingSpace
              : productEntity.livingSpace;

      if (productEntity.address != null) {
        this.street = productEntity.address.getStreet();
        this.city = productEntity.address.getCity();
        this.zipCode = productEntity.address.getZipCode();
        this.floor = productEntity.address.getFloor();
      } else {
        this.street = productEntity.member.address.getStreet();
        this.city = productEntity.member.address.getCity();
        this.zipCode = productEntity.member.address.getZipCode();
        this.floor = productEntity.member.address.getFloor();
      }

    } else {
      this.memberId = null;
      this.memberFirstName = null;
      this.memberLastName = null;
      this.personsInHouseHold = 0;
      this.signedOn = null;
      this.street = null;
      this.city = null;
      this.zipCode = null;
      this.floor = null;
      this.livingSpace = productEntity.livingSpace;
    }

    this.safetyIncreasers = GetGoodToHaveItems(productEntity);

    this.insuranceStatus =
        ProductStatus.createStatus(
            Clock.systemDefaultZone(),
            productEntity.state,
            productEntity.activeFrom,
            productEntity.activeTo).getStatus();
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

  private List<SafetyIncreaserType> GetGoodToHaveItems(ProductEntity p) {

    if (p.goodToHaveItems != null) {
        if (p.goodToHaveItems.size() > 0) {
            java.util.Collections.sort(p.goodToHaveItems);
            return p.goodToHaveItems
                .stream()
                .map(this::CreateSafetyIncreaserType)
                .collect(Collectors.toList());
        }
    }
    if (p.member != null && p.member.goodToHaveItems != null) {
        if(p.member.goodToHaveItems.size() > 0) {
            java.util.Collections.sort(p.member.goodToHaveItems);
            return p.member
                .goodToHaveItems
                .stream()
                .map(this::CreateSafetyIncreaserType)
                .collect(Collectors.toList());
        }
    }

    return Collections.emptyList();
  }

  private SafetyIncreaserType CreateSafetyIncreaserType(String i) {
    return SafetyIncreaser.createFrom(i);
  }
}
