/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jooby.assets;

import java.util.List;

import com.google.common.base.CaseFormat;
import com.typesafe.config.Config;

/**
 * <h2>asset aggregator</h2>
 * <p>
 * Contributes new or dynamically generated content to a <code>fileset</code>. Content generated by
 * an aggregator might be processed by an {@link AssetProcessor}.
 * </p>
 *
 * <h3>how to use it?</h3>
 * <p>
 * First thing to do is to add the dependency:
 * </p>
 * <pre>
 *   &lt;dependency&gt;
 *     &lt;groupId&gt;org.jooby&lt;/groupId&gt;
 *     &lt;artifactId&gt;jooby-assets-dr-svg-sprites&lt;/artifactId&gt;
 *     &lt;scope&gt;provided&lt;/scope&gt;
 *   &lt;/dependency&gt;
 * </pre>
 *
 * <p>
 * Did you see the <strong>provided</strong> scope? We just need the aggregator for development,
 * because assets are processed at runtime. For <code>prod</code>, assets are processed at
 * built-time via Maven/Gradle plugin, so we don't need it. This also, helps to keep our
 * dependencies and the jar size small.
 * </p>
 *
 * <p>
 * Now we have the dependency all we have to do is to add the <code>svg-sprites</code> aggregator to
 * a fileset:
 * </p>
 *
 * <pre>
 * assets {
 *   fileset {
 *     home: [
 *       // 1) Add the aggregator to a fileset
 *       svg-sprites,
 *       css/style.css,
 *       js/app.js
 *     ]
 *   }
 *
 *   svg-sprites {
 *     // 2) The `css/sprite.css` file is part of the `home` fileset.
 *     spritePath: "css/sprite.css"
 *
 *     spriteElementPath: "images/svg",
 *   }
 * }
 * </pre>
 *
 * <p>
 * Here for example, the <code>svg-sprites</code> aggregator contributes the
 * <code>css/sprite.css</code> file to the <code>home</code> fileset. The fileset then looks like:
 * </p>
 *
 * <pre>
 * assets {
 *   fileset {
 *     home: [
 *       css/sprite.css,
 *       css/style.css,
 *       js/app.js
 *     ]
 *   }
 * }
 * </pre>
 * <p>
 * Replaces the aggregator name with one or more files from {@link #fileset()} method.
 * </p>
 *
 * @author edgar
 * @since 1.0.0
 */
public abstract class AssetAggregator extends AssetOptions {

  {
    set("basedir", "public");
  }

  /**
   * @return Aggregator's name.
   */
  public String name() {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, getClass().getSimpleName());
  }

  @Override
  public AssetAggregator set(final String name, final Object value) {
    super.set(name, value);
    return this;
  }

  @Override
  public AssetAggregator set(final Config options) {
    super.set(options);
    return this;
  }

  /**
   * @return The list of generated files contributed by this aggregator.
   */
  public abstract List<String> fileset();

  /**
   * Run the aggregator and generates some output.
   *
   * @param conf Assets conf.
   * @throws Exception If something goes wrong.
   */
  public abstract void run(Config conf) throws Exception;

  @Override
  public String toString() {
    return name();
  }
}
