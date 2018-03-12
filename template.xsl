<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <entries>
            <xsl:apply-templates select="//field"/>
        </entries>
    </xsl:template>

    <xsl:template match="field">
        <xsl:element name="entry">
            <xsl:attribute name="field">
                <xsl:value-of select="."/>
            </xsl:attribute>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>