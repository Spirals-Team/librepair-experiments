package com.weaverplatform.protocol.weavermodel;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

/**
 * @author bastbijl, Sysunite 2017
 */
public class InlineArraySerializer extends StdSerializer<List<String>> {

  public InlineArraySerializer() {
    this(null);
  }

  public InlineArraySerializer(Class<List<String>> list) {
    super(list);
  }

  @Override
  public void serialize(List<String> list, JsonGenerator gen, SerializerProvider provider) throws IOException {

    String body = String.join(", ", list);
    if(body.isEmpty()) {
      gen.writeString("[]");
      return;
    }

    gen.writeString("[" + body + "]");
  }
}

