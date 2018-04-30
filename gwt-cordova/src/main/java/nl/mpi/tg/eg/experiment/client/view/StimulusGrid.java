/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experiment.client.view;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.listener.StimulusButton;

/**
 * @since Oct 6, 2015 1:30:16 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class StimulusGrid extends FlexTable {

    final ArrayList<HandlerRegistration> domHandlerArray = new ArrayList<>();
    final ArrayList<HandlerRegistration> domHandlerArrayParent;

    public StimulusGrid(final ArrayList<HandlerRegistration> domHandlerArrayParent) {
        this.domHandlerArrayParent = domHandlerArrayParent;
    }

    private StimulusButton addButton(final PresenterEventListner menuItemListerner, final ButtonBase pushButton, final int rowIndex, final int columnIndex, final int hotKeyIndex) {
        this.setStylePrimaryName("gridTable");
        pushButton.addStyleName("stimulusButton");
        pushButton.setEnabled(true);
        final SingleShotEventListner singleShotEventListner = new SingleShotEventListner() {

            @Override
            protected void singleShotFired() {
                if (pushButton.isEnabled()) {
                    menuItemListerner.eventFired(pushButton, this);
                    for (HandlerRegistration domHandler : domHandlerArray) {
                        domHandler.removeHandler();
                    }
                }
            }
        };
        pushButton.addClickHandler(singleShotEventListner);
        pushButton.addTouchStartHandler(singleShotEventListner);
        pushButton.addTouchMoveHandler(singleShotEventListner);
        pushButton.addTouchEndHandler(singleShotEventListner);
        final VerticalPanel verticalPanelOuter = new VerticalPanel();
        if (hotKeyIndex > 9) {
            throw new UnsupportedOperationException("Only 0-9 are valid hot keys for the stimuli grid");
        }
        if (hotKeyIndex > -1) {
            RootPanel root = RootPanel.get();
            final HandlerRegistration domHandler = root.addDomHandler(new KeyDownHandler() {
                @Override
                public void onKeyDown(KeyDownEvent event) {
                    if (pushButton.isEnabled()) {
                        final int nativeKeyCode = event.getNativeKeyCode();
                        final int hotKey = KeyCodes.KEY_ZERO + hotKeyIndex;
                        final int hotKeyNum = KeyCodes.KEY_NUM_ZERO + hotKeyIndex;
                        if (nativeKeyCode == hotKey || nativeKeyCode == hotKeyNum) {
                            event.getNativeEvent().preventDefault();
                            singleShotEventListner.eventFired();
                        }
                    }
                }
            }, KeyDownEvent.getType());
            // only one response should be possible for a given grid
            domHandlerArray.add(domHandler);
            // the parent screen needs to remove all handlers when exiting the screen
            domHandlerArrayParent.add(domHandler);
            final Label label = new Label("[" + hotKeyIndex + "]");
            label.setStylePrimaryName("gridCellLabel");
            verticalPanelOuter.add(label);
        }
        final VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setStylePrimaryName("gridCell");
        verticalPanel.add(pushButton);
        verticalPanelOuter.add(verticalPanel);
        this.setWidget(rowIndex, columnIndex, verticalPanelOuter);
        this.getCellFormatter().setHorizontalAlignment(rowIndex, columnIndex, HasHorizontalAlignment.ALIGN_CENTER);
        return new StimulusButton() {
            @Override
            public Widget getWidget() {
                return pushButton;
            }

            @Override
            public void addStyleName(String styleName) {
                pushButton.addStyleName(styleName);
            }

            @Override
            public void removeStyleName(String styleName) {
                pushButton.removeStyleName(styleName);
            }

            @Override
            public void setEnabled(boolean enabled) {
                pushButton.setEnabled(enabled);
            }

            @Override
            public boolean isEnabled() {
                return pushButton.isEnabled();
            }

            @Override
            public void setVisible(boolean visible) {
                pushButton.setVisible(visible);
            }

            @Override
            public void triggerSingleShotEventListner() {
                singleShotEventListner.eventFired();
            }
        };
    }

    public StimulusButton addStringItem(final PresenterEventListner menuItemListerner, final String labelString, final int rowIndex, final int columnIndex, final int hotKeyIndex) {
        final Button pushButton = new Button(labelString);
        return addButton(menuItemListerner, pushButton, rowIndex, columnIndex, hotKeyIndex);
    }

    public StimulusButton addImageItem(final PresenterEventListner menuItemListerner, final SafeUri imagePath, final int rowIndex, final int columnIndex, final String widthString, final String styleName, final int hotKeyIndex) {
        final Image image = new Image(imagePath);
        image.setWidth(widthString);
        if (styleName != null) {
            image.addStyleName(styleName);
        }
        final Button imageButton = new Button();
        imageButton.getElement().appendChild(image.getElement());
        imageButton.addStyleName("stimulusImageButton");
        return addButton(menuItemListerner, imageButton, rowIndex, columnIndex, hotKeyIndex);
    }
}
