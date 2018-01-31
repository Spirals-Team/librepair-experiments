package magazine;

import com.google.common.base.Preconditions;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/** @author danis.tazeev@gmail.com */
@MappedSuperclass
abstract class IdentifiedEntity {
    /** The value of {@code 0} (zero) means no ID assigned yet. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public boolean isNew() { return id == 0; }
    public int getId() { return id; }
    public IdentifiedEntity setId(int id) {
        Preconditions.checkArgument(id != 0, "id=0");
        Preconditions.checkState(this.id == 0, "not new");
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (id == 0) {
            throw new IllegalStateException("id=0");
        }
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IdentifiedEntity that = (IdentifiedEntity)obj;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        if (id == 0) {
            throw new IllegalStateException("id=0");
        }
        return id;
    }
}
