/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.storage.hdfs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import io.druid.jackson.DefaultObjectMapper;
import io.druid.segment.loading.DataSegmentPusherUtil;
import io.druid.timeline.DataSegment;
import io.druid.timeline.partition.NoneShardSpec;
import io.druid.timeline.partition.NumberedShardSpec;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

/**
 */
public class HdfsDataSegmentPusherTest
{
  @Rule
  public final TemporaryFolder tempFolder = new TemporaryFolder();

  @Rule
  public final ExpectedException expectedException = ExpectedException.none();

  TestObjectMapper objectMapper = new TestObjectMapper();

  @Test
  public void testPushWithScheme() throws Exception
  {
    testUsingScheme("file");
  }

  @Test
  public void testPushWithBadScheme() throws Exception
  {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Wrong FS");
    testUsingScheme("xyzzy");

    // Not reached
    Assert.assertTrue(false);
  }

  @Test
  public void testPushWithoutScheme() throws Exception
  {
    testUsingScheme(null);
  }

  @Test
  public void testPushWithMultipleSegments() throws Exception
  {
    testUsingSchemeForMultipleSegments("file", 3);
  }

  private void testUsingScheme(final String scheme) throws Exception
  {
    Configuration conf = new Configuration(true);

    // Create a mock segment on disk
    File segmentDir = tempFolder.newFolder();
    File tmp = new File(segmentDir, "version.bin");

    final byte[] data = new byte[]{0x0, 0x0, 0x0, 0x1};
    Files.write(data, tmp);
    final long size = data.length;

    HdfsDataSegmentPusherConfig config = new HdfsDataSegmentPusherConfig();
    final File storageDirectory = tempFolder.newFolder();

    config.setStorageDirectory(
        scheme != null
        ? String.format("%s://%s", scheme, storageDirectory.getAbsolutePath())
        : storageDirectory.getAbsolutePath()
    );
    HdfsDataSegmentPusher pusher = new HdfsDataSegmentPusher(config, conf, new DefaultObjectMapper());

    DataSegment segmentToPush = new DataSegment(
        "foo",
        new Interval("2015/2016"),
        "0",
        Maps.<String, Object>newHashMap(),
        Lists.<String>newArrayList(),
        Lists.<String>newArrayList(),
        NoneShardSpec.instance(),
        0,
        size
    );

    DataSegment segment = pusher.push(segmentDir, segmentToPush);


    String indexUri = String.format(
        "%s/%s/%d_index.zip",
        FileSystem.newInstance(conf).makeQualified(new Path(config.getStorageDirectory())).toUri().toString(),
        DataSegmentPusherUtil.getHdfsStorageDir(segmentToPush),
        segmentToPush.getShardSpec().getPartitionNum()
    );

    Assert.assertEquals(segmentToPush.getSize(), segment.getSize());
    Assert.assertEquals(segmentToPush, segment);
    Assert.assertEquals(ImmutableMap.of(
        "type",
        "hdfs",
        "path",
        indexUri
    ), segment.getLoadSpec());
    // rename directory after push
    final String segmentPath = DataSegmentPusherUtil.getHdfsStorageDir(segment);

    File indexFile = new File(String.format(
        "%s/%s/%d_index.zip",
        storageDirectory,
        segmentPath,
        segment.getShardSpec().getPartitionNum()
    ));
    Assert.assertTrue(indexFile.exists());
    File descriptorFile = new File(String.format(
        "%s/%s/%d_descriptor.json",
        storageDirectory,
        segmentPath,
        segment.getShardSpec().getPartitionNum()
    ));
    Assert.assertTrue(descriptorFile.exists());

    // push twice will fail and temp dir cleaned
    File outDir = new File(String.format("%s/%s", config.getStorageDirectory(), segmentPath));
    outDir.setReadOnly();
    try {
      pusher.push(segmentDir, segmentToPush);
    }
    catch (IOException e) {
      Assert.fail("should not throw exception");
    }
  }

