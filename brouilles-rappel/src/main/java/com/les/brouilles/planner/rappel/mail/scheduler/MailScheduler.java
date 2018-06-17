package com.les.brouilles.planner.rappel.mail.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.les.brouilles.planner.rappel.mail.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailScheduler {

	@Autowired
	private EmailService emailService;

	@Scheduled(fixedRate = 100000)
	public void reportCurrentTime() {
		log.info("Sending email");
		// emailService.sendSimpleMessage("vienne.julien@gmail.com", "Test SB",
		// "Test OK");
	}
}