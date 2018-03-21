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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;

/**
 * @since Aug 18, 2015 4:39:26 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class PresenterFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long displayOrder;
    static long globalfeatureCounter = 0;
    @Enumerated(EnumType.STRING)
    private FeatureType featureType;
    @ElementCollection
    private List<String> stimulusTags = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private RandomGrouping randomGrouping = new RandomGrouping();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<PresenterFeature> presenterFeatures = new ArrayList<>();
    private HashMap<FeatureAttribute, String> featureAttributes = new HashMap<>();
    private HashMap<String, String> undefinedAttributes = new HashMap<>();
    @XmlTransient
    @ManyToOne(cascade = CascadeType.ALL)
    private FeatureText translatable;

    public PresenterFeature() {
    }

    public PresenterFeature(FeatureType featureType, String featureText) {
        this.featureType = featureType;
//        this.presenterFeatures = new ArrayList<>();
        this.translatable = new FeatureText(featureText, null);
//        this.featureAttributes = new HashMap<>();
        displayOrder = globalfeatureCounter;
        globalfeatureCounter++;
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDisplayOrder(long displayOrder) {
        this.displayOrder = displayOrder;
    }

    @XmlTransient
    public long getDisplayOrder() {
        return displayOrder;
    }

//    @XmlElement(name = "feature")
//    public List<PresenterFeature> getPresenterFeatures() {
//        return presenterFeatures;
//    }
//    @XmlTransient
    public List<PresenterFeature> getPresenterFeatureList() {
        return presenterFeatures;
    }

//    @XmlAnyElement
//    public List<JAXBElement<PresenterFeature>> getPresenterFeatures() {
//        List<JAXBElement<PresenterFeature>> elements = new ArrayList<>();
//        presenterFeatures.stream().forEach((feature) -> {
//            elements.add(new JAXBElement<>(new QName(feature.getFeatureType().name()), PresenterFeature.class, feature));
//        });
//        return elements;
//    }
    @XmlAnyElement
    public List<JAXBElement<PresenterFeature>> getPresenterFeatures() {
        List<JAXBElement<PresenterFeature>> elements = new ArrayList<>();
        presenterFeatures.stream().forEach((feature) -> {
            elements.add(new JAXBElement<>(new QName(feature.getFeatureType().name()), PresenterFeature.class, feature));
        });
        return elements;
    }

    public void setPresenterFeatures(List<PresenterFeature> presenterFeatures) {
        this.presenterFeatures = presenterFeatures;
    }

    @XmlTransient
    public FeatureType getFeatureType() {
        return featureType;
    }

    public PresenterFeature addFeature(FeatureType featureType, String text, String... attributes) {
        if (featureType.canHaveText() == (text != null && !text.isEmpty())) {
            final PresenterFeature presenterFeature = new PresenterFeature(featureType, text);
            getPresenterFeatureList().add(presenterFeature);
            if (featureType.getFeatureAttributes() != null) {
                for (int index = 0; index < featureType.getFeatureAttributes().length; index++) {
                    presenterFeature.addFeatureAttributes(featureType.getFeatureAttributes()[index], attributes[index]);
                }
            }
            return presenterFeature;
        } else {
            throw new UnsupportedOperationException(featureType.name() + ((featureType.canHaveText()) ? " requires feature text." : " cannot have text"));
        }
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    @XmlAttribute
    public String getFeatureText() {
        return translatable.getFeatureText();
    }

    public void setFeatureText(String featureText) {
        this.translatable = new FeatureText(featureText, null);
    }

    public void addTranslation(FeatureText featureText) {
        translatable.addTranslation(featureText);
    }

    @XmlElement(name = "translation")
    public List<FeatureText> getTranslation() {
        return translatable.getTranslations();
    }

    @XmlElementWrapper(name = "stimuli")
    @XmlElement(name = "tag")
    public List<String> getStimulusTags() {
        return (featureType.canHaveStimulusTags()) ? stimulusTags : null;
    }

    public void setStimulusTags(List<String> stimulusTags) {
        this.stimulusTags = stimulusTags;
    }
//    @XmlAttribute
//    public String getStimulusTags() {
//        if (stimulusTags == null) {
//            return null;
//        } else {
//            return String.join(",", stimulusTags);
//        }
//    }

    @XmlElement(name = "randomGrouping")
    public RandomGrouping getRandomGrouping() {
        return (featureType.isCanHaveRandomGrouping()) ? randomGrouping : null;
    }

    public void setRandomGrouping(RandomGrouping randomGrouping) {
        if (featureType.isCanHaveRandomGrouping()) {
            this.randomGrouping = randomGrouping;
        } else {
            throw new UnsupportedOperationException("randomGrouping and StimulusTags are not supported in this type");
        }
    }

    public void addStimulusTag(String tag) {
        stimulusTags.add(Stimulus.cleanTagString(tag));
    }

    @XmlAnyAttribute
    public Map<QName, String> getFeatureAttributes() {
        Map<QName, String> attributeMap = new HashMap();
        final ArrayList<FeatureAttribute> keyList1 = new ArrayList<>(featureAttributes.keySet());
        keyList1.sort(Enum::compareTo);
        keyList1.stream().forEach((featureAttribute) -> {
            attributeMap.put(new QName(featureAttribute.name()), featureAttributes.get(featureAttribute));
        });
        final ArrayList<String> keyList2 = new ArrayList<>(undefinedAttributes.keySet());
        keyList2.sort(String::compareTo);
        keyList2.stream().forEach((featureAttribute) -> {
            attributeMap.put(new QName(featureAttribute), undefinedAttributes.get(featureAttribute));
        });
        return attributeMap;
    }

    public String getFeatureAttributes(FeatureAttribute featureAttribute) {
        return this.featureAttributes.get(featureAttribute);
    }

    public void addFeatureAttributes(FeatureAttribute featureAttribute, String attributeValue) {
        if (attributeValue == null) {
            throw new IllegalArgumentException("attributeValue cannot be null: " + featureAttribute.name());
        }
        this.featureAttributes.put(featureAttribute, attributeValue);
    }

    public void addUndefinedAttribute(String undefinedAttribute, String attributeValue) {
        if (featureType.isCanHaveUndefinedAttribute()) {
            if (attributeValue == null) {
                throw new IllegalArgumentException("attributeValue cannot be null: " + undefinedAttribute);
            }
            for (FeatureAttribute featureAttribute : FeatureAttribute.values()) {
                if (featureAttribute.toString().equals(undefinedAttribute)) {
                    throw new IllegalArgumentException("attributeValue cannot be be a member of FeatureAttribute: " + undefinedAttribute);
                }
            }
            this.undefinedAttributes.put(undefinedAttribute, attributeValue);
        } else {
            throw new UnsupportedOperationException("UndefinedAttribute is not supported in this type");
        }
    }

    public void setFeatureAttributes(HashMap<FeatureAttribute, String> featureAttributes) {
        this.featureAttributes = featureAttributes;
    }

    @XmlTransient
    public HashMap<FeatureAttribute, String> getFeatureAttributesMap() {
        return featureAttributes;
    }
}
