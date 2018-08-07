package com.hedvig.productPricing.service.web;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.hedvig.productPricing.pricing.PricingResult;
import com.hedvig.productPricing.service.aggregates.PerilAggregate;
import com.hedvig.productPricing.service.aggregates.PerilStates;
import com.hedvig.productPricing.service.aggregates.Product;
import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.commands.AcceptQuoteCommand;
import com.hedvig.productPricing.service.commands.CalculateQuoteCommand;
import com.hedvig.productPricing.service.commands.CreateMemberCommandV2;
import com.hedvig.productPricing.service.commands.CreatePerilCommand;
import com.hedvig.productPricing.service.commands.CreateProductCommandV2;
import com.hedvig.productPricing.service.commands.UpdatePerilCommand;
import com.hedvig.productPricing.service.query.PerilEntity;
import com.hedvig.productPricing.service.query.PerilRepository;
import com.hedvig.productPricing.service.query.ProductEntity;
import com.hedvig.productPricing.service.query.ProductRepository;
import com.hedvig.productPricing.service.query.UserEntity;
import com.hedvig.productPricing.service.query.UserRepository;
import com.hedvig.productPricing.service.service.InsuranceBillingService;
import com.hedvig.productPricing.service.service.InsuranceService;
import com.hedvig.productPricing.service.serviceIntegration.botService.dto.CalculateQuoteRequest;
import com.hedvig.productPricing.service.web.dto.CategoryDTO;
import com.hedvig.productPricing.service.web.dto.InsuranceDTO;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import com.hedvig.productPricing.service.web.dto.ProductStatus;
import com.hedvig.productPricing.service.web.dto.ProductStatusDTO;
import java.net.URL;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.val;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductPricingController {

  private static Logger log = LoggerFactory.getLogger(ProductPricingController.class);
  private final ProductRepository productRepository;
  private final PerilRepository perilRepository;
  private final UserRepository userRepository;
  private final CommandGateway commandBus;
  private final InsuranceBillingService insuranceBillingService;
  private final AmazonS3Client s3Client;
  private final InsuranceService insuranceService;

  @Value("${hedvig.counterkey:123}")
  public String counterKey;

  @Value("${hedvig.s3.content.url:https://s3.eu-central-1.amazonaws.com/com-hedvig-web-content/}")
  public String baseWebImageURL;

  private String s3BaseURL = "https://s3.eu-central-1.amazonaws.com/com-hedvig-web-content/";

  @Autowired
  public ProductPricingController(
      CommandGateway commandBus,
      ProductRepository repository,
      PerilRepository perilRepository,
      UserRepository userRepository,
      InsuranceBillingService insuranceBillingService,
      AmazonS3Client s3Client,
      InsuranceService insuranceService) {
    this.commandBus = commandBus;
    this.productRepository = repository;
    this.userRepository = userRepository;
    this.perilRepository = perilRepository;
    this.insuranceBillingService = insuranceBillingService;
    this.s3Client = s3Client;
    this.insuranceService = insuranceService;
  }

  /**
   * Initates an update to an insurance
   *
   * @param insurance the updated insurance
   * @param hid hedvig token
   * @return InsuranceDTO updated with the new price
   */
  @RequestMapping(path = "/insurance/quote", method = RequestMethod.POST)
  public ResponseEntity<InsuranceDTO> getQuote(
      @RequestBody InsuranceDTO insurance,
      @RequestHeader(value = "hedvig.token", required = true) String hid) {

    log.info("Quote for user : " + hid);

    List<PerilDTO> perilsToUpdate = new ArrayList<>();
    for (CategoryDTO c : insurance.categories) {
      for (PerilDTO p : c.perils) {
        if (p.state.equals(PerilStates.ADD_REQUESTED.name())
            || p.state.equals(PerilStates.REMOVE_REQUESTED.name())) {
          perilsToUpdate.add(p);
        }
      }
    }

    commandBus.sendAndWait(new CalculateQuoteCommand(hid, perilsToUpdate));

    Optional<ProductEntity> p = insuranceService.GetCurrentInsurance(hid);

    if (p.isPresent()) {
      List<PerilEntity> perils = perilRepository.findByIdIn(p.get().perils.keySet());
      InsuranceDTO ins = new InsuranceDTO(p.get(), perils);
      return ResponseEntity.ok(ins);
    } else {
      log.error("No product found for user:" + hid);
      InsuranceDTO ins = new InsuranceDTO();
      ins.status = ProductStatus.PENDING;
      return ResponseEntity.ok(ins);
    }
  }

  /**
   * Accept current quote.
   *
   * @param memberId
   * @return What does this function do?
   */
  @RequestMapping(path = "/insurance/{memberId}/quoteAccepted", method = RequestMethod.POST)
  public ResponseEntity<?> quoteAccept(@PathVariable String memberId) {
    commandBus.sendAndWait(new AcceptQuoteCommand(memberId));

    return ResponseEntity.noContent().build();
  }

  /**
   * Create a product for specified member. TODO: Move this to an internal controller
   *
   * @param
   * @return 204 on success
   */
  @RequestMapping(
      path = "/createProduct",
      method = RequestMethod.POST,
      produces = "application/json")
  public ResponseEntity<?> createProduct(@RequestBody CalculateQuoteRequest userData) {
    // Safety check. TODO: Create a more complete userData integrity test
    if (userData == null || userData.getMemberId() == null) {
      return ResponseEntity.notFound().build();
    }

    MDC.put("memberId", userData.getMemberId());

    // Avoid duplicate products
    Optional<ProductEntity> p = insuranceService.GetCurrentInsurance(userData.getMemberId());

    if (p.isPresent()) {
      return ResponseEntity.ok().body(String.format("{\"id\":\"%s\"}", p.get().id));
    }

    log.info("Creating product for user:" + userData.getMemberId());
    log.info("userData.getHouseType():" + userData.getHouseType());
    log.info("userData.getStudent():" + userData.getStudent());

    // Find user or create a new one
    UserEntity user =
        userRepository
            .findById(userData.getMemberId())
            .orElseGet(
                () -> {
                  String id = commandBus.sendAndWait(new CreateMemberCommandV2(userData));
                  return userRepository.findOne(id);
                });

    List<PerilDTO> perils = this.insuranceBillingService.getPerils(userData.getHouseType());

    PricingResult pr =
        this.insuranceBillingService.getPricingResult(
            userData.getMemberId(),
            userData.getBirthDate(),
            userData.getLivingSpace().intValue(),
            userData.getPersonsInHouseHold(),
            userData.getAddress().getZipCode(),
            userData.getAddress().getFloor(),
            userData.getSafetyIncreasers(),
            userData.getHouseType(),
            userData.getStudent());

    Double currentTotalPrice = pr.getTotalPerMonth();

    UUID id =
        commandBus.sendAndWait(
            new CreateProductCommandV2(
                user.id,
                perils,
                currentTotalPrice,
                user.firstName,
                user.lastName,
                user.birthDate,
                userData.getStudent(),
                userData.getAddress(),
                userData.getLivingSpace(),
                userData.getHouseType(),
                userData.getCurrentInsurer(),
                userData.getPersonsInHouseHold(),
                userData.getSafetyIncreasers()));

    return ResponseEntity.ok().body(String.format("{\"id\":\"%s\"}", id));
  }

  @EventListener(ContextRefreshedEvent.class)
  public void generatePerils() {
    log.info("Generate perils");

    ArrayList<PerilDTO> mePerils = new ArrayList<PerilDTO>();

    PerilDTO me_legal =
        new PerilDTO(
            "ME.LEGAL",
            "Juridisk tvist",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "jag_juridisk_tvist%402x.png",
            "Om du hamnar i domstol så täcker Hedvig kostnaden för ditt ombud, och andra rättegångskostnader. Hedvig täcker också om någon skulle kräva dig på skadestånd för att du har skadat någon, eller någons saker.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    mePerils.add(me_legal);

    PerilDTO me_assault =
        new PerilDTO(
            "ME.ASSAULT",
            "Överfall",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "jag_overfall%402x.png",
            "En hemförsäkring skyddar även dig personligen. Om någon skulle utsätta dig för ett våldsbrott, till exempel misshandel eller rån ersätts du med ett fast belopp.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    mePerils.add(me_assault);

    // Bort
    /*
     * PerilDTO peril3 = new
     * PerilDTO("ME.IDENTITY","ID-kapning",PerilAggregate.perilStates.COVERED.
     * toString(), baseWebImageURL + "jag_id_kapning%402x.png", "Lorum",
     * "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m"
     * , false);
     */

    PerilDTO me_travel_sick =
        new PerilDTO(
            "ME.TRAVEL.SICK",
            "Sjuk på resa",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "jag_sjuk_pa_resa%402x.png",
            "Få saker är roligare än att utforska världen, men det är mindre kul om man blir sjuk. Eller ännu värre, råkar ut för en olycka. Därför ersätts du både för missade resdagar och sjukhuskostnader. Är det riktigt illa står Hedvig för transporten hem till Sverige. Om du har skadats eller blivit sjuk utomlands och behöver akut vård, ring Hedvig Global Assistance dygnet runt på +45 38 48 94 61.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    mePerils.add(me_travel_sick);

    // Bort
    /*
     * PerilDTO peril5 = new
     * PerilDTO("ME.TRAVEL.ACCIDENT","Olycka på resa",PerilAggregate.perilStates.
     * COVERED.toString(), baseWebImageURL + "jag_olycka_pa_resa%402x.png",
     * "Om du skulle vara med om en olycka och hamna på sjukhus så ersätter Hedvig vårdkostnaderna. Om det är allvarligt så ordnar vi med hemtransport till Sverige\nOm något skulle hända på hemmaplan så att du måste åka hem ersätter Hedvig det också."
     * ,
     * "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m"
     * , false); mePerils.add(peril5);
     */

    // Bort
    /*
     * PerilDTO peril6 = new
     * PerilDTO("ME.TRAVEL.DELAY","Försenad resa",PerilAggregate.perilStates.COVERED
     * .toString(), baseWebImageURL + "jag_forsenad_resa%402x.png",
     * "Om du kommer för sent till en resa ersätter Hedvig dig så att du kan ta dig dit du skulle\nDu får även ersättning om du under en resas gång blir försenad till en plats du var på väg till, och går miste om något kul du hade tänkt göra."
     * ,
     * "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m"
     * , false); mePerils.add(peril6);
     */

    PerilDTO me_travel_luggage_delay =
        new PerilDTO(
            "ME.TRAVEL.LUGGAGE.DELAY",
            "Rese-\ntrubbel",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "jag_forsenat_bagage%402x.png",
            "Ibland klaffar inte allt som det ska när du ska ut i världen. Till exempel, om ditt bagage blir försenat ersätter Hedvig dig för att köpa saker du behöver. Och om det skulle bli oroligheter i landet du är i, som vid en naturkatastrof, så evakuerar Hedvig dig hem till Sverige.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    mePerils.add(me_travel_luggage_delay);

    for (PerilDTO p : mePerils) p.category = "ME";

    // -------------------------

    ArrayList<PerilDTO> housePerils = new ArrayList<PerilDTO>();

    PerilDTO house_brf_fire =
        new PerilDTO(
            "HOUSE.BRF.FIRE",
            "Eldsvåda",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_eldsvada%402x.png",
            "En överhettad mobilladdare eller ett misslyckat försök att fritera pommes frites, bränder uppstår på de mest vardagliga vis. Om det börjar brinna i din lägenhet får du ersättning för brand- och rökskador.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_brf_fire);

    PerilDTO house_brf_water =
        new PerilDTO(
            "HOUSE.BRF.WATER",
            "Vatten-\nläcka",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_vattenlacka%402x.png",
            "Din diskmaskin går sönder och du kommer hem till en swimmingpool i köket och blöta grannar våningen under. Råkar du ut för en vattenläcka får du ersättning för skadorna.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_brf_water);

    PerilDTO house_brf_weather =
        new PerilDTO(
            "HOUSE.BRF.WEATHER",
            "Oväder",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_ovader%402x.png",
            "En storm kan vara mäktig så länge den håller sig utanför fönstret. När åskan tar kol på din nya TV och skyfallet letar sig in och svämmar över ditt källarförråd är det tur att du har Hedvig. Du ersätts om ett oväder orsakat skador.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_brf_weather);

    // Rental customers ----------
    PerilDTO house_rent_fire =
        new PerilDTO(
            "HOUSE.RENT.FIRE",
            "Eldsvåda",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_eldsvada%402x.png",
            "Du bor i en hyresrätt, det betyder att det är din hyresvärds försäkring som täcker skador på lägenheten som orsakas av en eldsvåda. Men oroa dig inte, om dina prylar skadas av elden ersätter Hedvig så klart det.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_rent_fire);

    PerilDTO house_rent_water =
        new PerilDTO(
            "HOUSE.RENT.WATER",
            "Vatten-\nläcka",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_vattenlacka%402x.png",
            "Du bor i en hyresrätt, det betyder att det är din hyresvärds försäkring som täcker skador på lägenheten som orsakas av en vattenläcka. Men oroa dig inte, om dina prylar skadas av vattnet ersätter Hedvig så klart det.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_rent_water);

    PerilDTO house_rent_weather =
        new PerilDTO(
            "HOUSE.RENT.WEATHER",
            "Oväder",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_ovader%402x.png",
            "Du bor i en hyresrätt, det betyder att det är din hyresvärds försäkring som täcker skador på lägenheten som orsakas av ett oväder. Men oroa dig inte, om dina prylar skadas av ovädret ersätter Hedvig så klart det.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_rent_weather);

    // Sublet rental customers ----------
    PerilDTO house_sublet_rent_fire =
        new PerilDTO(
            "HOUSE.SUBLET.RENT.FIRE",
            "Eldsvåda",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_eldsvada%402x.png",
            "Du bor i en hyresrätt, det betyder att det är fastighetens försäkring som täcker skador på lägenheten som orsakas av en eldsvåda. Men oroa dig inte, om sakerna som finns i lägenheten skadas av elden ersätter Hedvig så klart det.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_sublet_rent_fire);

    PerilDTO house_sublet_rent_water =
        new PerilDTO(
            "HOUSE.SUBLET.RENT.WATER",
            "Vatten-\nläcka",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_vattenlacka%402x.png",
            "Du bor i en hyresrätt, det betyder att det är fastighetens försäkring som täcker skador på lägenheten som orsakas av en vattenläcka. Men oroa dig inte, om sakerna som finns i lägenheten skadas av vattnet ersätter Hedvig så klart det.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_sublet_rent_water);

    PerilDTO house_sublet_rent_weather =
        new PerilDTO(
            "HOUSE.SUBLET.RENT.WEATHER",
            "Oväder",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_ovader%402x.png",
            "Du bor i en hyresrätt, det betyder att det är fastighetens försäkring som täcker skador på lägenheten som orsakas av ett oväder. Men oroa dig inte, om sakerna som finns i lägenheten skadas av ovädret ersätter Hedvig så klart det.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_sublet_rent_weather);

    PerilDTO house_sublet_rent_appliances =
        new PerilDTO(
            "HOUSE.SUBLET.RENT.APPLIANCES",
            "Vitvaror",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_vitvaror%402x.png",
            "Plötsligt tackar din spis för sig eller så blir det kortslutning i din prisbelönta kaffemaskin. Hedvig ersätter dig för att reparera eller ersätta dem.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_sublet_rent_appliances);

    PerilDTO house_sublet_brf_appliances =
        new PerilDTO(
            "HOUSE.SUBLET.BRF.APPLIANCES",
            "Vitvaror",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_vitvaror%402x.png",
            "Hedvig ersätter skador på vitvaror som din hyresvärd inte är skyldig att ersätta",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_sublet_brf_appliances);

    // Sublet BRF customers ----------
    PerilDTO house_sublet_brf_fire =
        new PerilDTO(
            "HOUSE.SUBLET.BRF.FIRE",
            "Eldsvåda",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_eldsvada%402x.png",
            "En överhettad mobilladdare eller ett misslyckat försök att fritera pommes frites, bränder uppstår på olika sätt. Om det börjar brinna i din lägenhet så får du ersättning för brand- och rökskador.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_sublet_brf_fire);

    PerilDTO house_sublet_brf_water =
        new PerilDTO(
            "HOUSE.SUBLET.BRF.WATER",
            "Vatten-\nläcka",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_vattenlacka%402x.png",
            "Din diskmaskin går sönder och du kommer hem till en swimmingpool i köket och blöta grannar våningen under. Råkar du ut för en vattenläcka får du ersättning för skadorna.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_sublet_brf_water);

    PerilDTO house_sublet_brf_weather =
        new PerilDTO(
            "HOUSE.SUBLET.BRF.WEATHER",
            "Oväder",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_ovader%402x.png",
            "Om åskan tar kol på din nya TV och skyfallet letar sig in och svämmar över ditt källarförråd är det tur att du har Hedvig. Du ersätts om ett oväder orsakat skador.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_sublet_brf_weather);

    PerilDTO house_breakin =
        new PerilDTO(
            "HOUSE.BREAK-IN",
            "Inbrott",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_inbrott%402x.png",
            "Ditt hem är din borg. Skulle inkräktare bryta sig in för att stjäla dina saker så ersätter Hedvig dig för det.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_breakin);

    PerilDTO house_damage =
        new PerilDTO(
            "HOUSE.DAMAGE",
            "Skade-\ngörelse",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_skadegorelse%402x.png",
            "Om någon bryter sig in i din lägenhet för att vandalisera och förstöra så ersätter Hedvig dig för skadorna.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_damage);

    PerilDTO house_brf_appliances =
        new PerilDTO(
            "HOUSE.BRF.APPLIANCES",
            "Vitvaror",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_vitvaror%402x.png",
            "Livet blir utan tvekan lättare med hushållsmaskiner, men ibland lever de sina egna liv. Plötsligt tackar din spis för sig eller så blir det kortslutning i din prisbelönta kaffemaskin. Hedvig ersätter dig för att reparera eller ersätta dem.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_brf_appliances);

    PerilDTO house_rent_appliances =
        new PerilDTO(
            "HOUSE.RENT.APPLIANCES",
            "Vitvaror",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "lagenhet_vitvaror%402x.png",
            "Plötsligt tackar din spis för sig eller så blir det kortslutning i din prisbelönta kaffemaskin. Hedvig ersätter skador på dina vitvaror, så länge det inte rör sig om skador som din hyresvärd är skyldig att ersätta.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    housePerils.add(house_rent_appliances);

    // Bort
    /*
     * PerilDTO peril14 = new
     * PerilDTO("HOUSE.BUGS","Skadedjur",PerilAggregate.perilStates.COVERED.toString
     * (), baseWebImageURL + "lagenhet_skadedjur%402x.png", "Lorum",
     * "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m"
     * , false); housePerils.add(peril14);
     */

    for (PerilDTO p : housePerils) p.category = "HOUSE";
    // ---------------------------------

    ArrayList<PerilDTO> stuffPerils = new ArrayList<PerilDTO>();

    PerilDTO stuff_careless =
        new PerilDTO(
            "STUFF.CARELESS",
            "Drulle",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_drulle%402x.png",
            "De flesta känner igen känslan av slow-motion när mobilen glider ur handen och voltar ner mot kall asfalt. 'Drulle' kallas ibland för otursförsäkring, och det är just vad det är. Om du har otur och dina prylar går sönder, så ersätts du för dem.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_careless);

    PerilDTO stuff_theft =
        new PerilDTO(
            "STUFF.THEFT",
            "Stöld",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_stold%402x.png",
            "Prylar är till för att användas, i synnerhet favoriterna. Älskade väskor och jackor följer med på restaurang, datorn får komma med på kafé, plånboken och cykeln är med överallt. Om något stjäls av dig så ersätts du för det.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_theft);

    PerilDTO stuff_damage =
        new PerilDTO(
            "STUFF.DAMAGE",
            "Skade-\ngörelse",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_skadegorelse%402x.png",
            "Varför vissa väljer att förstöra andras saker är en gåta. Hursomhelst så ersätts du när dina prylar förstörs av skadegörelse, eller i samband med att du blir överfallen.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_damage);

    PerilDTO stuff_brf_fire =
        new PerilDTO(
            "STUFF.BRF.FIRE",
            "Eldsvåda",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_eldsvada%402x.png",
            "Om det skulle brinna så ersätter Hedvig utöver skador på din lägenhet även alla prylar som blir förstörda.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_brf_fire);

    PerilDTO stuff_rent_fire =
        new PerilDTO(
            "STUFF.RENT.FIRE",
            "Eldsvåda",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_eldsvada%402x.png",
            "Om det skulle brinna i din lägenhet så ersätter Hedvig dina prylar som blir förstörda.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_rent_fire);

    PerilDTO stuff_sublet_rent_fire =
        new PerilDTO(
            "STUFF.SUBLET.RENT.FIRE",
            "Eldsvåda",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_eldsvada%402x.png",
            "Om det skulle brinna så ersätter Hedvig prylarna som blir förstörda.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_sublet_rent_fire);

    PerilDTO stuff_sublet_brf_fire =
        new PerilDTO(
            "STUFF.SUBLET.BRF.FIRE",
            "Eldsvåda",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_eldsvada%402x.png",
            "Om det skulle brinna så ersätter Hedvig utöver skador på lägenheten även dina prylar som blir förstörda.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_sublet_brf_fire);

    PerilDTO stuff_brf_water =
        new PerilDTO(
            "STUFF.BRF.WATER",
            "Vatten-\nläcka",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_vattenlacka%402x.png",
            "Om du har en vattenläcka hemma så ersätter Hedvig utöver skador på din lägenhet även alla prylar som blir förstörda.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_brf_water);

    PerilDTO stuff_rent_water =
        new PerilDTO(
            "STUFF.RENT.WATER",
            "Vatten-\nläcka",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_vattenlacka%402x.png",
            "Om du har en vattenläcka hemma så ersätter Hedvig dina prylar som blir förstörda.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_rent_water);

    PerilDTO stuff_sublet_rent_water =
        new PerilDTO(
            "STUFF.SUBLET.RENT.WATER",
            "Vatten-\nläcka",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_vattenlacka%402x.png",
            "Om du har en vattenläcka hemma så ersätter Hedvig prylarna som blir förstörda.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_sublet_rent_water);

    PerilDTO stuff_sublet_brf_water =
        new PerilDTO(
            "STUFF.SUBLET.BRF.WATER",
            "Vatten-\nläcka",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_vattenlacka%402x.png",
            "Om du har en vattenläcka hemma så ersätter Hedvig utöver skador på lägenheten även dina prylar som blir förstörda.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_sublet_brf_water);

    PerilDTO stuff_brf_weather =
        new PerilDTO(
            "STUFF.BRF.WEATHER",
            "Oväder",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_ovader%402x.png",
            "Hedvig ersätter dig om ett oväder på något sätt skulle orsaka skador på dina prylar, utöver skador på din lägenhet.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_brf_weather);

    PerilDTO stuff_rent_weather =
        new PerilDTO(
            "STUFF.RENT.WEATHER",
            "Oväder",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_ovader%402x.png",
            "Hedvig ersätter dig om ett oväder på något sätt skulle orsaka skador på dina prylar.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_rent_weather);

    PerilDTO stuff_sublet_rent_weather =
        new PerilDTO(
            "STUFF.SUBLET.RENT.WEATHER",
            "Oväder",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_ovader%402x.png",
            "Hedvig ersätter dig om ett oväder på något sätt skulle orsaka skador på försäkrade prylar.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_sublet_rent_weather);

    PerilDTO stuff_sublet_brf_weather =
        new PerilDTO(
            "STUFF.SUBLET.BRF.WEATHER",
            "Oväder",
            PerilAggregate.perilStates.COVERED.toString(),
            baseWebImageURL + "prylar_ovader%402x.png",
            "Hedvig ersätter dig om ett oväder på något sätt skulle orsaka skador på dina prylar, utöver skador på lägenheten.",
            "LLorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
            false);
    stuffPerils.add(stuff_sublet_brf_weather);

    for (PerilDTO p : stuffPerils) p.category = "STUFF";

    // ------------------- //

    for (PerilDTO p : mePerils) {
      createOrUpdatePeril(p);
    }
    for (PerilDTO p : housePerils) {
      createOrUpdatePeril(p);
    }
    for (PerilDTO p : stuffPerils) {
      createOrUpdatePeril(p);
    }
  }

  private void createOrUpdatePeril(PerilDTO p) {
    try {
      commandBus.sendAndWait(new CreatePerilCommand(p));
    } catch (org.axonframework.commandhandling.model.ConcurrencyException e) {
      try {
        log.info("Peril " + p.id + " exists...");
        commandBus.sendAndWait(new UpdatePerilCommand(p));
      } catch (Exception ex) {
        log.error(ex.getMessage());
      }
    }
  }

  @RequestMapping(path = "/insurance", method = RequestMethod.GET)
  public ResponseEntity<InsuranceDTO> getInsurance(
      @RequestHeader(value = "hedvig.token", required = true) String hid) {

    log.info("Get insurance product for user: " + hid);

    Optional<ProductEntity> p = insuranceService.GetCurrentInsurance(hid);

    val u = userRepository.findById(hid);

    if (p.isPresent() && u.isPresent()) {
      ProductEntity product = p.get();
      val user = u.get();
      List<PerilEntity> perils = perilRepository.findByIdIn(product.perils.keySet());
      InsuranceDTO ins = new InsuranceDTO(product, perils, user);

      if (Objects.equals(product.state, ProductStates.QUOTE)) {
        ins.newTotalPrice = ins.currentTotalPrice;
      } else if (ins.newTotalPrice != null
          && Math.abs(ins.newTotalPrice - ins.currentTotalPrice) < 0.001d) {
        ins.newTotalPrice = null;
      }

      ins.policyUrl = selectPolicy(product.houseType);
      ins.presaleInformationUrl = selectPresaleInformation(product.houseType);

      if (product.certificateBucket != null && product.certificateKey != null) {
        ins.certificateUrl = generateCertificateURL(product, ins);
      } else {
        ins.certificateUrl = null;
      }

      return ResponseEntity.ok(ins);
    } else {
      InsuranceDTO insurance = new InsuranceDTO();
      insurance.status = ProductStatus.PENDING;
      return ResponseEntity.ok(insurance);
    }
  }

  // This is used in memberSrevice.
  // Showing the status of the inusrance
  // and the safetyincreasers in the second tab of the APP
  @RequestMapping(value = "/insurance/{memberId}/insuranceStatus", method = RequestMethod.GET)
  public ResponseEntity<ProductStatusDTO> getInsuranceStatus(@PathVariable String memberId) {

    UserEntity ue = userRepository.findOne(memberId);

    if (ue == null) {
      return ResponseEntity.notFound().build();
    }

    Optional<ProductEntity> product = insuranceService.GetCurrentInsurance(memberId);

    if (!product.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    ProductEntity p = product.get();
    List<String> safetyIncreasers = new ArrayList<>();

    if (p.goodToHaveItems != null && p.goodToHaveItems.size() > 0) {
      java.util.Collections.sort(p.goodToHaveItems);
      safetyIncreasers = p.goodToHaveItems;
    }
    if (p.member.goodToHaveItems != null && p.member.goodToHaveItems.size() > 0) {
      java.util.Collections.sort(p.member.goodToHaveItems);
      safetyIncreasers = p.member.goodToHaveItems;
    }

    String productStatus =
        ProductStatus.createStatus(Clock.systemDefaultZone(), p.state, p.activeFrom, p.activeTo)
            .getStatus()
            .toString();

    return ResponseEntity.ok(new ProductStatusDTO(safetyIncreasers, productStatus));
  }

  public String generateCertificateURL(ProductEntity product, InsuranceDTO ins) {
    GeneratePresignedUrlRequest req =
        new GeneratePresignedUrlRequest(product.certificateBucket, product.certificateKey);
    req.withExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)));
    req.withMethod(HttpMethod.GET);

    final URL url = s3Client.generatePresignedUrl(req);

    return url.toString();
  }

  private String selectPresaleInformation(Product.ProductTypes houseType) {

    switch (houseType) {
      case RENT:
        return s3BaseURL + "F%C3%B6rk%C3%B6psinformation+-+Hyresr%C3%A4tt+(Februari+2018).pdf";
      case BRF:
        return s3BaseURL + "F%C3%B6rk%C3%B6psinformation+-+Bostadsr%C3%A4tt+(Februari+2018).pdf";
      case SUBLET_BRF:
        return s3BaseURL + "F%C3%B6rk%C3%B6psinformation+-+Andrahandshyrare+(Februari+2018).pdf";
      case SUBLET_RENTAL:
        return s3BaseURL + "F%C3%B6rk%C3%B6psinformation+-+Andrahandshyrare+(Februari+2018).pdf";
      case STUDENT_RENT:
        return s3BaseURL + "F%C3%B6rk%C3%B6psinformation+Student+-+Hyresr%C3%A4tt.pdf";
      case STUDENT_BRF:
        return s3BaseURL + "F%C3%B6rk%C3%B6psinformation+Student+-+Bostadsr%C3%A4tt.pdf";
    }

    log.error("Could not generate presale information url for productType: {}", houseType);
    return s3BaseURL;
  }

  private String selectPolicy(Product.ProductTypes houseType) {
    switch (houseType) {
      case RENT:
        return s3BaseURL + "F%C3%B6rs%C3%A4kringsvillkor+-+Hyresr%C3%A4tt+(Februari+2018).pdf";
      case BRF:
        return s3BaseURL + "F%C3%B6rs%C3%A4kringsvillkor+-+Bostadsr%C3%A4tt+(Februari+2018).pdf";
      case SUBLET_BRF:
        return s3BaseURL + "F%C3%B6rs%C3%A4kringsvillkor+-+Andrahandshyrare+(Februari+2018).pdf";
      case SUBLET_RENTAL:
        return s3BaseURL + "F%C3%B6rs%C3%A4kringsvillkor+-+Andrahandshyrare+(Februari+2018).pdf";
      case STUDENT_RENT:
        return s3BaseURL + "F%C3%B6rs%C3%A4kringsvillkor+Student+-+Hyresr%C3%A4tt.pdf";
      case STUDENT_BRF:
        return s3BaseURL + "F%C3%B6rs%C3%A4kringsvillkor+Student+-+Bostadsr%C3%A4tt.pdf";
    }

    log.error("Could not generate policyUrl for productType: {}", houseType);
    return s3BaseURL;
  }
}
