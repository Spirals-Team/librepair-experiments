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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 * @since Dec 1, 2015 1:32:47 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class PublishEvents implements Serializable {

    public enum PublishState {

        editing,
        staging,
        production
    };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date publishDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date expiryDate;

    private PublishState publishState;
    private boolean isWebApp;
    private boolean isDesktop;
    private boolean isiOS;
    private boolean isAndroid;
    @ManyToOne
    private Experiment experiment;
    private String buildName;

    public PublishEvents() {
    }

    public PublishEvents(Experiment experiment, Date publishDate, Date expiryDate, PublishState publishState, boolean isWebApp, boolean isiOS, boolean isAndroid, boolean isDesktop) {
        this.publishDate = publishDate;
        this.expiryDate = expiryDate;
        this.publishState = publishState;
        this.isWebApp = isWebApp;
        this.isiOS = isiOS;
        this.isAndroid = isAndroid;
        this.isDesktop = isDesktop;
        this.experiment = experiment;
        this.buildName = experiment.getAppNameInternal();
    }

    public String getBuildName() {
        return buildName;
    }

    public String getExperimentInternalName() {
        return experiment.getAppNameInternal();
    }

    public String getExperimentDisplayName() {
        return experiment.getAppNameDisplay();
    }

    public PublishState getState() {
        return publishState;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public boolean isIsWebApp() {
        return isWebApp;
    }

    public boolean isIsiOS() {
        return isiOS;
    }

    public boolean isIsAndroid() {
        return isAndroid;
    }

    public boolean isIsDesktop() {
        return isDesktop;
    }

    public float getDefaultScale() {
        return experiment.getDefaultScale();
    }

    public int isIsScalable() {
        return (experiment.isIsScalable()) ? 1 : 0;
    }
}
