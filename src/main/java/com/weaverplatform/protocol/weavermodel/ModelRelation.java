package com.weaverplatform.protocol.weavermodel;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author bastbijl, Sysunite 2017
 */
@JsonFilter("skipOptionalField")
@JsonPropertyOrder({ "key", "range", "card", "items" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelRelation {

  @JsonIgnore
  private String name;
  private String customKey;
  @JsonSerialize(using = InlineArraySerializer.class)
  private List<String> range;
  @JsonSerialize(using = InlineArraySerializer.class)
  private List<String> items;
  @JsonSerialize(using = InlineArraySerializer.class)
  private List<String> card;

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

  public List<String> getRange() {
    return range;
  }

  public void setRange(List<String> range) {
    this.range = range;
  }

  public List<String> getItems() {
    return items;
  }

  public void setItems(List<String> items) {
    this.items = items;
  }

  public List<String> getCard() {
    return card;
  }

  public void setCard(List<String> card) {
    this.card = card;
  }

  public Set<String> unsetOptionalFields() {
    HashSet<String> set = new HashSet<>();
    if(customKey == null) {
      set.add("key");
    }
    return set;
  }
}
