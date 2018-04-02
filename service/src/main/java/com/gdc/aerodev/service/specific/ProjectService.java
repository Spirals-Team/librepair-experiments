package com.gdc.aerodev.service.specific;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.specific.ProjectDao;
import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;
import com.gdc.aerodev.service.GenericService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class ProjectService extends GenericService {

    private final ProjectDao dao;

    public ProjectService() {
        this.dao = new ProjectDao(new JdbcTemplate(), getTableName("project.table"));
    }

    public ProjectService(DataSource testDb, String tableName){
        this.dao = new ProjectDao(new JdbcTemplate(testDb), tableName);
    }

    /**
     * Inserts {@code Project} into database configured by input parameters.
     *
     * @param projectName name of new {@code Project}
     * @param projectOwner ID of {@code User}, who created this {@code Project}
     * @param projectType {@code ProjectType} of current {@code Project}
     * @param projectDescription text with description of {@code Project}
     * @return (0) {@param projectId} of inserted {@code Project}
     *         (1) or {@code null} in cause of problems
     */
    public Long createProject(String projectName, Long projectOwner, ProjectType projectType, String projectDescription) {
        if (projectName.equals("") || projectOwner == null || projectDescription.equals("")) {
            return null;
        }
        if (isExistentName(projectName)) {
            return null;
            //TODO: plug in logger
//            return "Project with name '" + projectName + "' is already exists.";
        }
        try {
            return dao.save(new Project(projectName, projectOwner, projectType, projectDescription));
//            return "Project '" + projectName + "' created with id " + id + ".";
        } catch (DaoException e) {
            return null;
        }
    }

    /**
     * Updates existent {@code Project} chosen by {@param projectId} with input parameters. If there is no need to
     * change some parameter, it should be left as empty ones.
     *
     * @param projectId ID of updating {@code Project}
     * @param projectName new name of updating {@code Project}
     * @param projectType new {@code ProjectType} of updating {@code Project}
     * @param projectDescription new description for {@code Project}
     * @return (0) {@param projectId} of updated {@code Project}
     *         (1) or {@code null} in cause of problems
     */
    public Long updateProject(Long projectId, String projectName, ProjectType projectType, String projectDescription) {
        Project project = dao.getById(projectId);
        if (!projectName.equals("")) {
            if (isExistentName(projectName)) {
                return null;
//                return "Project with name '" + projectName + "' is already exists.";
            }
            project.setProjectName(projectName);
        } else if (projectDescription.equals("")){
            return null;
        }
        if (!projectDescription.equals("")){
            project.setProjectDescription(projectDescription);
        }
        project.setProjectType(projectType);
        try{
            return dao.save(project);
//            return "Project '" + projectName + "' successfully updated.";
        } catch (DaoException e){
            return null;
        }
    }

    public ProjectDao getDao(){
        return dao;
    }

    private boolean isExistentName(String projectName) {
        return dao.getByName(projectName) != null;
    }

}
