/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @since Sep 4, 2017 4:05:09 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class FeatureText {

    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(max = 6000)
    private String featureText;
    private String locale;

    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeatureText> translations = new ArrayList<>();

    public FeatureText() {
    }

    public FeatureText(String featureText, String locale) {
        this.featureText = featureText;
        this.locale = locale;
    }

    public List<FeatureText> getTranslations() {
        return translations;
    }

    public void addTranslation(FeatureText featureText) {
        this.translations.add(featureText);
    }

    @XmlAttribute
    public String getFeatureText() {
        return featureText;
    }

    @XmlAttribute
    public String getLocale() {
        return locale;
    }
}
