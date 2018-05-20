package edu.itu.cavabunga.core.entity;

import com.fasterxml.jackson.annotation.*;
import edu.itu.cavabunga.core.entity.component.*;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.exception.Validation;
import lombok.Data;
import org.apache.commons.lang.Validate;
import org.hibernate.annotations.DiscriminatorOptions;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorOptions(force=true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Alarm.class, name = "Alarm"),
        @JsonSubTypes.Type(value = Calendar.class, name = "Calendar"),
        @JsonSubTypes.Type(value = Daylight.class, name = "Daylight"),
        @JsonSubTypes.Type(value = Event.class, name = "Event"),
        @JsonSubTypes.Type(value = Freebusy.class, name = "Freebusy"),
        @JsonSubTypes.Type(value = Journal.class, name = "Journal"),
        @JsonSubTypes.Type(value = Standard.class, name = "Standard"),
        @JsonSubTypes.Type(value = Timezone.class, name = "Timezone"),
        @JsonSubTypes.Type(value = Todo.class, name = "Todo")
})

@Data
public abstract class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_uuid")
    @JsonBackReference(value = "participantAndComponent")
    private Participant owner;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="parent_id")
    @JsonBackReference
    private Component parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Component> components = new ArrayList<>();

    @OneToMany(mappedBy = "component", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Property> properties = new ArrayList<>();

    @CreatedDate
    private Date creationDate;

    public void addComponent(Component component){
        component.setParent(this);
        component.setOwner(this.owner);
        components.add(component);
    }

    public void addProperty(Property property){
        property.setComponent(this);
        properties.add(property);
    }

    public void validate(){
        if(!components.isEmpty()){
            for (Component c : components){
                try {
                    c.validate();
                }catch (Exception e){
                    throw new Validation(this.getClass().getName() + " component's sub component validation failed: " + e.getMessage());
                }
            }
        }

        if(!properties.isEmpty()){
            for(Property p : properties){
                try {
                    p.validate();
                }catch (Exception e){
                    throw new Validation(this.getClass().getName() + " component property validateion failed " + e.getMessage());
                }
            }
        }
    }

    public void validateOptionalOneProperties(List<PropertyType> propertyTypeList){
        Integer propertyCount = 0;
        for(PropertyType pt : propertyTypeList){
            for(Property p : properties){
                if(p.getClass().getName().equals(pt.create().getClass().getName())){
                    propertyCount++;
                }

                if(propertyCount >= 2){
                    throw new Validation("Component validation failed in optional-one properties check: " + p.getClass().getName());
                }
            }
            propertyCount = 0;
        }
    }

    public void validateOptionalManyProperties(){
        //
    }

    public void validateRequiredOneProperties(List<PropertyType> propertyTypeList){
        Integer propertyCount = 0;
        for(PropertyType pt : propertyTypeList){
            for(Property p : properties){
                if(p.getClass().getName().equals(pt.create().getClass().getName())){
                    propertyCount++;
                }

                if(propertyCount >= 2 ){
                    throw new Validation("Component validation failed in required-one properties check. Count >= 2: " + p.getClass().getName());
                }
            }

            if(propertyCount == 0){
                throw new Validation("Component validation failed in required-one properties check. Count 0: " + pt.create().getClass().getName());
            }

        }
    }

    public void validateReqiredManyProperties(List<PropertyType> propertyTypeList){
        Integer propertyCount = 0;
        for(PropertyType pt : propertyTypeList){
            for(Property p : properties){
                if(p.getClass().getName().equals(pt.create().getClass().getName())){
                    propertyCount++;
                }
            }

            if(propertyCount == 0){
                throw new Validation("Component validation failed in required-many properties check: " + pt.create().getClass().getName());
            }

        }
    }
}
