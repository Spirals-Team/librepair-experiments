package com.hedvig.productPricing.aggregates;

import com.hedvig.productPricing.service.aggregates.MemberAggregate;
import com.hedvig.productPricing.service.aggregates.SafetyIncreaser;
import com.hedvig.productPricing.service.commands.CreateMemberCommand;
import com.hedvig.productPricing.service.commands.CreateProductCommand;
import com.hedvig.productPricing.service.events.MemberCreatedEvent;
import com.hedvig.productPricing.service.events.ProductCreatedEvent;
import com.hedvig.productPricing.service.pdfcreate.PdfCreator;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import com.hedvig.productPricing.testHelpers.EventCreator;
import com.hedvig.productPricing.testHelpers.FixureData;
import com.hedvig.productPricing.testHelpers.MemberFake;
import com.hedvig.productPricing.testHelpers.Members;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class MemberAggregateTest {

    private FixtureConfiguration<MemberAggregate> fixture;

    @MockBean
    PdfCreator pdfCreator;


    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(MemberAggregate.class);
        fixture.registerInjectableResource(pdfCreator);

    }

    @Test
    public void MemberCreatedEvent() {
        MemberFake tolvan = Members.TOLVAN;
        CreateMemberCommand cmd = new CreateMemberCommand(tolvan.getMemberId(), tolvan.getSsn(), tolvan.getFirstName(), tolvan.getLastName(),
                tolvan.getBirthDate(), tolvan.getAddress(), tolvan.getLivingSpace(), tolvan.getHouseType(), tolvan.getCurrentInsurer(), tolvan.getPersonsInHouseHold(),  tolvan.getSafetyIncreasers());
        fixture.
                given().
                when(cmd).
                expectEvents(new MemberCreatedEvent(
                tolvan.getMemberId(),
                tolvan.getSsn(),
                tolvan.getFirstName(),
                tolvan.getLastName(),
                tolvan.getBirthDate(),
                tolvan.getAddress(),
                tolvan.getLivingSpace(),
                tolvan.getHouseType(),
                tolvan.getCurrentInsurer(),
                tolvan.getPersonsInHouseHold(),
                tolvan.getSafetyIncreasers().stream().map(SafetyIncreaser::createFrom).collect(Collectors.toList())
        ));
    }
/*
    @Test
    public void CalculateQuoteCommand_set_perilStateTo_ADD_PENDING(){
        MemberFake mf = Members.TOLVAN;
        ArrayList<PerilDTO> perils = FixureData.createTwoPerils();

        ArrayList<PerilDTO> requestPerils = FixureData.createTwoPerils();
        requestPerils.get(1).state = "ADD_REQUESTED";

        ArrayList<PerilDTO> expectedPerils = FixureData.createTwoPerils();
        expectedPerils.get(1).state = "ADD_PENDING";
        expectedPerils.get(1).isRemovable = true;

        UUID productId = UUID.randomUUID();
        fixture.given(EventCreator.MemberCreated(mf),
                new ProductCreatedEvent(productId, mf.getMemberId(), perils, BigDecimal.valueOf(100.0d), Product.ProductTypes.RENT, false)).
                when(new CalculateQuoteCommand(
                        mf.getMemberId(), requestPerils)).
                expectSuccessfulHandlerExecution().
                expectEvents(new QuoteCalculatedEvent(productId, expectedPerils, BigDecimal.valueOf(110.0d)));

    }*/

/*
    @Test
    public void CalculateQuoteCommand_GIVEN_REMOVE_REQUESTED_setStateTo_REMOVE_PENDING(){
        MemberFake mf = Members.TOLVAN;
        ArrayList<PerilDTO> perils = FixureData.createTwoPerils();

        ArrayList<PerilDTO> requestPerils = FixureData.createTwoPerils();
        requestPerils.get(1).state = "REMOVE_REQUESTED";

        ArrayList<PerilDTO> expectedPerils = FixureData.createTwoPerils();
        expectedPerils.get(1).state = "REMOVE_PENDING";

        UUID productId = UUID.randomUUID();
        fixture.given(EventCreator.MemberCreated(mf),
                new ProductCreatedEvent(productId, mf.getMemberId(), perils, BigDecimal.valueOf(100.0d),  Product.ProductTypes.RENT, false)).
                when(new CalculateQuoteCommand(
                        mf.getMemberId(), requestPerils)).
                expectSuccessfulHandlerExecution().
                expectEvents(new QuoteCalculatedEvent(productId, expectedPerils, BigDecimal.valueOf(90.0d)));

    }
    */
