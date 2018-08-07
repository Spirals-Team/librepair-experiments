/*
 * Copyright (c) 2018-2018 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openpojo.dns.service.java.v9;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.openpojo.dns.DnsControl;
import com.openpojo.dns.service.java.reflection.ReflectionHelper;
import com.openpojo.dns.service.lookup.SimpleNameServiceLookup;
import com.openpojo.reflection.java.load.ClassUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xbill.DNS.Lookup;

import static com.openpojo.dns.constants.TestConstants.SERVER_1_IPv4_BYTES;
import static com.openpojo.dns.constants.TestConstants.SERVER_1_IPv6_BYTES;
import static com.openpojo.dns.constants.TestConstants.SERVER_1_NAME;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 * @author oshoukry
 */
public class Java9NameServiceInterceptorTest {
  private Java9NameServiceInterceptor java9NameServiceInterceptor;

  @Before
  public void setup() throws ClassNotFoundException {
    assumeThat(ClassUtil.loadClass("java.net.InetAddress$NameService"), notNullValue());

    Lookup.refreshDefault();
    DnsControl.recreateInstance().registerRoutingResolver();

    java9NameServiceInterceptor = new Java9NameServiceInterceptor();
    java9NameServiceInterceptor.changeInetAddressProxy(java9NameServiceInterceptor.createProxyForNameService());
  }

  @After
  public void teardown() {
    Object value = ReflectionHelper.invokeMethodOnClass(InetAddress.class, "createNameService", null);
    ReflectionHelper.setFieldValue(InetAddress.class, "nameService", null, value);
    Lookup.refreshDefault();
  }

  @Test
  public void forwardResolution() throws UnknownHostException {
    final InetAddress[] loopBackAddresses = InetAddress.getAllByName(SERVER_1_NAME);

    assertThat(loopBackAddresses.length, greaterThan(0));
    for (InetAddress address : loopBackAddresses) {
      assertThat(address.getHostName(), is(SERVER_1_NAME));
      assertThat(address.getAddress(), anyOf(is(SERVER_1_IPv4_BYTES), is(SERVER_1_IPv6_BYTES)));
    }
  }

  @Test
  public void reverseResolution() throws UnknownHostException {
    final InetAddress byAddress = InetAddress.getByAddress(SERVER_1_IPv4_BYTES);
    assertThat(byAddress.getHostName(), is(SERVER_1_NAME));
  }

  @Test
  public void proxyServiceShouldHandleToString() throws ClassNotFoundException {
    final Object proxyForNameService = java9NameServiceInterceptor.createProxyForNameService();
    assertThat(proxyForNameService.toString(), startsWith(SimpleNameServiceLookup.class.getName() + ".Generated_Proxy"));
  }

  @Test
  public void proxyServiceShouldNotThrowExceptionOnEquals() throws ClassNotFoundException {
    final Object proxyForNameService = java9NameServiceInterceptor.createProxyForNameService();
    assertThat(proxyForNameService.equals(new Object()), is(false));
  }

  @Test
  public void proxyServiceShouldNotThrowExceptionOnHashCode() throws ClassNotFoundException {
    final Object proxyForNameService = java9NameServiceInterceptor.createProxyForNameService();
    assertThat(proxyForNameService.hashCode(), notNullValue());
  }
}
