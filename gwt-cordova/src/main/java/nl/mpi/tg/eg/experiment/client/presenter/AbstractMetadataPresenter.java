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
package nl.mpi.tg.eg.experiment.client.presenter;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import nl.mpi.tg.eg.experiment.client.ApplicationController.ApplicationState;
import nl.mpi.tg.eg.experiment.client.exception.DataSubmissionException;
import nl.mpi.tg.eg.experiment.client.view.MetadataView;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.model.MetadataField;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.service.MetadataFieldProvider;
import nl.mpi.tg.eg.experiment.client.exception.MetadataFieldException;
import nl.mpi.tg.eg.experiment.client.exception.UserIdException;
import nl.mpi.tg.eg.experiment.client.listener.DataSubmissionListener;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.listener.StimulusButton;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import nl.mpi.tg.eg.experiment.client.model.DataSubmissionResult;
import nl.mpi.tg.eg.experiment.client.model.UserData;
import nl.mpi.tg.eg.experiment.client.model.UserId;
import nl.mpi.tg.eg.experiment.client.model.UserLabelData;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService;

/**
 * @since Oct 21, 2014 11:50:56 AM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public abstract class AbstractMetadataPresenter extends AbstractPresenter implements Presenter {

    final MetadataFieldProvider metadataFieldProvider = new MetadataFieldProvider();
    protected final UserResults userResults;
    private final DataSubmissionService submissionService;
    final LocalStorage localStorage;

    public AbstractMetadataPresenter(RootLayoutPanel widgetTag, DataSubmissionService submissionService, UserResults userResults, final LocalStorage localStorage) {
        super(widgetTag, new MetadataView());
        this.localStorage = localStorage;
        this.userResults = userResults;
        this.submissionService = submissionService;
    }

    @Override
    public void setState(final AppEventListner appEventListner, ApplicationState prevState, final ApplicationState nextState) {
        super.setState(appEventListner, prevState, null);
        this.nextState = nextState;
    }

    protected void saveMetadataButton(final String buttonLabel, final boolean sendData, final String networkErrorMessage, final TimedStimulusListener errorEventListner, final TimedStimulusListener successEventListner) {
        PresenterEventListner saveEventListner = new PresenterEventListner() {

            @Override
            public void eventFired(final ButtonBase button, final SingleShotEventListner singleShotEventListner) {
                try {
                    ((MetadataView) simpleView).setButtonError(false, button, null);
                    ((MetadataView) simpleView).clearErrors();
                    validateFields();
                    saveFields();
                    if (sendData) {
                        // this assumes that the user will not get to this page unless they have already agreed to submit data
                        submissionService.submitMetadata(userResults, new DataSubmissionListener() {

                            @Override
                            public void scoreSubmissionFailed(DataSubmissionException exception) {
                                if (exception.getErrorType() == DataSubmissionException.ErrorType.dataRejected) {
                                    ((MetadataView) simpleView).setButtonError(true, button, exception.getMessage());
                                } else {
                                    ((MetadataView) simpleView).setButtonError(true, button, networkErrorMessage);
                                    errorEventListner.postLoadTimerFired();
                                }
                                submissionService.submitScreenChange(userResults.getUserData().getUserId(), "submitMetadataFailed");
                            }

                            @Override
                            public void scoreSubmissionComplete(JsArray<DataSubmissionResult> highScoreData) {
                                successEventListner.postLoadTimerFired();
                            }
                        });
                    } else {
                        successEventListner.postLoadTimerFired();
                    }
                } catch (MetadataFieldException exception) {
                    ((MetadataView) simpleView).showFieldError(exception.getMetadataField());
                }
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public String getLabel() {
                return buttonLabel;
            }
        };

        ((MetadataView) simpleView).addOptionButton(saveEventListner);
    }

    protected void validateFields() throws MetadataFieldException {
        for (MetadataField fieldName : ((MetadataView) simpleView).getFieldNames()) {
            String fieldString = ((MetadataView) simpleView).getFieldValue(fieldName);
            fieldName.validateValue(fieldString);
        }
    }

    protected void saveFields() {
        for (MetadataField fieldName : ((MetadataView) simpleView).getFieldNames()) {
            String fieldString = ((MetadataView) simpleView).getFieldValue(fieldName);
            userResults.getUserData().setMetadataValue(fieldName, fieldString);
            try {
                UserId fieldConnection = ((MetadataView) simpleView).getFieldConnection(fieldName);
                userResults.getUserData().setMetadataConnection(fieldName, fieldConnection);
            } catch (UserIdException exception) {
                // this should not occur since the field value should have originated from a UserId instance
            }
        }
        localStorage.storeData(userResults, metadataFieldProvider);
    }

    protected void selectUserMenu(final AppEventListner appEventListner) {
        for (final UserLabelData labelData : localStorage.getUserIdList(metadataFieldProvider.workerIdMetadataField)) {
            final StimulusButton optionButton = ((MetadataView) simpleView).addOptionButton(new PresenterEventListner() {

                @Override
                public String getLabel() {
                    return labelData.getUserName();
                }

                @Override
                public int getHotKey() {
                    return -1;
                }

                @Override
                public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                    try {
                        userResults.setUser(localStorage.getStoredData(labelData.getUserId(), metadataFieldProvider));
                        localStorage.storeData(userResults, metadataFieldProvider);
                        appEventListner.requestApplicationState(nextState);
                    } catch (UserIdException exception) {
                        // this should not occur since the field value should have originated from a UserId instance
                    }
                }
            });
            if (labelData.getUserId().equals(userResults.getUserData().getUserId())) {
                optionButton.addStyleName("optionButtonHighlight");
            }
        }
    }

    protected void createUserButton(final AppEventListner appEventListner, final String label, final ApplicationState targetApplicationState) {
        ((MetadataView) simpleView).addOptionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return label;
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                userResults.setUser(new UserData());
                localStorage.storeData(userResults, metadataFieldProvider);
                appEventListner.requestApplicationState(targetApplicationState);
            }
        });
    }

    protected void existingUserCheck(TimedStimulusListener multipleUsers, TimedStimulusListener singleUser) {
        if (localStorage.getUserIdList(metadataFieldProvider.workerIdMetadataField).size() > 1 || !userResults.getUserData().getMetadataFields().isEmpty()) {
            multipleUsers.postLoadTimerFired();
        } else {
            singleUser.postLoadTimerFired();
        }
    }

    protected void allMetadataFields() {
        for (MetadataField metadataField : metadataFieldProvider.metadataFieldArray) {
            ((MetadataView) simpleView).addField(metadataField, userResults.getUserData().getMetadataValue(metadataField), metadataField.getFieldLabel(), null, null);
        }
    }

    protected void metadataFieldConnection(final MetadataField metadataField, final MetadataField metadataFieldOther) {
        ((MetadataView) simpleView).addField(metadataField, userResults.getUserData().getMetadataValue(metadataField), metadataField.getFieldLabel(), localStorage.getUserIdList(metadataFieldOther), userResults.getUserData().getUserId());
    }

    protected void metadataField(MetadataField metadataField) {
        ((MetadataView) simpleView).addField(metadataField, userResults.getUserData().getMetadataValue(metadataField), metadataField.getFieldLabel(), null, null);
    }

    public void focusFirstTextBox() {
        ((MetadataView) simpleView).focusFirstTextBox();
    }
}
