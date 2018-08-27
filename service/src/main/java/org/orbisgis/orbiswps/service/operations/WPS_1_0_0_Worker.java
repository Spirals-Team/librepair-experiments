/*
 * OrbisWPS contains a set of libraries to build a Web Processing Service (WPS)
 * compliant with the 2.0 specification.
 *
 * OrbisWPS is part of the OrbisGIS platform
 *
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 *
 * OrbisWPS is distributed under GPL 3 license.
 *
 * Copyright (C) 2015-2018 CNRS (Lab-STICC UMR CNRS 6285)
 *
 *
 * OrbisWPS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisWPS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisWPS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.orbiswps.service.operations;

import org.locationtech.jts.geom.Geometry;
import net.opengis.ows._1.BoundingBoxType;
import net.opengis.ows._1.ExceptionReport;
import net.opengis.ows._1.ExceptionType;
import net.opengis.ows._1.Operation;
import net.opengis.wps._1_0_0.*;
import org.h2gis.functions.io.geojson.GeoJsonRead;
import org.h2gis.functions.io.geojson.GeoJsonWrite;
import org.orbisgis.orbiswps.service.model.JaxbContainer;
import org.orbisgis.orbiswps.service.process.ProcessTranslator;
import org.orbisgis.orbiswps.service.process.ProcessWorkerImpl;
import org.orbisgis.orbiswps.service.utils.FormatFactory;
import org.orbisgis.orbiswps.service.utils.Job;
import org.orbisgis.orbiswps.service.utils.WpsDataUtils;
import org.orbisgis.orbiswps.serviceapi.process.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

import javax.sql.DataSource;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.beans.PropertyChangeEvent;
import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.orbisgis.orbiswps.service.operations.Converter.convertCodeType2to1;
import static org.orbisgis.orbiswps.service.operations.Converter.convertLanguageStringType2to1;

/**
 * Class managing the job execution and the generation of the Execute request response.
 *
 * @author Sylvain PALOMINOS (CNRS 2017, UBS 2018)
 * @author Erwan Bocher (CNRS)
 */
public class WPS_1_0_0_Worker implements ProcessExecutionListener, ProcessWorker {

    /** I18N object */
    private static final I18n I18N = I18nFactory.getI18n(ProcessWorkerImpl.class);
    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(WPS_1_0_0_Worker.class);

    private Job job;
    private ResponseDocumentType responseDocumentType;
    private ExceptionReport exceptionReport;
    private String language;
    private ProcessIdentifier pi;
    /** Map containing the process execution output/input model and URI */
    private Map<URI, Object> dataMap;
    private Execute execute;
    private StatusType status;
    private boolean isStatus;
    private ExecuteResponse.ProcessOutputs processOutputs = null;
    private Future future = null;
    private WPS_1_0_0_ServerProperties wpsProp;
    private ProcessManager processManager;
    private DataSource ds;
    private ProgressMonitor progressMonitor;
    private ExecuteResponse response;
    private Marshaller marshaller;

    public WPS_1_0_0_Worker(ExceptionReport exceptionReport, String language, ProcessIdentifier pi,
                            Map<URI, Object> dataMap, Execute execute, WPS_1_0_0_ServerProperties wpsProperties,
                            ProcessManager processManager, DataSource ds, Marshaller marshaller){
        if(execute.isSetResponseForm() && execute.getResponseForm().isSetResponseDocument()) {
            this.responseDocumentType = execute.getResponseForm().getResponseDocument();
        }
        this.exceptionReport = exceptionReport;
        this.language = language;
        this.pi = pi;
        this.dataMap = dataMap;
        this.execute = execute;
        this.wpsProp = wpsProperties;
        this.processManager = processManager;
        this.isStatus = execute.isSetResponseForm() && execute.getResponseForm().isSetResponseDocument() &&
                execute.getResponseForm().getResponseDocument().isSetStatus() &&
                execute.getResponseForm().getResponseDocument().isStatus();
        this.ds = ds;
        this.marshaller = marshaller;
        initJob();
    }


