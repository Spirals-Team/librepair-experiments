<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
         <body>
            <h1>Company Details</h1>
            <table border="1">
               <tr>
                  <th>Fecha</th>
                  <th>Descripcion</th>
                  <th>Observacion</th>
                  <th>Importe</th>
               </tr>
               <xsl:for-each select="list/com.economizate.entidades.MovimientoMonetario">
                  <tr>
                     <td>
                        <xsl:value-of select="substring(fecha,1,10)" />
                     </td>
                     <td>
                        <xsl:value-of select="descripcion" />
                     </td>
                     <td>
                        <xsl:value-of select="observacion" />
                     </td>
                     <td>
                        <xsl:value-of select="importe" />
                     </td>
                  </tr>
               </xsl:for-each>
            </table>
         </body>
      </html>
	</xsl:template>
</xsl:stylesheet>