  private void testUsingSchemeForMultipleSegments(final String scheme, final int numberOfSegments) throws Exception
  {
    Configuration conf = new Configuration(true);
    DataSegment[] segments = new DataSegment[numberOfSegments];

    // Create a mock segment on disk
    File segmentDir = tempFolder.newFolder();
    File tmp = new File(segmentDir, "version.bin");

    final byte[] data = new byte[]{0x0, 0x0, 0x0, 0x1};
    Files.write(data, tmp);
    final long size = data.length;

    HdfsDataSegmentPusherConfig config = new HdfsDataSegmentPusherConfig();
    final File storageDirectory = tempFolder.newFolder();

    config.setStorageDirectory(
        scheme != null
        ? String.format("%s://%s", scheme, storageDirectory.getAbsolutePath())
        : storageDirectory.getAbsolutePath()
    );
    HdfsDataSegmentPusher pusher = new HdfsDataSegmentPusher(config, conf, new DefaultObjectMapper());

    for (int i = 0; i < numberOfSegments; i++) {
      segments[i] = new DataSegment(
          "foo",
          new Interval("2015/2016"),
          "0",
          Maps.<String, Object>newHashMap(),
          Lists.<String>newArrayList(),
          Lists.<String>newArrayList(),
          new NumberedShardSpec(i, i),
          0,
          size
      );
    }

    for (int i = 0; i < numberOfSegments; i++) {
      final DataSegment pushedSegment = pusher.push(segmentDir, segments[i]);

      String indexUri = String.format(
          "%s/%s/%d_index.zip",
          FileSystem.newInstance(conf).makeQualified(new Path(config.getStorageDirectory())).toUri().toString(),
          DataSegmentPusherUtil.getHdfsStorageDir(segments[i]),
          segments[i].getShardSpec().getPartitionNum()
      );

      Assert.assertEquals(segments[i].getSize(), pushedSegment.getSize());
      Assert.assertEquals(segments[i], pushedSegment);
      Assert.assertEquals(ImmutableMap.of(
          "type",
          "hdfs",
          "path",
          indexUri
      ), pushedSegment.getLoadSpec());
      // rename directory after push
      String segmentPath = DataSegmentPusherUtil.getHdfsStorageDir(pushedSegment);

      File indexFile = new File(String.format(
          "%s/%s/%d_index.zip",
          storageDirectory,
          segmentPath,
          pushedSegment.getShardSpec().getPartitionNum()
      ));
      Assert.assertTrue(indexFile.exists());
      File descriptorFile = new File(String.format(
          "%s/%s/%d_descriptor.json",
          storageDirectory,
          segmentPath,
          pushedSegment.getShardSpec().getPartitionNum()
      ));
      Assert.assertTrue(descriptorFile.exists());

      //read actual data from descriptor file.
      DataSegment fromDescriptorFileDataSegment = objectMapper.readValue(descriptorFile, DataSegment.class);

      Assert.assertEquals(segments[i].getSize(), pushedSegment.getSize());
      Assert.assertEquals(segments[i], pushedSegment);
      Assert.assertEquals(ImmutableMap.of(
          "type",
          "hdfs",
          "path",
          indexUri
      ), fromDescriptorFileDataSegment.getLoadSpec());
      // rename directory after push
      segmentPath = DataSegmentPusherUtil.getHdfsStorageDir(fromDescriptorFileDataSegment);

      indexFile = new File(String.format(
          "%s/%s/%d_index.zip",
          storageDirectory,
          segmentPath,
          fromDescriptorFileDataSegment.getShardSpec().getPartitionNum()
      ));
      Assert.assertTrue(indexFile.exists());


      // push twice will fail and temp dir cleaned
      File outDir = new File(String.format("%s/%s", config.getStorageDirectory(), segmentPath));
      outDir.setReadOnly();
      try {
        pusher.push(segmentDir, segments[i]);
      }
      catch (IOException e) {
        Assert.fail("should not throw exception");
      }
    }
  }

  public class TestObjectMapper extends ObjectMapper
  {
    public TestObjectMapper()
    {
      configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      configure(MapperFeature.AUTO_DETECT_GETTERS, false);
      configure(MapperFeature.AUTO_DETECT_FIELDS, false);
      configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
      configure(MapperFeature.AUTO_DETECT_SETTERS, false);
      configure(SerializationFeature.INDENT_OUTPUT, false);
      registerModule(new TestModule().registerSubtypes(new NamedType(NumberedShardSpec.class, "NumberedShardSpec")));
    }

    public class TestModule extends SimpleModule
    {
      TestModule()
      {
        addSerializer(Interval.class, ToStringSerializer.instance);
        addSerializer(NumberedShardSpec.class, ToStringSerializer.instance);
        addDeserializer(
            Interval.class, new StdDeserializer<Interval>(Interval.class)
            {
              @Override
              public Interval deserialize(
                  JsonParser jsonParser, DeserializationContext deserializationContext
              ) throws IOException, JsonProcessingException
              {
                return new Interval(jsonParser.getText());
              }
            }
        );
      }
    }
  }

}
