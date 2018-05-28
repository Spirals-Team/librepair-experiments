package nl.mpi.tg.eg.experiment.client.presenter;
    
                import com.google.gwt.core.client.GWT;     
                import nl.mpi.tg.eg.experiment.client.model.ExtendedKeyCodes;    
                import com.google.gwt.safehtml.shared.UriUtils;
                import com.google.gwt.user.client.ui.ButtonBase;
                import com.google.gwt.user.client.ui.RootLayoutPanel;
                import java.util.Arrays;
                import nl.mpi.tg.eg.experiment.client.Version;
                import nl.mpi.tg.eg.experiment.client.ApplicationController.ApplicationState;
                import nl.mpi.tg.eg.experiment.client.exception.CanvasError;
                import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
                import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
                import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
                import nl.mpi.tg.eg.experiment.client.view.VideoPanel;
                import nl.mpi.tg.eg.experiment.client.view.AnnotationTimelinePanel;
                import nl.mpi.tg.eg.experiment.client.view.ComplexView;
                import nl.mpi.tg.eg.experiment.client.view.MenuView;     
                import nl.mpi.tg.eg.experiment.client.listener.GroupActivityListener;
                import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;  
                import nl.mpi.tg.eg.experiment.client.model.GeneratedStimulus.Tag;  
                import nl.mpi.tg.eg.experiment.client.model.UserId;    
                import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;
                import nl.mpi.tg.eg.experiment.client.model.UserResults;    
                import nl.mpi.tg.eg.experiment.client.view.MetadataView; 
                import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService; 
                import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
                import nl.mpi.tg.eg.experiment.client.service.MetadataFieldProvider;
                import nl.mpi.tg.eg.experiment.client.util.GeneratedStimulusProvider;
                import nl.mpi.tg.eg.frinex.common.model.StimulusSelector;
                        
                // generated with config2java.xsl
                public class Edit_UserPresenter extends AbstractMetadataPresenter implements Presenter {
                private final ApplicationState selfApplicationState = ApplicationState.Edit_User;    
                public Edit_UserPresenter(RootLayoutPanel widgetTag, DataSubmissionService submissionService, UserResults userResults, final LocalStorage localStorage) {
                        super(widgetTag, submissionService, userResults, localStorage);
                        }

                @Override
                protected String getTitle() {
                return messages.titleEdit_UserPresenter();
                }
                
                @Override
                protected String getSelfTag() {
                return ApplicationState.Edit_User.name();
                }

                @Override
                protected void setContent(final AppEventListner appEventListner) {
            requestFilePermissions();    metadataField(metadataFieldProvider.workerIdMetadataField);
            metadataField(metadataFieldProvider.datOfBirthMetadataField);
            metadataField(metadataFieldProvider.genderMetadataField);
            saveMetadataButton(messages.d1e71(), true, messages.errorMessaged1e71(), new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
            autoNextPresenter(appEventListner, ApplicationState.Introduction_1);
        
            }
            }, new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
            autoNextPresenter(appEventListner);
        
            }
            });
            }
                }