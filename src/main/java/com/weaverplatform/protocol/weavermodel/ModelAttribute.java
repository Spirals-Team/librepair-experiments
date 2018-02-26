package com.weaverplatform.protocol.weavermodel;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author bastbijl, Sysunite 2017
 */
@JsonFilter("skipOptionalField")
@JsonPropertyOrder({ "key", "datatype", "required" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelAttribute {

  @JsonIgnore
  private String name;
  private String customKey;
  private String datatype;
  private Boolean required;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKey() {
    if(customKey == null) {
      return name;
    }
    return customKey;
  }

  public void setKey(String key) {
    this.customKey = key;
  }

  public String getDatatype() {
    return datatype;
  }

  public void setDatatype(String datatype) {
    this.datatype = datatype;
  }

  public Boolean getRequired() {
    return required;
  }

  public void setRequired(Boolean required) {
    this.required = required;
  }

  public Set<String> unsetOptionalFields() {
    HashSet<String> set = new HashSet<>();
    if(customKey == null) {
      set.add("key");
    }
    return set;
  }
}
