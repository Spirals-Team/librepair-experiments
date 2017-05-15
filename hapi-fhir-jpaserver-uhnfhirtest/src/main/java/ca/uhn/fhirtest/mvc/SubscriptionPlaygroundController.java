package ca.uhn.fhirtest.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Bundle.Entry;
import ca.uhn.fhir.model.dstu2.resource.Subscription;
import ca.uhn.fhir.model.dstu2.valueset.SubscriptionChannelTypeEnum;
import ca.uhn.fhir.model.dstu2.valueset.SubscriptionStatusEnum;
import ca.uhn.fhir.rest.client.GenericClient;
import ca.uhn.fhir.to.BaseController;
import ca.uhn.fhir.to.model.HomeRequest;

@org.springframework.stereotype.Controller()
public class SubscriptionPlaygroundController extends BaseController {
	private static final org.slf4j.Logger ourLog = org.slf4j.LoggerFactory.getLogger(SubscriptionPlaygroundController.class);

	@RequestMapping(value = { "/subscriptions" })
	public String subscriptionsHome(final HttpServletRequest theServletRequest, HomeRequest theRequest, final ModelMap theModel) {
		addCommonParams(theServletRequest, theRequest, theModel);

		theModel.put("notHome", true);
		theModel.put("extraBreadcrumb", "Subscriptions");

		ourLog.info(logPrefix(theModel) + "Displayed subscriptions playground page");

		CaptureInterceptor interceptor = new CaptureInterceptor();
		GenericClient client = theRequest.newClient(theServletRequest, getContext(theRequest), myConfig, interceptor);

		//@formatter:off
		Bundle resp = client
			.search()
			.forResource(Subscription.class)
//			.where(Subscription.TYPE.exactly().code(SubscriptionChannelTypeEnum.WEBSOCKET.getCode()))
//			.and(Subscription.STATUS.exactly().code(SubscriptionStatusEnum.ACTIVE.getCode()))
			.sort().descending(Subscription.TYPE)
			.sort().ascending(Subscription.STATUS)
			.returnBundle(Bundle.class)
			.execute();
		//@formatter:off
		
		List<Subscription> subscriptions = new ArrayList<Subscription>();
		for (Entry next : resp.getEntry()) {
			if (next.getResource() instanceof Subscription) {
				subscriptions.add((Subscription) next.getResource());
			}
		}
		
		theModel.put("subscriptions", subscriptions);
		
		return "subscriptions";
	}

}
