package com.hedvig.productPricing.service.web;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.google.common.collect.Lists;
import com.hedvig.productPricing.pricing.PricingEngine;
import com.hedvig.productPricing.pricing.PricingQueryBase;
import com.hedvig.productPricing.pricing.PricingQuote;
import com.hedvig.productPricing.pricing.PricingResult;
import com.hedvig.productPricing.service.aggregates.Address;
import com.hedvig.productPricing.service.aggregates.Product.ProductTypes;
import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.commands.*;
import com.hedvig.productPricing.service.query.*;
import com.hedvig.productPricing.service.service.InsuranceTransferService;
import com.hedvig.productPricing.service.web.dto.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Stream;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping({"/i/insurance", "/_/insurance"})
public class InternalController {

  private Logger log = LoggerFactory.getLogger(InternalController.class);

  private final CommandGateway commandBus;
  private final UserRepository userRepository;
  private final ContractRepository contractRepository;
  private final ProductRepository productRepository;
  private final InsuranceTransferService insuranceTransferService;
  private final PerilRepository perilRepository;
  private final PricingEngine pricingEngine;
  private final PricingRepository pricingRepo;
  private final AmazonS3Client s3Client;

  private final String certificates3Bucket;

  @Autowired
  public InternalController(
      CommandGateway commandBus,
      UserRepository userRepository,
      ProductRepository productRepository,
      ContractRepository contractRepository,
      InsuranceTransferService insuranceTransferService,
      PerilRepository perilRepository,
      PricingEngine pricingEngine,
      PricingRepository pricingRepo,
      AmazonS3Client s3Client,
      @Value("${hedvig.productPricing.service.certificatesS3Bucket}") String certificateS3Bucket) {
    this.commandBus = commandBus;
    this.userRepository = userRepository;
    this.contractRepository = contractRepository;
    this.productRepository = productRepository;
    this.insuranceTransferService = insuranceTransferService;
    this.perilRepository = perilRepository;
    this.pricingEngine = pricingEngine;
    this.pricingRepo = pricingRepo;
    this.s3Client = s3Client;
    this.certificates3Bucket = certificateS3Bucket;
  }

  @RequestMapping(value = "contractSigned", method = RequestMethod.POST)
  public ResponseEntity<String> ContractsSigned(@RequestBody ContractSignedRequest r)
      throws IOException {

    log.info("Signature base64: {}", r.getSignature());
    log.info(
        "Signature: {}", IOUtils.toString(Base64.getDecoder().decode(r.getSignature()), "UTF-8"));
    log.info("oscp response base64: {}", r.getOscpResponse());
    log.info(
        "oscp response: ",
        IOUtils.toString(Base64.getDecoder().decode(r.getOscpResponse()), "UTF-8"));

    try {
      this.commandBus.sendAndWait(
          new SingedContractCommand(
              r.getMemberId(),
              r.getReferenceToken(),
              r.getSignature(),
              r.getOscpResponse(),
              r.getSignedOn()));
    } catch (AggregateNotFoundException e) {
      return ResponseEntity.notFound().build();
    }

    return ok("");
  }

  @RequestMapping(value = "{memberId}/safetyIncreasers", method = RequestMethod.GET)
  public ResponseEntity<SafetyIncreasersDTO> getSafetyIncreasers(@PathVariable String memberId) {
    UserEntity ue = userRepository.findOne(memberId);
    if (ue == null) {
      return ResponseEntity.notFound().build();
    }

    if (ue.goodToHaveItems == null) {
      ue.goodToHaveItems = new ArrayList<>();
    }

    java.util.Collections.sort(ue.goodToHaveItems);
    SafetyIncreasersDTO returnDTO = new SafetyIncreasersDTO(ue.goodToHaveItems);
    return ok(returnDTO);
  }

  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public List<InsuranceStatusDTO> search(
      @RequestParam(name = "state", defaultValue = "", required = false) ProductStates state,
      @RequestParam(name = "query", defaultValue = "", required = false) String query) {

    String queryParam = StringUtils.trimToNull(query);
    List<ProductEntity> result;

    if (state != null && queryParam != null) {
      result = productRepository.findByMemberAndState(queryParam, state);
    } else if (state == null && queryParam != null) {
      result = productRepository.findByMember(queryParam);
    } else if (state != null && queryParam == null) {
      result = productRepository.findByState(state);
    } else {
      result = productRepository.findAll();
    }

    return result.stream().map(InsuranceStatusDTO::new).collect(Collectors.toList());
  }

