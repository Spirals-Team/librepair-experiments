package com.les.brouilles.planner.rappel.mail.service;

public interface EmailService {

	void sendSimpleMessage(String to, String subject, String text);
}