    @Override
    public void run() {
        String title = job.getProcess().getTitle().get(0).getValue();
        progressMonitor.setTaskName(I18N.tr("{0} : Preprocessing", title));
        if(job != null) {
            job.setProcessState(ProcessExecutionListener.ProcessState.RUNNING);
        }
        net.opengis.wps._2_0.ProcessDescriptionType process = pi.getProcessDescriptionType();
        //Catch all the Exception that can be thrown during the script execution.
        try {
            //Print in the log the process execution start
            if(job != null) {
                job.appendLog(ProcessExecutionListener.LogType.INFO, I18N.tr("Start the process."));
            }

            //Pre-process the model
            if(job != null) {
                job.appendLog(ProcessExecutionListener.LogType.INFO, I18N.tr("Pre-processing."));
            }

            //Execute the process and retrieve the groovy object.
            if(job != null) {
                job.appendLog(ProcessExecutionListener.LogType.INFO, I18N.tr("Execute the script."));
            }
            progressMonitor.setTaskName(I18N.tr("{0} : Execution", title));
            processManager.executeProcess(job.getId(), pi, dataMap, pi.getProperties(), progressMonitor);
            progressMonitor.setTaskName(I18N.tr("{0} : Postprocessing", title));
            //Post-process the model
            if(job != null) {
                job.appendLog(ProcessExecutionListener.LogType.INFO, I18N.tr("Post-processing."));
            }

            //Print in the log the process execution end
            if(job != null) {
                job.appendLog(ProcessExecutionListener.LogType.INFO, I18N.tr("End of the process."));
                job.setProcessState(ProcessExecutionListener.ProcessState.SUCCEEDED);
            }
            progressMonitor.endOfProgress();
            processManager.onProcessWorkerFinished(job.getId());
        }
        catch (Exception e) {
            if(job != null) {
                job.setProcessState(ProcessExecutionListener.ProcessState.FAILED);
                LOGGER.error(e.getLocalizedMessage());
                //Print in the log the process execution error
                job.appendLog(ProcessExecutionListener.LogType.ERROR, e.getMessage());
            }
            else{
                LOGGER.error(I18N.tr("Error on execution the WPS  process {0}.\nCause : {1}.",
                        process.getTitle(),e.getMessage()));
            }
            processManager.onProcessWorkerFinished(job.getId());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if(propertyChangeEvent.getPropertyName().equals(ProgressMonitor.PROPERTY_CANCEL)){
            processManager.cancelProcess(job.getId());
        }
    }

    public StatusType getStatus(){
        return status;
    }

    public void setFuture(Future future){
        this.future = future;
    }

    @Override
    public UUID getJobId(){
        return job.getId();
    }

    public Job getJob(){
        return job;
    }

    private void initJob(){
        //Generation of the Job unique ID
        UUID jobId = UUID.randomUUID();

        //Generate the processInstance
        List<String> languages = new ArrayList<>();
        languages.add(language);

        net.opengis.wps._2_0.ProcessDescriptionType process = ProcessTranslator.getTranslatedProcess(pi, languages);
        if(execute.isSetDataInputs() && execute.getDataInputs().isSetInput()) {
            for (InputType inputType : execute.getDataInputs().getInput()){
                if((inputType.isSetData() && inputType.getData().isSetComplexData())){
                    URI uri = URI.create(inputType.getIdentifier().getValue());
                    dataMap.put(uri, WpsDataUtils.formatInputData(
                            dataMap.get(uri), inputType.getData().getComplexData().getMimeType(), ds));
                }
                if(inputType.isSetReference()){
                    URI uri = URI.create(inputType.getIdentifier().getValue());
                    dataMap.put(uri, WpsDataUtils.formatInputData(
                            dataMap.get(uri), inputType.getReference().getMimeType(), ds));
                }
            }
        }
        job = new Job(process, jobId, dataMap,
                wpsProp.CUSTOM_PROPERTIES.MAX_PROCESS_POLLING_DELAY,
                wpsProp.CUSTOM_PROPERTIES.BASE_PROCESS_POLLING_DELAY);
        job.addProcessExecutionlistener(this);

        //Sets the status parameter
        status = new StatusType();
        //Gets and set the process creationTime
        XMLGregorianCalendar xmlCalendar = null;
        try {
            GregorianCalendar gCalendar = new GregorianCalendar();
            gCalendar.setTime(new Date(job.getStartTime()));
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException e) {
            LOGGER.warn("Unable to get the current date into XMLGregorianCalendar : "+e.getMessage());
        }
        status.setCreationTime(xmlCalendar);

        progressMonitor = new ProgressMonitor(job.getProcess().getTitle().get(0).getValue());
        progressMonitor.addPropertyChangeListener(ProgressMonitor.PROPERTY_PROGRESS, this.job);
        progressMonitor.addPropertyChangeListener(ProgressMonitor.PROPERTY_CANCEL, this);
    }

    @Override
    public void appendLog(LogType logType, String message) {}

    @Override
    public void setProcessState(ProcessState processState) {
        updateStatus();
        //If the process has finished and it should be store
        if(responseDocumentType != null && responseDocumentType.isSetStoreExecuteResponse() &&
                responseDocumentType.isStoreExecuteResponse() &&
                (processState.equals(ProcessState.FAILED) || processState.equals(ProcessState.SUCCEEDED))){
        }
    }

    /**
     * Ask fo the process execution to finish and returns the result of the process as a ProcessOutputs object
     * containing all the outputs results.
     * @return A ProcessOutputs with the data of the outputs.
     */
    public ExecuteResponse.ProcessOutputs getResult(){
        job.removeProcessExecutionListener(this);
        if(future != null) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("Error while waiting the process '" + job.getProcess().getIdentifier().getValue() + "' to" +
                        " finish\n" + e.getMessage());
            }
        }
        updateStatus();
        return processOutputs;
    }

    /**
     * Updates the status of the process execution according to the job state.
     */
    private void updateStatus(){
        status.setProcessSucceeded(null);
        status.setProcessFailed(null);
        status.setProcessStarted(null);
        status.setProcessAccepted(null);
        status.setProcessPaused(null);
        switch(job.getState()){
            case IDLE:
                if(isStatus) {
                    ProcessStartedType pst = new ProcessStartedType();
                    pst.setPercentCompleted(job.getProgress());
                    pst.setValue("idle");
                    status.setProcessPaused(pst);
                }
                break;
            case ACCEPTED:
                status.setProcessAccepted("accepted");
                break;
            case RUNNING:
                if(isStatus) {
                    ProcessStartedType pst = new ProcessStartedType();
                    pst.setPercentCompleted(job.getProgress());
                    pst.setValue("running");
                    status.setProcessStarted(pst);
                }
                break;
            case FAILED:
                ProcessFailedType pft = new ProcessFailedType();
                //Sets a detailed exception to return to the client
                pft.setExceptionReport(exceptionReport);
                ExceptionType exceptionType = new ExceptionType();
                exceptionType.setExceptionCode("NoApplicableCode");
                for(Map.Entry<String, ProcessExecutionListener.LogType> entry : job.getLogMap().entrySet()) {
                    if(entry.getValue().equals(ProcessExecutionListener.LogType.ERROR)) {
                        exceptionType.getExceptionText().add(entry.getKey());
                    }
                }
                exceptionReport.getException().add(exceptionType);
                status.setProcessFailed(pft);
                break;
            case SUCCEEDED:
                Map<URI, Object> dataMap = job.getDataMap();
                status.setProcessSucceeded("succeeded");
                ProcessDescriptionType.ProcessOutputs outputs = Converter.convertOutputDescriptionTypeList2to1(
                        job.getProcess().getOutput(), wpsProp.GLOBAL_PROPERTIES.DEFAULT_LANGUAGE, language,
                        new BigInteger(wpsProp.CUSTOM_PROPERTIES.MAXIMUM_MEGABYTES));
                processOutputs = new ExecuteResponse.ProcessOutputs();
                //Iterate on the output defined in the Execute request and on the  output in the process to build
                // the response outputs
                if(responseDocumentType!=null && responseDocumentType.isSetOutput() &&
                        !responseDocumentType.getOutput().isEmpty()) {
                    for (DocumentOutputDefinitionType output : responseDocumentType.getOutput()) {
                        processOutputs.getOutput().addAll(getOutputData(outputs, output, dataMap));
                    }
                }
                else{
                    processOutputs.getOutput().addAll(getOutputData(outputs, null, dataMap));
                }
                break;
        }
        if (responseDocumentType!=null && responseDocumentType.isSetStoreExecuteResponse() &&
                responseDocumentType.isStoreExecuteResponse()) {
            try {
                writeResponse(response);
            } catch (JAXBException e) {
                LOGGER.error(I18N.tr("Error while writing process response :\n{0}", e.getLocalizedMessage()));
            }
        }
    }

    public void setResponse(ExecuteResponse executeResponse){
        this.response = executeResponse;
    }

    public void writeResponse(ExecuteResponse executeResponse) throws JAXBException {
        File f = new File(wpsProp.CUSTOM_PROPERTIES.WORKSPACE_PATH,job.getId().toString());
        response = executeResponse;
        if(response == null){
            response = new ExecuteResponse();
        }
        response.setStatus(status);
        response.setStatusLocation(f.toURI().toString());
        response.setProcessOutputs(processOutputs);
        marshaller.marshal(response, f);
    }

    /**
     * Returns the list of OutputDataType object generated from the given ProcessOutputs which match with the given
     * DocumentOutputDefinitionType if defined with the correct data from the given map.
     * If the DocumentOutputDefinitionType is set, try to use its specification (like mimeType) to set the outputs.
     * @param outputs Object containing all the process outputs.
     * @param output Output from the execute request.
     *               If not null, the returned OutputDataTypes should match with it.
     *               If null, return all the OutputDataTypes
     * @param dataMap Map containing the result data of the outputs
     * @return Lhe list of OutputDataType object generated
     */
    private List<OutputDataType> getOutputData(ProcessDescriptionType.ProcessOutputs outputs,
                                               DocumentOutputDefinitionType output, Map<URI, Object> dataMap){
        List<OutputDataType> list = new ArrayList<>();
        for(OutputDescriptionType outputDscrType : outputs.getOutput()) {
            if(output== null || output.getIdentifier().getValue().equals(outputDscrType.getIdentifier().getValue())) {
                URI uri = URI.create(outputDscrType.getIdentifier().getValue());
                Object o = dataMap.get(uri);

                OutputDataType outputDataType = new OutputDataType();
                list.add(outputDataType);
                outputDataType.setTitle(outputDscrType.getTitle());
                outputDataType.setAbstract(outputDscrType.getAbstract());
                outputDataType.setIdentifier(outputDscrType.getIdentifier());
                if(output != null && output.isSetAsReference() && output.isAsReference() && o != null) {
                    if (o instanceof Serializable) {
                        try {
                            File file = new File(wpsProp.CUSTOM_PROPERTIES.WORKSPACE_PATH,
                                    uri.toString().replaceAll("([.:/\\\\])", "_"));
                            FileOutputStream fout = new FileOutputStream(file);
                            fout.write(WpsDataUtils.formatOutputData(o, output.getMimeType(), ds,
                                    wpsProp.CUSTOM_PROPERTIES.WORKSPACE_PATH).toString().getBytes());
                            fout.close();
                            OutputReferenceType referenceType = new OutputReferenceType();
                            referenceType.setHref(file.toURI().toURL().toString());
                            outputDataType.setReference(referenceType);
                        } catch (IOException e) {
                            LOGGER.error("Unable to write the serializable object '" + uri.toString() + "'\n" +
                                    e.getMessage());
                        }
                    } else {
                        LOGGER.warn("Unable to write the object '" + uri.toString() + "', it should be an instance of Serializable");
                    }
                }
                else {
                    DataType dataType = new DataType();
                    outputDataType.setData(dataType);

                    if (outputDscrType.isSetLiteralOutput()) {
                        LiteralDataType literalDataType = new LiteralDataType();
                        dataType.setLiteralData(literalDataType);
                        literalDataType.setDataType(outputDscrType.getLiteralOutput().getDataType().getValue());
                        if (o instanceof String[]) {
                            StringBuilder data = new StringBuilder();
                            for (String str : (String[]) o) {
                                if (data.length() > 0) {
                                    data.append(";");
                                }
                                data.append(str);
                            }
                            literalDataType.setValue(data.toString());
                            dataType.setLiteralData(literalDataType);
                        } else if (o != null) {
                            literalDataType.setValue(o.toString());
                            dataType.setLiteralData(literalDataType);
                        } else {
                            literalDataType.setValue(null);
                            dataType.setLiteralData(literalDataType);
                        }
                    } else if (outputDscrType.isSetBoundingBoxOutput()) {
                        if (o instanceof Geometry) {
                            dataType.setBoundingBoxData(WpsDataUtils.parseGeometryToOws1BoundingBox((Geometry) o));
                        } else {
                            LOGGER.error("The output '" + uri + "' should be a Geometry");
                            dataType.setBoundingBoxData(new BoundingBoxType());
                        }

                    } else if (outputDscrType.isSetComplexOutput()) {
                        ComplexDataCombinationType dflt = outputDscrType.getComplexOutput().getDefault();
                        ComplexDataType complexDataType = new ComplexDataType();
                        if (output != null && output.isSetMimeType()) {
                            complexDataType.setMimeType(output.getMimeType());
                        } else {
                            complexDataType.setMimeType(dflt.getFormat().getMimeType());
                        }
                        if (output != null && output.isSetEncoding()) {
                            complexDataType.setEncoding(output.getEncoding());
                        } else {
                            complexDataType.setEncoding(dflt.getFormat().getEncoding());
                        }
                        if (output != null && output.isSetSchema()) {
                            complexDataType.setSchema(output.getSchema());
                        } else {
                            complexDataType.setSchema(dflt.getFormat().getSchema());
                        }
                        complexDataType.getContent().add(WpsDataUtils.formatOutputData(o,
                                complexDataType.getMimeType(), ds, wpsProp.CUSTOM_PROPERTIES.WORKSPACE_PATH));
                        dataType.setComplexData(complexDataType);
                    }
                }
            }
        }
        return list;
    }
}
