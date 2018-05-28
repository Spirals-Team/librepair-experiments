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
                public class DataUploadPresenter extends AbstractDataSubmissionPresenter implements Presenter {
                private final ApplicationState selfApplicationState = ApplicationState.DataUpload;    
                public DataUploadPresenter(RootLayoutPanel widgetTag, DataSubmissionService submissionService, UserResults userResults, final LocalStorage localStorage) {
                        super(widgetTag, submissionService, userResults, localStorage);
                        }

                @Override
                protected String getTitle() {
                return messages.titleDataUploadPresenter();
                }
                
                @Override
                protected String getSelfTag() {
                return ApplicationState.DataUpload.name();
                }

                @Override
                protected void setContent(final AppEventListner appEventListner) {
            requestFilePermissions();    addHtmlText(messages.d1e820(), null);    pause(100, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                sendMetadata(null, new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
            addText(messages.d1e883());
            addPadding();
            addPadding();
        targetButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.d1e889();
            }
            
            @Override
            public int getHotKey() {
            return  -1;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        appEventListner.requestApplicationState(ApplicationState.DataUpload);
            }
            }, "");
            addPadding();
            addPadding();
        targetButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.d1e896();
            }
            
            @Override
            public int getHotKey() {
            return  -1;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        appEventListner.requestApplicationState(ApplicationState.DataManagement);
            }
            }, "");
            addPadding();
            addPadding();
        
            }
            }, new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
            addHtmlText(messages.d1e828(), "");    pause(100, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                sendAllData(null, new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
            addText(messages.d1e858());
            addPadding();
            addPadding();
        targetButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.d1e864();
            }
            
            @Override
            public int getHotKey() {
            return  -1;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        appEventListner.requestApplicationState(ApplicationState.DataUpload);
            }
            }, "");
            addPadding();
            addPadding();
        targetButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.d1e871();
            }
            
            @Override
            public int getHotKey() {
            return  -1;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        appEventListner.requestApplicationState(ApplicationState.DataManagement);
            }
            }, "");
            addPadding();
            addPadding();
        
            }
            }, new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
            addHtmlText(messages.d1e836(), "");    addPadding();
            addPadding();
            eraseUsersDataButton(messages.d1e842(), ApplicationState.DataManagement);
            addPadding();
            addPadding();
        targetButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.d1e849();
            }
            
            @Override
            public int getHotKey() {
            return  -1;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        appEventListner.requestApplicationState(ApplicationState.DataManagement);
            }
            }, "");
            addPadding();
            addPadding();
        
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