  @PostMapping("/{memberId}/certificate")
  public ResponseEntity<?> insuranceCertificate(
      @PathVariable String memberId, @RequestParam MultipartFile file) throws IOException {

    final Optional<ProductEntity> byMemberId = GetCurrentInsurance(memberId);

    if (!byMemberId.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    UUID uid = UUID.randomUUID();
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());
    final String uploadKey = "uploadedCertificate/" + uid;
    PutObjectRequest req =
        new PutObjectRequest(certificates3Bucket, uploadKey, file.getInputStream(), metadata);

    List<Tag> tags = Lists.newArrayList();
    tags.add(new Tag("memberId", memberId));

    req.setTagging(new ObjectTagging(tags));

    final PutObjectResult putObjectResult = s3Client.putObject(req);

    commandBus.send(new CertificateUploadCommand(memberId, certificates3Bucket, uploadKey));
    return ResponseEntity.accepted().build();
  }

  @GetMapping("/{memberId}/certificate")
  public ResponseEntity<?> insuranceCertificate(@PathVariable String memberId) {

    final Optional<ProductEntity> byMemberId = GetCurrentInsurance(memberId);

    return byMemberId
        .filter(pe -> pe.certificateKey != null && pe.certificateBucket != null)
        .map(
            pe -> {
              val object = s3Client.getObject(pe.certificateBucket, pe.certificateKey);

              HttpHeaders headers = new HttpHeaders();
              headers.setContentType(MediaType.APPLICATION_PDF);
              headers.setContentLength(object.getObjectMetadata().getContentLength());

              final InputStreamResource inputStreamResource =
                  new InputStreamResource(object.getObjectContent());
              return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
            })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/{memberId}/insuredAtOtherCompany")
  public ResponseEntity<?> insuredAtOtherCompany(
      @PathVariable String memberId, @RequestBody @Valid InsuredAtOtherCompanyDTO dto) {
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(value = "{memberId}/insurance", method = RequestMethod.GET)
  public ResponseEntity<InsuranceStatusDTO> getInsuranceStatus(@PathVariable String memberId) {

    UserEntity ue = userRepository.findOne(memberId);

    if (ue == null) {
      return ResponseEntity.notFound().build();
    }

    Optional<ProductEntity> product = GetCurrentInsurance(memberId);

    if (!product.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    if (product.get().certificateKey != null && product.get().certificateBucket != null) {
      return ok(new InsuranceStatusDTO(product.get(), generateCertificateURL(product.get())));
    }

    return ok(new InsuranceStatusDTO(product.get()));
  }

  @RequestMapping(value = "{memberId}/activateAtDate", method = RequestMethod.POST)
  public ResponseEntity<?> activate(
      @PathVariable String memberId, @RequestBody ActivateRequestDTO requestBody) {

    try {
      this.commandBus.sendAndWait(
          new ActivateInsuranceAtDate(memberId, requestBody.getActivationDate()));
    } catch (AggregateNotFoundException e) {
      return ResponseEntity.notFound().build();
    }

    return null;
  }

  @GetMapping(path = "/contract/{memberId}", produces = "application/pdf")
  public ResponseEntity<byte[]> getQuotePdf(@PathVariable String memberId) {
    log.info("Getting contract with memberId: " + memberId);
    ContractEntity contract = contractRepository.findOne(memberId);

    return ok(contract.contract);
  }

  @PostMapping(path = "/{memberId}/sendCancellationEmail")
  public ResponseEntity<?> sendCancellationEmail(@PathVariable String memberId) {
    // insuranceTransferService.startTransferProcess(memberId,true, "");
    return ResponseEntity.noContent().build();
  }

  @PostMapping(path = "/{memberId}/setCancellationDate")
  public ResponseEntity<?> setCancellationDate(
      @PathVariable String memberId, @RequestBody SetCancellationDateRequest body) {
    this.commandBus.sendAndWait(
        new SetCancellationDateCommand(memberId, body.getInactivationDate()));
    return ResponseEntity.accepted().build();
  }

  @PostMapping(path = "/terminateMembers")
  public ResponseEntity<?> terminateMembers() {

    Set<UserEntity> usersToTerminate =
        this.userRepository.findByInsuanceActiveToAndInsuranceStateIsNot(
            LocalDateTime.now(), ProductStates.TERMINATED);

    for (UserEntity userEntity : usersToTerminate) {
      log.info("Sending TerminateInsuranceCommand to member: {}", userEntity.id);
      this.commandBus.sendAndWait(new TerminateInsuranceCommand(userEntity.id));
    }

    return ok().build();
  }

  /**
   * Modify specific product.
   *
   * @param
   * @return new insurance Id on success
   */
  @RequestMapping(
      path = "{insuranceToBeReplacedId}/createmodifiedProduct",
      method = RequestMethod.POST,
      produces = "application/json")
  public ResponseEntity<?> createmodifiedProduct(
      @PathVariable String insuranceToBeReplacedId,
      @RequestBody InsuranceModificationDTO changeRequest) {
    // Safety check. TODO: Create a more complete userData integrity test

    if (insuranceToBeReplacedId == null
        || changeRequest == null
        || changeRequest.getMemberId() == null) {
      return ResponseEntity.notFound().build();
    }

    if (!insuranceToBeReplacedId.equals(changeRequest.getIdToBeReplaced().toString())) {
      return ResponseEntity.badRequest().build();
    }

    MDC.put("insuranceId", insuranceToBeReplacedId);
    MDC.put("memeberId", changeRequest.getMemberId());

    // TODO: Avoid duplicate products

    Optional<ProductEntity> p = productRepository.findById(changeRequest.getIdToBeReplaced());
    Optional<UserEntity> m = userRepository.findById(changeRequest.getMemberId());

    if (!p.isPresent() || !m.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    ProductEntity currentProduct = p.get();

    HashMap<String, String> perils;

    if (!currentProduct.houseType.equals(changeRequest.getHouseType())) {
      perils = GetPerils(changeRequest.getHouseType());
    } else {
      perils = new HashMap<>(currentProduct.perils);
    }

    log.info("Price calculation for user:" + changeRequest.getMemberId());
    // Price calculation:
    PricingQueryBase pq = new PricingQueryBase();
    pq.setAlder(calculateAge(currentProduct.member.birthDate));
    pq.setBoyta(changeRequest.getLivingSpace().intValue());
    pq.setFbelopp(1000000);
    pq.setAntper(changeRequest.getPersonsInHouseHold());
    pq.setGeografi(changeRequest.getZipCode());
    pq.setVaning(changeRequest.getFloor());
    pq.setDuration(0); // Only "tillsvidare"
    pq.setSjrisk(1500);
    pq.setHyrdeg(
        changeRequest.getHouseType() == ProductTypes.SUBLET_BRF
            || changeRequest.getHouseType() == ProductTypes.SUBLET_RENTAL);
    pq.setNyteckning(false);
    pq.setLarm(changeRequest.getSafetyIncreasers().contains(SafetyIncreaserType.BURGLAR_ALARM));
    pq.setSakerhetsdorr(
        changeRequest.getSafetyIncreasers().contains(SafetyIncreaserType.SAFETY_DOOR));
    pq.setBetalningsanmarkning(false);
    pq.setPaymentType("");
    pq.setBRF(
        changeRequest.getHouseType() == ProductTypes.BRF
            || changeRequest.getHouseType() == ProductTypes.RENT);
    pq.setStudent(changeRequest.getIsStudent());

    PricingQuote quote = new PricingQuote();
    quote.setUserId(changeRequest.getMemberId());
    quote.setPricingQuery(pq);

    log.info("Ingoing query:\n" + pq);
    if (!pricingEngine.isStartupComplete()) {
      throw new RuntimeException("Pricing engine is not setup. Cannot calculate price");
    }
    pricingEngine.getPrice(quote);
    // insurance.currentTotalPrice = new PricingEngine(user, insurance).getPrice();

    PricingResult pr = quote.getPricingResult(PricingResult.ResultTypes.TOTAL);
    log.info("Outgoing price:" + pr);
    pricingRepo.saveAndFlush(quote);

    if (pr == null) {
      log.error("Could not calculate price");
      throw new RuntimeException("PricingEnginge returned null. Could not calculate price");
      // return ResponseEntity.state(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    Double updatedTotalPrice = pr.getTotalPerMonth();

    UUID id =
        commandBus.sendAndWait(
            new CreateModifiedProductCommand(
                changeRequest.getMemberId(),
                perils,
                updatedTotalPrice,
                changeRequest.getIsStudent(),
                new Address(
                    changeRequest.getStreet(), changeRequest.getCity(), changeRequest.getZipCode()),
                changeRequest.getLivingSpace(),
                changeRequest.getHouseType(),
                currentProduct.currentInsurer == null
                    ? (currentProduct.member.currentInsurer == null
                        ? ""
                        : currentProduct.member.currentInsurer)
                    : currentProduct.currentInsurer,
                changeRequest.getPersonsInHouseHold(),
                changeRequest.getSafetyIncreasers(),
                currentProduct.cancellationEmailSentAt));

    return ResponseEntity.ok().body(String.format("{\"id\":\"%s\"}", id));
  }

  /**
   * Replace insurance with another product.
   *
   * @param
   * @return 204 on success
   */
  public ResponseEntity<?> modifyProduct(@RequestBody ModifyRequestDTO request) {
    if (request.insuranceIdToBeReplaced == null
        || request.InsuranceIdToReplace == null
        || request.memberId == null) {
      return ResponseEntity.badRequest().build();
    }

    List<ProductEntity> products = productRepository.findByMember(request.memberId);

    if (products.size() < 2
        || products.stream().noneMatch(x -> x.id == request.getInsuranceIdToBeReplaced())
        || products.stream().noneMatch(x -> x.id == request.getInsuranceIdToReplace())) {
      return ResponseEntity.badRequest().build();
    }

    commandBus.sendAndWait(
        new ModifyProductCommand(
            request.getInsuranceIdToBeReplaced(),
            request.getInsuranceIdToReplace(),
            request.getMemberId(),
            request.getTerminationDate(),
            request.getActivationDate()));

    return ResponseEntity.ok().build();
  }

  public String generateCertificateURL(ProductEntity product) {
    GeneratePresignedUrlRequest req =
        new GeneratePresignedUrlRequest(product.certificateBucket, product.certificateKey);
    req.withExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)));
    req.withMethod(HttpMethod.GET);

    final URL url = s3Client.generatePresignedUrl(req);

    return url.toString();
  }

  private HashMap<String, String> GetPerils(ProductTypes productType) {

    // Put all perils in a hash map to facilitate subset tailoring
    List<PerilEntity> allPerils = perilRepository.findAll();
    HashMap<String, PerilEntity> perilMap = new HashMap<String, PerilEntity>();
    for (PerilEntity prl : allPerils) perilMap.put(prl.id, prl);

    ArrayList<PerilEntity> mePerils =
        new ArrayList<PerilEntity>(); // perilRepository.findByCategory("ME");
    ArrayList<PerilEntity> housePerils =
        new ArrayList<PerilEntity>(); // perilRepository.findByCategory("HOUSE");
    ArrayList<PerilEntity> stuffPerils =
        new ArrayList<PerilEntity>(); // perilRepository.findByCategory("STUFF");

    mePerils.add(perilMap.get("ME.LEGAL"));
    mePerils.add(perilMap.get("ME.ASSAULT"));
    mePerils.add(perilMap.get("ME.TRAVEL.SICK"));
    mePerils.add(perilMap.get("ME.TRAVEL.LUGGAGE.DELAY"));

    stuffPerils.add(perilMap.get("STUFF.CARELESS"));
    stuffPerils.add(perilMap.get("STUFF.THEFT"));
    stuffPerils.add(perilMap.get("STUFF.DAMAGE"));

    switch (productType) {
      case BRF:
        housePerils.add(perilMap.get("HOUSE.BRF.WATER"));
        housePerils.add(perilMap.get("HOUSE.BRF.WEATHER"));
        housePerils.add(perilMap.get("HOUSE.BRF.FIRE"));
        housePerils.add(perilMap.get("HOUSE.BRF.APPLIANCES"));
        stuffPerils.add(perilMap.get("STUFF.BRF.FIRE"));
        stuffPerils.add(perilMap.get("STUFF.BRF.WATER"));
        stuffPerils.add(perilMap.get("STUFF.BRF.WEATHER"));
        break;
      case RENT:
        housePerils.add(perilMap.get("HOUSE.RENT.WATER"));
        housePerils.add(perilMap.get("HOUSE.RENT.WEATHER"));
        housePerils.add(perilMap.get("HOUSE.RENT.FIRE"));
        housePerils.add(perilMap.get("HOUSE.RENT.APPLIANCES"));
        stuffPerils.add(perilMap.get("STUFF.RENT.FIRE"));
        stuffPerils.add(perilMap.get("STUFF.RENT.WATER"));
        stuffPerils.add(perilMap.get("STUFF.RENT.WEATHER"));
        break;
      case SUBLET_BRF:
        housePerils.add(perilMap.get("HOUSE.SUBLET.BRF.WATER"));
        housePerils.add(perilMap.get("HOUSE.SUBLET.BRF.WEATHER"));
        housePerils.add(perilMap.get("HOUSE.SUBLET.BRF.FIRE"));
        housePerils.add(perilMap.get("HOUSE.SUBLET.BRF.APPLIANCES"));
        stuffPerils.add(perilMap.get("STUFF.SUBLET.BRF.FIRE"));
        stuffPerils.add(perilMap.get("STUFF.SUBLET.BRF.WATER"));
        stuffPerils.add(perilMap.get("STUFF.SUBLET.BRF.WEATHER"));
        break;
      case SUBLET_RENTAL:
        housePerils.add(perilMap.get("HOUSE.SUBLET.RENT.WATER"));
        housePerils.add(perilMap.get("HOUSE.SUBLET.RENT.WEATHER"));
        housePerils.add(perilMap.get("HOUSE.SUBLET.RENT.FIRE"));
        housePerils.add(perilMap.get("HOUSE.SUBLET.RENT.APPLIANCES"));
        stuffPerils.add(perilMap.get("STUFF.SUBLET.RENT.FIRE"));
        stuffPerils.add(perilMap.get("STUFF.SUBLET.RENT.WATER"));
        stuffPerils.add(perilMap.get("STUFF.SUBLET.RENT.WEATHER"));
        break;
    }

    housePerils.add(perilMap.get("HOUSE.BREAK-IN"));
    housePerils.add(perilMap.get("HOUSE.DAMAGE"));

    // ------------------------------
    CategoryDTO cat1 = new CategoryDTO(mePerils);
    CategoryDTO cat2 = new CategoryDTO(housePerils);
    CategoryDTO cat3 = new CategoryDTO(stuffPerils);

    List<PerilDTO> perils = new ArrayList<>();
    perils.addAll(cat1.perils);
    perils.addAll(cat2.perils);
    perils.addAll(cat3.perils);

    HashMap<String, String> perilsHashMap = new HashMap<>();
    for (PerilDTO p : perils) {
      log.debug(p.id);
      perilsHashMap.put(p.id, p.state);
    }

    return perilsHashMap;
  }

  public static int calculateAge(LocalDate birthDate) {
    LocalDate currentDate = LocalDate.now();
    if ((birthDate != null) && (currentDate != null)) {
      return Period.between(birthDate, currentDate).getYears();
    } else {
      return 0;
    }
  }

  private Optional<ProductEntity> GetCurrentInsurance(String memberId) {

    Stream<ProductEntity> signedProducts =
        productRepository
            .findByMemberId(memberId)
            .stream()
            .filter(x -> x.state == ProductStates.SIGNED);

    if (signedProducts.count() > 1) {
      if (signedProducts.anyMatch(x -> x.activeTo == null)) {
        return signedProducts.filter(x -> x.activeTo == null).findFirst();
      } else {
        return signedProducts.max(Comparator.comparing(x -> x.activeTo));
      }
    }

    return signedProducts.findFirst();
  }
}
