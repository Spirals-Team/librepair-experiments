package io.symonk.sylenium.location;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.internal.*;

/**
 * $ylenium custom web element implementation for use with page factory Empowers the webelement to
 * Contain a mass of chainable, fluent, concise capabilities.
 * Provides a means of finding element and element(s), location of element (co-ordinates) and a wrapped driver & underlying WebElement
 */
public interface $Element
    extends WebElement,
        FindsById,
        FindsByCssSelector,
        FindsByXPath,
        FindsByName,
        FindsByTagName,
        FindsByLinkText,
        WrapsDriver,
        WrapsElement,
        Locatable {

    /**
     * Retrieve the text value of an element
     * By text value, we assume the attribute 'value'
     * @return the text presented in an element
     */
    String getValue();

    /**
     * Call click on a web element using a virtual mouse.
     */
    @Override
    void click();




}
