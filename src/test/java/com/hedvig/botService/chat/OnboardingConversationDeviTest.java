package com.hedvig.botService.chat;

import com.hedvig.botService.enteties.SignupCodeRepository;
import com.hedvig.botService.enteties.UserContext;
import com.hedvig.botService.enteties.message.Message;
import com.hedvig.botService.enteties.message.MessageBodySingleSelect;
import com.hedvig.botService.enteties.userContextHelpers.UserData;
import com.hedvig.botService.serviceIntegration.memberService.MemberService;
import com.hedvig.botService.serviceIntegration.productPricing.ProductPricingService;
import com.hedvig.botService.services.events.OnboardingQuestionAskedEvent;
import com.hedvig.botService.services.events.RequestObjectInsuranceEvent;
import com.hedvig.botService.services.events.UnderwritingLimitExcededEvent;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import static com.hedvig.botService.chat.OnboardingConversationDevi.MESSAGE_50K_LIMIT_YES_YES;
import static com.hedvig.botService.testHelpers.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class OnboardingConversationDeviTest {

  @Mock
  private MemberService memberService;

  @Mock
  private ProductPricingService productPricingService;

  @Mock
  private SignupCodeRepository signupRepo;

  @Mock
  private ApplicationEventPublisher publisher;

  @Mock
  private ConversationFactory conversationFactory;

  private UserContext userContext;
  private OnboardingConversationDevi testConversation;

  @Before
  public void setup() {
    userContext = new UserContext(TOLVANSSON_MEMBER_ID);
    userContext.putUserData(UserData.HOUSE, TOLVANSSON_PRODUCT_TYPE);

    testConversation = new OnboardingConversationDevi(memberService, productPricingService,
        signupRepo, publisher, conversationFactory);
  }

  @Test
  public void SendNotificationEventOn_HouseingUnderWritingLimit() {

    addLastnameToContext(userContext, TOLVANSSON_FIRSTNAME);
    addFirstnametoContext(userContext, TOLVANSSON_LASTNAME);

    Message m = testConversation.getMessage("message.uwlimit.housingsize");
    m.body.text = TOLVANSSON_PHONE_NUMBER;

    testConversation.receiveMessage(userContext, m);

    then(publisher).should()
        .publishEvent(new UnderwritingLimitExcededEvent(TOLVANSSON_MEMBER_ID,
            TOLVANSSON_PHONE_NUMBER, TOLVANSSON_FIRSTNAME, TOLVANSSON_LASTNAME,
            UnderwritingLimitExcededEvent.UnderwritingType.HouseingSize));
  }

  @Test
  public void SendNotificationEventOn_HousholdUnderWritingLimit() {

    addLastnameToContext(userContext, TOLVANSSON_FIRSTNAME);
    addFirstnametoContext(userContext, TOLVANSSON_LASTNAME);

    Message m = testConversation.getMessage("message.uwlimit.householdsize");
    m.body.text = TOLVANSSON_PHONE_NUMBER;

    testConversation.receiveMessage(userContext, m);

    then(publisher).should()
        .publishEvent(new UnderwritingLimitExcededEvent(TOLVANSSON_MEMBER_ID,
            TOLVANSSON_PHONE_NUMBER, TOLVANSSON_FIRSTNAME, TOLVANSSON_LASTNAME,
            UnderwritingLimitExcededEvent.UnderwritingType.HouseholdSize));
  }

  @Test
  public void SendNotificationEventOn_FriFraga() {
    addLastnameToContext(userContext, TOLVANSSON_LASTNAME);
    addFirstnametoContext(userContext, TOLVANSSON_FIRSTNAME);

    Message m = testConversation.getMessage("message.frifraga");
    m.body.text = "I wonder if I can get a home insurance, even thouh my name is Tolvan?";

    testConversation.receiveMessage(userContext, m);

    then(publisher).should()
        .publishEvent(new OnboardingQuestionAskedEvent(TOLVANSSON_MEMBER_ID, m.body.text));
  }

  @Test
  public void DoNotSendNotificationEvent_WhenMessge_50K_LIMIT_YES_withAnswer_MESSAGE_50K_LIMIT_YES_YES() {
    Message m =
        testConversation.getMessage(OnboardingConversationDevi.MESSAGE_50K_LIMIT_YES + ".2");
    val choice = ((MessageBodySingleSelect) m.body).choices.stream()
        .filter(x -> x.value.equalsIgnoreCase(MESSAGE_50K_LIMIT_YES_YES)).findFirst();

    choice.get().selected = true;

    testConversation.receiveMessage(userContext, m);
    then(publisher).should(times(0)).publishEvent(
        new RequestObjectInsuranceEvent(TOLVANSSON_MEMBER_ID, TOLVANSSON_PRODUCT_TYPE));
  }

  @Test
  public void SendNotificationEvent_WhenMemberSignedIsCalled_withUserContextValue50K_LIMITeqTRUE() {
    String referenceId = "53bb6e92-5cc7-11e8-8c3b-235d0786c76b";
    userContext.putUserData("{50K_LIMIT}", "true");
    testConversation.memberSigned(referenceId, userContext);
    then(publisher).should(times(1)).publishEvent(
        new RequestObjectInsuranceEvent(TOLVANSSON_MEMBER_ID, TOLVANSSON_PRODUCT_TYPE));
  }

  @Test
  public void DoNothing_WhenMemberSignedIsCalled_withUserContextValue50K_LIMITeqNULL() {
    String referenceId = "53bb6e92-5cc7-11e8-8c3b-235d0786c76b";
    testConversation.memberSigned(referenceId, userContext);
    then(publisher).should(times(0)).publishEvent(
        new RequestObjectInsuranceEvent(TOLVANSSON_MEMBER_ID, TOLVANSSON_PRODUCT_TYPE));
  }

  @Test
  public void ReturnFalse_WhenChat_IsBeforeHouseChoice() {

    val uc = new UserContext(TOLVANSSON_MEMBER_ID);
    val canAcceptAnswer = testConversation.canAcceptAnswerToQuestion(uc);

    assertThat(canAcceptAnswer).isEqualTo(false);
  }

  @Test
  public void ReturnTrue_WhenUserContext_ContainsHouseChoice() {

    userContext.getOnBoardingData()
        .setHouseType(OnboardingConversationDevi.ProductTypes.BRF.toString());

    val canAcceptAnswer = testConversation.canAcceptAnswerToQuestion(userContext);

    assertThat(canAcceptAnswer).isEqualTo(true);
  }
}
