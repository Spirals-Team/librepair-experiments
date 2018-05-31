package com.cmpl.web.facebook;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;

public class FacebookCustomApiVersionConnectionFactory extends OAuth2ConnectionFactory<Facebook> {

  public FacebookCustomApiVersionConnectionFactory(String apiVersion, String appId, String appSecret) {
    super("facebook", new FacebookCustomApiVersionServiceProvider(apiVersion, appId, appSecret, null),
        new org.springframework.social.facebook.connect.FacebookAdapter());
  }

}
