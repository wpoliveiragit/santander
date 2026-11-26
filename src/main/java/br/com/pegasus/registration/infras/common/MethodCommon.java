package br.com.pegasus.registration.infras.common;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

public final class MethodCommon {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String objToJson(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JacksonException exception) {
      return "{ERRO}";
    }
  }

}
