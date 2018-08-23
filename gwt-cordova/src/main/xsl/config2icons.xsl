<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : config2css.xsl
    Created on : March 1, 2016, 14:16 PM
    Author     : Peter Withers <peter.withers@mpi.nl>
    Description:
        This transform creates the bash script that is used to generate the icon.png for use in cordova.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text" encoding="UTF-8" />
    <!--     <xsl:variable name="iconSizes">
        <element>
            <iconSize>512x512</iconSize>
            <destinationFile>icon.png</destinationFile>
        </element>
        <element>
            <iconSize>1024x512</iconSize>
            <destinationFile>test.png</destinationFile>
        </element>
    </xsl:variable>
    <xsl:template match="/">
        <xsl:for-each select="$iconSizes">
            <xsl:text>
                convert -gravity center -size </xsl:text>
            <xsl:value-of select="@iconSize" />
            <xsl:text> -background "</xsl:text>
            <xsl:value-of select="experiment/@backgroundColour" />
            <xsl:text>" -fill "</xsl:text>
            <xsl:value-of select="experiment/@primaryColour4" />
            <xsl:text>" -bordercolor "</xsl:text>
            <xsl:value-of select="experiment/@primaryColour2" />
            <xsl:text>" -border 10x10 -font /Library/Fonts/Arial\ Black.ttf -pointsize 80 label:"</xsl:text>
            <xsl:value-of select="experiment/@appNameDisplay" />
            <xsl:text>\nFrinex\nFieldKit" </xsl:text>
            <xsl:value-of select="@destinationFile" />
            <xsl:text>
            </xsl:text>
        </xsl:for-each>
    </xsl:template>-->
    <xsl:template match="/">
        <xsl:text>if [ ! -f icon.png ]; then&#xa;</xsl:text>
        <xsl:text>convert -gravity center -size 512x512 -background "</xsl:text>
        <xsl:value-of select="experiment/@backgroundColour" />
        <xsl:text>" -fill "</xsl:text>
        <xsl:value-of select="experiment/@primaryColour4" />
        <xsl:text>" -bordercolor "</xsl:text>
        <xsl:value-of select="experiment/@primaryColour2" />
        <xsl:text>" -border 10x10 -font /Library/Fonts/Arial\ Black.ttf -pointsize 80 label:"</xsl:text>
        <xsl:value-of select="replace(experiment/@appNameDisplay, ' ', '\\ ')" />
        <xsl:text>\nFrinex\nFieldKit" icon.png&#xa;</xsl:text>
        <xsl:text>fi&#xa;</xsl:text>
    </xsl:template>
</xsl:stylesheet>
