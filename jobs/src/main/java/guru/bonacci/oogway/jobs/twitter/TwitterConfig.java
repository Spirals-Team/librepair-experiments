package guru.bonacci.oogway.jobs.twitter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@RefreshScope
@Configuration
public class TwitterConfig {

	@Bean
	public Twitter twitterTemplate( @Value("${twitter.oauth.consumerKey}") String consumerKey, 
									@Value("${twitter.oauth.consumerSecret}") String consumerSecret, 
									@Value("${twitter.oauth.accessToken}") String accessToken, 
									@Value("${twitter.oauth.accessTokenSecret}") String accessTokenSecret) {
		return new TwitterTemplate(
				consumerKey, 
				consumerSecret, 
				accessToken,
				accessTokenSecret);
	}
}