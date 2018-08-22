/*
 * Copyright (C) 2017 B3Partners B.V.
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

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import nl.b3p.playbase.ImportReport.ImportType;
import nl.b3p.playbase.db.DB;
import nl.b3p.playbase.entities.Asset;
import nl.b3p.playbase.entities.Location;
import nl.b3p.playbase.entities.Project;
import nl.b3p.playbase.stripes.MatchActionBean;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Meine Toonen
 */
public class PlaymappingImporter extends Importer {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private Map<String, List<Integer>> agecategories;
    private final Map<String, String> iso3ToCountryName;
    
    public PlaymappingImporter(Project project) {
        super(project);
        ArrayListHandler rsh = new ArrayListHandler();
        try {
            agecategories = new HashMap<>();

            agecategories.put(AGECATEGORY_TODDLER_KEY, new ArrayList<>());
            agecategories.put(AGECATEGORY_JUNIOR_KEY, new ArrayList<>());
            agecategories.put(AGECATEGORY_SENIOR_KEY, new ArrayList<>());

            List<Object[]> o = DB.qr().query("SELECT * from " + DB.LIST_AGECATEGORIES_TABLE, rsh);
            for (Object[] cat : o) {
                Integer id = (Integer) cat[0];
                String categorie = (String) cat[1];

                if (Arrays.asList(AGECATEGORY_TODDLER).contains(categorie)) {
                    agecategories.get(AGECATEGORY_TODDLER_KEY).add(id);
                } else if (Arrays.asList(AGECATEGORY_JUNIOR).contains(categorie)) {
                    agecategories.get(AGECATEGORY_JUNIOR_KEY).add(id);
                } else if (Arrays.asList(AGECATEGORY_SENIOR).contains(categorie)) {
                    agecategories.get(AGECATEGORY_SENIOR_KEY).add(id);
                } else {
                    throw new IllegalArgumentException("Found agecategory in db not defined in code");
                }
            }
        } catch (NamingException | SQLException ex) {
            log.error("Cannot initialize playmapping processor:", ex);
        }
        iso3ToCountryName = new HashMap<>();
        Locale dutch = new Locale("nl", "NLD");
        String[] countries = Locale.getISOCountries();
        for (String country : countries) {
            Locale l = new Locale ("nl", country);
            iso3ToCountryName.put(l.getISO3Country(), l.getDisplayCountry(dutch));
        }
    }

    private static final Log log = LogFactory.getLog("PlaymappingProcessor");

    private final String AGECATEGORY_TODDLER_KEY = "AgeGroupToddlers";
    private final String AGECATEGORY_JUNIOR_KEY = "AgeGroupJuniors";
    private final String AGECATEGORY_SENIOR_KEY = "AgeGroupSeniors";

    private final String[] AGECATEGORY_TODDLER = {"0 - 5 jaar"};
    private final String[] AGECATEGORY_JUNIOR = {"6 - 11 jaar", "12 - 18 jaar"};
    private final String[] AGECATEGORY_SENIOR = {"Volwassenen", "Senioren"};

    public void init() {

    }

    public ImportReport processAssets(JSONArray assetsString, ImportReport report, Connection con) throws NamingException, SQLException {
        Map<Integer,Set<Integer>> assetTypes = new HashMap<>();
        List<Asset> assets = parseAssets(assetsString, assetTypes, report, con);
        for (Asset asset : assets) {
            try {
                saveAsset(asset, report);
            } catch (NamingException | SQLException | IllegalArgumentException ex) {
                log.error("Cannot save asset: " + ex.getLocalizedMessage());
                report.addError(ex.getLocalizedMessage(), ImportType.ASSET);
            }
        }
        return report;
    }

    public List<String> processLocations(String temp, ImportReport report, Connection con) throws NamingException, SQLException {
        List<Location> childLocations = parseChildLocations(temp,report, con);
        List<String> pm_guids = new ArrayList<>();
        for (Location childLocation : childLocations) {
            childLocation = mergeLocation(childLocation);
            pm_guids.add(childLocation.getPm_guid());
            saveLocation(childLocation, report);
        }
        return pm_guids;
    }
    
