package org.esa.s2tbx.dataio.s2.l2a;

import org.esa.s2tbx.dataio.VirtualPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by obarrile on 14/06/2016.
 */
public class L2aUtils {

    public static boolean checkGranuleSpecificFolder(VirtualPath pathGranule, String specificFolder) {

        if (specificFolder.equals("Multi"))
            return true;
        VirtualPath rootPath = pathGranule.getParent();
        VirtualPath imgFolder = rootPath.resolve("IMG_DATA");
        VirtualPath[] paths;
        try {
            paths = imgFolder.listPaths();
        } catch (IOException e) {
            paths = null;
        }

        if (paths != null) {
            for (VirtualPath imgData : paths) {
                if (imgData.isDirectory()) {
                    if (imgData.getFileName().toString().equals("R" + specificFolder)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkMetadataSpecificFolder(VirtualPath pathMetadata, String specificFolder) {

        if (specificFolder.equals("Multi"))
            return true;
        VirtualPath rootPath = pathMetadata.getParent();
        VirtualPath granuleFolder = rootPath.resolve("GRANULE");
        VirtualPath[] paths;
        try {
            paths = granuleFolder.listPaths();
        } catch (IOException e) {
            paths = null;
        }

        if (paths != null) {
            for (VirtualPath granule : paths) {
                if (granule.isDirectory()) {

                    VirtualPath internalGranuleFolder = granule.resolve("IMG_DATA");
                    VirtualPath[] files2 ;
                    try {
                        files2 = internalGranuleFolder.listPaths();
                    } catch (IOException e) {
                        files2 = null;
                    }
                    if (files2 != null) {
                        for (VirtualPath imgData : files2) {
                            if (imgData.isDirectory()) {
                                if (imgData.getFileName().toString().equals("R" + specificFolder)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
