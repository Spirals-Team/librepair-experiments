/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import nl.mpi.tg.eg.experiment.client.ApplicationController;
import nl.mpi.tg.eg.experiment.client.Version;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.view.ComplexView;

/**
 * @since Dec 27, 2017 14:27 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class TestingVersionPresenter extends LocalStoragePresenter implements Presenter {

    private final Version version = GWT.create(Version.class);
    final ApplicationController.ApplicationState startTesingState;

    public TestingVersionPresenter(RootLayoutPanel widgetTag, final ApplicationController.ApplicationState nextState) {
        super(widgetTag);
        this.startTesingState = nextState;
    }

    @Override
    protected String getTitle() {
        return "Software Testing Version";
    }

    @Override
    protected String getSelfTag() {
        return "SoftwareTestingVersion";
    }

    @Override
    protected void setContent(final AppEventListner appEventListner) {
        ((ComplexView) simpleView).addText("This version is for software testing only, data will not be preserved in this version. This screen will not be shown in the production version.");
        ((ComplexView) simpleView).addPadding();
        ((ComplexView) simpleView).addText("Once you have verified that this version suits your needs, please request deployment of the production version.");
        ((ComplexView) simpleView).addPadding();
        ((ComplexView) simpleView).addPadding();
        ((ComplexView) simpleView).addText("Framework For Interactive Experiments\n" + "Version: "
//                + version.majorVersion() + "."
//                + version.minorVersion() + "."
//                + version.buildVersion() + "-"
                + version.projectVersion() + "\n");
        ((ComplexView) simpleView).addPadding();
        ((ComplexView) simpleView).addOptionButton(new PresenterEventListner() {

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                if (allowBackAction(appEventListner)) {
                    appEventListner.requestApplicationState(startTesingState);
                }
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public String getLabel() {
                return "Begin Software Testing";
            }
        });
    }
}
