package com.baselet.control.config;

public class ConfigMail {
	private static final ConfigMail instance = new ConfigMail();

	public static ConfigMail getInstance() {
		return instance;
	}

	private String mailSmtp = "";
	private boolean mailSmtpAuth = false;
	private String mailSmtpUser = "";
	private boolean mailSmtpPwStore = false;
	private String mailSmtpPw = "";
	private String mailFrom = "";
	private String mailTo = "";
	private String mailCc = "";
	private String mailBcc = "";
	private boolean mailXml = true;
	private boolean mailGif = true;
	private boolean mailPdf = false;

	private ConfigMail() {}

	public String getMailSmtp() {
		return mailSmtp;
	}

	public void setMailSmtp(String mailSmtp) {
		this.mailSmtp = mailSmtp;
	}

	public boolean isMailSmtpAuth() {
		return mailSmtpAuth;
	}

	public void setMailSmtpAuth(boolean mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}

	public String getMailSmtpUser() {
		return mailSmtpUser;
	}

	public void setMailSmtpUser(String mailSmtpUser) {
		this.mailSmtpUser = mailSmtpUser;
	}

	public boolean isMailSmtpPwStore() {
		return mailSmtpPwStore;
	}

	public void setMailSmtpPwStore(boolean mailSmtpPwStore) {
		this.mailSmtpPwStore = mailSmtpPwStore;
	}

	public String getMailSmtpPw() {
		return mailSmtpPw;
	}

	public void setMailSmtpPw(String mailSmtpPw) {
		this.mailSmtpPw = mailSmtpPw;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getMailCc() {
		return mailCc;
	}

	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}

	public String getMailBcc() {
		return mailBcc;
	}

	public void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc;
	}

	public boolean isMailXml() {
		return mailXml;
	}

	public void setMailXml(boolean mailXml) {
		this.mailXml = mailXml;
	}

	public boolean isMailGif() {
		return mailGif;
	}

	public void setMailGif(boolean mailGif) {
		this.mailGif = mailGif;
	}

	public boolean isMailPdf() {
		return mailPdf;
	}

	public void setMailPdf(boolean mailPdf) {
		this.mailPdf = mailPdf;
	}
}
