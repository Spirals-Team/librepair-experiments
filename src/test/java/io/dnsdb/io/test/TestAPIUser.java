package io.dnsdb.io.test;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.dnsdb.api.APIUser;

import static org.junit.Assert.assertEquals;

/**
 * <code>TestAPIUser</code>用于测试{@link io.dnsdb.api.APIUser}
 *
 * @author Remonsan
 */
public class TestAPIUser {

  @Test
  public void testToString() throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date creationTime = format.parse("2018/02/05 10:00:00");
    Date expirationTime = format.parse("2018/03/05 10:00:00");
    APIUser user = new APIUser("", "", 0, creationTime, expirationTime);
    String string = "APIUser{apiId='', user='', remainingRequests=0, creationTime=Mon Feb 05 10:00:00 CST 2018, expirationTime=Mon Mar 05 10:00:00 CST 2018}";
    assertEquals(string, user.toString());

  }
}
