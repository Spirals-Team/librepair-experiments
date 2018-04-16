package com.gdc.aerodev.service;

import com.gdc.aerodev.model.Project;
import com.gdc.aerodev.model.ProjectType;

public interface GenericProjectService extends GenericService {

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
    Long createProject(String projectName, Long projectOwner, ProjectType projectType, String projectDescription);

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
    Long updateProject(Long projectId, String projectName, ProjectType projectType, String projectDescription);

    Project getProject(String name);

    Project getProject(Long id);

}
