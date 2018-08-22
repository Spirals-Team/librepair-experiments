/*
 * Copyright (C) 2018 B3Partners B.V.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.b3p.playbase;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.naming.NamingException;
import nl.b3p.loader.jdbc.GeometryJdbcConverter;
import nl.b3p.loader.jdbc.GeometryJdbcConverterFactory;
import nl.b3p.loader.util.DbUtilsGeometryColumnConverter;
import nl.b3p.playbase.db.DB;
import nl.b3p.playbase.entities.Asset;
import nl.b3p.playbase.entities.Project;
import nl.b3p.playbase.entities.Location;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Meine Toonen
 */
public class PlayadvisorExporter {

    private static final Log log = LogFactory.getLog(PlayadvisorExporter.class);
    protected final ResultSetHandler<Project> projectHandler = new BeanHandler(Project.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public PlayadvisorExporter() {
        initLists();
    }

    public void export(List<Location> locations, Connection con, String baseurl, String authkey) {
        for (Location location : locations) {
            try {
                pushLocation(location, con, new HashMap<>(), baseurl, authkey);
            } catch (IOException | SQLException | NamingException ex) {
                log.error("Error exporting to export", ex);
            }
        }
    }

    public String pushLocations(List<Location> locations, String imagepath, String baseurl, String authkey, ImageDownloader downloader) {
        StringBuilder sb = new StringBuilder();
        try (Connection con = DB.getConnection()) {

            downloader.run();
            HashMap<Integer, JSONArray> imagesPerLocation = new HashMap<>();
            for (Location loc : locations) {
                if ((loc.getRemovedfromplayadvisor() == null || !loc.getRemovedfromplayadvisor()) && (loc.getRemovedfromplaymapping() == null || !loc.getRemovedfromplaymapping())) {
                    imagesPerLocation.put(loc.getId(), retrieveImages(loc.getId(), con, imagepath, downloader));
                }
            }
            downloader.stop();

            analyseImagesForDuplicates(locations, imagesPerLocation, con);

            for (Location loc : locations) {
                if ((loc.getRemovedfromplayadvisor() == null || !loc.getRemovedfromplayadvisor()) && (loc.getRemovedfromplaymapping() == null || !loc.getRemovedfromplaymapping())) {
                    try {
                        sb.append(pushLocation(loc, con, imagesPerLocation, baseurl, authkey));
                    } catch (IOException ex) {
                        log.error("Cannot push location to playadvisor: " + ex.getLocalizedMessage());
                        log.debug(ex);
                    }
                }
            }

        } catch (NamingException | SQLException | IOException ex) {
            log.error("Cannot push locations to playadvisor", ex);
        }
        return sb.toString();
    }

    public Map<Integer, JSONArray> analyseImagesForDuplicates(List<Location> locations, Map<Integer, JSONArray> imagesPerLocation, Connection con) throws NamingException, SQLException {

        // hier per locatie kijken of er identieke plaatjes bij zijn
        // zo ja, zet een van de identieke op pa_deleted en 
        //haal ze uit de imagesPerLocation hashmap
        // verwijder ze van OS
        Map<Integer, JSONArray> uniques = new HashMap<>();
        for (Location location : locations) {
            Boolean alreadyChecked = location.getDuplicate_images_checked();
            if (alreadyChecked == null || !alreadyChecked) {
                JSONArray images = imagesPerLocation.get(location.getId());
                JSONArray uniqueArray = removeDuplicates(images, con);
                imagesPerLocation.put(location.getId(), uniqueArray);
                uniques.put(location.getId(), uniqueArray);
                try {
                    int numUpdated = DB.qr().update(con, "UPDATE " + DB.LOCATION_TABLE + " SET duplicate_images_checked = true WHERE id = ?", location.getId());
                    location.setDuplicate_images_checked(true);
                    int a = 0;
                } catch (NamingException | SQLException ex) {
                    log.error("Cannot update location for duplicate_images_checked", ex);
                }
            }
        }
        // zet location op duplicateChecked
        return uniques;

    }

    protected JSONArray removeDuplicates(JSONArray images, Connection con) throws NamingException, SQLException {
        
        Map<Integer, BufferedImage> loadedImages = new HashMap<>();
        Map<Integer, JSONObject> indexedImages = new HashMap<>();
        
        for (Object image : images) {
            JSONObject img = (JSONObject)image;
            Integer id = img.getInt("PlaybaseID");
            BufferedImage ri = open(img);
            if(ri != null){
                loadedImages.put(id, ri);
                indexedImages.put(id, img);
            }
        }
        List<Integer> imgsToRemove = new ArrayList<>();
        double threshold = 5;
        Set<Integer> completedKeys = new HashSet<>();
       
        for (Integer key1 : loadedImages.keySet()) {
            if(completedKeys.contains(key1)){
                continue;
            }
            BufferedImage img1 = loadedImages.get(key1);
            for (Integer key2 : loadedImages.keySet()) {
                if(!key1.equals(key2) && !completedKeys.contains(key2)){
                    BufferedImage img2 = loadedImages.get(key2);
                    double diff = getDifferencePercent(img1, img2);
                    if(diff < threshold){
                        log.error("Difference for " + indexedImages.get(key1).getString("Path") + " - " + indexedImages.get(key2).getString("Path") + ": " + diff);
                        completedKeys.add(key2);
                        imgsToRemove.add(key1);
                    }
                }
            }
        }
        
        for (Integer keyToRemove : imgsToRemove) {
            JSONObject objectToRemove = indexedImages.remove(keyToRemove);
            removeImage(objectToRemove, con);
        }
        JSONArray uniqueImages = new JSONArray();
        for (JSONObject obj : indexedImages.values()) {
            uniqueImages.put(obj);
        }
        return uniqueImages;
    }
    
    private double getDifferencePercent(BufferedImage img1, BufferedImage img2) {
        int width = img1.getWidth();
        int height = img1.getHeight();
        int width2 = img2.getWidth();
        int height2 = img2.getHeight();
        if (width != width2 || height != height2) {
            return 100;
        }

        long diff = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
            }
        }
        long maxDiff = 3L * 255 * width * height;

