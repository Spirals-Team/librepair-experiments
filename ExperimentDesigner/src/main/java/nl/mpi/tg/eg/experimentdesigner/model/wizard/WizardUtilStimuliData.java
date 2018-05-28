/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.experimentdesigner.model.wizard;

/**
 * @since Mar 8, 2018 12:18:58 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardUtilStimuliData {

    public enum StimuliType {
        text, touch
    }

    public enum StimuliFields {
        identifier, audio, video, image, label, code, pause, rating, correct
    }
    protected String stimuliName;
    protected StimuliType stimuliType;
    protected String instructions = null;
    protected String[] stimuliCodes; // todo: implement or remove the use of stimuliCodes for image screens
    protected StimuliFields[] stimuliFields;
    protected String[] stimuliArray;
    protected String[] randomStimuliTags;
    protected String[] options;
    protected String ratingLabels = null;
    protected String stimuliLabelStyle = null;
    protected String stimuliLayout = null;
    protected String stimuliHotKey = null;
    // todo: move these rating specific items into options
    protected String freeTextValidationRegex = null;
    protected String freeTextValidationMessage = null;
    protected String freeTextAllowedCharCodes = null;

    public String getStimuliName() {
        return stimuliName;
    }

    public void setStimuliName(String stimuliName) {
        this.stimuliName = stimuliName;
    }

    public StimuliType getStimuliType() {
        return stimuliType;
    }

    public void setStimuliType(StimuliType stimuliType) {
        this.stimuliType = stimuliType;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getRatingLabels() {
        return ratingLabels;
    }

    public void setRatingLabels(String ratingLabels) {
        this.ratingLabels = ratingLabels;
    }

    public String getFreeTextValidationRegex() {
        return freeTextValidationRegex;
    }

    public void setFreeTextValidationRegex(String freeTextValidationRegex) {
        this.freeTextValidationRegex = freeTextValidationRegex;
    }

    public String getFreeTextValidationMessage() {
        return freeTextValidationMessage;
    }

    public void setFreeTextValidationMessage(String freeTextValidationMessage) {
        this.freeTextValidationMessage = freeTextValidationMessage;
    }

    public String getFreeTextAllowedCharCodes() {
        return freeTextAllowedCharCodes;
    }

    public void setFreeTextAllowedCharCodes(String freeTextAllowedCharCodes) {
        this.freeTextAllowedCharCodes = freeTextAllowedCharCodes;
    }

    public String getStimuliLabelStyle() {
        return stimuliLabelStyle;
    }

    public void setStimuliLabelStyle(String stimuliLabelStyle) {
        this.stimuliLabelStyle = stimuliLabelStyle;
    }

    public String getStimuliLayout() {
        return stimuliLayout;
    }

    public void setStimuliLayout(String stimuliLayout) {
        this.stimuliLayout = stimuliLayout;
    }

    public String getStimuliHotKey() {
        return stimuliHotKey;
    }

    public void setStimuliHotKey(String stimuliHotKey) {
        this.stimuliHotKey = stimuliHotKey;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String[] getStimuliArray() {
        return stimuliArray;
    }

    public void setStimuliArray(String[] stimuliArray) {
        this.stimuliArray = stimuliArray;
    }

    public StimuliFields[] getStimuliFields() {
        return stimuliFields;
    }

    public void setStimuliFields(StimuliFields[] stimuliFields) {
        this.stimuliFields = stimuliFields;
    }

    public String[] getStimuliCodes() {
        return stimuliCodes;
    }

    public void setStimuliCodes(String[] stimuliCodes) {
        this.stimuliCodes = stimuliCodes;
    }

    public String[] getRandomStimuliTags() {
        return randomStimuliTags;
    }

    public void setRandomStimuliTags(String[] randomStimuliTags) {
        this.randomStimuliTags = randomStimuliTags;
    }
}
