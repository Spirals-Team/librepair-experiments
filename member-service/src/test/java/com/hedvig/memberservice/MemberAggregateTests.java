package com.hedvig.memberservice;

import static org.mockito.Mockito.when;

import com.hedvig.common.UUIDGenerator;
import com.hedvig.external.bisnodeBCI.BisnodeClient;
import com.hedvig.memberservice.aggregates.MemberAggregate;
import com.hedvig.memberservice.aggregates.MemberStatus;
import com.hedvig.memberservice.commands.AuthenticationAttemptCommand;
import com.hedvig.memberservice.commands.BankIdAuthenticationStatus;
import com.hedvig.memberservice.commands.BankIdSignCommand;
import com.hedvig.memberservice.commands.MemberUpdateContactInformationCommand;
import com.hedvig.memberservice.commands.SelectNewCashbackCommand;
import com.hedvig.memberservice.commands.StartOnboardingWithSSNCommand;
import com.hedvig.memberservice.events.EmailUpdatedEvent;
import com.hedvig.memberservice.events.LivingAddressUpdatedEvent;
import com.hedvig.memberservice.events.MemberAuthenticatedEvent;
import com.hedvig.memberservice.events.MemberCreatedEvent;
import com.hedvig.memberservice.events.MemberSignedEvent;
import com.hedvig.memberservice.events.MemberStartedOnBoardingEvent;
import com.hedvig.memberservice.events.NameUpdatedEvent;
import com.hedvig.memberservice.events.NewCashbackSelectedEvent;
import com.hedvig.memberservice.events.OnboardingStartedWithSSNEvent;
import com.hedvig.memberservice.events.SSNUpdatedEvent;
import com.hedvig.memberservice.events.TrackingIdCreatedEvent;
import com.hedvig.memberservice.services.CashbackService;
import com.hedvig.memberservice.web.dto.Address;
import com.hedvig.memberservice.web.dto.StartOnboardingWithSSNRequest;
import com.hedvig.memberservice.web.dto.UpdateContactInformationRequest;
import java.util.UUID;
import lombok.val;
import org.axonframework.eventsourcing.AbstractAggregateFactory;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

@RunWith(SpringRunner.class)
public class MemberAggregateTests {

  private FixtureConfiguration<MemberAggregate> fixture;

  @MockBean private BisnodeClient bisnodeClient;

  @MockBean private CashbackService cashbackService;

  @MockBean private UUIDGenerator uuidGenerator;

  private class AggregateFactoryM<T> extends AbstractAggregateFactory<T> {

    AggregateFactoryM(Class<T> aggregateType) {
      super(aggregateType);
    }

