package pl.kayzone.exchange.model.entity;

import org.mongodb.morphia.annotations.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(value="currency")
@Indexes({
        @Index(value = "idCode", fields = @Field("idCode")),
        @Index(value = "urlNBP", fields = @Field("urlNBP"))
})
public class Currency implements Serializable
{
    @Id
    private String idCode;
    private String name;
    @Property("urlNBP")
    private String urlNbp;
    private String tablesType;
    private Double rates;
    private LocalDateTime lastUpdate;
    private final static long serialVersionUID = -8773799396147693543L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Currency() {
    }

    /**
     *
     * @param name
     * @param idCode
     * @param tablesType
     * @param rates
     * @param urlNbp
     */
    public Currency(String idCode, String name, String urlNbp, String tablesType, Double rates) {
        super();
        this.idCode = idCode;
        this.name = name;
        this.urlNbp = urlNbp;
        this.tablesType = tablesType;
        this.rates = rates;
        this.lastUpdate = LocalDateTime.now();
    }

    public Currency(String idCode, Double rates) {
        this(idCode, "", "","A",1.0);
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlNbp() {
        return urlNbp;
    }

    public void setUrlNbp(String urlNbp) {
        this.urlNbp = urlNbp;
    }


    public String getTablesType() {
        return tablesType;
    }

    public void setTablesType(String tablesType) {
        this.tablesType = tablesType;
    }

    public Double getRates() {
        return rates;
    }

    public void setRates(Double rates) {
        this.rates = rates;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return //Objects.equals(getIdCode(), currency.getIdCode()) &&
                Objects.equals(getName(), currency.getName()) &&
                Objects.equals(getUrlNbp(), currency.getUrlNbp()) &&
                Objects.equals(getTablesType(), currency.getTablesType()) &&
                Objects.equals(getRates(), currency.getRates()) &&
                Objects.equals(getLastUpdate(), currency.getLastUpdate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdCode(), getName(), getUrlNbp(), getTablesType(), getRates(), getLastUpdate());
    }

    @Override
    public String toString() {
        return "Currency{" +
                "idCode='" + idCode + '\'' +
                ", name='" + name + '\'' +
                ", urlNbp='" + urlNbp + '\'' +
                ", tablesType='" + tablesType + '\'' +
                ", rates=" + rates +
                '}';
    }
    public boolean isLikeNull() {
        if (getName().equals(null) &&
                getUrlNbp().equals(null) &&
                getRates().equals(null) &&
                getTablesType().equals(null) ) {
            return true;
        }
        else return false;
    }

}
