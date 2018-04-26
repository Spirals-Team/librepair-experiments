package ru.neustupov.votingforrestaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "meals")
public class Meal extends AbstractNamedEntity {

    @Column(name = "price",  nullable = false)
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_menu", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnoreProperties("meals")
    private Menu menu;

    public Meal() {
    }

    public Meal(Meal m) {
        this(m.getId(), m.getName(), m.getPrice());
    }

    public Meal(Integer price) {
        this.price = price;
    }

    public Meal(int id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    public Meal(String name, Integer price) {
        super(null, name);
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
