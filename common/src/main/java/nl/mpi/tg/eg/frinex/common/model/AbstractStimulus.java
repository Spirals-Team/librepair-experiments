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
package nl.mpi.tg.eg.frinex.common.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @since Oct 27, 2017 2:17:01 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AbstractStimulus implements Stimulus {

    final private String uniqueId;
    final private List<Tag> tags;
    final private String label;
    final private String code;
    final private int pauseMs;
    final private String audioPath;
    final private String videoPath;
    final private String imagePath;
    final private String ratingLabels;
    final private String correctResponses;

//    public AbstractStimulus(String uniqueId, Tag tags[], String label, String ratingLabels, String correctResponses) {
//        this.uniqueId = uniqueId;
//        this.tags = Arrays.asList(tags);
//        this.label = label;
//        this.code = null;
//        this.pauseMs = 0;
//        this.audioPath = null;
//        this.videoPath = null;
//        this.imagePath = null;
//        this.ratingLabels = ratingLabels;
//        this.correctResponses = correctResponses;
//    }
    public AbstractStimulus(String uniqueId, Tag tags[], String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses) {
        this.uniqueId = uniqueId;
        this.tags = Arrays.asList(tags);
        this.label = label;
        this.code = code;
        this.pauseMs = pauseMs;
        this.audioPath = audioPath;
        this.videoPath = videoPath;
        this.imagePath = imagePath;
        this.ratingLabels = ratingLabels;
        this.correctResponses = correctResponses;
    }

//    public AbstractStimulus(String uniqueId, Tag tags[], String label, String code, int pauseMs, String ratingLabels, String correctResponses) {
//        this.uniqueId = (uniqueId != null) ? uniqueId : code;
//        this.tags = Arrays.asList(tags);
//        this.label = label;
//        this.code = code;
//        this.pauseMs = pauseMs;
//        this.audioPath = null;
//        this.videoPath = null;
//        this.imagePath = null;
//        this.ratingLabels = ratingLabels;
//        this.correctResponses = correctResponses;
//    }
   
    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getRatingLabels() {
        return ratingLabels;
    }

    @Override
    public String getCorrectResponses() {
        return correctResponses;
    }

    @Override
    public int getPauseMs() {
        return pauseMs;
    }

    @Override
    public boolean hasAudio() {
        return audioPath != null;
    }

    @Override
    public boolean hasVideo() {
        return videoPath != null;
    }

    @Override
    public boolean hasImage() {
        return imagePath != null;
    }

    @Override
    public boolean hasRatingLabels() {
        return ratingLabels != null;
    }

    @Override
    public boolean hasCorrectResponses() {
        return correctResponses != null;
    }

    @Override
    public String getAudio() {
        return audioPath;
    }

    @Override
    public String getImage() {
        return imagePath;
    }

    @Override
    public String getVideo() {
        return videoPath;
    }

    @Override
    public int compareTo(Stimulus o) {
        return this.uniqueId.compareTo(o.getUniqueId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.uniqueId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stimulus other = (Stimulus) obj;
        if (!Objects.equals(this.uniqueId, other.getUniqueId())) {
            return false;
        }
        return true;
    }
}
