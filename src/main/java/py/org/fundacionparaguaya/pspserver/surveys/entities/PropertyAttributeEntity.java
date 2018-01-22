package py.org.fundacionparaguaya.pspserver.surveys.entities;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by rodrigovillalba on 10/16/17.
 */
@Entity
@Table(name = "snapshots_properties_attributes", schema = "data_collect")
public class PropertyAttributeEntity implements Serializable {

    @Id
    @Column(name = "property_system_name")
    private String propertySystemName;

    @Column(name = "property_schema_name")
    private String propertySchemaName;


    @Column(name = "stoplight_type")
    @Enumerated(EnumType.STRING)
    private StopLightType stoptLightType;

    @Embedded
    @Column(name = "property_title")
    private EmbeddablePropertyTitle embeddablePropertyTitle;

    @Column(name = "stoplight_group")
    @Enumerated(EnumType.STRING)
    private StopLightGroup stopLightGroup;

    public PropertyAttributeEntity() {
    }

    public String getPropertySystemName() {
        return propertySystemName;
    }

    public void setPropertySystemName(String propertySystemName) {
        this.propertySystemName = propertySystemName;
    }

    public StopLightType getStoptLightType() {
        return stoptLightType;
    }

    public void setStoptLightType(StopLightType stoptLightType) {
        this.stoptLightType = stoptLightType;
    }

    public EmbeddablePropertyTitle getEmbeddablePropertyTitle() {
        return embeddablePropertyTitle;
    }

    public void setEmbeddablePropertyTitle(EmbeddablePropertyTitle embeddablePropertyTitle) {
        this.embeddablePropertyTitle = embeddablePropertyTitle;
    }

    public String getPropertySchemaName() {
        return propertySchemaName;
    }

    public void setPropertySchemaName(String propertySchemaName) {
        this.propertySchemaName = propertySchemaName;
    }

    public StopLightGroup getStopLightGroup() {
        return stopLightGroup;
    }

    public void setStopLightGroup(StopLightGroup stopLightGroup) {
        this.stopLightGroup = stopLightGroup;
    }

   
}
