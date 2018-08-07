package com.hedvig.productPricing.service.aggregates;

import com.hedvig.productPricing.service.aggregates.Product.ProductTypes;
import com.hedvig.productPricing.service.commands.*;
import com.hedvig.productPricing.service.events.*;
import com.hedvig.productPricing.service.pdfcreate.PdfCreator;
import com.hedvig.productPricing.service.web.dto.SafetyIncreaserType;
import java.util.ArrayList;
import java.util.Optional;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class MemberAggregate {

  private static Logger log = LoggerFactory.getLogger(MemberAggregate.class);

  private ModelMapper mapper = new ModelMapper();

  @AggregateIdentifier private String memberId;

  private String firstName;
  private String lastName;
  private LocalDate birthDate;
  private Address address;
  private Float livingSpace;
  private ProductTypes houseType;
  private String currentInsurer;
  private Integer personsInHouseHold;
  private List<SafetyIncreaser> safetyIncreasers;

  private boolean terminated = false;

  @AggregateMember List<Product> products = new ArrayList<>();

  private String ssn;

  public MemberAggregate() {
    log.info("Instansiating MemberAggregate");
  }

  @CommandHandler
  public MemberAggregate(CreateMemberCommand command) {
    log.info("create user");
    MemberCreatedEvent e =
        new MemberCreatedEvent(
            command.getMemberId(),
            command.getSsn(),
            command.getFirstName(),
            command.getLastName(),
            command.getBirthDate(),
            command.getAddress(),
            command.getLivingSpace(),
            command.getHouseType(),
            command.getCurrentInsurer(),
            command.getPersonsInHouseHold(),
            createIncreaser(command.getSafetyIncreasers()));

    apply(e);
  }

  @CommandHandler
  public MemberAggregate(CreateMemberCommandV2 command) {
    log.info("create user v2");
    MemberCreatedEventV2 e =
        new MemberCreatedEventV2(
            command.getMemberId(),
            command.getSsn(),
            command.getFirstName(),
            command.getLastName(),
            command.getBirthDate());

    apply(e);
  }

  private List<SafetyIncreaser> createIncreaser(List<SafetyIncreaserType> increasers) {
    return increasers.stream().map(this::CreateSafetyIncreaser).collect(Collectors.toList());
  }

  private SafetyIncreaser CreateSafetyIncreaser(SafetyIncreaserType i) {
    return SafetyIncreaser.createFrom(i);
  }

  @CommandHandler
  public UUID createProduct(CreateProductCommand command) {
    log.info("create product");
    ProductCreatedEvent e =
        new ProductCreatedEvent(
            command.getId(),
            command.getUserId(),
            command.getPerils(),
            BigDecimal.valueOf(command.getCurrentTotalPrice()),
            // ProductStates.QUOTE,
            command.getHouseType(),
            this.currentInsurer != null);

    apply(e);

    return command.getId();
  }

  @CommandHandler
  public UUID createProductV2(CreateProductCommandV2 cmd) {
    log.info("create product v2");
    ProductCreatedEventV2 e =
        new ProductCreatedEventV2(
            cmd.getId(),
            cmd.getMemberId(),
            BigDecimal.valueOf(cmd.getCurrentTotalPrice()),
            cmd.getCurrentInsurer() != null,
            cmd.getCurrentInsurer(),
            cmd.getStudent(),
            cmd.getAddress(),
            cmd.getLivingSpace(),
            cmd.getHouseType(),
            cmd.getPersonsInHouseHold(),
            createIncreaser(cmd.getSafetyIncreasers()),
            cmd.getPerils());

    apply(e);

    return cmd.getId();
  }

  @CommandHandler
  public UUID createmodifiedProduct(CreateModifiedProductCommand cmd) {
    log.info("Create modified product {} " + cmd.getUpdatedId());

    if (this.products != null && this.products.stream().anyMatch(x -> x.id == cmd.getUpdatedId())) {
      throw new RuntimeException("Product already exists");
    }

    ModifiedProductCreatedEvent e =
        new ModifiedProductCreatedEvent(
            cmd.getUpdatedId(),
            cmd.getMemberId(),
            BigDecimal.valueOf(cmd.getCurrentTotalPrice()),
            !cmd.getCurrentInsurer().isEmpty(),
            cmd.getCurrentInsurer(),
            cmd.getStudent(),
            cmd.getAddress(),
            cmd.getLivingSpace(),
            cmd.getHouseType(),
            cmd.getPersonsInHouseHold(),
            createIncreaser(cmd.getSafetyIncreasers()),
            cmd.getPerils(),
            cmd.getCancellationEmailSentAt());

    apply(e);

    return cmd.getUpdatedId();
  }

  @CommandHandler
  public void modifyProduct(ModifyProductCommand cmd) {
    log.info(
        "Modify Product, Product to be replaced {}, product to replace {}",
        cmd.getInsuranceIdToBeReplaced(),
        cmd.getInsuranceIdToReplace());

    if (this.products != null
        && (this.products.stream().noneMatch(x -> x.id.equals(cmd.getInsuranceIdToBeReplaced()))
            || this.products.stream().noneMatch(x -> x.id.equals(cmd.getInsuranceIdToReplace())))) {
      throw new RuntimeException("ModifyProductCommand: Product does not exist {}" + cmd);
    }

    ProductModifiedEvent e =
        new ProductModifiedEvent(
            cmd.getInsuranceIdToBeReplaced(),
            cmd.getInsuranceIdToReplace(),
            cmd.getMemberId(),
            cmd.getTerminationDate(),
            cmd.getActivationDate());

    apply(e);
  }

  @CommandHandler
  boolean acceptQuote(AcceptQuoteCommand cmd) throws IOException {
    log.info("acceptQuote");
    if (this.products == null) {
      throw new RuntimeException("Cannot acceptQuote if no product exists");
    }
    if (this.products.get(0).state != ProductStates.QUOTE) {
      throw new RuntimeException(
          String.format("Cannot accept quote on product in state: %s", this.products.get(0).state));
    }

    apply(new QuoteAcceptedEvent(cmd.id, null, this.products.get(0).id));

    // apply(new TermsOfServicesGenerated());

    return true;
  }

  @CommandHandler()
  void signedContract(SingedContractCommand cmd, PdfCreator pdfCreator) throws IOException {
    if (this.products == null) {
      throw new RuntimeException("No product assigned to member!");
    }

    LocalDateTime activeFrom = this.currentInsurer == null ? LocalDateTime.now() : null;

    Long numberOfQuoteInsurances =
        this.products.stream().filter(x -> x.state == ProductStates.QUOTE).count();

    if (numberOfQuoteInsurances >= 1) {

      Optional<Product> productToBeSigned =
          numberOfQuoteInsurances == 1
              ? this.products.stream().filter(x -> x.state == ProductStates.QUOTE).findFirst()
              : this.products
                  .stream()
                  .reduce((a, b) -> b); // Take latest if there are multiple products

      if (productToBeSigned.isPresent()) {
        apply(
            new ContractSignedEvent(
                this.memberId,
                productToBeSigned.get().id,
                cmd.getReferenceToken(),
                activeFrom,
                cmd.getSignedOn()));

        final byte[] mandatePdf =
            pdfCreator.createMandate(
                LocalDate.now(),
                this.ssn,
                this.firstName + " " + this.lastName,
                cmd.getReferenceToken(),
                cmd.getSignature());

        apply(new InsuranceMandateCreatedEvent(this.memberId, mandatePdf));
      }
    } else {
      log.error(
          "Got request to Sign member insurance but no product was found! for member id: {}",
          memberId);
    }
  }

  @CommandHandler
  void activateAtDate(ActivateInsuranceAtDate cmd) {

    if (products.size() > 1) {

      Optional<Product> productToBeActivated =
          this.products.stream().filter(x -> x.state == ProductStates.SIGNED).findFirst();

      if (!productToBeActivated.isPresent()) {
        throw new RuntimeException("There is no Signed Product!!");
      }

      final ZonedDateTime timeInStockholmZone =
          cmd.getActivationDate().atStartOfDay(ZoneId.of("Europe/Stockholm"));
      apply(
          new ActivationDateUpdatedEvent(
              this.memberId, productToBeActivated.get().id, timeInStockholmZone.toInstant()));

    } else if (products.size() == 1) {

      final ZonedDateTime timeInStockholmZone =
          cmd.getActivationDate().atStartOfDay(ZoneId.of("Europe/Stockholm"));
      apply(
          new ActivationDateUpdatedEvent(
              this.memberId, this.products.get(0).id, timeInStockholmZone.toInstant()));

    } else {
      log.error(
          "Got request to Activate member insurance but no product was found! for member id: {}",
          memberId);
    }
  }

  @CommandHandler
  void cancelInsurance(SetCancellationDateCommand cmd) {

    Optional<Product> productToBeReplaced =
        this.products.stream().filter(x -> x.id.equals(cmd.getInsuranceId())).findFirst();

    if (productToBeReplaced.isPresent()) {

      Product product = productToBeReplaced.get();

      if (product.getActiveFrom() == null
          || (product.getActiveFrom().isBefore(cmd.getCancellationDate())
              || product.getActiveFrom().equals(cmd.getCancellationDate()))) {
        apply(
            new CancellationDateSetEvent(cmd.getMemberId(), product.id, cmd.getCancellationDate()));
      } else {
        log.error("Tried to cancel insurance before activationDate");
      }
    } else {
      log.error(
          "Got request to cancel member insurance but no product was found! for member id: {}",
          memberId);
    }
  }

  @CommandHandler
  void terminateMember(TerminateInsuranceCommand cmd) {

    List<Product> productsToBeTermnated =
        this.products
            .stream()
            .filter(x -> x.activeTo != null && x.state.equals(ProductStates.SIGNED))
            .collect(Collectors.toList());

    if (productsToBeTermnated.size() > 0) {

      for (Product product : productsToBeTermnated) {
        if (product.getActiveTo().isBefore(Instant.now()) && !product.isTerminated) {
          apply(new MemberTerminatedEvent(cmd.getMemberId()));
        } else {
          log.error(
              "Product doesnt have an activeTo date in order to be terminated {} or seems to be terminated isTerminated: {}",
              product,
              product.isTerminated);
        }
      }

    } else {
      log.error(
          "Got request to terminate member insurance but no product was found! for member id: {}",
          memberId);
    }
  }

  @CommandHandler
  void certificateUpload(CertificateUploadCommand cmd) {

    Optional<Product> productToUploadCertificate =
        this.products
            .stream()
            .filter(x -> x.state == ProductStates.SIGNED && x.activeTo == null)
            .findFirst();

    if (productToUploadCertificate.isPresent()) {

      Product product = productToUploadCertificate.get();

      apply(
          new CertificateUploadedEvent(
              cmd.getMemberId(), product.id, cmd.getBucketName(), cmd.getKey()));
    } else {

      apply(
          new CertificateUploadFailedEvent(
              cmd.getMemberId(), cmd.getBucketName(), cmd.getKey(), "Member has no product"));

      log.error(
          "Got request to upload member's insurance certificate but no product was found! for member id: {}",
          memberId);
    }
  }

  @EventSourcingHandler
  public void on(MemberCreatedEvent e) {
    this.memberId = e.getMemberId();
    this.ssn = e.getSsn();
    this.firstName = e.getFirstName();
    this.lastName = e.getLastName();
    this.birthDate = e.getBirthDate();
    this.address = e.getAddress();
    this.livingSpace = e.getLivingSpace();
    this.houseType = e.getHouseType();
    this.currentInsurer = e.getCurrentInsurer();
    this.personsInHouseHold = e.getPersonsInHouseHold();
    this.safetyIncreasers = e.getSafetyIncreasers();
  }

  @EventSourcingHandler
  public void on(MemberCreatedEventV2 e) {
    this.memberId = e.getMemberId();
    this.ssn = e.getSsn();
    this.firstName = e.getFirstName();
    this.lastName = e.getLastName();
    this.birthDate = e.getBirthDate();
  }

  @EventSourcingHandler
  public void on(ProductCreatedEvent e) {
    if (this.products == null) {
      List<Product> ps = new ArrayList<>();
      ps.add(new Product(e));
      this.products = ps;
    } else {
      products.add(new Product(e));
    }
  }

  @EventSourcingHandler
  public void on(ProductCreatedEventV2 e) {
    this.currentInsurer = e.getCurrentInsurer();
    if (this.products == null) {
      List<Product> ps = new ArrayList<>();
      ps.add(new Product(e));
      this.products = ps;
    } else {
      products.add(new Product(e));
    }
  }

  @EventSourcingHandler
  public void on(ModifiedProductCreatedEvent e) {
    if (this.products == null) {
      List<Product> ps = new ArrayList<>();
      ps.add(new Product(e));
      this.products = ps;
    } else {
      products.add(new Product(e));
    }
  }

  @EventSourcingHandler
  public void on(ProductModifiedEvent e) {

    this.products
            .stream()
            .filter(x -> x.id.equals(e.getInsuranceIdToBeReplaced()))
            .findFirst()
            .get()
            .activeTo =
        e.getTerminationDate().atStartOfDay().toInstant(ZoneOffset.UTC);

    this.products
            .stream()
            .filter(x -> x.id.equals(e.getInsuranceIdToReplace()))
            .findFirst()
            .get()
            .state =
        this.products
            .stream()
            .filter(x -> x.id.equals(e.getInsuranceIdToBeReplaced()))
            .findFirst()
            .get()
            .state;

    this.products
            .stream()
            .filter(x -> x.id.equals(e.getInsuranceIdToReplace()))
            .findFirst()
            .get()
            .activeFrom =
        e.getActivationDate().atStartOfDay().toInstant(ZoneOffset.UTC);
  }

  // TODO: FIXME Refactor MemberTerminatedEvent to pass product ID in order to not have the logic
  // scattered
  @EventSourcingHandler
  public void on(MemberTerminatedEvent e) {

    this.products
        .stream()
        .filter(
            x ->
                x.getActiveTo() != null
                    && x.getActiveTo().isBefore(Instant.now())
                    && x.state.equals(ProductStates.SIGNED))
        .forEach(
            x -> {
              x.isTerminated = true;
              x.state = ProductStates.TERMINATED;
            });
  }

  @EventSourcingHandler
  public void on(CertificateUploadedEvent e) {
    this.products
            .stream()
            .filter(x -> x.id.equals(e.getProductId()))
            .findFirst()
            .get()
            .hasCertificate =
        true;
  }
}