    @Override
    protected T doCreateAggregate(String aggregateIdentifier, DomainEventMessage firstEvent) {
      return (T) new MemberAggregate(bisnodeClient, cashbackService, uuidGenerator);
    }
  }

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(MemberAggregate.class);
    fixture.registerAggregateFactory(new AggregateFactoryM<>(MemberAggregate.class));
  }

  @Test
  public void contextLoads() {
    Long memberId = 123L;
    fixture.given(new MemberCreatedEvent(memberId, MemberStatus.INITIATED));
  }

  @Test
  public void authenticatedAttemptCommand() {
    Long memberId = 1234L;
    final String ssn = "191212121212";
    final String referenceTokenValue = "referenceTokenValue";
    final String firstName = "TOLVAN";
    final String lastName = "TOLVANSSON";

    when(bisnodeClient.match(ssn)).thenThrow(new RestClientException("Something went wrong!"));

    val uuid = UUID.fromString("971b25bc-5db5-11e8-9f7c-039208e9dccf");
    when(uuidGenerator.generateRandom()).thenReturn(uuid);

    BankIdAuthenticationStatus authStatus = new BankIdAuthenticationStatus();
    authStatus.setSSN(ssn);
    authStatus.setReferenceToken(referenceTokenValue);
    authStatus.setSurname(lastName);
    authStatus.setGivenName(firstName);

    AuthenticationAttemptCommand cmd = new AuthenticationAttemptCommand(memberId, authStatus);

    fixture
        .given(new MemberCreatedEvent(memberId, MemberStatus.INITIATED))
        .when(cmd)
        .expectSuccessfulHandlerExecution()
        .expectEvents(
            new SSNUpdatedEvent(memberId, ssn),
            new TrackingIdCreatedEvent(memberId, uuid),
            new NameUpdatedEvent(memberId, "Tolvan", "Tolvansson"),
            new MemberStartedOnBoardingEvent(memberId, MemberStatus.ONBOARDING),
            new MemberAuthenticatedEvent(memberId, referenceTokenValue));
  }

  @Test
  public void MemberUpdatePersonalInformation() {
    Long memberId = 1234L;

    UpdateContactInformationRequest request = new UpdateContactInformationRequest();
    request.setFirstName("Arn");
    request.setLastName("Magnusson");
    request.setEmail("email@hedvig.com");
    Address address = new Address();
    address.setStreet("Spånga bro");
    address.setCity("Spånga");
    address.setApartmentNo("1104");
    address.setZipCode("55748");
    address.setFloor(0);
    request.setAddress(address);

    fixture
        .given(new MemberCreatedEvent(memberId, MemberStatus.INITIATED))
        .when(new MemberUpdateContactInformationCommand(memberId, request))
        .expectSuccessfulHandlerExecution()
        .expectEvents(
            new NameUpdatedEvent(memberId, request.getFirstName(), request.getLastName()),
            new EmailUpdatedEvent(memberId, "email@hedvig.com"),
            new LivingAddressUpdatedEvent(
                memberId,
                address.getStreet(),
                address.getCity(),
                address.getZipCode(),
                address.getApartmentNo(),
                0));
  }

  @Test
  public void StartOnBoardingFromSSN() {
    Long memberId = 1234L;

    String ssn = "192005059999";
    StartOnboardingWithSSNRequest request = new StartOnboardingWithSSNRequest(ssn);

    fixture
        .given(new MemberCreatedEvent(memberId, MemberStatus.INITIATED))
        .when(new StartOnboardingWithSSNCommand(memberId, request))
        .expectSuccessfulHandlerExecution()
        .expectEvents(
            new OnboardingStartedWithSSNEvent(memberId, ssn),
            new MemberStartedOnBoardingEvent(memberId, MemberStatus.ONBOARDING));
  }

  @Test
  public void SelectNewCashbackCommand() {
    Long memberId = 1234L;
    String cashbackId = "328354a4-d119-11e7-ac68-139bd471ea9a";

    fixture
        .given(new MemberCreatedEvent(memberId, MemberStatus.INITIATED))
        .when(new SelectNewCashbackCommand(memberId, UUID.fromString(cashbackId)))
        .expectSuccessfulHandlerExecution()
        .expectEvents(new NewCashbackSelectedEvent(memberId, cashbackId));
  }

  @Test
  public void BankIdSignCommand() {
    Long memberId = 1234L;
    String referenceId = "someReferenceId";

    UUID defaultCashback = UUID.fromString("9881f632-fb69-11e7-9238-a39b7922d42d");

    val uuid = UUID.fromString("971b25bc-5db5-11e8-9f7c-039208e9dccf");
    when(uuidGenerator.generateRandom()).thenReturn(uuid);

    when(cashbackService.getDefaultId()).thenReturn(defaultCashback);

    fixture
        .given(new MemberCreatedEvent(memberId, MemberStatus.INITIATED))
        .when(new BankIdSignCommand(memberId, referenceId, "", ""))
        .expectSuccessfulHandlerExecution()
        .expectEvents(
            new NewCashbackSelectedEvent(memberId, defaultCashback.toString()),
            new MemberSignedEvent(memberId, referenceId, "", ""),
            new TrackingIdCreatedEvent(memberId, uuid));
  }
}
