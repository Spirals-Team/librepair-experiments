package com.hedvig.productPricing.service.web.dto;


import com.hedvig.productPricing.service.aggregates.Product;
import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.query.ProductEntity;
import com.hedvig.productPricing.service.query.UserEntity;
import com.hedvig.productPricing.service.serviceIntegration.botService.dto.Address;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class InsuranceDTOTest {

    private Clock clock;

    private static final String TOLVANSSON_MEMBER_ID = "1337";

    @Test
    public void statusIsACTIVE_When_stateIsSignedAND_activeFromIsEqualToCurrentTime() {

        LocalDateTime activeDate = LocalDateTime.parse("2018-02-01T00:01:01");
        ProductEntity productEntity = makeProduct(ProductStates.SIGNED, activeDate);
        UserEntity ue = makeUser(activeDate);

        clock = Clock.fixed(Instant.parse("2018-02-01T00:01:01.00Z"), ZoneId.systemDefault());

        InsuranceDTO dto = new InsuranceDTO(clock, productEntity, new ArrayList<>(), ue);

        assertThat(dto.status).isEqualTo(ProductStatus.ACTIVE);
    }

    @Test
    public void statusIsACTIVE_When_stateIsSignedAND_activeFromIsLessThenCurrentTime() {

        val activeDate = LocalDateTime.parse("2018-02-01T00:01:01");
        ProductEntity productEntity = makeProduct(ProductStates.SIGNED, activeDate);
        UserEntity ue = makeUser(activeDate);

        clock = Clock.fixed(Instant.parse("2018-02-02T00:01:01.00Z"), ZoneId.systemDefault());

        InsuranceDTO dto = new InsuranceDTO(clock, productEntity, new ArrayList<>(), ue);

        assertThat(dto.status).isEqualTo(ProductStatus.ACTIVE);
    }

    @Test
    public void statusIsINACTIVE_When_stateIsSigned_andActiveFromIsGreaterThenCurrentTime() {

        LocalDateTime activeDate = LocalDateTime.parse("2018-02-01T00:01:01");
        ProductEntity productEntity = makeProduct(ProductStates.SIGNED, activeDate);
        UserEntity ue = makeUser(activeDate);

        clock = Clock.fixed(Instant.parse("2018-01-31T00:01:01.00Z"), ZoneId.systemDefault());

        InsuranceDTO dto = new InsuranceDTO(clock, productEntity, new ArrayList<>(), ue);

        assertThat(dto.status).isEqualTo(ProductStatus.INACTIVE_WITH_START_DATE);
    }

    @Test
    public void statusIsINACTIVE_When_stateIsSigned_andActiveFromIsNull() {

        ProductEntity productEntity = makeProduct(ProductStates.SIGNED, null);
        UserEntity ue = makeUser(null);

        clock = Clock.fixed(Instant.parse("2018-01-31T00:01:01.00Z"), ZoneId.systemDefault());

        InsuranceDTO dto = new InsuranceDTO(clock, productEntity, new ArrayList<>(), ue);

        assertThat(dto.status).isEqualTo(ProductStatus.INACTIVE);
    }

    @Test
    public void statusIsPENDING_When_stateIsQUOTE() {

        ProductEntity productEntity = makeProduct(ProductStates.QUOTE, null);
        UserEntity ue = makeUser(null);

        clock = Clock.fixed(Instant.parse("2018-01-31T00:01:01.00Z"), ZoneId.systemDefault());

        InsuranceDTO dto = new InsuranceDTO(clock, productEntity, new ArrayList<>(), ue);

        assertThat(dto.status).isEqualTo(ProductStatus.PENDING);
    }

    @Test
    public void statusIsINACTIVE_When_stateIsTERMINATED() {

        ProductEntity productEntity = makeProduct(ProductStates.TERMINATED, null);
        UserEntity ue = makeUser(null);

        clock = Clock.fixed(Instant.parse("2018-01-31T00:01:01.00Z"), ZoneId.systemDefault());
        InsuranceDTO dto = new InsuranceDTO(clock, productEntity, new ArrayList<>(), ue);

        assertThat(dto.status).isEqualTo(ProductStatus.INACTIVE);
    }

    private ProductEntity makeProduct(ProductStates state, LocalDateTime activeDate) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.insuredAtOtherCompany = false;
        productEntity.houseType = Product.ProductTypes.BRF;
        productEntity.perils = new HashMap<>();
        productEntity.state = state;
        productEntity.member = new UserEntity(TOLVANSSON_MEMBER_ID);
        productEntity.currentTotalPrice = BigDecimal.valueOf(250f);
        productEntity.activeFrom = activeDate;
        return productEntity;
    }

    private UserEntity makeUser(LocalDateTime activeDate){
        val entity = new UserEntity();
        entity.currentInsurer = "if";
        entity.personsInHouseHold = 3;
        entity.address = new Address();
        entity.address.setStreet("En gata 3");
        return entity;
    }

}
