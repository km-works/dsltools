<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml"/>
	<!-- copy all nodes and attributes recursively ... -->
	<xsl:template match="@*|*">
		<xsl:copy>
			<xsl:apply-templates select="@*|*"/>
		</xsl:copy>
	</xsl:template>
	<!-- ... except the following nodes or attributes -->
	<xsl:template match="Multiple[@multiplicity='ZeroOrOne']">
		<xsl:choose>
			<xsl:when test="Choice">
				<xsl:apply-templates select="Choice" mode="convertEpsilon"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy>
					<xsl:apply-templates select="@*|*"/>
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- Helpers -->
	<xsl:template match="Choice" mode="convertEpsilon">
		<xsl:copy>
			<xsl:attribute name="epsilon">
				<xsl:value-of select="true()"/>
			</xsl:attribute>
			<xsl:apply-templates select="@*|*"/>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>
