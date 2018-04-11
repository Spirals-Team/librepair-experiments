package guru.bonacci.oogway.entrance.ip;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class IPologist implements IIPologist {

	private final Logger logger = getLogger(this.getClass());

	static final String LOCAL_IP_1 = "0:0:0:0:0:0:0:1";
	static final String LOCAL_IP_2 = "127.0.0.1";
	static final String DOCKER_IP_1 = "172.19.0.1";
	static final String DOCKER_IP_2 = "172.20.0.1";
	static final String DOCKER_IP_3 = "172.21.0.1";
	static final String DOCKER_IP_4 = "172.22.0.1";

	List<String> ips;

	private Iterator<String> iperator;

	@PostConstruct
	public void init() {
		ips = Arrays.asList(LOCAL_IP_1, LOCAL_IP_2, DOCKER_IP_1, DOCKER_IP_2, DOCKER_IP_3, DOCKER_IP_4);
	}

	@Autowired
	public IPologist(IPerable iperable) {
		iperator = iperable.iterator();
	}

	@Override
	public String checkUp(String ipIn) {
		String ipOut = ipIn == null || ips.contains(ipIn) ? iperator.next() : ipIn;
		logger.debug(ipIn + " becomes " + ipOut);
		return ipOut;
	}
}
