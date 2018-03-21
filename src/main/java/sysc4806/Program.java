package sysc4806;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Program {

    @Id
    private String name;

    private String shortName;

    public Program(){
        this("", "");
    }

    public Program(String name, String shortName){
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
