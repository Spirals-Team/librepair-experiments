<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : config2stimuli-sh.xsl
    Created on : October 22, 2015, 15:21 PM
    Author     : Peter Withers <peter.withers@mpi.nl>
    Description:
        Purpose of this transformation is to create a bash script which generates some testing stimuli files.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text" encoding="UTF-8" />
    <xsl:template match="/">
        <xsl:text>for i in *.wav; do ffmpeg -i "$i" "${i%.wav}.mp3"; done; </xsl:text>
        <xsl:text>for i in *.wav; do ffmpeg -i "$i" "${i%.wav}.ogg"; done;&#xa;</xsl:text>
        <xsl:for-each select="experiment/stimuli/stimulus">
            <xsl:text>&#xa;</xsl:text>
            <xsl:text>#convert -gravity center -background blue -fill white -font /Library/Fonts/Tahoma.ttf -pointsize 80 label:"</xsl:text>
            <xsl:value-of select="replace(@label, '_', '\\n')" />
            <xsl:text>" "</xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>.jpg"</xsl:text>
            <xsl:text>&#xa;</xsl:text>
            <xsl:text>#say -v Alex "</xsl:text>
            <xsl:value-of select="@label" />
            <xsl:text>" -o </xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>.aiff</xsl:text>
            <xsl:text>&#xa;</xsl:text>
            <!-- create audio files in a directory based on the tag -->
            <xsl:text>mkdir </xsl:text>
            <xsl:value-of select="@label" />
            <xsl:text>&#xa;</xsl:text>
            <xsl:text>say -v Alex "</xsl:text>
            <xsl:value-of select="@label" />
            <xsl:text>" -o </xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>.aiff</xsl:text>
            <xsl:text>&#xa;</xsl:text>
            <xsl:text>ffmpeg -n -i "</xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>.aiff" -acodec libmp3lame "</xsl:text>
            <xsl:value-of select="@label" />
            <xsl:text>/</xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>.mp3"</xsl:text>
            <xsl:text>&#xa;</xsl:text>
            <!-- end audio files in a directory based on the tag -->
            <xsl:text>#ffmpeg -n -i "</xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>.aiff" -acodec libmp3lame "</xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>.mp3"</xsl:text>
            <xsl:text>&#xa;</xsl:text>
            <xsl:text>#ffmpeg -n -i "</xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>.aiff" -acodec libvorbis "</xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>.ogg"</xsl:text>
            <xsl:text>&#xa;</xsl:text>
            <xsl:text>#ffmpeg -n -loop 1 -i </xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>.jpg -filter:v fade=in:5:20:color=green -t 10 -vcodec mpeg4 </xsl:text>
            <xsl:value-of select="generate-id(.)" />
            <xsl:text>.mp4</xsl:text>
            <xsl:text>&#xa;</xsl:text>
        </xsl:for-each>
        <xsl:text>&#xa;</xsl:text>
        <xsl:text>&#xa;</xsl:text>
        <xsl:text>&#xa;</xsl:text>
        <xsl:for-each select="experiment/stimuli/stimulus[@image]">           
            <xsl:text>convert "</xsl:text>
            <xsl:value-of select="@image" />
            <!--<xsl:text>" -gravity center -background white -extent 800x800\&lt; </xsl:text>-->
            <xsl:text>" -gravity center -background white -resize 500x500 -extent 500x </xsl:text>
            <!--<xsl:value-of select="generate-id(.)" />-->
            <xsl:value-of select="@code" />
            <xsl:text>.jpg</xsl:text>
            <xsl:text>&#xa;</xsl:text>
        </xsl:for-each>
        <xsl:for-each select="experiment/stimuli/stimulus[@audioPath]">           
            <xsl:text>ffmpeg -i "</xsl:text>
            <xsl:value-of select="@audioPath" />
            <xsl:text>" "</xsl:text>
            <xsl:value-of select="substring-before(@audioPath,'.wav')" /> 
            <xsl:text>.mp3"</xsl:text>
            <xsl:text>&#xa;</xsl:text>
        </xsl:for-each>
        <xsl:for-each select="experiment/stimuli/stimulus[@videoPath]">           
            <xsl:text>ffmpeg -i "</xsl:text>
            <xsl:value-of select="@videoPath" />
            <xsl:text>" "</xsl:text>
            <xsl:value-of select="substring-before(@videoPath,'.mpg')" /> 
            <xsl:text>.mp4"</xsl:text>
            <xsl:text>&#xa;</xsl:text>
            <xsl:text>ffmpeg -i "</xsl:text>
            <xsl:value-of select="@videoPath" />
            <xsl:text>" "</xsl:text>
            <xsl:value-of select="substring-before(@videoPath,'.mpg')" /> 
            <xsl:text>.ogg"</xsl:text>
            <xsl:text>&#xa;</xsl:text>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
