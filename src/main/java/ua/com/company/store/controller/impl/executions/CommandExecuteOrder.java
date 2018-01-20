package ua.com.company.store.controller.impl.executions;

import org.apache.log4j.Logger;
import ua.com.company.store.constants.Redirection;
import ua.com.company.store.controller.command.CommandTypical;
import ua.com.company.store.model.entity.Order;
import ua.com.company.store.model.entity.User;
import ua.com.company.store.service.OrderService;
import ua.com.company.store.service.UserService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;
import java.util.Properties;

/**
 * Created by Владислав on 31.12.2017.
 */
public class CommandExecuteOrder implements CommandTypical {
    private OrderService orderService;
    private UserService userService;
    private Logger logger = Logger.getRootLogger();


    public CommandExecuteOrder(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userFormSession;
        if (req.getAttribute("user") != null) {
            userFormSession = (User) req.getAttribute("user");
        } else {
            userFormSession = (User) req.getSession().getAttribute("user");
        }
        if (req.getSession() == null || req.getSession().getAttribute("user") == null || !userFormSession.isRole()) {
            return Redirection.ACCESS_ERROR_PAGE;
        }

        String userNameByOrder = req.getParameter("userName");
        User user = userService.getUserByNickName(userNameByOrder);
        Order order = orderService.getByParameter(user);
        try {
            sendMessage(user.getEmail(), "Test", order.toString());
        } catch (MessagingException e) {
            logger.error("Error with sending message " + e);
        }


        return Redirection.ADMIN_PAGE;
    }

    public void sendMessage(String to, String subj, String text) throws IOException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.mail.ru");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator()
                {
                    protected PasswordAuthentication getPasswordAuthentication()
                    { return new PasswordAuthentication("vd1321@mail.ru","11111111z");     }
                });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("vd1321@mail.ru","11111111z"));
        message.setSubject("fewef");
        message.setContent("fewef", "text/plain");
        message.setRecipients(Message.RecipientType.TO, "vdvoreckij4@gmail.com");


        Transport.send(message);
    }

}



