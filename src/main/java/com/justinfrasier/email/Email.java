package com.justinfrasier.email;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {


    //Emailing the Log File
    public boolean sendEmail(String body){

        String email = JsonEmail.getUserName();
        String host = "smtp.gmail.com";
        String port = "465";


        Properties props = new Properties();
        props.put("mail.smtp.user", email);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.debug", "false");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SMTPAuthenticator auth = new SMTPAuthenticator();
        Session session = Session.getInstance(props,auth);
        session.setDebug(false);

        String to = JsonEmail.getsendto();
        String from = JsonEmail.getUserName();
        String subject = "Log";
        Message message = new MimeMessage(session);
        try{
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            return true;
        }catch (MessagingException e){
            return false;
        }
    }

    private class SMTPAuthenticator extends Authenticator{
        public PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(JsonEmail.getUserName(),JsonEmail.getPassword());
        }
    }
}
