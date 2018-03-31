package guru.bonacci.oogway.web.ip;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Iterator;

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
	static final String DOCKER_IP_2 = "172.19.0.1";

	private Iterator<String> iperator;

	@Autowired
	public IPologist(IPerable iperable) {
		iperator = iperable.iterator();
	}

	@Override
	public String checkUp(String ipIn) {
		String ipOut = ipIn == null || LOCAL_IP_1.equals(ipIn) || LOCAL_IP_2.equals(ipIn) || DOCKER_IP_2.equals(ipIn)
				? iperator.next()
				: ipIn;
		logger.debug(ipIn + " becomes " + ipOut);
		return ipOut;
	}
}
