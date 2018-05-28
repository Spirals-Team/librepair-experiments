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
                public class Room_4Presenter extends AbstractStimulusPresenter implements Presenter {
                private final ApplicationState selfApplicationState = ApplicationState.Room_4;    
                public Room_4Presenter(RootLayoutPanel widgetTag, AudioPlayer audioPlayer, DataSubmissionService submissionService, UserResults userResults, LocalStorage localStorage) {
                        super(widgetTag, audioPlayer, submissionService, userResults, localStorage, 
                    new nl.mpi.tg.eg.experiment.client.service.StimulusProvider(
                        GeneratedStimulusProvider.values));
                        }

                @Override
                protected String getTitle() {
                return messages.titleRoom_4Presenter();
                }
                
                @Override
                protected String getSelfTag() {
                return ApplicationState.Room_4.name();
                }

                @Override
                protected void setContent(final AppEventListner appEventListner) {
            requestFilePermissions();    backgroundImage("huisje_02.jpg", "zoomToBlock4", 3000, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                audioButton("room_4", "room_4", "titleBarButton", "intro_1.jpg", true, ExtendedKeyCodes.KEY_R1_MA_A, new TimedStimulusListener() {

                @Override
                public void postLoadTimerFired() {
                pause(2000, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            ((nl.mpi.tg.eg.experiment.client.service.StimulusProvider)stimulusProvider).setrandomise("false");((nl.mpi.tg.eg.experiment.client.service.StimulusProvider)stimulusProvider).setrepeatRandomWindow("0");((nl.mpi.tg.eg.experiment.client.service.StimulusProvider)stimulusProvider).setmaxStimuli("1000");((nl.mpi.tg.eg.experiment.client.service.StimulusProvider)stimulusProvider).setrepeatCount("1");    loadStimulus("Room 4", new StimulusSelector[]{new StimulusSelector("allRooms", Tag.tag_allRooms)}, new StimulusSelector[]{new StimulusSelector("Set_1", Tag.tag_Set_1), new StimulusSelector("Set_2", Tag.tag_Set_2), new StimulusSelector("Set_3", Tag.tag_Set_3), new StimulusSelector("Set_4", Tag.tag_Set_4)}, metadataFieldProvider.groupAllocation_Room_4MetadataField, "allRooms", new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
            backgroundImage("", "", 0, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            
                }
                });
                clearPage("fullScreenWidth");
            stimulusCodeAudio(1000, "<code>_1", false, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                stimulusCodeAudio(0, "<code>_2", false, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            
                }
                });
                stimulusCodeVideo(0, 100, 100, "borderedVideoLeft", true, false, false, 0, "<code>_L", new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            
                }
                });
                stimulusCodeVideo(0, 100, 100, "borderedVideoRight", true, false, false, 0, "<code>_R", new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                stimulusCodeAudio(0, "<code>_3", false, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            touchInputStimulusButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.d1e665();
            }
            
            @Override
            public int getHotKey() {
            return -1;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        logTimeStamp("touchInputStimulusButton", "Left");    disableStimulusButtons();
            stimulusCodeAudio(500, "Correct", false, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                enableStimulusButtons();
        
                }
                });
            
            }
            }, "Left", "leftOverlayButton", "", "leftButtonGroup");
        touchInputStimulusButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.d1e675();
            }
            
            @Override
            public int getHotKey() {
            return -1;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        logTimeStamp("touchInputStimulusButton", "Right");    disableStimulusButtons();
            stimulusCodeAudio(500, "Correct", false, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                enableStimulusButtons();
        
                }
                });
            
            }
            }, "Right", "rightOverlayButton", "", "rightButtonGroup");
        
                }
                });
            
                }
                });
            
                }
                });
                touchInputCaptureStart(false, -1, new TimedStimulusListener() {

                @Override
                public void postLoadTimerFired() {
            
                }
                });
            table("titleBarButton", true, new TimedStimulusListener() {
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
            return messages.d1e696();
            }
            
            @Override
            public int getHotKey() {
            return ExtendedKeyCodes.KEY_R1_MA_ENTER;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        logTimeStamp("actionButton", "R1_MA_ENTER");    touchInputReportSubmit();
            autoNextPresenter(appEventListner, ApplicationState.Menu);
        
            }
            }, "");
        
                }
                });
                column("", new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            actionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.d1e706();
            }
            
            @Override
            public int getHotKey() {
            return ExtendedKeyCodes.KEY_R1_MA_LEFT;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        logTimeStamp("actionButton", "R1_MA_LEFT");    touchInputReportSubmit();
            prevStimulus(false);
        
            }
            }, "");
        
                }
                });
                column("", new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            actionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.d1e716();
            }
            
            @Override
            public int getHotKey() {
            return ExtendedKeyCodes.KEY_R1_MA_A;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        logTimeStamp("actionButton", "R1_MA_A");    touchInputReportSubmit();
            showStimulus();
        
            }
            }, "");
        
                }
                });
                column("", new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            actionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.d1e726();
            }
            
            @Override
            public int getHotKey() {
            return ExtendedKeyCodes.KEY_R1_MA_RIGHT;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        logTimeStamp("actionButton", "R1_MA_RIGHT");    touchInputReportSubmit();
            nextStimulus(false);
        
            }
            }, "");
        
                }
                });
            
                }
                });
            
                }
                });
            
            }
            }, new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
            autoNextPresenter(appEventListner);
        
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