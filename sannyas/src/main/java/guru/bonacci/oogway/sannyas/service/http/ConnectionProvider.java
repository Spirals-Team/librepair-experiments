package guru.bonacci.oogway.sannyas.service.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "proxy.enabled", havingValue = "false")
public class ConnectionProvider implements IConnectionProvider {

	public HttpURLConnection provideConnection(URL url) throws IOException {
		return (HttpURLConnection)url.openConnection();
	}	
}	
