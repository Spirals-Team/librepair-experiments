/*
 * Copyright (C) 2014-2015 CS-SI (foss-contact@thor.si.c-s.fr)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see http://www.gnu.org/licenses/
 */

package org.esa.s2tbx.dataio.gdal;

import org.esa.snap.core.util.StringUtils;
import org.esa.snap.core.util.io.FileUtils;
import org.esa.snap.runtime.Config;
import org.esa.snap.utils.FileHelper;
import org.esa.snap.utils.NativeLibraryUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.Stream;

import static org.apache.commons.lang.SystemUtils.*;

/**
 * Activator class for deploying GDAL binaries to the aux data dir
 *
 * @author Cosmin Cara
 */
public class GDALInstaller {
    private static final Logger logger = Logger.getLogger(GDALInstaller.class.getName());

    private static final String SRC_PATH = "auxdata/gdal";

    public GDALInstaller() {
    }

    public final Path copyDistribution(Path gdalApplicationFolderPath, OSCategory osCategory) throws IOException {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Copy the GDAL distribution to folder '" + gdalApplicationFolderPath.toString() + "'.");
        }

        Config config = Config.instance("s2tbx");
        config.load();
        Preferences preferences = config.preferences();
        String preferencesKey = "gdal.installer";
        String moduleVersion = getModuleSpecificationVersion();

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Check the GDAL distribution folder from the local disk.");
        }

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "The module version is '" + moduleVersion + "'.");
        }

        boolean canCopyGDALDistribution = true;
        if (Files.exists(gdalApplicationFolderPath)) {
            // the the GDAL distribution folder already exists on the local disk
            String savedVersion = preferences.get(preferencesKey, null);

            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "The saved GDAL distribution folder version is '" + savedVersion + "'.");
            }

            if (!StringUtils.isNullOrEmpty(savedVersion)) {
                if (compareVersions(savedVersion, moduleVersion) >= 0) {
                    canCopyGDALDistribution = false;
                }
            }

            if (canCopyGDALDistribution) {
                // different module versions and delete the library saved on the local disk
                boolean deleted = FileUtils.deleteTree(gdalApplicationFolderPath.toFile());
                if (!deleted) {
                    throw new IllegalArgumentException("Failed to delete the GDAL distribution folder '" + gdalApplicationFolderPath.toString() + "'.");
                }
            }
        }

        if (canCopyGDALDistribution) {
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "Create the folder '" + gdalApplicationFolderPath.toString() + "' to copy the GDAL distribution.");
            }

            Files.createDirectories(gdalApplicationFolderPath);
        }

        String zipArchivePath = osCategory.getDirectory() + "/" + osCategory.getZipFileName();
        Path zipFilePathOnLocalDisk = gdalApplicationFolderPath.resolve(zipArchivePath);
        Path gdalDistributionRootFolderPath = zipFilePathOnLocalDisk.getParent();

        fixUpPermissions(gdalApplicationFolderPath);

        if (Files.exists(gdalDistributionRootFolderPath)) {
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "The distribution root folder '" + gdalDistributionRootFolderPath.toString() + "' exists on the local disk.");
            }
        } else {
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "Create the distribution root folder '" + gdalDistributionRootFolderPath.toString() + "'.");
            }

            Files.createDirectories(gdalDistributionRootFolderPath);
            try {
                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, "Copy the zip archive to folder '" + zipFilePathOnLocalDisk.toString() + "'.");
                }
                String zipFilePathFromSources = SRC_PATH + "/" + zipArchivePath;
                URL zipFileURLFromSources = getClass().getClassLoader().getResource(zipFilePathFromSources);
                FileHelper.copyFile(zipFileURLFromSources, zipFilePathOnLocalDisk);

                if (logger.isLoggable(Level.FINE)) {
                    logger.log(Level.FINE, "Decompress the zip archive to folder '" + gdalDistributionRootFolderPath.toString() + "'.");
                }
                FileHelper.unzip(zipFilePathOnLocalDisk, gdalDistributionRootFolderPath, true);
            } finally {
                try {
                    Files.deleteIfExists(zipFilePathOnLocalDisk);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "GDAL configuration error: failed to delete the zip archive after decompression.", e);
                }
            }
        }

        String libraryFileNameFromSources = System.mapLibraryName(osCategory.getEnvironmentVariablesFileName());
        String libraryFileName = System.mapLibraryName("environment-variables");
        Path libraryFilePath = gdalApplicationFolderPath.resolve(libraryFileName);

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "The library file path on the local disk is '" + libraryFilePath.toString() + "' and the library file name from sources is '" + libraryFileNameFromSources + "'.");
        }

        if (canCopyGDALDistribution) {
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "Copy the library file.");
            }

            String libraryFilePathFromSources = SRC_PATH + "/" + libraryFileNameFromSources;
            URL libraryFileURLFromSources = getClass().getClassLoader().getResource(libraryFilePathFromSources);
            FileHelper.copyFile(libraryFileURLFromSources, libraryFilePath);
        }

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Register the native paths for folder '" + libraryFilePath.getParent()+"'.");
        }
        NativeLibraryUtils.registerNativePaths(libraryFilePath.getParent());

        if (canCopyGDALDistribution) {
            preferences.put(preferencesKey, moduleVersion);
            try {
                preferences.flush();
            } catch (BackingStoreException exception) {
                // ignore exception
            }
        }

        return gdalDistributionRootFolderPath;
    }

    private String getModuleSpecificationVersion() throws IOException {
        String manifestFilePath = "/META-INF/MANIFEST.MF";
        Class<?> clazz = getClass();
        String className = clazz.getSimpleName() + ".class";
        String classPath = clazz.getResource(className).toString();
        String manifestPath = null;
        if (classPath.startsWith("jar")) {
            manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + manifestFilePath;
        } else {
            // class not from jar archive
            String relativePath = clazz.getName().replace('.', File.separatorChar) + ".class";
            String classFolder = classPath.substring(0, classPath.length() - relativePath.length() - 1);
            manifestPath = classFolder + manifestFilePath;
        }
        Manifest manifest = new Manifest(new URL(manifestPath).openStream());
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue("OpenIDE-Module-Specification-Version");
    }

    private static void fixUpPermissions(Path destPath) throws IOException {
        Stream<Path> files = Files.list(destPath);
        files.forEach(path -> {
            if (Files.isDirectory(path)) {
                try {
                    fixUpPermissions(path);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "GDAL configuration error: failed to fix permissions on " + path, e);
                }
            }
            else {
                setExecutablePermissions(path);
            }
        });
    }

    private static void setExecutablePermissions(Path executablePathName) {
        if (IS_OS_UNIX) {
            Set<PosixFilePermission> permissions = new HashSet<>(Arrays.asList(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE,
                    PosixFilePermission.GROUP_READ,
                    PosixFilePermission.GROUP_EXECUTE,
                    PosixFilePermission.OTHERS_READ,
                    PosixFilePermission.OTHERS_EXECUTE));
            try {
                Files.setPosixFilePermissions(executablePathName, permissions);
            } catch (IOException e) {
                // can't set the permissions for this file, eg. the file was installed as root
                // send a warning message, user will have to do that by hand.
                logger.log(Level.SEVERE, "Can't set execution permissions for executable " + executablePathName.toString() +
                        ". If required, please ask an authorised user to make the file executable.", e);
            }
        }
    }

    private static int compareVersions(String currentModuleVersion, String savedModuleVersion) {
        int[] moduleVersionFragments = parseVersion(currentModuleVersion);
        int[] savedVersionFragments = parseVersion(savedModuleVersion);

        int max = Math.max(moduleVersionFragments.length, savedVersionFragments.length);
        for(int i = 0; i < max; ++i) {
            int d1 = (i < moduleVersionFragments.length) ? moduleVersionFragments[i] : 0;
            int d2 = (i < savedVersionFragments.length) ? savedVersionFragments[i] : 0;
            if (d1 != d2) {
                return d1 - d2;
            }
        }
        return 0;
    }

    private static int[] parseVersion(String version) throws NumberFormatException {
        StringTokenizer tok = new StringTokenizer(version, ".", true);
        int len = tok.countTokens();
        if (len % 2 == 0) {
            throw new NumberFormatException("Even number of pieces in a spec version: `" + version + "\'");
        } else {
            int[] digits = new int[len / 2 + 1];
            int index = 0;
            boolean expectingNumber = true;
            while (tok.hasMoreTokens()) {
                String fragment = tok.nextToken();
                if (expectingNumber) {
                    expectingNumber = false;
                    int piece = Integer.parseInt(fragment);
                    if (piece < 0) {
                        throw new NumberFormatException("Spec version component '" + piece + "' is negative.");
                    }
                    digits[index++] = piece;
                } else {
                    if(!".".equals(fragment)) {
                        throw new NumberFormatException("Expected dot in version '" + version + "'.");
                    }
                    expectingNumber = true;
                }
            }

            return digits;
        }
    }
}
