package com.hedvig.productPricing.service.web;

import static org.springframework.http.ResponseEntity.ok;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.Tag;
import com.google.common.collect.Lists;
import com.hedvig.productPricing.pricing.PricingResult;
import com.hedvig.productPricing.service.aggregates.Address;
import com.hedvig.productPricing.service.aggregates.Product.ProductTypes;
import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.commands.ActivateInsuranceAtDate;
import com.hedvig.productPricing.service.commands.CertificateUploadCommand;
import com.hedvig.productPricing.service.commands.CreateModifiedProductCommand;
import com.hedvig.productPricing.service.commands.ModifyProductCommand;
import com.hedvig.productPricing.service.commands.SetCancellationDateCommand;
import com.hedvig.productPricing.service.commands.SingedContractCommand;
import com.hedvig.productPricing.service.commands.TerminateInsuranceCommand;
import com.hedvig.productPricing.service.query.ContractEntity;
import com.hedvig.productPricing.service.query.ContractRepository;
import com.hedvig.productPricing.service.query.ProductEntity;
import com.hedvig.productPricing.service.query.ProductRepository;
import com.hedvig.productPricing.service.query.UserEntity;
import com.hedvig.productPricing.service.query.UserRepository;
import com.hedvig.productPricing.service.service.InsuranceBillingService;
import com.hedvig.productPricing.service.service.InsuranceService;
import com.hedvig.productPricing.service.web.dto.ActivateRequestDTO;
import com.hedvig.productPricing.service.web.dto.ContractSignedRequest;
import com.hedvig.productPricing.service.web.dto.InsuranceBillingDTO;
import com.hedvig.productPricing.service.web.dto.InsuranceModificationDTO;
import com.hedvig.productPricing.service.web.dto.InsuranceNotificationDTO;
import com.hedvig.productPricing.service.web.dto.InsuranceStatusDTO;
import com.hedvig.productPricing.service.web.dto.InsuredAtOtherCompanyDTO;
import com.hedvig.productPricing.service.web.dto.ModifyRequestDTO;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import com.hedvig.productPricing.service.web.dto.SafetyIncreasersDTO;
import com.hedvig.productPricing.service.web.dto.SetCancellationDateRequest;
import java.io.IOException;
import java.net.URL;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/i/insurance", "/_/insurance"})
public class InternalController {

  private Logger log = LoggerFactory.getLogger(InternalController.class);

  private final CommandGateway commandBus;
  private final UserRepository userRepository;
  private final ContractRepository contractRepository;
  private final ProductRepository productRepository;
  private final InsuranceBillingService insuranceBillingService;
  private final AmazonS3Client s3Client;
  private final InsuranceService insuranceService;
  private final String certificates3Bucket;

