package guru.bonacci.oogway.doorway.ip;

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

	List<String> ips;

	private Iterator<String> iperator;

	@PostConstruct
	public void init() {
		ips = Arrays.asList("0:0:0:0:0:0:0:1", 
							"127.0.0.1", 
							//TODO list all possible docker-machine ip's
							"172.18.0.1", 
							"172.18.0.20", 
							"172.19.0.1", 
							"172.20.0.1", 
							"172.21.0.1",
							"172.22.0.1"
							);
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
