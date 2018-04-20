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

package com.google.cloud.tools.appengine.api.versions;

import com.google.cloud.tools.appengine.api.DefaultConfiguration;
import java.util.Collection;

/** Plain Java bean implementation of {@link VersionsSelectionConfiguration}. */
public class DefaultVersionsSelectionConfiguration extends DefaultConfiguration
    implements VersionsSelectionConfiguration {

  private Collection<String> versions;
  private String service;

  @Override
  public Collection<String> getVersions() {
    return versions;
  }

  public void setVersions(Collection<String> versions) {
    this.versions = versions;
  }

  @Override
  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }
}
