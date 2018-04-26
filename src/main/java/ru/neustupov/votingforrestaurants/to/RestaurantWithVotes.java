package ru.neustupov.votingforrestaurants.to;

import ru.neustupov.votingforrestaurants.model.AbstractNamedEntity;
import ru.neustupov.votingforrestaurants.model.Menu;
import ru.neustupov.votingforrestaurants.model.Vote;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class RestaurantWithVotes extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Menu> menus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Vote> votes;

    private Integer numberOfVotes;

    public RestaurantWithVotes(RestaurantWithVotes r) {
        this(r.getId(), r.getName());
    }

    public RestaurantWithVotes() {
        super(null, null);
    }

    public RestaurantWithVotes(@NotNull int id, @NotNull String name, int numberOfVotes) {
        super(id, name);
        this.numberOfVotes = numberOfVotes;
    }

    public RestaurantWithVotes(@NotNull Integer id, @NotNull String name) {
        super(id, name);
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Integer getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(Integer numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}