    private Location mergeLocation(Location loc) throws SQLException, NamingException{
        Location dbLoc = getExistingLocation(loc);
        if(dbLoc != null){
            dbLoc.setMunicipality(loc.getMunicipality());
            loc = MatchActionBean.mergeLocations(dbLoc, loc);
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
            sb.append(" where pm_guid = '");
            sb.append(newLocation.getPm_guid());
        }
        sb.append("';");
        loc = DB.qr().query(sb.toString(), locationHandler);
        return loc;
    }


    // <editor-fold desc="Assets" defaultstate="collapsed">
    protected List<Asset> parseAssets(JSONArray assetsArray, Map<Integer,Set<Integer>> assetTypes, ImportReport report, Connection con) throws NamingException, SQLException {
        List<Asset> assets = new ArrayList<>();
     //   JSONArray assetsArray = new JSONArray(assetsString);

        for (int i = 0; i < assetsArray.length(); i++) {
            JSONObject asset = assetsArray.getJSONObject(i);
            JSONObject location = asset.getJSONObject("Location");
            Integer locationId = getLocationId(location.getString("ID"));
            assets.add(parseAsset(asset, locationId, assetTypes, report, con));
            JSONArray linkedAssets = asset.getJSONArray("LinkedAssets");
            assets.addAll(parseAssets(linkedAssets,assetTypes, report, con));
        }
        return assets;
    }

    protected Asset parseAsset(JSONObject assetJSON, Integer locationId, Map<Integer,Set<Integer>> locationTypes, ImportReport report, Connection con) {
        Asset asset = new Asset();
        asset.setPm_guid(assetJSON.optString("ID"));
        asset.setLocation(locationId);
        asset.setName(assetJSON.optString("Name").replaceAll("\'", "\'\'"));
        JSONObject assetType = assetJSON.optJSONObject("AssetType");
        if(assetType != null){
            asset.setType_(getAssetType(assetType.optString("Name")));
        }
        
        JSONObject manufacturer = assetJSON.optJSONObject("Manufacturer");
        if(manufacturer != null){
            asset.setManufacturer( manufacturer.optString("Name"));
        }
        
        JSONObject product = assetJSON.optJSONObject("Product");
        if(product != null){
            asset.setProduct(product.optString("ProductNumber"));
            asset.setSerialnumber(product.optString("SerialNumber"));
            asset.setProductid(product.optString("ID"));
            JSONObject productgroup = product.optJSONObject("ProductGroup");
            if(productgroup != null){
                asset.setProductvariantid(productgroup.optString("ID"));
            }
        }
        
        JSONObject material = assetJSON.optJSONObject("Material");
        if(material != null){
            asset.setMaterial(material.optString("Name"));
        }
        asset.setInstalleddate(assetJSON.optString("InstalledDate"));
        asset.setEndoflifeyear(assetJSON.optInt("EndOfLifeYear"));
        asset.setHeight(assetJSON.optInt("Height"));
        asset.setDepth(assetJSON.optInt("Depth"));
        asset.setWidth(assetJSON.optInt("Width"));
        asset.setFreefallheight( assetJSON.optInt("FreefallHeight"));
        asset.setSafetyzonelength(assetJSON.optInt("SafetyZoneLength"));
        asset.setSafetyzonewidth(assetJSON.optInt("SafetyZoneWidth"));
        asset.setPricepurchase( assetJSON.optInt("PricePurchase"));
        asset.setPriceinstallation( assetJSON.optInt("PriceInstallation"));
        asset.setPricereinvestment( assetJSON.optInt("PriceReInvestment"));
        asset.setPricemaintenance( assetJSON.optInt("PriceMaintenance"));
        asset.setPriceindexation(assetJSON.optInt("PriceIndexation"));
        asset.setLatitude(Double.parseDouble(assetJSON.optString("Lat").replaceAll(",", ".")));
        asset.setLongitude( Double.parseDouble(assetJSON.optString("Lng").replaceAll(",", ".")));
        boolean removedFromPlaymapping = assetJSON.optBoolean("Removed", false) || assetJSON.optBoolean("Archived", false);
        asset.setRemovedfromplaymapping(removedFromPlaymapping);
        
        asset.setDocuments( parseImagesAndWords(assetJSON.optJSONArray("Documents"),removedFromPlaymapping));
        
        List<Map<String,Object>> images = parseImagesAndWords(assetJSON.optJSONArray("Images"),removedFromPlaymapping);
        processImages(locationId, images, report, con, true, asset.isRemovedfromplaymapping());
        asset.setImages(images);
        
        
        asset.setAgecategories(parseAgecategories(assetJSON));
        
        if (!locationTypes.containsKey(locationId)) {
            locationTypes.put(locationId, new HashSet<>());
        }
        if (asset.getType_() != null){
            locationTypes.get(locationId).add(assetTypeToLocationCategory.get(asset.getType_()));
        }
        // ToDo hyperlinks: asset.put("Hyperlinks", assetJSON.optJSONArray("Hyperlinks"));
        
        return asset;
    }
    
