/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.bigtable.beam.sequencefiles;

import com.google.cloud.bigtable.hbase.BigtableConfiguration;
import com.google.common.base.Throwables;
import java.util.List;
import java.util.Objects;
import org.apache.beam.sdk.io.BoundedSource.BoundedReader;
import org.apache.beam.sdk.extensions.gcp.options.GcpOptions;
import org.apache.beam.sdk.io.FileBasedSource;
import org.apache.beam.sdk.io.FileSystems;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider.StaticValueProvider;
import org.apache.beam.sdk.values.KV;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;

/**
 * Command to create a new table.
 *
 * <pre>
 * mvn compile exec:java \
 *   -Dexec.args="create-table --runner=DataflowRunner \
 *                --project=$PROJECT  \
 *                --bigtableInstanceId=$INSTANCE \
 *                --bigtableTableId=$TABLE \
 *                --sourcePattern=gs://$SOURCE_PATTERN \
 *                --families=$FAMILIES"
 *</pre>
 *
 * <p>The table will be presplit based on row keys collected from a set of sequence files. This is
 * intended to be a preparation step before running an {@link ImportJob}.
 */
class CreateTableHelper {
  private static final Log LOG = LogFactory.getLog(CreateTableHelper.class);

  interface CreateTableOpts extends GcpOptions {
    @Description("The project that contains the table to export. Defaults to --project.")
    @Default.InstanceFactory(Utils.DefaultBigtableProjectFactory.class)
    String getBigtableProject();
    @SuppressWarnings("unused")
    void setBigtableProject(String projectId);

    @Description("The Bigtable instance id that contains the table to export.")
    String getBigtableInstanceId();
    @SuppressWarnings("unused")
    void setBigtableInstanceId(String instanceId);

    @Description("The Bigtable table id to export.")
    String getBigtableTableId();
    @SuppressWarnings("unused")
    void setBigtableTableId(String tableId);

    @Description(
        "The fully qualified file pattern to import. Should of the form '[destinationPath]/part-*'")
    String getSourcePattern();
    @SuppressWarnings("unused")
    void setSourcePattern(String sourcePath);

    @Description("The families to add to the new table")
    List<String> getFamilies();
    void setFamilies(List<String> families);
  }

  public static void main(String[] args) throws Exception {
    PipelineOptionsFactory.register(CreateTableOpts.class);

    CreateTableOpts opts = PipelineOptionsFactory
        .fromArgs(args).withValidation()
        .as(CreateTableOpts.class);

    FileSystems.setDefaultPipelineOptions(opts);

    createTable(opts);

  }

  static void createTable(CreateTableOpts opts) throws Exception {
    LOG.info("Extracting splits from the source files");

    // Create the same source as the import job would use
    SequenceFileSource<ImmutableBytesWritable, Result> source =
        ImportJob.createSource(StaticValueProvider.of(opts.getSourcePattern()));

    // Extract the splits from the sequence files
    List<? extends FileBasedSource<KV<ImmutableBytesWritable, Result>>> splitSources = source
        .split(ImportJob.BUNDLE_SIZE, opts);

    // Read the start key of each split
    byte[][] splits = splitSources.stream()
        .parallel()
        .map(splitSource -> {
          try (BoundedReader<KV<ImmutableBytesWritable, Result>> reader = splitSource
              .createReader(opts)) {
            if (reader.start()) {
              return reader.getCurrent().getKey();
            }
          } catch (Exception e) {
            Throwables.propagate(e);
          }
          return null;
        })
        .filter(Objects::nonNull)
        .sorted()
        .map(ImmutableBytesWritable::copyBytes)
        .toArray(byte[][]::new);

    LOG.info(String.format("Creating a new table with %d splits and the families: %s",
        splits.length, opts.getFamilies()));

    try (Connection connection =
        BigtableConfiguration.connect(opts.getBigtableProject(), opts.getBigtableInstanceId())) {

      TableName tableName = TableName.valueOf(opts.getBigtableTableId());
      HTableDescriptor descriptor = new HTableDescriptor(tableName);

      for (String family : opts.getFamilies()) {
        descriptor.addFamily(
            new HColumnDescriptor(family)
        );
      }

      connection.getAdmin().createTable(descriptor, splits);
    }
  }
}
