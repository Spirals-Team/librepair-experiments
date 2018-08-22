package com.hedvig.botService.chat;

import com.hedvig.botService.enteties.SignupCodeRepository;
import com.hedvig.botService.serviceIntegration.claimsService.ClaimsService;
import com.hedvig.botService.serviceIntegration.memberService.MemberService;
import com.hedvig.botService.serviceIntegration.productPricing.ProductPricingService;
import com.hedvig.botService.services.triggerService.TriggerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(Parameterized.class)
public class ConversationFactoryTest {

  private final Class<?> conversationClass;
  @Mock ClaimsService claimsService;
  @Mock SignupCodeRepository signupCodeRepository;
  @Mock ApplicationEventPublisher applicationEventPublisher;
  @Mock StatusBuilder statusBuilder;
  @Mock Environment springEnvironment;
  @Mock private MemberService memberService;
  @Mock private ProductPricingService productPricingService;
  @Mock private TriggerService triggerService;

  public ConversationFactoryTest(Class<?> conversationClass) {
    this.conversationClass = conversationClass;
  }

  @Parameterized.Parameters
  public static Collection<Object> data() {
    return Arrays.asList(
        new Object[] {
          TrustlyConversation.class,
          ClaimsConversation.class,
          CharityConversation.class,
          MainConversation.class,
          OnboardingConversationDevi.class
        });
  }

  @Before
  public void setUp() {
    springEnvironment = Mockito.mock(Environment.class);
    given(springEnvironment.acceptsProfiles("development")).willReturn(true);
  }

  @Test
  public void test() {
    ConversationFactory factory =
        new ConversationFactoryImpl(
            memberService,
            productPricingService,
            triggerService,
            signupCodeRepository,
            applicationEventPublisher,
            claimsService,
            statusBuilder,
            0);

    Conversation conversation = factory.createConversation(conversationClass);

    assertThat(conversation).isNotNull();
    assertThat(conversation).isInstanceOf(conversationClass);
  }
}
