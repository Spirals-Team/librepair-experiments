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
                public class Introduction_3Presenter extends AbstractStimulusPresenter implements Presenter {
                private final ApplicationState selfApplicationState = ApplicationState.Introduction_3;    
                public Introduction_3Presenter(RootLayoutPanel widgetTag, AudioPlayer audioPlayer, DataSubmissionService submissionService, UserResults userResults, LocalStorage localStorage) {
                        super(widgetTag, audioPlayer, submissionService, userResults, localStorage, 
                    new nl.mpi.tg.eg.experiment.client.service.StimulusProvider(
                        GeneratedStimulusProvider.values));
                        }

                @Override
                protected String getTitle() {
                return messages.titleIntroduction_3Presenter();
                }
                
                @Override
                protected String getSelfTag() {
                return ApplicationState.Introduction_3.name();
                }

                @Override
                protected void setContent(final AppEventListner appEventListner) {
            requestFilePermissions();    backgroundImage("huisje_02.jpg", "", 1000, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            
                }
                });
                addHtmlText(messages.d1e119(), null);    audioButton("intro_3", "intro_3", "titleBarButton", "audiobutton.jpg", false, ExtendedKeyCodes.KEY_ENTER, new TimedStimulusListener() {

                @Override
                public void postLoadTimerFired() {
                pause(0, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                autoNextPresenter(appEventListner);
        
                }
                });
            
                }
                });
            }
                }