<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tree="ddm"

  <xsl:template match="ddm-tree">
  1
    <xsl:for-each select="ddm-node">
    2
      <xsl:attribute name="type">
       3
        <xsl:choose>
         <xsl:when test="@focus='menu-node'">
          <li>menu-node</li>
         </xsl:when>
         <xsl:when test="@focus='cod-node'">
          <li>cod-node</li>
         </xsl:when>       
         <xsl:when test="@focus='menu-leaf'">
          <li>menu-leaf</li>
         </xsl:when>
         <xsl:when test="@focus='managed-menu-leaf'">
          <li>managed-menu-leaf</li>
         </xsl:when>       
         <xsl:when test="@focus='cod-leaf'">
          <li>cod-leaf</li>
         </xsl:when>       
         <xsl:otherwise>
          <li>type not defined</li>
         </xsl:otherwise>
        </xsl:choose>
       </xsl:attribute>
     </xsl:for-each>

  </xsl:template>
</xsl:stylesheet>