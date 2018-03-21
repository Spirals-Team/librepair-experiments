package sysc4806;

import java.util.ArrayList;
import java.util.List;

/*
*
 * Created by CraigBook on 2018-03-06.
*/


public class ProjectRepo_ {
    private List<Prof> prof_s = new ArrayList<>();

    public List<Prof> getProf_s() {
        return prof_s;
    }

    public List<Student> getStudent_s() {
        return student_s;
    }

    private List<Student> student_s = new ArrayList<>();

    private List <Project> archivedProjects = new ArrayList<>();

    public List<Project> getArchivedProjects() {
        return archivedProjects;
    }

    public List<Project> getProject_s() {
        return project_s;
    }

    private List <Project> project_s = new ArrayList<>();
    public void addProject(Project project_) {
        project_s.add(project_);
    }

    public void deleteProject(Project project_) {
        project_s.remove(project_);
    }

    public void archiveProject(Project project_) {
        archivedProjects.add(project_);
        deleteProject(project_);
    }

    public void addProf(Prof prof_) {
        prof_s.add(prof_);
    }

    public void addStudent_(Student student_) {
        student_s.add(student_);
    }
}
