package guru.bonacci.oogway.doorway.services;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.doorway.bigbrother.WatchMe;
import guru.bonacci.oogway.doorway.cheaters.Postponer;
import guru.bonacci.oogway.doorway.clients.AuthClient;
import guru.bonacci.oogway.doorway.clients.OracleClient;
import guru.bonacci.oogway.doorway.security.Credentials;
import guru.bonacci.oogway.shareddomain.GemCarrier;

/**
 * Tier I is the initial support level responsible for basic customer issues. It
 * is synonymous with first-line support, level 1 support, front-end support,
 * support line 1, and various other headings denoting basic level technical
 * support functions. The first job of a Tier I specialist is to gather the
 * customer’s information and to determine the customer’s issue by analyzing the
 * symptoms and figuring out the underlying problem. When analyzing the
 * symptoms, it is important for the technician to identify what the customer is
 * trying to accomplish so that time is not wasted on "attempting to solve a
 * symptom instead of a problem."
 */
@Service
public class FirstLineSupportService {

	@Autowired
	private OracleClient oracleClient;

	@Autowired
	private AuthClient authClient;

	@Autowired
	private Postponer postponer;

//	@WatchMe
	public GemCarrier enquire(String q, String apiKey) {
		if (isEmpty(q))
			return new GemCarrier("No question no answer..", "oogway");

//		Credentials currentUser = authClient.user(apiKey);
		Credentials currentUser = new Credentials();
		currentUser.setPassword("password");
		currentUser.setUsername("oogway");
		Optional<GemCarrier> gem = oracleClient.consult(q, null, currentUser);
		return gem.orElse(new GemCarrier(postponer.saySomething(), "oogway"));
	}
}
