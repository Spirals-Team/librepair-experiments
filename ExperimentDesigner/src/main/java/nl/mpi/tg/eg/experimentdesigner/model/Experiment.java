/*
 * Copyright (C) 2015 Pivotal Software, Inc.
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
package nl.mpi.tg.eg.experimentdesigner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @since Sep 4, 2015 2:42:21 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
@XmlRootElement
public class Experiment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String appNameDisplay;
    private boolean showMenuBar = true;
    @Column(unique = true)
    private String appNameInternal;
    private String resourceNetworkPath; // path to MPI_Scratch that contains any resource files needed for the experiment
//    private String nextPresenterTag;
    private String primaryColour0;
    private String primaryColour1;
    private String primaryColour2;
    private String primaryColour3;
    private String primaryColour4;
    private String complementColour0;
    private String complementColour1;
    private String complementColour2;
    private String complementColour3;
    private String complementColour4;
    private String backgroundColour;
    private int textFontSize = 17;
    private boolean isScalable = true;
    private boolean isRotatable = true;
    private boolean preserveLastState = true;
    private boolean preventWindowClose = true;
    private float defaultScale = 1;

    @OneToMany(mappedBy = "experiment")
    private List<PublishEvents> publishEvents = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("displayOrder ASC")
    private List<PresenterScreen> presenterScreen = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Metadata> metadata = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Stimulus> stimuli = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DataChannel> dataChannels = new ArrayList<>();

    public Experiment() {
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    @XmlAttribute
    public String getAppNameDisplay() {
        return appNameDisplay;
    }

    public void setAppNameDisplay(String appNameDisplay) {
        this.appNameDisplay = appNameDisplay;
    }

    @XmlAttribute
    public String getAppNameInternal() {
        return appNameInternal;
    }

    public void setAppNameInternal(String appNameInternal) {
        this.appNameInternal = appNameInternal.toLowerCase();
    }

    @XmlAttribute
    public boolean isShowMenuBar() {
        return showMenuBar;
    }

    public void setShowMenuBar(boolean showMenuBar) {
        this.showMenuBar = showMenuBar;
    }

    @XmlAttribute
    public int getTextFontSize() {
        return textFontSize;
    }

    public void setTextFontSize(int textFontSize) {
        this.textFontSize = textFontSize;
    }

    @XmlElementWrapper(name = "administration")
    @XmlElement(name = "dataChannel")
    public List<DataChannel> getDataChannels() {
        return dataChannels;
    }

    public void setDataChannels(List<DataChannel> dataChannels) {
        this.dataChannels = dataChannels;
    }

    @XmlAttribute
    public boolean isIsScalable() {
        return isScalable;
    }

    public void setIsScalable(boolean isScalable) {
        this.isScalable = isScalable;
    }

    @XmlAttribute
    public boolean isRotatable() {
        return isRotatable;
    }

    public void setRotatable(boolean isRotatable) {
        this.isRotatable = isRotatable;
    }

    @XmlAttribute
    public boolean isPreserveLastState() {
        return preserveLastState;
    }

    public void setPreserveLastState(boolean preserveLastState) {
        this.preserveLastState = preserveLastState;
    }

    public boolean preventWindowClose() {
        return preventWindowClose;
    }

    public void setPreventWindowClose(boolean preventWindowClose) {
        this.preventWindowClose = preventWindowClose;
    }

    @XmlAttribute
    public float getDefaultScale() {
        return defaultScale;
    }

    public void setDefaultScale(float defaultScale) {
        this.defaultScale = defaultScale;
    }

    @XmlAttribute
    public String getResourceNetworkPath() {
        return resourceNetworkPath;
    }

    public void setResourceNetworkPath(String resourceNetworkPath) {
        this.resourceNetworkPath = resourceNetworkPath;
    }

    public List<PublishEvents> getPublishEvents() {
        return publishEvents;
    }

    public void setPrimaryColour1(String primaryColour1) {
        this.primaryColour1 = primaryColour1;
    }

    @XmlAttribute
    public String getPrimaryColour1() {
        return primaryColour1;
    }

    public void setPrimaryColour2(String primaryColour2) {
        this.primaryColour2 = primaryColour2;
    }

    @XmlAttribute
    public String getPrimaryColour2() {
        return primaryColour2;
    }

    public void setPrimaryColour3(String primaryColour3) {
        this.primaryColour3 = primaryColour3;
    }

    @XmlAttribute
    public String getPrimaryColour3() {
        return primaryColour3;
    }

    @XmlAttribute
    public String getComplementColour1() {
        return complementColour1;
    }

    public void setComplementColour1(String complementColour1) {
        this.complementColour1 = complementColour1;
    }

    @XmlAttribute
    public String getComplementColour2() {
        return complementColour2;
    }

    public void setComplementColour2(String complementColour2) {
        this.complementColour2 = complementColour2;
    }

    @XmlAttribute
    public String getComplementColour3() {
        return complementColour3;
    }

    public void setComplementColour3(String complementColour3) {
        this.complementColour3 = complementColour3;
    }

    @XmlAttribute
    public String getPrimaryColour0() {
        return primaryColour0;
    }

    public void setPrimaryColour0(String primaryColour0) {
        this.primaryColour0 = primaryColour0;
    }

    @XmlAttribute
    public String getPrimaryColour4() {
        return primaryColour4;
    }

    public void setPrimaryColour4(String primaryColour4) {
        this.primaryColour4 = primaryColour4;
    }

    @XmlAttribute
    public String getComplementColour0() {
        return complementColour0;
    }

    public void setComplementColour0(String complementColour0) {
        this.complementColour0 = complementColour0;
    }

    @XmlAttribute
    public String getComplementColour4() {
        return complementColour4;
    }

    public void setComplementColour4(String complementColour4) {
        this.complementColour4 = complementColour4;
    }

    @XmlAttribute
    public String getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

//    public String getNextPresenterTag() {
//        return nextPresenterTag;
//    }
//
//    public void setNextPresenterTag(String nextPresenterTag) {
//        this.nextPresenterTag = nextPresenterTag;
//    }
    @XmlElement(name = "presenter")
    public List<PresenterScreen> getPresenterScreen() {
        return presenterScreen;
    }

    public void setPresenterScreen(List<PresenterScreen> PresenterScreen) {
        this.presenterScreen = PresenterScreen;
    }

    @XmlElementWrapper(name = "metadata")
    @XmlElement(name = "field")
    public List<Metadata> getMetadata() {
        return metadata;
    }

//    @XmlAttribute
//    public int getMetadataCount() {
//        return metadata.size();
//    }
    @Transient
    public void addMetadataOnce(Metadata metadataField) {
        if (!metadata.contains(metadataField)) {
            metadata.add(metadataField);
        }
    }

    public void setMetadata(List<Metadata> metadata) {
        this.metadata = metadata;
    }

    @XmlElementWrapper(name = "stimuli")
    @XmlElement(name = "stimulus")
    public List<Stimulus> getStimuli() {
        return stimuli;
    }

    public void setStimuli(List<Stimulus> stimuli) {
        this.stimuli = stimuli;
    }

    @Transient
    public void appendUniqueStimuli(List<Stimulus> stimuliList) {
        if (stimuliList != null) {
            for (Stimulus stimulus : stimuliList) {
                if (!this.stimuli.contains(stimulus)) {
                    this.stimuli.add(stimulus);
                } else {
                    final int indexExisting = this.stimuli.indexOf(stimulus);
                    this.stimuli.get(indexExisting).getStimulusTags().addAll(stimulus.getStimulusTags());
                }
            }
        }
    }
}
