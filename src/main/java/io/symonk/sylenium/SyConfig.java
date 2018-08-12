package io.symonk.sylenium;

import lombok.extern.slf4j.Slf4j;

/** @author SimonK */
@Slf4j
public class SyConfig {

  private static final String SYLENIUM_WAIT = "sylenium.wait";
  private static final String SYLENIUM_POLL = "sylenium.poll";
  private static final String SYLENIUM_BROWSER = "sylenium.browser";
  private static final String SYLENIUM_HEADLESS = "sylenium.headless";
  private static final String SYLENIUM_DISTRIBUTED = "sylenium.distributed";
  private static final String SYLENIUM_POWERED = "sylenium.powered";
  private static final String SYLENIUM_MAXIMIZED = "sylenium.maximized";

  /**
   * Explicit wait for predicaments, will wait for verification of webelement state for this amount of time.
   * By default, explicit wait is 10 seconds, time should be provided in milliseconds
   */
  public static long $wait = parseLongFromSystemPropertyString(SYLENIUM_WAIT, "5000");

  /**
   * Polling interval to run against predicaments
   * By default, polling occurrs every 100 milliseconds
   */
  public static long $poll = parseLongFromSystemPropertyString(SYLENIUM_POLL, "100");

  /**
   * Get the specified browser
   * By default this is 'chrome'
   */
  public static String $browser = System.getProperty(SYLENIUM_BROWSER, "chrome");

  /**
   * Determine if the browser should be executed headlessly
   * By default, this is false
   */
  public static boolean $headless = Boolean.valueOf(System.getProperty(SYLENIUM_HEADLESS, "false"));

  /**
   * Property to determine if sylenium should spawn and use RemoteWebDrivers for a grid setup etc
   * By default, this is false
   */
  public static boolean $distributed = parseBooleanFromSystemPropertyString(SYLENIUM_DISTRIBUTED, "false");

  /**
   * Property to determine if sylenide should handle driver binary management
   * By default, this is true
   */
  public static boolean $powered = parseBooleanFromSystemPropertyString(SYLENIUM_POWERED, "true");

  /**
   * Property to determine if we should maximise the driver window
   * By default, this is true
   */
  public static boolean $maximize = parseBooleanFromSystemPropertyString(SYLENIUM_MAXIMIZED, "true");


  public static void rebuildConfiguration() {
    $wait = parseLongFromSystemPropertyString(SYLENIUM_WAIT, "5000");
    $poll = parseLongFromSystemPropertyString(SYLENIUM_POLL, "100");
    $browser = System.getProperty(SYLENIUM_BROWSER, "chrome");
    $headless = parseBooleanFromSystemPropertyString(SYLENIUM_HEADLESS, "false");
    $distributed = parseBooleanFromSystemPropertyString(SYLENIUM_DISTRIBUTED, "false");
    $powered = parseBooleanFromSystemPropertyString(SYLENIUM_POWERED, "true");
    $maximize = parseBooleanFromSystemPropertyString(SYLENIUM_MAXIMIZED, "true");
  }

  private static Long parseLongFromSystemPropertyString(final String propertyKey, final String defaultValue) {
    return Long.parseLong(System.getProperty(propertyKey, defaultValue));
  }

  private static boolean parseBooleanFromSystemPropertyString(final String propertyKey, final String defaultValue) {
    return Boolean.parseBoolean(System.getProperty(propertyKey, defaultValue));
  }

}
