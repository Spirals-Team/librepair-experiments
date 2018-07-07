package pl.sda.jira.template;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "heroes")
public class Template {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, updatable = false, name = "first_name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Convert(converter = DescriptionConverter.class)
    private Description description;

    @Embedded
    private FullName fullName;

    private Template() {}

    public Template(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
        fullName = new FullName(name, lastName);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return fullName.getName();
    }

    public String getFullName() {
        return fullName.value();
    }

    public String getDescription() {
        return description.value();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return Objects.equals(id, template.id) &&
                Objects.equals(name, template.name) &&
                Objects.equals(lastName, template.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, description);
    }
}
