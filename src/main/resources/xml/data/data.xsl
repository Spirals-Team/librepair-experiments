
<xsl:output method="text" encoding="iso-8859-1"/>

<!--
  <xsl:template match="data">1
    <xsl:for-each select="table">2
      <xsl:for-each select="record">3
        <xsl:for-each select="field">4
          <xsl:value-of select="value"/>5
        </xsl:for-each>
      </xsl:for-each>
    </xsl:for-each>
  </xsl:template>
  -->
  
<xsl:template match="data">
 <xsl:for-each select="table"> 
  <xsl:for-each select="record"> 
    <xsl:apply-templates select="field"/>
    </xsl:for-each> 
  </xsl:for-each>
</xsl:template>

<xsl:template match="field">
  <xsl:copy-of select="value"/>
</xsl:template>

<xsl:template match="field">
  <xsl:copy-of select="."/>
</xsl:template>
 
</xsl:stylesheet>