package com.mailosaur;

import java.util.Random;

import com.mailosaur.implementation.MailosaurBaseClientImpl;
import com.microsoft.rest.RestClient;
import com.microsoft.rest.ServiceResponseBuilder;
import com.microsoft.rest.credentials.BasicAuthenticationCredentials;
import com.microsoft.rest.serializer.JacksonAdapter;

public class MailosaurClient extends MailosaurBaseClientImpl {
	public MailosaurClient(String apiKey) {
		this(buildRestClient(apiKey, null));
    }
	
	public MailosaurClient(String apiKey, String baseUrl) {
		this(buildRestClient(apiKey, baseUrl));
    }
	
	private MailosaurClient(RestClient restClient) {
		super(restClient);
	}
	
	private static RestClient buildRestClient(String apiKey, String baseUrl) {
		baseUrl = (baseUrl != null) ? baseUrl : DEFAULT_BASE_URL;
		return new RestClient.Builder()
	        .withBaseUrl(baseUrl)
			.withCredentials(new BasicAuthenticationCredentials(apiKey, ""))
			.withUserAgent("mailosaur-java/5.0.0")
	        .withResponseBuilderFactory(new ServiceResponseBuilder.Factory())
	        .withSerializerAdapter(new JacksonAdapter())
	        .build();
	}
}
