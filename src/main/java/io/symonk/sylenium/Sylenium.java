package io.symonk.sylenium;

import io.symonk.sylenium.commands.driver.Navigator;

public class Sylenium {

  private static Navigator navigator = new Navigator();

  public static void $go(final String absoluteUrl) {
    navigator.go(absoluteUrl);
  }











}
