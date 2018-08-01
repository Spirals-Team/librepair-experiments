/*
 * Copyright (C) 2014 Language In Interaction
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
package nl.ru.languageininteraction.language.client.presenter;

import nl.mpi.tg.eg.experiment.client.presenter.Presenter;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import nl.mpi.tg.eg.experiment.client.ApplicationController.ApplicationState;
import nl.mpi.tg.eg.experiment.client.exception.AudioException;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.AudioEventListner;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;
import nl.ru.languageininteraction.language.client.view.MatchLanguageView;

/**
 * @since Nov 26, 2014 4:12:27 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class MatchLanguagePresenter implements Presenter {

    protected final RootLayoutPanel widgetTag;
    final AudioPlayer audioPlayer;
    final MatchLanguageView matchLanguageView;
    private PresenterEventListner backEventListner = null;
    private PresenterEventListner nextEventListner = null;

    public MatchLanguagePresenter(RootLayoutPanel widgetTag, final AudioPlayer audioPlayer) throws AudioException {
        matchLanguageView = new MatchLanguageView(audioPlayer);
        this.audioPlayer = audioPlayer;
        this.widgetTag = widgetTag;
    }

    @Override
    public void savePresenterState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fireWindowClosing() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setState(final AppEventListner appEventListner, final ApplicationState prevState, final ApplicationState nextState) {
        widgetTag.clear();
        if (prevState != null) {
            backEventListner = new PresenterEventListner() {

                @Override
                public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                    audioPlayer.stopAll();
                    appEventListner.requestApplicationState(prevState);
                }

                @Override
                public String getLabel() {
                    return prevState.label;
                }

                @Override
                public int getHotKey() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
        } else {
            backEventListner = new PresenterEventListner() {

                @Override
                public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                    audioPlayer.stopAll();
                    appEventListner.requestApplicationState(ApplicationState.menu);
                }

                @Override
                public String getLabel() {
                    return ApplicationState.menu.label;
                }

                @Override
                public int getHotKey() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
        }
        if (nextState != null) {
            nextEventListner = new PresenterEventListner() {

                @Override
                public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                    audioPlayer.stopAll();
                    appEventListner.requestApplicationState(nextState);
                }

                @Override
                public String getLabel() {
                    return nextState.label;
                }

                @Override
                public int getHotKey() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
        }
        matchLanguageView.setupScreen(backEventListner, nextEventListner);
        audioPlayer.setOnEndedListener(new AudioEventListner() {

            @Override
            public void audioEnded() {
                matchLanguageView.showAudioEnded();
            }

            @Override
            public void audioLoaded() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void audioFailed() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        widgetTag.add(matchLanguageView);
    }

    @Override
    public void fireBackEvent() {
        if (backEventListner != null) {
            audioPlayer.stopAll();
            backEventListner.eventFired(null, null);
        }
    }

    @Override
    public void fireResizeEvent() {
        matchLanguageView.resizeView();
    }
}
