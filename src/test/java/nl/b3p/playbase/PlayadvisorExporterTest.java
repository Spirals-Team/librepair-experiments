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

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import nl.b3p.playbase.db.DB;
import nl.b3p.playbase.db.TestUtil;
import nl.b3p.playbase.entities.Location;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author Meine Toonen
 */
public class PlayadvisorExporterTest extends TestUtil {

    private PlayadvisorExporter instance;

    public PlayadvisorExporterTest() {
        this.useDB = true;
        this.initData = false;
    }

    @Before
    public void initTest() {
        instance = new PlayadvisorExporter();
    }

    //@Test
    public void testExport() {
        //  try {
        //JSONObject location = new JSONObject("{      \"Leeftijdscategorie\" : [],      \"Telefoon\" : \"\",      \"Straat\" : \"Gilze-Rijenhof 22184\",      \"Images\" : [         {            \"url\" : null,            \"attachment_id\" : null         },         {            \"url\" : null,            \"attachment_id\" : null         },         {            \"attachment_id\" : null,            \"url\" : null         },         {            \"url\" : null,            \"attachment_id\" : null         },         {            \"attachment_id\" : null,            \"url\" : null         },         {            \"url\" : null,            \"attachment_id\" : null         },         {            \"attachment_id\" : null,            \"url\" : null         },         {            \"attachment_id\" : null,            \"url\" : null         }      ],      \"Land\" : \"\",      \"Content\" : \"\",      \"Toegankelijkheid\" : [],      \"Titel\" : \"Speeltuin Gilze-Rijenhof in Nootdorp\",      \"Plaats\" : \"\",      \"Samenvatting\" : \"\",      \"Website\" : \"\",      \"Regio\" : \"\",      \"Email\" : \"\",      \"Categorien\" : [         \"Openbare speeltuin\",         \"Speeltuinen\"      ],      \"Faciliteiten\" : [],      \"Assets\" : [         \"Draaitoestel\",         \"Schommel\",         \"Speelhuis\"      ],      \"id\" : \"72567\",      \"Longitude\" : \"4.383566\",      \"newPlayGround\" : \"false\",      \"Latitude\" : \"52.04646\",      \"Parkeren\" : [],      \"PlayadvisorID\" : 88721   }");
        JSONObject location = new JSONObject("{\"PlaybaseID\":\"72619\",\"Titel\":\"bij pannenkoekenhuis Soete suikerbol\",\"Content\":\"<br>\",\"Samenvatting\":\"\",\"Latitude\":\"52.0065051\",\"Longitude\":\"4.4511693\",\"Straat\":\"Ade 23\",\"Plaats\":\"\",\"Regio\":\"\",\"Land\":\"Nederland\",\"Website\":\"\",\"Email\":\"\",\"Telefoon\":\"\",\"PlayadvisorID\":98609,\"Images\":[{\"PlaybaseID\":-1,\"PlayadvisorID\":\"38\",\"Path\":\"http:\\/\\/playadvisor.b3p.nl\\/wp-content\\/uploads\\/2016\\/04\\/001.jpg\"}],\"Categorien\":[\"Openbare speeltuin\",\"Speeltuinen\"],\"Leeftijdscategorie\":[],\"Toegankelijkheid\":[],\"Faciliteiten\":[],\"Parkeren\":[],\"Assets\":[],\"newPlayGround\":\"false\"}");
        // instance.pushLocation(location, "98609",null);
        int a = 0;
        /*  } catch (IOException ex) {
            fail(ex.getLocalizedMessage());
        }*/
    }
    
     
    @Test
    public void testImageCompletelyDifferent() throws NamingException, SQLException, URISyntaxException{
        
        JSONArray pics = new JSONArray();

        JSONObject pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/1_diff.jpg").toURI().toString());
        pic.put("PlaybaseID",1);
        pics.put(pic);

        pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/Image79537-2.jpg").toURI().toString());
        pic.put("PlaybaseID",2);
        pics.put(pic);

        
        Connection con =  DB.getConnection();
        JSONArray result = instance.removeDuplicates(pics, con);
        Assert.assertEquals(2, result.length());
    }
    
    
    @Test
    public void testImageSortaEquals() throws NamingException, SQLException, URISyntaxException{
        
        JSONArray pics = new JSONArray();

        JSONObject pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/Image79168-9.jpg").toURI().toString());
        pic.put("PlaybaseID",1);
        pics.put(pic);

        pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/Image79168-13.jpg").toURI().toString());
        pic.put("PlaybaseID",1);
        pics.put(pic);

        
        Connection con =  DB.getConnection();
        JSONArray result = instance.removeDuplicates(pics, con);
        Assert.assertEquals(1, result.length());
    }
    
    @Test
    public void testImageCompletelyEquals() throws NamingException, SQLException, URISyntaxException{
        
        JSONArray pics = new JSONArray();

        JSONObject pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/1.jpg").toURI().toString());
        pic.put("PlaybaseID",1);
        pics.put(pic);

        pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/1_copy.jpg").toURI().toString());
        pic.put("PlaybaseID",1);
        pics.put(pic);

        
        Connection con =  DB.getConnection();
        JSONArray result = instance.removeDuplicates(pics, con);
        Assert.assertEquals(1, result.length());
        
    }
    
    @Test
    public void testRemovingImages() throws URISyntaxException, NamingException, SQLException {
        Location loc = new Location();
        List<Location> locations = Collections.singletonList(loc);
        Integer locationId = 16;
        loc.setId(locationId);
        Connection con =  DB.getConnection();
        loc.setDuplicate_images_checked(false);

        Map<Integer, JSONArray> imagesPerLocation = getData(locationId);

        Map<Integer, JSONArray> result = instance.analyseImagesForDuplicates(locations, imagesPerLocation,con);
        JSONArray images = result.get(locationId);
        Assert.assertEquals(4, images.length());
        Assert.assertEquals(Boolean.TRUE, loc.getDuplicate_images_checked());
        
    }

    @Test
    public void testRemovingImagesAlreadyProcessed() throws URISyntaxException, NamingException, SQLException {
        Location loc = new Location();
        List<Location> locations = Collections.singletonList(loc);
        Integer locationId = 16;
        loc.setId(locationId);
        loc.setDuplicate_images_checked(true);

        Map<Integer, JSONArray> imagesPerLocation = getData(locationId);

        Map<Integer, JSONArray> result = instance.analyseImagesForDuplicates(locations, imagesPerLocation, DB.getConnection());
        Assert.assertEquals(0,result.keySet().size());
    }

    private Map<Integer, JSONArray> getData(Integer locationId) throws URISyntaxException {
        JSONArray pics = new JSONArray();
        Map<Integer, JSONArray> imagesPerLocation = new HashMap<>();

        JSONObject pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/1.jpg").toURI().toString());
        pic.put("PlaybaseID",1);
        pics.put(pic);

        pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/1_copy.jpg").toURI().toString());
        pic.put("PlaybaseID",2);
        pics.put(pic);

        pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/2.jpg").toURI().toString());
        pic.put("PlaybaseID",3);
        pics.put(pic);

        pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/2_copy.jpg").toURI().toString());
        pic.put("PlaybaseID",4);
        pics.put(pic);

        pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/3.jpg").toURI().toString());
        pic.put("PlaybaseID",5);
        pics.put(pic);

        pic = new JSONObject();
        pic.put("Path", PlayadvisorExporterTest.class.getResource("imgs/4.jpg").toURI().toString());
        pic.put("PlaybaseID",6);
        pics.put(pic);

        imagesPerLocation.put(locationId, pics);
        return imagesPerLocation;
    }

}
