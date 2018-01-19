/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.processors.hadoop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.nifi.annotation.behavior.ReadsAttribute;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.components.AllowableValue;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.flowfile.attributes.CoreAttributes;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;
import org.apache.nifi.util.StopWatch;

/**
 * This processor renames files on HDFS.
 */
@Tags({"hadoop", "HDFS", "put", "move", "filesystem", "restricted", "moveHDFS"})
@CapabilityDescription("Rename existing files or a directory of files (non-recursive) on Hadoop Distributed File System (HDFS).")
@ReadsAttribute(attribute = "filename", description = "The name of the file written to HDFS comes from the value of this attribute.")
@WritesAttributes({
        @WritesAttribute(attribute = "filename", description = "The name of the file written to HDFS is stored in this attribute."),
        @WritesAttribute(attribute = "absolute.hdfs.path", description = "The absolute path to the file on HDFS is stored in this attribute.")})
@SeeAlso({PutHDFS.class, GetHDFS.class})
public class MoveHDFS extends AbstractHadoopProcessor {

    // static global
    public static final String REPLACE_RESOLUTION = "replace";
    public static final String IGNORE_RESOLUTION = "ignore";
    public static final String FAIL_RESOLUTION = "fail";

    private static final Set<Relationship> relationships;

    public static final AllowableValue REPLACE_RESOLUTION_AV = new AllowableValue(REPLACE_RESOLUTION,
            REPLACE_RESOLUTION, "Replaces the existing file if any.");
    public static final AllowableValue IGNORE_RESOLUTION_AV = new AllowableValue(IGNORE_RESOLUTION, IGNORE_RESOLUTION,
            "Failed rename operation stops processing and routes to success.");
    public static final AllowableValue FAIL_RESOLUTION_AV = new AllowableValue(FAIL_RESOLUTION, FAIL_RESOLUTION,
            "Failing to rename a file routes to failure.");

    public static final String ABSOLUTE_HDFS_PATH_ATTRIBUTE = "absolute.hdfs.path";

    // relationships
    public static final Relationship REL_SUCCESS = new Relationship.Builder().name("success")
            .description("Files that have been successfully renamed on HDFS are transferred to this relationship")
            .build();

    public static final Relationship REL_FAILURE = new Relationship.Builder().name("failure")
            .description("Files that could not be renamed on HDFS are transferred to this relationship").build();

    // properties
    public static final PropertyDescriptor CONFLICT_RESOLUTION = new PropertyDescriptor.Builder()
            .name("Conflict Resolution Strategy")
            .description(
                    "Indicates what should happen when a file with the same name already exists in the output directory")
            .required(true).defaultValue(FAIL_RESOLUTION_AV.getValue())
            .allowableValues(REPLACE_RESOLUTION_AV, IGNORE_RESOLUTION_AV, FAIL_RESOLUTION_AV).build();

    public static final PropertyDescriptor FILE_FILTER_REGEX = new PropertyDescriptor.Builder()
            .name("File Filter Regex")
            .description(
                    "A Java Regular Expression for filtering Filenames; if a filter is supplied then only files whose names match that Regular "
                            + "Expression will be fetched, otherwise all files will be fetched")
            .required(false).addValidator(StandardValidators.REGULAR_EXPRESSION_VALIDATOR).build();

    public static final PropertyDescriptor IGNORE_DOTTED_FILES = new PropertyDescriptor.Builder()
            .name("Ignore Dotted Files")
            .description("If true, files whose names begin with a dot (\".\") will be ignored").required(true)
            .allowableValues("true", "false").defaultValue("true").build();

    public static final PropertyDescriptor INPUT_DIRECTORY_OR_FILE = new PropertyDescriptor.Builder()
            .name("Input Directory or File")
            .description("The HDFS directory from which files should be read, or a single file to read.")
            .defaultValue("${path}").addValidator(StandardValidators.ATTRIBUTE_EXPRESSION_LANGUAGE_VALIDATOR)
            .expressionLanguageSupported(true).build();

    public static final PropertyDescriptor OUTPUT_DIRECTORY = new PropertyDescriptor.Builder().name("Output Directory")
            .description("The HDFS directory where the files will be moved to").required(true)
            .addValidator(StandardValidators.ATTRIBUTE_EXPRESSION_LANGUAGE_VALIDATOR).expressionLanguageSupported(true)
            .build();

    public static final PropertyDescriptor OPERATION = new PropertyDescriptor.Builder().name("HDFS Operation")
            .description("The operation that will be performed on the source file").required(true)
            .allowableValues("move", "copy").defaultValue("move").build();

