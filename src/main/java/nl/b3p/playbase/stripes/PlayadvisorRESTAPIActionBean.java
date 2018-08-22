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
package nl.b3p.playbase.stripes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.JsonResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RestActionBean;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import nl.b3p.loader.jdbc.GeometryJdbcConverter;
import nl.b3p.loader.jdbc.GeometryJdbcConverterFactory;
import nl.b3p.loader.util.DbUtilsGeometryColumnConverter;
import nl.b3p.playbase.ImageDownloader;
import nl.b3p.playbase.ImportReport;
import nl.b3p.playbase.Importer;
import nl.b3p.playbase.PlayadvisorExporter;
import nl.b3p.playbase.PlayadvisorImporter;
import nl.b3p.playbase.PlaymappingImporter;
import nl.b3p.playbase.db.DB;
import nl.b3p.playbase.entities.Location;
import nl.b3p.playbase.entities.Project;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Meine Toonen
 */
@RestActionBean
@UrlBinding("/rest/playadvisor/{location}")
public class PlayadvisorRESTAPIActionBean implements ActionBean {

    private static final Log log = LogFactory.getLog(PlayadvisorRESTAPIActionBean.class);

    private static final String JSP = "/WEB-INF/jsp/admin/playadvisor/view.jsp";
    protected ResultSetHandler<Location> locationHandler;

    protected final ResultSetHandler<Project> projectHandler = new BeanHandler(Project.class);

    private ActionBeanContext context;

    @Validate
    private Integer location;

    @Validate
    private Integer project;

    // <editor-fold desc="Getters and settesr" defaultstate="collapsed">
    public void setLocation(Integer location) {
        this.location = location;
    }

    public Integer getLocation() {
        return location;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    // </editor-fold>
    
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(JSP);
    }

    public Resolution post() {
        String logString = "";
        try (Connection con = DB.getConnection()) {
            ImportReport report = new ImportReport();
            HttpServletRequest req = context.getRequest();
            String data = IOUtils.toString(req.getInputStream(), "UTF-8");
            log.debug("Receiving post: " + data);
            PlayadvisorImporter paimporter = new PlayadvisorImporter(null);

            JSONArray ar = new JSONArray();
            ar.put(new JSONObject(data));

            List<Location> locs = paimporter.processLocations(ar.toString(), report, con);
            logString = "Playadvisor:  " + System.lineSeparator() + report.toLog();

            PlayadvisorExporter pe = new PlayadvisorExporter();
            
            String baseurl = context.getServletContext().getInitParameter("wordpress.baseurl");
            String authkey = context.getServletContext().getInitParameter("wordpress.authkey");
            pe.updateIDs(locs, con,baseurl, authkey);
        } catch (NamingException | SQLException | IOException ex) {
            log.error("Cannot do initial load for playadvisor", ex);
            context.getValidationErrors().add("cronjob", new SimpleError("Error saving:" + ex.getLocalizedMessage()));
            logString = "Error for playadvisor: " + System.lineSeparator();
            logString += ex.getLocalizedMessage();
        }
        return new JsonResolution(logString);
    }

    public Resolution delete() {
        if (location != null) {
            log.debug("Receiving delete: " + location);
            try (Connection con = DB.getConnection()) {
                GeometryJdbcConverter geometryConverter = GeometryJdbcConverterFactory.getGeometryJdbcConverter(con);
                locationHandler = new BeanHandler(Location.class, new BasicRowProcessor(new DbUtilsGeometryColumnConverter(geometryConverter)));
                Location loc;

                StringBuilder sb = new StringBuilder();
                sb.append("select * from ");

                sb.append(DB.LOCATION_TABLE);
                sb.append(" where id = '");
                sb.append(location);

                sb.append("';");
                loc = DB.qr().query(con, sb.toString(), locationHandler);
                if (loc != null) {
                    loc.setRemovedfromplayadvisor(true);
                    Project p = getProject(loc.getProject(), con);
                    Importer imp = new PlaymappingImporter(p);
                    ImportReport report = new ImportReport();
                    imp.saveLocation(loc, report);
                    List<String> errors = report.getErrors();
                    if (errors.isEmpty()) {
                        return new JsonResolution("Success");

                    } else {
                        return new JsonResolution(report.getAllErrors());
                    }
                } else {
                    return new JsonResolution("Error: invalid id given");

                }

            } catch (NamingException | SQLException ex) {
                log.error("Error updating locations", ex);
                return new JsonResolution(ex);
            }
        }
        return new JsonResolution("No location given.");
    }
    
    public Resolution get(){
        
        if (location != null ){
               try (Connection con = DB.getConnection()) {
                GeometryJdbcConverter geometryConverter = GeometryJdbcConverterFactory.getGeometryJdbcConverter(con);
                ResultSetHandler<List<Location>> listHandler = new BeanListHandler(Location.class, new BasicRowProcessor(new DbUtilsGeometryColumnConverter(geometryConverter)));

                List<Location> locs = null;

                locs = DB.qr().query(con, "select * from " + DB.LOCATION_TABLE + " where id = ? and removedfromplaymapping = false and (removedfromplayadvisor = false or removedfromplayadvisor is null);", listHandler, location);
                Location loc = locs.get(0);
                HashMap<Integer, JSONArray> imgsPerLoc = new HashMap<>();
                PlayadvisorExporter pe = new PlayadvisorExporter();
                if ((loc.getRemovedfromplayadvisor() == null || !loc.getRemovedfromplayadvisor()) && (loc.getRemovedfromplaymapping() == null || !loc.getRemovedfromplaymapping())) {
                    imgsPerLoc.put(loc.getId(), pe.retrieveImages(loc.getId(), con, "pietje", null));
                }
                
                JSONObject obj = pe.createLocationJSON(loc, con, imgsPerLoc);
                return new StreamingResolution("application/json", obj.toString(4));
            } catch (NamingException | SQLException | IOException ex) {
                log.error("Error updating locations", ex);
                return new JsonResolution(ex);
            } 
        }
        return new JsonResolution("Geen locatie gevonden");
    }

