<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : config2java.xsl
    Created on : June 17, 2015, 17:27 PM
    Author     : Peter Withers <peter.withers@mpi.nl>
    Description:
        Converts the XML config file into concrete presenter classes.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0">
    <xsl:output method="text" encoding="UTF-8" />
    <xsl:param name="targetClientDirectory" select="targetClientDirectory"/>
    <xsl:template match="/">
        <xsl:text>package nl.mpi.tg.eg.experiment.client;

            import com.google.gwt.user.client.History;
            import com.google.gwt.user.client.ui.RootLayoutPanel;
            import nl.mpi.tg.eg.experiment.client.exception.AudioException;
            import nl.mpi.tg.eg.experiment.client.exception.UserIdException;
            import nl.mpi.tg.eg.experiment.client.exception.CanvasError;
            import nl.mpi.tg.eg.experiment.client.presenter.*;
            import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;

            public class ApplicationController extends AppController {

            public static final boolean SHOW_HEADER = </xsl:text>
        <xsl:value-of select="experiment/@showMenuBar" />
        <xsl:text>;
            public static final int[] SDCARD_DATACHANNELS = {</xsl:text>
        <xsl:for-each select="experiment/administration/dataChannel[@logToSdCard eq'true']">
            <xsl:value-of select="@channel" />
            <xsl:if test="position() != last()">
                <xsl:text> </xsl:text>
            </xsl:if>
        </xsl:for-each>
        <xsl:text>};
            public enum ApplicationState {
        
            start(null),
        </xsl:text>
        <xsl:for-each select="experiment/presenter">
            <xsl:text>        </xsl:text>
            <xsl:value-of select="@self" />
            <xsl:text>("</xsl:text>
            <xsl:value-of select="@menuLabel" />
            <xsl:text>"),
            </xsl:text>
        </xsl:for-each>
        <xsl:text>        highscoresubmitted(null),
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
            version("Version");
        
            final public String label;

            ApplicationState(String label) {
            this.label = label;
            }
            } 
            @Override
            boolean preserveLastState() {
            return </xsl:text>
        <xsl:value-of select="experiment/@preserveLastState" />
        <xsl:text>;
            }
            public ApplicationController(RootLayoutPanel widgetTag) throws UserIdException {
            super(widgetTag);
        </xsl:text>
        <!--todo: does this even work?-->
        <xsl:value-of select="if(experiment/preventWindowClose) then concat('preventWindowClose(messages.', generate-id(experiment/preventWindowClose), '());') else ''" />
        <xsl:text>        
            }
            
            @Override
            public void requestApplicationState(ApplicationState applicationState) {
            localStorage.saveAppState(userResults.getUserData().getUserId(), applicationState);
            if (presenter != null) {
            presenter.savePresenterState();
            }
        </xsl:text>
        <xsl:if test="experiment/presenter/@type = 'colourPicker' or experiment/presenter/@type = 'preload' or experiment/presenter/@type = 'stimulus' or experiment/presenter/@type = 'kindiagram' or experiment/presenter/@type = 'timeline'">
            <xsl:text>try {</xsl:text>
        </xsl:if>
        <xsl:text>
            submissionService.submitScreenChange(userResults.getUserData().getUserId(), applicationState.name());
            History.newItem(applicationState.name(), false);
            // todo:
            // on each state change check if there is an completed game data, if the share is true then upload or store if offline
            // when any stored data is uploaded then delete the store 
            // on new game play erase any in memory game data regardless of its shared or not shared state
            switch (applicationState) {
            case start:
        </xsl:text>
        <xsl:for-each select="experiment/presenter">
            <xsl:text>
                case </xsl:text>
            <xsl:value-of select="@self" />
            <xsl:text>:
                this.presenter = new </xsl:text>
            <xsl:value-of select="@self" />
            <xsl:text>Presenter(widgetTag</xsl:text>
            <xsl:value-of select="
if(@type = 'transmission' or @type = 'metadata'  or @type = 'colourReport') then ', submissionService, userResults, localStorage' else
if(@type = 'preload') then ', new AudioPlayer(this), submissionService, userResults' else
if(@type = 'menu') then ', userResults, localStorage' else
if(@type = 'stimulus' or @type = 'kindiagram' or @type = 'timeline' or @type = 'colourPicker') then ', new AudioPlayer(this), submissionService, userResults, localStorage' else ''" />
            <xsl:value-of select="if(@type = 'stimulus') then ', timerService' else ''" />
            <xsl:text>);
                presenter.setState(this, </xsl:text>
            <xsl:choose>
                <xsl:when test="@back">
                    <xsl:text>ApplicationState.</xsl:text>
                    <xsl:value-of select="@back" />
                </xsl:when>
                <xsl:otherwise>
                    <xsl:text>null</xsl:text>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:text>, </xsl:text>
            <xsl:choose>
                <xsl:when test="@next">
                    <xsl:text>ApplicationState.</xsl:text>
                    <xsl:value-of select="@next" />
                </xsl:when>
                <xsl:otherwise>
                    <xsl:text>null</xsl:text>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:text>);
                break;                                                                                                                                                  
            </xsl:text>
        </xsl:for-each>
        <xsl:text>
            case version:
            this.presenter = new VersionPresenter(widgetTag);
            presenter.setState(this, ApplicationState.start, null);
            break;
        </xsl:text>
        <xsl:text>
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
        </xsl:text>
        <xsl:if test="experiment/presenter/@type = 'colourPicker' or experiment/presenter/@type = 'preload' or experiment/presenter/@type = 'stimulus' or experiment/presenter/@type = 'kindiagram' or experiment/presenter/@type = 'timeline'">
            <xsl:text>
                } catch (AudioException</xsl:text>
            <xsl:value-of select="if(experiment/presenter/@type = 'colourPicker') then '|CanvasError' else ''" />
            <xsl:text> error) {
                logger.warning(error.getMessage());
                this.presenter = new ErrorPresenter(widgetTag, error.getMessage());
                presenter.setState(this, ApplicationState.start, applicationState);
                }</xsl:text>
        </xsl:if>
        <xsl:text>
            }
            }</xsl:text>

        <xsl:apply-templates select="experiment"/>
    </xsl:template>
    <xsl:template match="presenter">        
        <!--<xsl:value-of select="concat(@self, 'Presenter.java')" />-->                                                                                                                                  
        <xsl:result-document href="{$targetClientDirectory}/presenter/{@self}Presenter.java" method="text">
            <xsl:text>package nl.mpi.tg.eg.experiment.client.presenter;
    
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
                import nl.mpi.tg.eg.experiment.client.listener.CancelableStimulusListener;
                import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
                import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
                import nl.mpi.tg.eg.experiment.client.view.VideoPanel;
                import nl.mpi.tg.eg.experiment.client.view.AnnotationTimelinePanel;
                import nl.mpi.tg.eg.experiment.client.view.ComplexView;
                import nl.mpi.tg.eg.experiment.client.view.MenuView;     
                import nl.mpi.tg.eg.experiment.client.listener.GroupActivityListener;
                import nl.mpi.tg.eg.experiment.client.listener.CurrentStimulusListener;
                import nl.mpi.tg.eg.experiment.client.listener.TimerListner;
                import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;  
                import nl.mpi.tg.eg.experiment.client.model.GeneratedStimulus.Tag;  
                import nl.mpi.tg.eg.experiment.client.model.UserId;    
                import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;
                import nl.mpi.tg.eg.experiment.client.service.TimerService;
                import nl.mpi.tg.eg.experiment.client.model.UserResults;    
                import nl.mpi.tg.eg.experiment.client.view.MetadataView; 
                import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService; 
                import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
                import nl.mpi.tg.eg.experiment.client.service.MetadataFieldProvider;
                import nl.mpi.tg.eg.experiment.client.util.GeneratedStimulusProvider;
                import nl.mpi.tg.eg.frinex.common.StimuliProvider;
                import nl.mpi.tg.eg.frinex.common.model.Stimulus;
                import nl.mpi.tg.eg.frinex.common.model.StimulusSelector;
                        
                // generated with config2java.xsl
                public class </xsl:text>
            <xsl:value-of select="@self" />
            <xsl:text>Presenter extends </xsl:text>
            <xsl:value-of select="if(@type = 'colourPicker') then 'AbstractColourPicker' else if(@type = 'colourReport') then 'AbstractColourReport' else if(@type = 'timeline') then 'AbstractTimeline' else if(@type = 'transmission') then 'AbstractDataSubmission' else if(@type = 'menu') then 'AbstractMenu' else if(@type = 'stimulus') then 'AbstractStimulus' else if(@type = 'preload') then 'AbstractPreloadStimulus' else if(@type = 'debug') then 'LocalStorage' else if(@type = 'metadata') then 'AbstractMetadata' else if(@type = 'kindiagram') then 'AbstractKinDiagram' else 'Abstract'" />
            <xsl:text>Presenter implements Presenter {
                private final ApplicationState selfApplicationState = ApplicationState.</xsl:text>
            <xsl:value-of select="@self" />
            <xsl:text>;</xsl:text> 
            <xsl:if test="versionData">
                <xsl:text>
                    private final Version version = GWT.create(Version.class);
                </xsl:text> 
            </xsl:if>
            <xsl:text>    
                public </xsl:text>
            <xsl:value-of select="@self" />
            <xsl:text>Presenter(RootLayoutPanel widgetTag</xsl:text>
            <xsl:value-of select="
if(@type = 'transmission' or @type = 'metadata' or @type = 'colourReport') then ', DataSubmissionService submissionService, UserResults userResults, final LocalStorage localStorage' else 
if(@type = 'preload') then ', AudioPlayer audioPlayer, DataSubmissionService submissionService, UserResults userResults' else 
if(@type = 'menu') then ', UserResults userResults, LocalStorage localStorage' else
if(@type = 'stimulus' or @type = 'kindiagram' or @type = 'timeline' or @type = 'colourPicker') then ', AudioPlayer audioPlayer, DataSubmissionService submissionService, UserResults userResults, LocalStorage localStorage' else ''" />
            <xsl:value-of select="if(@type = 'stimulus') then ', final TimerService timerService' else ''"/>
            <xsl:value-of select="if(@type = 'colourPicker') then ') throws CanvasError {' else ') {'"/>
            <xsl:choose>
                <xsl:when test="@type = 'menu'">
                    <xsl:text>
                        super(widgetTag, new MenuView(), userResults, localStorage);
                    </xsl:text>
                </xsl:when>
                <xsl:when test="@type = 'text'">
                    <xsl:text>
                        super(widgetTag, new ComplexView());
                    </xsl:text>
                </xsl:when>
                <xsl:when test="@type = 'debug'">
                    <xsl:text>
                        super(widgetTag);
                    </xsl:text>
                </xsl:when>
                <xsl:when test="@type = 'preload'">
                    <xsl:text>
                        super(widgetTag, audioPlayer, submissionService, userResults);
                    </xsl:text>                    
                </xsl:when>
                <xsl:when test="@type = 'kindiagram' or @type = 'timeline' or @type = 'colourPicker'">
                    <xsl:text>
                        super(widgetTag, audioPlayer, submissionService, userResults, localStorage);
                    </xsl:text>                    
                </xsl:when>
                <xsl:when test="@type = 'stimulus'">
                    <xsl:text>
                        super(widgetTag, audioPlayer, submissionService, userResults, localStorage, timerService);
                    </xsl:text>                    
                </xsl:when>
                <xsl:when test="@type = 'metadata' or @type = 'transmission' or @type = 'colourReport'">
                    <xsl:text>
                        super(widgetTag, submissionService, userResults, localStorage);
                    </xsl:text>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:text>
                        no type attribute configured for "</xsl:text>
                    <xsl:value-of select="@type" />
                    <xsl:text>" in config2java.xsl
                    </xsl:text>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:text>    }

                @Override
                protected String getTitle() {
                return messages.title</xsl:text>
            <xsl:value-of select="@self" />
            <xsl:text>Presenter();
                }
                
                @Override
                protected String getSelfTag() {
                return ApplicationState.</xsl:text>
            <xsl:value-of select="@self" />
            <xsl:text>.name();
                }

                @Override
                protected void setContent(final AppEventListner appEventListner) {
            </xsl:text>
            <xsl:value-of select="if(descendant::startAudioRecorder) then 'requestRecorderPermissions();' else 'requestFilePermissions();'" />
            <xsl:apply-templates/> <!--select="htmlText|padding|image|menuItem|text|versionData|optionButton|userInfo|localStorageData|stimulusImage|stimulusAudio"-->
            <xsl:text>    }
                }</xsl:text>
        </xsl:result-document>
    </xsl:template>
    <xsl:template match="text()" /><!--prevent text nodes slipping into the output-->
    <xsl:template match="htmlText">
        <xsl:text>    addHtmlText(messages.</xsl:text>
        <xsl:value-of select="generate-id(.)" />
        <xsl:text>(), </xsl:text>
        <xsl:value-of select="if(@styleName) then concat('&quot;', @styleName, '&quot;') else 'null'" />
        <xsl:text>);</xsl:text>
    </xsl:template>
    <xsl:template match="plainText">
        <xsl:text>    addText(messages.</xsl:text>
        <xsl:value-of select="generate-id(.)" />
        <xsl:text>());
        </xsl:text>
    </xsl:template>
    <!--    <xsl:template match="image">
        <xsl:text>    </xsl:text>
        <xsl:value-of select="local-name()" />
        <xsl:text>(</xsl:text>                
        <xsl:text>"</xsl:text>
        <xsl:value-of select="@src" />
        <xsl:text>", "</xsl:text>
        <xsl:value-of select="@styleName" />
        <xsl:text>", </xsl:text>
        <xsl:value-of select="@msToNext" />
        <xsl:text>, new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
        </xsl:text>
        <xsl:apply-templates/>
        <xsl:text>
            }
            });
        </xsl:text>
    </xsl:template>-->
    <xsl:template match="menuItem">
        <xsl:text>    </xsl:text>
        <xsl:value-of select="local-name()" />
        <xsl:text>(appEventListner, </xsl:text>      
        <xsl:text>ApplicationState.</xsl:text>
        <xsl:value-of select="@target" />
        <xsl:text>, messages.</xsl:text>
        <xsl:value-of select="generate-id(.)" />
        <xsl:text>()</xsl:text>  
        <xsl:value-of select="if(@hotKey eq '-1' or @hotKey eq '') then ', -1' else if(@hotKey) then concat(', ExtendedKeyCodes.KEY_', @hotKey) else ', -1'" />
        <xsl:text>);
        </xsl:text>
    </xsl:template>
    <xsl:template match="stimulusFreeText">           
        <xsl:value-of select ="local-name()"/>    
        <xsl:text>(currentStimulus, </xsl:text>
        <xsl:value-of select="if(@validationRegex) then concat('&quot;', @validationRegex, '&quot;') else 'null'" />
        <xsl:text>, messages.inputErrorMessage</xsl:text>
        <xsl:value-of select="generate-id(.)" />
        <xsl:text>(),</xsl:text>
        <xsl:text>messages.</xsl:text>
        <xsl:value-of select="generate-id(.)" />
        <xsl:text>(),</xsl:text>
        <xsl:value-of select="if(@allowedCharCodes) then concat('&quot;', @allowedCharCodes, '&quot;') else 'null'" />
        <xsl:text>,</xsl:text>
        <xsl:value-of select="if(@hotKey eq '-1' or @hotKey eq '') then '-1' else if(@hotKey) then concat('ExtendedKeyCodes.KEY_', @hotKey) else '-1'" />
        <xsl:text>,</xsl:text>
        <xsl:value-of select="if(@styleName) then concat('&quot;', @styleName, '&quot;') else 'null'" />
        <xsl:value-of select="if(@dataChannel) then concat(', ', @dataChannel) else ', 0'" />
        <xsl:text>);
        </xsl:text>
    </xsl:template>
    <!--it should be possible to merge the two following templates into one-->
    <xsl:template match="touchInputStimulusButton|stimulusButton|targetButton|actionButton|targetFooterButton|actionFooterButton"> 
        <xsl:value-of select="local-name()"/>
        <xsl:text>(</xsl:text>
        <xsl:if test="local-name() eq 'stimulusButton'">
            <xsl:text>stimulusProvider, </xsl:text>
            <xsl:text>currentStimulus,</xsl:text>
        </xsl:if>
        <xsl:text>new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.</xsl:text>
        <xsl:value-of select="generate-id(.)" />
        <xsl:text>();
            }
            
            @Override
            public int getHotKey() {
            return </xsl:text>
        <xsl:value-of select="if(@hotKey eq '-1' or @hotKey eq '') then ' -1' else if(@hotKey) then concat('ExtendedKeyCodes.KEY_', @hotKey) else '-1'" />
        <xsl:text>;
            }
            
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
        </xsl:text>
        <xsl:choose>
            <xsl:when test="@target">
                <xsl:text>appEventListner.requestApplicationState(ApplicationState.</xsl:text>
                <xsl:value-of select="@target" />
                <xsl:text>);</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <!--// todo: should this @eventTag exist in this button type given that tags can only happen in a stimulus presenter?-->
                <!--<xsl:value-of select="if(@eventTag) then concat('logTimeStamp(stimulusProvider, currentStimulus, &quot;', local-name(), '&quot;, &quot;', @eventTag, '&quot;, 0);') else ''" />-->
                <xsl:apply-templates/>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:text>
            }
            }</xsl:text>
        <xsl:value-of select="if(local-name() eq 'touchInputStimulusButton') then concat(', &quot;', @eventTag, '&quot;') else ''" />
        <xsl:value-of select="if(local-name() eq 'targetFooterButton' or local-name() eq 'actionFooterButton') then '' else if(@styleName) then concat(', &quot;', @styleName, '&quot;') else ', null'" />
        <xsl:if test="local-name() eq 'stimulusButton'">
            <xsl:value-of select="if(@dataChannel) then concat(', ', @dataChannel) else ', 0'" />
        </xsl:if>
        <xsl:value-of select="if(local-name() eq 'touchInputStimulusButton') then if(@src) then concat(', &quot;', @src, '&quot;') else ', null' else ''" />
        <xsl:value-of select="if(@listenerId) then concat(', &quot;',@listenerId, '&quot;') else ''" />
        <xsl:text>);
        </xsl:text>
    </xsl:template>
    <xsl:template match="endOfStimulusButton">
        <xsl:value-of select ="local-name()"/>
        <xsl:text>(new PresenterEventListner() {

            @Override
            public String getLabel() {
            return messages.</xsl:text>
        <xsl:value-of select="generate-id(.)" />
        <xsl:text>();
            }
            @Override
            public int getHotKey() {
            return </xsl:text>
        <xsl:value-of select="if(@hotKey eq '-1' or @hotKey eq '') then '-1' else if(@hotKey) then concat('ExtendedKeyCodes.KEY_', @hotKey) else '-1'" />
        <xsl:text>;
            }
            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
            appEventListner.requestApplicationState(ApplicationState.</xsl:text>
        <xsl:value-of select="@target" />
        <xsl:text>);
            }
            }</xsl:text>
        <xsl:value-of select="if(@eventTag) then concat(', &quot;', @eventTag, '&quot;') else ''" />
        <xsl:text>);
        </xsl:text>
    </xsl:template>
    <xsl:template match="showStimuliReport|sendStimuliReport|logTokenText|htmlTokenText|submitGroupEvent|helpDialogue|eraseUsersDataButton|saveMetadataButton|localStorageData|stimulusMetadataField|allMetadataFields|metadataField|metadataFieldConnection|eraseLocalStorageButton|showCurrentMs|enableStimulusButtons|cancelPauseTimers|disableStimulusButtons|showStimulus|showStimulusProgress|hideStimulusButtons|showStimulusButtons|displayCompletionCode|generateCompletionCode|sendAllData|sendMetadata|eraseLocalStorageOnWindowClosing|clearStimulus|removeStimulus|keepStimulus|removeMatchingStimulus|stimulusLabel">
        <xsl:text>    </xsl:text>    
        <xsl:value-of select ="local-name()"/>
        <xsl:text>(</xsl:text>
        <xsl:if test="local-name() eq 'showStimulus' 
        or local-name() eq 'showStimuliReport' 
        or local-name() eq 'sendStimuliReport' 
        or local-name() eq 'keepStimulus' 
        or local-name() eq 'removeStimulus' 
        or local-name() eq 'showStimulusProgress' 
        ">
            <xsl:text>stimulusProvider</xsl:text>
        </xsl:if>
        <xsl:value-of select="if(@eventTag) then concat('&quot;', @eventTag, '&quot;, ') else ''" />
        <xsl:value-of select="if(local-name() eq 'showStimulusProgress' and @styleName
) then ', ' else ''" />
        <xsl:value-of select="if(local-name() eq 'keepStimulus'
