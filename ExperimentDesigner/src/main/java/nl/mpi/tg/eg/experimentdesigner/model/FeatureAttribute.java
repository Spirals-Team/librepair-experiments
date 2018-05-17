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

/**
 * this can be updated with the output of: grep select=
 * ~/Documents/ExperimentTemplate/gwt-cordova/src/main/xsl/config2java.xsl
 *
 * @since Aug 18, 2015 4:51:23 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public enum FeatureAttribute {

    menuLabel,
    closeButtonLabel,
    back,
    next,
    fieldName,
    parameterName,
    linkedFieldName,
    code,
    codeFormat,
    validationRegex,
    allowedCharCodes(true),
    matchingRegex(true),
    replacement(true),
    src,
    link,
    type,
    headerKey(true), separator(true), // these attributes are used by the administration system to process the tabular data from custom stimuli modules eg advocas
    percentOfPage,
    maxHeight,
    maxWidth,
    align,
    target(true), // this is probably not optional in some cases
    styleName(true),
    showOnBackButton(true),
    eventTier,
    filePerStimulus, // when recording audio this boolean determins if a separate recording should be made for each stimulus or one recording for the set of stimuli
    eventTag(true),
    ratingLabels,
    ratingLabelLeft(true),
    ratingLabelRight(true),
    sendData,
    networkErrorMessage,
    inputErrorMessage,
    randomise,
    repeatCount(true),
    repeatRandomWindow(true),
    adjacencyThreshold(true),
    repeatIncorrect,
    hotKey(true),
    @Deprecated
    mp3,
    @Deprecated
    mp4,
    @Deprecated
    ogg,
    @Deprecated
    webm,
    wavFormat,
    poster,
    autoPlay,
    loop,
    columnCount,
    kintypestring,
    diagramName,
    imageWidth,
    alternativeChoice,
    msToNext(true),
    listenerId, threshold, maximum, minimum,
    msLabelFormat,
    animate(true), // animate currently has bounce stimuliCode or none
    minStimuliPerTag(true), // for each tag there should be at least N of each represented in the final list
    maxStimuliPerTag(true), // for each tag there should be no more than N of each represented in the final list
    maxStimuli(true),
    excludeRegex(true),
    //    alias, // alias is used to specify a tag or set of tags via GET parameters
    condition0Tag,
    condition1Tag,
    condition2Tag,
    scoreThreshold,
    showPlaybackIndicator,
    showControls(true),
    groupRole,
    groupMembers,
    groupCommunicationChannels,
    incrementPhase,
    //    incrementStimulus,
    phasesPerStimulus,
    scoreValue;
    final boolean isOptional;

    private FeatureAttribute() {
        this.isOptional = false;
    }

    private FeatureAttribute(boolean isOptional) {
        this.isOptional = isOptional;
    }

    public boolean isOptional() {
        return isOptional;
    }
}
