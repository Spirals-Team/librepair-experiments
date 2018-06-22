/*
 * Copyright (c) 2018, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.postgresql.util;

import org.postgresql.PGProperty;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyLoader {
  private static final Logger LOGGER = Logger.getLogger("org.postgresql.util.PropertyLogger");

  /**
   * Full libpq-style property loading
   *
   * <p>
   * Loads properties from standard location like PG* env vars. The precedence
   * of the sources of properties is the same as in libpq.
   */
  public static Properties load(String url, Properties info) throws PSQLException {
    return load(url, info, System.getenv());
  }

  public static Properties load(String url, Properties info, Map<String, String> env) throws PSQLException {
    Properties defaults;
    PropertyLoader loader = new PropertyLoader();
    defaults = loader.getDefaultProperties();
    Properties props = new Properties(defaults);
    loadEnv(props, env);
    overrideProperties(props, info);
    if ((props = parseURL(url, props)) == null) {
      return null;
    }
    return props;
  }

  // loadMinimal is a pure pgJDBC-style property loading. It's more an internal
  // method, the Driver.connect() should be used instead.
  public static Properties loadMinimal(String url, Properties info) throws PSQLException {
    Properties defaults;
    PropertyLoader loader = new PropertyLoader();
    defaults = loader.getDefaultProperties();
    Properties props = new Properties(defaults);
    overrideProperties(props, info);
    if ((props = parseURL(url, props)) == null) {
      return null;
    }
    return props;
  }

  // Helper to retrieve default properties from classloader resource
  // properties files.
  private Properties defaultProperties;

  private synchronized Properties getDefaultProperties() throws PSQLException {
    if (defaultProperties != null) {
      return defaultProperties;
    }

    // Make sure we load properties with the maximum possible privileges.
    try {
      defaultProperties =
          AccessController.doPrivileged(new PrivilegedExceptionAction<Properties>() {
            public Properties run() throws IOException {
              return loadDefaultProperties();
            }
          });
    } catch (PrivilegedActionException e) {
      throw new PSQLException(GT.tr("Error loading default settings from driverconfig.properties"),
                              PSQLState.UNEXPECTED_ERROR, (IOException) e.getException());
    }

    return defaultProperties;
  }

  private Properties loadDefaultProperties() throws IOException {
    Properties merged = new Properties();

    try {
      PGProperty.USER.set(merged, System.getProperty("user.name"));
    } catch (SecurityException se) {
      // We're just trying to set a default, so if we can't
      // it's not a big deal.
    }

    // If we are loaded by the bootstrap classloader, getClassLoader()
    // may return null. In that case, try to fall back to the system
    // classloader.
    //
    // We should not need to catch SecurityException here as we are
    // accessing either our own classloader, or the system classloader
    // when our classloader is null. The ClassLoader javadoc claims
    // neither case can throw SecurityException.
    ClassLoader cl = getClass().getClassLoader();
    if (cl == null) {
      cl = ClassLoader.getSystemClassLoader();
    }

    if (cl == null) {
      LOGGER.log(Level.WARNING, "Can't find a classloader for the Driver; not loading driver configuration");
      return merged; // Give up on finding defaults.
    }

    LOGGER.log(Level.FINE, "Loading driver configuration via classloader {0}", cl);

    // When loading the driver config files we don't want settings found
    // in later files in the classpath to override settings specified in
    // earlier files. To do this we've got to read the returned
    // Enumeration into temporary storage.
    ArrayList<URL> urls = new ArrayList<URL>();
    Enumeration<URL> urlEnum = cl.getResources("org/postgresql/driverconfig.properties");
    while (urlEnum.hasMoreElements()) {
      urls.add(urlEnum.nextElement());
    }

    for (int i = urls.size() - 1; i >= 0; i--) {
      URL url = urls.get(i);
      LOGGER.log(Level.FINE, "Loading driver configuration from: {0}", url);
      InputStream is = url.openStream();
      merged.load(is);
      is.close();
    }

    return merged;
  }

  private static void overrideProperties(Properties props, Properties info) throws PSQLException {
    if (info == null) {
      return;
    }

    Set<String> e = info.stringPropertyNames();
    for (String propName : e) {
      String propValue = info.getProperty(propName);
      if (propValue == null) {
        throw new PSQLException(GT.tr("Properties for the driver contains a non-string value for the key ")
                                + propName,
                                PSQLState.UNEXPECTED_ERROR);
      }
      props.setProperty(propName, propValue);
    }
  }

  /**
   * Constructs a new DriverURL, splitting the specified URL into its component parts
   *
   * @param url JDBC URL to parse
   * @param defaults Default properties
   * @return Properties with elements added from the url
   */
  public static Properties parseURL(String url, Properties defaults) {
    Properties urlProps = new Properties(defaults);

    String l_urlServer = url;
    String l_urlArgs = "";

    int l_qPos = url.indexOf('?');
    if (l_qPos != -1) {
      l_urlServer = url.substring(0, l_qPos);
      l_urlArgs = url.substring(l_qPos + 1);
    }

    if (!l_urlServer.startsWith("jdbc:postgresql:")) {
      return null;
    }
    l_urlServer = l_urlServer.substring("jdbc:postgresql:".length());

    if (l_urlServer.startsWith("//")) {
      l_urlServer = l_urlServer.substring(2);
      int slash = l_urlServer.indexOf('/');
      if (slash == -1) {
        return null;
      }
      urlProps.setProperty("PGDBNAME", URLDecoder.decode(l_urlServer.substring(slash + 1)));

      String[] addresses = l_urlServer.substring(0, slash).split(",");
      StringBuilder hosts = new StringBuilder();
      StringBuilder ports = new StringBuilder();
      for (String address : addresses) {
        int portIdx = address.lastIndexOf(':');
        if (portIdx != -1 && address.lastIndexOf(']') < portIdx) {
          String portStr = address.substring(portIdx + 1);
          try {
            // squid:S2201 The return value of "parseInt" must be used.
            // The side effect is NumberFormatException, thus ignore sonar error here
            Integer.parseInt(portStr); //NOSONAR
          } catch (NumberFormatException ex) {
            return null;
          }
          ports.append(portStr);
          hosts.append(address.subSequence(0, portIdx));
        } else {
          ports.append("/*$mvn.project.property.template.default.pg.port$*/");
          hosts.append(address);
        }
        ports.append(',');
        hosts.append(',');
      }
      ports.setLength(ports.length() - 1);
      hosts.setLength(hosts.length() - 1);
      urlProps.setProperty("PGPORT", ports.toString());
      urlProps.setProperty("PGHOST", hosts.toString());
    } else {
      /*
       if there are no defaults set or any one of PORT, HOST, DBNAME not set
       then set it to default
      */
      if (defaults == null || !defaults.containsKey("PGPORT")) {
        urlProps.setProperty("PGPORT", "/*$mvn.project.property.template.default.pg.port$*/");
      }
      if (defaults == null || !defaults.containsKey("PGHOST")) {
        urlProps.setProperty("PGHOST", "localhost");
      }
      if (defaults == null || !defaults.containsKey("PGDBNAME")) {
        urlProps.setProperty("PGDBNAME", URLDecoder.decode(l_urlServer));
      }
    }

    // parse the args part of the url
    String[] args = l_urlArgs.split("&");
    for (String token : args) {
      if (token.isEmpty()) {
        continue;
      }
      int l_pos = token.indexOf('=');
      if (l_pos == -1) {
        urlProps.setProperty(token, "");
      } else {
        urlProps.setProperty(token.substring(0, l_pos), URLDecoder.decode(token.substring(l_pos + 1)));
      }
    }

    return urlProps;
  }

  public static void loadEnv(Properties props, Map<String, String> env) {
    if (env.containsKey("PGHOST")) {
      PGProperty.PG_HOST.set(props, env.get("PGHOST"));
    }
    if (env.containsKey("PGPORT")) {
      PGProperty.PG_PORT.set(props, env.get("PGPORT"));
    }
    if (env.containsKey("PGDATABASE")) {
      PGProperty.PG_DBNAME.set(props, env.get("PGDATABASE"));
    }
    if (env.containsKey("PGUSER")) {
      PGProperty.USER.set(props, env.get("PGUSER"));
    }
    if (env.containsKey("PGPASSWORD")) {
      PGProperty.PASSWORD.set(props, env.get("PGPASSWORD"));
    }
    if (env.containsKey("PGAPPAME")) {
      PGProperty.APPLICATION_NAME.set(props, env.get("PGAPPNAME"));
    }
  }
}
