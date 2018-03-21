package sysc4806;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by CraigBook on 2018-03-06.
 */
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer studentId;
    private String name;

    //    private ProjectRepo project_repo;
//
//
//    private Project currentProject = new Project();
//    public Student(Double studentId, ProjectRepo project_repo) {
//        this.project_repo = project_repo;
//        this.studentId = studentId;
//        project_repo.addStudent_(this);
//    }
//
//    public Student(Double id) {
//        this.studentId = id;
//    }
    public Student(){}

//    public void chooseProject(Project prospectiveProject){
//        currentProject = prospectiveProject;
//    }
//
//    public Project getCurrentProject() {
//        return currentProject;
//    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}