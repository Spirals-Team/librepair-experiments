package edu.itu.cavabunga.core.entity;

import com.fasterxml.jackson.annotation.*;
import edu.itu.cavabunga.core.entity.property.*;
import edu.itu.cavabunga.core.entity.property.Class;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force=true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Acknowledged.class, name = "Acknowledged"),
        @JsonSubTypes.Type(value = Action.class, name = "Action"),
        @JsonSubTypes.Type(value = Attach.class, name = "Attach"),
        @JsonSubTypes.Type(value = Calscale.class, name = "Calscale"),
        @JsonSubTypes.Type(value = Catagories.class, name = "Catagories"),
        @JsonSubTypes.Type(value = Class.class, name = "Class"),
        @JsonSubTypes.Type(value = Comment.class, name = "Comment"),
        @JsonSubTypes.Type(value = Completed.class, name = "Completed"),
        @JsonSubTypes.Type(value = Contact.class, name = "Contact"),
        @JsonSubTypes.Type(value = Country.class, name = "Country"),
        @JsonSubTypes.Type(value = Created.class, name = "Created"),
        @JsonSubTypes.Type(value = Description.class, name = "Description"),
        @JsonSubTypes.Type(value = Dtend.class, name = "Dtend"),
        @JsonSubTypes.Type(value = Dtstamp.class, name = "Dtstamp"),
        @JsonSubTypes.Type(value = Dtstart.class, name = "Dtstart"),
        @JsonSubTypes.Type(value = Due.class, name = "Due"),
        @JsonSubTypes.Type(value = Duration.class, name = "Duration"),
        @JsonSubTypes.Type(value = Exdate.class, name = "Exdate"),
        @JsonSubTypes.Type(value = Freebusy.class, name = "Freebusy"),
        @JsonSubTypes.Type(value = Geo.class, name = "Geo"),
        @JsonSubTypes.Type(value = Lastmod.class, name = "Lastmod"),
        @JsonSubTypes.Type(value = Location.class, name = "Location"),
        @JsonSubTypes.Type(value = Method.class, name = "Method"),
        @JsonSubTypes.Type(value = Organizer.class, name = "Organizer"),
        @JsonSubTypes.Type(value = Percent.class, name = "Percent"),
        @JsonSubTypes.Type(value = Priority.class, name = "Priority"),
        @JsonSubTypes.Type(value = Prodid.class, name = "Prodid"),
        @JsonSubTypes.Type(value = Rdate.class, name = "Rdate"),
        @JsonSubTypes.Type(value = Recurid.class, name = "Recurid"),
        @JsonSubTypes.Type(value = Related.class, name = "Related"),
        @JsonSubTypes.Type(value = Repeat.class, name = "Repeat"),
        @JsonSubTypes.Type(value = Resources.class, name = "Resources"),
        @JsonSubTypes.Type(value = Rrule.class, name = "Rrule"),
        @JsonSubTypes.Type(value = Rstatus.class, name = "Rstatus"),
        @JsonSubTypes.Type(value = Seq.class, name = "Seq"),
        @JsonSubTypes.Type(value = Status.class, name = "Status"),
        @JsonSubTypes.Type(value = Summary.class, name = "Summary"),
        @JsonSubTypes.Type(value = Transp.class, name = "Transp"),
        @JsonSubTypes.Type(value = Trigger.class, name = "Trigger"),
        @JsonSubTypes.Type(value = Tzid.class, name = "Tzid"),
        @JsonSubTypes.Type(value = Tzname.class, name = "Tzname"),
        @JsonSubTypes.Type(value = Tzoffsetfrom.class, name = "Tzoffsetfrom"),
        @JsonSubTypes.Type(value = Tzoffsetto.class, name = "Tzoffsetto"),
        @JsonSubTypes.Type(value = Tzurl.class, name = "Tzurl"),
        @JsonSubTypes.Type(value = Uid.class, name = "Uid"),
        @JsonSubTypes.Type(value = Url.class, name = "Url"),
        @JsonSubTypes.Type(value = Version.class, name = "Version")
})
public abstract class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "component_id")
    @JsonBackReference
    private Component component;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Parameter> parameters = new ArrayList<Parameter>();

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(Parameter parameter){
        parameter.setProperty(this);
        parameters.add(parameter);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
