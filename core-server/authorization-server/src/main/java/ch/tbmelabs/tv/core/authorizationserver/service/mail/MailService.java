package ch.tbmelabs.tv.core.authorizationserver.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Service
@Profile({ "!" + SpringApplicationProfile.NO_MAIL })
public class MailService {
  protected static final Logger LOGGER = LogManager.getLogger(MailService.class);

  @Value("${spring.mail.username}")
  private String senderAddress;

  @Autowired
  private JavaMailSender mailSender;

  public void sendMail(User receiver, String subject, String htmlMessage) {
    LOGGER.info("Sending email from " + senderAddress + " to " + receiver.getEmail());

    try {
      MimeMessage message = mailSender.createMimeMessage();
      message.setSubject(subject);

      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom(senderAddress);
      helper.setTo(receiver.getEmail());

      helper.setText(htmlMessage, true);

      mailSender.send(message);
    } catch (MessagingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}