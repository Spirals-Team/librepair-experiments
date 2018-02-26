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
@JsonSerialize(using = ModelAttributesSerializer.class)
public class ModelAttributes {

  private Map<String, ModelAttribute> attributes = new TreeMap<>();

  @JsonAnySetter
  public void dynamic(String name, ModelAttribute object) {
    if(object == null) {
      object = new ModelAttribute();
    }
    object.setName(name);
    attributes.put(name, object);
  }

  public Map<String, ModelAttribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, ModelAttribute> attributes) {
    this.attributes = attributes;
  }
}

class ModelAttributesSerializer extends StdSerializer<ModelAttributes> {

  public ModelAttributesSerializer() {
    this(null);
  }

  public ModelAttributesSerializer(Class<ModelAttributes> modelAttributes) {
    super(modelAttributes);
  }

  @Override
  public void serialize(ModelAttributes value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeObject(value.getAttributes());
  }
}
