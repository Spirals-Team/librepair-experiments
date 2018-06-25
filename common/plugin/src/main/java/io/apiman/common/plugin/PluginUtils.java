/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.common.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Some generally useful static methods.
 *
 * @author eric.wittmann@redhat.com
 */
public class PluginUtils {

    public static final String PLUGIN_SPEC_PATH = "META-INF/apiman/plugin.json"; //$NON-NLS-1$
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Set<URI> MAVEN_REPOSITORIES = new HashSet<>();
    static {
        try {
            MAVEN_REPOSITORIES.add(new URI("https://repo1.maven.org/maven2/")); //$NON-NLS-1$
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private PluginUtils() {
    }

    /**
     * @return a set of default maven repositories to search for plugins
     */
    public static Set<URI> getDefaultMavenRepositories() {
        return MAVEN_REPOSITORIES;
    }

    /**
     * Returns the relative path (within a plugin registry's plugins directory) where
     * the plugin with the given coordinates can be found.  For example, if the plugin
     * coordinate are "io.apiman.sample:sample-plugin:1.0.1:classifier:war" then the
     * path will be:
     *
     * io.apiman.sample/sample-plugin/1.0.1/sample-plugin-classifier.war/
     *
     * @param coordinates the coordinates
     * @return plugin's relative path
     */
    public static String getPluginRelativePath(PluginCoordinates coordinates) {
        StringBuilder pluginRelativePath = new StringBuilder();
        pluginRelativePath.append(coordinates.getGroupId());
        pluginRelativePath.append("/"); //$NON-NLS-1$
        pluginRelativePath.append(coordinates.getArtifactId());
        pluginRelativePath.append("/"); //$NON-NLS-1$
        pluginRelativePath.append(coordinates.getVersion());
        pluginRelativePath.append("/"); //$NON-NLS-1$
        pluginRelativePath.append(coordinates.getArtifactId());
        if (coordinates.getClassifier() != null) {
            pluginRelativePath.append("-").append(coordinates.getClassifier()); //$NON-NLS-1$
        }
        pluginRelativePath.append("."); //$NON-NLS-1$
        pluginRelativePath.append(coordinates.getType());
        return pluginRelativePath.toString();
    }

    /**
     * Reads a plugin spec file and returns a {@link PluginSpec}.
     * @param pluginSpec the plugin spec
     * @throws IOException when an unhandled exception occurs
     * @return plugin's specification
     */
    public static PluginSpec readPluginSpecFile(URL pluginSpec) throws IOException {
        return (PluginSpec) mapper.reader(PluginSpec.class).readValue(pluginSpec);
    }

    /**
     * Gets the user's local m2 directory or null if not found.
     *
     * @return user's M2 repo
     */
    public static File getUserM2Repository() {
		// if there is m2override system propery, use it.
        String m2Override = System.getProperty("apiman.gateway.m2-repository-path"); //$NON-NLS-1$
        if (m2Override != null) {
            return new File(m2Override).getAbsoluteFile();
        }
		
        String userHome = System.getProperty("user.home"); //$NON-NLS-1$
        if (userHome != null) {
            File userHomeDir = new File(userHome);
            if (userHomeDir.isDirectory()) {
                File m2Dir = new File(userHome, ".m2/repository"); //$NON-NLS-1$
                if (m2Dir.isDirectory()) {
                    return m2Dir;
                }
            }
        }
        return null;
    }

    /**
     * Find the plugin artifact in the local .m2 directory.
     * @param m2Dir the maven m2 directory
     * @param coordinates the coordinates
     * @return the M2 path
     */
    public static File getM2Path(File m2Dir, PluginCoordinates coordinates) {
        String artifactSubPath = getMavenPath(coordinates);
        return new File(m2Dir, artifactSubPath);
    }

    /**
     * Calculates the relative path of the artifact from the given coordinates.
     * @param coordinates the coordinates
     * @return the maven path
     */
    public static String getMavenPath(PluginCoordinates coordinates) {
        StringBuilder artifactSubPath = new StringBuilder();
        artifactSubPath.append(coordinates.getGroupId().replace('.', '/'));
        artifactSubPath.append('/');
        artifactSubPath.append(coordinates.getArtifactId());
        artifactSubPath.append('/');
        artifactSubPath.append(coordinates.getVersion());
        artifactSubPath.append('/');
        artifactSubPath.append(coordinates.getArtifactId());
        artifactSubPath.append('-');
        artifactSubPath.append(coordinates.getVersion());
        if (coordinates.getClassifier() != null) {
            artifactSubPath.append('-');
            artifactSubPath.append(coordinates.getClassifier());
        }
        artifactSubPath.append('.');
        artifactSubPath.append(coordinates.getType());
        return artifactSubPath.toString();
    }

    /**
     * Returns true if the plugin identified by the given coordinates is a
     * snapshot plugin.
     * @param coordinates the plugin coordinates
     * @return true if the given plugin is a snapshot
     */
    public static boolean isSnapshot(PluginCoordinates coordinates) {
        return coordinates.getVersion().endsWith("-SNAPSHOT"); //$NON-NLS-1$
    }

}
