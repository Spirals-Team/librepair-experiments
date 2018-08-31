package io.testrex.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Failure
 */
@Validated
@Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-30T21:24:04.222Z")
@Entity
public class Failure {
  @JsonProperty("message")
  @Column
  private String message = null;

  // this is a field only in json
  // in xml it represents the textual content of the element
  @JsonProperty("content")
  @Column
  private String content = null;

  @JsonProperty("time")
  @Column
  private BigDecimal time = null;

  @JsonProperty("type")
  @JacksonXmlText
  @Column
  private String type = null;

  public Failure message(String message) {
    this.message = message;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Failure content(String content) {
    this.content = content;
    return this;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Failure time(BigDecimal time) {
    this.time = time;
    return this;
  }

  @Valid
  public BigDecimal getTime() {
    return time;
  }

  public void setTime(BigDecimal time) {
    this.time = time;
  }

  public Failure type(String type) {
    this.type = type;
    return this;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Failure failure = (Failure) o;
    return Objects.equals(this.message, failure.message) && Objects.equals(this.content, failure.content) && Objects.equals(this.time, failure.time)
        && Objects.equals(this.type, failure.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, content, time, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Failure {\n");

    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
