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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import nl.b3p.playbase.db.DB;
import nl.b3p.playbase.entities.Asset;
import nl.b3p.playbase.entities.Project;
import nl.b3p.playbase.entities.Location;
import nl.b3p.playbase.entities.Status;
import nl.b3p.playbase.stripes.MatchActionBean;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Meine Toonen
 */
public class PlayadvisorImporter extends Importer {

    private static final Log log = LogFactory.getLog(PlayadvisorImporter.class);

    public PlayadvisorImporter(Project project) {
        super(project);
        postfix = "_playadvisor";
    }

    public void initialLoad(Project job, ImportReport report, Connection con, String baseurl, String authkey) throws NamingException, SQLException {

        // haal alle speelplekken voro dit project/gemeente op
        // Sla alle playadvisor plekken op
        // Bij nieuwe plekken, mail monique
        // Na merge: Stuur lijstje van ids van playbaseID vs playadvisor id terug naar playadvisor
        String muns = this.getProject().getName().toLowerCase();
        String[] municipalities = muns.split(",");
        for (String municipality : municipalities) {
            municipality = municipality.trim();
            try {
                String url = baseurl + "/wp-json/b3p/v1/playbase/" + URLEncoder.encode(municipality,"UTF-8") + "?" + System.nanoTime();
                HttpClient httpClient = HttpClientBuilder.create().build();

                HttpGet request = new HttpGet(url);
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Authorization", "Basic " + authkey);
                HttpResponse response = httpClient.execute(request);
                StatusLine sl = response.getStatusLine();
                HttpEntity entity = response.getEntity();
                int statusCode = sl.getStatusCode();
                if (statusCode != 200) {
                    String statusLine = response.getStatusLine().getReasonPhrase();

                    log.debug("Error: " + statusLine);
                    throw new IOException(statusLine);

                } else {
                    String stringResult = EntityUtils.toString(entity);
                    List<Location> locs = processLocations(stringResult, report, con);
                    log.debug("Result: " + stringResult);
                }
            } catch (IOException ex) {
                log.error("Error while getting all speelplekken for project " + this.getProject() + " and municipality: " + municipality, ex);
                report.addError(ex.getLocalizedMessage(), ImportReport.ImportType.GENERAL);
            }
        }

    }

    public List<Location> processLocations(String locs, ImportReport report, Connection con) throws NamingException, SQLException {
        List<Location> locations = new ArrayList<>();
        JSONArray ar = new JSONArray(locs);
        for (Iterator<Object> iterator = ar.iterator(); iterator.hasNext();) {

            JSONObject obj = (JSONObject) iterator.next();
            boolean hasProject = this.getProject() != null;
            String prevpostfix = postfix;
            Location l = parseLocation(obj, con);
            try {
                if (isProjectReady(this.getProject(), con) || isPlaygroundAlreadyMerged(l, con)) {
                    postfix = "";
                }

                processLocation(l, obj, report, con);
                locations.add(l);

                if (!hasProject) {
                    this.setProject(null);
                }

                if (postfix.isEmpty()) {
                    postfix = prevpostfix;
                }
            } catch (IllegalArgumentException e) {
                log.error("Error processing location " + l.getTitle(), e);
            }

        }
        return locations;
    }

    private boolean isProjectReady(Project project, Connection con) throws NamingException, SQLException, IllegalArgumentException {
        if (project.getStatus() == null) {
            ResultSetHandler<List<Project>> handler = new BeanListHandler(Project.class);
            List<Project> projects = DB.qr().query(con, "SELECT id,cronexpressie,type_,username,password,name,log,lastrun,mailaddress,baseurl, status from " + DB.PROJECT_TABLE + " WHERE name = ?", handler, project.getName());
            if (projects.size() == 1) {
                project = projects.get(0);
            } else if (projects.isEmpty()) {
                throw new IllegalArgumentException("Speelplek heeft geen geconfigureerd project voor " + project.getName());
            } else {
                throw new IllegalArgumentException("Speelplek heeft meer dan 1 (" + projects.size() + ") geconfigureerd project voor " + project.getName());
            }
        }

        return project.getStatus() == Status.PUBLISHED;
    }

