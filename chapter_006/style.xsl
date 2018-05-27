<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/entries">
		<xsl:text>&#10;</xsl:text>
        <entries>
            <xsl:apply-templates/>
        </entries>
    </xsl:template>
    <xsl:template match="/entries/entry">
        <entry field = "{field}"/>
    </xsl:template>
</xsl:stylesheet>