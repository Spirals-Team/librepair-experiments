<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : config2css.xsl
    Created on : October 9, 2015, 10:36 AM
    Author     : Peter Withers <peter.withers@mpi.nl>
    Description:
        This transform creates the properties file that is used by maven to filter the CSS with the colours from the configuration file.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text" encoding="UTF-8" />
    <xsl:template match="/">
        <xsl:text>
$primaryColour0: </xsl:text>
        <xsl:value-of select="experiment/@primaryColour0" /><xsl:text>;
$primaryColour1: </xsl:text>
        <xsl:value-of select="experiment/@primaryColour1" /><xsl:text>;
$primaryColour2: </xsl:text>
        <xsl:value-of select="experiment/@primaryColour2" /><xsl:text>;
$primaryColour3: </xsl:text>
        <xsl:value-of select="experiment/@primaryColour3" /><xsl:text>;
$primaryColour4: </xsl:text>
        <xsl:value-of select="experiment/@primaryColour4" /><xsl:text>;
$complementColour0: </xsl:text>
        <xsl:value-of select="experiment/@complementColour0" /><xsl:text>;
$complementColour1: </xsl:text>
        <xsl:value-of select="experiment/@complementColour1" /><xsl:text>;
$complementColour2: </xsl:text>
        <xsl:value-of select="experiment/@complementColour2" /><xsl:text>;
$complementColour3: </xsl:text>
        <xsl:value-of select="experiment/@complementColour3" /><xsl:text>;
$complementColour4: </xsl:text>
        <xsl:value-of select="experiment/@complementColour4" /><xsl:text>;
$backgroundColour: </xsl:text>
        <xsl:value-of select="experiment/@backgroundColour" /><xsl:text>;
$textFontSize: </xsl:text>
        <xsl:value-of select="experiment/@textFontSize" /><xsl:text>pt;
$gridCellZoom: </xsl:text>
        <xsl:value-of select="experiment/@defaultScale" /><xsl:text>;
</xsl:text>
    </xsl:template>
</xsl:stylesheet>
