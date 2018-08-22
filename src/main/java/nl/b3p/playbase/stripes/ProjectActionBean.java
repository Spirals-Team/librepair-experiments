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
package nl.b3p.playbase.stripes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.EnumeratedTypeConverter;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import nl.b3p.playbase.ImportReport;
import nl.b3p.playbase.PlayadvisorImporter;
import nl.b3p.playbase.PlaymappingImporter;
import nl.b3p.playbase.WaitPageStatus;
import nl.b3p.playbase.cron.CronListener;
import nl.b3p.playbase.db.DB;
import nl.b3p.playbase.entities.Project;
import nl.b3p.playbase.entities.ProjectType;
import nl.b3p.playbase.entities.Status;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.stripesstuff.plugin.waitpage.WaitPage;

/**
 *
 * @author Meine Toonen
 */
@StrictBinding
@UrlBinding("/action/project/{$event}")
public class ProjectActionBean implements ActionBean {

    private ResultSetHandler<Project> projectHandler = new BeanHandler(Project.class);
    private static final Log log = LogFactory.getLog(ProjectActionBean.class);

    private ActionBeanContext context;
    private static final String JSP = "/WEB-INF/jsp/admin/project/view.jsp";

    private static final String WIZARD_NEW_JSP = "/WEB-INF/jsp/admin/project/createNew.jsp";
    private static final String WIZARD_AFTER_INITIAL_IMPORT = "/WEB-INF/jsp/admin/project/afterInitialImport.jsp";

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private WaitPageStatus status;
    
    @Validate
    private String locationguid;

    @Validate
    private Integer projectid;

    @ValidateNestedProperties({
        @Validate(field = "cronexpressie"),
        @Validate(field = "username"),
        @Validate(field = "password"),
        @Validate(field = "mailaddress"),
        @Validate(field = "name"),
        @Validate(field = "id"),
        @Validate(field = "type_", converter = EnumeratedTypeConverter.class, required = true, on = "save"),
        @Validate(field = "status", converter = EnumeratedTypeConverter.class, required = true, on = "save")
    })
    private Project project = new Project();

    @DefaultHandler
    public Resolution view() {
        if (projectid != null) {
            try {
                project = DB.qr().query("SELECT id,cronexpressie,type_,username,password,name,log,lastrun,mailaddress,baseurl, status,authkey,imagepath from " + DB.PROJECT_TABLE + " WHERE id = ?", projectHandler, projectid);
            } catch (NamingException | SQLException ex) {
                log.error("Cannot load cronjob", ex);
            }
        }
        return new ForwardResolution(JSP);
    }

    public Resolution nieuw() {
        project = new Project();
        projectid = null;
        return new ForwardResolution(WIZARD_NEW_JSP);
    }

    public Resolution removeCron() {
        try {
            CronListener.unscheduleJob(project);
            DB.qr().update("delete from " + DB.PROJECT_TABLE + " WHERE id = ?", project.getId());
        } catch (NamingException | SQLException ex) {
            log.error("Cannot delete cronjob", ex);
            context.getValidationErrors().add("cronjob", new SimpleError("Error deleting:", ex.getLocalizedMessage()));
        }
        return view();
    }

    public Resolution runNow() {
        try {
            CronListener.runNow(project);
        } catch (SchedulerException ex) {
            log.error("Cannot run cronjob", ex);
            context.getValidationErrors().add("cronjob", new SimpleError("Error running:", ex.getLocalizedMessage()));
        }
        return view();
    }

    public Resolution downloadString() {
        ResultSetHandler h = new MapHandler();
        try {
            Map o = (Map) DB.qr().query("SELECT importedstring from " + DB.PROJECT_TABLE + " WHERE id = ?", h, project.getId());

            StreamingResolution res = new StreamingResolution("application/json", (String) o.get("importedstring"));
            res.setFilename(project.getType_().toString() + "-" + project.getName() + project.getId());
            res.setAttachment(true);
            return res;
        } catch (NamingException | SQLException ex) {
            log.error("Cannot retrieve string", ex);
            return view();
        }
    }

    @WaitPage(path = "/WEB-INF/jsp/waitpage.jsp", delay = 2000, refresh = 1000, ajax = "/WEB-INF/jsp/waitpageajax.jsp")
    public Resolution saveNew() {
        project.setStatus(Status.UNDER_REVIEW);
         if (project.getCronexpressie() == null || !CronExpression.isValidExpression(project.getCronexpressie())) {
            context.getValidationErrors().add("cronjob.cronexpressie", new SimpleError("Cronexpressie niet correct"));
            return view();
        }
        doSave();
        if (context.getValidationErrors().size() > 0) {
            return nieuw();
        }
        String baseurl = CronListener.WORDPRESS_BASEURL;
        String authkey = CronListener.AUTHKEY;
        String pmbaseurl = CronListener.PLAYMAPPING_BASEURL;
        executeInitialLoad(baseurl, authkey, pmbaseurl);
        return new ForwardResolution(WIZARD_AFTER_INITIAL_IMPORT);
                
    }

