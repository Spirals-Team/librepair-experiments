package com.conveyal.taui.controllers;

import com.conveyal.r5.util.ExceptionUtils;
import com.conveyal.taui.AnalysisServerException;
import com.conveyal.taui.ThreadPool;
import com.conveyal.taui.grids.SeamlessCensusGridExtractor;
import com.conveyal.taui.models.Region;
import com.conveyal.taui.persistence.OSMPersistence;
import com.conveyal.taui.persistence.Persistence;
import com.conveyal.taui.util.JsonUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 * Created by matthewc on 7/12/16.
 */
public class RegionController {
    private static final Logger LOG = LoggerFactory.getLogger(RegionController.class);
    private static final FileItemFactory fileItemFactory = new DiskFileItemFactory();

    public static Region getRegion (Request req, Response res) {
        return Persistence.regions.findByIdFromRequestIfPermitted(req);
    }

    public static Collection<Region> getAllRegions (Request req, Response res) {
        return Persistence.regions.findAllForRequest(req);
    }

    public static Map<String, List<FileItem>> getFilesFromRequest (Request req) {
        try {
            ServletFileUpload sfu = new ServletFileUpload(fileItemFactory);
            return sfu.parseParameterMap(req.raw());
        } catch (FileUploadException e) {
            throw AnalysisServerException.fileUpload("Error uploading files. " + ExceptionUtils.asString(e));
        }
    }

    public static Region getRegionFromFiles(Map<String, List<FileItem>> files) {
        try {
            InputStream is = files.get("region").get(0).getInputStream();
            final Region region = JsonUtil.objectMapper.readValue(is, Region.class);
            is.close();

            return region;
        } catch (IOException e) {
            throw AnalysisServerException.badRequest("Error parsing region. " + ExceptionUtils.asString(e));
        }
    }

    public static void fetchOsmAndCensusDataInThread (String _id, Map<String, List<FileItem>> files, boolean newBounds) {
        boolean customOsm = files.containsKey("customOpenStreetMapData");
        ThreadPool.run(() -> {
            final Region region = Persistence.regions.get(_id);

            try {
                if (customOsm) {
                    region.customOsm = true;
                    File customOsmData = File.createTempFile("uploaded-osm", ".pbf");
                    files.get("customOpenStreetMapData").get(0).write(customOsmData);
                    OSMPersistence.cache.put(region._id, customOsmData);
                    customOsmData.delete();
                } else {
                    region.statusCode = Region.StatusCode.ERROR;
                    region.statusMessage = "An OSM network is required but was not uploaded.";
                    Persistence.regions.put(region);
                    //  return;
                }

                if (newBounds) {

                    // Download census data
                    region.statusCode = Region.StatusCode.DOWNLOADING_CENSUS;
                    Persistence.regions.put(region); // save the status
                    region.opportunityDatasets = SeamlessCensusGridExtractor.retrieveAndExtractCensusDataForBounds(region.bounds, region._id);
                }

                region.statusCode = Region.StatusCode.DONE;
                Persistence.regions.put(region);
            } catch (Exception e) {
                region.statusCode = Region.StatusCode.ERROR;
                region.statusMessage = "Error while fetching data. " + ExceptionUtils.asString(e);
                Persistence.regions.put(region);

                LOG.error("Error while fetching OSM. " + ExceptionUtils.asString(e));
                e.printStackTrace();
            }
        });
    }

    public static Region create(Request req, Response res) {
        final Map<String, List<FileItem>> files = getFilesFromRequest(req);
        Region region = getRegionFromFiles(files);

        // Set the `accessGroup` and `createdBy`
        region.accessGroup = req.attribute("accessGroup");
        region.createdBy = req.attribute("email");

        // Set the status to Started
        region.statusCode = Region.StatusCode.STARTED;

        // Create the region
        Persistence.regions.create(region);

        // Fetch data and update the statuses separately
        fetchOsmAndCensusDataInThread(region._id, files, true);

        return region;
    }

    public static Region update(Request req, Response res) {
        final Region existingRegion = Persistence.regions.findByIdFromRequestIfPermitted(req);
        final Map<String, List<FileItem>> files = getFilesFromRequest(req);
        Region region = getRegionFromFiles(files);

        boolean boundsChanged = !existingRegion.bounds.equals(region.bounds, 1e-6);
        boolean customOSM = files.containsKey("customOpenStreetMapData");

        // Set updatedBy
        region.updatedBy = req.attribute("email");
        // And update the `nonce` and `updatedAt`
        Persistence.regions.put(region);

        if (boundsChanged || customOSM) {
            region.statusCode = Region.StatusCode.STARTED;
            Persistence.regions.put(region);
            // Fetch data and update the statuses separately
            fetchOsmAndCensusDataInThread(region._id, files, boundsChanged);
        }

        return region;
    }

    public static Region deleteRegion (Request req, Response res) {
        return Persistence.regions.removeIfPermitted(req.params("_id"), req.attribute("accessGroup"));
    }

    public static void register () {
        get("/api/region", RegionController::getAllRegions, JsonUtil.objectMapper::writeValueAsString);
        get("/api/region/:_id", RegionController::getRegion, JsonUtil.objectMapper::writeValueAsString);
        get("/api/region/:region/projects", ProjectController::getAllProjects, JsonUtil.objectMapper::writeValueAsString);
        get("/api/region/:region/bookmark", BookmarkController::getAllBookmarks, JsonUtil.objectMapper::writeValueAsString);
        post("/api/region/:region/bookmark", BookmarkController::createBookmark, JsonUtil.objectMapper::writeValueAsString);
        post("/api/region", RegionController::create, JsonUtil.objectMapper::writeValueAsString);
        put("/api/region/:_id", RegionController::update, JsonUtil.objectMapper::writeValueAsString);
        delete("/api/region/:_id", RegionController::deleteRegion, JsonUtil.objectMapper::writeValueAsString);
    }
}
