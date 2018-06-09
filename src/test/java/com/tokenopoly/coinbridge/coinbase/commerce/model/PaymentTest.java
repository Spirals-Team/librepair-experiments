package com.tokenopoly.coinbridge.coinbase.commerce.model;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class PaymentTest {


  @Test
  public void coverage() {
    final Payment p1 = Payment.builder()
            .build();

    final PaymentPK ppk = new PaymentPK("Test", "fake");
    final Payment p2 = Payment.builder()
            .paymentId(ppk)
            .value(new HashMap<>())
            .build();

    assertNotEquals(p1, p2);

    p1.setNetwork("Test");
    assertNotEquals(p1, p2);

    assertNull(p1.getPriceForKey("foo"));

    p2.setValue(null);
    assertNull(p2.getPriceForKey("local"));

    assertNotEquals(p1.hashCode(), p2.hashCode());

  }

}