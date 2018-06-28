<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:output omit-xml-declaration="yes" indent="yes"/>
  <xsl:strip-space elements="*"/>
  
	<xsl:template match="entries">
		<entries>
        <xsl:apply-templates/>
		</entries>
    </xsl:template>	
	
	<xsl:template match="//field">
		<xsl:element name="entry">
			<xsl:attribute name = "{name()}">
			<xsl:value-of select="normalize-space(.)"/>
			</xsl:attribute>
			</xsl:element>		
    </xsl:template>
</xsl:stylesheet>


 
 
