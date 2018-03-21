package sysc4806;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/*
*
 * Created by CraigBook on 2018-03-06.
*/

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


    private String title;
    private String description;
    private String programs;
    private int studentLimit;
    public Project(){
//        this();
        this("","", "", 0);
//        this
    }

    public Project(String title, String description, String programs, int studentLimit) {
        this.title = title;
        this.description = description;
        this.programs = programs;
        this.studentLimit = studentLimit;
    }

    public boolean validProject(){
        return (title != "" && description != "" && programs != "" && studentLimit!=0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrograms() {
        return programs;
    }

    public void setPrograms(String programs) {
        this.programs = programs;
    }

    public int getStudentLimit() {
        return studentLimit;
    }

    public void setStudentLimit(int studentLimit) {
        this.studentLimit = studentLimit;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
