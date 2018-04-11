package guru.bonacci.oogway.shareddomain;


import static java.lang.String.format;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

/**
 * https://rlogiacco.wordpress.com/2010/05/25/five-reasons-to-hate-dtos/
 * I personally don't hate DTO's, but I also don't like them.
 * Therefore an IDTO: Incognito-DTO.
 */
public class GemCarrier {

	@ApiModelProperty(notes = "Once said", required = true)
	@NotBlank
	private String saying;

	@ApiModelProperty(notes = "By a certain individual")
	private String author;

	public GemCarrier() {
	}

	public GemCarrier(String saying) {
		this.saying = saying;
	}

	public GemCarrier(String saying, String author) {
		this(saying);
		this.author = author;
	}

	public String getSaying() {
		return saying;
	}

	public void setSaying(String saying) {
		this.saying = saying;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return format("GemCarrier[saying='%s', author='%s']", saying, author);
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
