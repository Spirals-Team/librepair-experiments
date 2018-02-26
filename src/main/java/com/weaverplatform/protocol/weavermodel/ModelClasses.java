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
@JsonSerialize(using = ModelClassesSerializer.class)
public class ModelClasses {

  private Map<String, ModelClass> classes = new TreeMap<>();

  @JsonAnySetter
  public void dynamic(String name, ModelClass object) {
    if(object == null) {
      object = new ModelClass();
    }
    object.setName(name);
    classes.put(name, object);
  }

  public Map<String, ModelClass> getClasses() {
    return classes;
  }

  public void setClasses(Map<String, ModelClass> classes) {
    this.classes = classes;
  }
}

class ModelClassesSerializer extends StdSerializer<ModelClasses> {

  public ModelClassesSerializer() {
    this(null);
  }

  public ModelClassesSerializer(Class<ModelClasses> modelClasses) {
    super(modelClasses);
  }

  @Override
  public void serialize(ModelClasses value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeObject(value.getClasses());
  }
}