/*
    @Test
    public void CalculateQuoteCommand_ADD_REMOVE_Perils_SholdSetNewPriceToCurrentPrice(){
        MemberFake mf = Members.TOLVAN;
        ArrayList<PerilDTO> perils = FixureData.createTwoPerils();

        ArrayList<PerilDTO> addRequest = FixureData.createTwoPerils();
        addRequest.get(1).state = "ADD_REQUESTED";

        ArrayList<PerilDTO> removeRequest = FixureData.createTwoPerils();
        removeRequest.get(1).state = "REMOVE_REQUESTED";

        ArrayList<PerilDTO> expectedPerils = FixureData.createTwoPerils();
        expectedPerils.get(1).state = "REMOVE_PENDING";

        UUID productId = UUID.randomUUID();
        fixture.given(
                    EventCreator.MemberCreated(mf),
                    new ProductCreatedEvent(productId, mf.getMemberId(), perils, BigDecimal.valueOf(100.0d), mf.getHouseType(), false),
                    new QuoteCalculatedEvent(productId , addRequest, BigDecimal.valueOf(110.0d))
                ).
                when(
                        new CalculateQuoteCommand(mf.getMemberId(), removeRequest)
                ).
                expectSuccessfulHandlerExecution().
                expectEvents(new QuoteCalculatedEvent(productId, expectedPerils, BigDecimal.valueOf(100.0d)));

    }
    */

   /* @Test
    public void CalculateQuoteCommand_SignedContractCommand_SetsProductStateTo_ACTIVE_IF_CurrentInsurerISNULL() {
        MemberFake mf = Members.TOLVAN;
        ArrayList<PerilDTO> perils = FixureData.createTwoPerils();

        UUID productId = UUID.randomUUID();
        final String someReferenceToken = "SomeReferenceToken";
        fixture.given(
                EventCreator.MemberCreated(mf),
                new ProductCreatedEvent(
                        productId,
                        mf.getMemberId(),
                        perils,
                        BigDecimal.valueOf(100.0d),
                        ProductStatus.QUOTE,
                        mf.getHouseType(),
                        false,
                        null,
                        null)
                ).
                when(
                        new SingedContractCommand(mf.getMemberId(), someReferenceToken, "", "")
                ).
                expectSuccessfulHandlerExecution().
                expectEvents(new ContractSignedEvent(mf.getMemberId(), productId, someReferenceToken, null),
                            new InsuranceMandateCreatedEvent(mf.getMemberId(), null));
    }*/

    @Test
    public void CreateProductCommandWhenMemberHasInsuranceFromOtherCompany() {
        MemberFake mf = Members.MRTVA;
        ArrayList<PerilDTO> perils = FixureData.createTwoPerils();

        UUID productId = UUID.randomUUID();

        final double currentTotalPrice = 112.0;
        fixture.given(
                    EventCreator.MemberCreated(mf)
                ).
                when(
                    new CreateProductCommand(
                            productId,
                            mf.getMemberId(),
                            perils,
                            currentTotalPrice,
                            mf.getHouseType())
                ).
                expectSuccessfulHandlerExecution().
                expectEvents(
                        new ProductCreatedEvent(
                                productId,
                                mf.getMemberId(),
                                perils,
                                BigDecimal.valueOf(currentTotalPrice),
                                mf.getHouseType(),
                                true
                        ));
    }

    @Test
    public void CreateProductCommandWhenMemberNoInsuranceFromOtherCompany() {
        MemberFake mf = Members.TOLVAN;
        ArrayList<PerilDTO> perils = FixureData.createTwoPerils();

        UUID productId = UUID.randomUUID();

        final double currentTotalPrice = 112.0;
        fixture.given(
                EventCreator.MemberCreated(mf)
        ).
                when(
                        new CreateProductCommand(
                                productId,
                                mf.getMemberId(),
                                perils,
                                currentTotalPrice,
                                mf.getHouseType())
                ).
                expectSuccessfulHandlerExecution().
                expectEvents(
                        new ProductCreatedEvent(
                                productId,
                                mf.getMemberId(),
                                perils,
                                BigDecimal.valueOf(currentTotalPrice),
                                mf.getHouseType(),
                                false));
    }

}
