package com.myanmareffective.demo.welcome;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class TicketRevenueTest {

	private TicketRevenue ticketRevenue;
	private BigDecimal expectedRevenue;

	@Before
	public void setUp() {
		ticketRevenue = new TicketRevenue();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void failIfLessThanZeroTicketsAreSold() {
		ticketRevenue.estimateTotalRevenue(-1);
	}

	@Test
	public void zeroSalesEqualsZeroRevenue() {
		assertThat(BigDecimal.ZERO, is(ticketRevenue.estimateTotalRevenue(0)));
	}

	@Test
	public void oneTicketSoldIsThirtyInRevenue() {
		expectedRevenue = new BigDecimal("30");
		assertThat(expectedRevenue, is(ticketRevenue.estimateTotalRevenue(3)));
	}

	@Test
	public void tenTicketsSoldIsThreeHundredInRevenue() {
		expectedRevenue = new BigDecimal("300");
		assertThat(expectedRevenue, is(ticketRevenue.estimateTotalRevenue(10)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void failIfMoreThanOneHundredTicketsAreSold() {
		ticketRevenue.estimateTotalRevenue(101);
	}
}