    protected Integer[] parseAgecategories(JSONObject assetJSON){
        
        boolean toddler = assetJSON.optBoolean(AGECATEGORY_TODDLER_KEY, false);
        boolean junior = assetJSON.optBoolean(AGECATEGORY_JUNIOR_KEY, false);
        boolean senior = assetJSON.optBoolean(AGECATEGORY_SENIOR_KEY, false);
        List<Integer> agecategoriesList = new ArrayList<>();
        
        if (toddler) {
            agecategoriesList.addAll(agecategories.get(AGECATEGORY_TODDLER_KEY));
        }

        if (junior) {
            agecategoriesList.addAll(agecategories.get(AGECATEGORY_JUNIOR_KEY));
        }

        if (senior) {
            agecategoriesList.addAll(agecategories.get(AGECATEGORY_SENIOR_KEY));
        }
        return agecategoriesList.toArray(new Integer[0]);
    }


    // </editor-fold>
    
    // <editor-fold desc="Locations" defaultstate="collapsed">
    protected List<Location> parseChildLocations(String locations, ImportReport report, Connection con) {
        List<Location> locs = new ArrayList<>();
        JSONArray childLocations = new JSONArray(locations);
        for (int i = 0; i < childLocations.length(); i++) {
            JSONObject childLocation = childLocations.getJSONObject(i);
            JSONArray cls = childLocation.getJSONArray("ChildLocations");
            if (cls.length() == 0) {
                locs.add(parseLocation(childLocation, report, con));
            } else {
                locs.addAll(parseChildLocations(cls.toString(), report, con));
            }

        }
        return locs;
    }

    protected Location parseLocation(JSONObject json, ImportReport report, Connection con)  {
        Location location = new Location();
        
        location.setPm_guid(json.optString("ID"));
        location.setTitle(json.optString("Name").replaceAll("\'", "\'\'"));
        location.setStreet(json.optString("AddressLine1"));
        //location.setMunicipality( json.optString("Suburb"));
        location.setMunicipality( json.optString("City"));
        location.setArea(json.optString("Area"));
        location.setPostalcode(json.optString("PostCode"));
        String content = json.optString("Notes");
        content = content == null || content.isEmpty() ? null : content;
        location.setPm_content(content);
        try {
            location.setPm_lastmodified(json.has("LastUpdated") ? sdf.parse(json.getString("LastUpdated")): null);
        } catch (ParseException ex) {
            log.error("Cannot parse last updated field to date",ex);
        }
        location.setLatitude(Double.parseDouble(json.optString("Lat").replaceAll(",", ".")));
        location.setLongitude( Double.parseDouble(json.optString("Lng").replaceAll(",", ".")));
        location.setRemovedfromplaymapping(json.optBoolean("Archived", true));
        
        String country = iso3ToCountryName.get(json.optString("Country", "NLD"));
        location.setCountry(country);
        
        location.setDocuments(parseImagesAndWords(json.optJSONArray("Documents"), location.getRemovedfromplaymapping()));
        List<Map<String,Object>> images = parseImagesAndWords(json.optJSONArray("Images"), location.getRemovedfromplaymapping());
        processImages(location.getId(), images, report, con, false, false);
        location.setImages( images );
        
        return location;
    }

