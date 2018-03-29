package com.weaverplatform.protocol.weavermodel;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author bastbijl, Sysunite 2017
 */
@JsonSerialize(using = ModelRelationsSerializer.class)
public class ModelRelations {

  private Map<String, ModelRelation> relations = new TreeMap<>();

  @JsonAnySetter
  public void dynamic(String name, ModelRelation object) {
    if(object == null) {
      object = new ModelRelation();
    }
    object.setName(name);
    relations.put(name, object);
  }

  public Map<String, ModelRelation> getRelations() {
    return relations;
  }

  public void setRelations(Map<String, ModelRelation> relations) {
    this.relations = relations;
  }
}

class ModelRelationsSerializer extends StdSerializer<ModelRelations> {

  public ModelRelationsSerializer() {
    this(null);
  }

  public ModelRelationsSerializer(Class<ModelRelations> modelRelations) {
    super(modelRelations);
  }

  @Override
  public void serialize(ModelRelations value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeObject(value.getRelations());
  }
}
