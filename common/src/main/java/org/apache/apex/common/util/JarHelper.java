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
package org.apache.apex.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

@InterfaceAudience.Public
@InterfaceStability.Evolving
/**
 * @since 3.6.0
 */
public class JarHelper
{
  private static final Logger logger = LoggerFactory.getLogger(JarHelper.class);

  private final Map<URL, String> sourceToJar = new HashMap<>();

  public static String createJar(String prefix, File dir, boolean deleteOnExit) throws IOException
  {
    if (!dir.exists() || !dir.isDirectory()) {
      throw new IllegalArgumentException(String.format("dir %s must be an existing directory.", dir));
    }
    File temp = File.createTempFile(prefix, ".jar");
    if (deleteOnExit) {
      temp.deleteOnExit();
    }
    new JarCreator(temp).createJar(dir);
    return temp.getAbsolutePath();
  }

  public String getJar(Class<?> jarClass)
  {
    List<String> list = getJars(jarClass, true, false);
    if (list == null) {
      return null;
    } else  if (list.isEmpty()) {
      throw new AssertionError("Cannot resolve jar file for " + jarClass);
    }
    return list.get(0);
  }

  /**
   * Returns a full path to the jar-file that contains the given class and all full paths to dependent jar-files
   * that are defined in the property "apex-dependencies" of the manifest of the root jar-file.
   * If the class is an independent file the method makes jar file from the folder that contains the class
   * @param classPath Class path
   * @param makeJarFromFolder True if the method should make jar from folder that contains the independent class
   * @param addJarDependencies True if the method should include dependent jar files
   * @return List of names of the jar-files
   */
  public List<String> getJars(String classPath, boolean makeJarFromFolder, boolean addJarDependencies)
  {
    try {
      return getJars(Thread.currentThread()
          .getContextClassLoader().loadClass(classPath), makeJarFromFolder, addJarDependencies);
    } catch (ClassNotFoundException ex) {
      logger.error("Cannot find the class {}", classPath, ex);
      throw new RuntimeException("Cannot find the class " + classPath, ex);
    }
  }

  /**
   * Returns a full path to the jar-file that contains the given class and all full paths to dependent jar-files
   * that are defined in the property "apex-dependencies" of the manifest of the root jar-file.
   * If the class is an independent file the method makes jar file from the folder that contains the class
   * @param clazz Class
   * @param makeJarFromFolder True if the method should make jar from folder that contains the independent class
   * @param addJarDependencies True if the method should include dependent jar files
   * @return List of names of the jar-files
   */
  public List<String> getJars(Class<?> clazz, boolean makeJarFromFolder, boolean addJarDependencies)
  {
    CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
    if (codeSource == null) {
      return null;
    }

    List<String> list = new LinkedList<>();
    URL location  =  codeSource.getLocation();
    String jarPath = sourceToJar.get(location);

    if (jarPath == null) {
      try {
        URLConnection conn = clazz.getResource(clazz.getSimpleName() + ".class").openConnection();

        if (conn instanceof JarURLConnection) {
          jarPath = ((JarURLConnection)conn).getJarFileURL().getFile();
          if (addJarDependencies) {
            getDependentJarsFromManifest((JarURLConnection)conn, jarPath, list);
            // add the location of the jar-file to cache only if the dependent jars were added to the jar list
            sourceToJar.put(location, jarPath);
          }
        } else if (makeJarFromFolder && ("file".equals(location.getProtocol()))) {
          jarPath = createJar("apex-", new File(conn.getURL().getFile()).getParentFile(), false);
          sourceToJar.put(location, jarPath);
        } else {
          logger.warn("The class {} was loaded from incorrect url connection {}", clazz.getCanonicalName(), conn);
          return list;
        }
      } catch (IOException ex) {
        String className = clazz.getCanonicalName();
        logger.error("Cannot resolve jar file for the class {}", className, ex);
        throw new RuntimeException("Cannot resolve jar file for the class " + className, ex);
      }
    }

    list.add(jarPath);
    logger.debug("added sourceLocation {} as {}", location, jarPath);

    return list;
  }

  /**
   * Gets dependent jar-files from manifest
   * @param conn Jar connection
   * @param jarPath path to jar file
   * @param list List of target jar-files
   * @throws IOException
   */
  private void getDependentJarsFromManifest(JarURLConnection conn, String jarPath, List<String> list) throws IOException
  {
    String value = ((JarURLConnection)conn).getMainAttributes().getValue("apex-dependencies");
    if (!StringUtils.isEmpty(value)) {
      String folderPath = new File(jarPath).getParent();
      for (String jarFile : value.split(",")) {
        String file = folderPath + File.separator + jarFile;
        if (new File(file).exists()) {
          list.add(file);
          logger.debug("Jar-file {} was added as a dependent jar", file);
        } else {
          logger.warn("Jar-file {} does not exist", file);
        }
      }
    }
  }

  private static class JarCreator
  {

    private final JarOutputStream jos;

    private JarCreator(File file) throws IOException
    {
      jos = new JarOutputStream(new FileOutputStream(file));
    }

    private void createJar(File dir) throws IOException
    {
      try {
        File manifestFile = new File(dir, JarFile.MANIFEST_NAME);
        if (!manifestFile.exists()) {
          jos.putNextEntry(new JarEntry(JarFile.MANIFEST_NAME));
          new Manifest().write(jos);
          jos.closeEntry();
        } else {
          addEntry(manifestFile, JarFile.MANIFEST_NAME);
        }
        final Path root = dir.toPath();
        Files.walkFileTree(root,
            new SimpleFileVisitor<Path>()
            {
              String relativePath;

              @Override
              public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
              {
                relativePath = root.relativize(dir).toString();
                if (!relativePath.isEmpty()) {
                  if (!relativePath.endsWith("/")) {
                    relativePath += "/";
                  }
                  addEntry(dir.toFile(), relativePath);
                }
                return super.preVisitDirectory(dir, attrs);
              }

              @Override
              public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
              {
                String name = relativePath + file.getFileName().toString();
                if (!JarFile.MANIFEST_NAME.equals(name)) {
                  addEntry(file.toFile(), relativePath + file.getFileName().toString());
                }
                return super.visitFile(file, attrs);
              }

              @Override
              public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
              {
                relativePath = root.relativize(dir.getParent()).toString();
                if (!relativePath.isEmpty() && !relativePath.endsWith("/")) {
                  relativePath += "/";
                }
                return super.postVisitDirectory(dir, exc);
              }
            }
        );
      } finally {
        jos.close();
      }
    }

    private void addEntry(File file, String name) throws IOException
    {
      final JarEntry ze = new JarEntry(name);
      ze.setTime(file.lastModified());
      jos.putNextEntry(ze);
      if (file.isFile()) {
        try (final FileInputStream input = new FileInputStream(file)) {
          IOUtils.copy(input, jos);
        }
      }
      jos.closeEntry();
    }
  }
}
