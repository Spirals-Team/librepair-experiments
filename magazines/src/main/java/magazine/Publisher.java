package magazine;

import com.google.common.base.Preconditions;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

/** @author danis.tazeev@gmail.com */
@Entity
@Table(name = "publisher")
@AttributeOverride(name = "id", column = @Column(name = "publisher_id"))
class Publisher extends IdentifiedEntity {
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = ALL, mappedBy = "publisher")
    private Set<Magazine> magazines = new HashSet<>();

    protected Publisher() {}

    public Publisher(String name) {
        setName(name);
    }

    public String getName() { return name; }
    public Publisher setName(String name) {
        Objects.requireNonNull(name, "name");
        Preconditions.checkArgument(name.trim() == name && !name.isEmpty(), "name='%s'", name);
        this.name = name;
        return this;
    }

    public Set<Magazine> getMagazines() { return magazines; }
    public Publisher addMagazine(Magazine magazine) {
        Objects.requireNonNull(magazine, "magazine");
        magazines.add(magazine);
        return this;
    }

    @Override
    public String toString() {
        return "P#" + getId() + ':' + name;
    }
}
