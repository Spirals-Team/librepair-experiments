package io.scalecube.gateway.core;

public interface TestInputs {
  Integer SID = 42;
  Integer I = 422;
  Integer SIG = 422;
  String Q = "/test/test";
  String Q_LONG = new String(new char[16_000]);
  String D_STING =
      "\"StrData\"";

  String NO_DATA = "{" +
      " \"q\":\"" + Q + "\", " +
      " \"sid\":" + SID + "," +
      " \"sig\":" + SIG + "," +
      " \"i\":" + I +
      "}";


  String STRING_DATA_PATTERN_Q_SIG_SID_D = "{" +
      "    \"q\":\"%s\", " +
      "    \"sig\":%d, " +
      "    \"sid\": %d," +
      "    \"d\": %s" +
      "}";
}
