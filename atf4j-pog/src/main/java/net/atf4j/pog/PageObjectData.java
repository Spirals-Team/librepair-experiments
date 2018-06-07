/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.pog;

import java.util.ArrayList;
import java.util.List;

import net.atf4j.core.TestResultsReporting;

/**
 * PageObject data class. <code>
 * &#64;FindBy(id = "")
 * &#64;FindBy(name = "")
 * &#64;FindBy(linkText = "")
 * &#64;FindBy(partialLinkText = "")
 * &#64;FindBy(className = "")
 * &#64;FindBy(xpath = "")
 * &#64;FindBy(css = "")
 * </code>
 */
public class PageObjectData extends TestResultsReporting {

    /** page elements. */
    private final List<WebElementField> pageElements = new ArrayList<WebElementField>();

    /** content elements. */
    private final List<WebElementField> contentElements = new ArrayList<WebElementField>();

    /** input elements. */
    private final List<WebElementField> inputElements = new ArrayList<WebElementField>();

    /** navigation elements. */
    private final List<WebElementField> navElements = new ArrayList<WebElementField>();

    /**
     * Instantiates a new page object data.
     */
    public PageObjectData() {
        super();
    }

    /**
     * Add the web page element.
     *
     * @param pageWebElement the page web element
     * @return this for a fluent interface.
     */
    public PageObjectData add(final WebElementField pageWebElement) {
        this.pageElements.add(pageWebElement);
        return this;
    }

    /**
     * Adds a content element.
     *
     * @param contentMethod the content method
     * @return this for a fluent interface.
     */
    public PageObjectData addContent(final WebElementField contentMethod) {
        this.contentElements.add(contentMethod);
        return this;
    }

    /**
     * Adds the input element.
     *
     * @param inputMethod the input method
     * @return this for a fluent interface.
     */
    public PageObjectData addInput(final WebElementField inputMethod) {
        this.inputElements.add(inputMethod);
        return this;
    }

    /**
     * Add the navigation element.
     *
     * @param navElement the nav element.
     * @return this for a fluent interface.
     */
    public PageObjectData addNav(final WebElementField navElement) {
        this.navElements.add(navElement);
        return this;
    }

    /**
     * Get the page element.
     *
     * @return the list of page-elements.
     */
    public List<WebElementField> get() {
        return this.pageElements;
    }

    /**
     * Get the content elements.
     *
     * @return the list of content elements.
     */
    public List<WebElementField> getContent() {
        return this.contentElements;
    }

    /**
     * Get the input elements.
     *
     * @return the list of input elements.
     */
    public List<WebElementField> getInput() {
        return this.inputElements;
    }

    /**
     * Get the navigation elements.
     *
     * @return the list of navigation elements.-
     */
    public List<WebElementField> getNav() {
        return this.navElements;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final WebElementField pageWebElement : this.pageElements) {
            stringBuilder.append(pageWebElement);
        }
        for (final WebElementField pageWebElement : this.navElements) {
            stringBuilder.append(pageWebElement);
        }
        for (final WebElementField pageWebElement : this.inputElements) {
            stringBuilder.append(pageWebElement);
        }
        for (final WebElementField pageWebElement : this.contentElements) {
            stringBuilder.append(pageWebElement);
        }
        return stringBuilder.toString();
    }

}
