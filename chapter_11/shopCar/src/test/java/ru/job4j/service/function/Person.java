package ru.job4j.service.function;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 17.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
@Getter
@Setter
public class Person {
    private static final Logger LOG = LogManager.getLogger(Person.class);
    private String name;
    private Date birth;
    private Double grade;

    public Person(String name, Date birth, Double grade) {
        this.name = name;
        this.birth = birth;
        this.grade = grade;
    }

    public Double getGrade() {
        return grade;
    }
}
