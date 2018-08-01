/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.experiment.client.model;

import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @since Jan 11, 2016 4:07:26 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SdCardStimulus implements Stimulus {

    final private String uniqueId;
    final private String stimulusBasePath;
    private String stimulusImagePath;
    final private String label;
    final private String code;
    final private int pauseMs;
    private boolean isAudio;
    private boolean isVideo;

    public SdCardStimulus(String uniqueId, String stimulusBasePath, String label, String code, int pauseMs, boolean isAudio, boolean isVideo, String stimulusImagePath) {
        this.uniqueId = uniqueId;
        this.stimulusBasePath = stimulusBasePath;
        this.stimulusImagePath = stimulusImagePath;
        this.label = label;
        this.code = code;
        this.pauseMs = pauseMs;
        this.isAudio = isAudio;
        this.isVideo = isVideo;
    }

    public void addImage(String stimulusImagePath) {
        this.stimulusImagePath = stimulusImagePath;
    }

    public void addAudio() {
        this.isAudio = true;
    }

    public void addVideo() {
        this.isVideo = true;
    }

    @Override
    public boolean isCorrect(String value) {
        return false;
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
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
    public int getPauseMs() {
        return pauseMs;
    }

    @Override
    public String getAudio() {
        return isAudio ? stimulusBasePath : null;
    }

    @Override
    public String getImage() {
        return stimulusImagePath;
    }

    @Override
    public String getVideo() {
        return isVideo ? stimulusBasePath : null;
    }

    @Override
    public boolean hasImage() {
        return stimulusImagePath != null;
    }

    @Override
    public boolean hasAudio() {
        return isAudio;
    }

    @Override
    public boolean hasVideo() {
        return isVideo;
    }

    @Override
    public boolean hasRatingLabels() {
        return false;
    }

    @Override
    public String getRatingLabels() {
        return null;
    }

    @Override
    public boolean hasCorrectResponses() {
        return false;
    }

    @Override
    public String getCorrectResponses() {
        return null;
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList(stimulusImagePath.split("_"));
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