or local-name() eq 'removeStimulus'
) then ', currentStimulus' else ''" />
        <xsl:value-of select="if(
local-name() eq 'stimulusMetadataField'
or local-name() eq 'stimulusLabel'
) then 'currentStimulus' else ''" />
        <xsl:value-of select="if(local-name() eq 'stimulusMetadataField' or (local-name() eq 'stimulusLabel' and @styleName)
) then ', ' else ''" />
        <xsl:value-of select="if(local-name() eq 'sendStimuliReport') then ', ' else ''" />
        <xsl:value-of select="if(@type) then concat('&quot;', @type, '&quot;, ') else ''" />   
        <xsl:if test="local-name() eq 'logTokenText'">
            <!--<xsl:value-of select="if(@headerKey) then concat('&quot;', @headerKey, '&quot;, ') else 'currentStimulus, '" />-->
            <xsl:value-of select="if(@headerKey) then concat('&quot;', @headerKey, '&quot;, ') else '&quot;logTokenText&quot;, '" />
        </xsl:if>
        <xsl:value-of select="if(@featureText) then concat('messages.', generate-id(.), '()') else ''" />    
        <xsl:value-of select="if(@fieldName) then concat('metadataFieldProvider.', @fieldName, 'MetadataField') else ''" />
        <xsl:value-of select="if(@linkedFieldName) then concat(', metadataFieldProvider.', @linkedFieldName, 'MetadataField') else ''" />
        <xsl:value-of select="if(@featureText and @styleName) then ', ' else ''" />    
        <xsl:value-of select="if(@styleName) then concat('&quot;', @styleName, '&quot;') else ''" />
        <xsl:value-of select="if(@sendData) then concat(', ', @sendData eq 'true') else ''" />    
        <xsl:value-of select="if(@matchingRegex) then concat('&quot;', @matchingRegex, '&quot;') else ''" />
        <xsl:value-of select="if(@target) then concat(', ApplicationState.', @target) else ''" />
        <xsl:value-of select="if(local-name() eq 'stimulusMetadataField') then ',' else ''" />
        <!--<xsl:if test="local-name() eq 'htmlTokenText'">-->
        <!--</xsl:if>-->
        <xsl:if test="local-name() eq 'generateCompletionCode'
 or local-name() eq 'sendStimuliReport'
 or local-name() eq 'stimulusMetadataField'
 or local-name() eq 'logTokenText'