    public static final PropertyDescriptor REMOTE_OWNER = new PropertyDescriptor.Builder().name("Remote Owner")
            .description(
                    "Changes the owner of the HDFS file to this value after it is written. This only works if NiFi is running as a user that has HDFS super user privilege to change owner")
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR).build();

    public static final PropertyDescriptor REMOTE_GROUP = new PropertyDescriptor.Builder().name("Remote Group")
            .description(
                    "Changes the group of the HDFS file to this value after it is written. This only works if NiFi is running as a user that has HDFS super user privilege to change group")
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR).build();

    static {
        final Set<Relationship> rels = new HashSet<>();
        rels.add(REL_SUCCESS);
        rels.add(REL_FAILURE);
        relationships = Collections.unmodifiableSet(rels);
    }

    // non-static global
    protected ProcessorConfiguration processorConfig;
    private final AtomicLong logEmptyListing = new AtomicLong(2L);

    private final Lock listingLock = new ReentrantLock();
    private final Lock queueLock = new ReentrantLock();

    private final BlockingQueue<Path> filePathQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Path> processing = new LinkedBlockingQueue<>();

    // methods
    @Override
    public Set<Relationship> getRelationships() {
        return relationships;
    }

    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        List<PropertyDescriptor> props = new ArrayList<>(properties);
        props.add(CONFLICT_RESOLUTION);
        props.add(INPUT_DIRECTORY_OR_FILE);
        props.add(OUTPUT_DIRECTORY);
        props.add(OPERATION);
        props.add(FILE_FILTER_REGEX);
        props.add(IGNORE_DOTTED_FILES);
        props.add(REMOTE_OWNER);
        props.add(REMOTE_GROUP);
        return props;
    }

    @OnScheduled
    public void onScheduled(ProcessContext context) throws Exception {
        super.abstractOnScheduled(context);
        // copy configuration values to pass them around cleanly
        processorConfig = new ProcessorConfiguration(context);
        // forget the state of the queue in case HDFS contents changed while
        // this processor was turned off
        queueLock.lock();
        try {
            filePathQueue.clear();
            processing.clear();
        } finally {
            queueLock.unlock();
        }
    }

    @Override
    public void onTrigger(ProcessContext context, ProcessSession session) throws ProcessException {
        // MoveHDFS
        FlowFile parentFlowFile = session.get();
        if (parentFlowFile == null) {
            return;
        }

        final FileSystem hdfs = getFileSystem();
        final String filenameValue = context.getProperty(INPUT_DIRECTORY_OR_FILE).evaluateAttributeExpressions(parentFlowFile).getValue();

        Path inputPath = null;
        try {
            inputPath = new Path(filenameValue);
            if (!hdfs.exists(inputPath)) {
                throw new IOException("Input Directory or File does not exist in HDFS");
            }
        } catch (Exception e) {
            getLogger().error("Failed to retrieve content from {} for {} due to {}; routing to failure", new Object[]{filenameValue, parentFlowFile, e});
            parentFlowFile = session.putAttribute(parentFlowFile, "hdfs.failure.reason", e.getMessage());
            parentFlowFile = session.penalize(parentFlowFile);
            session.transfer(parentFlowFile, REL_FAILURE);
            return;
        }

        List<Path> files = new ArrayList<Path>();

        try {
            final StopWatch stopWatch = new StopWatch(true);
            Set<Path> listedFiles = performListing(context, inputPath);
            stopWatch.stop();
            final long millis = stopWatch.getDuration(TimeUnit.MILLISECONDS);

            if (listedFiles != null) {
                // place files into the work queue
                int newItems = 0;
                queueLock.lock();
                try {
                    for (Path file : listedFiles) {
                        if (!filePathQueue.contains(file) && !processing.contains(file)) {
                            if (!filePathQueue.offer(file)) {
                                break;
                            }
                            newItems++;
                        }
                    }
                } catch (Exception e) {
                    getLogger().warn("Could not add to processing queue due to {}", new Object[]{e.getMessage()}, e);
                } finally {
                    queueLock.unlock();
                }
                if (listedFiles.size() > 0) {
                    logEmptyListing.set(3L);
                }
                if (logEmptyListing.getAndDecrement() > 0) {
                    getLogger().info(
                            "Obtained file listing in {} milliseconds; listing had {} items, {} of which were new",
                            new Object[]{millis, listedFiles.size(), newItems});
                }
            }
        } catch (IOException e) {
            context.yield();
            getLogger().warn("Error while retrieving list of files due to {}", new Object[]{e});
            return;
        }

        // prepare to process a batch of files in the queue
        queueLock.lock();
        try {
            filePathQueue.drainTo(files);
            if (files.isEmpty()) {
                // nothing to do!
                session.remove(parentFlowFile);
                context.yield();
                return;
            }
        } finally {
            queueLock.unlock();
        }

        processBatchOfFiles(files, context, session, parentFlowFile);

        queueLock.lock();
        try {
            processing.removeAll(files);
        } finally {
            queueLock.unlock();
        }

        session.remove(parentFlowFile);
    }

    protected void processBatchOfFiles(final List<Path> files, final ProcessContext context,
                                       final ProcessSession session, FlowFile parentFlowFile) {
        Preconditions.checkState(parentFlowFile != null, "No parent flowfile for this batch was provided");

        // process the batch of files
        final Configuration conf = getConfiguration();
        final FileSystem hdfs = getFileSystem();
        final UserGroupInformation ugi = getUserGroupInformation();

        if (conf == null || ugi == null) {
            getLogger().error("Configuration or UserGroupInformation not configured properly");
            session.transfer(parentFlowFile, REL_FAILURE);
            context.yield();
            return;
        }

        for (final Path file : files) {

            ugi.doAs(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    FlowFile flowFile = session.create(parentFlowFile);
                    try {
                        final String originalFilename = file.getName();
                        final Path configuredRootOutputDirPath = processorConfig.getOutputDirectory();
                        final Path newFile = new Path(configuredRootOutputDirPath, originalFilename);
                        final boolean destinationExists = hdfs.exists(newFile);
                        // If destination file already exists, resolve that
                        // based on processor configuration
                        if (destinationExists) {
                            switch (processorConfig.getConflictResolution()) {
                                case REPLACE_RESOLUTION:
                                    if (hdfs.delete(file, false)) {
                                        getLogger().info("deleted {} in order to replace with the contents of {}",
                                                new Object[]{file, flowFile});
                                    }
                                    break;
                                case IGNORE_RESOLUTION:
                                    session.transfer(flowFile, REL_SUCCESS);
                                    getLogger().info(
                                            "transferring {} to success because file with same name already exists",
                                            new Object[]{flowFile});
                                    return null;
                                case FAIL_RESOLUTION:
                                    session.transfer(session.penalize(flowFile), REL_FAILURE);
                                    getLogger().warn(
                                            "penalizing {} and routing to failure because file with same name already exists",
                                            new Object[]{flowFile});
                                    return null;
                                default:
                                    break;
                            }
                        }

                        // Create destination directory if it does not exist
                        try {
                            if (!hdfs.getFileStatus(configuredRootOutputDirPath).isDirectory()) {
                                throw new IOException(configuredRootOutputDirPath.toString()
                                        + " already exists and is not a directory");
                            }
                        } catch (FileNotFoundException fe) {
                            if (!hdfs.mkdirs(configuredRootOutputDirPath)) {
                                throw new IOException(configuredRootOutputDirPath.toString() + " could not be created");
                            }
                            changeOwner(context, hdfs, configuredRootOutputDirPath);
                        }

                        boolean moved = false;
                        for (int i = 0; i < 10; i++) { // try to rename multiple
                            // times.
                            if (processorConfig.getOperation().equals("move")) {
                                if (hdfs.rename(file, newFile)) {
                                    moved = true;
                                    break;// rename was successful
                                }
                            } else {
                                if (FileUtil.copy(hdfs, file, hdfs, newFile, false, conf)) {
                                    moved = true;
                                    break;// copy was successful
                                }
                            }
                            Thread.sleep(200L);// try waiting to let whatever might cause rename failure to resolve
                        }
                        if (!moved) {
                            throw new ProcessException("Could not move file " + file + " to its final filename");
                        }

                        changeOwner(context, hdfs, newFile);
                        final String outputPath = newFile.toString();
                        final String newFilename = newFile.getName();
                        final String hdfsPath = newFile.getParent().toString();
                        flowFile = session.putAttribute(flowFile, CoreAttributes.FILENAME.key(), newFilename);
                        flowFile = session.putAttribute(flowFile, ABSOLUTE_HDFS_PATH_ATTRIBUTE, hdfsPath);
                        final String transitUri = (outputPath.startsWith("/")) ? "hdfs:/" + outputPath
                                : "hdfs://" + outputPath;
                        session.getProvenanceReporter().send(flowFile, transitUri);
                        session.transfer(flowFile, REL_SUCCESS);

                    } catch (final Throwable t) {
                        getLogger().error("Failed to rename on HDFS due to {}", new Object[]{t});
                        session.transfer(session.penalize(flowFile), REL_FAILURE);
                        context.yield();
                    }
                    return null;
                }
            });
        }
    }

    protected Set<Path> performListing(final ProcessContext context, Path path) throws IOException {
        Set<Path> listing = null;

        if (listingLock.tryLock()) {
            try {
                final FileSystem hdfs = getFileSystem();
                // get listing
                listing = selectFiles(hdfs, path, null);
            } finally {
                listingLock.unlock();
            }
        }

        return listing;
    }

    protected void changeOwner(final ProcessContext context, final FileSystem hdfs, final Path name) {
        try {
            // Change owner and group of file if configured to do so
            String owner = context.getProperty(REMOTE_OWNER).getValue();
            String group = context.getProperty(REMOTE_GROUP).getValue();
            if (owner != null || group != null) {
                hdfs.setOwner(name, owner, group);
            }
        } catch (Exception e) {
            getLogger().warn("Could not change owner or group of {} on HDFS due to {}", new Object[]{name, e.getMessage()}, e);
        }
    }

    protected Set<Path> selectFiles(final FileSystem hdfs, final Path inputPath, Set<Path> filesVisited)
            throws IOException {
        if (null == filesVisited) {
            filesVisited = new HashSet<>();
        }

        if (!hdfs.exists(inputPath)) {
            throw new IOException("Selection directory " + inputPath.toString() + " doesn't appear to exist!");
        }

        final Set<Path> files = new HashSet<>();

        FileStatus inputStatus = hdfs.getFileStatus(inputPath);

        if (inputStatus.isDirectory()) {
            for (final FileStatus file : hdfs.listStatus(inputPath)) {
                final Path canonicalFile = file.getPath();

                if (!filesVisited.add(canonicalFile)) { // skip files we've already seen (may be looping directory links)
                    continue;
                }

                if (!file.isDirectory() && processorConfig.getPathFilter(inputPath).accept(canonicalFile)) {
                    files.add(canonicalFile);

                    if (getLogger().isDebugEnabled()) {
                        getLogger().debug(this + " selected file at path: " + canonicalFile.toString());
                    }
                }
            }
        } else if (inputStatus.isFile()) {
            files.add(inputPath);
        }
        return files;
    }

    protected static class ProcessorConfiguration {

        final private String conflictResolution;
        final private String operation;
        final private Path inputRootDirPath;
        final private Path outputRootDirPath;
        final private Pattern fileFilterPattern;
        final private boolean ignoreDottedFiles;

        ProcessorConfiguration(final ProcessContext context) {
            conflictResolution = context.getProperty(CONFLICT_RESOLUTION).getValue();
            operation = context.getProperty(OPERATION).getValue();
            final String inputDirValue = context.getProperty(INPUT_DIRECTORY_OR_FILE).evaluateAttributeExpressions().getValue();
            inputRootDirPath = new Path(inputDirValue);
            final String outputDirValue = context.getProperty(OUTPUT_DIRECTORY).evaluateAttributeExpressions().getValue();
            outputRootDirPath = new Path(outputDirValue);
            final String fileFilterRegex = context.getProperty(FILE_FILTER_REGEX).getValue();
            fileFilterPattern = (fileFilterRegex == null) ? null : Pattern.compile(fileFilterRegex);
            ignoreDottedFiles = context.getProperty(IGNORE_DOTTED_FILES).asBoolean();
        }

        public String getOperation() {
            return operation;
        }

        public String getConflictResolution() {
            return conflictResolution;
        }

        public Path getInput() {
            return inputRootDirPath;
        }

        public Path getOutputDirectory() {
            return outputRootDirPath;
        }

        protected PathFilter getPathFilter(final Path dir) {
            return new PathFilter() {

                @Override
                public boolean accept(Path path) {
                    if (ignoreDottedFiles && path.getName().startsWith(".")) {
                        return false;
                    }
                    final String pathToCompare;
                    String relativePath = getPathDifference(dir, path);
                    if (relativePath.length() == 0) {
                        pathToCompare = path.getName();
                    } else {
                        pathToCompare = relativePath + Path.SEPARATOR + path.getName();
                    }

                    if (fileFilterPattern != null && !fileFilterPattern.matcher(pathToCompare).matches()) {
                        return false;
                    }
                    return true;
                }

            };
        }
    }
}