        return 100.0 * diff / maxDiff;
    }

    private int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = rgb1 & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = rgb2 & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }

    public BufferedImage open(JSONObject obj) {
        BufferedImage renderedImage;
        try {
            String path = obj.getString("Path");
            URI u = new URI(path);
            File f;
            if(u.isAbsolute()){
                f = new File(u);
            }else{
                f = new File(path);
            }
            renderedImage = ImageIO.read(f);
            return renderedImage;
        } catch (IOException | URISyntaxException e) {
            log.error(e, e);
            return null;
        }
    }

    private void removeImage(JSONObject img, Connection con) {
        if(img != null){
            log.info("Removing image: " + img.toString());
            // ze pa_deleted op true
            try {
                int numUpdated = DB.qr().update(con, "UPDATE " + DB.IMAGES_TABLE + " SET pa_deleted = true WHERE id = ?", img.getInt("PlaybaseID"));
            } catch (NamingException | SQLException ex) {
                log.error("Cannot update location for duplicate_images_checked", ex);
            }
            try {
                // verwijder van filesystem
                String path = img.getString("Path");
                URI u = new URI(path);
                File f;
                if(u.isAbsolute()){
                    f = new File(u);
                }else{
                    f = new File(path);
                }
                boolean deleted = f.delete();
                if(!deleted){
                    log.error("Cannot create file: " + path);
                }
            } catch (URISyntaxException ex) {
                log.error("Cannot create file object for deleting file: ",ex);
            }
        }
    }

    public String pushLocation(Location loc, Connection con, HashMap<Integer, JSONArray> imgsPerLoc, String baseurl, String authkey) throws IOException, SQLException, NamingException {
        return pushLocation(createLocationJSON(loc, con, imgsPerLoc), "", baseurl, authkey);
    }

    public String pushLocation(JSONObject location, String id, String baseurl, String authkey) throws IOException {
        return doRequest("", location, baseurl, authkey);
    }

    public void updateIDs(List<Location> locs, Connection con, String baseurl, String authkey) {
        // POST naar playadvisor/wp-json/b3p/v1/playbase/update-ids
        // met als body: 
        /*
        {
            playadvisorID: playbaseID
        }
         */

        for (Location loc : locs) {
            updateId(loc, con, baseurl, authkey);
        }
    }

    public void updateId(Location loc, Connection con, String baseurl, String authkey) {
        try {
            Project project = getProject(loc.getProject(), con);

            List<Map<String, Object>> images = loc.getImages();
            for (Map<String, Object> image : images) {
                JSONObject payload = new JSONObject();
                payload.put((String) image.get("pa_id"), (String) image.get("PlaybaseID"));
                try {
                    doRequest("update-ids", payload, baseurl, authkey);
                } catch (IOException ex) {
                    log.error("Cannot update image id for imageurl: " + image.get("URI"), ex);
                }
            }
            JSONObject locationPayload = new JSONObject();
            try {
                locationPayload.put(loc.getPa_id(), loc.getId());
                doRequest("update-ids", locationPayload, baseurl, authkey);
            } catch (IOException ex) {
                log.error("Cannot update image id for location: " + loc.getId(), ex);
            }
        } catch (NamingException | SQLException ex) {
            log.error("Cannot get project for location " + loc.getId(), ex);
        }
    }

    private void processResponse(JSONObject obj) {

    }

    private String doRequest(String endpoint, JSONObject payload, String baseurl, String authkey) throws IOException {
        String url = baseurl + "/wp-json/b3p/v1/playbase/" + endpoint;
        log.debug("Do request to playadvisor: " + url + ". With payload: " + payload.toString());

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost(url);
        StringEntity params = new StringEntity(payload.toString(), ContentType.APPLICATION_JSON);
        request.addHeader("content-type", "application/json");
        request.addHeader("Authorization", "Basic " + authkey);
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
        StatusLine sl = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        int statusCode = sl.getStatusCode();
        String stringResult = EntityUtils.toString(entity);
        if (statusCode != 200) {
            String statusLine = response.getStatusLine().getReasonPhrase();

            log.debug("Error: " + statusLine);
            log.debug("Error: " + stringResult);
            throw new IOException(statusLine);

        } else {
            log.debug("Result: " + stringResult);
            return stringResult;
        }
    }

    // <editor-fold desc="Helper functions for creating a correct JSON" defaultstate="collapsed">
    private final Integer[] excl = {58, 60, 61, 93, 22, 7, 56, 64, 177, 81, 82, 125, 87, 88, 89, 90, 92, 178, 111, 110, 109, 108, 127, 15, 23, 31, 32, 83, 171, 156, 155, 69, 68, 59, 152, 145, 144, 143, 141, 140, 139, 120, 119, 118, 117, 116, 115};
    private final List<Integer> excludedAssetTypes = Arrays.asList(excl);
    protected Map<Integer, List<String>> locationTypes;
    private Map<Integer, Integer> assetTypes;
    private Map<Integer, Integer> assetTypeToLocationCategory;
    private Map<Integer, String> equipmentTypes;

    public JSONObject createLocationJSON(Location loc, Connection con, HashMap<Integer, JSONArray> imgsPerLoc) throws SQLException, NamingException, IOException {
        JSONObject obj = loc.toPlayadvisorJSON();
        Integer id = loc.getId();
        JSONArray imgs = imgsPerLoc.getOrDefault(loc.getId(), new JSONArray());
        obj.put("Images", imgs);

        retrieveCategories(id, obj, con);
        retrieveAssets(id, obj, con);

        retrieveAgeCategories(id, obj, con);
        retrieveAccessibilities(id, obj, con);
        retrieveFacilities(id, obj, con);
        retrieveParking(id, obj, con);
        retrieveYoungestAssetDate(id, obj, con);
        return obj;
    }

    protected void retrieveParking(Integer id, JSONObject obj, Connection con) throws NamingException, SQLException {
        MapHandler rsh = new MapHandler();
        Map m = DB.qr().query(con, "SELECT park.parking from " + DB.LOCATION_TABLE + " loc inner join " + DB.LIST_PARKING_TABLE + " park on loc.parking = park.id", rsh);

        JSONArray parkings = new JSONArray();
        if (m != null) {
            parkings.put(m.get("parking"));
        }
        obj.put("Parkeren", parkings);
    }

    protected void retrieveFacilities(Integer id, JSONObject obj, Connection con) throws NamingException, SQLException {
        ArrayListHandler rsh = new ArrayListHandler();
        List<Object[]> facilities = DB.qr().query(con, "SELECT cat.facility from " + DB.LOCATION_FACILITIES_TABLE + " loc inner join " + DB.LIST_FACILITIES_TABLE + " as cat on cat.id = loc.facility  WHERE location = " + id, rsh);

        JSONArray facs = new JSONArray();
        for (Object[] cat : facilities) {
            facs.put(cat[0]);
        }
        obj.put("Faciliteiten", facs);
    }

    protected void retrieveAccessibilities(Integer id, JSONObject obj, Connection con) throws NamingException, SQLException {
        ArrayListHandler rsh = new ArrayListHandler();
        List<Object[]> cats = DB.qr().query(con, "SELECT cat.accessibility from " + DB.LOCATION_ACCESSIBILITY_TABLE + " loc inner join " + DB.LIST_ACCESSIBILITY_TABLE + " as cat on cat.id = loc.accessibility  WHERE location = " + id, rsh);
        JSONArray agecats = new JSONArray();

        for (Object[] cat : cats) {
            agecats.put(cat[0]);
        }
        obj.put("Toegankelijkheid", agecats);
    }

    protected void retrieveAgeCategories(Integer id, JSONObject obj, Connection con) throws NamingException, SQLException {
        ArrayListHandler rsh = new ArrayListHandler();
        List<Object[]> cats = DB.qr().query(con, "SELECT cat.agecategory from " + DB.LOCATION_AGE_CATEGORY_TABLE + " loc inner join " + DB.LIST_AGECATEGORIES_TABLE + " as cat on cat.id = loc.agecategory  WHERE location = " + id, rsh);
        JSONArray agecats = new JSONArray();
        for (Object[] cat : cats) {
            agecats.put(cat[0]);
        }
        obj.put("Leeftijdscategorie", agecats);
    }

    private void retrieveYoungestAssetDate(Integer id, JSONObject obj, Connection con) throws NamingException, SQLException {
        ArrayListHandler rsh = new ArrayListHandler();
        List<Object[]> m = DB.qr().query(con, "select installeddate from " + DB.ASSETS_TABLE + " where  location = " + id, rsh);
        int numNew = 0;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -2);
        Date mustBeAfter = c.getTime();
        for (Object[] asset : m) {
            String d = (String) asset[0];
            if (d == null || d.equals("")) {
                continue;
            }
            try {
                Date date = sdf.parse(d);
                if (mustBeAfter.before(date)) {
                    numNew++;
                }
            } catch (ParseException ex) {
                log.debug("Cannot parse date: " + d, ex);
            }
        }
        double ratio = (double) numNew / m.size();
        Boolean isNew = ratio > 0.3;
        obj.put("newPlayGround", isNew.toString());
    }

    public JSONArray retrieveImages(Integer id, Connection con, String imagepath, ImageDownloader downloader) throws NamingException, SQLException {
        ArrayListHandler rsh = new ArrayListHandler();

        List<Object[]> images = DB.qr().query(con, "SELECT url, caption,pa_id,id,pa_url, pa_deleted, pm_deleted from " + DB.IMAGES_TABLE + " WHERE location = ? order by equipment desc, lastupdated desc", rsh, id);
        JSONArray imgs = new JSONArray();
        int index = 0;
        for (Object[] image : images) {
            String url = (String) valueOrEmptyString(image[0]);
            if (url.isEmpty()) {
                continue;
            }
            Boolean pa_deleted = (Boolean) image[5];
            Boolean pm_deleted = (Boolean) image[6];
            if ((pa_deleted != null && pa_deleted) || (pm_deleted != null && pm_deleted)) {
                index++;
                continue;
            }
            String imageName = url.substring(url.lastIndexOf("/") + 1);
            if (imageName.contains("GetImage.ashx")) {
                imageName = "Image" + id + "-" + index + ".jpg";
            }
            Integer imageId = (Integer) image[3];
            JSONObject img = new JSONObject();
            img.put("Path", imagepath + File.separator + imageName);
            img.put("PlaybaseID", imageId);
            img.put("PlayadvisorID", valueOrEmptyString(image[2]));
            imgs.put(img);
            index++;
            if (downloader != null) {
                downloader.add(url, imageName);
            }
        }
        return imgs;
    }

    private void retrieveAssets(Integer id, JSONObject location, Connection con) throws NamingException, SQLException {
        GeometryJdbcConverter geometryConverter = GeometryJdbcConverterFactory.getGeometryJdbcConverter(con);
        ResultSetHandler<List<Asset>> assHandler = new BeanListHandler(Asset.class, new BasicRowProcessor(new DbUtilsGeometryColumnConverter(geometryConverter)));
        List<Asset> assets = DB.qr().query(con, "SELECT * FROM " + DB.ASSETS_TABLE + " WHERE location = ? and removedfromplaymapping = false", assHandler, id);
        Set<String> equipments = new HashSet<>();
        for (Asset asset : assets) {
            Integer type = asset.getType_();
            Integer equipment = assetTypes.get(type);
            String eq = equipmentTypes.get(equipment);
            if (eq != null) {
                equipments.add(eq);
            }
        }
        Set<String> types = new HashSet<>();
        for (String assetType : equipments) {
            types.add(assetType);
        }
        location.put("Assets", new JSONArray(types));
    }

    protected void retrieveCategories(Integer id, JSONObject location, Connection con) throws NamingException, SQLException {
        ArrayListHandler rsh = new ArrayListHandler();
        List<Object[]> cats = DB.qr().query(con, "SELECT cat.main, cat.category from " + DB.LOCATION_CATEGORY_TABLE + " loc inner join " + DB.LIST_CATEGORY_TABLE + " cat on cat.id = loc.category  WHERE location = " + id, rsh);
        Set<String> types = new HashSet<>();

        for (Object[] cat : cats) {
            types.add((String) cat[0]);
            types.add((String) cat[1]);
        }

        GeometryJdbcConverter geometryConverter = GeometryJdbcConverterFactory.getGeometryJdbcConverter(con);
        ResultSetHandler<List<Asset>> assHandler = new BeanListHandler(Asset.class, new BasicRowProcessor(new DbUtilsGeometryColumnConverter(geometryConverter)));
        List<Asset> assets = DB.qr().query(con, "SELECT * FROM " + DB.ASSETS_TABLE + " WHERE location = ? and removedfromplaymapping = false", assHandler, id);
        for (Asset asset : assets) {
            Integer type = asset.getType_();
            if (excludedAssetTypes.contains(type) || type == null) {
                continue;
            }
            Integer eq = assetTypeToLocationCategory.get(type);
            List<String> locCats = locationTypes.get(eq);
            types.addAll(locCats);
        }

        if (types.isEmpty()) {
            types.add("Openbare speeltuin");
        }

        location.put("Categorieen", new JSONArray(types));
    }

    private void initLists() {
        ArrayListHandler rsh = new ArrayListHandler();

        try {
            equipmentTypes = new HashMap<>();
            List<Object[]> o = DB.qr().query("SELECT id, equipment from " + DB.LIST_EQUIPMENT_TYPE_TABLE, rsh);
            for (Object[] type : o) {
                Integer id = (Integer) type[0];
                String cat = (String) type[1];
                equipmentTypes.put(id, cat);
            }
        } catch (NamingException | SQLException ex) {
            log.error("Cannot initialize playmapping assettypes:", ex);
        }

        try {
            locationTypes = new HashMap<>();
            List<Object[]> o = DB.qr().query("SELECT id, category, main from " + DB.LIST_CATEGORY_TABLE, rsh);
            for (Object[] type : o) {
                Integer id = (Integer) type[0];
                String category = (String) type[1];
                String main = (String) type[2];

                locationTypes.put(id, new ArrayList<>());
                locationTypes.get(id).add(category);
                locationTypes.get(id).add(main);
            }
        } catch (NamingException | SQLException ex) {
            log.error("Cannot initialize playadvisor location types:", ex);
        }

        try {
            assetTypes = new HashMap<>();
            assetTypeToLocationCategory = new HashMap<>();
            List<Object[]> o = DB.qr().query("SELECT id, equipment_type, locationcategory from " + DB.ASSETS_TYPE_GROUP_LIST_TABLE, rsh);
            for (Object[] type : o) {
                Integer id = (Integer) type[0];
                Integer equipmentType = (Integer) type[1];
                Integer locationcategory = (Integer) type[2];
                assetTypes.put(id, equipmentType);
                assetTypeToLocationCategory.put(id, locationcategory);
            }
        } catch (NamingException | SQLException ex) {
            log.error("Cannot initialize playmapping assettypes:", ex);
        }

    }

    private Object valueOrEmptyString(Object value) {
        return value == null ? "" : value;
    }

    protected Project getProject(Integer projectID, Connection con) throws NamingException, SQLException {
        Project p = DB.qr().query(con, "SELECT id,cronexpressie,type_,username,password,name,log,lastrun,mailaddress,baseurl, status, authkey, imagePath from " + DB.PROJECT_TABLE + " WHERE id = ?", projectHandler, projectID);
        return p;
    }

    // </editor-fold>
}
