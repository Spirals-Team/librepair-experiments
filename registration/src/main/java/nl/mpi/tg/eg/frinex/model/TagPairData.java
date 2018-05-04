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
package nl.mpi.tg.eg.frinex.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * @since Jun 30, 2015 12:13:58 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class TagPairData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date tagDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date submitDate;
    private String experimentName;
    private String screenName;
    private String eventTag;
    private String tagValue1;
//    @Column(length = 8192)
//    @Column(length = 16384)
//    @Column(length = 32768)
//    @Column(length = 63536)
//    @Column(length = 126976) // touch input data can be greater than 507904 
    @Column(length = 507904)
    private String tagValue2;
//    ALTER TABLE tag_pair_data ALTER COLUMN tag_value2 TYPE character varying(507904);
    private String userId;
    private int eventMs;

    public TagPairData() {
    }

    public TagPairData(String userId, String screenName, String eventTag, String tagValue1, String tagValue2, int eventMs, Date tagDate) {
        this.tagDate = tagDate;
        this.screenName = screenName;
        this.eventTag = eventTag;
        this.tagValue1 = tagValue1;
        this.tagValue2 = tagValue2;
        this.userId = userId;
        this.eventMs = eventMs;
    }

    public long getId() {
        return id;
    }

    public Date getTagDate() {
        return tagDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getEventTag() {
        return eventTag;
    }

    public String getTagValue1() {
        return tagValue1;
    }

    public String getTagValue2() {
        return tagValue2;
    }

    public String getUserId() {
        return userId;
    }

    public int getEventMs() {
        return eventMs;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }
}