  @Autowired
  public InternalController(
      CommandGateway commandBus,
      UserRepository userRepository,
      ProductRepository productRepository,
      ContractRepository contractRepository,
      InsuranceBillingService insuranceBillingService,
      AmazonS3Client s3Client,
      InsuranceService insuranceService,
      @Value("${hedvig.productPricing.service.certificatesS3Bucket}") String certificateS3Bucket) {
    this.commandBus = commandBus;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
    this.contractRepository = contractRepository;
    this.insuranceBillingService = insuranceBillingService;
    this.s3Client = s3Client;
    this.insuranceService = insuranceService;
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

  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public List<InsuranceNotificationDTO> searchByActivationDate(
      @RequestParam(name = "activationDate") @Valid LocalDate activationDate) {

    List<ProductEntity> products =
        productRepository.findByActiveFromAndActiveToNull(activationDate);

    return products.stream().map(InsuranceNotificationDTO::new).collect(Collectors.toList());
  }

  @PostMapping("/{memberId}/certificate")
  public ResponseEntity<?> insuranceCertificate(
      @PathVariable String memberId, @RequestParam MultipartFile file) throws IOException {

    final Optional<ProductEntity> byMemberId = insuranceService.GetCurrentInsurance(memberId);

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

    final Optional<ProductEntity> byMemberId = insuranceService.GetCurrentInsurance(memberId);

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

    Optional<ProductEntity> product = insuranceService.GetCurrentInsurance(memberId);

    if (!product.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    if (product.get().certificateKey != null && product.get().certificateBucket != null) {
      return ok(new InsuranceStatusDTO(product.get(), generateCertificateURL(product.get())));
    }

    return ok(new InsuranceStatusDTO(product.get()));
  }

  @RequestMapping(value = "{memberId}/insurances", method = RequestMethod.GET)
  public ResponseEntity<List<InsuranceStatusDTO>> getInsurancesByMember(
      @PathVariable String memberId) {

    UserEntity ue = userRepository.findOne(memberId);

    if (ue == null) {
      return ResponseEntity.notFound().build();
    }

    List<ProductEntity> products = productRepository.findByMemberId(memberId);

    if (products.size() <= 0) {
      return ResponseEntity.notFound().build();
    }

    List<InsuranceStatusDTO> insurances =
        products.stream().map(InsuranceStatusDTO::new).collect(Collectors.toList());

    return ok(insurances);
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
        new SetCancellationDateCommand(
            memberId, body.getInsuranceId(), body.getInactivationDate()));
    return ResponseEntity.accepted().build();
  }

  @PostMapping(path = "/terminateMembers")
  public ResponseEntity<?> terminateMembers() {

    Set<UserEntity> usersToTerminate =
        this.userRepository.findByInsuanceActiveToAndInsuranceStateIsNot(
            LocalDateTime.now(), ProductStates.TERMINATED);

    for (UserEntity userEntity : usersToTerminate) {
      log.info("Sending TerminateInsuranceCommand-UserEntity to member: {}", userEntity.id);
      this.commandBus.sendAndWait(new TerminateInsuranceCommand(userEntity.id));
    }

    List<ProductEntity> productsToBeTerminated =
        this.productRepository.findByMemberActiveToAndNotState(
            LocalDateTime.now(), ProductStates.TERMINATED);

    if (productsToBeTerminated.size() > 0) {
      log.error("TerminateInsuranceCommand-ProductEntity extra product ids to terminate");
      for (ProductEntity product : productsToBeTerminated) {
        log.error(
            "Sending TerminateInsuranceCommand-ProductEntity to member: {]", product.member.id);
        this.commandBus.sendAndWait(new TerminateInsuranceCommand(product.member.id));
      }
    }

    return ok().build();
  }

  /**
   * Modify specific product.
   *
   * @param
   * @return new insurance on success
   */
  @RequestMapping(
      path = "{memberId}/createmodifiedProduct",
      method = RequestMethod.POST,
      produces = "application/json")
  public ResponseEntity<InsuranceStatusDTO> createmodifiedProduct(
      @PathVariable String memberId, @RequestBody InsuranceModificationDTO changeRequest) {
    // Safety check. TODO: Create a more complete userData integrity test

    if (memberId == null || changeRequest == null || changeRequest.getMemberId() == null) {
      return ResponseEntity.notFound().build();
    }

    if (!memberId.equals(changeRequest.getMemberId().toString())) {
      return ResponseEntity.badRequest().build();
    }

    MDC.put("memberId", changeRequest.getMemberId());

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

    PricingResult pr =
        insuranceBillingService.getPricingResult(
            changeRequest.getMemberId(),
            currentProduct.member.birthDate,
            changeRequest.getLivingSpace().intValue(),
            changeRequest.getPersonsInHouseHold(),
            changeRequest.getZipCode(),
            changeRequest.getFloor(),
            changeRequest.getSafetyIncreasers(),
            changeRequest.getHouseType(),
            changeRequest.getIsStudent());

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

    Optional<ProductEntity> updatedInsurance = productRepository.findById(id);

    return updatedInsurance.isPresent()
        ? ResponseEntity.ok(new InsuranceStatusDTO(updatedInsurance.get()))
        : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  /**
   * Replace insurance with another product.
   *
   * @param
   * @return 204 on success
   */
  @RequestMapping(
      path = "{memberId}/modifyProduct",
      method = RequestMethod.POST,
      produces = "application/json")
  public ResponseEntity<?> modifyProduct(
      @PathVariable String memberId, @RequestBody ModifyRequestDTO request) {
    if (memberId == null
        || request.insuranceIdToBeReplaced == null
        || request.insuranceIdToReplace == null
        || request.memberId == null) {
      return ResponseEntity.badRequest().build();
    }

    List<ProductEntity> products = productRepository.findByMember(request.memberId);

    if (products.size() < 2
        || products.stream().noneMatch(x -> x.id.equals(request.getInsuranceIdToBeReplaced()))
        || products.stream().noneMatch(x -> x.id.equals(request.getInsuranceIdToReplace()))) {
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

  @RequestMapping(value = "/monthlyBilling", method = RequestMethod.GET)
  public ResponseEntity<List<InsuranceBillingDTO>> getMonthlyBilling(
      @RequestParam(name = "year") int year, @RequestParam(name = "month") int month) {
    YearMonth period;
    try {
      period = YearMonth.of(year, month);
    } catch (DateTimeException exception) {
      log.error(
          "Failed to parse year: {} and month: {} to YearMonth type with the following error: {}",
          year,
          month,
          exception.getMessage());
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(insuranceBillingService.getMonthlySubscription(period));
  }

  @RequestMapping(value = "{memberId}/monthlyBilling", method = RequestMethod.GET)
  public ResponseEntity<InsuranceBillingDTO> getMonthlyBilling(
      @PathVariable @Valid String memberId,
      @RequestParam(name = "year") int year,
      @RequestParam(name = "month") int month) {
    YearMonth period;
    try {
      period = YearMonth.of(year, month);
    } catch (DateTimeException exception) {
      log.error(
          "Failed to parse year: {} and month: {} to YearMonth type with the following error: {}",
          year,
          month,
          exception.getMessage());
      return ResponseEntity.badRequest().build();
    }

    Optional<InsuranceBillingDTO> insuranceBilling =
        insuranceBillingService.getMonthlySubscriptionByMemberId(period, memberId);

    if (!insuranceBilling.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(insuranceBilling.get());
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
    val perils = insuranceBillingService.getPerils(productType);

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
}
