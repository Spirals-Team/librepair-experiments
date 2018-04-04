package app;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faisal on 2018-03-20.
 */
@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private AcademicYear year;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="COURSE_PROGRAM",
            joinColumns=@JoinColumn(name="COURSE_ID"),
            inverseJoinColumns = @JoinColumn(name="PROGRAM_ID")
    )
    private List<Program> programs = new ArrayList<Program>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.PERSIST)
    private List<LearningOutcome> learningOutcomes = new ArrayList<LearningOutcome>();

    /**
     * Default constructor
     */
    public Course() {}

    /**
     * Constructor for Course
     * @param name - Name for the Course
     */
    public Course(String name) {
        this.name = name;
    }
    /**
     * Constructor for Course
     * @param name - Name for the Course
     * @param description - Description used to display what the course is about
     * @param year - What academic year course belongs to
     */
    public Course(String name, String description, AcademicYear year) {
        this.name = name;
        this.description = description;
        this.year = year;
    }

    /**
     * Constructor for Course
     * @param name - Name for the Course
     * @param description - Description used to display what the course is about
     * @param year - What academic year course belongs to
     * @param p - List of programs the course covers
     */
    public Course(String name, String description, AcademicYear year, List<Program> p) {
        this.name = name;
        this.description = description;
        this.year = year;
        this.programs = p;
    }

    /**
     * Constructor for Course
     * @param name - Name for the Course
     * @param description - Description used to display what the course is about
     * @param year - What academic year course belongs to
     * @param p - List of programs the course covers
     * @param los - List of learning objectives the course covers
     */
    public Course(String name, String description, AcademicYear year, List<Program> p, List<LearningOutcome> los) {
        this.name = name;
        this.description = description;
        this.year = year;
        this.programs = p;
        this.learningOutcomes = los;
    }

    /**
     * Function for adding program
     * @param p - Program to be added
     */
    public void addProgram(Program p){ this.programs.add(p); }

    /**
     * Function for removing program
     * @param p - program to be removed
     */
    public void removeProgram(Program p){ this.programs.remove(p); }

    /**
     * Function for adding learning outcome
     * @param los - learning outcome to be added
     */
    public void addLearningOutcome(LearningOutcome los){ this.learningOutcomes.add(los); }

    /**
     * Function for removing learning outcome
     * @param los - learning outcome to be removed
     */
    public void removeLearningOutcome(LearningOutcome los){ this.learningOutcomes.remove(los); }

    /** Getters **/
    public Long getId() {
        return id;
    }
    public String getDescription() { return description; }
    public String getName() { return name; }
    public AcademicYear getYear() {return year; }
    public List<Program> getPrograms() { return programs; };
    public List<LearningOutcome> getLearningOutcomes() { return learningOutcomes; };



    /** Setters **/
    public void setId(Long objectiveId) {
        this.id = objectiveId;
    }
    public void setDescription(String desc) { this.description = desc; }
    public void setName(String name) { this.name = name; }
    public void setYear(AcademicYear year) {this.year = year; }
    public void setPrograms(List<Program> progs) {this.programs = progs; }
    public void setLearningOutcomes(List<LearningOutcome> los) {this.learningOutcomes = los; }


    /**
     * Overridden toString method to ensure only description is displayed
     * @return
     */
    @Override
    public String toString(){
        return "ID: " +this.getId() + ", " + name + ": " + description;
    }

}

