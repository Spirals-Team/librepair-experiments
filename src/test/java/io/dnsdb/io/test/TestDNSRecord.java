package io.dnsdb.io.test;

import org.junit.Test;

import io.dnsdb.api.DNSRecord;

import static org.junit.Assert.assertEquals;

/**
 * <code>DNSRecord</code>用于测试{@link DNSRecord}
 *
 * @author Remonsan
 */
public class TestDNSRecord {

  @Test
  public void testToString() {
    DNSRecord record = new DNSRecord();
    record.setHost("www.foo.com");
    record.setType("CNAME");
    record.setValue("foo.com");
    assertEquals("DNSRecord{host='www.foo.com', type='CNAME', value='foo.com'}", record.toString());
  }
}
