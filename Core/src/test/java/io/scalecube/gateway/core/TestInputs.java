package io.scalecube.gateway.core;

public class TestInputs {
  public static final int SID = 42;
  public static final int T = 422;
  public static final int SIG = 422;
  public static final String Q = "/test/test";

  public static final String NO_DATA = "{ \n" +
      "    \"q\":\"" + Q + "\", " +
      "    \"sid\":" + SID + "," +
      "    \"sig\":" + SIG + "," +
      "    \"t\":" + T +
      "}";
}