">
            <xsl:value-of select="if(@dataChannel) then @dataChannel else '0'" />
        </xsl:if>
        <xsl:value-of select="if(@dataLogFormat) then concat(',&quot;', @dataLogFormat, '&quot;') else ''" />
        <xsl:value-of select="if(local-name() eq 'sendAllData' or local-name() eq 'sendMetadata') then 'null' else ''" />   
        <xsl:value-of select="if(local-name() eq 'saveMetadataButton') then concat(', messages.errorMessage', generate-id(.), '()') else ''" />
        <xsl:value-of select="if(local-name() eq 'helpDialogue') then concat(', messages.closeButtonLabel', generate-id(.), '()') else ''" />
        <xsl:apply-templates select="onError" />        
        <xsl:apply-templates select="onSuccess" />
        <xsl:text>);
        </xsl:text>
    </xsl:template>
    <xsl:template match="centrePage|clearPage|addPadding">
        <xsl:text>    </xsl:text>    
        <xsl:value-of select ="local-name()"/>
        <xsl:text>(</xsl:text>
        <xsl:value-of select="if(@styleName) then concat('&quot;', @styleName, '&quot;') else ''" />
        <xsl:text>);
        </xsl:text>
    </xsl:template>
    <xsl:template match="activateRandomItem|createUserButton|selectUserMenu|allMenuItems|addKinTypeGui|gotoPresenter|gotoNextPresenter">    
        <xsl:text>    </xsl:text>
        <xsl:value-of select ="local-name()"/>
        <xsl:text>(appEventListner</xsl:text>
        <xsl:value-of select="if(@diagramName) then concat(', &quot;', @diagramName, '&quot;') else ''" />
        <xsl:value-of select="if(@eventTag) then concat(', &quot;', @eventTag, '&quot;') else ''" />
        <xsl:value-of select="if(@featureText) then concat(', messages.', generate-id(.), '()') else ''" />
        <xsl:value-of select="if(@target) then concat(', ApplicationState.', @target) else ''" />
        <xsl:value-of select="if(local-name() eq 'allMenuItems') then ', selfApplicationState' else ''" />        
        <!--<xsl:value-of select="if(@repeatIncorrect) then concat(', ', @repeatIncorrect eq 'true') else ''" />-->
        <xsl:text>);
        </xsl:text>
    </xsl:template>
    <xsl:template match="touchInputCaptureStart|touchInputReportSubmit|logTimeStamp|audioButton|prevStimulusButton|nextStimulusButton|prevStimulus|nextStimulus|nextMatchingStimulus|sendGroupMessageButton|sendGroupMessage|sendGroupEndOfStimuli|sendGroupStoredMessage">
        <xsl:text>    </xsl:text>
        <xsl:value-of select ="local-name()"/>
        <xsl:text>(</xsl:text>
        <xsl:if test="local-name() eq 'nextStimulus' 
or local-name() eq 'logTimeStamp'
or local-name() eq 'touchInputCaptureStart'
or local-name() eq 'prevStimulus'
or local-name() eq 'stimulusButton'
or local-name() eq 'prevStimulusButton'
or local-name() eq 'nextStimulusButton'
or local-name() eq 'sendGroupMessageButton'
or local-name() eq 'sendGroupMessage'
or local-name() eq 'sendGroupStoredMessage'
or local-name() eq 'nextMatchingStimulus'
or local-name() eq 'sendGroupEndOfStimuli'
">
            <xsl:text>stimulusProvider</xsl:text>
            <xsl:value-of select="if (local-name() ne 'nextMatchingStimulus') then ', ' else ''" />
        </xsl:if>
        <xsl:if test="local-name() ne 'audioButton'
            and local-name() ne 'nextMatchingStimulus'
            and local-name() ne 'sendGroupEndOfStimuli'
           ">
            <xsl:text>currentStimulus,</xsl:text>
        </xsl:if>
        <xsl:value-of select="if(@eventTag) then concat('&quot;', @eventTag, '&quot;') else ''" />
        <xsl:if test="local-name() ne 'nextStimulus' and local-name() ne 'prevStimulus' and local-name() ne 'prevStimulusButton' and local-name() ne 'nextMatchingStimulus' and local-name() ne 'nextStimulusButton' and local-name() ne 'sendGroupMessage' and local-name() ne 'sendGroupStoredMessage' and local-name() ne 'sendGroupEndOfStimuli'">
            <xsl:value-of select="if(@eventTag) then ',' else ''" />
            <xsl:value-of select="if(@dataChannel) then @dataChannel else '0'" />
        </xsl:if>
        <xsl:value-of select="if(@featureText) then concat(', messages.', generate-id(.), '()') else ''" />
        <xsl:value-of select="if(@src) then concat(', &quot;', @src, '&quot;') else ''" />
        <xsl:value-of select="if(local-name() eq 'touchInputCaptureStart') then ', ' else ''" />        
        <xsl:value-of select="if(@showControls) then @showControls eq ', true' else ''" />  
        <xsl:if test="local-name() eq 'audioButton'
or local-name() eq 'prevStimulusButton'
or local-name() eq 'nextStimulusButton'
or local-name() eq 'sendGroupMessageButton'
">
            <xsl:value-of select="if(@styleName) then concat(', &quot;', @styleName, '&quot;') else ', null'" />    
        </xsl:if>    
        <xsl:value-of select="if(@poster) then concat(', &quot;', @poster, '&quot;') else ''" />
        <xsl:value-of select="if(@autoPlay) then concat(', ', @autoPlay) else ''" />        
        <xsl:value-of select="if(local-name() eq 'nextStimulusButton' or local-name() eq 'sendGroupMessageButton' or local-name() eq 'prevStimulusButton') then ', ' else ''" />        
        <xsl:value-of select="if(@repeatIncorrect) then @repeatIncorrect eq 'true' else ''" />
        <xsl:value-of select="if(not (@hotKey) and (
            local-name() eq 'audioButton'
            or local-name() eq 'sendGroupMessageButton'
            )) then ', -1' else ''" />
        <xsl:value-of select="if(@hotKey eq '-1' or @hotKey eq '') then ', -1' else if(@hotKey) then concat(', ExtendedKeyCodes.KEY_', @hotKey) else ''" />
        <xsl:value-of select="if(@incrementPhase) then concat(', callerPhase, ', @incrementPhase, ',expectedRespondents') else ''" />
        <!--<xsl:value-of select="if(@incrementStimulus) then concat(', ', @incrementStimulus) else ''" />-->
        <xsl:value-of select="if(@msToNext) then concat(', ', @msToNext) else ''" />
        <xsl:if test="local-name() eq 'touchInputCaptureStart'">
            <xsl:text>, new TimedStimulusListener() {

                @Override
                public void postLoadTimerFired() {
            </xsl:text>
            <xsl:apply-templates />
            <xsl:text>
                }
                }</xsl:text>
        </xsl:if>        
        <xsl:apply-templates select="mediaLoaded" />
        <xsl:apply-templates select="mediaLoadFailed" />
        <xsl:apply-templates select="mediaPlaybackComplete" />
        <xsl:text>);
        </xsl:text>
    </xsl:template>
    <xsl:template match="kinTypeStringDiagram|loadKinTypeStringDiagram|editableKinEntitesDiagram|ratingFooterButton|ratingButton|stimulusRatingButton|stimulusRatingRadio">
        <xsl:text>    </xsl:text>
        <xsl:value-of select="local-name()" />
        <xsl:text>(appEventListner</xsl:text>
        <xsl:if test="local-name() eq 'stimulusRatingButton'
or local-name() eq 'stimulusRatingRadio'
or local-name() eq 'ratingButton'
or local-name() eq 'ratingFooterButton'
">
            <xsl:text>, stimulusProvider</xsl:text>
        </xsl:if>
        <xsl:if test="local-name() eq 'stimulusRatingButton'
or local-name() eq 'stimulusRatingRadio'
or local-name() eq 'ratingButton'
or local-name() eq 'ratingFooterButton'
">
            <xsl:text>, currentStimulus</xsl:text>
        </xsl:if>
        <xsl:value-of select="if(@msToNext) then concat(', ', @msToNext) else ''" />
        <xsl:text>, new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
        </xsl:text>
        <xsl:apply-templates/>
        <xsl:text>
            }
            }</xsl:text>
        <xsl:value-of select="if(@kintypestring) then concat(', &quot;', @kintypestring, '&quot;') else ''" />
        <xsl:value-of select="if(@diagramName) then concat(', &quot;', @diagramName, '&quot;') else ''" />
        <xsl:value-of select="if(@imageWidth) then concat(', &quot;', @imageWidth, '&quot;') else ''" />
        <xsl:value-of select="if(@ratingLabels) then concat(', &quot;', @ratingLabels, '&quot;') else ''" />
        <xsl:value-of select="if(local-name() eq 'ratingFooterButton' or local-name() eq 'ratingButton' or local-name() eq 'stimulusRatingButton' or local-name() eq 'stimulusRatingRadio') then concat(', &quot;', @ratingLabelLeft, '&quot;') else ''" />
        <xsl:value-of select="if(local-name() eq 'ratingFooterButton' or local-name() eq 'ratingButton' or local-name() eq 'stimulusRatingButton' or local-name() eq 'stimulusRatingRadio') then concat(', &quot;', @ratingLabelRight, '&quot;') else ''" />
        <xsl:value-of select="if(@eventTier) then concat(', ', @eventTier) else ''" />
        <xsl:value-of select="if(@eventTag) then concat(', &quot;', @eventTag, '&quot;') else ''" />
        <xsl:value-of select="if(local-name() eq 'ratingFooterButton' or local-name() eq 'ratingButton' or local-name() eq 'stimulusRatingButton' or local-name() eq 'stimulusRatingRadio') then concat(', &quot;', @styleName, '&quot;') else ''" />
        <xsl:value-of select="if(local-name() eq 'ratingFooterButton' or local-name() eq 'ratingButton' or local-name() eq 'stimulusRatingButton' or local-name() eq 'stimulusRatingRadio') then if(@dataChannel) then concat(', ', @dataChannel) else ', 0' else ''" />
        <xsl:value-of select="if(local-name() eq 'stimulusRatingRadio') then concat(', &quot;', generate-id(.), '&quot;') else ''" />
        <xsl:if test="@tags">
            <xsl:text>, new Tag[]{</xsl:text>
            <xsl:for-each select="tokenize(@tags,' ')">
                <xsl:text>Tag.tag_</xsl:text>
                <xsl:value-of select="." />
                <xsl:if test="position() != last()">
                    <xsl:text>, </xsl:text>
                </xsl:if>
            </xsl:for-each>
            <xsl:text>}</xsl:text>
        </xsl:if>
        <xsl:apply-templates select="stimuli" mode="stimuliTags" />
        <xsl:apply-templates select="randomGrouping" mode="stimuliTags" />
        <xsl:text>);
        </xsl:text>
    </xsl:template>
    <xsl:template match="mediaLoaded|mediaLoadFailed|mediaPlaybackComplete|conditionTrue|conditionFalse|onError|onSuccess|responseCorrect|responseIncorrect|beforeStimulus|eachStimulus|afterStimulus|hasMoreStimulus|endOfStimulus|multipleUsers|singleUser|aboveThreshold|withinThreshold">
        <xsl:value-of select="if(@msToNext) then concat(', ', @msToNext) else ''" />
        <xsl:value-of select="if(local-name() eq 'multipleUsers') then '' else ', '" />
        <xsl:text>&#xa;new </xsl:text>
        <xsl:value-of select="if(local-name() eq 'eachStimulus' or local-name() eq 'hasMoreStimulus') then 'CurrentStimulusListener' else if(local-name() eq 'mediaLoaded' or local-name() eq 'mediaLoadFailed' or local-name() eq 'mediaPlaybackComplete') then 'CancelableStimulusListener' else 'TimedStimulusListener'" />
        <xsl:text>() {

            @Override
            public void </xsl:text>
        <xsl:value-of select="if(local-name() eq 'mediaLoaded' or local-name() eq 'mediaLoadFailed' or local-name() eq 'mediaPlaybackComplete') then 'trigggerCancelableEvent' else 'postLoadTimerFired'" />
        <xsl:text>(</xsl:text>
        <xsl:value-of select="if(local-name() eq 'eachStimulus' or local-name() eq 'hasMoreStimulus') then 'final StimuliProvider stimulusProvider, final Stimulus currentStimulus' else ''" />
        <xsl:text>) {
        </xsl:text>
        <xsl:apply-templates />
        <xsl:text>
            }
            }</xsl:text>
    </xsl:template>
    <xsl:template match="hasGetParameter|showStimulusGrid|matchingStimulusGrid|groupResponseFeedback|stimulusHasRatingOptions|stimulusHasResponse">
        <xsl:text>    </xsl:text>
        <xsl:value-of select="local-name()" />
        <xsl:text>(appEventListner</xsl:text>
        <xsl:if test="local-name() eq 'showStimulusGrid'
        or local-name() eq 'matchingStimulusGrid'
">
            <xsl:text>, stimulusProvider</xsl:text>
        </xsl:if>
        <xsl:if test="local-name() eq 'stimulusHasRatingOptions'
or local-name() eq 'stimulusHasResponse'
or local-name() eq 'showStimulusGrid'
or local-name() eq 'matchingStimulusGrid'
">
            <xsl:text>, currentStimulus</xsl:text>
        </xsl:if>
        <xsl:apply-templates select="conditionTrue" />
        <xsl:apply-templates select="conditionFalse" />
        <xsl:apply-templates select="responseCorrect" />
        <xsl:apply-templates select="responseIncorrect" />
        <xsl:apply-templates select="hasMoreStimulus" />
        <xsl:apply-templates select="endOfStimulus" />
        <xsl:value-of select="if(@matchingRegex) then concat(', &quot;', @matchingRegex, '&quot;') else ''" />
        <xsl:value-of select="if(@maxStimuli) then concat(', ', @maxStimuli, '') else ''" />
        <xsl:value-of select="if(@randomise) then concat(', ', @randomise eq 'true') else ''" />
        <xsl:value-of select="if(@columnCount) then concat(', ', @columnCount) else ''" />
        <xsl:value-of select="if(@imageWidth) then concat(', &quot;', @imageWidth, '&quot;') else ''" />
        <xsl:value-of select="if(@maxWidth) then concat(', ', @maxWidth) else ''" />
        <xsl:value-of select="if(@animate) then concat(', AnimateTypes.', @animate, '') else ''" />
        <xsl:value-of select="if(@msToNext) then concat(', ', @msToNext) else ''" />
        <xsl:value-of select="if(@eventTag) then concat(', &quot;', @eventTag, '&quot;') else ''" />
        <xsl:value-of select="if(@alternativeChoice) then concat(', &quot;', @alternativeChoice, '&quot;') else ''" />
        <xsl:value-of select="if(@parameterName) then concat(', &quot;', @parameterName, '&quot;') else ''" />
        <xsl:if test="local-name() eq 'showStimulusGrid' or local-name() eq 'matchingStimulusGrid'">
            <xsl:value-of select="if(@dataChannel) then concat(', ',@dataChannel) else ',0'" />
        </xsl:if>
        <xsl:text>);
        </xsl:text>
    </xsl:template>
    <xsl:template match="region|regionStyle|regionReplace|regionClear|logTimerValue|startTimer|triggerListener|habituationParadigmListener|image|groupResponseStimulusImage|backgroundImage|randomMsPause|pause|countdownLabel|stimulusImage|stimulusPresent|stimulusImageCapture|stimulusCodeImage|stimulusCodeAudio|stimulusCodeVideo|stimulusAudio|stimulusPause|groupNetwork|groupNetworkActivity|table|row|column">
        <xsl:text>    </xsl:text>
        <xsl:value-of select="local-name()" />
        <xsl:text>(</xsl:text>
        <xsl:value-of select="if(local-name() eq 'groupNetwork') then 'appEventListner, selfApplicationState, ' else ''" />   
        <xsl:if test="local-name() eq 'groupResponseStimulusImage' 
or local-name() eq 'stimulusImageCapture'
        or local-name() eq 'groupNetwork'
        or local-name() eq 'stimulusPresent'
">
            <xsl:text>stimulusProvider, </xsl:text>
        </xsl:if>
        <xsl:if test="local-name() eq 'stimulusPresent'
 or local-name() eq 'stimulusCodeAudio' 
or local-name() eq 'stimulusCodeVideo' 
or local-name() eq 'stimulusImage' 
or local-name() eq 'stimulusAudio' 
or local-name() eq 'stimulusCodeImage'
or local-name() eq 'stimulusPause'
or local-name() eq 'stimulusImageCapture'
">
            <!--<xsl:value-of select="if(@codeFormat) then ',' else ''" />-->
            <xsl:text>currentStimulus, </xsl:text>
        </xsl:if>
        <xsl:if test="local-name() eq 'groupNetwork'">
            <!--some multiparticipant features require the current stimulus, except the case of an end of stimulus event, in this case the group still needs to be informed-->
            <xsl:value-of select="if(local-name(..) ne 'endOfStimulus') then 'currentStimulus, ' else 'null, '" />
        </xsl:if>
        <xsl:value-of select="if(local-name() eq 'stimulusImageCapture' or local-name() eq 'countdownLabel') then concat('messages.', generate-id(.), '(), ') else ''" />
        <xsl:value-of select="if(@percentOfPage) then concat(@percentOfPage, ', ') else ''" />
        <xsl:value-of select="if(@maxHeight) then concat(@maxHeight, ', ') else ''" />
        <xsl:value-of select="if(@maxWidth) then concat(@maxWidth, ', ') else ''" />
        <xsl:value-of select="if(@src) then concat('&quot;', @src, '&quot;, ') else ''" />
        <xsl:value-of select="if(@animate) then concat('AnimateTypes.', @animate, ', ') else ''" />
        <xsl:value-of select="if(@regionId) then concat('&quot;', @regionId, '&quot;') else ''" />
        <xsl:value-of select="if(@regionId and local-name() ne 'regionClear') then ', ' else ''" />
        <xsl:if test="local-name() eq 'stimulusCodeImage'
or local-name() eq 'table'
or local-name() eq 'image'
or local-name() eq 'column'
or local-name() eq 'stimulusCodeVideo'
or local-name() eq 'region'
or local-name() eq 'regionStyle'
or local-name() eq 'regionReplace'
or local-name() eq 'backgroundImage'">
            <xsl:value-of select="if(@styleName) then concat('&quot;', @styleName, '&quot;') else 'null'" />    
            <xsl:value-of select="if(local-name() ne 'regionStyle') then ', ' else ''" />
        </xsl:if>
        <xsl:value-of select="if(@showOnBackButton) then concat(@showOnBackButton eq 'true', ', ') else ''" />
        <xsl:value-of select="if(@autoPlay) then concat(@autoPlay, ', ') else ''" />
        <xsl:value-of select="if(@loop) then concat(@loop, ', ') else ''" />
        <xsl:value-of select="if(@showControls) then concat(@showControls, ', ') else ''" />
        <xsl:value-of select="if(@msToNext) then @msToNext else ''" />
        <xsl:value-of select="if(@msToNext
 and local-name() ne 'image'
 and local-name() ne 'stimulusImageCapture'
) then ', ' else ''" />
        <xsl:value-of select="if(@listenerId) then concat('&quot;',@listenerId, '&quot;, ') else ''" />
        <xsl:value-of select="if(@threshold) then concat(@threshold, ', ') else ''" />
        <xsl:value-of select="if(@minimum) then concat(@minimum, ', ') else ''" />
        <xsl:value-of select="if(@maximum) then concat(@maximum, ', ') else ''" />
        <xsl:value-of select="if(@matchingRegex) then concat('&quot;', @matchingRegex, '&quot;, ') else ''" />
        <xsl:value-of select="if(@replacementRegex) then concat('&quot;', @replacementRegex, '&quot;, ') else ''" />
        <xsl:value-of select="if(@replacement) then concat('&quot;', @replacement, '&quot;, ') else ''" />
        <xsl:value-of select="if(@msLabelFormat) then concat('&quot;', @msLabelFormat, '&quot;, ') else ''" />
        <xsl:value-of select="if(@codeFormat) then concat('&quot;', @codeFormat, '&quot;,') else ''" />
        <xsl:value-of select="if(@showPlaybackIndicator) then concat(@showPlaybackIndicator eq 'true', ', ') else ''" />
        <xsl:value-of select="if(@groupMembers) then concat('&quot;', @groupMembers, '&quot;, ') else ''" />
        <xsl:value-of select="if(@groupCommunicationChannels) then concat('&quot;', @groupCommunicationChannels, '&quot;, ') else ''" />
        <xsl:value-of select="if(@phasesPerStimulus) then concat(@phasesPerStimulus, ', ') else ''" />
        <xsl:value-of select="if(@eventTag) then concat('&quot;', @eventTag, '&quot;,') else ''" />
        <xsl:if test="
