<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : config2customfelds.xsl
    Created on : September 23, 2015, 10:15 AM
    Author     : Peter Withers <peter.withers@mpi.nl>
    Description:
        Converts the metadata segment of the configuration file into a class that can be stored when matching data is received from the remote app.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text" encoding="UTF-8" />
    <xsl:param name="targetClientDirectory" select="targetClientDirectory"/>
    <xsl:param name="targetTemplateDirectory" select="targetTemplateDirectory"/>
    <xsl:template match="/">
        <xsl:result-document href="{$targetClientDirectory}/model/Participant.java" method="text">
            <xsl:text>package nl.mpi.tg.eg.frinex.model;

                import java.io.Serializable;
                import java.util.Date;
                import javax.persistence.Entity;
                import javax.persistence.GeneratedValue;
                import javax.persistence.GenerationType;
                import javax.persistence.Id;
                import javax.persistence.Temporal;

                @Entity                     
                public class Participant implements Serializable, Comparable&lt;Participant&gt; {

                @Id
                @GeneratedValue(strategy = GenerationType.AUTO)
                private long id;

                @Temporal(javax.persistence.TemporalType.TIMESTAMP)
                private Date submitDate;
                private String userId;
                private String remoteAddr;
                private String acceptLang;
                private String userAgent;
                private Boolean staleCopy = false;
            </xsl:text>
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>
                    private String </xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>;</xsl:text>
            </xsl:for-each>
            <xsl:text>
                
                @Override
                public int compareTo(Participant o) {
                return (this.userId != null) ? this.userId.compareTo(o.getUserId()) : 1;
                }
                
                public long getId() {
                return id;
                }

                public boolean getStaleCopy() {
                return (staleCopy == null) ? false : staleCopy;
                }
                
                public String getUserId() {
                return userId;
                }
                
                public Date getSubmitDate() {
                return submitDate;
                }

                public void setSubmitDate(Date submitDate) {
                this.submitDate = submitDate;
                }

                public String getRemoteAddr() {
                return remoteAddr;
                }
                
                public void setRemoteAddr(String remoteAddr) {
                this.remoteAddr = remoteAddr;
                }

                public String getAcceptLang() {
                return acceptLang;
                }

                public void setAcceptLang(String acceptLang) {
                this.acceptLang = acceptLang;
                }

                public String getUserAgent() {
                return userAgent;
                }

                public void setUserAgent(String userAgent) {
                this.userAgent = userAgent;
                }
            </xsl:text>
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>
                    public String get</xsl:text>
                <xsl:value-of select="concat(upper-case(substring(@postName,1,1)), substring(@postName, 2))" />
                <xsl:text>() {
                    return </xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>;
                    }
                    public void set</xsl:text>
                <xsl:value-of select="concat(upper-case(substring(@postName,1,1)), substring(@postName, 2))" />
                <xsl:text>(String </xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>) {
                    this.</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text> = </xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>;
                    }
                </xsl:text>
            </xsl:for-each>
            <xsl:text>              
                }    </xsl:text>
        </xsl:result-document>
        <xsl:result-document href="{$targetClientDirectory}/model/ReportTypes.java" method="text">
            <xsl:text>package nl.mpi.tg.eg.frinex.model;

                public class ReportTypes {
                
                enum CsvReportTypes {
            </xsl:text>
            <xsl:for-each select="experiment//sendStimuliReport">
                <xsl:value-of select="generate-id(.)" />
                <xsl:text>("</xsl:text>
                <xsl:value-of select="@type" />
                <xsl:text>","</xsl:text>
                <xsl:value-of select="@headerKey" />
                <xsl:text>","</xsl:text>
                <xsl:value-of select="@separator" />
                <xsl:text>")
                </xsl:text>
                <xsl:if test="position() != last()">
                    <xsl:text>,</xsl:text> 
                </xsl:if>
                <xsl:if test="position() = last()">
                    <xsl:text>;</xsl:text> 
                </xsl:if>
            </xsl:for-each>
            <xsl:if test="experiment//sendStimuliReport">
                <xsl:text>
                    public final String type;
                    public final String separator;
                    public final String headerKey;

                    private CsvReportTypes(final String type, final String separator, final String headerKey) {
                    this.type = type;
                    this.separator = separator;
                    this.headerKey = headerKey;
                    }
                    static public CsvReportTypes getCsvReportType(final String eventTag) {
                    for (CsvReportTypes crt : values()) {
                    if (eventTag.equals(crt.type)) {
                    return crt;
                    }
                    }
                    return null;
                    }

                    public boolean isHeader(final String eventTag, final String tagValue1) {
                    return eventTag.equals(type) &amp;&amp; tagValue1.equals(headerKey);
                    }

                    public boolean isRow(final String eventTag, final String tagValue1) {
                    return eventTag.equals(type) &amp;&amp; !tagValue1.equals(headerKey);
                    }
                </xsl:text> 
            </xsl:if>
            <xsl:text>
                }
                enum ScreenReports {
            </xsl:text>
            <xsl:for-each select="experiment/presenter">
                <xsl:value-of select="@self" />
                <xsl:text>,
                </xsl:text>
            </xsl:for-each>
            <xsl:text>
                }
                enum LoadStimulusReports {
            </xsl:text>
            <xsl:for-each select="experiment/presenter[loadStimulus]">
                <xsl:value-of select="@self" />
                <xsl:text>,
                </xsl:text>
            </xsl:for-each>
            <xsl:text>
                }
                }
            </xsl:text>
        </xsl:result-document>
        <xsl:result-document href="{$targetClientDirectory}/util/ParticipantCsvExporter.java" method="text">
            <xsl:text>package nl.mpi.tg.eg.frinex.util;
                
                import java.io.IOException;
                import java.text.SimpleDateFormat;
                import java.util.List;
                import nl.mpi.tg.eg.frinex.model.Participant;
                import nl.mpi.tg.eg.frinex.model.TagData;
                import org.apache.commons.csv.CSVPrinter;
                
                public class ParticipantCsvExporter {
            </xsl:text>
            <!--            <xsl:if test="count(experiment/stimuli/stimulus) gt 200">
                <xsl:text>
                    public void appendAggregateCsvHeader(CSVPrinter printer) throws IOException {
                    printer.printRecord("Too many stimuli items (</xsl:text>
                <xsl:value-of select="count(experiment/stimuli/stimulus)"/>
                <xsl:text>)for this CSV export type to be produced. Please refer to the other CSV files provided.");
                    }
                    public void appendAggregateCsvRow(CSVPrinter printer, Participant participant, List&lt;TagData&gt; participantTagData) {
                    }</xsl:text>
            </xsl:if>-->
            <!--<xsl:if test="count(experiment/stimuli/stimulus) le 200">-->
            <!--<xsl:value-of select="count(experiment/stimuli/stimulus)"/>-->
            <xsl:text>
                public void appendAggregateCsvHeader(CSVPrinter printer) throws IOException {
                printer.printRecord("UserId"</xsl:text>
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>,"</xsl:text>
                <xsl:value-of select="concat(upper-case(substring(@postName,1,1)), substring(@postName, 2))" />
                <xsl:text>"</xsl:text>           
            </xsl:for-each>
            <xsl:for-each select="distinct-values(experiment/stimuli/stimulus/@id)">
                <xsl:text>,"</xsl:text>                
                <xsl:value-of select="." />                
                <xsl:text>","</xsl:text>  
                <xsl:value-of select="." /> 
                <xsl:text>_datetime",</xsl:text>
                <xsl:text>"</xsl:text>                
                <xsl:value-of select="." />                
                <xsl:text>_ms"</xsl:text>
                <!--                    <xsl:if test="position() != last()">
                    <xsl:text>,</xsl:text>
                </xsl:if>-->
            </xsl:for-each>
            <xsl:text>);
                }
                public void appendAggregateCsvRow(CSVPrinter printer, Participant participant, List&lt;TagData&gt; participantTagData) throws IOException, CsvExportException {
                SimpleDateFormat format = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss");
            </xsl:text>
            <xsl:for-each select="distinct-values(experiment/stimuli/stimulus/@id)">
                <xsl:text>String stimulus_</xsl:text>                
                <xsl:value-of select="." />                
                <xsl:text> = "";&#xa;</xsl:text>
                <xsl:text>String datetime_</xsl:text>                
                <xsl:value-of select="." />                
                <xsl:text> = "";&#xa;</xsl:text>
                <xsl:text>String ms_</xsl:text>                
                <xsl:value-of select="." />                
                <xsl:text> = "";&#xa;</xsl:text>
            </xsl:for-each>
            <xsl:text>
                TagData startData=null;
                for (TagData currentData : participantTagData) {
                if ("NextStimulus".equals(currentData.getEventTag())) {
                <!--                if(startData!=null){
                ms_</xsl:text>
            <xsl:value-of select="@code" />
            <xsl:text> += "no end event ";
                throw new CsvExportException("no end tag for for: " + startData.getEventTag() + " " + startData.getTagValue() + " " + startData.getUserId() + " " + startData.getTagDate());
                }-->
                startData=currentData;
                switch(startData.getTagValue()){
            </xsl:text>
            <xsl:for-each select="distinct-values(experiment/stimuli/stimulus/@id)">
                <xsl:text>case "</xsl:text>                
                <xsl:value-of select="." />                
                <xsl:text>":
                    datetime_</xsl:text>
                <xsl:value-of select="." />
                <xsl:text> += format.format(startData.getTagDate()) + " ";
                    break;
                </xsl:text>
            </xsl:for-each>    
            <xsl:text>
                default:
                throw new CsvExportException("no case for: " + startData.getEventTag() + " " + startData.getTagValue() + " " + startData.getUserId());
                }}
                if ("RatingButton".equals(currentData.getEventTag()) || "volgende [ spatiebalk ]".equals(currentData.getTagValue())) {
                TagData endData=currentData;
                String msString = (startData==null)?"no start event ":Integer.toString(endData.getEventMs()-startData.getEventMs());   
                if(startData!=null) //throw new CsvExportException("no start for: " + endData.getEventTag() + " " + endData.getTagValue() + " " + endData.getUserId() + " " + endData.getTagDate());
                switch(startData.getTagValue()){
            </xsl:text>
            <xsl:for-each select="distinct-values(experiment/stimuli/stimulus/@id)">
                <xsl:text>case "</xsl:text>                
                <xsl:value-of select="." />                
                <xsl:text>":
                    stimulus_</xsl:text>
                <xsl:value-of select="." />
                <xsl:text> += endData.getTagValue() + " ";
                    ms_</xsl:text>
                <xsl:value-of select="." />
                <xsl:text> += msString + " ";
                    break;
                </xsl:text>
            </xsl:for-each>    
            <xsl:text>
                default:
                throw new CsvExportException("no case for: " + endData.getEventTag() + " " + endData.getTagValue() + " " + endData.getUserId());
                }
                startData=null;
                }                
                }
                printer.printRecord(participant.getUserId()</xsl:text>
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>,&#xa;participant.get</xsl:text>
                <xsl:value-of select="concat(upper-case(substring(@postName,1,1)), substring(@postName, 2))" />
                <xsl:text>()</xsl:text>
            </xsl:for-each>
            <xsl:for-each select="distinct-values(experiment/stimuli/stimulus/@id)">
                <xsl:text>,&#xa;stimulus_</xsl:text>  
                <xsl:value-of select="." />                
                <xsl:text>, datetime_</xsl:text>                
                <xsl:value-of select="." />                
                <xsl:text>, ms_</xsl:text>                
                <xsl:value-of select="." />                
                <!--                    <xsl:if test="position() != last()">
                    <xsl:text>,&#xa;</xsl:text>
                </xsl:if>-->
            </xsl:for-each>
            <xsl:text>);
                }
            </xsl:text>
            <!--</xsl:if>-->
            <xsl:text>
                public void appendCsvHeader(CSVPrinter printer) throws IOException {
                printer.printRecord("UserId",</xsl:text>
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>"</xsl:text>
                <xsl:value-of select="concat(upper-case(substring(@postName,1,1)), substring(@postName, 2))" />
                <xsl:text>"</xsl:text>
                <xsl:if test="position() != last()">
                    <xsl:text>,</xsl:text>
                </xsl:if>
            </xsl:for-each>
            <xsl:text>);
                }
                public void appendCsvRow(CSVPrinter printer, Participant participant) throws IOException {
                printer.printRecord(participant.getUserId(),</xsl:text>
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>participant.get</xsl:text>
                <xsl:value-of select="concat(upper-case(substring(@postName,1,1)), substring(@postName, 2))" />
                <xsl:text>()</xsl:text>
                <xsl:if test="position() != last()">
                    <xsl:text>,</xsl:text>
                </xsl:if>
            </xsl:for-each>
            <xsl:text>);
                }
                }</xsl:text>
        </xsl:result-document>
        <xsl:result-document href="{$targetTemplateDirectory}/participanttable.html" method="text">
            <xsl:text>&lt;!DOCTYPE html&gt;
                &lt;html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"&gt;
                    &lt;head&gt;&lt;title&gt;&lt;/title&gt;&lt;/head&gt;
    &lt;body&gt;
        &lt;table&gt;
                    &lt;tr th:fragment="participantheader"&gt;
                    &lt;th th:if="${param.detailed}"&gt;&lt;a th:attr="href='?' + ${(param.detailed != null)? 'detailed' : 'simple'} + '&amp;amp;sort=id'"&gt;ID&lt;/a&gt;&lt;/th&gt;
                    &lt;th th:if="${param.detailed}"&gt;&lt;a th:attr="href='?' + ${(param.detailed != null)? 'detailed' : 'simple'} + '&amp;amp;sort=staleCopy'"&gt;Stale&lt;/a&gt;&lt;/th&gt;
                    &lt;th th:if="${param.detailed}"&gt;&lt;a th:attr="href='?' + ${(param.detailed != null)? 'detailed' : 'simple'} + '&amp;amp;sort=userId'"&gt;UUID&lt;/a&gt;&lt;/th&gt;
                    &lt;th th:if="${param.detailed}"&gt;&lt;a th:attr="href='?' + ${(param.detailed != null)? 'detailed' : 'simple'} + '&amp;amp;sort=userAgent'"&gt;User Agent&lt;/a&gt;&lt;/th&gt;
                    &lt;th th:if="${param.detailed}"&gt;&lt;a th:attr="href='?' + ${(param.detailed != null)? 'detailed' : 'simple'} + '&amp;amp;sort=acceptLang'"&gt;Browser Language&lt;/a&gt;&lt;/th&gt;
                <!--&lt;th&gt;&lt;a th:attr="href='?sort=remoteAddr'"&gt;remoteAddr&lt;/a&gt;&lt;/th&gt;-->
            </xsl:text>
            <!--&amp;${(sortOrder='a')? 'd' : 'a'}-->
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>&lt;th&gt;&lt;a th:attr="href='?' + ${(param.detailed != null)? 'detailed' : 'simple'} + '&amp;amp;sort=</xsl:text>
                <xsl:value-of select="replace(@postName,'_','\\_')" />
                <xsl:text>'"&gt;</xsl:text>
                <xsl:value-of select="@registrationField" />
                <xsl:text>&lt;/a&gt;&lt;/th&gt;
                </xsl:text>
            </xsl:for-each>
            <xsl:text>    
                &lt;th&gt;&lt;a th:attr="href='?' + ${(param.detailed != null)? 'detailed' : 'simple'} + '&amp;amp;sort=submitDate'"&gt;Date&lt;/a&gt;&lt;/th&gt;
                &lt;/tr&gt;
                    &lt;tr th:fragment="participantrows"&gt;
                    &lt;td th:if="${param.detailed}" th:text="${participant.id}"&gt;id&lt;/td&gt;
                    &lt;td th:if="${param.detailed}" th:text="${participant.staleCopy}"&gt;staleCopy&lt;/td&gt;
                    &lt;td th:if="${param.detailed}" th:text="${participant.userId}"&gt;userId&lt;/td&gt;
                    &lt;td th:if="${param.detailed}" th:text="${participant.userAgent}"&gt;userAgent&lt;/td&gt;
                    &lt;td th:if="${param.detailed}" th:text="${participant.acceptLang}"&gt;acceptLang&lt;/td&gt;
                <!--&lt;td th:text="${participant.remoteAddr}"&gt;remoteAddr&lt;/td&gt;-->
            </xsl:text>
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>
                    &lt;td  th:text="${participant.</xsl:text>
                <xsl:value-of select="replace(@postName,'__','\\_\\_')" />
                <xsl:text>}"&gt;&lt;/td&gt;</xsl:text>
            </xsl:for-each>
            <xsl:text>    
                &lt;td th:text="${participant.submitDate}"&gt;submitDate&lt;/td&gt;
                &lt;/tr&gt;
                &lt;tr th:fragment="participantinputfields"&gt;
                &lt;td th:if="${param.detailed}"&gt;&lt;/td&gt;
                &lt;td th:if="${param.detailed}"&gt;&lt;/td&gt;
                &lt;td th:if="${param.detailed}"&gt;&lt;/td&gt;
                &lt;td th:if="${param.detailed}"&gt;&lt;/td&gt;
            </xsl:text>
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>&lt;td&gt;&lt;input id="</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>Input" /&gt;&lt;/td&gt;</xsl:text>
            </xsl:for-each>
            <xsl:text>    
                &lt;td&gt;&lt;button id="addParticipantButton" class="tableButton" &gt;Add Participant&lt;/button&gt;&lt;/td&gt;&lt;/tr&gt;
                &lt;/table&gt;
            &lt;/body&gt;
        &lt;/html&gt;
            </xsl:text>
            <xsl:text>    
                    &lt;script th:fragment="addparticipantscript"&gt;
                $(document).ready(function () {
                $("#addParticipantButton").on('click', function () {
                $.ajax({
                url: 'metadata',
                type: "POST",
                dataType : "json",
                contentType: "application/json; charset=utf-8",
                data: "[{</xsl:text>
            <xsl:for-each select="experiment/metadata/field">
                <xsl:text>\"</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>\": \"" + $("#</xsl:text>
                <xsl:value-of select="@postName" />
                <xsl:text>Input").val() + "\"</xsl:text>
                <xsl:if test="position() != last()">
                    <xsl:text>,</xsl:text>
                </xsl:if>
            </xsl:for-each>
            <xsl:text>}]",
                success: function (result) {
                location.reload();
                },
                error: function (xhr, resp, text) {
                console.log(xhr, resp, text);
                }
                })
                });
                });

                    &lt;/script&gt;
            </xsl:text>
        </xsl:result-document>
    </xsl:template>
</xsl:stylesheet>
