package com.hedvig.productPricing.service.aggregates;

import com.hedvig.productPricing.service.commands.CalculateQuoteCommand;
import com.hedvig.productPricing.service.events.*;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import java.util.List;
import lombok.EqualsAndHashCode;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.EntityId;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@EqualsAndHashCode
public class Product {

  private static Logger log = LoggerFactory.getLogger(Product.class);
  public String certificateUrl;
  public boolean hasCertificate;
  private byte[] termsOfSerice;
  public Instant activeFrom;
  public Instant activeTo;

  public Instant getActiveFrom() {
    return activeFrom;
  }

  public Instant getActiveTo() {
    return activeTo;
  }

  public enum ProductTypes {
    BRF,
    RENT,
    SUBLET_RENTAL,
    SUBLET_BRF,
    STUDENT_BRF,
    STUDENT_RENT,
  };

  @EntityId
  public UUID id;

  public String userId;

  // @Collection
  public HashMap<String, String> perils;
  public BigDecimal currentTotalPrice;
  public ProductStates state;
  private BigDecimal newPrice;
  public ProductTypes houseType;

  // new properties
  public Address address;
  public Float livingSpace;
  public Integer personsInhouseHold;
  public List<SafetyIncreaser> safetyIncreasers;
  public String currentInsurer;
  public Boolean isTerminated = false;

  public Product() {
    log.info("Instansiating Product");
  }

  public Product(ProductCreatedEvent e) {
    this.id = e.getId();
    this.userId = e.getUserId();

    // Convert perils from DTO to HashMap
    this.perils = new HashMap<>();
    for (PerilDTO p : e.getPerils()) {
      log.debug(p.id);
      this.perils.put(p.id, p.state);
    }
    // this.perils = e.getPerils();
    this.currentTotalPrice = e.getCurrentTotalPrice();
    this.state = ProductStates.QUOTE;
  }

  public Product(ProductCreatedEventV2 e) {
    this.id = e.getId();
    this.userId = e.getMemberId();

    this.perils = new HashMap<>();
    for (PerilDTO p : e.getPerils()) {
      log.debug(p.id);
      this.perils.put(p.id, p.state);
    }

    this.currentTotalPrice = e.getCurrentTotalPrice();
    this.state = ProductStates.QUOTE;
    this.address = e.getAddress();
    this.livingSpace = e.getLivingSpace();
    this.personsInhouseHold = e.getPersonsInHouseHold();
    this.safetyIncreasers = e.getSafetyIncreasers();
  }

    public Product(ModifiedProductCreatedEvent e) {
        this.id = e.getId();
        this.userId = e.getMemberId();

        this.perils = e.getPerils();

        this.currentTotalPrice = e.getCurrentTotalPrice();
        this.state = ProductStates.QUOTE;
        this.address = e.getAddress();
        this.livingSpace = e.getLivingSpace();
        this.personsInhouseHold = e.getPersonsInHouseHold();
        this.safetyIncreasers = e.getSafetyIncreasers();
    }

  @CommandHandler()
  void calculateQuote(CalculateQuoteCommand cmd) {
    BigDecimal priceDiff = BigDecimal.valueOf(0.0);
    ArrayList<PerilDTO> updatedPerils = new ArrayList<>(cmd.getPerilsToUpdate());
    BigDecimal newPrice = this.newPrice == null ? this.currentTotalPrice : this.newPrice;

    for (PerilDTO p : updatedPerils) {
      if (p.state.equals("ADD_REQUESTED")) {
        p.state = "ADD_PENDING";
        newPrice = newPrice.add(BigDecimal.valueOf(10.0d));
        p.isRemovable = true;
      } else if (p.state.equals("REMOVE_REQUESTED")) {
        p.state = "REMOVE_PENDING";
        newPrice = newPrice.subtract(BigDecimal.valueOf(10.0d));
        p.isRemovable = true;
      }
    }
    apply(new QuoteCalculatedEvent(this.id, updatedPerils, newPrice));
  }

  @EventSourcingHandler
  public void on(QuoteAcceptedEvent event) {
    if (!this.id.equals(event.getProductId())) {
      log.info(
          String.format(
              "Quote did not match this product, this.id: %s, event.productId: %s",
              this.id, event.getProductId()));
      return;
    }

    // If no insurance mandate exist generate mandate to sign
    // Generate ToS to sign
    // If no autogrio mandate exists generate autogiro mandate
    if (this.state != ProductStates.QUOTE) {
      throw new RuntimeException(
          String.format("Cannot accept quote current product is in state: %s", this.state));
    }

    this.termsOfSerice = event.getContract();
  }

  @EventSourcingHandler
  public void on(ContractSignedEvent e) {
    if (!this.id.equals(e.getProductId())) {
      log.info(
          "ContractSingedEvent dit not match this product, this.id: %s, event.productId: %s",
          this.id, e.getProductId());
    }
    this.state = ProductStates.SIGNED;
  }

  @EventSourcingHandler
  void on(QuoteCalculatedEvent e) {
    if (this.id.equals(e.getProductId())) {
      this.newPrice = e.getNewPrice();
    }
  }

  @EventSourcingHandler
  void on(ActivationDateUpdatedEvent e) {
    if (this.id.equals(e.getProductId())) {
      this.activeFrom = e.getActivationDate();
    }
  }

  @EventSourcingHandler
  void on(CancellationDateSetEvent e) {
    if (this.id.equals(e.getProductId())) {
      this.activeTo = e.getCancellationDate();
    }
  }
}
