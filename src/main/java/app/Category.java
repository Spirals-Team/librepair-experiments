package app;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<LearningOutcome> learningOutcomes = new ArrayList<LearningOutcome>();

    /**
     * Default constructor
     */
    public Category() {}

    /**
     * Default constructor for when description is no provided.
     * @param name
     */
    public Category(String name) {
        this.name = name;
        this.description = "";
    }

    /**
     * Constructor for category
     * @param name - Name of the category, e.g. Software Engineering
     * @param description - Description for the category
     */
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Constructor for category when name, description and learning outcomes provided
     * @param name - Name of the category, e.g. Software Engineering
     * @param description - Description for the category
     * @param los - List of learning outcomes belonging to this category
     */
    public Category(String name, String description, List<LearningOutcome> los) {
        this.name = name;
        this.description = description;
        this.learningOutcomes = los;
    }

    /**
     * Function for adding learning outcome
     * @param lo - Learning outcome to be added
     */
    public void addLearningOutcome(LearningOutcome lo){ this.learningOutcomes.add(lo); }

    /**
     * Function for removing learning outcome
     * @param lo - Learning outcome to be removed
     */
    public void removeLearningOutcome(LearningOutcome lo){ this.learningOutcomes.remove(lo); }


    /**
     * Overridden toString method that returns category name followed by a
     * description for the category
     * @return String - description of the category
     */
    @Override
    public String toString(){
        return "ID: " +this.getId() + ", " + name + ": " + description;
    }

}