    private boolean isPlaygroundAlreadyMerged(Location loc, Connection con) throws NamingException, SQLException, IllegalArgumentException {
        Status s = loc.getStatus();
        if(loc.getId() != null){
            Location currentLocation = DB.qr().query(con, "select * from " + DB.LOCATION_TABLE + " where id = ?", locationHandler, loc .getId());
            return currentLocation != null;
        }
        if (s == null || s == Status.UNDER_REVIEW || s == Status.UNPUBLISHED) {
            return false;
        } else {
            // merge location to main table when status is "PUBLISHED"
            return true;
        }
    }

    protected void processLocation(Location location, JSONObject obj, ImportReport report, Connection con) throws NamingException {
        // Set neccessary playbase field for location, like status
        boolean locationAlreadyExists = false;
        int id = -1;
        try {
            if (location.getPa_id() != null) {
                Location currentLocation = DB.qr().query(con, "select * from " + DB.LOCATION_TABLE + postfix + " where pa_id = ?", locationHandler, location.getPa_id());
                if (currentLocation != null) {
                    locationAlreadyExists = true;
                    location.setStatus(currentLocation.getStatus());
                } else {
                    location.setStatus(Status.UNPUBLISHED);
                }
            }

            List<Map<String, Object>> imgs = parseImages(location, obj, report);
            List<Map<String, Object>> images = processImages(location, imgs, report, con);
            location = mergeLocation(location);
            location.setImages(images);
            id = saveLocation(location, report);
            try {
                List<Asset> assets = parseAssets(location, obj.getJSONArray("Assets"), report, false, con);

                for (Asset asset : assets) {
                    saveAsset(asset, report);
                }

            } catch (SQLException e) {
                log.error("Cannot process location", e);
                report.addError("Cannot process location: " + e.getLocalizedMessage(), ImportReport.ImportType.ASSET);
            }

        } catch (SQLException e) {
            log.error("Cannot process location", e);
            report.addError("Cannot process location: " + e.getLocalizedMessage(), ImportReport.ImportType.LOCATION);
        }
       
        try {
            saveLocationAgeCategory(location, Arrays.asList(location.getAgecategories()), locationAlreadyExists);
        } catch (SQLException ex) {
            report.addError(ex.getLocalizedMessage() + ". Location is saved, but agecategory is not.", ImportReport.ImportType.LOCATION);
        }

        try {
            saveLocationType(location, obj, locationAlreadyExists);
        } catch (IllegalArgumentException | SQLException | UnsupportedEncodingException ex) {
            report.addError(ex.getLocalizedMessage() + ". Location is saved, but type is not.", ImportReport.ImportType.LOCATION);
        }

        try {
            saveFacilities(location, obj, locationAlreadyExists);
        } catch (IllegalArgumentException | SQLException ex) {
            report.addError(ex.getLocalizedMessage() + ". Location is saved, but facilities are not.", ImportReport.ImportType.LOCATION);
        }
  
        try {
            saveAccessibility(id, obj, locationAlreadyExists);
        } catch (IllegalArgumentException | SQLException ex) {
            report.addError(ex.getLocalizedMessage() + ". Location is saved, but accessiblity is not.", ImportReport.ImportType.LOCATION);
        }
         
    }
    
    private Location mergeLocation(Location loc) throws SQLException, NamingException{
        Location dbLoc = getExistingLocation(loc);
        if(dbLoc != null){
            dbLoc.setMunicipality(loc.getMunicipality());
            dbLoc.setAgecategories(loc.getAgecategories());
            dbLoc.setStatus(loc.getStatus());
            loc = MatchActionBean.mergeLocations(loc,dbLoc);
        }
        
        return loc;
    }
    
    public Location getExistingLocation(Location newLocation) throws NamingException, SQLException {
        Location loc;

        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");

        sb.append(DB.LOCATION_TABLE);
        if (newLocation.getId() != null) {
            sb.append(" where id = '");
            sb.append(newLocation.getId());
        } else {
            sb.append(" where pa_id = '");
            sb.append(newLocation.getPa_id());
        }
        sb.append("';");
        loc = DB.qr().query(sb.toString(), locationHandler);
        return loc;
    }

