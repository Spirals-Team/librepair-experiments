<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : config2metadata.xsl
    Created on : June 22, 2015, 11:30 AM
    Author     : Peter Withers <peter.withers@mpi.nl>
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text" encoding="UTF-8" />
    <xsl:param name="targetClientDirectory" select="targetClientDirectory"/>
    <xsl:template match="/">
        <xsl:result-document href="{$targetClientDirectory}/MetadataFields.properties" method="text">
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>postName_</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>=</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>
                </xsl:text>
                <xsl:text>registrationField_</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>=</xsl:text>
                <xsl:value-of select="replace(@registrationField,'''','''''')" />
                <xsl:text>
                </xsl:text>
                <!--<xsl:if test="@fieldValues">-->
                <xsl:text>fieldValues_</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>=</xsl:text>
                <xsl:value-of select="@fieldValues" />
                <xsl:text>
                </xsl:text>
                <!--</xsl:if>-->
                <!--<xsl:if test="@controlledMessage">-->
                <xsl:text>controlledMessage_</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>=</xsl:text>
                <xsl:value-of select="@controlledMessage" />
                <xsl:text>
                </xsl:text>
                <!--</xsl:if>-->
                <!--<xsl:if test="@controlledRegex">-->
                <xsl:text>controlledRegex_</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>=</xsl:text>
                <xsl:value-of select="@controlledRegex" />
                <xsl:text>
                </xsl:text>
                <!--                   <xsl:text>preventServerDuplicates_</xsl:text><xsl:value-of select="@postName" /><xsl:text>=</xsl:text><xsl:value-of select="@preventServerDuplicates" /><xsl:text>
                </xsl:text>
                                   <xsl:text>duplicatesControlledMessage_</xsl:text><xsl:value-of select="@postName" /><xsl:text>=</xsl:text><xsl:value-of select="@duplicatesControlledMessage" /><xsl:text>
                </xsl:text>-->
                <!--</xsl:if>-->
            </xsl:for-each>
        </xsl:result-document>
        <xsl:result-document href="{$targetClientDirectory}/service/MetadataFieldProvider.java" method="text">
            <xsl:text>package nl.mpi.tg.eg.experiment.client.service;

                import com.google.gwt.core.client.GWT;
                import nl.mpi.tg.eg.experiment.client.MetadataFields;
                import nl.mpi.tg.eg.experiment.client.model.MetadataField;
                
                public class MetadataFieldProvider {

                private final MetadataFields mateadataFields = GWT.create(MetadataFields.class);
            </xsl:text>
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>
                    public final MetadataField </xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>MetadataField = new MetadataField(mateadataFields.postName_</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>(), mateadataFields.registrationField_</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>(), mateadataFields.fieldValues_</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>(), mateadataFields.controlledRegex_</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>(), mateadataFields.controlledMessage_</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>());</xsl:text>
            </xsl:for-each>
            <xsl:text>
                public final MetadataField[] metadataFieldArray = new MetadataField[]{
            </xsl:text>
            <xsl:value-of select="experiment/metadata/field/@postName" separator="MetadataField, " />
            <xsl:text>MetadataField
                };
                }</xsl:text>
        </xsl:result-document>
    </xsl:template>
</xsl:stylesheet>