local-name() eq 'logTimerValue' or local-name() eq 'groupResponseStimulusImage' or local-name() eq 'stimulusPresent' or local-name() eq 'stimulusCodeAudio' or local-name() eq 'stimulusCodeVideo' or local-name() eq 'stimulusImage' or local-name() eq 'stimulusAudio' or local-name() eq 'stimulusCodeImage'">
            <!--<xsl:value-of select="if(@codeFormat) then ',' else ''" />-->
            <xsl:value-of select="if(@dataChannel) then concat(@dataChannel,'') else '0'" />
        </xsl:if>
        <!--<xsl:value-of select="if(local-name() eq 'stimulusAudio') then ',' else ''" />-->
        <xsl:if test="local-name() eq 'groupNetworkActivity'">
            <xsl:text>new GroupActivityListener("</xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>", "</xsl:text>
            <xsl:value-of select="@groupRole" />
            <xsl:text>") {
                @Override
                public void triggerActivityListener(final int callerPhase, final String expectedRespondents) {
            </xsl:text>
            <xsl:apply-templates/>
            <xsl:text>
                }
                }
            </xsl:text>            
        </xsl:if>
            
        <xsl:if test="local-name() eq 'table' or local-name() eq 'row' or local-name() eq 'column'
 or local-name() eq 'pause'
 or local-name() eq 'stimulusPause'
 or local-name() eq 'randomMsPause'
 or local-name() eq 'triggerListener'
 or local-name() eq 'habituationParadigmListener'
 or local-name() eq 'countdownLabel'
 or local-name() eq 'stimulusImageCapture'
 or local-name() eq 'groupNetwork'
 or local-name() eq 'backgroundImage'
 or local-name() eq 'startTimer'
 or local-name() eq 'region'
 or local-name() eq 'regionReplace'
