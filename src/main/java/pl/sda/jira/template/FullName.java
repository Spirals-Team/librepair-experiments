package pl.sda.jira.template;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FullName {
    private String name;

    @Column(name = "full_last_name")
    private String lastName;

    private FullName() {}

    public FullName(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String value() {
        return name + " " + lastName;
    }
}