    @WaitPage(path = "/WEB-INF/jsp/waitpage.jsp", delay = 2000, refresh = 1000, ajax = "/WEB-INF/jsp/waitpageajax.jsp")
    public Resolution doInitialLoad() {
        
        String baseurl = CronListener.WORDPRESS_BASEURL;
        String authkey = CronListener.AUTHKEY;
        String pmbaseurl = CronListener.PLAYMAPPING_BASEURL;
        executeInitialLoad(baseurl, authkey, pmbaseurl);
        return new ForwardResolution(WIZARD_AFTER_INITIAL_IMPORT);
    }
    
    public String executeInitialLoad(String wpbaseurl, String authkey, String pmbaseurl) {
        status = new WaitPageStatus();

        status.addLog("Start");
        String logString = "";
        if (project.getType_() == ProjectType.IMPORT_PLAYADVISOR || project.getType_() == ProjectType.PLAYMAPPING_PLAYADVISOR) {
            status.setCurrentAction("Start met laden playadvisor");
            status.addLog("Start met laden playadvisor");
            status.setProgress(10);
            try (Connection con = DB.getConnection()) {
                ImportReport report = new ImportReport();
                PlayadvisorImporter paimporter = new PlayadvisorImporter(project);

                paimporter.initialLoad(project, report, con, wpbaseurl, authkey);
                logString = "Playadvisor:  " + System.lineSeparator() + report.toLog()+ System.lineSeparator() ;
            } catch (NamingException | SQLException ex) {
                log.error("Cannot do initial load for playadvisor", ex);
                context.getValidationErrors().add("cronjob", new SimpleError("Error saving:" + ex.getLocalizedMessage()));
                logString = "Error for playadvisor: " + System.lineSeparator();
                logString += ex.getLocalizedMessage();
            }
        }
        status.setProgress(30);

        if (project.getType_() == ProjectType.IMPORT_PLAYMAPPING || project.getType_() == ProjectType.PLAYMAPPING_PLAYADVISOR) {
            status.setCurrentAction("Start met laden playmapping locaties");
            status.addLog("Start met laden playmapping locaties");
            try (Connection con = DB.getConnection()) {
                PlaymappingImporter pi = new PlaymappingImporter(project);
                ImportReport locationReport = new ImportReport();
                ImportReport assetsReport = new ImportReport();
                status.setProgress(50);
                List<String> pm_guids = pi.importJSONLocationsFromAPI(project.getUsername(), project.getPassword(), pmbaseurl + "/CustomerLocation/GetAll", locationReport, true, con);
                if (pm_guids != null) {
                    status.addLog("Start met laden playmapping assets");
                    status.setCurrentAction("Start met laden playmapping assets");
                    assetsReport = pi.importJSONAssetsFromAPI(project.getUsername(), project.getPassword(), pmbaseurl + "/CustomerAsset/GetByLocationId/", pm_guids, assetsReport, con);
                } else {
                    logString += "Playmapping: " + locationReport.getErrors(ImportReport.ImportType.GENERAL);
                }

                logString += "Playmapping: " + System.lineSeparator() + " Location: " + locationReport.toLog() + System.lineSeparator() + "Assets: " + assetsReport.toLog();

            } catch (NamingException | SQLException ex) {
                log.error("Cannot do initial load for playadvisor", ex);
                context.getValidationErrors().add("cronjob", new SimpleError("Error saving:" + ex.getLocalizedMessage()));
                logString = "Error for playmapping: " + System.lineSeparator();
                logString += ex.getLocalizedMessage();
            }
        }
        status.setProgress(90);
        project.setLog(logString);
        status.setCurrentAction("Eind");
        status.setFinished(true);
        return logString;
    }
    
    @WaitPage(path = "/WEB-INF/jsp/waitpage.jsp", delay = 2000, refresh = 1000, ajax = "/WEB-INF/jsp/waitpageajax.jsp")
    public Resolution loadPMLocation(){
        status = new WaitPageStatus();
        
             
        status.addLog("Start");
        String logString = "";
        String pmbaseurl = CronListener.PLAYMAPPING_BASEURL;
     
        status.setCurrentAction("Start met laden playmapping locatie ");
        status.addLog("Start met laden playmapping locaties");
        try (Connection con = DB.getConnection()) {
            project = DB.qr().query("SELECT id,cronexpressie,type_,username,password,name,log,lastrun,mailaddress,baseurl, status,authkey,imagepath from " + DB.PROJECT_TABLE + " WHERE id = ?", projectHandler, projectid);
            PlaymappingImporter pi = new PlaymappingImporter(project);
            ImportReport locationReport = new ImportReport();
            ImportReport assetsReport = new ImportReport();
            status.setProgress(50);
            List<String> pm_guids = Collections.singletonList(locationguid);
            if (pm_guids != null) {
                status.addLog("Start met laden playmapping assets");
                status.setCurrentAction("Start met laden playmapping assets");
                assetsReport = pi.importJSONAssetsFromAPI(project.getUsername(), project.getPassword(), pmbaseurl + "/CustomerAsset/GetByLocationId/", pm_guids, assetsReport, con);
            } else {
                logString += "Playmapping: " + locationReport.getErrors(ImportReport.ImportType.GENERAL);
            }

            logString += "Playmapping: " + System.lineSeparator() + " Location: " + locationReport.toLog() + System.lineSeparator() + "Assets: " + assetsReport.toLog();

        } catch (NamingException | SQLException ex) {
            log.error("Cannot do initial load for playadvisor", ex);
            context.getValidationErrors().add("cronjob", new SimpleError("Error saving:" + ex.getLocalizedMessage()));
            logString = "Error for playmapping: " + System.lineSeparator();
            logString += ex.getLocalizedMessage();
        }
        
        return new ForwardResolution(JSP);
    }