">
            <xsl:value-of select="if(local-name() eq 'stimulusImageCapture') then ',' else ''" />
            <xsl:text>&#xa;new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
            </xsl:text>
            <xsl:apply-templates/>
            <xsl:text>
                }
                }
            </xsl:text>
        </xsl:if>
        <xsl:apply-templates select="mediaLoaded" />
        <xsl:apply-templates select="mediaLoadFailed" />
        <xsl:apply-templates select="mediaPlaybackComplete" />
        <xsl:text>
            );
        </xsl:text>
    </xsl:template>
    <xsl:template match="userInfo">
        <xsl:text>    addHtmlText(messages.</xsl:text>
        <xsl:value-of select="generate-id(.)" />
        <xsl:text>(userNameValue, userResults.getUserData().getUserId().toString()));
        </xsl:text>
    </xsl:template>
    <xsl:template match="versionData">
        <xsl:text>    addText("Framework For Interactive Experiments\n" + "Version: " + version.majorVersion() + "."
            + version.minorVersion() + "."
            + version.buildVersion() + "-"
            + version.projectVersion() + "\n"
            + "Compile Date: " + version.compileDate() + "\n"
            + "Last Commit Date: " + version.lastCommitDate());
            stimuliValidation();
            addKeyboardDebug();
        </xsl:text>
    </xsl:template>
    <xsl:template match="stimuli|randomGrouping" mode="stimuliTags">
        <xsl:text>,&#xa;new StimulusSelector[]{</xsl:text>
        <xsl:for-each select="tag">
            <xsl:text>new StimulusSelector("</xsl:text>
            <xsl:value-of select="if(@alias) then @alias else text()" />
            <xsl:text>", Tag.tag_</xsl:text>
            <xsl:value-of select="text()" />
            <xsl:text>)</xsl:text>
            <xsl:if test="position() != last()">
                <xsl:text>, </xsl:text>
            </xsl:if>
        </xsl:for-each>
        <xsl:text>}</xsl:text>
        <xsl:if test="local-name() eq 'randomGrouping'">
            <xsl:value-of select="if(@storageField) then concat(', metadataFieldProvider.', @storageField, 'MetadataField') else ',null'" />
            <xsl:value-of select="if(@consumedTagGroup) then concat(', &quot;', @consumedTagGroup, '&quot;') else ',null'" />
        </xsl:if>
    </xsl:template>
    <xsl:template match="compareTimer|preloadAllStimuli|trigger|resetTrigger|resetStimulus|groupMessageLabel|groupMemberCodeLabel|groupMemberLabel|groupScoreLabel|groupChannelScoreLabel|scoreLabel|clearCurrentScore|scoreIncrement|scoreAboveThreshold|bestScoreAboveThreshold|totalScoreAboveThreshold|withMatchingStimulus|showColourReport|submitTestResults|VideoPanel|startAudioRecorder|stopAudioRecorder|startAudioRecorderTag|endAudioRecorderTag|AnnotationTimelinePanel|withStimuli|loadStimulus|loadSdCardStimulus|validateStimuliResponses|currentStimulusHasTag|existingUserCheck|rewindVideo|playVideo|pauseVideo|stimulusExists">
        <xsl:if test="local-name() eq 'preloadAllStimuli' or local-name() eq 'withStimuli' or local-name() eq 'loadStimulus' or local-name() eq 'loadSdCardStimulus'">
            <xsl:text>{</xsl:text>
            <xsl:text>final StimuliProvider stimulusProvider = </xsl:text>
            <xsl:value-of select="if(@class) then concat('new ', @class, '(') else 'new nl.mpi.tg.eg.experiment.client.service.StimulusProvider('" />
            <xsl:text>
                GeneratedStimulusProvider.values);
            </xsl:text>                    
            <!--iterate oer all undefined attributes and call them on the loadStimulusClass as setters-->
            <xsl:for-each select="@*">
                <xsl:if test="name() ne 'eventTag' and name() ne 'class' and name() ne 'tags'">                
                    <xsl:text>((</xsl:text>     
                    <xsl:value-of select="if(../@class) then ../@class else 'nl.mpi.tg.eg.experiment.client.service.StimulusProvider'" />
                    <xsl:value-of select="concat(')stimulusProvider).set', name(), '(&quot;', ., '&quot;);')"/>
                </xsl:if>
            </xsl:for-each>
        </xsl:if>
        <xsl:value-of select="if(ends-with(local-name(), 'Panel')) then '    set' else '    '" />
        <xsl:value-of select="local-name()" />
        <!--        <xsl:text>(new </xsl:text>
        <xsl:value-of select="local-name()" />-->
        <xsl:text>(</xsl:text>
        <xsl:value-of select="if(@msToNext) then @msToNext else ''" />
        <xsl:value-of select="if(@msToNext and @listenerId) then ', ' else ''" />
        <xsl:value-of select="if(@listenerId) then concat('&quot;',@listenerId, '&quot;') else ''" />
        <xsl:value-of select="if(@target) then concat('ApplicationState.', @target, '.name()') else ''" />
        <xsl:value-of select="if(@src) then concat('&quot;', @src, '&quot;') else ''" />        
        <xsl:value-of select="if(@recordingFormat) then concat('&quot;', @recordingFormat, '&quot;, ') else ''" />
        <xsl:value-of select="if(local-name() eq 'startAudioRecorder') then if(@deviceRegex) then concat('&quot;', @deviceRegex, '&quot;, ') else 'null,' else ''" />
        <xsl:value-of select="if(@filePerStimulus) then concat(@filePerStimulus eq 'true', ', ') else ''" />
        <xsl:value-of select="if(@eventTier) then concat(@eventTier, if (@eventTag) then ', ' else '') else ''" />
        <xsl:value-of select="if(@src and @eventTag) then ', ' else ''" />
        <xsl:value-of select="if(@percentOfPage) then concat(', ', @percentOfPage) else ''" />
        <xsl:value-of select="if(@maxHeight) then concat(', ', @maxHeight) else ''" />
        <xsl:value-of select="if(@maxWidth) then concat(', ', @maxWidth) else ''" />
        <xsl:value-of select="if(@eventTag) then concat('&quot;', @eventTag, '&quot;') else ''" />
        <xsl:value-of select="if(@styleName) then concat('&quot;', @styleName, '&quot;') else ''" />
        <xsl:value-of select="if(@poster) then concat(', &quot;', @poster, '&quot;') else ''" />
        <xsl:apply-templates select="stimuli" mode="stimuliTags" />
        <!-- // todo: currentStimulusHasTag has changed from stimuli/tag... to @tags so this needs to be updated -->
        <xsl:if test="@tags">
            <!--preloadAllStimuli uses this tags attribute-->
            <xsl:value-of select="if(local-name() eq 'preloadAllStimuli') then '' else ','" />
            <xsl:text>new Tag[]{</xsl:text>
            <xsl:for-each select="tokenize(@tags,' ')">
                <xsl:text>Tag.tag_</xsl:text>
                <xsl:value-of select="." />
                <xsl:if test="position() != last()">
                    <xsl:text>, </xsl:text>
                </xsl:if>
            </xsl:for-each>
            <xsl:text>}</xsl:text>
        </xsl:if>
        
        <xsl:apply-templates select="randomGrouping" mode="stimuliTags" />
        <xsl:value-of select="if(@matchingRegex) then concat(', &quot;', @matchingRegex, '&quot;') else ''" />
        <xsl:value-of select="if(@replacementRegex) then concat(', &quot;', @replacementRegex, '&quot;') else ''" />
        <!--<xsl:value-of select="if(@maxStimuli) then concat(', ', @maxStimuli, '') else ''" />-->
        <!--<xsl:value-of select="if(@minStimuliPerTag) then concat(', ', @minStimuliPerTag, '') else ''" />-->
        <!--<xsl:value-of select="if(@maxStimuliPerTag) then concat(', ', @maxStimuliPerTag, '') else ''" />-->
        <xsl:if test="local-name() eq 'bestScoreAboveThreshold' or local-name() eq 'totalScoreAboveThreshold' or local-name() eq 'scoreAboveThreshold'">
            <xsl:value-of select="if(@scoreThreshold eq '') then 'null' else if(@scoreThreshold) then concat('', @scoreThreshold, '') else 'null'" />
            <xsl:value-of select="if(@errorThreshold eq '') then ', null' else if(@errorThreshold) then concat(', ', @errorThreshold, '') else ', null'" />
            <xsl:value-of select="if(@potentialThreshold eq '') then ', null' else if(@potentialThreshold) then concat(', ', @potentialThreshold, '') else ', null'" />
        </xsl:if>
        <xsl:if test="local-name() eq 'bestScoreAboveThreshold' or local-name() eq 'scoreAboveThreshold'">
            <xsl:value-of select="if(@correctStreak eq '') then ', null' else if(@correctStreak) then concat(', ', @correctStreak, '') else ', null'" />
            <xsl:value-of select="if(@errorStreak eq '') then ', null' else if(@errorStreak) then concat(', ', @errorStreak, '') else ', null'" />
        </xsl:if>
        <!-- the trailing comma after scoreThreshold is needed for SynQuiz2, needs to be checked for other configurations. -->
        <!--<xsl:value-of select="if(local-name() eq 'submitTestResults') then ', ' else ''" />-->
        <xsl:value-of select="if(local-name() eq 'showColourReport') then if(@scoreThreshold eq '') then '0, ' else if(@scoreThreshold) then concat('', @scoreThreshold, ', ') else '0, ' else ''" />
        <xsl:value-of select="if(@scoreValue) then concat('', @scoreValue, '') else ''" />
        <xsl:value-of select="if(@columnCount) then concat(', ', @columnCount, '') else ''" />
        <xsl:value-of select="if(@imageWidth) then concat(', &quot;', @imageWidth, '&quot;') else ''" />
        <xsl:value-of select="if(@offset) then @offset else ''" />
        <!--<xsl:value-of select="if(local-name() eq 'loadSdCardStimulus') then if(@excludeRegex) then concat(', &quot;', @excludeRegex, '&quot;') else ', null' else ''" />-->
        <!--<xsl:value-of select="if(@randomise) then concat(', ', @randomise eq 'true') else ''" />-->
        <!--<xsl:value-of select="if(@repeatCount) then concat(', ', @repeatCount) else ''" />-->
        <!--<xsl:value-of select="if(@repeatRandomWindow) then concat(', ', @repeatRandomWindow) else ''" />-->
        <!--        <xsl:if test="@repeatRandomWindow">
            <xsl:value-of select="if(@adjacencyThreshold) then concat(', ', @adjacencyThreshold) else ', 0'" />
        </xsl:if>-->
        <xsl:if test="local-name() eq 'withStimuli'
 or local-name() eq 'loadStimulus' 
or local-name() eq 'withMatchingStimulus'
or local-name() eq 'loadSdCardStimulus'
or local-name() eq 'preloadAllStimuli'
or local-name() eq 'stimulusExists'
">
            <xsl:text>
                ,stimulusProvider
            </xsl:text>
        </xsl:if>       
        <xsl:value-of select="if(local-name() eq 'validateStimuliResponses') then 'false' else ''" />
        <xsl:if test="local-name() eq 'currentStimulusHasTag'
        or local-name() eq 'startAudioRecorder'
        or local-name() eq 'endAudioRecorderTag'
">
            <xsl:text>
                ,currentStimulus
            </xsl:text>
        </xsl:if>                                
        <xsl:apply-templates select="hasMoreStimulus" />
        <xsl:apply-templates select="endOfStimulus" />
        <xsl:apply-templates select="beforeStimulus" />
        <xsl:apply-templates select="eachStimulus" />
        <xsl:apply-templates select="afterStimulus" />
        <xsl:apply-templates select="conditionTrue" />
        <xsl:apply-templates select="conditionFalse" />
        <xsl:apply-templates select="multipleUsers" />
        <xsl:apply-templates select="singleUser" />
        <xsl:if test="local-name() eq 'showColourReport' or local-name() eq 'submitTestResults'">
            <!--the colour report needs to know the email address metadata field, but this field does not exist in all experiments so it must be passed in here-->
            <xsl:text>new MetadataFieldProvider().emailAddressMetadataField</xsl:text>
        </xsl:if>
        <xsl:apply-templates select="aboveThreshold" />
        <xsl:apply-templates select="withinThreshold" />
        <xsl:apply-templates select="onError" />
        <xsl:apply-templates select="onSuccess" />
        <xsl:text>);
        </xsl:text> 
        <xsl:if test="local-name() eq 'preloadAllStimuli' or local-name() eq 'withStimuli' or local-name() eq 'loadStimulus' or local-name() eq 'loadSdCardStimulus'">
            <xsl:text>}</xsl:text>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
