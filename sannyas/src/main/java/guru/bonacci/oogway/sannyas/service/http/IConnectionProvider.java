package guru.bonacci.oogway.sannyas.service.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public interface IConnectionProvider {

	public HttpURLConnection provideConnection(URL url) throws IOException;
}	
