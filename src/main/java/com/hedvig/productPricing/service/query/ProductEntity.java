package com.hedvig.productPricing.service.query;

import com.hedvig.productPricing.service.aggregates.Product.ProductTypes;
import com.hedvig.productPricing.service.serviceIntegration.botService.dto.Address;
import com.hedvig.productPricing.service.aggregates.ProductStates;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
public class ProductEntity {

  private static Logger log = LoggerFactory.getLogger(ProductEntity.class);

  @Id
  public UUID id;

  @ManyToOne
  public UserEntity member;

  @Enumerated(EnumType.STRING)
  public ProductStates state;

  public BigDecimal currentTotalPrice;
  public BigDecimal newPrice;

  public LocalDateTime activeFrom;
  public LocalDateTime activeTo;

  public Boolean insuredAtOtherCompany;
  public String currentInsurer;
  public Instant cancellationEmailSentAt;

  public String certificateBucket;
  public String certificateKey;

  @Embedded
  public Address address;
  public Float livingSpace;

  @Enumerated(EnumType.STRING)
  public ProductTypes houseType;

  public Integer personsInHouseHold;

  @ElementCollection
  @CollectionTable(name = "insurance_items")
  @MapKeyJoinColumn(name = "Id")
  @Column(name = "item")
  public List<String> goodToHaveItems;

  @ElementCollection
  @CollectionTable(name = "peril_states")
  @MapKeyJoinColumn(name = "id")
  @Column(name = "state")
  public Map<String, String> perils;
}
