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
package nl.mpi.tg.eg.experimentdesigner.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;

/**
 * @since May 9, 2018 5:41:05 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SchemaGenerator {

    private void getStart(Writer writer) throws IOException {
        writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
                + "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n");
    }

    private void addExperiment(Writer writer) throws IOException {
        writer.append("<xs:simpleType name=\"rgbHexValue\">\n");
        writer.append("<xs:restriction base=\"xs:token\">\n");
        writer.append("<xs:pattern value=\"#[\\dA-Fa-f]{6}\"/>\n");
        writer.append("</xs:restriction>\n");
        writer.append("</xs:simpleType>\n");
        writer.append("<xs:simpleType name=\"lowercaseValue\">\n");
        writer.append("<xs:restriction base=\"xs:string\">\n");
        writer.append("<xs:pattern value=\"[a-z]([a-z_0-9]){3,}\"/>\n");
        writer.append("</xs:restriction>\n");
        writer.append("</xs:simpleType>\n");
        writer.append("<xs:simpleType name=\"integerList\">\n");
        writer.append("<xs:list itemType=\"xs:integer\"/>\n");
        writer.append("</xs:simpleType>\n");

        writer.append("<xs:element name=\"experiment\">\n").append("<xs:complexType>\n").append("<xs:sequence minOccurs=\"1\" maxOccurs=\"1\">\n");
        writer.append("<xs:annotation>\n");
        writer.append("<xs:documentation>Root element of the experiment configuration file of which only one is permitted.</xs:documentation>\n");
        writer.append("</xs:annotation>\n");
//        for (final PresenterType presenterType : presenterTypes) {
//            writer.append("<xs:element ref=\"").append(presenterType.name()).append("\"/>\n");
//        }
        writer.append("<xs:element name=\"preventWindowClose\" minOccurs=\"0\" maxOccurs=\"1\">\n");
        writer.append("<xs:complexType>\n");
        writer.append("<xs:attribute name=\"featureText\" use=\"required\" type=\"xs:string\"/>\n");
        writer.append("</xs:complexType>\n");
        writer.append("</xs:element>\n");
        writer.append("<xs:element name=\"administration\" type=\"administrationType\" minOccurs=\"0\" maxOccurs=\"1\"/>\n");
        writer.append("<xs:element name=\"metadata\" type=\"metadataType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
        writer.append("<xs:element name=\"presenter\"  minOccurs=\"1\" maxOccurs=\"unbounded\" type=\"presenterType\"/>\n");
        writer.append("<xs:element name=\"stimuli\" type=\"stimuliType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
        writer.append("</xs:sequence>\n");
        for (String attributeStrings : new String[]{"appNameDisplay"}) {
            writer.append("<xs:attribute name=\"").append(attributeStrings).append("\" type=\"xs:string\" use=\"required\"/>\n");
        }
        for (String attributeLowercase : new String[]{"appNameInternal"}) {
            writer.append("<xs:attribute name=\"").append(attributeLowercase).append("\" type=\"lowercaseValue\" use=\"required\"/>\n");
        }
        for (String attributeRGBs : new String[]{"backgroundColour", "complementColour0", "complementColour1", "complementColour2", "complementColour3", "complementColour4", "primaryColour0", "primaryColour1", "primaryColour2", "primaryColour3", "primaryColour4"}) {
            writer.append("<xs:attribute name=\"").append(attributeRGBs).append("\" type=\"rgbHexValue\" use=\"required\"/>\n");
        }
        for (String attributeBooleans : new String[]{"isScalable", "preserveLastState", "rotatable", "showMenuBar"}) {
            writer.append("<xs:attribute name=\"").append(attributeBooleans).append("\" type=\"xs:boolean\" use=\"required\"/>\n");
        }
        for (String attributeFloats : new String[]{"defaultScale"}) {
            writer.append("<xs:attribute name=\"").append(attributeFloats).append("\" type=\"xs:decimal\" use=\"required\"/>\n");
        }
        for (String attributeIntegers : new String[]{"textFontSize"}) {
            writer.append("<xs:attribute name=\"").append(attributeIntegers).append("\" type=\"xs:integer\" use=\"required\"/>\n");
        }
        for (String attributeIntegerLists : new String[]{}) {
            writer.append("<xs:attribute name=\"").append(attributeIntegerLists).append("\" type=\"integerList\" use=\"required\"/>\n");
        }
        writer.append("</xs:complexType>\n").append("</xs:element>\n");
    }

    private void addPresenter(Writer writer, final PresenterType[] presenterTypes) throws IOException {
        writer.append("<xs:complexType name=\"presenterType\">\n").append("<xs:choice minOccurs=\"0\" maxOccurs=\"unbounded\">\n");
        for (final FeatureType featureRef : FeatureType.values()) {
            if (featureRef.getIsChildType() == FeatureType.Contitionals.none) {
                writer.append("<xs:element name=\"").append(featureRef.name()).append("\" type=\"").append(featureRef.name()).append("Type\"/>\n");
            }
        }
        writer.append("</xs:choice>\n");
        writer.append("<xs:attribute name=\"back\" type=\"xs:string\"/>\n"); // todo: constrain back to always refer to an presenter that exists
        writer.append("<xs:attribute name=\"next\" type=\"xs:string\"/>\n"); // todo: constrain back to always refer to an presenter that exists
        writer.append("<xs:attribute name=\"self\" type=\"xs:string\"/>\n");
        writer.append("<xs:attribute name=\"title\" type=\"xs:string\"/>\n");
        writer.append("<xs:attribute name=\"menuLabel\" type=\"xs:string\"/>\n");
        writer.append("<xs:attribute name=\"type\">\n");
        writer.append("<xs:simpleType>\n");
        writer.append("<xs:restriction>\n");
        writer.append("<xs:simpleType>\n");
        writer.append("<xs:list>\n");
        writer.append("<xs:simpleType>\n");
        writer.append("<xs:restriction base=\"xs:token\">\n");
        for (final PresenterType presenterType : presenterTypes) {
            writer.append("<xs:enumeration value=\"").append(presenterType.name()).append("\"/>\n");
        }
        writer.append("</xs:restriction>\n");
        writer.append("</xs:simpleType>\n");
        writer.append("</xs:list>\n");
        writer.append("</xs:simpleType>\n");
        writer.append("<xs:minLength value=\"1\"/>\n");
        writer.append("</xs:restriction>\n");
        writer.append("</xs:simpleType>\n");
        writer.append("</xs:attribute>\n");
//        writer.append("<xs:assert test=\"count(*/metadataField) le 0\"/>"); // todo: apply this test to enforce presenter type constraints if possible
        writer.append("</xs:complexType>\n");
    }

    private void addAdministration(Writer writer) throws IOException {
        writer.append("<xs:complexType  name=\"administrationType\">\n").append("<xs:sequence>\n");
        writer.append("<xs:element name=\"dataChannel\" minOccurs=\"0\" maxOccurs=\"unbounded\">\n").append("<xs:complexType>\n");
//        writer.append("<xs:sequence>\n").append("</xs:sequence>\n");
        writer.append("<xs:attribute name=\"channel\" type=\"xs:decimal\" use=\"required\"/>\n");
        writer.append("<xs:attribute name=\"label\" type=\"xs:string\" use=\"required\"/>\n");
        writer.append("<xs:attribute name=\"logToSdCard\" type=\"xs:boolean\" use=\"required\"/>\n");
        writer.append("</xs:complexType>\n").append("</xs:element>\n");
        writer.append("</xs:sequence>\n");
        writer.append("</xs:complexType>\n");
    }

    private void addMetadata(Writer writer) throws IOException {
        writer.append("<xs:complexType  name=\"metadataType\">\n").append("<xs:sequence>\n");
        writer.append("<xs:element name=\"field\" maxOccurs=\"unbounded\">\n").append("<xs:complexType>\n");
//        writer.append("<xs:sequence>\n").append("</xs:sequence>\n");
        writer.append("<xs:attribute name=\"controlledMessage\" type=\"xs:string\" use=\"required\"/>\n");
        writer.append("<xs:attribute name=\"controlledRegex\" type=\"xs:string\" use=\"required\"/>\n");
        writer.append("<xs:attribute name=\"postName\" type=\"xs:string\" use=\"required\"/>\n");
        writer.append("<xs:attribute name=\"preventServerDuplicates\" type=\"xs:boolean\" use=\"optional\"/>\n");
        writer.append("<xs:attribute name=\"duplicatesControlledMessage\" type=\"xs:string\" use=\"optional\"/>\n");
        writer.append("<xs:attribute name=\"registrationField\" type=\"xs:string\" use=\"required\"/>\n");
        writer.append("</xs:complexType>\n").append("</xs:element>\n");
        writer.append("</xs:sequence>\n");
        writer.append("</xs:complexType>\n");
    }

    private void addStimuli(Writer writer) throws IOException {
        writer.append("<xs:complexType name=\"stimuliType\">\n").append("<xs:sequence>\n");
        writer.append("<xs:element name=\"stimulus\" minOccurs=\"0\" maxOccurs=\"unbounded\">\n");
        writer.append("</xs:element>\n");
        writer.append("</xs:sequence>\n");
        writer.append("</xs:complexType>\n");
    }

    private void addFeature(Writer writer, final FeatureType featureType) throws IOException {
        writer.append("<xs:complexType name=\"").append(featureType.name()).append("Type\">\n");
        if (featureType.canHaveFeatures()) {
            writer.append("<xs:choice minOccurs=\"0\" maxOccurs=\"unbounded\">\n");
            for (final FeatureType featureRef : FeatureType.values()) {
                if (featureRef.getIsChildType() == FeatureType.Contitionals.none || featureRef.getIsChildType() == featureType.getRequiresChildType()
                        || featureRef.getIsChildType() == FeatureType.Contitionals.groupNetworkAction // currently allowing all groupNetworkAction in any element
                        || featureRef.getIsChildType() == FeatureType.Contitionals.stimulusAction // currently allowing all stimulusAction in any element
                        ) {
                    writer.append("<xs:element name=\"").append(featureRef.name()).append("\" type=\"").append(featureRef.name()).append("Type\"/>\n");
                }
            }
            writer.append("</xs:choice>\n");
        } else {
            switch (featureType.getRequiresChildType()) {
                case hasTrueFalseCondition:
                    writer.append("<xs:all>\n");
                    writer.append("<xs:element name=\"conditionTrue\" type=\"conditionTrueType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("<xs:element name=\"conditionFalse\" type=\"conditionFalseType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("</xs:all>\n");
                    break;
                case hasCorrectIncorrect:
                    writer.append("<xs:all>\n");
                    writer.append("<xs:element name=\"responseCorrect\" type=\"responseCorrectType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("<xs:element name=\"responseIncorrect\" type=\"responseIncorrectType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("</xs:all>\n");
                    break;
                case hasMoreStimulus:
                    writer.append("<xs:all>\n");
                    writer.append("<xs:element name=\"hasMoreStimulus\" type=\"hasMoreStimulusType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("<xs:element name=\"endOfStimulus\" type=\"endOfStimulusType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("<xs:element name=\"randomGrouping\" minOccurs=\"0\" maxOccurs=\"1\"/>\n");
                    writer.append("<xs:element name=\"stimuli\" minOccurs=\"0\" maxOccurs=\"1\"/>\n");
                    writer.append("</xs:all>\n");
                    break;
                case hasErrorSuccess:
                    writer.append("<xs:all>\n");
                    writer.append("<xs:element name=\"onError\" type=\"onErrorType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("<xs:element name=\"onSuccess\" type=\"onSuccessType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("</xs:all>\n");
                    break;
                case hasUserCount:
                    writer.append("<xs:all>\n");
                    writer.append("<xs:element name=\"multipleUsers\" type=\"multipleUsersType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("<xs:element name=\"singleUser\" type=\"singleUserType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("</xs:all>\n");
                    break;
                case hasThreshold:
                    writer.append("<xs:all>\n");
                    writer.append("<xs:element name=\"aboveThreshold\" type=\"aboveThresholdType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("<xs:element name=\"withinThreshold\" type=\"withinThresholdType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("</xs:all>\n");
                    break;
                case groupNetworkActivity:
                    writer.append("<xs:choice minOccurs=\"0\" maxOccurs=\"unbounded\">\n");
                    writer.append("<xs:element name=\"groupNetworkActivity\" type=\"groupNetworkActivityType\"/>\n");
                    writer.append("<xs:element name=\"sendGroupEndOfStimuli\" type=\"sendGroupEndOfStimuliType\"/>\n");
                    writer.append("</xs:choice>\n");
                    break;
                case hasMediaPlayback:
                    writer.append("<xs:all>\n");
                    writer.append("<xs:element name=\"mediaLoaded\" type=\"mediaLoadedType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("<xs:element name=\"mediaLoadFailed\" type=\"mediaLoadFailedType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("<xs:element name=\"mediaPlaybackComplete\" type=\"mediaPlaybackCompleteType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("</xs:all>\n");
                    break;
                case hasMediaLoading:
                    writer.append("<xs:all>\n");
                    writer.append("<xs:element name=\"mediaLoaded\" type=\"mediaLoadedType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("<xs:element name=\"mediaLoadFailed\" type=\"mediaLoadFailedType\" minOccurs=\"1\" maxOccurs=\"1\"/>\n");
                    writer.append("</xs:all>\n");
                    break;
//                case needsConditionalParent:
//                    break;
                case none:
                    break;
                default:
                    writer.append("<xs:all>\n");
                    for (FeatureType featureType1 : FeatureType.values()) {
                        if (featureType1.getIsChildType() == featureType.getRequiresChildType()) {
                            writer.append("<xs:element name=\"").append(featureType1.name()).append("\" type=\"").append(featureType1.name()).append("Type\"/>\n");
                        }
                    }
                    if (featureType.canHaveStimulusTags() && featureType.isCanHaveRandomGrouping()) {
                        writer.append("<xs:element name=\"randomGrouping\" minOccurs=\"0\" maxOccurs=\"1\"/>\n");
                        writer.append("<xs:element name=\"stimuli\" minOccurs=\"0\" maxOccurs=\"1\"/>\n");
                    }
                    writer.append("</xs:all>\n");
                    break;
            }
        }
        // todo: canHaveStimulus check for currentStimulusHasTag etc
        if (featureType.canHaveText()) {
            writer.append("<xs:attribute name=\"featureText\" type=\"xs:string\" use=\"required\"/>\n");
        }
        if (featureType.getFeatureAttributes() != null) {
            for (final FeatureAttribute featureAttribute : featureType.getFeatureAttributes()) {
                writer.append("<xs:attribute name=\"");
                writer.append(featureAttribute.name());
                writer.append("\" type=\"xs:string\"");
                if (!featureAttribute.isOptional() && !featureType.allowsCustomImplementation()) {
                    writer.append(" use=\"required\"");
                }
                writer.append("/>\n");
            }
        }
        if (featureType.canHaveStimulusTags() && !featureType.isCanHaveRandomGrouping()) {
            writer.append("<xs:attribute name=\"tags\" type=\"xs:string\" use=\"required\"/>\n");
        }
        if (featureType.allowsCustomImplementation()) {
            writer.append("<xs:attribute name=\"class\" type=\"xs:string\"/>\n");
            writer.append("<xs:anyAttribute  processContents=\"lax\"/>\n");

            writer.append("<xs:assert test=\"(@class) or (not(@class) ");
            for (final FeatureAttribute featureAttribute : featureType.getFeatureAttributes()) {
                if (!featureAttribute.isOptional()) {
                    writer.append(" and @");
                    writer.append(featureAttribute.name());
                }
            }
            writer.append(")\"/>\n");
        }
        writer.append("</xs:complexType>\n");
    }

    private void endState(Writer writer) throws IOException {
        writer.append("}\n");
    }

    private void getStateChange(Writer writer, final String state1, final String state2, final String... attributes) throws IOException {
        writer.append("<xs:element name=\"").append(state1).append("\">\n")
                .append("<xs:complexType>\n")
                .append("<xs:sequence>\n");
        for (final String value : attributes) {
            writer.append("<xs:element ref=\"").append(value).append("\"/>\n");
        }
        writer.append("</xs:sequence>\n").append("</xs:complexType>\n").append("</xs:element>\n");
    }

    private void getEnd(Writer writer) throws IOException {
        writer.append("</xs:schema>\n");
    }

    public void appendContents(Writer writer) throws IOException {
        getStart(writer);
        addExperiment(writer);
        addAdministration(writer);
        addMetadata(writer);
        addPresenter(writer, PresenterType.values());
        addStimuli(writer);
//        for (PresenterType presenterType : PresenterType.values()) {
//            addPresenter(writer, presenterType);
//            for (FeatureType featureType : presenterType.getFeatureTypes()) {
//                getStateChange(writer, presenterType.name(), featureType.name());
//            }
//            endState(writer);
//        }
        for (FeatureType featureType : FeatureType.values()) {
            addFeature(writer, featureType);
        }
        getEnd(writer);
        System.out.println(writer);
//        assertEquals(expResult, stringBuilder.toString());
    }

    public void createSchemaFile(final File schemaOutputFile) throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
        FileWriter schemaOutputWriter = new FileWriter(schemaOutputFile);
        BufferedWriter bufferedWriter = new BufferedWriter(schemaOutputWriter);
        appendContents(bufferedWriter);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
