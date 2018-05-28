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
                public class AtticPresenter extends AbstractStimulusPresenter implements Presenter {
                private final ApplicationState selfApplicationState = ApplicationState.Attic;    
                public AtticPresenter(RootLayoutPanel widgetTag, AudioPlayer audioPlayer, DataSubmissionService submissionService, UserResults userResults, LocalStorage localStorage) {
                        super(widgetTag, audioPlayer, submissionService, userResults, localStorage, 
                    new nl.mpi.tg.eg.experiment.client.service.StimulusProvider(
                        GeneratedStimulusProvider.values));
                        }

                @Override
                protected String getTitle() {
                return messages.titleAtticPresenter();
                }
                
                @Override
                protected String getSelfTag() {
                return ApplicationState.Attic.name();
                }

                @Override
                protected void setContent(final AppEventListner appEventListner) {
            requestFilePermissions();    backgroundImage("huisje_02.jpg", "zoomToAttic", 1000, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            
                }
                });
                addHtmlText(messages.d1e788(), null);    audioButton("room_5", "room_5", "titleBarButton", "audiobutton.jpg", true, ExtendedKeyCodes.KEY_R1_MA_A, new TimedStimulusListener() {

                @Override
                public void postLoadTimerFired() {
                backgroundImage("Playroom.jpg", "zoomToPlayroom", 0, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            
                }
                });
                table("titleBarButton", new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                row(new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                column("", new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            actionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.d1e800();
            }
            
            @Override
            public int getHotKey() {
            return ExtendedKeyCodes.KEY_ENTER;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
            autoNextPresenter(appEventListner);
        
            }
            }, null);
        
                }
                });
            
                }
                });
            
                }
                });
            
                }
                });
            }
                }