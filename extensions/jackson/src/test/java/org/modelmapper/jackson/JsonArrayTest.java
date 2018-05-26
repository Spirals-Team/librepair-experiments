package org.modelmapper.jackson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.testng.Assert.assertEquals;

@Test
public class JsonArrayTest {
  static class Dest {
    private String target;
    private Date date;

    public String getTarget() {
      return target;
    }

    public void setTarget(String target) {
      this.target = target;
    }

    public Date getDate() {
      return date;
    }

    public void setDate(Date date) {
      this.date = date;
    }
  }

  private ModelMapper modelMapper;

  @BeforeMethod
  public void setUp() {
    modelMapper = new ModelMapper();
  }

  public void shouldMap() throws IOException, ParseException {
    final Converter<String, Date> dateConverter = new Converter<String, Date>() {
      @Override
      public Date convert(MappingContext<String, Date> context) {
        try {
          return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(context.getSource());
        } catch (ParseException e) {
          return null;
        }
      }
    };
    modelMapper.getConfiguration().addValueReader(new JsonNodeValueReader());
    modelMapper.getConfiguration().getConverters().add(new ArrayNodeConverter());

    JsonNode source = new ObjectMapper().readTree(
        "[{\"source\":\"a\",\"date\":\"2018-06-18 08:00:00\"},{\"source\":\"a1\",\"date\":\"2018-06-18 08:00:00\"}]");
    Class<?> destType = new TypeToken<List<Dest>>() {}.getRawType();

    modelMapper.addMappings(new PropertyMap<JsonNode, Dest>() {
      @Override
      protected void configure() {
        map(source("source")).setTarget(null);
        using(dateConverter).map(source("date")).setDate(null);
      }
    });

    @SuppressWarnings("unchecked")
    List<Dest> dests = (List<Dest>) modelMapper.map(source, destType);
    assertEquals(dests.size(), 2);
    assertEquals(dests.get(0).date, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        .parse("2018-06-18 08:00:00"));
    assertEquals(dests.get(0).target, "a");
    assertEquals(dests.get(1).date, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        .parse("2018-06-18 08:00:00"));
    assertEquals(dests.get(1).target, "a");

  }
}
