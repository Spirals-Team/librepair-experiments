package codeu.model.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

public class Util {

  /**
   * Returns the date and time in the format: Thu, 14 Jun 2018 05:01:09 GMT
   */
  public static String FormatDateTime(Instant creationTime) {
    return DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC).format(creationTime);
  }

  /**
   * Sends an email to the user in case they forget their password.
   */
  public static String sendEmail(String name, String to, String code, String url) {
    String from = "angoma@codeustudents.com";
    String subject = "CodeByter Password Reset";

    String messageText = String.format("Hello %s, \n\n" +
            "Can't remember your password? \n" +
            "It happens to the best of us.\n\n" +
            "Use this temporary code to login:\t\t%s\n\n" +
            "Use this link to login:\t%s\n\n" +
            "Thanks, \nThe CodeByter's Security Team", name, code, url);

    Properties props = new Properties();
    Session mailSession = Session.getDefaultInstance(props, null);
    try {
      Message msg = new MimeMessage(mailSession);
      InternetAddress[] addressTo = {new InternetAddress(to)};
      InternetAddress addressFrom = new InternetAddress(from);

      msg.setFrom(addressFrom);
      msg.addRecipients(Message.RecipientType.TO, addressTo);
      msg.setSubject(subject);
      msg.setSentDate(new Date());
      msg.setText(messageText);
      Transport.send(msg);
    } catch (AddressException email) {
      return "Your email address was NOT valid.";
    } catch (MessagingException mex) {
      return "Sorry. The server could not sent that request. Please create a new account.";
    }
    return "Sended";
  }

  /**
   * For security purpose:
   * Hides the email of the User to the public.
   * Ex: an474@cornell.edu
   * return: an***@c******.***
   *
   * @return
   */
  public static String transform(String email) {
    StringBuilder transform = new StringBuilder();
    int index = email.indexOf("@");
    String part1 = email.substring(index);
    int index2 = part1.indexOf(".") + email.substring(0, index).length();

    for (int i = 0; i < index; i++) {
      if (i == 0 || i == 1) transform.append(email.charAt(i));
      else transform.append("*");
    }
    transform.append("@" + part1.charAt(1));
    for (int i = index + 2; i < index2; i++) {
      transform.append("*");
    }
    transform.append(".");
    for (int i = index2 + 1; i < email.length(); i++) {
      transform.append("*");
    }
    return transform.toString();
  }
}