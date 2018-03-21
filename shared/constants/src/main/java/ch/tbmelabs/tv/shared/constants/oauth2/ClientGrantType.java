package ch.tbmelabs.tv.shared.constants.oauth2;

public class ClientGrantType {
  public static final String AUTHORIZATION_CODE = "authorization_code";
  public static final String REFRESH_TOKEN = "refresh_token";
  public static final String IMPLICIT = "implicit";
  public static final String PASSWORD = "password";

  private ClientGrantType() {
    // Hidden constructor
  }
}