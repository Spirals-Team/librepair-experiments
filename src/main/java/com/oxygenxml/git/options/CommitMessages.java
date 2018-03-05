package com.oxygenxml.git.options;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.oxygenxml.git.utils.Equaler;


/**
 * Entity for the JAXB to store the list of commit messages. Stores last 7
 * committed messages
 * 
 * @author Beniamin Savu
 *
 */
@XmlRootElement(name = "commitMessages")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommitMessages {

	/**
	 * The last 7 committed messages
	 */
	@XmlElement(name = "message")
	private List<String> messages = new ArrayList<>();

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	  boolean toReturn = false;
	  if (obj instanceof CommitMessages) {
	    CommitMessages mess = (CommitMessages) obj;
	    toReturn = Equaler.verifyListEquals(messages, mess.getMessages());
	  }
	  return toReturn;
	}

}