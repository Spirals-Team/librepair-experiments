package com.hedvig.productPricing.service.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.query.ProductEntity;
import com.hedvig.productPricing.service.query.ProductRepository;
import com.hedvig.productPricing.service.query.UserEntity;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lombok.val;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class InsuranceServiceImplTest {

  private static final String MEMBER_ID = "1234";
  @MockBean private ProductRepository productRepository;

  @MockBean private InsuranceService insuranceService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    insuranceService = new InsuranceServiceImpl(productRepository);
  }

  @Test
  public void GetCurrentInsurance_Should_ReturnOneInsurance_When_MemberHasOnlyOneInsurance() {

    // Given that we have active insurances

    String memberId = MEMBER_ID;
    UUID testId = UUID.randomUUID();

    List<ProductEntity> mockedProducts = new ArrayList<>();

    ProductEntity p = new ProductEntity();
    p.member = new UserEntity();
    p.id = testId;
    p.member.id = memberId;
    p.activeFrom = null;
    p.activeTo = null;
    p.currentTotalPrice = BigDecimal.valueOf(309);
    p.state = ProductStates.QUOTE;

    mockedProducts.add(p);

    Mockito.when(productRepository.findByMemberId(Mockito.anyString())).thenReturn(mockedProducts);

    // When we fetch the insurances for a specific period
    val currentInsurance = insuranceService.GetCurrentInsurance(memberId);

    // Then the calculated for this period should be returned
    assertThat(currentInsurance.isPresent()).isTrue();
    assertThat(currentInsurance.get().id.equals(testId)).isTrue();
  }

  @Test
  public void
      GetCurrentInsurance_Should_ReturnTheSignedInsurance_When_MemberHasMultipleInsurances() {
    // Given that we have active insurances
    String memberId = MEMBER_ID;

    List<ProductEntity> mockedProdcuts = new ArrayList<>();

    mockedProdcuts.addAll(GetListOfProductEntityStubsForAMember(1, memberId, ProductStates.SIGNED));
    mockedProdcuts.addAll(GetListOfProductEntityStubsForAMember(5, memberId, ProductStates.QUOTE));
    mockedProdcuts.addAll(
        GetListOfProductEntityStubsForAMember(5, memberId, ProductStates.TERMINATED));

    Mockito.when(productRepository.findByMemberId(Mockito.anyString())).thenReturn(mockedProdcuts);

    // When we fetch the insurances for a specific period
    val currentInsurance = insuranceService.GetCurrentInsurance(memberId);

    // Then the calculated for this period should be returned
    assertThat(currentInsurance.isPresent()).isTrue();
    assertThat(currentInsurance.get().state == ProductStates.SIGNED).isTrue();
  }

  @Test
  public void
      GetCurrentInsurance_Should_ReturnTheActiveSignedInsurance_When_MemberHasMultipleSignedInsurances() {
    // Given that we have active insurances
    String memberId = MEMBER_ID;

    UUID activeUUID = UUID.randomUUID();
    UUID notActiveUUID = UUID.randomUUID();

    List<ProductEntity> mockedProdcuts = new ArrayList<>();

    mockedProdcuts.addAll(GetListOfProductEntityStubsForAMember(2, memberId, ProductStates.SIGNED));

    mockedProdcuts.get(0).activeFrom = YearMonth.now().atDay(1).atStartOfDay();
    mockedProdcuts.get(0).activeTo = null;
    mockedProdcuts.get(0).id = activeUUID;

    mockedProdcuts.get(1).activeFrom = YearMonth.now().minusMonths(1).atDay(1).atStartOfDay();
    mockedProdcuts.get(1).activeTo = YearMonth.now().minusMonths(1).atEndOfMonth().atStartOfDay();
    mockedProdcuts.get(1).id = notActiveUUID;

    Mockito.when(productRepository.findByMemberId(Mockito.anyString())).thenReturn(mockedProdcuts);

    // When we fetch the insurances for a specific period
    val currentInsurance = insuranceService.GetCurrentInsurance(memberId);

    // Then the calculated for this period should be returned
    assertThat(currentInsurance.isPresent()).isTrue();
    assertThat(currentInsurance.get().id.equals(activeUUID)).isTrue();
  }

  @Test
  public void
      GetCurrentInsurance_Should_ReturnTheLatestQuoted_When_MemberHasMultipleQuotedInsurances() {
    // Given that we have active insurances
    String memberId = MEMBER_ID;

    UUID one = UUID.randomUUID();
    UUID two = UUID.randomUUID();

    List<ProductEntity> mockedProdcuts = new ArrayList<>();

    mockedProdcuts.addAll(GetListOfProductEntityStubsForAMember(2, memberId, ProductStates.QUOTE));

    mockedProdcuts.get(0).activeFrom = null;
    mockedProdcuts.get(0).activeTo = null;
    mockedProdcuts.get(0).id = one;

    mockedProdcuts.get(1).activeFrom = null;
    mockedProdcuts.get(1).activeTo = null;
    mockedProdcuts.get(1).id = two;

    Mockito.when(productRepository.findByMemberId(Mockito.anyString())).thenReturn(mockedProdcuts);

    // When we fetch the insurances for a specific period
    val currentInsurance = insuranceService.GetCurrentInsurance(memberId);

    // Then the calculated for this period should be returned
    assertThat(currentInsurance.isPresent()).isTrue();
    assertThat(currentInsurance.get().id).isEqualTo(two);
  }

  @Test
  public void
      GetCurrentInsurance_Should_ReturnTheLatestTerminated_When_MemberHasMultipleTerminated() {
    // Given that we have active insurances
    String memberId = MEMBER_ID;
    UUID one = UUID.randomUUID();
    UUID two = UUID.randomUUID();

    List<ProductEntity> mockedProdcuts = new ArrayList<>();

    mockedProdcuts.addAll(
        GetListOfProductEntityStubsForAMember(2, memberId, ProductStates.TERMINATED));
    mockedProdcuts.get(0).id = one;
    mockedProdcuts.get(1).id = two;

    Mockito.when(productRepository.findByMemberId(Mockito.anyString())).thenReturn(mockedProdcuts);

    // When we fetch the insurances for a specific period
    val currentInsurance = insuranceService.GetCurrentInsurance(memberId);

    // Then the calculated for this period should be returned
    assertThat(currentInsurance.isPresent()).isTrue();
    assertThat(currentInsurance.get().state).isEqualTo(ProductStates.TERMINATED);
    assertThat(currentInsurance.get().id).isEqualTo(two);
  }

  private List<ProductEntity> GetListOfProductEntityStubsForAMember(
      int length, String memberId, ProductStates state) {
    List<ProductEntity> stubs = new ArrayList<>();

    for (int i = 0; i < length; i++) {

      ProductEntity p = new ProductEntity();
      p.member = new UserEntity();
      p.member.id = memberId;
      p.activeFrom = YearMonth.now().atDay(1).atStartOfDay();
      p.activeTo =
          RandomUtils.nextBoolean()
              ? YearMonth.now().atDay(new Random().nextInt(27) + 1).atStartOfDay()
              : null;
      p.currentTotalPrice =
          BigDecimal.valueOf(new Random().nextInt(length) + new Random().nextFloat());
      p.state = state;

      stubs.add(p);
    }

    return stubs;
  }
}
