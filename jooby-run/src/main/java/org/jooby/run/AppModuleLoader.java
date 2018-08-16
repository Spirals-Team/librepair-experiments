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
package org.jooby.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;

import org.jboss.modules.DependencySpec;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoadException;
import org.jboss.modules.ModuleLoader;
import org.jboss.modules.ModuleSpec;
import org.jboss.modules.ResourceLoaderSpec;
import org.jboss.modules.ResourceLoaders;

public class AppModuleLoader extends ModuleLoader {

  private Map<ModuleIdentifier, ModuleSpec> modules;

  public AppModuleLoader(final Map<ModuleIdentifier, ModuleSpec> modules) {
    this.modules = modules;
  }

  @Override
  protected ModuleSpec findModule(final ModuleIdentifier moduleId) throws ModuleLoadException {
    ModuleSpec spec = modules.get(moduleId);
    return spec == null ? super.findModule(moduleId) : spec;
  }

  public void unload(final Module module) {
    super.unloadModuleLocal(module);
  }

  /**
   * Build a flat jboss module, with some minor exceptions (like j2v8).
   *
   * @param name module name.
   * @param cp Classpath.
   * @return A new app module loader.
   * @throws Exception If something goes wrong.
   */
  public static AppModuleLoader build(final String name, final File... cp) throws Exception {
    Map<ModuleIdentifier, ModuleSpec> modules = newModule(name, 0, "", cp);
    return new AppModuleLoader(modules);
  }

  private static Map<ModuleIdentifier, ModuleSpec> newModule(final String name,
      final int level, final String prefix, final File... cp) throws Exception {
    Map<ModuleIdentifier, ModuleSpec> modules = new HashMap<>();

    String mId = name.replace(".jar", "");
    ModuleSpec.Builder builder = ModuleSpec.build(ModuleIdentifier.fromString(mId));
    if (level == 0) {
      String classurl = AppModuleLoader.class
          .getResource("/" + Main.JOOBY_REF.replace(".", "/") + ".class")
          .toString();
      String jartoken = ".jar!";
      File jar = new File(URI
          .create(classurl.substring(0, classurl.indexOf(jartoken) + jartoken.length() - 1)
              .replace("jar:", "")));
      Main.debug("loading hack: %s?%s", jar, jar.exists());
      builder.addResourceRoot(ResourceLoaderSpec
          .createResourceLoaderSpec(ResourceLoaders
              .createJarResourceLoader("jooby-run", new JarFile(jar))));
    }

    int l = (prefix.length() + mId.length() + level);
    Main.debug("%1$" + l + "s", prefix + mId);
    for (File file : cp) {
      if (file.getName().endsWith(".pom")) {
        // skip pom(s)
        continue;
      }
      if (!file.exists()) {
        // skip missing file/dir
        continue;
      }
      String fname = "└── " + file.getAbsolutePath();
      if (file.getName().startsWith("j2v8") && !name.equals(file.getName())) {
        ModuleSpec dependency = newModule(file.getName(), level + 2, "└── ", file)
            .values()
            .iterator()
            .next();
        builder.addDependency(
            DependencySpec.createModuleDependencySpec(dependency.getModuleIdentifier()));
        modules.put(dependency.getModuleIdentifier(), dependency);
      } else {
        Main.debug("%1$" + (fname.length() + level + 2) + "s", fname);
        if (file.getName().endsWith(".jar")) {
          builder.addResourceRoot(ResourceLoaderSpec
              .createResourceLoaderSpec(ResourceLoaders
                  .createJarResourceLoader(file.getName(), new JarFile(file))));
        } else {
          builder.addResourceRoot(ResourceLoaderSpec
              .createResourceLoaderSpec(ResourceLoaders
                  .createFileResourceLoader(file.getName(), file)));
        }
      }
    }
    Set<String> sysPaths = sysPaths();

    Main.trace("system packages:");
    sysPaths.forEach(p -> Main.trace("  %s", p));

    builder.addDependency(DependencySpec.createSystemDependencySpec(sysPaths));
    builder.addDependency(DependencySpec.createLocalDependencySpec());

    ModuleSpec module = builder.create();
    modules.put(module.getModuleIdentifier(), builder.create());
    return modules;
  }

  @SuppressWarnings({"rawtypes", "unchecked" })
  private static Set<String> jdkPaths() throws Exception {
    Class jdkPath = AppModuleLoader.class.getClassLoader().loadClass("org.jboss.modules.JDKPaths");
    Field field = jdkPath.getDeclaredField("JDK");
    field.setAccessible(true);
    return (Set<String>) field.get(null);
  }

  private static Set<String> sysPaths() throws Exception {
    Set<String> pkgs = new LinkedHashSet<>();

    pkgs.addAll(jdkPaths());
    pkgs.addAll(pkgs(new InputStreamReader(Main.class.getResourceAsStream("pkgs"))));

    /**
     * Hack to let users to configure system packages, javax.transaction cause issues with
     * hibernate.
     */
    pkgs.addAll(pkgs(Paths.get("src", "etc", "jboss-modules", "pkgs.includes").toFile()));

    pkgs.removeAll(pkgs(Paths.get("src", "etc", "jboss-modules", "pkgs.excludes").toFile()));
    return pkgs;
  }

  private static Set<String> pkgs(final File file) throws IOException {
    if (file.exists()) {
      return pkgs(new FileReader(file));
    }
    return new LinkedHashSet<>();
  }

  private static Set<String> pkgs(final Reader reader) throws IOException {
    try (BufferedReader in = new BufferedReader(reader)) {
      Set<String> pkgs = new LinkedHashSet<>();
      String line = in.readLine();
      while (line != null) {
        pkgs.add(line.trim());
        line = in.readLine();
      }
      return pkgs;
    }
  }

  public static void main(final String[] args) throws MalformedURLException {
    URI jaruri = URI.create(
        "jar:file:/Users/edgar/.m2/repository/org/jooby/jooby-run/1.0.4-SNAPSHOT/jooby-run-1.0.4-SNAPSHOT.jar!/org/jooby/internal/run__/JoobyRef.class");
    System.out.println(jaruri.toURL().toExternalForm());
  }
}
