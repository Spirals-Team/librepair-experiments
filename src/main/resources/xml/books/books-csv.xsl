
<!-- Created by mpamer.me.uk on 19 May 2003, 17:59 -->


	<xsl:output method="text"/>

	<!-- This stylesheet outputs the book list as a CSV file -->

	<xsl:template match="DDM-DATA">
			<xsl:apply-templates select="RECORD"/>
	</xsl:template>

	<xsl:template match="TABLE">
	<xsl:for-each select="RECORD">
	<xsl:value-of select="TITLE"/>
	</xsl:for-each>
	</xsl:template>

	</xsl:stylesheet>