    protected Location parseLocation(JSONObject obj, Connection con) throws NamingException, SQLException {
        Location loc = new Location();

        loc.setId(obj.optInt("PlaybaseID"));
        loc.setPa_title(obj.optString("Titel"));
        loc.setPhone(obj.optString("Telefoon"));
        loc.setPa_content(obj.optString("Content"));
        loc.setWebsite(obj.optString("Website"));
        if (obj.has("Latitude")) {
            loc.setLatitude(obj.getDouble("Latitude"));
        }
        if (obj.has("Longitude")) {
            loc.setLongitude(obj.getDouble("Longitude"));
        }
        loc.setPa_id("" + obj.getInt("PlayadvisorID"));
        loc.setMunicipality(obj.getString("Plaats"));
        if (this.getProject() == null) {
            Project p = this.getProject(loc.getMunicipality(), con);
            this.setProject(p);
            loc.setProject(p.getId());
        } else {
            loc.setProject(this.getProject().getId());
        }
        loc.setCountry(obj.getString("Land"));
        loc.setStreet(obj.optString("Straat"));
        loc.setEmail(obj.optString("Email"));
        
        
        JSONArray agecategories = obj.optJSONArray("Leeftijdscategorie");
        List<Integer> ids = new ArrayList<>();
        for (Object next : agecategories) {
            String agecategory = (String) next;
            Integer id = agecategoryTypes.get(agecategory.toLowerCase());
            if (id == null) {
                throw new IllegalArgumentException("Agecategory >" + agecategory + "< does not exist. Location with title >" + loc.getPa_title() + "< not saved.");
            }
            ids.add(id);
        }
        loc.setAgecategories(ids.toArray(new Integer[0]));
        
        return loc;
    }

    protected void saveLocationType(Location location, JSONObject locationObject, boolean deleteFirst) throws NamingException, SQLException, UnsupportedEncodingException {
        if(deleteFirst){
            DB.qr().update("DELETE FROM " + DB.LOCATION_CATEGORY_TABLE + postfix + " WHERE location = " + location.getId());
        }

        JSONArray types = locationObject.getJSONArray("Categorieen");
        Set<Integer>  typeSet = new HashSet<>();
        for (Object t : types) {
            String type = (String)t;
            type = StringEscapeUtils.unescapeHtml4(type);
            Integer categoryId = allLocationTypes.containsKey(type) ? allLocationTypes.get(type) : null;
            
            if (categoryId == null) {
                throw new IllegalArgumentException("Unknown category given: subcategory: " + type+ ". Cannot save types for location with id: " + location.getId());
            }
            typeSet.add(categoryId);
        }
        this.saveLocationTypes(typeSet, location.getId());
    }

    protected void saveFacilities(Location location, JSONObject locationObj, boolean deleteFirst) throws NamingException, SQLException {
        if (deleteFirst) {
            DB.qr().update("DELETE FROM " + DB.LOCATION_FACILITIES_TABLE + postfix + " WHERE location = " + location.getId());
        }

        JSONArray facilities = locationObj.getJSONArray("Faciliteiten");
        for (Object f : facilities) {
            String facility = (String)f;
            Integer facilityId = facilityTypes.get(facility);
            if (facilityId == null) {
                throw new IllegalArgumentException("Unknown facility given: " + facility + ". Cannot save facilities for location with id: " + location.getId());
            }
            this.saveFacilities(location, facilityId);
        }
    }
    
    protected void saveAccessibility(Integer locationId, JSONObject locationObj, boolean deleteFirst) throws NamingException, SQLException {
        if (deleteFirst) {
            DB.qr().update("DELETE FROM " + DB.LOCATION_ACCESSIBILITY_TABLE + postfix + " WHERE location = " + locationId);
        }
        JSONArray accessibilities = locationObj.getJSONArray("Toegankelijkheid");

        for (Object a : accessibilities) {
            String accessiblity = (String)a;
            Integer id = accessibilityTypes.get(accessiblity);
            if (id == null) {
                throw new IllegalArgumentException("Unknown accessibilty given: " + accessiblity + ". Cannot save types for location with id: " + locationId);
            }
            this.saveAccessibility(locationId, id);
        }
    }
    
