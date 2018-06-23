package com.piggymetrics.notification.service;

import com.piggymetrics.notification.domain.NotificationType;
import com.piggymetrics.notification.domain.Recipient;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author yibo
 */
public interface EmailService {

    /**
     * @param type
     * @param recipient
     * @param attachment
     * @throws MessagingException
     * @throws IOException
     */
    void send(NotificationType type, Recipient recipient, String attachment) throws MessagingException, IOException;

}
