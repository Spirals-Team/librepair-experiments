package edu.rosehulman.billing.router;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import edu.rosehulman.billing.datastore.TierDatastore;
import edu.rosehulman.billing.datastore.UserDatastore;
import edu.rosehulman.billing.models.Tier;
import edu.rosehulman.billing.models.User;
import spark.Request;
import spark.Response;

public class AddTierToUserHandlerTest {

	TierDatastore tierDatastore;
	UserDatastore userDatastore;
	AddTierToUserHandler handler;
	Request request;
	Response response;
	User user;
	Tier tier;

	@Before
	public void setUp() throws Exception {
		tierDatastore = EasyMock.createMock(TierDatastore.class);
		userDatastore = EasyMock.createMock(UserDatastore.class);

		handler = new AddTierToUserHandler(userDatastore, tierDatastore);

		request = EasyMock.createMock(Request.class);
		response = EasyMock.createMock(Response.class);
		user = EasyMock.createMock(User.class);
		tier = EasyMock.createMock(Tier.class);
	}

	@Test
	public void test() throws Exception {
		EasyMock.expect(request.params(":partnerId")).andReturn("testPartnerID");
		EasyMock.expect(request.params(":productId")).andReturn("testProductID");
		EasyMock.expect(request.params(":tierId")).andReturn("testTierID");
		EasyMock.expect(request.params(":quotaId")).andReturn("testQuotaID");
		EasyMock.expect(request.params(":userId")).andReturn("testUserID");

		EasyMock.expect(userDatastore.getUser("testPartnerID", "testProductID", "testUserID")).andReturn(user);
		EasyMock.expect(
				tierDatastore.getTier("testPartnerID", "testProductID", "testUserID", "testQuotaID", "testTierID"))
				.andReturn(tier);
		user.setTier(tier);
		EasyMock.expectLastCall();
		EasyMock.expect(userDatastore.updateUser(user)).andReturn("");

		EasyMock.replay(tier, tierDatastore, user, userDatastore, request, response);

		handler.handle(request, response);

		EasyMock.verify(tier, tierDatastore, user, userDatastore, request, response);
	}

}
