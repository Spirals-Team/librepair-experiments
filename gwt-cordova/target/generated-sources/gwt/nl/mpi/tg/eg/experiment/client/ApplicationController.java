package nl.mpi.tg.eg.experiment.client;

            import com.google.gwt.user.client.History;
            import com.google.gwt.user.client.ui.RootLayoutPanel;
            import nl.mpi.tg.eg.experiment.client.exception.AudioException;
            import nl.mpi.tg.eg.experiment.client.exception.UserIdException;
            import nl.mpi.tg.eg.experiment.client.exception.CanvasError;
            import nl.mpi.tg.eg.experiment.client.presenter.*;
            import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;

            public class ApplicationController extends AppController {

            public static final boolean SHOW_HEADER = false;

            public enum ApplicationState {
        
            start(null),
                Bluetooth_Instructions("Bluetooth Instructions"),
                    Start("Start"),
                    SelectUser("Select Participant"),
                    Menu("Menu"),
                    Edit_User("Terug"),
                    Introduction_1("Introduction 1"),
                    Introduction_2("Introduction 2"),
                    Introduction_3("Introduction 3"),
                    Training("Training"),
                    TestMenu("TestMenu"),
                    Room_1("Room 1"),
                    Room_2("Room 2"),
                    Room_3("Room 3"),
                    Room_4("Room 4"),
                    completion("Einde van het experiment"),
                    Attic("Attic"),
                    DataManagement("Data Management"),
                    DataUpload("Data Upload"),
                    about("Over"),
                    highscoresubmitted(null),
            highscoresfailednon202(null),
            highscoresfailedbuildererror(null),
            highscoresfailedconnectionerror(null),
            end(null),
            menu(null),
            playerdetails(null),
            locale(null),
            tutorial(null),
            stopSharing(null),
            tutorialorguessround(null),
            chooseplayer(null),
            guessround(null),
            metadata(null),
            registration(null),
            infoscreen(null),
            explaindatasharing(null),
            moreinfo(null),
            alien(null),
            scores(null),
            map(null),
            setuser(null),
            matchlanguage(null),
            autotyp_regions(null),
            startscreen(null),
            version(null);
        
            final public String label;

            ApplicationState(String label) {
            this.label = label;
            }
            } 
            @Override
            boolean preserveLastState() {
            return true;
            }
            public ApplicationController(RootLayoutPanel widgetTag) throws UserIdException {
            super(widgetTag);
                
            }
            
            @Override
            public void requestApplicationState(ApplicationState applicationState) {
            localStorage.saveAppState(userResults.getUserData().getUserId(), applicationState);
            if (presenter != null) {
            presenter.savePresenterState();
            }
        try {
            submissionService.submitScreenChange(userResults.getUserData().getUserId(), applicationState.name());
            History.newItem(applicationState.name(), false);
            // todo:
            // on each state change check if there is an completed game data, if the share is true then upload or store if offline
            // when any stored data is uploaded then delete the store 
            // on new game play erase any in memory game data regardless of its shared or not shared state
            switch (applicationState) {
            case start:
        
                case Bluetooth_Instructions:
                this.presenter = new Bluetooth_InstructionsPresenter(widgetTag);
                presenter.setState(this, ApplicationState.Menu, null);
                break;                                                                                                                                                  
            
                case Start:
                this.presenter = new StartPresenter(widgetTag, submissionService, userResults, localStorage);
                presenter.setState(this, null, null);
                break;                                                                                                                                                  
            
                case SelectUser:
                this.presenter = new SelectUserPresenter(widgetTag, submissionService, userResults, localStorage);
                presenter.setState(this, ApplicationState.Start, ApplicationState.Edit_User);
                break;                                                                                                                                                  
            
                case Menu:
                this.presenter = new MenuPresenter(widgetTag, userResults, localStorage);
                presenter.setState(this, null, null);
                break;                                                                                                                                                  
            
                case Edit_User:
                this.presenter = new Edit_UserPresenter(widgetTag, submissionService, userResults, localStorage);
                presenter.setState(this, ApplicationState.SelectUser, ApplicationState.Introduction_1);
                break;                                                                                                                                                  
            
                case Introduction_1:
                this.presenter = new Introduction_1Presenter(widgetTag, new AudioPlayer(this), submissionService, userResults, localStorage);
                presenter.setState(this, ApplicationState.Menu, ApplicationState.Introduction_2);
                break;                                                                                                                                                  
            
                case Introduction_2:
                this.presenter = new Introduction_2Presenter(widgetTag, new AudioPlayer(this), submissionService, userResults, localStorage);
                presenter.setState(this, ApplicationState.Menu, ApplicationState.Introduction_3);
                break;                                                                                                                                                  
            
                case Introduction_3:
                this.presenter = new Introduction_3Presenter(widgetTag, new AudioPlayer(this), submissionService, userResults, localStorage);
                presenter.setState(this, ApplicationState.Menu, ApplicationState.Training);
                break;                                                                                                                                                  
            
                case Training:
                this.presenter = new TrainingPresenter(widgetTag, new AudioPlayer(this), submissionService, userResults, localStorage);
                presenter.setState(this, null, ApplicationState.Room_1);
                break;                                                                                                                                                  
            
                case TestMenu:
                this.presenter = new TestMenuPresenter(widgetTag, userResults, localStorage);
                presenter.setState(this, ApplicationState.Menu, null);
                break;                                                                                                                                                  
            
                case Room_1:
                this.presenter = new Room_1Presenter(widgetTag, new AudioPlayer(this), submissionService, userResults, localStorage);
                presenter.setState(this, null, ApplicationState.Room_2);
                break;                                                                                                                                                  
            
                case Room_2:
                this.presenter = new Room_2Presenter(widgetTag, new AudioPlayer(this), submissionService, userResults, localStorage);
                presenter.setState(this, null, ApplicationState.Room_3);
                break;                                                                                                                                                  
            
                case Room_3:
                this.presenter = new Room_3Presenter(widgetTag, new AudioPlayer(this), submissionService, userResults, localStorage);
                presenter.setState(this, null, ApplicationState.Room_4);
                break;                                                                                                                                                  
            
                case Room_4:
                this.presenter = new Room_4Presenter(widgetTag, new AudioPlayer(this), submissionService, userResults, localStorage);
                presenter.setState(this, null, ApplicationState.Attic);
                break;                                                                                                                                                  
            
                case completion:
                this.presenter = new completionPresenter(widgetTag, submissionService, userResults, localStorage);
                presenter.setState(this, ApplicationState.Menu, ApplicationState.Edit_User);
                break;                                                                                                                                                  
            
                case Attic:
                this.presenter = new AtticPresenter(widgetTag, new AudioPlayer(this), submissionService, userResults, localStorage);
                presenter.setState(this, null, ApplicationState.completion);
                break;                                                                                                                                                  
            
                case DataManagement:
                this.presenter = new DataManagementPresenter(widgetTag, submissionService, userResults, localStorage);
                presenter.setState(this, ApplicationState.Menu, ApplicationState.DataUpload);
                break;                                                                                                                                                  
            
                case DataUpload:
                this.presenter = new DataUploadPresenter(widgetTag, submissionService, userResults, localStorage);
                presenter.setState(this, ApplicationState.DataManagement, ApplicationState.DataManagement);
                break;                                                                                                                                                  
            
                case about:
                this.presenter = new aboutPresenter(widgetTag);
                presenter.setState(this, ApplicationState.Menu, null);
                break;                                                                                                                                                  
            
            case end:
            exitApplication();
            break;
            case highscoresubmitted:
            case highscoresfailedbuildererror:
            case highscoresfailedconnectionerror:
            case highscoresfailednon202:
            break;
            default:
            this.presenter = new ErrorPresenter(widgetTag, "No state for: " + applicationState);
            presenter.setState(this, ApplicationState.start, applicationState);
            break;
            }
        
                } catch (AudioException error) {
                logger.warning(error.getMessage());
                this.presenter = new ErrorPresenter(widgetTag, error.getMessage());
                presenter.setState(this, ApplicationState.start, applicationState);
                }
            }
            }