package lelental.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Paweł Lelental
 **/
public class Author {

    private long id;
    private String name;
    private Date dateOfCreation;


    public Author(long id, String name, Date dateOfCreation) {
        this.id = id;
        this.name = name;
        this.dateOfCreation = dateOfCreation;
    }

    public Author() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
