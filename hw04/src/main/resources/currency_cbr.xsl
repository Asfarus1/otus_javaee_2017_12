<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <xsl:for-each select="ValCurs/Valute[CharCode = 'USD' or CharCode = 'CNY' or CharCode = 'JPY']">
            <p><xsl:value-of select="NumCode" /><xsl:text> </xsl:text><xsl:value-of select="Name" />(<xsl:value-of select="CharCode" />) : <xsl:value-of select="Value" /> </p>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>