    /**
     * Parse images and documents (=words).
     *
     * @param images The images or documents to be parsed
     * @param removedFromPlaymapping
     * @return Returns the converted list with maps representing the documents or images
     */
    protected List<Map<String, Object>> parseImagesAndWords(JSONArray images, boolean removedFromPlaymapping) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < images.length(); i++) {
            JSONObject img = images.getJSONObject(i);
            Map<String, Object> image = parseImageAndWord(img,removedFromPlaymapping);
            list.add(image);
        }
        return list;
    }

    protected Map<String, Object> parseImageAndWord(JSONObject image, boolean removedFromPlaymapping) {

        Map<String, Object> map = new HashMap<>();
        map.put("$id", image.optString("$id"));
        map.put("ID", image.optString("ID"));
        String url = image.optString("URI");
        url = url.replaceAll("&w=350&h=350", "");
        map.put("URI", url);
        map.put("pm_deleted", removedFromPlaymapping);
        map.put("Description", image.optString("Description"));
        try {
            map.put("LastUpdated", image.has("LastUpdated") ? sdf.parse(image.getString("LastUpdated")): null);
        } catch (ParseException ex) {
            log.debug("Cannot parse date: " + image.getString("LastUpdated"), ex);
        }
        return map;
    }
    
    protected void processImages(Integer location, List<Map<String, Object>> imgs, ImportReport report, Connection con, boolean isAsset, boolean isAssetRemoved){
        try {
            ArrayListHandler rsh = new ArrayListHandler();
            String q = "SELECT url, caption,pa_id,id from " + DB.IMAGES_TABLE + " WHERE location = ? and pm_guid is not null ";
            if(isAsset){
                q += "and equipment is not null ";
            }
            q += "order by equipment desc, lastupdated desc";
            List<Object[]> images = DB.qr().query(con, q, rsh, location);
            for (Object[] img : images) {
                boolean found = false;
                String curImgUrl = (String)img[0];
                int curId = (int)img[3];
                for (Map<String, Object> pushedImg : imgs) {
                    if (pushedImg.get("Path") != null && ((String)pushedImg.get("Path")).equals(curImgUrl)){
                        found = true;
                        break;
                    }
                }
                if(!found || isAssetRemoved){
                    // zet img op pm_deleted = true
                     int numUpdated = DB.qr().update(con, "UPDATE " + DB.IMAGES_TABLE + " SET pm_deleted = true WHERE id = ?", curId);
                     int a= 0;
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Cannot process images",ex);
        }
    }
    
    
    protected Integer getLocationId(String pmguid) throws NamingException, SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("select id from ");

        sb.append(DB.LOCATION_TABLE);
        sb.append(" where pm_guid = '");
        sb.append(pmguid);

        sb.append("';");
        Integer id = (Integer) DB.qr().query(sb.toString(), new ScalarHandler<>());
        return id;
    }
    // </editor-fold>

    public ImportReport importJSONAssetsFromAPI(String username, String password, String apiurl, List<String> pm_guids,ImportReport report, Connection con) throws SQLException, NamingException {
        JSONArray results = new JSONArray();
        for (String pm_guid : pm_guids) {
            String url = apiurl + pm_guid;
            String stringResult = getResponse(username, password, url, report);
            JSONArray result = new JSONArray(stringResult);
            
            for (Iterator<Object> iterator = result.iterator(); iterator.hasNext();) {
                Object next = iterator.next();
                results.put(next);
            }
        }
        

        processAssets(results, report, con);
        ImportType type = ImportType.ASSET;
        report.setImportedstring(type, results.toString());
        return report;
    }
    
    public List<String> importJSONLocationsFromAPI(String username, String password, String apiurl,ImportReport report, boolean isInitialLoad, Connection con) throws SQLException, NamingException {
        String stringResult = getResponse(username, password, apiurl, report);
        if(stringResult == null){
            return null;
        }
        log.debug("Result: " + stringResult);
        List<String> locationIds = processLocations(stringResult, report, con);
        ImportType type = ImportType.LOCATION;
        report.setImportedstring(type, stringResult);
        return locationIds;
    }

}
