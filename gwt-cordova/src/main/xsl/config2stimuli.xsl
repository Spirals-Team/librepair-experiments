<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : config2stimuli.xsl
    Created on : September 24, 2015, 5:21 PM
    Author     : Peter Withers <peter.withers@mpi.nl>
    Description:
        Purpose of transformation is to create the stimuli class in the model package for the current experiment.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text" encoding="UTF-8" />
    <xsl:param name="targetClientDirectory" select="targetClientDirectory"/>
    <xsl:template match="/">
        <xsl:text>package nl.mpi.tg.eg.experiment.client.model;
            
            import com.google.gwt.core.client.GWT;
            import java.util.Arrays;
            import java.util.List;
            import nl.mpi.tg.eg.experiment.client.ServiceLocations;
            import nl.mpi.tg.eg.experiment.client.util.GeneratedStimulusProvider;
            import nl.mpi.tg.eg.frinex.common.model.AbstractStimulus;
            import nl.mpi.tg.eg.frinex.common.model.Stimulus;

            public class GeneratedStimulus extends </xsl:text>
        <xsl:value-of select="if(experiment/stimuli[@class]) then experiment/stimuli/@class else 'AbstractStimulus'" />
        <xsl:text> {
            protected final ServiceLocations serviceLocations = GWT.create(ServiceLocations.class);
        </xsl:text>    
            
        <xsl:result-document href="{$targetClientDirectory}/model/GeneratedStimulusStrings.java" method="text">
            package nl.mpi.tg.eg.experiment.client.model;
            public class GeneratedStimulusStrings {
            <xsl:for-each select="experiment/stimuli/stimulus">
                <xsl:text>
                    public static final String label_</xsl:text>
                <xsl:value-of select="generate-id(.)" />
                <xsl:text> = "</xsl:text>
                <xsl:value-of select="@label" />
                <xsl:text>";
                    public static final String code_</xsl:text>
                <xsl:value-of select="generate-id(.)" />
                <xsl:text> = "</xsl:text>
                <xsl:value-of select="@code" />
                <xsl:text>";
                </xsl:text>
            </xsl:for-each>
            <xsl:text>}</xsl:text>
        </xsl:result-document>        
        <xsl:variable name="parameter" select="tokenize(experiment/stimuli/@parameters, ' ')"/>
        <xsl:result-document href="{$targetClientDirectory}/util/GeneratedStimulusProvider.java" method="text">
            <xsl:text>
                package nl.mpi.tg.eg.experiment.client.util;
                import nl.mpi.tg.eg.experiment.client.model.GeneratedStimulus;
                import nl.mpi.tg.eg.experiment.client.model.GeneratedStimulus.Tag;
                import nl.mpi.tg.eg.experiment.client.util.GeneratedStimulus.*;
                public class GeneratedStimulusProvider {
                private static GeneratedStimulus G(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses, String... parameters) {
                return new GeneratedStimulus(uniqueId, tags, label, code, pauseMs, audioPath, videoPath, imagePath, ratingLabels, correctResponses);
                }
                private static Tag[] T(Tag... tagArray ){
                return tagArray;
                }
                static final private String N = null;
                public static final GeneratedStimulus[] values = new GeneratedStimulus[]{</xsl:text>
            <xsl:for-each select="experiment/stimuli/stimulus">
                <xsl:result-document href="{$targetClientDirectory}/util/GeneratedStimulus/S_{generate-id(.)}.java" method="text">
                 <xsl:text>package nl.mpi.tg.eg.experiment.client.util.GeneratedStimulus;
                import nl.mpi.tg.eg.experiment.client.model.GeneratedStimulus;
                import nl.mpi.tg.eg.experiment.client.model.GeneratedStimulus.Tag;
                import static nl.mpi.tg.eg.experiment.client.model.GeneratedStimulus.Tag.*;
                import static nl.mpi.tg.eg.experiment.client.model.GeneratedStimulusStrings.*;
    public class S_</xsl:text>
                <xsl:value-of select="generate-id(.)" />
                <xsl:text> extends GeneratedStimulus {
                    public S_</xsl:text>
                <xsl:value-of select="generate-id(.)" />
                <xsl:text>(){
                super(</xsl:text>
                <!--<xsl:value-of select="generate-id(.)" />
                generate-id(.) caused issues with the node ID changing and pointing to the wrong file. It might be better at some point to use an explicit identifier value but for now we are using the 'code'.
                -->
                <xsl:value-of select="if(@identifier) then concat('&quot;', @identifier, '&quot;, ') else concat('&quot;', generate-id(.), '&quot;, ')" />
                <xsl:text>new Tag[]{</xsl:text>
                <xsl:for-each select="distinct-values(tokenize(@tags, ' '))">
                    <xsl:text>tag_</xsl:text>
                    <xsl:value-of select="." />
                    <xsl:if test="position() != last()">
                        <xsl:text>, </xsl:text>
                    </xsl:if>
                </xsl:for-each>
                <xsl:text>}, label_</xsl:text>
                <xsl:value-of select="generate-id(.)" />
                <!--<xsl:value-of select="@label" />-->
                <xsl:text>, code_</xsl:text>
                <xsl:value-of select="generate-id(.)" />
                <xsl:text>, </xsl:text>
                <xsl:value-of select="@pauseMs" />
                <!--<xsl:if test="@audioPath or @videoPath or @ogg or @imagePath">-->
                <xsl:text>, </xsl:text>
                <xsl:value-of select="if(@audioPath) then concat('&quot;', @audioPath, '&quot;') else 'null'" />
                <xsl:text>, </xsl:text>
                <xsl:value-of select="if(@videoPath) then concat('&quot;', @videoPath, '&quot;') else 'null'" />
                <xsl:text>, </xsl:text>
                <xsl:value-of select="if(@imagePath) then concat('&quot;', @imagePath, '&quot;') else 'null'" />
                <!--</xsl:if>-->
                <xsl:value-of select="if(@ratingLabels) then concat(',&quot;', @ratingLabels, '&quot;') else ',null'" />
                <xsl:value-of select="if(@correctResponses) then concat(',&quot;', @correctResponses, '&quot;') else ',null'" />
                <xsl:variable name="stimuliElement" select="."/>
                <xsl:for-each select="$parameter">
                    <xsl:variable name="parameterName" select="."/>
                    <xsl:variable name="parameterValue" select="$stimuliElement/@*[name() = $parameterName]"/>
                    <xsl:text>,</xsl:text>
                    <xsl:value-of select="if($parameterValue) then concat('&quot;', $parameterValue, '&quot;') else 'null'" />
                </xsl:for-each>
                <xsl:text>);
                    }
                }</xsl:text>
                </xsl:result-document>
                <xsl:text>new S_</xsl:text>
                <xsl:value-of select="generate-id(.)" />
                <xsl:text>()</xsl:text>
<!--                <xsl:text>G(</xsl:text>
                <xsl:value-of select="generate-id(.)" />
                generate-id(.) caused issues with the node ID changing and pointing to the wrong file. It might be better at some point to use an explicit identifier value but for now we are using the 'code'.
                
                <xsl:value-of select="if(@identifier) then concat('&quot;', @identifier, '&quot;, ') else concat('&quot;', generate-id(.), '&quot;, ')" />
                <xsl:text>T(</xsl:text>
                <xsl:for-each select="distinct-values(tokenize(@tags, ' '))">
                    <xsl:text>tag_</xsl:text>
                    <xsl:value-of select="." />
                    <xsl:if test="position() != last()">
                        <xsl:text>, </xsl:text>
                    </xsl:if>
                </xsl:for-each>
                <xsl:text>), label_</xsl:text>
                <xsl:value-of select="generate-id(.)" />
                <xsl:value-of select="@label" />
                <xsl:text>, code_</xsl:text>
                <xsl:value-of select="generate-id(.)" />
                <xsl:text>, </xsl:text>
                <xsl:value-of select="@pauseMs" />
                <xsl:if test="@audioPath or @videoPath or @ogg or @imagePath">
                <xsl:text>, </xsl:text>
                <xsl:value-of select="if(@audioPath) then concat('&quot;', @audioPath, '&quot;') else 'N'" />
                <xsl:text>, </xsl:text>
                <xsl:value-of select="if(@videoPath) then concat('&quot;', @videoPath, '&quot;') else 'N'" />
                <xsl:text>, </xsl:text>
                <xsl:value-of select="if(@imagePath) then concat('&quot;', @imagePath, '&quot;') else 'N'" />
                </xsl:if>
                <xsl:value-of select="if(@ratingLabels) then concat(',&quot;', @ratingLabels, '&quot;') else ',N'" />
                <xsl:value-of select="if(@correctResponses) then concat(',&quot;', @correctResponses, '&quot;') else ',N'" />
                <xsl:variable name="stimuliElement" select="."/>
                <xsl:for-each select="$parameter">
                    <xsl:variable name="parameterName" select="."/>
                    <xsl:variable name="parameterValue" select="$stimuliElement/@*[name() = $parameterName]"/>
                    <xsl:text>,</xsl:text>
                    <xsl:value-of select="if($parameterValue) then concat('&quot;', $parameterValue, '&quot;') else 'N'" />
                </xsl:for-each>
                <xsl:text>)</xsl:text>-->
                <xsl:if test="position() != last()">
                    <xsl:text>,&#10;</xsl:text>
                    <!--                    <xsl:if test="(position() mod 100) = 99">
                        <xsl:text>},{</xsl:text>
                    </xsl:if>-->
                </xsl:if>
                <xsl:if test="position() = last()">
                </xsl:if>
            </xsl:for-each>
            <xsl:text>};</xsl:text>
            <xsl:text>
                }
            </xsl:text>
        </xsl:result-document>
        <xsl:text>
     
            public enum Tag implements nl.mpi.tg.eg.frinex.common.model.Stimulus.Tag {

        </xsl:text>
        <xsl:for-each select="distinct-values(tokenize(string-join(experiment/stimuli/stimulus/@tags, ' '), ' '))">
            <xsl:text>tag_</xsl:text>
            <xsl:value-of select="." />
            <xsl:if test="position() != last()">
                <xsl:text>, </xsl:text>
            </xsl:if>
        </xsl:for-each>
        <xsl:text>
            }

        </xsl:text>
        <xsl:for-each select="$parameter">
            <xsl:text>private final String </xsl:text>
            <xsl:value-of select="." />
            <xsl:text>;</xsl:text>
        </xsl:for-each>
        <xsl:text>
            
            public static final void fillStimulusList(List&lt;Stimulus&gt; stimulusArray) {
            stimulusArray.addAll(Arrays.asList(GeneratedStimulusProvider.values));</xsl:text>
        <xsl:text>
            }
            
            public GeneratedStimulus(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses, String ... parameters) {
            super(uniqueId, tags, label, code, pauseMs, audioPath, videoPath, imagePath, ratingLabels, correctResponses);
        </xsl:text>
        <xsl:for-each select="$parameter">
            <xsl:text></xsl:text>
            <xsl:value-of select="." />
            <xsl:text>= parameters[</xsl:text>
            <xsl:value-of select="position()" />                
            <xsl:text>-1];</xsl:text>
        </xsl:for-each>
        <xsl:text>
            }

            /*public GeneratedStimulus(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String ratingLabels, String correctResponses) {
            super(uniqueId, tags, label, code, pauseMs, ratingLabels, correctResponses);
            }*/
            
            @Override
            public boolean isCorrect(String value) {
            return (value != null) ? value.matches(getCorrectResponses()) : false;
            }
            
            @Override
            public String getAudio() {
            return serviceLocations.staticFilesUrl() + super.getAudio();
            }

            @Override
            public String getImage() {
            return serviceLocations.staticFilesUrl() + super.getImage();
            }

            @Override
            public String getVideo() {
            return serviceLocations.staticFilesUrl() + super.getVideo();
            }            
        </xsl:text>
        <xsl:for-each select="$parameter">
            <xsl:text>@Override
                public String get</xsl:text>
            <xsl:value-of select="." />
            <xsl:text>() {
                return </xsl:text>
            <xsl:value-of select="." />           
            <xsl:text>;
                }
            </xsl:text>
        </xsl:for-each>
        <xsl:text>           
            }   
        </xsl:text>
    </xsl:template>
</xsl:stylesheet>
