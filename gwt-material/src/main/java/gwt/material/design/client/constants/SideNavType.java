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
package gwt.material.design.client.constants;

import gwt.material.design.client.base.helper.EnumHelper;

/**
 * @author kevzlou7979
 * @author Ben Dol
 */
public enum SideNavType implements CssType {
    FIXED("fixed"),
    PUSH("push"),
    PUSH_WITH_HEADER("push-with-header"),
    MINI("mini"),
    MINI_WITH_EXPAND("mini-with-expand"),
    DRAWER("drawer"),
    DRAWER_WITH_HEADER("drawer-with-header"),
    CARD("card");

    private final String cssClass;

    SideNavType(final String cssClass) {
        this.cssClass = cssClass;
    }

    @Override
    public String getCssName() {
        return cssClass;
    }

    public static SideNavType fromStyleName(final String styleName) {
        return EnumHelper.fromStyleName(styleName, SideNavType.class, FIXED);
    }
}