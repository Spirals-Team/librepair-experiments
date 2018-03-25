package com.revature.project2.helpers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Json {
  public static byte[] toJson(Object object) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    try {
      return mapper.writeValueAsBytes(object);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
      return new byte[]{};
    }
  }
}
