package io.xrates.backend.communication;

import io.xrates.backend.datamodel.beans.User;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGridException;

@Component
public class XratesEmailSendGridImpl implements XratesEmail {
	
	private Logger log = LoggerFactory.getLogger(XratesEmailSendGridImpl.class.getName());
	private static SendGrid sendGrid = new SendGrid("api-key-here");
	private String subject = "";
	private String textBody = "";
	private String htmlBody = "";
	private String fromEmail = "no-reply@xrates.io";
	private String fromName = "Exchange Rates";
	private List<User> toList = null;

	@Override
	public void addTo(User user) {
		if (toList == null) {
			toList = new ArrayList<User>();
		}
		toList.add(user);
	}

	@Override
	public void addTo(List<User> users) {
		if (toList == null) {
			toList = users;
		} else {
			toList.addAll(users);
		}
	}

	@Override
	public void addSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public void addTextBody(String textBody) {
		this.textBody = textBody;
	}

	@Override
	public void addHTMLBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}
	
	@Override
	public void setFromEmail(String emailAddress) {
		fromEmail = emailAddress;
	}
	
	@Override
	public void setFromName(String name) {
		this.fromName = name;
	};

	@Override
	public void sendMail() {
		for (User user:toList) {
			Email email = new Email();
			email.addTo(user.getEmail());
			email.setFrom(fromEmail);
			email.setFromName(fromName);
			if (subject != null && !subject.equals("")) {
				email.setSubject(subject);
			}
			if (textBody != null && !textBody.equals("")) {
				email.setText(textBody);
			}
			if (htmlBody != null && !htmlBody.equals("")) {
				email.setHtml(htmlBody);
			}
			try {
				sendGrid.send(email);
			} catch (SendGridException e) {
				log.error("SendGridException in sending email : " + e);
			}
		}
	}

}
