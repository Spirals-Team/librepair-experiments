/*
 * Copyright (C) 2015 Language In Interaction
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
package nl.mpi.tg.eg.experiment.client.presenter;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import nl.mpi.tg.eg.experiment.client.ApplicationController.ApplicationState;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.model.GeneratedStimulus;
import nl.mpi.tg.eg.experiment.client.service.SimuliValidationRunner;
import nl.mpi.tg.eg.experiment.client.view.ComplexView;

/**
 * @since Mar 10, 2015 2:43:42 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public abstract class LocalStoragePresenter extends AbstractPresenter {

    public LocalStoragePresenter(RootLayoutPanel widgetTag) {
        super(widgetTag, new ComplexView());
    }

    @Override
    protected String getTitle() {
        return "Storage Viewer";
    }

    @Override
    protected void setContent(final AppEventListner appEventListner) {

        ((ComplexView) simpleView).addOptionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "Edit Current User Data";
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                appEventListner.requestApplicationState(ApplicationState.metadata);
            }
        });
        ((ComplexView) simpleView).addOptionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return ApplicationState.scores.label;
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                appEventListner.requestApplicationState(ApplicationState.scores);
            }
        });
    }

    protected void eraseLocalStorageButton() {
        ((ComplexView) simpleView).addOptionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "Erase Stored Data";
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                final Storage localStorage = Storage.getLocalStorageIfSupported();
                localStorage.clear();
                Window.Location.replace(Window.Location.getPath());
            }
        });
    }

    protected void stimuliValidation() {
        for (final GeneratedStimulus.Tag tag : GeneratedStimulus.Tag.values()) {
            ((ComplexView) simpleView).addOptionButton(new PresenterEventListner() {
                @Override
                public String getLabel() {
                    return "Stimuli Check: " + tag.name();
                }

                @Override
                public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                    final HTML simuliValidationHtmlText = ((ComplexView) simpleView).addHtmlText("simuliValidation: " + tag.name(), null);
                    final HTML sampleCount = ((ComplexView) simpleView).addHtmlText("sampleCount", null);
                    final HTML uniqueCount = ((ComplexView) simpleView).addHtmlText("uniqueCount", null);
                    final FlexTable outputTable = new FlexTable();
                    ((ComplexView) simpleView).addWidget(outputTable);
                    final FlexTable transitionTable = new FlexTable();
                    ((ComplexView) simpleView).addWidget(transitionTable);
                    new SimuliValidationRunner() {
                        @Override
                        public void appendOutput(String outputString) {
                            simuliValidationHtmlText.setHTML(simuliValidationHtmlText.getHTML() + "<br/>" + outputString);
                        }

                        @Override
                        public void sampleCount(int outputString) {
                            sampleCount.setHTML("sampleCount: " + outputString);
                        }

                        @Override
                        public void uniqueCount(int outputString) {
                            uniqueCount.setHTML("uniqueCount: " + outputString);
                        }

                        @Override
                        public void transitionTableValue(int column, int row, String value) {
                            transitionTable.setText(row, column, value);
                        }

                        @Override
                        public void appendUniqueStimuliList(String outputString) {
                            ((ComplexView) simpleView).addText(outputString);
                        }

                        @Override
                        public void outputTableValue(int column, int row, String value) {
                            outputTable.setText(row, column, value);
                        }

                    }.calculate(tag);
                }

                @Override
                public int getHotKey() {
                    return -1;
                }
            });
        }
    }

    protected void addKeyboardDebug() {
        final Label clickLabel = new Label();
        final Label mouseLabel = new Label();
        final Label wheelLabel = new Label();
        final Label keyDownLabel = new Label();

        ((ComplexView) simpleView).addWidget(clickLabel);
        ((ComplexView) simpleView).addWidget(mouseLabel);
        ((ComplexView) simpleView).addWidget(wheelLabel);
        ((ComplexView) simpleView).addWidget(keyDownLabel);
        RootPanel root = RootPanel.get();
        root.addDomHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                clickLabel.setText(event.toDebugString());
            }
        }, KeyUpEvent.getType());
        root.addDomHandler(new MouseWheelHandler() {
            @Override
            public void onMouseWheel(MouseWheelEvent event) {
                wheelLabel.setText(event.getX() + " : " + event.getY() + " DeltaY: " + event.getDeltaY());
            }
        }, MouseWheelEvent.getType());
        root.addDomHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                mouseLabel.setText(event.getX() + " : " + event.getY());
            }
        }, MouseMoveEvent.getType());
        root.addDomHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
//                final int nativeKeyCode = event.getNativeKeyCode();
                keyDownLabel.setText("NativeKeyCode: " + event.getNativeKeyCode()
                        + " AltKey:" + event.isAltKeyDown()
                        + " ControlKey:" + event.isControlKeyDown()
                        + " DownArrow:" + event.isDownArrow()
                        + " LeftArrow:" + event.isLeftArrow()
                        + " MetaKey:" + event.isMetaKeyDown()
                        + " RightArrow:" + event.isRightArrow()
                        + " ShiftKey:" + event.isShiftKeyDown()
                        + " UpArrow:" + event.isUpArrow());
            }
        }, KeyDownEvent.getType());
    }

    protected void localStorageData() {
        final Storage localStorage = Storage.getLocalStorageIfSupported();
        for (int itemIndex = 0; itemIndex < localStorage.getLength(); itemIndex++) {
            final String key = localStorage.key(itemIndex);
            ((ComplexView) simpleView).addHtmlText(key, "highlightedText");
            ((ComplexView) simpleView).addText(localStorage.getItem(key));
        }
    }
}
