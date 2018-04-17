package lv.robertsv.webjob;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@EnableWebSocketMessageBroker
public class WebJobsWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	public static String JOB_STATUS_URL = "/jobstatus";
	public static String WEB_SOCKET_URL = "/webjobsocket";

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker(JOB_STATUS_URL);
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(WEB_SOCKET_URL).withSockJS();
	}

}
