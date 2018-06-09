/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.tools.appengine.cloudsdk;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.cloud.tools.appengine.api.AppEngineException;
import com.google.cloud.tools.appengine.api.deploy.StageStandardConfiguration;
import com.google.cloud.tools.appengine.cloudsdk.process.ProcessHandlerException;
import com.google.cloud.tools.test.utils.SpyVerifier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/** Unit tests for {@link CloudSdkAppEngineStandardStaging}. */
@RunWith(MockitoJUnitRunner.class)
public class CloudSdkAppEngineStandardStagingTest {

  @Rule public TemporaryFolder tmpDir = new TemporaryFolder();

  @Mock private AppCfgRunner appCfgRunner;

  private File source;
  private File destination;
  private File dockerfile;
  private StageStandardConfiguration configuration;
  private CloudSdkAppEngineStandardStaging staging;

  @Before
  public void setUp() throws IOException {
    source = tmpDir.newFolder("source");
    destination = tmpDir.newFolder("destination");
    dockerfile = tmpDir.newFile("dockerfile");

    staging = new CloudSdkAppEngineStandardStaging(appCfgRunner);

    configuration =
        new StageStandardConfiguration.Builder()
            .setSourceDirectory(source)
            .setStagingDirectory(destination)
            .build();
  }

  @Test
  public void testCheckFlags_allFlags() throws Exception {
    StageStandardConfiguration spy = Mockito.spy(configuration);
    spy.setDockerfile(dockerfile);
    spy.setEnableQuickstart(true);
    spy.setDisableUpdateCheck(true);
    spy.setEnableJarSplitting(true);
    spy.setJarSplittingExcludes("suffix1,suffix2");
    spy.setCompileEncoding("UTF8");
    spy.setDeleteJsps(true);
    spy.setEnableJarClasses(true);
    spy.setDisableJarJsps(true);
    spy.setRuntime("java");

    SpyVerifier.newVerifier(spy).verifyDeclaredSetters();

    List<String> expected =
        ImmutableList.of(
            "--enable_quickstart",
            "--disable_update_check",
            "--enable_jar_splitting",
            "--jar_splitting_excludes=suffix1,suffix2",
            "--compile_encoding=UTF8",
            "--delete_jsps",
            "--enable_jar_classes",
            "--disable_jar_jsps",
            "--allow_any_runtime",
            "--runtime=java",
            "stage",
            source.toPath().toString(),
            destination.toPath().toString());

    staging.stageStandard(spy);

    verify(appCfgRunner, times(1)).run(eq(expected));
    SpyVerifier.newVerifier(spy)
        .verifyDeclaredGetters(
            ImmutableMap.<String, Integer>of(
                "getRuntime", 4,
                "getSourceDirectory", 3,
                "getDockerfile", 2,
                "getStagingDirectory", 3));
  }

  @Test
  public void testCheckFlags_booleanFlags()
      throws AppEngineException, ProcessHandlerException, IOException {
    configuration.setEnableQuickstart(false);
    configuration.setDisableUpdateCheck(false);
    configuration.setEnableJarSplitting(false);
    configuration.setDeleteJsps(false);
    configuration.setEnableJarClasses(false);
    configuration.setDisableJarJsps(false);

    List<String> expected =
        ImmutableList.of("stage", source.toPath().toString(), destination.toPath().toString());

    staging.stageStandard(configuration);

    verify(appCfgRunner, times(1)).run(eq(expected));
  }

  @Test
  public void testCheckFlags_noFlags()
      throws AppEngineException, ProcessHandlerException, IOException {

    List<String> expected =
        ImmutableList.of("stage", source.toPath().toString(), destination.toPath().toString());

    staging.stageStandard(configuration);

    verify(appCfgRunner, times(1)).run(eq(expected));
  }
}