    public Resolution pushLocationsWithoutImages() {
        //(pushLocation(loc, con, project, imagesPerLocation));  if (location != null) {
        if (location != null || project != null) {

            try (Connection con = DB.getConnection()) {
                GeometryJdbcConverter geometryConverter = GeometryJdbcConverterFactory.getGeometryJdbcConverter(con);
                ResultSetHandler<List<Location>> listHandler = new BeanListHandler(Location.class, new BasicRowProcessor(new DbUtilsGeometryColumnConverter(geometryConverter)));

                PlayadvisorExporter pe = new PlayadvisorExporter();
                //Project p = getProject(loc.getProject(), con);
               // List<Location> locs = DB.qr().query(con, "select * from " + DB.LOCATION_TABLE + " where project = ?", listHandler, location);
                List<Location> locs = null;
                if(project != null){
                    locs = DB.qr().query(con, "select * from " + DB.LOCATION_TABLE + " where project = ?", listHandler, "" +project);
                }else{
                    locs = DB.qr().query(con, "select * from " + DB.LOCATION_TABLE + " where id = ?", listHandler, location);
                }
                
                for (Location loc : locs) {
                    Project p = getProject(loc.getMunicipality(), con);
                    if(p  == null){
                        context.getMessages().add(new SimpleMessage("Locatie overgeslagen want geen project. Loc: " + loc.getTitle() + " - Gemeente: " + loc.getMunicipality() ));
                    }else {
                        try {
                            String baseurl = context.getServletContext().getInitParameter("wordpress.baseurl");
                            String authkey = context.getServletContext().getInitParameter("wordpress.authkey");
                            String result = pe.pushLocation(loc, con, new HashMap<>(), baseurl, authkey);
                            context.getMessages().add(new SimpleMessage(result));
                        }catch(IOException ex){
                            log.error("Canot push location to playadvisor",ex);
                            context.getValidationErrors().add("Error",new SimpleError(ex.getLocalizedMessage()));
                        }
                    }
                }
            } catch (NamingException | SQLException ex) {
                log.error("Error updating locations", ex);context.getMessages().add(new SimpleMessage("FOUT: ",ex));
            }
        }
        return new ForwardResolution(JSP);
    }
    
    public Resolution updateLocation() {
        if (location != null || project != null) {
            try (Connection con = DB.getConnection()) {
                GeometryJdbcConverter geometryConverter = GeometryJdbcConverterFactory.getGeometryJdbcConverter(con);
                ResultSetHandler<List<Location>> listHandler = new BeanListHandler(Location.class, new BasicRowProcessor(new DbUtilsGeometryColumnConverter(geometryConverter)));

                List<Location> locs = null;
                if (project != null) {
                    locs = DB.qr().query(con, "select * from " + DB.LOCATION_TABLE + " where project = ? and removedfromplaymapping = false and (removedfromplayadvisor = false or removedfromplayadvisor is null);", listHandler, "" + project);
                } else {
                    locs = DB.qr().query(con, "select * from " + DB.LOCATION_TABLE + " where id = ? and removedfromplaymapping = false and (removedfromplayadvisor = false or removedfromplayadvisor is null);", listHandler, location);
                }

                String imagepath = context.getServletContext().getInitParameter("wordpress.upload.path");
                String baseurl = context.getServletContext().getInitParameter("wordpress.baseurl");
                String authkey = context.getServletContext().getInitParameter("wordpress.authkey");
                updateLoc(locs, imagepath, baseurl, authkey);
            } catch (NamingException | SQLException ex) {
                log.error("Error updating locations", ex);
            }
        }
        return new ForwardResolution(JSP);
    }
    
    private void updateLoc(List<Location> locs, String imagepath, String baseurl, String authkey){
        PlayadvisorExporter pe = new PlayadvisorExporter();
        
        ImageDownloader downloader = new ImageDownloader(imagepath);
        String result = pe.pushLocations(locs, imagepath, baseurl, authkey, downloader);

        context.getMessages().add(new SimpleMessage(result));
    }

    protected Project getProject(String gemeente, Connection con) throws NamingException, SQLException {
        Project p = DB.qr().query(con, "SELECT id,cronexpressie,type_,username,password,name,log,lastrun,mailaddress,baseurl, status, authkey, imagePath from " + DB.PROJECT_TABLE + " WHERE name ilike ?", projectHandler, "%" + gemeente + "%");
        return p;
    }

    protected Project getProject(Integer projectID, Connection con) throws NamingException, SQLException {
        Project p = DB.qr().query(con, "SELECT id,cronexpressie,type_,username,password,name,log,lastrun,mailaddress,baseurl, status, authkey, imagePath from " + DB.PROJECT_TABLE + " WHERE id = ?", projectHandler, projectID);
        return p;
    }

}
