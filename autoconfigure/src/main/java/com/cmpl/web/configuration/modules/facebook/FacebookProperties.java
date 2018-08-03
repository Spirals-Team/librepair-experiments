package com.cmpl.web.configuration.modules.facebook;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.cmpl.web.modules.social.configuration.SocialProperties;

@ConfigurationProperties(prefix = "spring.social.facebook")
public class FacebookProperties extends SocialProperties {

}