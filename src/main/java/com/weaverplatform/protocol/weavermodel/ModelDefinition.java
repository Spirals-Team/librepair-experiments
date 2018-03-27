package com.weaverplatform.protocol.weavermodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;


/**
 * @author bastbijl, Sysunite 2017
 */
public class ModelDefinition {

  private String name;
  private String version;
  private ModelAuthor author;
  private ModelClasses classes;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public ModelAuthor getAuthor() {
    return author;
  }

  public void setAuthor(ModelAuthor author) {
    this.author = author;
  }

  public ModelClasses getClasses() {
    return classes;
  }

  @JsonProperty("classes")
  public void setClasses(ModelClasses classes) {
    this.classes = classes;
  }
  public void setClasses(Map<String, ModelClass> map) {
    ModelClasses classes = new ModelClasses();
    classes.setClasses(map);
    this.classes = classes;
  }
  public void setClasses(Collection<ModelClass> collection) {
    ModelClasses classes = new ModelClasses();
    for(ModelClass item : collection) {
      classes.dynamic(item.getName(), item);
    }
    this.classes = classes;
  }

  public static boolean illegalName(String toExamine) {
    if(toExamine == null || toExamine.isEmpty()) {
      return true;
    }
    if(StringUtils.isNumeric(toExamine.substring(0, 1))) {
      return true;
    }
    Pattern pattern = Pattern.compile("[^A-Za-z0-9_]");
    Matcher matcher = pattern.matcher(toExamine);
    return matcher.find();
  }

  public static ModelDefinition parse(InputStream inputStream) {

    ObjectMapper mapper = new ObjectMapper(
      new YAMLFactory()
    );

    try {
      return mapper.readValue(inputStream, ModelDefinition.class);
    } catch (Exception e) {
      throw new RuntimeException("Was not able to parse config file: "+e.getMessage());
    }
  }

  public static String write(Object configFile) {

    ObjectMapper mapper = new ObjectMapper(
      new YAMLFactory()
        .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
        .disable(YAMLGenerator.Feature.CANONICAL_OUTPUT)
        .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
        .disable(YAMLGenerator.Feature.INDENT_ARRAYS)
    ).enable(WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);

    final SimpleFilterProvider filterProvider = new SimpleFilterProvider();
    filterProvider.addFilter("skipOptionalField", new SimpleBeanPropertyFilter() {
      @Override
      public void serializeAsField(Object object, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
        if (include(writer)) {
          if(object instanceof ModelClass) {
            if(((ModelClass) object).unsetOptionalFields().contains(writer.getName())) {
              return;
            }
          }
          if(object instanceof ModelAttribute) {
            if(((ModelAttribute) object).unsetOptionalFields().contains(writer.getName())) {
              return;
            }
          }
          if(object instanceof ModelRelation) {
            if(((ModelRelation) object).unsetOptionalFields().contains(writer.getName())) {
              return;
            }
          }
          writer.serializeAsField(object, jgen, provider);
        } else if (!jgen.canOmitFields()) { // since 2.3
          writer.serializeAsOmittedField(object, jgen, provider);
        }
      }
      @Override
      protected boolean include(BeanPropertyWriter writer) {
        return true;
      }
      @Override
      protected boolean include(PropertyWriter writer) {
        return true;
      }
    });

    mapper.setFilterProvider(filterProvider);

    try {
      String yml = mapper.writeValueAsString(configFile);
      yml = yml.replace(" {}","");
      yml = yml.replace("'[","[");
      yml = yml.replace("]'","]");
      yml = yml.trim();
      return yml;
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Was not able to map object to yml: "+e.getMessage());
    }
  }
}
