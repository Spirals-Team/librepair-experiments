/*
 * Copyright (C) 2010-2014 Hamburg Sud and the contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aludratest.service.gui.component;


/**
 * Represents a radio button on a GUI.
 * @author Joerg Langnickel
 * @author Volker Bergmann
 */
public interface RadioButton extends Element<RadioButton> {

    /** Selects this radio button. */
    public void select();

    /** Selects this radio button if and only if the passed string parameter has the value <code>"true"</code>. In every other
     * case, no action is performed.
     * 
     * @param value String parameter to indicate if a select operation shall be performed on this radio button. */
    public void select(String value);

    /** Asserts that the radio button is checked */
    public void assertChecked();

    /** Asserts that this Radio button is in the expected state, passed by expected string. If the expected string is null or
     * marked as null, no operation will be executed.
     * 
     * @param expected <code>"true"</code> or <code>"false"</code>, or <code>null</code> or marked as null to not perform any
     *            assertion. */
    public void assertChecked(String expected);

    /** Returns if the checkbox is currently checked or not.
     * 
     * @return <code>true</code> if the checkbox is currently checked (has a checkmark in its box), <code>false</code> otherwise. */
    public boolean isChecked();

}
