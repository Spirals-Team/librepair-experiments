package com.hedvig.productPricing.service.service;

import com.hedvig.productPricing.pricing.PricingEngine;
import com.hedvig.productPricing.pricing.PricingQueryBase;
import com.hedvig.productPricing.pricing.PricingQuote;
import com.hedvig.productPricing.pricing.PricingResult;
import com.hedvig.productPricing.service.aggregates.Product;
import com.hedvig.productPricing.service.aggregates.Product.ProductTypes;
import com.hedvig.productPricing.service.query.PerilEntity;
import com.hedvig.productPricing.service.query.PerilRepository;
import com.hedvig.productPricing.service.query.PricingRepository;
import com.hedvig.productPricing.service.query.ProductEntity;
import com.hedvig.productPricing.service.query.ProductRepository;
import com.hedvig.productPricing.service.web.dto.CategoryDTO;
import com.hedvig.productPricing.service.web.dto.InsuranceBillingDTO;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import com.hedvig.productPricing.service.web.dto.SafetyIncreaserType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InsuranceBillingServiceImpl implements InsuranceBillingService {

  private Logger log = LoggerFactory.getLogger(InsuranceBillingServiceImpl.class);

  private final ProductRepository productRepository;
  private final PricingEngine pricingEngine;
  private final PricingRepository pricingRepository;
  private final PerilRepository perilRepository;

  public InsuranceBillingServiceImpl(
      ProductRepository productRepository,
      PricingEngine pricingEngine,
      PricingRepository pricingRepository,
      PerilRepository perilRepository) {
    this.productRepository = productRepository;
    this.pricingEngine = pricingEngine;
    this.pricingRepository = pricingRepository;
    this.perilRepository = perilRepository;
  }

  @Override
  public PricingResult getPricingResult(
      String memberId,
      LocalDate birthDate,
      int livingSpace,
      int householdSize,
      String zipCode,
      int floor,
      List<SafetyIncreaserType> safetyIncreasers,
      ProductTypes houseType,
      boolean isStudent) {
    log.info("Price calculation for user:" + memberId);

    boolean BRF =
        Objects.equals(houseType, Product.ProductTypes.BRF)
            || Objects.equals(houseType, Product.ProductTypes.STUDENT_BRF);

    boolean sublet =
        Objects.equals(houseType, Product.ProductTypes.SUBLET_BRF)
            || Objects.equals(houseType, Product.ProductTypes.SUBLET_RENTAL); // Andra hand

    boolean student = isStudent;

    boolean isStudentPolicy =
        Objects.equals(houseType, Product.ProductTypes.STUDENT_BRF)
            || Objects.equals(houseType, Product.ProductTypes.STUDENT_RENT);

    // Price calculation:
    PricingQueryBase pq = new PricingQueryBase();
    pq.setAlder(calculateAge(birthDate));
    pq.setBoyta(livingSpace);
    if (isStudentPolicy) {
      pq.setFbelopp(250000);
    } else {
      pq.setFbelopp(1000000);
    }
    pq.setAntper(householdSize);
    pq.setGeografi(zipCode);
    pq.setVaning(floor);
    pq.setDuration(0); // Only "tillsvidare"
    pq.setSjrisk(1500);
    pq.setHyrdeg(sublet);
    pq.setNyteckning(false);
    pq.setLarm(safetyIncreasers.contains(SafetyIncreaserType.BURGLAR_ALARM));
    pq.setSakerhetsdorr(safetyIncreasers.contains(SafetyIncreaserType.SAFETY_DOOR));
    pq.setBetalningsanmarkning(false);
    pq.setPaymentType("");
    pq.setBRF(BRF);
    pq.setStudent(student);
    pq.setStudentPolicy(isStudentPolicy);

    PricingQuote quote = new PricingQuote();
    quote.setUserId(memberId);
    quote.setPricingQuery(pq);

    log.info("Ingoing query:\n" + pq);
    if (!pricingEngine.isStartupComplete()) {
      throw new RuntimeException("Pricing engine is not setup. Cannot calculate price");
    }
    pricingEngine.getPrice(quote);
    // insurance.currentTotalPrice = new PricingEngine(user, insurance).getPrice();

    PricingResult pr = quote.getPricingResult(PricingResult.ResultTypes.TOTAL);
    log.info("Outgoing price:" + pr);
    pricingRepository.saveAndFlush(quote);

    if (pr == null) {
      log.error("Could not calculate price");
      throw new RuntimeException("PricingEnginge returned null. Could not calculate price");
      // return ResponseEntity.state(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return pr;
  }

  @Override
  public List<PerilDTO> getPerils(ProductTypes houseType) {
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

    switch (houseType) {
      case STUDENT_BRF:
      case BRF:
        housePerils.add(perilMap.get("HOUSE.BRF.WATER"));
        housePerils.add(perilMap.get("HOUSE.BRF.WEATHER"));
        housePerils.add(perilMap.get("HOUSE.BRF.FIRE"));
        housePerils.add(perilMap.get("HOUSE.BRF.APPLIANCES"));
        stuffPerils.add(perilMap.get("STUFF.BRF.FIRE"));
        stuffPerils.add(perilMap.get("STUFF.BRF.WATER"));
        stuffPerils.add(perilMap.get("STUFF.BRF.WEATHER"));
        break;
      case STUDENT_RENT:
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
    return perils;
  }

  @Override
  public List<InsuranceBillingDTO> getMonthlySubscription(YearMonth period) {

    List<ProductEntity> billingList =
        productRepository.findBillingByDate(period.getMonthValue(), period.getYear());

    List<InsuranceBillingInfo> filterdList =
        billingList.stream().map(InsuranceBillingInfo::new).collect(Collectors.toList());

    filterdList.forEach(x -> x.setPrice(x.CalculatePricePerMonth(period)));

    return GetListGroupedByMemberId(filterdList);
  }

  @Override
  public Optional<InsuranceBillingDTO> getMonthlySubscriptionByMemberId(
      YearMonth period, String memberId) {

    List<ProductEntity> billingList =
        productRepository.findBillingByDateByMemberId(
            period.getMonthValue(), period.getYear(), memberId);

    if (billingList.size() == 0) {
      return Optional.empty();
    }

    List<InsuranceBillingInfo> filterdList =
        billingList.stream().map(InsuranceBillingInfo::new).collect(Collectors.toList());

    filterdList.forEach(x -> x.setPrice(x.CalculatePricePerMonth(period)));

    return GetInsuranceBillingByMemberId(filterdList);
  }

  private List<InsuranceBillingDTO> GetListGroupedByMemberId(List<InsuranceBillingInfo> list) {
    return list.stream()
        .filter(x -> x.getPrice().compareTo(BigDecimal.ZERO) > 0)
        .collect(
            Collectors.groupingBy(
                InsuranceBillingInfo::getMemberId,
                Collectors.summingLong(x -> x.price.longValueExact())))
        .entrySet()
        .stream()
        .map(InsuranceBillingDTO::new)
        .collect(Collectors.toList());
  }

  private Optional<InsuranceBillingDTO> GetInsuranceBillingByMemberId(
      List<InsuranceBillingInfo> billingInfoList) {

    if (billingInfoList.size() == 1) {
      return Optional.of(new InsuranceBillingDTO(billingInfoList.get(0)));
    }

    return billingInfoList
        .stream()
        .filter(x -> x.getPrice().compareTo(BigDecimal.ZERO) >= 0)
        .collect(
            Collectors.groupingBy(
                InsuranceBillingInfo::getMemberId,
                Collectors.summingLong(x -> x.price.longValueExact())))
        .entrySet()
        .stream()
        .map(InsuranceBillingDTO::new)
        .findAny();
  }

  private int calculateAge(LocalDate birthDate) {
    LocalDate currentDate = LocalDate.now();
    if ((birthDate != null) && (currentDate != null)) {
      return Period.between(birthDate, currentDate).getYears();
    } else {
      return 0;
    }
  }
}
