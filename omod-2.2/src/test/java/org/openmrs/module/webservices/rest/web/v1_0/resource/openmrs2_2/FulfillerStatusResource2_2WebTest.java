/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs2_2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.v1_0.RestTestConstants2_2;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;
import org.springframework.mock.web.MockHttpServletRequest;

public class FulfillerStatusResource2_2WebTest extends MainResourceControllerTest {
	
	@Before
	public void setUp() throws Exception {
		Order order = Context.getOrderService().getOrderByUuid(RestTestConstants2_2.ORDER_UUID).copy();
		order.setUuid(RestTestConstants2_2.ORDER_UUID_Web);
		order.setOrderId(null);
		Context.getOrderService().saveOrder(order, null);
	}
	
	@Test
	public void shouldCreateNew() throws Exception {
		Order.FulfillerStatus fulfillerStatus = Order.FulfillerStatus.RECEIVED;
		String fillerComment = "A example comment from a filler";
		SimpleObject post = new SimpleObject().add("fulfillerStatus", fulfillerStatus)
		        .add("fulfillerComment", fillerComment);
		MockHttpServletRequest request = newPostRequest(getURI(), post);
		
		handle(request);
		System.out.println("Executing and stuff2");
		
		Order order = Context.getOrderService().getOrderByUuid(RestTestConstants2_2.ORDER_UUID_Web);
		assertEquals(order.getFulfillerStatus(), fulfillerStatus.toString());
		assertEquals(order.getFulfillerComment(), fillerComment);
	}
	
	@Override
	public String getURI() {
		return "order/" + RestTestConstants2_2.ORDER_UUID_Web + "/fulfillerstatus";
	}
	
	@Override
	public String getUuid() {
		return null;
	}
	
	@Override
	public long getAllCount() {
		return 0;
	}
}
