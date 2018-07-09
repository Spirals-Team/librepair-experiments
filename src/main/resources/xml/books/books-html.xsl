
<!-- Created by mpamer.me.uk on 19 May 2003, 17:58 -->

<xsl:transform
	 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	 xmlns:saxon="http://icl.com/saxon"
	 exclude-result-prefixes="saxon"
	>

	<xsl:variable name="bgcolor" select="'x00ffff'"</xsl:variable>
	<xsl:variable name="fontcolor" select="'xff0080'"/>

	<xsl:template match="/">
		<HTML>
		<xsl:call-template name="header">
			<xsl:with-param name="title" select="'Book List'"/>
		</xsl:call-template>
		<BODY BGCOLOR="{$bgcolor}">
		<FONT COLOR="{$fontcolor}">
		<xsl:apply-templates/>
		</FONT>
		</BODY>
		</HTML>
	</xsl:template>

	<xsl:template name="header" xml:space="preserve">
		<xsl:param name="title" select="'Default Title'"/>
		<HEAD>
		<TITLE><xsl:value-of select="$title"/></TITLE></HEAD>
	</xsl:template>

	<xsl:template match="BOOKLIST">
	</xsl:template>   

		<div xsl:extension-element-prefixes="saxon">
		<xsl:sort select="TITLE" order="ascending"/>
			 (<xsl:value-of select="position()"/> of <xsl:value-of select="last()"/>)</h3>
			<TABLE>
			<saxon:item>
				<TR><TD WIDTH="100" VALIGN="TOP"><xsl:number format="i"/></TD>
					<TD>
					TITLE: <xsl:value-of select="TITLE"/><BR/>
					CATEGORY: <xsl:value-of select="id(@CAT)/@DESC" />
							(<xsl:value-of select="@CAT" />)
					</TD></TR>
			</saxon:item>
			</TABLE><HR/>
		</saxon:group>
		</div>
	</xsl:template>
	</xsl:transform>