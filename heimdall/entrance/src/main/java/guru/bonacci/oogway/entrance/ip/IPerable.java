package guru.bonacci.oogway.entrance.ip;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Iterable supplying an infinite stream of ip addresses
 */
@Component
@Profile("dev")
public class IPerable implements Iterable<String> {

	private int bufferSize = 10;
	private List<String> ipList = new ArrayList<>();

	public IPerable() {
	}

	public IPerable(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public IPerable(Collection<String> newIPs) {
		this.ipList.addAll(newIPs);
	}

	public IPerable(List<String> newIPs, int bufferSize) {
		this(newIPs);
		this.bufferSize = bufferSize;
	}

	@Override
	public Iterator<String> iterator() {
		Iterator<String> ips = new Iterator<String>() {

			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public String next() {
				if (currentIndex >= ipList.size()) {
					try {
						ipList = IPCrawler.crawl(bufferSize);
					} catch (Exception e) {
						e.printStackTrace();
						ipList = asList("119.168.33.192", 
										"23.125.246.164", 
										"78.76.19.148", 
										"218.110.128.1",
										"157.179.74.159", 
										"119.95.229.45", 
										"34.121.147.91", 
										"6.93.254.186", 
										"195.41.169.4",
										"175.113.80.151");
					}
					currentIndex = 0;
				}
				return ipList.get(currentIndex++);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
		return ips;
	}
	
	static class IPCrawler {

		public static final String serviceURL = "http://sqa.fyicenter.com/Online_Test_Tools/Test_IP_Address_Generator.php";

		private static final int DEFAULT_SIZE = 12;

		static List<String> crawl() throws IOException {
			return crawl(DEFAULT_SIZE);
		}
		
		static List<String> crawl(int count) throws IOException {
			Response response = 
	                Jsoup.connect(serviceURL)
	                .userAgent("Mozilla")
	                .method(Method.POST)
	                .data("Count", Integer.toString(count))
	                .data("Submit", "Generate")
	                .execute();
	        
			Element ips = response.parse().select("pre").first();
			return Stream.of(ips.text().split("\n")).collect(toList());
		}
	}
}
