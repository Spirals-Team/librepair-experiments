package asw.inci_manager.inci_manager_gest.entities;

import asw.inci_manager.util.Estado;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Entity
@Table(name = "Incidences")
public class Incidence {

    // Id generado automáticamente para diferenciar cada uno (para mapear)
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Agent agent;                    // nombre de usuario del agente
    private String incidenceName;
    private String description;
    private String location;                    // formato de la localización: "45.67, 32.86"

    @ElementCollection(targetClass = String.class)
    private Set<String> labels;                // etiquetas de la incidencia

    private HashMap<String, String> campos;     // campos con propiedad valor
    private Estado status;                      // Ver Enum: "Estado". Ej: ABIERTA, EN_PROCESO, CERRADA, ANULADA
    private Date expiration;                    // fecha de caducidad, ej: en caso de los sensores de temperatura

    public Incidence() {
    }

    /**
     * Constructor con todos los parametros
     *
     * @param agent
     * @param incidenceName
     * @param description
     * @param location
     * @param labels
     * @param campos
     * @param status
     * @param expiration
     */
    public Incidence(Agent agent, String incidenceName, String description, String location, Set<String> labels, HashMap<String, String> campos, Estado status, Date expiration) {
        this.agent = agent;
        this.incidenceName = incidenceName;
        this.description = description;
        this.location = location;
        this.labels = labels;
        this.campos = campos;
        this.status = status;
        this.expiration = expiration;
    }


    public Incidence(Agent agent, String incidenceName, String description, String location, Set<String> labels) {
        this.agent = agent;
        this.incidenceName = incidenceName;
        this.description = description;
        this.location = location;
        this.labels = labels;
        this.status = Estado.ABIERTA;
    }

    public Long getId() {
        return id;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getIncidenceName() {
        return incidenceName;
    }

    public void setIncidenceName(String incidenceName) {
        this.incidenceName = incidenceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }

    public HashMap<String, String> getCampos() {
        return campos;
    }

    public void setCampos(HashMap<String, String> campos) {
        this.campos = campos;
    }

    public Estado getStatus() {
        return status;
    }

    public void setStatus(Estado status) {
        this.status = status;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
