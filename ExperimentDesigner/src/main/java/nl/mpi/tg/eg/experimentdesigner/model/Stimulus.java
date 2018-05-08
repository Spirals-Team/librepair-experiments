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
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @since Sep 16, 2015 11:15:06 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class Stimulus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int pauseMs;

    private String audioPath;
    private String videoPath;
    private String imagePath;
    private String identifier;
    @Size(max = 5500)
    private String label;
    private String code;
    private byte[] imageData = null;
    private byte[] audioData = null;
    private byte[] videoData = null;
    private String ratingLabels = null;
    private String correctResponses = null;

    TreeSet<String> stimulusTags;

    public Stimulus() {
    }

    public Stimulus(String identifier, String audioPath, String videoPath, String imagePath, String label, String code, int pauseMs, HashSet<String> stimulusTags, String ratingLabels, String correctResponses) {
        this.identifier = (identifier == null) ? null : cleanIdString(identifier); // todo: ; in the id kills the JSON data sending cleanTagString(... or cleanIdString(
        // todo: fields need to be systematically (identifier is treated in two different ways constructor and setter) cleaned of bad chars or the XSLT updated
        this.audioPath = audioPath;
        this.videoPath = videoPath;
        this.imagePath = imagePath;
        this.label = label;
        this.code = code;
        this.pauseMs = pauseMs;
        this.stimulusTags = new TreeSet<>();
        for (String tag : stimulusTags) {
            addStimulusTag(tag);
        }
        this.ratingLabels = ratingLabels;
        this.correctResponses = correctResponses;
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlAttribute
    public int getPauseMs() {
        return pauseMs;
    }

    public void setPauseMs(int pauseMs) {
        this.pauseMs = pauseMs;
    }

    @XmlAttribute
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = (identifier == null) ? null : cleanIdString(identifier); // todo: ; in the id kills the JSON data sending cleanTagString(... or cleanIdString(
    }

    @XmlAttribute
    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    @XmlAttribute
    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @XmlAttribute
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @XmlAttribute
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlAttribute
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @XmlTransient
    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @XmlTransient
    public byte[] getAudioData() {
        return audioData;
    }

    public void setAudioData(byte[] audioData) {
        this.audioData = audioData;
    }

    @XmlTransient
    public byte[] getVideoData() {
        return videoData;
    }

    public void setVideoData(byte[] videoData) {
        this.videoData = videoData;
    }

    @XmlAttribute
    public String getRatingLabels() {
        return ratingLabels;
    }

    public void setRatingLabels(String ratingLabels) {
        this.ratingLabels = ratingLabels;
    }

    @XmlAttribute
    public String getCorrectResponses() {
        return correctResponses;
    }

    public void setCorrectResponses(String correctResponses) {
        this.correctResponses = correctResponses;
    }

    @XmlAttribute(name = "tags")
    public TreeSet<String> getStimulusTags() {
        return stimulusTags;
    }

    final public void addStimulusTag(String stimulusTag) {
        this.stimulusTags.add(cleanTagString(stimulusTag));
    }

    public static final String cleanTagString(String stimulusTag) {
        return stimulusTag.replaceAll("[ \\t\\n\\x0B\\f\\r\\(\\)\\{\\};\\?\\/\\\\\\]\\[,'\"\\.=-]+", "_");
    }

    public static final String cleanIdString(String stimulusTag) {
        return stimulusTag.replaceAll(";", "_");
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.identifier);
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
        if (!Objects.equals(this.identifier, other.identifier)) {
            return false;
        }
        return true;
    }

}
