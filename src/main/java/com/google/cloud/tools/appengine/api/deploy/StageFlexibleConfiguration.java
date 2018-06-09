/*
 * Copyright 2017 Google Inc.
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

package com.google.cloud.tools.appengine.api.deploy;

import com.google.common.base.Preconditions;
import java.io.File;
import javax.annotation.Nullable;

/** Configuration for {@link AppEngineFlexibleStaging#stageFlexible(StageFlexibleConfiguration)}. */
public class StageFlexibleConfiguration {

  private File appEngineDirectory;
  private File dockerDirectory;
  private File artifact;
  private File stagingDirectory;

  private StageFlexibleConfiguration(
      File appEngineDirectory, File dockerDirectory, File artifact, File stagingDirectory) {
    this.appEngineDirectory = Preconditions.checkNotNull(appEngineDirectory);
    this.dockerDirectory = Preconditions.checkNotNull(dockerDirectory);
    this.artifact = Preconditions.checkNotNull(artifact);
    this.stagingDirectory = Preconditions.checkNotNull(stagingDirectory);
  }

  /** Directory containing {@code app.yaml}. */
  public File getAppEngineDirectory() {
    return appEngineDirectory;
  }

  /** Directory containing {@code Dockerfile} and other resources used by it. */
  public File getDockerDirectory() {
    return dockerDirectory;
  }

  /** Artifact to deploy such as WAR or JAR. */
  public File getArtifact() {
    return artifact;
  }

  /**
   * Directory where {@code app.yaml}, files in docker directory, and the artifact to deploy will be
   * copied for deploying.
   */
  public File getStagingDirectory() {
    return stagingDirectory;
  }

  public static class Builder {

    @Nullable private File appEngineDirectory;
    @Nullable private File dockerDirectory;
    @Nullable private File artifact;
    @Nullable private File stagingDirectory;

    public Builder setAppEngineDirectory(File appEngineDirectory) {
      this.appEngineDirectory = Preconditions.checkNotNull(appEngineDirectory);
      return this;
    }

    public Builder setDockerDirectory(File dockerDirectory) {
      this.dockerDirectory = Preconditions.checkNotNull(dockerDirectory);
      return this;
    }

    public Builder setArtifact(File artifact) {
      this.artifact = Preconditions.checkNotNull(artifact);
      return this;
    }

    public Builder setStagingDirectory(File stagingDirectory) {
      this.stagingDirectory = Preconditions.checkNotNull(stagingDirectory);
      return this;
    }

    /**
     * Returns a fully configured StageFlexibleConfiguration object.
     *
     * @throws NullPointerException if any of the fields have not been set
     */
    public StageFlexibleConfiguration build() {
      if (appEngineDirectory == null
          || dockerDirectory == null
          || artifact == null
          || stagingDirectory == null) {
        throw new NullPointerException("Incomplete configuration");
      }
      return new StageFlexibleConfiguration(
          appEngineDirectory, dockerDirectory, artifact, stagingDirectory);
    }
  }
}
