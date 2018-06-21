/*
 * Copyright (c) 2018, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.postgresql.test.jdbc2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.postgresql.Driver;
import org.postgresql.PGProperty;
import org.postgresql.test.TestUtil;
import org.postgresql.util.NullOutputStream;
import org.postgresql.util.WriterHandler;
import org.postgresql.util.PropertyLoader;

import org.junit.Test;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class PropertyLoaderTest {

  /*
   * This tests the acceptsURL() method with a couple of well and poorly formed jdbc urls.
   */
  @Test
  public void testAcceptsURL() throws Exception {
    TestUtil.initDriver(); // Set up log levels, etc.

    // Load the driver (note clients should never do it this way!)
    PropertyLoader ldr = new PropertyLoader();
    assertNotNull(ldr);

    // These are always correct
    verifyUrl(ldr, "jdbc:postgresql:test", "localhost", "5432", "test");
    verifyUrl(ldr, "jdbc:postgresql://localhost/test", "localhost", "5432", "test");
    verifyUrl(ldr, "jdbc:postgresql://localhost:5432/test", "localhost", "5432", "test");
    verifyUrl(ldr, "jdbc:postgresql://127.0.0.1/anydbname", "127.0.0.1", "5432", "anydbname");
    verifyUrl(ldr, "jdbc:postgresql://127.0.0.1:5433/hidden", "127.0.0.1", "5433", "hidden");
    verifyUrl(ldr, "jdbc:postgresql://[::1]:5740/db", "[::1]", "5740", "db");

    // failover urls
    verifyUrl(ldr, "jdbc:postgresql://localhost,127.0.0.1:5432/test", "localhost,127.0.0.1",
        "5432,5432", "test");
    verifyUrl(ldr, "jdbc:postgresql://localhost:5433,127.0.0.1:5432/test", "localhost,127.0.0.1",
        "5433,5432", "test");
    verifyUrl(ldr, "jdbc:postgresql://[::1],[::1]:5432/db", "[::1],[::1]", "5432,5432", "db");
    verifyUrl(ldr, "jdbc:postgresql://[::1]:5740,127.0.0.1:5432/db", "[::1],127.0.0.1", "5740,5432",
        "db");
  }

  private void verifyUrl(PropertyLoader ldr, String url, String hosts, String ports, String dbName)
    throws Exception {
    Method parseMethod =
      ldr.getClass().getDeclaredMethod("parseURL", String.class, Properties.class);
    parseMethod.setAccessible(true);
    Properties p = (Properties) parseMethod.invoke(ldr, url, null);
    assertTrue(url != null);
    assertEquals(url, dbName, p.getProperty(PGProperty.PG_DBNAME.getName()));
    assertEquals(url, hosts, p.getProperty(PGProperty.PG_HOST.getName()));
    assertEquals(url, ports, p.getProperty(PGProperty.PG_PORT.getName()));
  }

  @Test
  public void testEnvironment() throws Exception {
    Map<String, String> env = new HashMap();
    env.put("PGHOST", "localhost");
    env.put("PGPORT", "5432");
    env.put("PGUSER", "me");
    env.put("PGAPPNAME", "myapp");

    Properties props = new Properties();
    PropertyLoader.loadEnv(props, env);

    assertTrue(PGProperty.PG_HOST.isPresent(props));
    assertEquals("localhost", PGProperty.PG_HOST.get(props));

    assertTrue(PGProperty.PG_PORT.isPresent(props));
    assertEquals(5432, PGProperty.PG_PORT.getInt(props));

    assertTrue(PGProperty.USER.isPresent(props));
    assertEquals("me", PGProperty.USER.get(props));
  }
}
