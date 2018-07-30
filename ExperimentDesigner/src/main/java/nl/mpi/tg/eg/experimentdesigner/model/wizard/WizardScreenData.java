/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.experimentdesigner.model.wizard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;
import nl.mpi.tg.eg.experimentdesigner.model.Metadata;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.StimuliSubAction;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;

/**
 * @since Aug 8, 2016 11:15:50 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class WizardScreenData implements Serializable {

    @Transient
    final PresenterScreen presenterScreen = new PresenterScreen();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long displayOrder; //@ todo: set and provide a method for the user to change displayOrder
    @Enumerated(EnumType.STRING)
    private WizardScreenEnum wizardScreenType = null;
    @OneToOne(cascade = CascadeType.ALL)
    private WizardScreenData backWizardScreenData = null;
    @OneToOne(cascade = CascadeType.ALL)
    private WizardScreenData nextWizardScreenData = null;
    @OneToMany(cascade = CascadeType.ALL)
    private List<WizardScreenData> menuWizardScreenData = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ScreenText> screenText = null;
    @ElementCollection(targetClass = Boolean.class)
    private List<Boolean> screenBooleans = null;
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> screenIntegers = null;
    private String[] nextButton = null;
    private String screenTitle = null;
    private String menuLabel = null;
    private String screenTag = null;
    private Boolean centreScreen = null;
    private String freeTextValidationRegex = null;
    private String freeTextValidationMessage = null;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Stimulus> stimuli = null;
    private boolean canHaveStimuli = false;
    private String[] stimuliRandomTags = null;
    private String screenMediaPath = null;
//    private Integer stimulusMsDelay = null;
    private String stimulusCodeMatch = null;
    private Integer stimulusCodeMsDelay = null;
    private String stimulusCodeFormat = null;
    private Integer stimuliCount = null;
    private Integer maxStimuliPerTag = null;
    private Integer repeatCount = null;
    private Integer repeatRandomWindow = null;
    private String buttonLabelEventTag = null;
    private String backgroundImage = null;
    private String on_Error_Text = null;
    @Size(max = 2000)
    private String helpText = null;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Metadata> metadataFields = null;
    private String stimulusResponseOptions = null;
    private String stimulusResponseLabelRight = null;
    private String stimulusResponseLabelLeft = null;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StimuliSubAction> stimuliSubActions = null;
    private Integer taskIndex = null;
    private String groupMembers = null;
    private String groupCommunicationChannels = null;
    private String[] groupPhasesRoles = null;

    public WizardScreenData() {
    }

    public WizardScreenData(WizardScreenEnum wizardScreenType) {
        this.wizardScreenType = wizardScreenType;
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(long displayOrder) {
        this.displayOrder = displayOrder;
    }

    @XmlElement(name = "wizardScreenType")
    public WizardScreenEnum getWizardScreenType() {
        return wizardScreenType;
    }

    public PresenterScreen getPresenterScreen() {
        return presenterScreen;
    }

    @XmlIDREF
    @XmlElement(name = "backScreen")
    public WizardScreenData getBackWizardScreenData() {
        return backWizardScreenData;
    }

    public void setBackWizardScreenData(WizardScreenData backWizardScreenData) {
        this.backWizardScreenData = backWizardScreenData;
    }

    @XmlIDREF
    @XmlElement(name = "nextScreen")
    public WizardScreenData getNextWizardScreenData() {
        return nextWizardScreenData;
    }

    public void setNextWizardScreenData(WizardScreenData nextWizardScreenData) {
        this.nextWizardScreenData = nextWizardScreenData;
    }

    @XmlIDREF
    @XmlElement(name = "menuScreen")
    public List<WizardScreenData> getMenuWizardScreenData() {
        return menuWizardScreenData;
    }

    public void setMenuWizardScreenData(List<WizardScreenData> menuWizardScreenData) {
        this.menuWizardScreenData = menuWizardScreenData;
    }

    @XmlElement(name = "text")
    public List<ScreenText> getScreenText() {
        return screenText;
    }

    public String getScreenText(int index) {
        return (screenText != null && screenText.size() > index) ? screenText.get(index).screenText : null;
    }

    public void setScreenText(int index, String screenTextString) {
        if (screenText == null) {
            screenText = new ArrayList<>();
        }
        while (screenText.size() <= index) {
            screenText.add(new ScreenText());
        }
        this.screenText.get(index).screenText = screenTextString;
    }

    @XmlElement(name = "boolean")
    public List<Boolean> getScreenBooleans() {
        return screenBooleans;
    }

    public Boolean getScreenBoolean(int index) {
        return (screenBooleans != null && screenBooleans.size() > index) ? screenBooleans.get(index) : null;
    }

    public void setScreenBoolean(int index, Boolean screenBoolean) {
        if (screenBooleans == null) {
            screenBooleans = new ArrayList<>();
        }
        while (screenBooleans.size() <= index) {
            screenBooleans.add(null);
        }
        this.screenBooleans.set(index, screenBoolean);
    }

    @XmlElement(name = "integer")
    public List<Integer> getScreenIntegers() {
        return screenIntegers;
    }

    public void setScreenIntegers(List<Integer> screenIntegers) {
        this.screenIntegers = screenIntegers;
    }

    public Integer getScreenInteger(int index) {
        return (screenIntegers != null && screenIntegers.size() > index) ? screenIntegers.get(index) : null;
    }

    public void setScreenIntegers(int index, Integer screenInteger) {
        if (screenIntegers == null) {
            screenIntegers = new ArrayList<>();
        }
        while (screenIntegers.size() <= index) {
            screenIntegers.add(null);
        }
        this.screenIntegers.set(index, screenInteger);
    }

    public String[] getNextButton() {
        return nextButton;
    }

    public void setNextButton(String[] nextButton) {
        this.nextButton = nextButton;
    }

    public String getScreenTitle() {
        return screenTitle;
    }

    public void setScreenTitle(String screenTitle) {
        this.screenTitle = screenTitle;
    }

    public String getMenuLabel() {
        return menuLabel;
    }

    public void setMenuLabel(String menuLabel) {
        this.menuLabel = menuLabel;
    }

    @XmlID
    public String getScreenTag() {
        return screenTag;
    }

    public void setScreenTag(String screenTag) {
        this.screenTag = screenTag;
    }

    public Boolean isCentreScreen() {
        return centreScreen;
    }

    public void setCentreScreen(Boolean centreScreen) {
        this.centreScreen = centreScreen;
    }

    public boolean canHaveStimuli() {
        return canHaveStimuli;
    }

    public List<Stimulus> getStimuli() {
        return stimuli;
    }

    public void setStimuli(List<Stimulus> stimuli) {
        canHaveStimuli = true;
        this.stimuli = stimuli;
    }

    public String getScreenMediaPath() {
        return screenMediaPath;
    }

    public void setScreenMediaPath(String screenMediaPath) {
        this.screenMediaPath = screenMediaPath;
    }

    public String[] getStimuliRandomTags() {
        return stimuliRandomTags;
    }

    public void setStimuliRandomTags(String[] stimuliRandomTags) {
        this.stimuliRandomTags = stimuliRandomTags;
    }

    public String getStimulusCodeMatch() {
        return stimulusCodeMatch;
    }

    public void setStimulusCodeMatch(String stimulusCodeMatch) {
        this.stimulusCodeMatch = stimulusCodeMatch;
    }

    public Integer getStimulusCodeMsDelay() {
        return stimulusCodeMsDelay;
    }

    public void setStimulusCodeMsDelay(Integer stimulusCodeMsDelay) {
        this.stimulusCodeMsDelay = stimulusCodeMsDelay;
    }

    public String getStimulusCodeFormat() {
        return stimulusCodeFormat;
    }

    public void setStimulusCodeFormat(String stimulusCodeFormat) {
        this.stimulusCodeFormat = stimulusCodeFormat;
    }

    public Integer getStimuliCount() {
        return stimuliCount;
    }

    public void setStimuliCount(int stimuliCount) {
        this.stimuliCount = stimuliCount;
    }

    public Integer getMaxStimuliPerTag() {
        return maxStimuliPerTag;
    }

    public void setMaxStimuliPerTag(Integer maxStimuliPerTag) {
        this.maxStimuliPerTag = maxStimuliPerTag;
    }

    public String getButtonLabelEventTag() {
        return buttonLabelEventTag;
    }

    public void setButtonLabelEventTag(String buttonLabelEventTag) {
        this.buttonLabelEventTag = buttonLabelEventTag;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getOn_Error_Text() {
        return on_Error_Text;
    }

    public void setOn_Error_Text(String on_Error_Text) {
        this.on_Error_Text = on_Error_Text;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Integer getRepeatRandomWindow() {
        return repeatRandomWindow;
    }

    public void setRepeatRandomWindow(Integer repeatRandomWindow) {
        this.repeatRandomWindow = repeatRandomWindow;
    }

    public List<Metadata> getMetadataFields() {
        if (metadataFields == null) {
            metadataFields = new ArrayList<>();
        }
        return metadataFields;
    }

    public String getStimulusResponseOptions() {
        return stimulusResponseOptions;
    }

    public void setStimulusResponseOptions(String stimulusResponseOptions) {
        this.stimulusResponseOptions = stimulusResponseOptions;
    }

    public String getStimulusResponseLabelRight() {
        return stimulusResponseLabelRight;
    }

    public void setStimulusResponseLabelRight(String stimulusResponseLabelRight) {
        this.stimulusResponseLabelRight = stimulusResponseLabelRight;
    }

    public String getStimulusResponseLabelLeft() {
        return stimulusResponseLabelLeft;
    }

    public void setStimulusResponseLabelLeft(String stimulusResponseLabelLeft) {
        this.stimulusResponseLabelLeft = stimulusResponseLabelLeft;
    }

    public List<StimuliSubAction> getStimuliSubActions() {
        return stimuliSubActions;
    }

    public void setStimuliSubActions(List<StimuliSubAction> stimuliSubActions) {
        this.stimuliSubActions = stimuliSubActions;
    }

    public Integer getTaskIndex() {
        return taskIndex;
    }

    public void setTaskIndex(Integer taskIndex) {
        this.taskIndex = taskIndex;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getGroupCommunicationChannels() {
        return groupCommunicationChannels;
    }

    public void setGroupCommunicationChannels(String groupCommunicationChannels) {
        this.groupCommunicationChannels = groupCommunicationChannels;
    }

    public String[] getGroupPhasesRoles() {
        return groupPhasesRoles;
    }

    public void setGroupPhasesRoles(String[] groupPhasesRoles) {
        this.groupPhasesRoles = groupPhasesRoles;
    }

}
