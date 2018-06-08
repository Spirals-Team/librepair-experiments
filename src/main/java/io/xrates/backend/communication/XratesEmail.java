package io.xrates.backend.communication;

import io.xrates.backend.datamodel.beans.User;

import java.util.List;


public interface XratesEmail {
	
	public void addTo(User user);
	public void addTo(List<User> users);
	public void addSubject(String subject);
	public void addTextBody(String body);
	public void addHTMLBody(String body);
	public void setFromEmail(String email);
	public void setFromName(String name);
	public void sendMail();
	
}
