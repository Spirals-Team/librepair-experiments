package guru.bonacci.oogway.sannyas.service.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@ConditionalOnProperty(name = "proxy.enabled", havingValue = "true")
public class ProxyConnectionProvider implements IConnectionProvider {

	private Proxy proxy;
	
	public ProxyConnectionProvider( @Value("${proxy.host}") String host, 
									@Value("${proxy.port}") int port, 
									@Value("${proxy.type}") String proxyType) {
		proxy = new Proxy(Proxy.Type.valueOf(proxyType), new InetSocketAddress(host, port));
	}

	public HttpURLConnection provideConnection(URL url) throws IOException {
		return (HttpURLConnection)url.openConnection(proxy);
	}	
}	
