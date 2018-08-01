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
package nl.mpi.tg.eg.experimentdesigner.model;

import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardScreen;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardScreenData;

/**
 * @since Mar 4, 2016 3:10:35 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
@XmlRootElement
public class WizardData {

    @Id
    @XmlTransient
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String appName = "";
    private boolean showMenuBar = true;
    private boolean obfuscateScreenNames = false;

    private int textFontSize = 17;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @XmlElement(name = "dataChannel")
    private List<DataChannel> dataChannels = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("displayOrder ASC")
    @XmlElement(name = "wizardScreen")
    private final List<WizardScreenData> wizardScreenList = new ArrayList<>();

    public WizardData() {
    }

    public String getAppName() {
        return appName;
    }

    public long getId() {
        return id;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isShowMenuBar() {
        return showMenuBar;
    }

    public void setShowMenuBar(boolean showMenuBar) {
        this.showMenuBar = showMenuBar;
    }

    public int getTextFontSize() {
        return textFontSize;
    }

    public void setTextFontSize(int textFontSize) {
        this.textFontSize = textFontSize;
    }

    public List<DataChannel> getDataChannels() {
        return dataChannels;
    }

    public void addDataChannel(DataChannel dataChannel) {
        this.dataChannels.add(dataChannel);
    }

    public boolean isObfuscateScreenNames() {
        return obfuscateScreenNames;
    }

    public void setObfuscateScreenNames(boolean obfuscateScreenNames) {
        this.obfuscateScreenNames = obfuscateScreenNames;
    }

    public void addScreen(final WizardScreen wizardScreen) {
        final WizardScreenData wizardScreenData = wizardScreen.getWizardScreenData();
        wizardScreenData.setDisplayOrder(wizardScreenList.size());
        wizardScreenList.add(wizardScreenData);
    }

    public List<WizardScreenData> getWizardScreens() {
        return wizardScreenList;
    }
}
