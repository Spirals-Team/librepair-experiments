package com.weaverplatform.protocol.weavermodel;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

/**
 * @author bastbijl, Sysunite 2017
 */
@JsonFilter("skipOptionalField")
@JsonPropertyOrder({ "id", "super", "attributes", "relations" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ModelClass {

  @JsonIgnore
  private String name;
  private String customId;
  private ArrayList<String> supers = new ArrayList<>();
  private ModelAttributes attributes;
  private ModelRelations relations;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    if(customId == null) {
      return name;
    }
    return customId;
  }

  public void setId(String id) {
    this.customId = id;
  }


  public ArrayList<String> getSuper() {
    return supers;
  }


  @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
  public void setSuper(ArrayList<String> supers) {
    this.supers = supers;
  }

  public ModelAttributes getAttributes() {
    return attributes;
  }

  @JsonProperty("attributes")
  public void setAttributes(ModelAttributes attributes) {
    this.attributes = attributes;
  }
  public void setAttributes(Map<String, ModelAttribute> map) {
    ModelAttributes attributes = new ModelAttributes();
    attributes.setAttributes(map);
    this.attributes = attributes;
  }
  public void setAttributes(Collection<ModelAttribute> collection) {
    ModelAttributes attributes = new ModelAttributes();
    for(ModelAttribute item : collection) {
      attributes.dynamic(item.getName(), item);
    }
    this.attributes = attributes;
  }

  public ModelRelations getRelations() {
    return relations;
  }

  @JsonProperty("relations")
  public void setRelations(ModelRelations relations) {
    this.relations = relations;
  }
  public void setRelations(Map<String, ModelRelation> map) {
    ModelRelations relations = new ModelRelations();
    relations.setRelations(map);
    this.relations = relations;
  }
  public void setRelations(Collection<ModelRelation> collection) {
    ModelRelations relations = new ModelRelations();
    for(ModelRelation item : collection) {
      relations.dynamic(item.getName(), item);
    }
    this.relations = relations;
  }

  public Set<String> unsetOptionalFields() {
    HashSet<String> set = new HashSet<>();
    if(customId == null) {
      set.add("id");
    }
    return set;
  }
}
