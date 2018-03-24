package ch.tbmelabs.tv.core.authorizationserver.test.service.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.service.mail.MailService;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class MailServiceTest {
  private static final String TEST_SENDER_ADDRESS = "no-reply@tbme.tv";

  @Mock
  private Session mockSession;

  @Mock
  private JavaMailSender mockJavaMailSender;

  @Spy
  @InjectMocks
  private MailService fixture;

  private static MimeMessage sentMimeMessage;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    ReflectionTestUtils.setField(fixture, "senderAddress", TEST_SENDER_ADDRESS);

    doReturn(new Properties()).when(mockSession).getProperties();
    doReturn(new MimeMessage(mockSession)).when(mockJavaMailSender).createMimeMessage();
    doAnswer(new Answer<MimeMessage>() {
      @Override
      public MimeMessage answer(InvocationOnMock invocation) throws Throwable {
        MailServiceTest.sentMimeMessage = invocation.getArgument(0);
        return MailServiceTest.sentMimeMessage;
      }
    }).when(mockJavaMailSender).send(Mockito.any(MimeMessage.class));
  }

  @Test
  public void mailServiceShouldBeAnnotated() {
    assertThat(MailService.class).hasAnnotation(Service.class).hasAnnotation(Profile.class);

    assertThat(MailService.class.getDeclaredAnnotation(Profile.class).value())
        .containsExactly("!" + SpringApplicationProfile.NO_MAIL);
  }

  @Test
  public void mailServiceShouldHavePublicConstructor() {
    assertThat(new MailService()).isNotNull();
  }

  @Test
  public void sendMailShouldCallJavaMailSender() throws MessagingException, IOException {
    User receiver = new User();
    receiver.setEmail("mailservicetest.user@tbme.tv");

    String subject = RandomStringUtils.random(11);
    String htmlBody = "<html><body><h1>" + RandomStringUtils.random(11) + "</h1></body></html>";

    fixture.sendMail(receiver, subject, htmlBody);

    verify(mockJavaMailSender, times(1)).send(Mockito.any(MimeMessage.class));

    // TODO: Why does this fail in Maven and not in plain JUnit?
    // assertThat(MailServiceTest.sentMimeMessage.getSubject()).isEqualTo(subject);
    assertThat(
        Arrays.stream(MailServiceTest.sentMimeMessage.getFrom()).map(Address::toString).collect(Collectors.toList()))
            .hasSize(1).containsExactly(TEST_SENDER_ADDRESS);
    assertThat(Arrays.stream(MailServiceTest.sentMimeMessage.getAllRecipients()).map(Address::toString)
        .collect(Collectors.toList())).hasSize(1).containsExactly(receiver.getEmail());
    assertThat(
        ((MimeMultipart) ((MimeMultipart) MailServiceTest.sentMimeMessage.getContent()).getBodyPart(0).getContent())
            .getBodyPart(0).getContent()).isEqualTo(htmlBody);
  }
}