    protected List<Asset> parseAssets(Location location, JSONArray assetsArray, ImportReport report, boolean merged, Connection con) throws NamingException, SQLException {

        List<Asset> assets = new ArrayList<>();
        // Save new assets
        for (Iterator<Object> iterator = assetsArray.iterator(); iterator.hasNext();) {
            String asset = (String) iterator.next();
            if (asset.isEmpty()) {
                continue;
            }
            Asset ass = new Asset();
            ass.setName(asset);
            ass.setPa_guid(location.getPa_id());
            ass.setLocation(location.getId());
            ass.setLatitude(location.getLatitude());
            ass.setLongitude(location.getLongitude());

            Integer[] cats = location.getAgecategories();

            ass.setAgecategories(cats);
            Integer equipmentType = getEquipmentType(asset);
            ass.setEquipment(equipmentType);
            ass.setType_(equipmenttypePAtoPM.get(equipmentType));
            assets.add(ass);
        }
        return assets;
    }

    protected  List<Map<String, Object>> processImages(Location location, List<Map<String, Object>> imgs, ImportReport report, Connection con){
        List<Map<String, Object>> newImages = new ArrayList<>();
        try {
            ArrayListHandler rsh = new ArrayListHandler();
            int id = location.getId();
            int index = 0;
            List<Object[]> dbImages = DB.qr().query(con, "SELECT url, caption,pa_id,id, equipment, location from " + DB.IMAGES_TABLE + " WHERE location = ? order by equipment desc, lastupdated desc", rsh, location.getId());
            for (Object[] dbImg : dbImages) {
                boolean found = false;
                String curImgUrl = (String) dbImg[0];
                String imageName = curImgUrl.substring(curImgUrl.lastIndexOf("/") + 1);
                if (imageName.contains("GetImage.ashx")) {
                    imageName = "Image" + id + "-" + index + ".jpg";
                }

                int curId = (int)dbImg[3];
                for (Map<String, Object> pushedImg : imgs) {
                    if (((String)pushedImg.get("URI")).contains(imageName)){
                        found = true;
                        Map<String,Object> dbImage = new HashMap<>();
                        dbImage.put("Description", dbImg[1]);
                        dbImage.put("URI", curImgUrl);
                        dbImage.put("location", dbImg[5]);
                        dbImage.put("pa_id", pushedImg.get("pa_id"));
                        dbImage.put("id", dbImg[3]);
                        int numUpdated = DB.qr().update(con, "UPDATE " + DB.IMAGES_TABLE + " SET pa_id = ? WHERE id = ?",pushedImg.get("pa_id"), curId);
                        newImages.add(dbImage);
                        break;
                    }
                }
                if(!found){
                    // zet img op pa_deleted = true
                     int numUpdated = DB.qr().update(con, "UPDATE " + DB.IMAGES_TABLE + " SET pa_deleted = true WHERE id = ?", curId);
                     int a= 0;
                }
                index ++;
            }

            for (Map<String, Object> pushedImg : imgs) {
                boolean existsInDB = false;
                for (Object[] dbImg : dbImages) {
                    String curImgUrl = (String) dbImg[0];
                    String imageName = curImgUrl.substring(curImgUrl.lastIndexOf("/") + 1);
                    if (imageName.contains("GetImage.ashx")) {
                        imageName = "Image" + id + "-" + index + ".jpg";
                    }
                    
                    if (((String)pushedImg.get("URI")).contains(imageName)){
                        existsInDB = true;
                        break;
                    }
                }
                if(!existsInDB){
                    newImages.add(pushedImg);
                }
            }
           // hier alle images van playadvisor inladen.
        } catch (SQLException | NamingException ex) {
            log.error("Cannot process images");
        }
        
        
        return newImages;
    }
    
    protected List<Map<String, Object>> parseImages(Location location, JSONObject obj, ImportReport report){
        List<Map<String, Object>> images = new ArrayList<>();
        JSONArray imagesJSON = obj.optJSONArray("Images");
        for (Object object : imagesJSON) {
            JSONObject img = (JSONObject)object;
            images.add(parseImage(location, img, report));
        }
        return images;
    }
    
    protected Map<String, Object> parseImage(Location location, JSONObject image, ImportReport report){
        Map<String, Object> map = new HashMap<>();
        
        map.put("pa_id", image.optString("PlayadvisorID"));
        String url = image.optString("Path");
        map.put("URI", url);
        map.put("PlaybaseID", image.optString("PlaybaseID"));
        return map;
    }
}
