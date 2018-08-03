package com.cmpl.web.facebook;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Facebook ServiceProvider implementation that allows to change Facebook API version.
 */
public class FacebookCustomApiVersionServiceProvider extends AbstractOAuth2ServiceProvider<Facebook> {

  private final String appNamespace;

  private final String apiVersion;

  /**
   * Creates a FacebookServiceProvider for the given API version, application ID, secret, and namespace.
   *
   * @param apiVersion Facebook API version
   * @param appId The application's App ID as assigned by Facebook
   * @param appSecret The application's App Secret as assigned by Facebook
   * @param appNamespace The application's App Namespace as configured with Facebook. Enables use of Open Graph
   *          operations.
   */
  public FacebookCustomApiVersionServiceProvider(String apiVersion, String appId, String appSecret, String appNamespace) {
    super(getOAuth2Template(apiVersion, "https://graph.facebook.com/v" + apiVersion + "/", appId, appSecret));
    this.apiVersion = apiVersion;
    this.appNamespace = appNamespace;
  }

  private static OAuth2Template getOAuth2Template(String apiVersion, String graphApiUrl, String appId, String appSecret) {
    OAuth2Template oAuth2Template = new OAuth2Template(appId, appSecret, "https://www.facebook.com/v" + apiVersion
        + "/dialog/oauth", graphApiUrl + "oauth/access_token");
    oAuth2Template.setUseParametersForClientAuthentication(true);
    return oAuth2Template;
  }

  public Facebook getApi(String accessToken) {
    FacebookTemplate facebook = new FacebookTemplate(accessToken, appNamespace);
    facebook.setApiVersion(apiVersion);
    return facebook;
  }

}
