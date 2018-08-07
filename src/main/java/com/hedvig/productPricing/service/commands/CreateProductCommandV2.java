package com.hedvig.productPricing.service.commands;

import com.hedvig.productPricing.service.aggregates.Address;
import com.hedvig.productPricing.service.aggregates.Product.ProductTypes;
import com.hedvig.productPricing.service.web.dto.SafetyIncreaserType;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class CreateProductCommandV2 {

  private static Logger log = LoggerFactory.getLogger(CreateProductCommandV2.class);

  private UUID id;

  @TargetAggregateIdentifier private String memberId;

  private List<PerilDTO> perils;
  private double currentTotalPrice;

  private String firstName;
  private String lastName;
  private LocalDate birthDate;
  private Boolean student;

  private Address address;
  private Float livingSpace;
  private ProductTypes houseType;
  private String currentInsurer;
  private Integer personsInHouseHold;
  private List<SafetyIncreaserType> safetyIncreasers;

  public CreateProductCommandV2(
      String memberId,
      List<PerilDTO> perils,
      double currentTotalPrice,
      String firstName,
      String lastName,
      LocalDate birthDate,
      Boolean student,
      com.hedvig.productPricing.service.serviceIntegration.botService.dto.Address address,
      Float livingSpace,
      ProductTypes houseType,
      String currentInsurer,
      Integer personsInHouseHold,
      List<SafetyIncreaserType> safetyIncreasers) {
    log.info("CreateProductCommandV2");

    this.id = UUID.randomUUID();
    this.memberId = memberId;
    this.perils = perils;
    this.currentTotalPrice = currentTotalPrice;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.student = student;
    this.address = new Address(address.getStreet(), address.getCity(), address.getZipCode());
    this.livingSpace = livingSpace;
    this.houseType = houseType;
    this.currentInsurer = currentInsurer;
    this.personsInHouseHold = personsInHouseHold;
    this.safetyIncreasers = safetyIncreasers;

    log.info(this.toString());
  }
}
