package guru.bonacci.oogway.entrance.bigbrother;

import static org.slf4j.LoggerFactory.getLogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.entrance.clients.LumberjackClient;
import guru.bonacci.oogway.entrance.events.SpectreGateway;
import guru.bonacci.oogway.entrance.exceptions.GreedyException;
import guru.bonacci.oogway.entrance.ip.IIPologist;
import guru.bonacci.oogway.entrance.utils.IPCatcher;
import guru.bonacci.oogway.shareddomain.COMINT;

@Aspect
@Component
public class BigBrother {

	private final Logger logger = getLogger(this.getClass());

	private static final Long GREED_STARTS_HERE = 10l;
	
	@Autowired
	private LumberjackClient lumberClient;

	@Autowired
	public IPCatcher iPCatcher;

	@Autowired
	private IIPologist ipologist;

	@Autowired
	private SpectreGateway gateway;

	@Before("@annotation(WatchMe) && args(q,apiKey)")
	public void spreadTheNews(JoinPoint joinPoint, String q, String apiKey) throws Throwable {
		// eavesdrop
		String ip = ipologist.checkUp(iPCatcher.getClientIp());
		logger.info(ip + " said '" + q + "'");
		gateway.send(new COMINT(ip, q));


		// and block the greedy clients
		long visits = lumberClient.visits(apiKey);
		if (visits >= GREED_STARTS_HERE) { //TODO this could be user specific info
			throw new GreedyException();
		}
	}
}
