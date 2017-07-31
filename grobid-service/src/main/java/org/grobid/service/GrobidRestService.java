/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.grobid.service;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.grobid.core.factory.AbstractEngineFactory;
import org.grobid.core.utilities.GrobidProperties;
import org.grobid.service.process.GrobidRestProcessAdmin;
import org.grobid.service.process.GrobidRestProcessFiles;
import org.grobid.service.process.GrobidRestProcessGeneric;
import org.grobid.service.process.GrobidRestProcessString;
import org.grobid.service.util.GrobidRestUtils;
import org.grobid.service.util.GrobidServiceProperties;
import org.grobid.service.util.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * RESTful service for the GROBID system.
 *
 * @author FloZi, Damien, Patrice
 *
 */

@Singleton
@Path(GrobidPaths.PATH_GROBID)
public class GrobidRestService implements GrobidPaths {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrobidRestService.class);

    private static final String NAMES = "names";
    private static final String DATE = "date";
    private static final String AFFILIATIONS = "affiliations";
    private static final String CITATION = "citations";
    private static final String TEXT = "text";
    private static final String SHA1 = "sha1";
    private static final String XML = "xml";
    private static final String INPUT = "input";

    private final GrobidRestProcessAdmin restProcessAdmin;
    private GrobidRestProcessFiles restProcessFiles;
    private GrobidRestProcessGeneric restProcessGeneric;
    private GrobidRestProcessString restProcessString;

    @Inject
    public GrobidRestService(GrobidServiceConfiguration configuration, GrobidRestProcessAdmin grobidRestProcessAdmin) {
        this.restProcessAdmin = grobidRestProcessAdmin;
        GrobidProperties.set_GROBID_HOME_PATH(new File(configuration.getGrobid().getGrobidHome()).getAbsolutePath());
        if (configuration.getGrobid().getGrobidProperties() != null) {
            GrobidProperties.setGrobidPropertiesPath(new File(configuration.getGrobid().getGrobidProperties()).getAbsolutePath());
        } else {
            GrobidProperties.setGrobidPropertiesPath(new File(configuration.getGrobid().getGrobidHome(), "/config/grobid.properties").getAbsolutePath());
        }

        LOGGER.info("Initiating Servlet GrobidRestService");
        AbstractEngineFactory.fullInit();
        GrobidServiceProperties.getInstance(configuration);
        LOGGER.info("Initiating of Servlet GrobidRestService finished.");
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessGeneric#isAlive()
     */
    @Path(GrobidPaths.PATH_IS_ALIVE)
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public Response isAlive() {
        return restProcessGeneric.isAlive();
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessGeneric#getVersion()
     */
    @Path(GrobidPaths.PATH_GET_VERSION)
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public Response getVersion() {
        return restProcessGeneric.getVersion();
    }

    /**
     *
     * @see org.grobid.service.process.GrobidRestProcessGeneric#getDescription_html(UriInfo)
     */
    @Produces(MediaType.TEXT_HTML)
    @GET
    @Path("grobid")
    public Response getDescription_html(@Context UriInfo uriInfo) {
        return restProcessGeneric.getDescription_html(uriInfo);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessAdmin#getAdminParams(String)
     */
    @Path(PATH_ADMIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @POST
    public Response getAdmin_htmlPost(@FormParam(SHA1) String sha1) {
        return restProcessAdmin.getAdminParams(sha1);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessAdmin#getAdminParams(String)
     */
    @Path(PATH_ADMIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_HTML)
    @GET
    public Response getAdmin_htmlGet(@QueryParam(SHA1) String sha1) {
        return restProcessAdmin.getAdminParams(sha1);
    }

    @Path(PATH_HEADER)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @POST
    public Response processHeaderDocument_post(@FormDataParam(INPUT) InputStream inputStream,
                                               @FormDataParam("consolidate") String consolidate
    ) throws Exception {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessFiles.processStatelessHeaderDocument(inputStream, consol, false);
    }

    @Path(PATH_HEADER)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @PUT
    public Response processStatelessHeaderDocument(@FormDataParam(INPUT) InputStream inputStream,
                                                   @FormDataParam("consolidate") String consolidate
    ) throws IOException, SAXException {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessFiles.processStatelessHeaderDocument(inputStream, consol, false);
    }

    @Path(PATH_HEADER_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @POST
    public Response processHeaderDocument_postHTML(@FormDataParam(INPUT) InputStream inputStream,
                                                   @FormDataParam("consolidate") String consolidate) throws IOException, SAXException {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessFiles.processStatelessHeaderDocument(inputStream, consol, true);
    }

    @Path(PATH_HEADER_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @PUT
    public Response processStatelessHeaderDocumentHTML(@FormDataParam(INPUT) InputStream inputStream,
                                                       @FormDataParam("consolidate") String consolidate) throws IOException, SAXException {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessFiles.processStatelessHeaderDocument(inputStream, consol, true);
    }

    @Path(PATH_FULL_TEXT)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @POST
    public Response processFulltextDocument_post(@FormDataParam(INPUT) InputStream inputStream,
                                                 @FormDataParam("consolidate") String consolidate,
                                                 @DefaultValue("-1") @FormDataParam("start") int startPage,
                                                 @DefaultValue("-1") @FormDataParam("end") int endPage,
                                                 @FormDataParam("generateIDs") String generateIDs) throws Exception {
        return processSatelessFulltextHelper(inputStream, consolidate, startPage, endPage, generateIDs);
    }

    @Path(PATH_FULL_TEXT)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @PUT
    public Response processStatelessFulltextDocument(@FormDataParam(INPUT) InputStream inputStream,
                                                     @FormDataParam("consolidate") String consolidate,
                                                     @DefaultValue("-1") @FormDataParam("start") int startPage,
                                                     @DefaultValue("-1") @FormDataParam("end") int endPage,
                                                     @FormDataParam("generateIDs") String generateIDs) throws Exception {
        return processSatelessFulltextHelper(inputStream, consolidate, startPage, endPage, generateIDs);
    }

    private Response processSatelessFulltextHelper(InputStream inputStream,
                                                   String consolidate,
                                                   int startPage,
                                                   int endPage,
                                                   String generateIDs) throws Exception {
        boolean consol = false;
        boolean generate = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        if ((generateIDs != null) && (generateIDs.equals("1"))) {
            generate = true;
        }
        return restProcessFiles.processStatelessFulltextDocument(inputStream,
                consol, false, startPage, endPage, generate);
    }

    @Path(PATH_FULL_TEXT_ASSET)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/zip")
    @POST
    public Response processFulltextAssetDocument_post(@FormDataParam(INPUT) InputStream inputStream,
                                                      @FormDataParam("consolidate") String consolidate,
                                                      @DefaultValue("-1") @FormDataParam("start") int startPage,
                                                      @DefaultValue("-1") @FormDataParam("end") int endPage,
                                                      @FormDataParam("generateIDs") String generateIDs) throws Exception {
        return processStatelessFulltextAssetHelper(inputStream, consolidate, startPage, endPage, generateIDs);
    }

    @Path(PATH_FULL_TEXT_ASSET)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/zip")
    @PUT
    public Response processStatelessFulltextAssetDocument(@FormDataParam(INPUT) InputStream inputStream,
                                                          @FormDataParam("consolidate") String consolidate,
                                                          @DefaultValue("-1") @FormDataParam("start") int startPage,
                                                          @DefaultValue("-1") @FormDataParam("end") int endPage,
                                                          @FormDataParam("generateIDs") String generateIDs) throws Exception {
        return processStatelessFulltextAsset(inputStream, consolidate, startPage, endPage, generateIDs);
    }

    private Response processStatelessFulltextAsset(@FormDataParam(INPUT) InputStream inputStream, @FormDataParam("consolidate") String consolidate, @DefaultValue("-1") @FormDataParam("start") int startPage, @DefaultValue("-1") @FormDataParam("end") int endPage, @FormDataParam("generateIDs") String generateIDs) throws Exception {
        return processStatelessFulltextAssetHelper(inputStream, consolidate, startPage, endPage, generateIDs);
    }

    private Response processStatelessFulltextAssetHelper(InputStream inputStream,
                                                         String consolidate,
                                                         int startPage,
                                                         int endPage,
                                                         String generateIDs) throws Exception {
        boolean consol = false;
        boolean generate = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        if ((generateIDs != null) && (generateIDs.equals("1"))) {
            generate = true;
        }
        return restProcessFiles.processStatelessFulltextAssetDocument(inputStream,
                consol, startPage, endPage, generate);
    }


    @Path(PATH_FULL_TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @POST
    public Response processFulltextDocument_postHTML(@FormDataParam(INPUT) InputStream inputStream,
                                                     @FormDataParam("consolidate") String consolidate,
                                                     @DefaultValue("-1") @FormDataParam("start") int startPage,
                                                     @DefaultValue("-1") @FormDataParam("end") int endPage,
                                                     @FormDataParam("generateIDs") String generateIDs) throws Exception {
        boolean consol = false;
        boolean generate = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        if ((generateIDs != null) && (generateIDs.equals("1"))) {
            generate = true;
        }
        return restProcessFiles.processStatelessFulltextDocument(inputStream,
                consol, true, startPage, endPage, generate);
    }

    @Path(PATH_FULL_TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @PUT
    public Response processStatelessFulltextDocumentHTML(@FormDataParam(INPUT) InputStream inputStream,
                                                         @FormDataParam("consolidate") String consolidate,
                                                         @DefaultValue("-1") @FormDataParam("start") int startPage,
                                                         @DefaultValue("-1") @FormDataParam("end") int endPage,
                                                         @FormDataParam("generateIDs") String generateIDs) throws Exception {
        boolean consol = false;
        boolean generate = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        if ((generateIDs != null) && (generateIDs.equals("1"))) {
            generate = true;
        }
        return restProcessFiles.processStatelessFulltextDocument(inputStream,
                consol, true, startPage, endPage, generate);
    }

    @Path(PATH_CITATION_PATENT_TEI)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @POST
    public StreamingOutput processCitationPatentTEI(@FormDataParam(INPUT) InputStream pInputStream,
                                                    @FormDataParam("consolidate") String consolidate) throws Exception {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessFiles.processCitationPatentTEI(pInputStream, consol);
    }

    @Path(PATH_CITATION_PATENT_ST36)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @POST
    public Response processCitationPatentST36(@FormDataParam(INPUT) InputStream pInputStream,
                                              @FormDataParam("consolidate") String consolidate) throws Exception {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }

        pInputStream = ZipUtils.decompressStream(pInputStream);

        return restProcessFiles.processCitationPatentST36(pInputStream, consol);
    }

    @Path(PATH_CITATION_PATENT_PDF)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @POST
    public Response processCitationPatentPDF(@FormDataParam(INPUT) InputStream pInputStream,
                                             @FormDataParam("consolidate") String consolidate) throws Exception {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessFiles.processCitationPatentPDF(pInputStream, consol);
    }

    @Path(PATH_CITATION_PATENT_TXT)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_XML)
    @POST
    public Response processCitationPatentTXT_post(@FormParam(TEXT) String text,
                                                  @FormParam("consolidate") String consolidate) {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessString.processCitationPatentTXT(text, consol);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessString#processDate(String)
     */
    @Path(PATH_DATE)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response processDate_post(@FormParam(DATE) String date) {
        return restProcessString.processDate(date);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessString#processDate(String)
     */
    @Path(PATH_DATE)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @PUT
    public Response processDate(@FormParam(DATE) String date) {
        return restProcessString.processDate(date);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessString#processNamesHeader(String)
     */
    @Path(PATH_HEADER_NAMES)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response processNamesHeader_post(@FormParam(NAMES) String names) {
        return restProcessString.processNamesHeader(names);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessString#processNamesHeader(String)
     */
    @Path(PATH_HEADER_NAMES)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @PUT
    public Response processNamesHeader(@FormParam(NAMES) String names) {
        return restProcessString.processNamesHeader(names);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessString#processNamesCitation(String)
     */
    @Path(PATH_CITE_NAMES)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response processNamesCitation_post(@FormParam(NAMES) String names) {
        return restProcessString.processNamesCitation(names);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessString#processNamesCitation(String)
     */
    @Path(PATH_CITE_NAMES)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @PUT
    public Response processNamesCitation(@FormParam(NAMES) String names) {
        return restProcessString.processNamesCitation(names);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessString#processAffiliations(String)
     */
    @Path(PATH_AFFILIATION)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response processAffiliations_post(@FormParam(AFFILIATIONS) String affiliations) {
        return restProcessString.processAffiliations(affiliations);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessString#processAffiliations(String)
     */
    @Path(PATH_AFFILIATION)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @PUT
    public Response processAffiliations(@FormParam(AFFILIATIONS) String affiliation) {
        return restProcessString.processAffiliations(affiliation);
    }

    @Path(PATH_CITATION)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_XML)
    @POST
    public Response processCitation_post(@FormParam(CITATION) String citation,
                                         @FormParam("consolidate") String consolidate) {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessString.processCitation(citation, consol);
    }

    @Path(PATH_CITATION)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_XML)
    @PUT
    public Response processCitation(@FormParam(CITATION) String citation,
                                    @FormParam("consolidate") String consolidate) {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessString.processCitation(citation, consol);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessAdmin#processSHA1(String)
     */
    @Path(PATH_SHA1)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response processSHA1Post(@FormParam(SHA1) String sha1) {
        return restProcessAdmin.processSHA1(sha1);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessAdmin#processSHA1(String)
     */
    @Path(PATH_SHA1)
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public Response processSHA1Get(@QueryParam(SHA1) String sha1) {
        return restProcessAdmin.processSHA1(sha1);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessAdmin#getAllPropertiesValues(String)
     */
    @Path(PATH_ALL_PROPS)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response getAllPropertiesValuesPost(@FormParam(SHA1) String sha1) {
        return restProcessAdmin.getAllPropertiesValues(sha1);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessAdmin#getAllPropertiesValues(String)
     */
    @Path(PATH_ALL_PROPS)
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public Response getAllPropertiesValuesGet(@QueryParam(SHA1) String sha1) {
        return restProcessAdmin.getAllPropertiesValues(sha1);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessAdmin#changePropertyValue(String)
     */
    @Path(PATH_CHANGE_PROPERTY_VALUE)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response changePropertyValuePost(@FormParam(XML) String xml) throws ParserConfigurationException, SAXException, IOException {
        return restProcessAdmin.changePropertyValue(xml);
    }

    /**
     * @see org.grobid.service.process.GrobidRestProcessAdmin#changePropertyValue(String)
     */
    @Path(PATH_CHANGE_PROPERTY_VALUE)
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public Response changePropertyValueGet(@QueryParam(XML) String xml) throws ParserConfigurationException, SAXException, IOException {
        return restProcessAdmin.changePropertyValue(xml);
    }

    @Path(PATH_REFERENCES)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @POST
    public Response processReferencesDocument_post(@FormDataParam(INPUT) InputStream inputStream,
                                                   @FormDataParam("consolidate") String consolidate) {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessFiles.processStatelessReferencesDocument(inputStream, consol);
    }

    @Path(PATH_REFERENCES)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_XML)
    @PUT
    public Response processStatelessReferencesDocument(@FormDataParam(INPUT) InputStream inputStream,
                                                       @FormDataParam("consolidate") String consolidate) {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessFiles.processStatelessReferencesDocument(inputStream, consol);
    }

    @Path(PATH_PDF_ANNOTATION)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/pdf")
    @POST
    public Response processAnnotatePDF(@FormDataParam(INPUT) InputStream inputStream,
                                       @FormDataParam("name") String fileName,
                                       @FormDataParam("type") int type) throws Exception {
        return restProcessFiles.processPDFAnnotation(inputStream, fileName, GrobidRestUtils.getAnnotationFor(type));
    }

    @Path(PATH_REFERENCES_PDF_ANNOTATION)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    @POST
    public Response processPDFReferenceAnnotation(@FormDataParam(INPUT) InputStream inputStream) throws Exception {
        return restProcessFiles.processPDFReferenceAnnotation(inputStream);
    }

    /**
     */
    @Path(PATH_CITATIONS_PATENT_PDF_ANNOTATION)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    @POST
    public Response annotatePDFPatentCitation(@FormDataParam(INPUT) InputStream inputStream,
                                              @FormDataParam("consolidate") String consolidate) throws Exception {
        boolean consol = false;
        if ((consolidate != null) && (consolidate.equals("1"))) {
            consol = true;
        }
        return restProcessFiles.annotateCitationPatentPDF(inputStream, consol);
    }

    public void setRestProcessFiles(GrobidRestProcessFiles restProcessFiles) {
        this.restProcessFiles = restProcessFiles;
    }

    public void setRestProcessGeneric(GrobidRestProcessGeneric restProcessGeneric) {
        this.restProcessGeneric = restProcessGeneric;
    }

    public void setRestProcessString(GrobidRestProcessString restProcessString) {
        this.restProcessString = restProcessString;
    }
}
