package com.hedvig.productPricing.service.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.hedvig.productPricing.pricing.PricingEngine;
import com.hedvig.productPricing.service.query.PerilRepository;
import com.hedvig.productPricing.service.query.PricingRepository;
import com.hedvig.productPricing.service.query.ProductEntity;
import com.hedvig.productPricing.service.query.ProductRepository;
import com.hedvig.productPricing.service.query.UserEntity;
import com.hedvig.productPricing.testHelpers.Members;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.val;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;;

@RunWith(SpringRunner.class)
public class InsuranceBillingServiceImplTest {

  public static final String MEMBERID = Members.TOLVAN.getMemberId();
  public static final String MEMBERID2 = "456";

  @MockBean private ProductRepository productRepository;

  @MockBean private InsuranceBillingService insuranceBillingService;

  @MockBean private PricingEngine pricingEngine;

  @MockBean private PricingRepository pricingRepository;

  @MockBean private PerilRepository perilRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    insuranceBillingService =
        new InsuranceBillingServiceImpl(
            productRepository, pricingEngine, pricingRepository, perilRepository);
  }

  @Test
  public void
      GetMonthlySubscription_Should_ReturnInsuranceBilling_WhenHaveMultipleInsuranceForMultipleMembers() {

    // Given that we have active insurances
    List<ProductEntity> products = new ArrayList<>();
    products.addAll(GetListOfProductEntityStubs(100));

    Mockito.when(productRepository.findBillingByDate(Mockito.anyInt(), Mockito.anyInt()))
        .thenReturn(products);

    // When we fetch the insurances for a specific period
    val list = insuranceBillingService.getMonthlySubscription(YearMonth.now());

    // Then the calculated for this period should be returned
    assertThat(list).isNotEmpty();
  }

  @Test
  public void
      GetMonthlySubscription_Should_NotReturnInsuranceBilling_WhenInsuranceWithTheSameActivationAndCancellationDay() {

    // Given that we have inactive insurances within the same day
    // We will avoid to charge customer with modified insurances
    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().atDay(1).atStartOfDay();
    p.activeTo = YearMonth.now().atDay(1).atStartOfDay();
    p.currentTotalPrice = BigDecimal.valueOf(309);

    List<ProductEntity> products = new ArrayList<>();
    products.add(p);

    Mockito.when(productRepository.findBillingByDate(Mockito.anyInt(), Mockito.anyInt()))
        .thenReturn(products);

    // When we fetch the insurances for a specific period
    val list = insuranceBillingService.getMonthlySubscription(YearMonth.now());

    // Then the calculated for this period should be empty
    assertThat(list).isEmpty();
  }

  @Test
  public void
      GetMonthlySubscription_Should_ReturnOneInsuranceBilling_WhenMemberWithMultipleActiveInsurances() {

    // Given that we have multiple insurances for the same member

    List<ProductEntity> stubs = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      ProductEntity p = new ProductEntity();
      p.member = new UserEntity();
      p.member.id = MEMBERID;
      p.activeFrom = YearMonth.now().atDay(1).atStartOfDay();
      p.activeTo = null;
      p.currentTotalPrice = BigDecimal.TEN;
      stubs.add(p);
    }

    int theDayNumberInTheMiddleOfTheMonth = YearMonth.now().lengthOfMonth() / 2;

    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().atDay(theDayNumberInTheMiddleOfTheMonth).atStartOfDay();
    p.activeTo = null;
    p.currentTotalPrice = BigDecimal.TEN;
    stubs.add(p);

    Mockito.when(productRepository.findBillingByDate(Mockito.anyInt(), Mockito.anyInt()))
        .thenReturn(stubs);

    // When we fetch the insurances for a specific member and period
    val list = insuranceBillingService.getMonthlySubscription(YearMonth.now());

    // Then the total price for this member should be returned
    assertThat(list.size() == 1).isTrue();
    assertThat(list.get(0).getSubscription().equals(Money.of(45, "SEK"))).isTrue();
  }

  @Test
  public void
      GetMonthlySubscription_Should_ReturnInsuranceBillingForOnlyOneDay_WhenMemberWithInsuranceTheLastDayOfTheMonth() {

    // Given that we have active insurances
    List<ProductEntity> stubs = new ArrayList<>();
    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().atEndOfMonth().atStartOfDay();
    p.activeTo = null;
    p.currentTotalPrice = BigDecimal.valueOf(200L);

    stubs.add(p);

    Mockito.when(productRepository.findBillingByDate(Mockito.anyInt(), Mockito.anyInt()))
        .thenReturn(stubs);

    // When we fetch the insurances for a specific period
    val list = insuranceBillingService.getMonthlySubscription(YearMonth.now());

    // Then the price for the customer should not be zero
    assertThat(list.size() == 1);
    assertThat(list.get(0).getSubscription().isZero()).isFalse();
    assertThat(list.get(0).getSubscription().isEqualTo(Money.of(200, "SEK"))).isFalse();
  }

  @Test
  public void
      GetMonthlySubscription_Should_NotReturnInsuranceBilling_WhenMemberWithInsuranceWtihFutureActiveFrom() {

    // Given that we have active insurances
    List<ProductEntity> stubs = new ArrayList<>();
    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().plusMonths(5).atDay(1).atStartOfDay();
    p.activeTo = null;
    p.currentTotalPrice = BigDecimal.valueOf(200L);

    stubs.add(p);

    Mockito.when(productRepository.findBillingByDate(Mockito.anyInt(), Mockito.anyInt()))
        .thenReturn(stubs);

    // When we fetch the insurances for a specific period
    val list = insuranceBillingService.getMonthlySubscription(YearMonth.now());

    // Then the price for the customer should not be zero
    assertThat(list.size() == 0).isTrue();
  }

  @Test
  public void
      GetMonthlySubscription_Should_NotReturnInsuranceBilling_WhenMemberWithTerminatedInsurance() {

    // Given that we have active insurances
    List<ProductEntity> stubs = new ArrayList<>();
    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().minusMonths(5).atDay(1).atStartOfDay();
    p.activeTo = YearMonth.now().atDay(1).minusDays(1).atStartOfDay();
    p.currentTotalPrice = BigDecimal.valueOf(200L);

    stubs.add(p);

    Mockito.when(productRepository.findBillingByDate(Mockito.anyInt(), Mockito.anyInt()))
        .thenReturn(stubs);

    // When we fetch the insurances for a specific period
    val list = insuranceBillingService.getMonthlySubscription(YearMonth.now());

    // Then the price for the customer should not be zero
    assertThat(list.size() == 0).isTrue();
  }

  @Test
  public void
      GetMonthlySubscription_Should_ReturnInsuranceBillingForOnly4Days_WhenInsuranceWithOnly4Days() {

    // Given that we have active insurances
    List<ProductEntity> stubs = new ArrayList<>();
    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().minusMonths(1).atDay(1).atStartOfDay();
    p.activeTo = YearMonth.now().atDay(1).plusDays(4).atStartOfDay();
    p.currentTotalPrice = BigDecimal.valueOf(99L);

    stubs.add(p);

    ProductEntity t = new ProductEntity();
    t.member = new UserEntity();
    t.member.id = MEMBERID2;
    t.activeFrom = YearMonth.now().atDay(1).atStartOfDay();
    t.activeTo = YearMonth.now().atDay(1).plusDays(4).atStartOfDay();
    t.currentTotalPrice = BigDecimal.valueOf(99L);

    stubs.add(t);

    Mockito.when(productRepository.findBillingByDate(Mockito.anyInt(), Mockito.anyInt()))
        .thenReturn(stubs);

    // When we fetch the insurances for a specific period
    val list = insuranceBillingService.getMonthlySubscription(YearMonth.now());

    // Then the price for the customer should not be zero
    assertThat(list.size() == 2).isTrue();
    assertThat(list.get(0).getSubscription().equals(Money.of(16, "SEK"))).isTrue();
    assertThat(list.get(1).getSubscription().equals(Money.of(16, "SEK"))).isTrue();
  }

  @Test
  public void
      GetMonthlySubscription_Should_NotReturnInsuranceBilling_WhenInsuranceActiveToIsBeforeActiveFrom() {

    // Given that we have active insurances
    List<ProductEntity> stubs = new ArrayList<>();
    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().atDay(10).atStartOfDay();
    p.activeTo = YearMonth.now().atDay(1).atStartOfDay();
    p.currentTotalPrice = BigDecimal.valueOf(99L);

    stubs.add(p);

    Mockito.when(productRepository.findBillingByDate(Mockito.anyInt(), Mockito.anyInt()))
        .thenReturn(stubs);

    // When we fetch the insurances for a specific period
    val list = insuranceBillingService.getMonthlySubscription(YearMonth.now());

    // Then the price for the customer should not be zero
    assertThat(list.size() == 0).isTrue();
  }

  @Test
  public void
      GetMonthlySubscriptionByMemberId_Should_ReturnInsuranceBillingFor4OnlyDays_WhenMemberInsuranceIs4DaysActive() {

    // Given that we have active insurance

    List<ProductEntity> stubs = new ArrayList<>();
    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().minusMonths(1).atDay(1).atStartOfDay();
    p.activeTo = YearMonth.now().atDay(1).plusDays(4).atStartOfDay();
    p.currentTotalPrice = BigDecimal.valueOf(99L);

    stubs.add(p);

    Mockito.when(
            productRepository.findBillingByDateByMemberId(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
        .thenReturn(stubs);

    // When we fetch the insurance for a specific period
    val insuranceBilling =
        insuranceBillingService.getMonthlySubscriptionByMemberId(YearMonth.now(), MEMBERID);

    // Then the price for the customer should not be zero
    assertThat(insuranceBilling).isNotEmpty();
    assertThat(insuranceBilling.get().getSubscription()).isEqualTo(Money.of(16, "SEK"));
  }

  @Test
  public void
      GetMonthlySubscriptionByMemberId_Should_ReturnInsuranceBillingWithZeroBillingAmount_WhenInsuranceWithTheSameActivationAndCancellationDayForAMember() {

    // Given that we have inactive insurances within the same day
    // We will avoid to charge customer with modified insurances

    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().atDay(1).atStartOfDay();
    p.activeTo = YearMonth.now().atDay(1).atStartOfDay();
    p.currentTotalPrice = BigDecimal.valueOf(309);

    List<ProductEntity> products = new ArrayList<>();
    products.add(p);

    Mockito.when(
            productRepository.findBillingByDateByMemberId(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
        .thenReturn(products);

    // When we fetch the insurances for a specific period
    val insuranceBilling =
        insuranceBillingService.getMonthlySubscriptionByMemberId(YearMonth.now(), MEMBERID);

    // Then the calculated for this period should be emptya
    assertThat(insuranceBilling).isNotEmpty();
    assertThat(insuranceBilling.get().getSubscription()).isEqualTo(Money.of(0, "SEK"));
  }

  @Test
  public void
      GetMonthlySubscriptionByMemberId_Should_ReturnInsuranceBillingWithZeroBillingAmount_WhenTerminatedInsuranceForASpecificMebmer() {

    // Given that we have a terminated insurance
    List<ProductEntity> stubs = new ArrayList<>();
    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().minusMonths(5).atDay(1).atStartOfDay();
    p.activeTo = YearMonth.now().atDay(1).minusDays(1).atStartOfDay();
    p.currentTotalPrice = BigDecimal.valueOf(200L);

    stubs.add(p);

    Mockito.when(
            productRepository.findBillingByDateByMemberId(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
        .thenReturn(stubs);

    // When we fetch the insurances for a specific period
    val insuranceBilling =
        insuranceBillingService.getMonthlySubscriptionByMemberId(YearMonth.now(), MEMBERID);

    // Then the price for the customer should not be zero
    assertThat(insuranceBilling).isNotEmpty();
    assertThat(insuranceBilling.get().getSubscription()).isEqualTo(Money.of(0, "SEK"));
  }

  @Test
  public void
      GetMonthlySubscriptionByMemberId_Should_ReturnInsuranceBillingWithZeroBillingAmount_WhenMemberWithInsuranceWtihFutureActiveFrom() {

    // Given that we have future insurance
    List<ProductEntity> stubs = new ArrayList<>();
    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().plusMonths(5).atDay(1).atStartOfDay();
    p.activeTo = null;
    p.currentTotalPrice = BigDecimal.valueOf(200L);

    stubs.add(p);

    Mockito.when(
            productRepository.findBillingByDateByMemberId(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
        .thenReturn(stubs);

    // When we fetch the insurances for a specific period
    val insuranceBilling =
        insuranceBillingService.getMonthlySubscriptionByMemberId(YearMonth.now(), MEMBERID);

    // Then the insurace should be empty
    assertThat(insuranceBilling).isNotEmpty();
    assertThat(insuranceBilling.get().getSubscription()).isEqualTo(Money.of(0, "SEK"));
  }

  @Test
  @Ignore
  // FIXME: We need to fix this case before Automate charging!
  public void GetMonthlySubscriptionByMemberId_Should_ReturnInsuranceBillingWithZeroBillingAmount_WhenInsuranceActiveToIsBeforeActiveFrom() {

    // Given that we have active insurances
    List<ProductEntity> stubs = new ArrayList<>();
    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.member.id = MEMBERID;
    p.activeFrom = YearMonth.now().atDay(10).atStartOfDay();
    p.activeTo = YearMonth.now().atDay(1).atStartOfDay();
    p.currentTotalPrice = BigDecimal.valueOf(99L);

    stubs.add(p);

    Mockito.when(
            productRepository.findBillingByDateByMemberId(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
        .thenReturn(stubs);

    // When we fetch the insurances for a specific period
    val insuranceBilling =
        insuranceBillingService.getMonthlySubscriptionByMemberId(YearMonth.now(), MEMBERID);

    // Then the price for the customer should not be zero
    assertThat(insuranceBilling).isNotEmpty();
    assertThat(insuranceBilling.get().getSubscription()).isEqualTo(Money.of(0, "SEK"));
  }

    private List<ProductEntity> GetListOfProductEntityStubs(int length){
    List<ProductEntity> stubs = new ArrayList<>();
        for (int i= 0; i< length; i++){
            ProductEntity p = new ProductEntity();
            p.member = new UserEntity();
            p.member.id = String.valueOf(i+1);
            p.activeFrom = YearMonth.now().atDay(1).atStartOfDay();
            p.activeTo = new Random().nextBoolean() ? YearMonth.now().atDay(new Random().nextInt(27) +1 ).atStartOfDay() : null;
            p.currentTotalPrice = BigDecimal.valueOf(new Random().nextInt(length) + new Random().nextFloat());
            stubs.add(p);
    }
    return stubs;
  }
}
