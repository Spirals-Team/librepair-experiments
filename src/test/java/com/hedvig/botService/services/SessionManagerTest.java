package com.hedvig.botService.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.hedvig.botService.chat.Conversation;
import com.hedvig.botService.chat.ConversationFactory;
import com.hedvig.botService.chat.OnboardingConversationDevi;
import com.hedvig.botService.enteties.ResourceNotFoundException;
import com.hedvig.botService.enteties.SignupCodeRepository;
import com.hedvig.botService.enteties.TrackingDataRespository;
import com.hedvig.botService.enteties.UserContext;
import com.hedvig.botService.enteties.UserContextRepository;
import com.hedvig.botService.enteties.message.Message;
import com.hedvig.botService.enteties.message.MessageBodySingleSelect;
import com.hedvig.botService.enteties.message.SelectLink;
import com.hedvig.botService.serviceIntegration.claimsService.ClaimsService;
import com.hedvig.botService.serviceIntegration.memberService.MemberService;
import com.hedvig.botService.serviceIntegration.memberService.dto.BankIdAuthResponse;
import com.hedvig.botService.serviceIntegration.memberService.dto.BankIdStatusType;
import com.hedvig.botService.serviceIntegration.productPricing.ProductPricingService;
import com.hedvig.botService.web.dto.AddMessageRequestDTO;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Optional;
import java.util.UUID;
import static com.hedvig.botService.chat.Conversation.HEDVIG_USER_ID;
import static com.hedvig.botService.services.TriggerServiceTest.TOLVANSSON_MEMBERID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SessionManagerTest {

  public static final String MESSAGE = "Heh hej";
  public static final SelectLink SELECT_LINK = SelectLink.toOffer("Offer", "offer");
  public static final String TOLVANSSON_FCM_TOKEN = "test-token";
  @Mock
  UserContextRepository userContextRepository;

  @Mock
  MemberService memberService;

  @Mock
  ProductPricingService productPricingService;

  @Mock
  SignupCodeRepository signupCodeRepository;

  @Mock
  TrackingDataRespository campaignCodeRepository;

  @Mock
  ConversationFactory conversationFactory;

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  Conversation mockConversation;

  @Mock
  MessagesService messagesService;

  @Mock
  ClaimsService claimsService;

  SessionManager sessionManager;

  @Before
  public void setUp() {
    val objectMapper = new ObjectMapper();
    sessionManager = new SessionManager(userContextRepository, memberService, claimsService,
        conversationFactory, campaignCodeRepository, objectMapper);
  }

  // FIXME
  @Test
  public void givenConversationThatCanAcceptMessage_WhenAddMessageFromHedvig_ThenAddsMessageToHistory() {

    val tolvanssonUserContext = makeTolvanssonUserContext();
    startMockConversation(tolvanssonUserContext);

    when(userContextRepository.findByMemberId(TOLVANSSON_MEMBERID))
        .thenReturn(Optional.of(tolvanssonUserContext));
    when(conversationFactory.createConversation(anyString())).thenReturn(mockConversation);

    when(mockConversation.canAcceptAnswerToQuestion(tolvanssonUserContext)).thenReturn(true);
    when(mockConversation.getSelectItemsForAnswer(tolvanssonUserContext))
        .thenReturn(Lists.newArrayList(SELECT_LINK));

    AddMessageRequestDTO requestDTO = new AddMessageRequestDTO(TOLVANSSON_MEMBERID, MESSAGE);

    val messageCouldBeAdded = sessionManager.addMessageFromHedvig(requestDTO);

    assertThat(messageCouldBeAdded).isTrue();

    Message message = Iterables.getLast(tolvanssonUserContext.getMemberChat().chatHistory);
    assertThat(message.body.text).isEqualTo(MESSAGE);
    assertThat(message.id).isEqualTo("message.bo.message");
    assertThat(message.header.fromId).isEqualTo(HEDVIG_USER_ID);
    assertThat(((MessageBodySingleSelect) message.body).choices).containsExactly(SELECT_LINK);
  }

  // FIXME
  @Test
  public void givenConversationThatCanAcceptMessage_WhenAddMessageFromHedvig_ThenReturnFalse() {

    val tolvanssonUserContext = makeTolvanssonUserContext();
    startMockConversation(tolvanssonUserContext);

    when(userContextRepository.findByMemberId(TOLVANSSON_MEMBERID))
        .thenReturn(Optional.of(tolvanssonUserContext));
    when(mockConversation.canAcceptAnswerToQuestion(tolvanssonUserContext)).thenReturn(false);
    when(conversationFactory.createConversation(anyString())).thenReturn(mockConversation);

    AddMessageRequestDTO requestDTO = new AddMessageRequestDTO(TOLVANSSON_MEMBERID, MESSAGE);

    val messageCouldBeAdded = sessionManager.addMessageFromHedvig(requestDTO);

    assertThat(messageCouldBeAdded).isFalse();
  }

  @Test
  public void givenNoExistingConversation_whenGetAllMessages_thenOnboardingConversationIsStarted() {

    val tolvanssonUserContext = makeTolvanssonUserContext();

    when(userContextRepository.findByMemberId(TOLVANSSON_MEMBERID))
        .thenReturn(Optional.of(tolvanssonUserContext));
    when(conversationFactory.createConversation(any(Class.class)))
        .thenReturn(makeOnboardingConversation());

    val messages = sessionManager.getAllMessages(TOLVANSSON_MEMBERID, null);

    assertThat(Iterables.getLast(messages)).hasFieldOrPropertyWithValue("id",
        "message.onboardingstart");
    assertThat(tolvanssonUserContext.getActiveConversation().get()).isNotNull();
  }

  @Test
  public void givenNoExistingConversation_whenGetAllMessagesWithIntentLOGIN_thenOnboardingConversationIsStarted() {

    val tolvanssonUserContext = makeTolvanssonUserContext();

    when(userContextRepository.findByMemberId(TOLVANSSON_MEMBERID))
        .thenReturn(Optional.of(tolvanssonUserContext));
    when(conversationFactory.createConversation(any(Class.class)))
        .thenReturn(makeOnboardingConversation());
    when(memberService.auth(TOLVANSSON_MEMBERID)).thenReturn(Optional.of(makeBankIdResponse()));

    val messages = sessionManager.getAllMessages(TOLVANSSON_MEMBERID, SessionManager.Intent.LOGIN);

    assertThat(Iterables.getLast(messages)).hasFieldOrPropertyWithValue("id",
        "message.start.login");
  }

  @Test
  public void givenAnExistingMember_whenRegisteringAnFCMToken_ThenShouldPersistTokenInUserContext() {
    val tolvanssonUserContext = makeTolvanssonUserContext();

    when(userContextRepository.findByMemberId(TOLVANSSON_MEMBERID))
        .thenReturn(Optional.of(tolvanssonUserContext));

    sessionManager.saveFirebasePushToken(TOLVANSSON_MEMBERID, TOLVANSSON_FCM_TOKEN);

    assertThat(tolvanssonUserContext.getDataEntry("FIREBASE-TOKEN"))
        .isEqualTo(TOLVANSSON_FCM_TOKEN);
  }

  @Test
  public void givenANonExistingMember_whenRegisteringAnFCMToken_ThenShouldThrowException() {
    when(userContextRepository.findByMemberId("")).thenReturn(Optional.empty());
    assertThatExceptionOfType(ResourceNotFoundException.class)
        .isThrownBy(() -> sessionManager.saveFirebasePushToken("", ""));
  }

  @Test
  public void givenAnExistingMemberWithFCMToken_whenGettingFCMToken_ThenShouldReturnFCMToken() {
    val tolvanssonUserContext = makeTolvanssonUserContext();
    tolvanssonUserContext.putUserData("FIREBASE-TOKEN", TOLVANSSON_FCM_TOKEN);
    when(userContextRepository.findByMemberId(TOLVANSSON_MEMBERID))
        .thenReturn(Optional.of(tolvanssonUserContext));

    val token = sessionManager.getFirebasePushToken(TOLVANSSON_MEMBERID);
    assertThat(token).isEqualTo(TOLVANSSON_FCM_TOKEN);
  }

  @Test
  public void givenANonExistingMember_whenGettingFCMToken_ThenShouldThrowException() {
    when(userContextRepository.findByMemberId("")).thenReturn(Optional.empty());

    assertThatExceptionOfType(ResourceNotFoundException.class)
        .isThrownBy(() -> sessionManager.getFirebasePushToken(""));
  }

  @Test
  public void givenAnExistingMemberWithoutFCMToken_whenGettingFCMToken_ThenShouldReturnNull() {
    val tolvanssonUserContext = makeTolvanssonUserContext();

    when(userContextRepository.findByMemberId(TOLVANSSON_MEMBERID))
        .thenReturn(Optional.of(tolvanssonUserContext));

    assertThat(sessionManager.getFirebasePushToken(TOLVANSSON_MEMBERID)).isNull();
  }

  private OnboardingConversationDevi makeOnboardingConversation() {
    return new OnboardingConversationDevi(memberService, productPricingService, null, null, null);
  }

  private BankIdAuthResponse makeBankIdResponse() {
    return new BankIdAuthResponse(BankIdStatusType.STARTED, UUID.randomUUID().toString(),
        UUID.randomUUID().toString());
  }

  private UserContext makeTolvanssonUserContext() {
    val tolvanssonUserContext = new UserContext(TOLVANSSON_MEMBERID);

    return tolvanssonUserContext;
  }

  private void startMockConversation(UserContext tolvanssonUserContext) {
    tolvanssonUserContext.startConversation(mockConversation);
  }
}
