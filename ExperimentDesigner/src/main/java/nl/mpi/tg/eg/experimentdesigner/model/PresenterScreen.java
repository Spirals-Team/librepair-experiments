/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics
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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;

/**
 * @since Aug 18, 2015 1:42:03 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class PresenterScreen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long displayOrder;
    private String title;
    private String menuLabel;
    @ManyToOne
    private PresenterScreen backPresenter;
    private String selfPresenterTag;
    @ManyToOne
    private PresenterScreen nextPresenter;

    @Enumerated(EnumType.STRING)
    private PresenterType presenterType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<PresenterFeature> presenterFeatures = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("displayOrder ASC")
    private List<PresenterScreen> presenterScreens = new ArrayList<>();

    public PresenterScreen() {
    }

    public PresenterScreen(String title, String menuLabel, PresenterScreen backPresenter, String selfPresenterTag, PresenterScreen nextPresenter, PresenterType presenterType, long displayOrder) {
        this.title = title;
        this.menuLabel = menuLabel;
        this.backPresenter = backPresenter;
        this.selfPresenterTag = selfPresenterTag;
        this.nextPresenter = nextPresenter;
        this.presenterType = presenterType;
        this.displayOrder = displayOrder;
    }

    public void setPresenterType(PresenterType presenterType) {
        this.presenterType = presenterType;
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public int getUsageCount() {
        return presenterScreens.size();
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlAttribute
    public long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(long displayOrder) {
        this.displayOrder = displayOrder;
    }

    @XmlAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlAttribute
    public String getMenuLabel() {
        return menuLabel;
    }

    public void setMenuLabel(String menuLabel) {
        this.menuLabel = menuLabel;
    }

    @XmlAttribute(name = "back")
    public String getBackPresenterTag() {
        return (backPresenter == null) ? null : backPresenter.selfPresenterTag;
    }

    @XmlTransient
    public PresenterScreen getBackPresenter() {
        return backPresenter;
    }

    @XmlTransient
    public PresenterScreen getNextPresenter() {
        return nextPresenter;
    }

    public void setBackPresenter(PresenterScreen backPresenter) {
        this.backPresenter = backPresenter;
    }

    @XmlAttribute(name = "self")
    public String getSelfPresenterTag() {
        return selfPresenterTag;
    }

    public void setSelfPresenterTag(String selfPresenterTag) {
        this.selfPresenterTag = selfPresenterTag;
    }

    @XmlAttribute(name = "next")
    public String getNextPresenterTag() {
        return (nextPresenter == null) ? null : nextPresenter.selfPresenterTag;
    }

    public void setNextPresenter(PresenterScreen nextPresenter) {
        this.nextPresenter = nextPresenter;
    }

    @XmlAttribute(name = "type")
    public PresenterType getPresenterType() {
        return presenterType;
    }

    public List<PresenterFeature> getPresenterFeatureList() {
        return presenterFeatures;
    }

    @XmlElement(name = "feature")
    public List<PresenterFeature> getPresenterFeature() {
        return null;
    }

    @XmlAnyElement
    public List<JAXBElement<PresenterFeature>> getPresenterFeatures() {
        List<JAXBElement<PresenterFeature>> elements = new ArrayList<>();
        presenterFeatures.stream().forEach((feature) -> {
            elements.add(new JAXBElement<>(new QName(feature.getFeatureType().name()), PresenterFeature.class, feature));
        });
        return elements;
    }

//    @XmlElements({
//        @XmlElement(name = "htmlText", type = PresenterFeature.class),
//        @XmlElement(name = "padding", type = PresenterFeature.class),
//        @XmlElement(name = "AudioRecorderPanel", type = PresenterFeature.class),
//        @XmlElement(name = "optionButton", type = PresenterFeature.class),
//        @XmlElement(name = "padding", type = PresenterFeature.class),
//        @XmlElement(name = "versionData", type = PresenterFeature.class)
//    })
//    public void setPresenterFeatures(List<PresenterFeature> presenterFeatures) {
//        this.presenterFeatures = presenterFeatures;
//    }
}
