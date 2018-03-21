package sysc4806;

import java.util.*;
/*
*
 * Created by CraigBook on 2018-03-06.
*/


public class Prof {
    ProjectRepo_ project_repo;
    public Prof() {

    }

    public Prof(ProjectRepo_ project_repo) {
        this.project_repo = project_repo;
        project_repo.addProf(this);
    }

    public void addProject(String title, String description, String programs, int studentLimit) {
        Project project_ = new Project(title, description, programs, studentLimit);
        project_repo.addProject(project_);
    }

    public void deleteProject(Project project_){
        project_repo.deleteProject(project_);
    }

    public void archiveProject(Project project_){
        project_repo.archiveProject(project_);
    }
}
