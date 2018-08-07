/*
 * Copyright (c) 2018-2018 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openpojo.dns.config.props;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.openpojo.dns.config.props.FileStreamer.getAsStream;

/**
 * @author oshoukry
 */
public class PropertiesLoader {
  private final String fileName;
  private Properties properties;
  private AtomicBoolean loaded = new AtomicBoolean(false);

  public PropertiesLoader(String fileName) {
    this.fileName = fileName;
  }

  public synchronized void load() {
    if (!loaded.get()) {
      properties = new Properties();

      try (InputStream asStream = getAsStream(fileName)) {
        properties.load(asStream);
      } catch (Exception ignored) { }
    }
    loaded.set(true);
  }

  public boolean exists() {
    return FileStreamer.exists(fileName);
  }

  public Map<String, String> getAllProperties() {
    Map<String, String> propertiesMap = new HashMap<>();
    for (String entry : properties.stringPropertyNames())
      propertiesMap.put(entry, properties.getProperty(entry));
    return propertiesMap;
  }

  public String get(String key) {
    return properties.getProperty(key);
  }

}
