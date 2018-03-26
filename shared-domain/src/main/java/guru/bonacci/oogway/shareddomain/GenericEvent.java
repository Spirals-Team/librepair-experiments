package guru.bonacci.oogway.shareddomain;

import static java.lang.String.format;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GenericEvent implements Serializable {

	private static final long serialVersionUID = -241744111039377832L;

	private String content;

	public GenericEvent() {}

	public GenericEvent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return format("Event[content='%s']", content);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
