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
package nl.b3p.playbase.cron;

import java.sql.Connection;
import nl.b3p.playbase.entities.ProjectType;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import net.sourceforge.stripes.action.ActionBeanContext;
import nl.b3p.loader.jdbc.GeometryJdbcConverter;
import nl.b3p.loader.jdbc.GeometryJdbcConverterFactory;
import nl.b3p.loader.util.DbUtilsGeometryColumnConverter;
import nl.b3p.mail.Mailer;
import nl.b3p.playbase.ImageDownloader;
import nl.b3p.playbase.PlayadvisorExporter;
import nl.b3p.playbase.db.DB;
import nl.b3p.playbase.entities.Location;
import nl.b3p.playbase.entities.Project;
import nl.b3p.playbase.stripes.ProjectActionBean;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Meine Toonen
 */
public class PlaybaseJob implements Job {

    private static final Log log = LogFactory.getLog(PlaybaseJob.class);
    private ImageDownloader downloader;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        JobDataMap jdm = jec.getJobDetail().getJobDataMap();
        Project project = (Project) jdm.get(CronListener.QUARTZ_JOB_DATA_MAP_ENTITY_KEY);
        log.info("Executing playbasejob " + project.getId() + ". Type is " + project.getType_().toString());
        ProjectType ct = project.getType_();

        String imagepath = (String)jdm.get(CronListener.KEY_IMAGEPATH);
        String wpbaseurl = (String)jdm.get(CronListener.KEY_WORDPRESS_BASEURL);
        String authkey = (String)jdm.get(CronListener.KEY_AUTHKEY);
        String pmbaseurl = (String)jdm.get(CronListener.KEY_PLAYMAPPING_BASEURL);
        ProjectActionBean pab = new ProjectActionBean();
        pab.setProject(project);
        pab.setContext(new ActionBeanContext());
        String logstring = pab.executeInitialLoad(wpbaseurl,authkey, pmbaseurl);
      
        if (project.getType_() == ProjectType.PLAYMAPPING_PLAYADVISOR) {
            try (Connection con = DB.getConnection()) {
                GeometryJdbcConverter geometryConverter = GeometryJdbcConverterFactory.getGeometryJdbcConverter(con);
                ResultSetHandler<List<Location>> listHandler = new BeanListHandler(Location.class, new BasicRowProcessor(new DbUtilsGeometryColumnConverter(geometryConverter)));

                List<Location> locs = DB.qr().query(con, "select * from " + DB.LOCATION_TABLE + " where project = ? and (removedfromplaymapping is null or removedfromplaymapping = false) and (removedfromplayadvisor = false or removedfromplayadvisor is null);", listHandler, "" + project);

                PlayadvisorExporter pe = new PlayadvisorExporter();

                downloader = new ImageDownloader(imagepath);
                String result = pe.pushLocations(locs, imagepath, wpbaseurl, authkey, downloader);
                logstring += result;
                pe.updateIDs(locs, con, wpbaseurl, authkey);
            } catch (NamingException | SQLException ex) {
                log.error("Error updating locations", ex);
            }
        }
        
        sendMail(project, logstring);
    }

    private void sendMail(Project cronjob, String logString) {
        if (cronjob.getMailaddress() != null) {
            String subject = "Playbase cron status: " + cronjob.getType_().toString() + " voor project " + cronjob.getName();
            StringBuilder content = new StringBuilder();
            content.append("Status rapport ").append(cronjob.getName());
            content.append(System.lineSeparator());
            content.append("Log: ");
            content.append(System.lineSeparator());
            content.append(logString);
            try {
                Mailer.sendMail("Playbase", "support@b3partners.nl", cronjob.getMailaddress(), subject, content.toString());
            } catch (Exception ex) {
                log.error("Cannot send mail:", ex);
            }
        }
    }
}
