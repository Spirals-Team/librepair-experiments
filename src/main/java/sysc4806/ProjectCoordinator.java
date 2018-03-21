package sysc4806;

import java.util.List;

/*
*
 * Created by CraigBook on 2018-03-06.
*/


public class ProjectCoordinator {
    public List<Student> projectlessStudents;
    public ProjectRepo_ project_repo;
    public ProjectCoordinator(ProjectRepo_ project_repo){
        this.project_repo = project_repo;
        checkRogueStudents();
    }

    public void checkRogueStudents(){
        for (Student student_: project_repo.getStudent_s()){
            /*if(!student_.getCurrentProject().validProject()){
                projectlessStudents.add(student_);
            }*/
        }
    }

    public void notifyStudents(String message) {

    }
}
