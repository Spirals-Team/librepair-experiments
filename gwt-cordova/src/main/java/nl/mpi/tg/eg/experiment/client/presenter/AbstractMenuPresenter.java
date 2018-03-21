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
package nl.mpi.tg.eg.experiment.client.presenter;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import nl.mpi.tg.eg.experiment.client.ApplicationController;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
import nl.mpi.tg.eg.experiment.client.view.MenuView;
import nl.mpi.tg.eg.experiment.client.view.SimpleView;

/**
 * @since Jul 14, 2015 1:37:00 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AbstractMenuPresenter extends AbstractPresenter implements Presenter {

    protected final LocalStorage localStorage;
    protected final UserResults userResults;
    private final List<ApplicationController.ApplicationState> nonCompletedScreens = new ArrayList<>();

    public AbstractMenuPresenter(RootLayoutPanel widgetTag, SimpleView simpleView, UserResults userResults, LocalStorage localStorage) {
        super(widgetTag, simpleView);
        this.userResults = userResults;
        this.localStorage = localStorage;
    }

    public void allMenuItems(final AppEventListner appEventListner, final ApplicationController.ApplicationState selfApplicationState) {
        for (final ApplicationController.ApplicationState currentAppState : ApplicationController.ApplicationState.values()) {
            if (currentAppState.label != null && selfApplicationState != currentAppState) {
                menuItem(appEventListner, currentAppState, currentAppState.label, -1);
            }
        }
    }

    public void menuItem(final AppEventListner appEventListner, final ApplicationController.ApplicationState target, final String menuLabel, final int hotkey) {
        final boolean screenCompleted = Boolean.parseBoolean(localStorage.getStoredDataValue(userResults.getUserData().getUserId(), "completed-screen-" + target.name()));
        if (!screenCompleted) {
            nonCompletedScreens.add(target);
        }
        ((MenuView) simpleView).addMenuItem(new PresenterEventListner() {

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                appEventListner.requestApplicationState(target);
            }

            @Override
            public String getLabel() {
                return menuLabel + ((screenCompleted) ? " (complete)" : "");
            }

            @Override
            public int getHotKey() {
                return hotkey;
            }
        }, true);
    }

    public void activateRandomItem(final AppEventListner appEventListner) {
        final ApplicationController.ApplicationState target;
        if (nonCompletedScreens.isEmpty()) {
            target = nextState;
        } else {
            target = nonCompletedScreens.get(new Random().nextInt(nonCompletedScreens.size()));
        }
        Timer timer = new Timer() {
            public void run() {
                appEventListner.requestApplicationState(target);
            }
        };
        timer.schedule(100);
    }
}
