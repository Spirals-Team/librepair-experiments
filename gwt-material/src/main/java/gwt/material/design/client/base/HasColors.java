/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2017 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package gwt.material.design.client.base;

import gwt.material.design.client.constants.Color;

public interface HasColors {

    /**
     * Sets the background color of material components, for example:<br/><br/>
     * <pre>panel.setBackgroundColor(BLUE);</pre><br/>
     * Refer to - http://gwtmaterialdesign.github.io/gwt-material-demo/#!colors for the color pallete.
     */
    void setBackgroundColor(Color bgColor);

    Color getBackgroundColor();

    /**
     * Set the text color of material components, for example:<br/><br/>
     * <pre>panel.setTextColor(BLUE_DARKEN_2);</pre><br/>
     * Refer to - http://gwtmaterialdesign.github.io/gwt-material-demo/#!colors for the color pallete.
     */
    void setTextColor(Color textColor);

    Color getTextColor();
}
