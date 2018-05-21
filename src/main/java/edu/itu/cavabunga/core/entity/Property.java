package edu.itu.cavabunga.core.entity;

import com.fasterxml.jackson.annotation.*;
import edu.itu.cavabunga.core.entity.property.*;
import edu.itu.cavabunga.core.entity.property.Class;
import edu.itu.cavabunga.exception.Validation;
import lombok.Data;
import org.hibernate.annotations.DiscriminatorOptions;
import org.omg.PortableServer.THREAD_POLICY_ID;

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
@Data
public abstract class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String value;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id")
    @JsonBackReference
    private Component component;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Parameter> parameters = new ArrayList<>();

    public void addParameter(Parameter parameter){
        parameter.setProperty(this);
        parameters.add(parameter);
    }

    public void validate(){
        if(!this.parameters.isEmpty()){
            for(Parameter pr : this.parameters){
                try{
                    pr.validate();
                }catch (Exception e){
                    throw new Validation(this.getClass().getName() + " property class validation failed: " + e.getMessage());
                }
            }
        }
    }

    public void validateMustHaveParameters(List<Parameter> parameterList){
        if(parameterList != null && !parameterList.isEmpty()) {
            Integer instanceCount = parameterList.size();
            Integer foundInstance = 0;
            for (Parameter pr : parameterList) {
                for (Parameter search : parameters) {
                    if (pr.getClass().getName().equals(search.getClass().getName())) {
                        foundInstance++;
                    }
                }
            }

            if (foundInstance != instanceCount) {
                throw new Validation(this.getClass().getName() + "property musthaveList is not valid");
            }
        }
    }

    public void validateCannotHaveParameters(List<Parameter> parameterList){
        if(parameterList != null && !parameterList.isEmpty()) {
            for (Parameter pr : parameterList) {
                for (Parameter search : parameters) {
                    if (pr.getClass().getName().equals(search.getClass().getName())) {
                        throw new Validation(this.getClass().getName() + " property cannot have child parameter of: " + search.getClass().getName());
                    }
                }
            }
        }
    }

    public void validateValueType(PropertyValueType propertyValueType){
        if(propertyValueType != null) {
            if (propertyValueType == PropertyValueType.BINARY) {
                if (!value.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$")) {
                    throw new Validation(this.getClass().getName() + " value is not valid BINARY type");
                }
            }

            if (propertyValueType == PropertyValueType.BOOLEAN) {
                if (value != "TRUE" && value != "FALSE") {
                    throw new Validation(this.getClass().getName() + " value is not valid BOOLEAN type " + value);
                }
            }

            if (propertyValueType == PropertyValueType.CALADDRESS) {
                //TODO: there is a full rfc about it: rfc3986
            }

            if (propertyValueType == PropertyValueType.DATE) {
                String[] parts = value.split(",");
                for (String part : parts) {
                    if (!part.matches("^[0-9]{8}$")) {
                        throw new Validation(this.getClass().getName() + " value is not valid DATE type " + value);
                    }
                }
            }

            if (propertyValueType == PropertyValueType.DATETIME) {
                String[] parts = value.split(",");
                for (String part : parts) {
                    if (!part.matches("^[0-9]{8}T[0-9]{6}|[0-9]{8}T[0-9]{6}Z")) {
                        throw new Validation(this.getClass().getName() + " value is not valid DATE-TIME type " + value);
                    }
                }
            }

            if (propertyValueType == PropertyValueType.DURATION) {
                //TODO: rfc5545 pg 35
            }

            if (propertyValueType == PropertyValueType.FLOAT) {
                String[] parts = value.split(",");
                for (String part : parts) {
                    try {
                        Float f = Float.parseFloat(value);
                    } catch (NumberFormatException e) {
                        //TODO: GEO property has a value type in 'float;float' format !!
                        throw new Validation(this.getClass().getName() + " value is not valid FLOAT type " + value);
                    }
                }
            }

            if (propertyValueType == PropertyValueType.INTEGER) {
                String[] parts = value.split(",");
                for (String part : parts) {
                    try {
                        Integer i = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        throw new Validation(this.getClass().getName() + " value is not valid INTEGER type" + value);
                    }
                }
            }

            if (propertyValueType == PropertyValueType.PERIOD) {
                //TODO: rfc5545 pg 36
            }

            if (propertyValueType == PropertyValueType.RECUR) {
                //TODO: rfc5545 pg 37-45
            }

            if (propertyValueType == PropertyValueType.TEXT) {
                //TODO: rfc5545 pg 45
            }

            if (propertyValueType == PropertyValueType.TIME) {
                String[] parts = value.split(",");
                for (String part : parts) {
                    if (!part.matches("^[0-9]{6}$|^[0-9]{6}Z$")) {
                        throw new Validation(this.getClass().getName() + " value is not valid TIME type " + value);
                    }
                }
            }

            if (propertyValueType == PropertyValueType.URI) {
                //TODO: there is a full rfc about it: rfc3986
            }

            if (propertyValueType == PropertyValueType.UTCOFFSET) {
                String[] parts = value.split(",");
                for (String part : parts) {
                    if (!part.matches("^[-+][0-9]{4}$|^[-+][0-9]{4}Z$")) {
                        throw new Validation(this.getClass().getName() + " value is not valid TIME type " + value);
                    }
                }
            }
        }
    }
}
