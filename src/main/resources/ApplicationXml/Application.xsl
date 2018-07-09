<?xml version="1.0" encoding="UTF-8"?>

<!-- Document : Application.xsl Created on : 07 January 2014, 14:16 Author 
	: Description: Purpose of transformation follows. -->

<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" />
	<xsl:strip-space elements="wicast:Application" />

	<xsl:template match="/">
		<html>
			<head>
				<title>
					<xsl:value-of select="/wicast:Application/Name" />
				</title>
			</head>
			<body>
				<xsl:value-of select="/wicast:Application/Description" />
				<xsl:apply-templates />
			</body>
		</html>
	</xsl:template>

	<xsl:template match="/Application/Name">
		<h1 align="center">
			<xsl:apply-templates />
		</h1>
	</xsl:template>

</xsl:stylesheet>
