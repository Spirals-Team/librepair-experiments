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
package gwt.material.design.client.ui;

import com.google.gwt.dom.client.Document;
import gwt.material.design.client.base.HasAxis;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.mixin.CssNameMixin;
import gwt.material.design.client.constants.Axis;
import gwt.material.design.client.constants.CssName;
import gwt.material.design.client.constants.Orientation;

//@formatter:off

/**
 * Cards are a convenient means of displaying content composed of different types
 * of objects. They’re also well-suited for presenting similar objects whose size
 * or supported actions can vary considerably, like photos with captions of variable
 * length.
 * <h3>UiBinder Usage:</h3>
 * <pre>
 * {@code <!-- Basic Card -->
 * <m:MaterialCard backgroundColor="BLUE_GREY_DARKEN_1" grid="l3">
 *   <m:MaterialCardContent textColor="WHITE">
 *     <m:MaterialCardTitle text="Sample" iconType="POLYMER" iconPosition="RIGHT"/>
 *     <m:MaterialLabel text="I am a very simple card. I am good at containing small bits of information. I am convenient because I require little markup to use effectively." />
 *   </m:MaterialCardContent>
 *   <m:MaterialCardAction>
 *     <m:MaterialLink text="Link 1" iconType="POLYMER" iconPosition="LEFT"/>
 *     <m:MaterialLink text="Link 1" iconType="POLYMER" iconPosition="LEFT"/>
 *   </m:MaterialCardAction>
 * </m:MaterialCard>
 *
 * <!-- Image Card -->
 * <m:MaterialCard backgroundColor="WHITE" grid="l3">
 *   <m:MaterialCardImage waves="LIGHT">
 *     <m:MaterialImage url="http://assets.materialup.com/uploads/ac9bf2ac-bf1c-4dc0-b655-0e13bf523bc8/20150710-__.png"/>
 *     <m:MaterialCardTitle text="Sample" iconPosition="RIGHT"/>
 *   </m:MaterialCardImage>
 *
 *   <m:MaterialCardContent textColor="BLACK">
 *     <m:MaterialLabel text="I am a very simple card. I am good at containing small bits of information. I am convenient because I require little markup to use effectively." />
 *   </m:MaterialCardContent>
 *
 *   <m:MaterialCardAction>
 *     <m:MaterialLink text="Link 1" iconType="POLYMER" iconPosition="LEFT"/>
 *     <m:MaterialLink text="Link 1" iconType="POLYMER" iconPosition="LEFT"/>
 *   </m:MaterialCardAction>
 * </m:MaterialCard>
 *
 * <!-- Reveal Card -->
 * <m:MaterialCard backgroundColor="WHITE" grid="l3">
 *   <m:MaterialCardImage waves="LIGHT">
 *     <m:MaterialImage url="http://assets.materialup.com/uploads/b6992fb2-7bf4-401d-a233-e34a486b9337/gif.gif"/>
 *   </m:MaterialCardImage>
 *
 *   <m:MaterialCardContent textColor="BLACK">
 *     <m:MaterialCardTitle text="Sample" iconType="MORE_VERT" iconPosition="RIGHT" textColor="BLACK"/>
 *   </m:MaterialCardContent>
 *
 *   <m:MaterialCardReveal>
 *     <m:MaterialCardTitle text="Sample" iconType="CLOSE" iconPosition="RIGHT" textColor="BLACK"/>
 *     <m:MaterialLabel text="Here is some more information about this product that is only revealed once clicked on." />
 *   </m:MaterialCardReveal>
 *
 *   <m:MaterialCardAction>
 *     <m:MaterialLink text="Link 1" textColor="BLUE" iconType="POLYMER" iconPosition="LEFT"/>
 *     <m:MaterialLink text="Link 1" textColor="BLUE" iconType="POLYMER" iconPosition="LEFT"/>
 *   </m:MaterialCardAction>
 * </m:MaterialCard>}
 * </pre>
 *
 * @author kevzlou7979
 * @author Ben Dol
 * @see <a href="http://gwtmaterialdesign.github.io/gwt-material-demo/#cards">Material Cards</a>
 * @see <a href="https://material.io/guidelines/components/cards.html">Material Design Specification</a>
 */
//@formatter:on
public class MaterialCard extends MaterialWidget implements HasAxis {

    private CssNameMixin<MaterialCard, Axis> axisMixin;

    /**
     * Creates an empty card.
     */
    public MaterialCard() {
        super(Document.get().createDivElement(), CssName.CARD);
    }

    @Override
    public void setGrid(String grid) {
        super.setGrid(grid);
        addStyleName(CssName.NO_PADDING);
    }

    /**
     * Replace by {@link MaterialWidget#setOrientation(Orientation)}
     * @param axis
     */
    @Deprecated
    @Override
    public void setAxis(Axis axis) {
        getAxisMixin().setCssName(axis);
    }

    /**
     * Replace by {@link MaterialWidget#getOrientation()}
     * @return
     */
    @Deprecated
    @Override
    public Axis getAxis() {
        return getAxisMixin().getCssName();
    }

    protected CssNameMixin<MaterialCard, Axis> getAxisMixin() {
        if (axisMixin == null) {
            axisMixin = new CssNameMixin<>(this);
        }
        return axisMixin;
    }
}