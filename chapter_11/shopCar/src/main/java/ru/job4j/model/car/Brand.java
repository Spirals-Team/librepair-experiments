package ru.job4j.model.car;

import ru.job4j.model.Persistent;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 19.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
@Entity
@Table(name = "brand")
public class Brand extends Persistent {

    private static final long serialVersionUID = 7814551052352183461L;

    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Model> models = new ArrayList<>();

    public Brand() {
        super();
    }

    public Brand(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}
