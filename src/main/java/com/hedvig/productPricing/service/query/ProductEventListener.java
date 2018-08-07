package com.hedvig.productPricing.service.query;

import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.aggregates.SafetyIncreaser;
import com.hedvig.productPricing.service.events.*;
import com.hedvig.productPricing.service.serviceIntegration.botService.dto.Address;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import org.axonframework.eventhandling.EventHandler;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductEventListener {

  private static Logger log = LoggerFactory.getLogger(ProductEventListener.class);
  private final ProductRepository repository;
  private final PerilRepository perilRepository;
  private final UserRepository userRepository;
  private final ContractRepository contractRepository;
  private ModelMapper mapper;

  @Autowired
  public ProductEventListener(
      ProductRepository productRepo,
      PerilRepository perilRepo,
      UserRepository userRepo,
      ContractRepository contractRepository) {
    this.repository = productRepo;
    this.perilRepository = perilRepo;
    this.userRepository = userRepo;
    this.contractRepository = contractRepository;
    mapper = new ModelMapper();
  }

  @EventHandler
  public void on(MemberCreatedEvent e) {
    log.info("Creating user: " + e);
    UserEntity user = new UserEntity();
    user.id = e.getMemberId();
    user.firstName = e.getFirstName();
    user.lastName = e.getLastName();
    user.birthDate = e.getBirthDate();

    user.address = mapper.map(e.getAddress(), Address.class);
    user.livingSpace = e.getLivingSpace();
    user.houseType = e.getHouseType().toString();
    user.currentInsurer = e.getCurrentInsurer();
    user.personsInHouseHold = e.getPersonsInHouseHold();
    user.goodToHaveItems =
        e.getSafetyIncreasers().stream().map(SafetyIncreaser::getName).collect(Collectors.toList());
    userRepository.save(user);
  }

  @EventHandler
  public void on(MemberCreatedEventV2 e) {
    log.info("Creating user: " + e);
    UserEntity user = new UserEntity();
    user.id = e.getMemberId();
    user.firstName = e.getFirstName();
    user.lastName = e.getLastName();
    user.birthDate = e.getBirthDate();
    userRepository.save(user);
  }

  @EventHandler
  public void on(UserUpdatedEvent e) {
    log.info("Updating user: " + e);
    UserEntity user = new UserEntity();
    user.id = e.getMemberId();
    user.firstName = e.getFirstName();
    user.lastName = e.getLastName();
    user.birthDate = e.getBirthDate();
    user.address = e.getAddress();
    user.livingSpace = e.getLivingSpace();
    user.houseType = e.getHouseType();
    user.currentInsurer = e.getCurrentInsurer();
    user.personsInHouseHold = e.getPersonsInHouseHold();
    user.goodToHaveItems = e.getGoodToHaveItems();
    userRepository.save(user);
  }

  @EventHandler
  public void on(PerilCreatedEvent e) {
    log.info("Creating peril: " + e);
    PerilEntity peril = new PerilEntity();
    peril.id = e.id; // Name of peril
    peril.defaultState = e.state;
    peril.title = e.title;
    peril.shortText = e.shortText;
    peril.longText = e.longText;
    peril.imageUrl = e.imageUrl;
    peril.category = e.category;
    peril.isRemovable = e.isRemovable;
    perilRepository.save(peril);
  }

  @EventHandler
  public void on(PerilUpdatedEvent e) {
    log.info("Updating peril: " + e);
    Optional<PerilEntity> p = perilRepository.findById(e.getId());
    if (!p.isPresent()) {
      log.error("Peril " + e.getId() + " does not exist");
      return;
    }
    PerilEntity peril = p.get();
    peril.defaultState = e.state;
    peril.title = e.title;
    peril.shortText = e.shortText;
    peril.longText = e.longText;
    peril.imageUrl = e.imageUrl;
    peril.category = e.category;
    peril.isRemovable = e.isRemovable;
    perilRepository.save(peril);
  }

  @EventHandler
  public void on(ProductCreatedEvent e) {
    log.info("ProductCreatedEvent: " + e);
    // assert e.state != null;

    HashMap<String, String> perils = new HashMap<String, String>();
    for (PerilDTO p : e.perils) {
      log.debug(p.id);
      perils.put(p.id, p.state);
    }

    ProductEntity product = new ProductEntity();
    product.id = e.id;
    product.perils = perils;
    product.currentTotalPrice = e.currentTotalPrice;
    product.state = ProductStates.QUOTE;
    product.member = new UserEntity(e.userId);
    product.houseType = e.houseType;
    product.insuredAtOtherCompany = e.insuredAtOtherCompany;
    repository.save(product);

    UserEntity ue = userRepository.findOne(e.userId);
    ue.insuranceState = ProductStates.QUOTE;
    userRepository.save(ue);
  }

  @EventHandler
  public void on(ProductCreatedEventV2 e) {
    log.info("ProductCreatedEventV2: " + e);

    HashMap<String, String> perils = new HashMap<>();
    for (PerilDTO p : e.getPerils()) {
      log.debug(p.id);
      perils.put(p.id, p.state);
    }

    ProductEntity product = new ProductEntity();
    product.id = e.getId();
    product.perils = perils;
    product.currentTotalPrice = e.getCurrentTotalPrice();
    product.state = ProductStates.QUOTE;
    product.member = new UserEntity(e.getMemberId());
    product.insuredAtOtherCompany = e.getInsuredAtOtherCompany();
    product.currentInsurer = e.getCurrentInsurer();

    product.address = mapper.map(e.getAddress(), Address.class);
    product.livingSpace = e.getLivingSpace();
    product.personsInHouseHold = e.getPersonsInHouseHold();
    product.houseType = e.getHouseType();

    product.goodToHaveItems =
        e.getSafetyIncreasers().stream().map(SafetyIncreaser::getName).collect(Collectors.toList());

    repository.save(product);
  }

  @EventHandler
  public void on(ModifiedProductCreatedEvent e) {
    log.info("ModifiedProductCreatedEvent: " + e);

    ProductEntity product = new ProductEntity();
    product.id = e.getId();
    product.perils = e.getPerils();
    product.currentTotalPrice = e.getCurrentTotalPrice();
    product.state = ProductStates.QUOTE;
    product.member = new UserEntity(e.getMemberId());
    product.insuredAtOtherCompany = e.getInsuredAtOtherCompany();
    product.currentInsurer = e.getCurrentInsurer();

    product.address = mapper.map(e.getAddress(), Address.class);
    product.livingSpace = e.getLivingSpace();
    product.personsInHouseHold = e.getPersonsInHouseHold();
    product.houseType = e.getHouseType();
    product.cancellationEmailSentAt = e.getCancellationEmailSentAt();

    product.goodToHaveItems =
        e.getSafetyIncreasers().stream().map(SafetyIncreaser::getName).collect(Collectors.toList());

    repository.save(product);
  }

  @EventHandler
  public void on(ProductModifiedEvent e) {
    log.info("ProductModifiedEvent: " + e);

    List<ProductEntity> products = repository.findByMemberId(e.getMemberId());

    Optional<ProductEntity> productToBeReplaced =
        products.stream().filter(x -> x.id.equals(e.getInsuranceIdToBeReplaced())).findFirst();

    Optional<ProductEntity> productToReplace =
        products.stream().filter(x -> x.id.equals(e.getInsuranceIdToReplace())).findFirst();

    if (productToBeReplaced.isPresent() && productToReplace.isPresent()) {

      ProductEntity pToBeReplaced = productToBeReplaced.get();
      ProductEntity pToReplace = productToReplace.get();

      pToBeReplaced.activeTo = e.getTerminationDate().atStartOfDay();

      pToReplace.activeFrom = e.getActivationDate().atStartOfDay();
      pToReplace.state = pToBeReplaced.state;

      repository.save(pToBeReplaced);
      repository.save(pToReplace);
    } else {
      log.error("ProductModifiedEvent:  Product was not found with the following event {} " + e);
    }
  }

  @EventHandler
  public void on(QuoteAcceptedEvent acceptedEvent) {
    // NOOP
  }

  @EventHandler
  public void on(ContractSignedEvent e) {
    Optional<ProductEntity> product = repository.findById(e.getProductId());

    if (product.isPresent()) {
      ProductEntity p = product.get();
      p.state = ProductStates.SIGNED;
      p.activeFrom = e.getActiveFrom();
      repository.save(p);
    }

    UserEntity ue = userRepository.findOne(e.getMemberId());
    ue.insuranceState = ProductStates.SIGNED;
    ue.insuranceActiveFrom = e.getActiveFrom();
    ue.signedOn = e.getSignedOn();

    userRepository.save(ue);
  }

  @EventHandler
  public void on(QuoteCalculatedEvent e) {
    Optional<ProductEntity> productEntity = repository.findById(e.getProductId());

    productEntity.ifPresent(
        p -> {
          p.newPrice = e.getNewPrice();
          for (PerilDTO updatedPeril : e.getUpdatedPerils()) {
            for (Map.Entry<String, String> existingPeril : p.perils.entrySet()) {
              String perilId = existingPeril.getKey();
              if (updatedPeril.id.equals(perilId)) {
                existingPeril.setValue(updatedPeril.state);
              }
            }
          }
          repository.save(p);
        });
  }

  @EventHandler
  public void on(InsuranceMandateCreatedEvent mandateCreatedEvent) {
    ContractEntity entity = new ContractEntity();
    entity.contract = mandateCreatedEvent.getMandatePdf();
    entity.memberId = mandateCreatedEvent.getMemberId();
    entity.type = ContractEntity.ContractType.InsuranceMandate;

    contractRepository.save(entity);
  }

  @EventHandler
  public void on(ActivationDateUpdatedEvent e) {
    final UserEntity user = userRepository.findOne(e.getMemberId());

    user.insuranceActiveFrom =
        e.getActivationDate().atZone(ZoneId.of("Europe/Stockholm")).toLocalDateTime();

    userRepository.save(user);

    final ProductEntity pe = repository.findOne(e.getProductId());
    pe.activeFrom = e.getActivationDate().atZone(ZoneId.of("Europe/Stockholm")).toLocalDateTime();
    repository.save(pe);
  }

  @EventHandler
  public void on(CancellationDateSetEvent e) {
    final UserEntity user = userRepository.findOne(e.getMemberId());
    user.insuanceActiveTo =
        e.getCancellationDate().atZone(ZoneId.of("Europe/Stockholm")).toLocalDateTime();

    final ProductEntity pe = repository.findOne(e.getProductId());
    pe.activeTo = e.getCancellationDate().atZone(ZoneId.of("Europe/Stockholm")).toLocalDateTime();
    repository.save(pe);
  }

  @EventHandler
  public void on(MemberTerminatedEvent e) {
    log.info("Terminating member ({}) in MemberTerminatedEvent event listener", e.getMemberId());
    final UserEntity user = userRepository.findOne(e.getMemberId());
    user.insuranceState = ProductStates.TERMINATED;
    userRepository.save(user);

    List<ProductEntity> pe =
        repository
            .findByMemberId(e.getMemberId())
            .stream()
            .filter(
                x ->
                    x.state == ProductStates.SIGNED
                        && x.activeTo != null
                        && x.activeTo.isBefore(LocalDateTime.now()))
            .collect(Collectors.toList());

    if (pe.size() > 0) {
      pe.forEach(x -> x.state = ProductStates.TERMINATED);
      repository.save(pe);
    }
  }

  @EventHandler
  public void on(CertificateUploadedEvent e) {
    final ProductEntity pe = repository.findOne(e.getProductId());
    pe.certificateBucket = e.getBucketName();
    pe.certificateKey = e.getCertificateKey();
    repository.save(pe);
  }
}