    public Resolution save() {
        if (project.getCronexpressie() == null || !CronExpression.isValidExpression(project.getCronexpressie())) {
            context.getValidationErrors().add("cronjob.cronexpressie", new SimpleError("Cronexpressie niet correct"));
            return view();
        }
        doSave();
        return view();
    }
    
    private void doSave(){
        try (Connection con = DB.getConnection()) {
            StringBuilder sb = new StringBuilder();
            if (project.getId() == null) {
                sb.append("INSERT ");
                sb.append("INTO ");
                sb.append(DB.PROJECT_TABLE);
                sb.append("(");
                sb.append("username,");
                sb.append("password,");
                sb.append("name,");
                sb.append("type_,");
                sb.append("mailaddress,");
                sb.append("status,");
                sb.append("cronexpressie) ");
                sb.append("VALUES(  ?,?,?,?,?,?,?);");

                project = DB.qr().insert(con, sb.toString(), projectHandler, project.getUsername(), project.getPassword(), project.getName(), project.getType_().name(),
                        project.getMailaddress(), project.getStatus().name(), project.getCronexpressie());
                CronListener.scheduleProject(project);
            } else {
                sb.append("update ");
                sb.append(DB.PROJECT_TABLE);
                sb.append(" set ");
                sb.append("username = ?,");
                sb.append("password = ?,");
                sb.append("name = ?,");
                sb.append("mailaddress= ?,");
                sb.append("status= ?,");
                sb.append("type_= ?,");
                sb.append("cronexpressie = ?");
                sb.append(" where id = ?");

                DB.qr().update(con, sb.toString(), project.getUsername(), project.getPassword(), project.getName(),
                        project.getMailaddress(), project.getStatus().name(), project.getType_().name(),
                        project.getCronexpressie(), project.getId());
                CronListener.rescheduleJob(project);
                // ToDo: als status == published, zet alle locaties van dit project op published. Evenals unpublished
            }
        } catch (NamingException | SQLException | SchedulerException ex) {
            log.error("Cannot save cronjob", ex);
            context.getValidationErrors().add("cronjob", new SimpleError("Error saving:" + ex.getLocalizedMessage()));
        }
    }

    public Resolution tabledata() {
        JSONObject result = new JSONObject();

        GsonBuilder builder = new GsonBuilder();
        builder.serializeSpecialFloatingPointValues();
        Gson gson = builder.create();

        try {

            ResultSetHandler<List<Project>> handler = new BeanListHandler(Project.class);
            String sql = "select id,cronexpressie,type_,username,password,name,log,lastrun, status, authkey from " + DB.PROJECT_TABLE;
            List<Project> jobs = DB.qr().query(sql, handler);
            JSONArray ar = new JSONArray();
            for (Project job : jobs) {
                JSONObject obj = new JSONObject(gson.toJson(job, Project.class));
                Date d = CronListener.getNextFireTime(job);
                String formattedDate = d != null ? sdf.format(d) : "";
                obj.put("next_fire_time", formattedDate);
                obj.put("lastrun", obj.optString("lastrun", " - "));
                obj.put("type_", obj.optString("type_", " - "));
                obj.put("authkey", obj.optString("authkey", " - "));
                ar.put(obj);
            }
            result.put("data", ar);

        } catch (NamingException | SQLException ex) {
            log.error("Cannot get geometryConverter: ", ex);
            result.put("message", "Cannot get geometryConverter: " + ex.getLocalizedMessage());
        }

        StreamingResolution res = new StreamingResolution("application/json", result.toString(4));
        res.setFilename("");
        res.setAttachment(true);
        return res;
    }

    // <editor-fold desc="getters and setters" defaultstate="collapsed">
    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public WaitPageStatus getStatus() {
        return status;
    }

    public void setStatus(WaitPageStatus status) {
        this.status = status;
    }
        
    public String getLocationguid() {
        return locationguid;
    }

    public void setLocationguid(String locationguid) {
        this.locationguid = locationguid;
    }
    // </editor-fold>

}
