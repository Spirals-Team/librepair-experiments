package io.symonk.sylenium.location;

import org.openqa.selenium.By;

/** Custom selector methods to locate $Elements */
public class Sylector {

  /**
   * Find an element By its Identifer 'id='
   * @param elementIdentifier -> the element id text value to find
   * @return -> a By locator
   */
  public By $id(final String elementIdentifier) {
    return By.id(elementIdentifier);
  }

  public By byId(final String elementIdentifier) {
    return $id(elementIdentifier);
  }


}
