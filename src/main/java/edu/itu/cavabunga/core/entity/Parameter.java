package edu.itu.cavabunga.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.itu.cavabunga.core.entity.parameter.*;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force=true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Altrep.class, name = "Altrep"),
        @JsonSubTypes.Type(value = Cn.class, name = "Cn"),
        @JsonSubTypes.Type(value = Cutype.class, name = "Cutype"),
        @JsonSubTypes.Type(value = DelegatedFrom.class, name = "DelegatedFrom"),
        @JsonSubTypes.Type(value = DelegatedTo.class, name = "DelegatedTo"),
        @JsonSubTypes.Type(value = Dir.class, name = "Dir"),
        @JsonSubTypes.Type(value = Encoding.class, name = "Encoding"),
        @JsonSubTypes.Type(value = Fbtype.class, name = "Fbtype"),
        @JsonSubTypes.Type(value = Fmttype.class, name = "Fmttype"),
        @JsonSubTypes.Type(value = Language.class, name = "Language"),
        @JsonSubTypes.Type(value = Member.class, name = "Member"),
        @JsonSubTypes.Type(value = Partstat.class, name = "Partstat"),
        @JsonSubTypes.Type(value = Range.class, name = "Range"),
        @JsonSubTypes.Type(value = Related.class, name = "Related"),
        @JsonSubTypes.Type(value = Reltype.class, name = "Reltype"),
        @JsonSubTypes.Type(value = Role.class, name = "Role"),
        @JsonSubTypes.Type(value = Rsvp.class, name = "Rsvp"),
        @JsonSubTypes.Type(value = SentBy.class, name = "SentBy"),
        @JsonSubTypes.Type(value = Tzid.class, name = "Tzid"),
        @JsonSubTypes.Type(value = Value.class, name = "Value")
})
public abstract class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id")
    @JsonBackReference
    private Property property;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
