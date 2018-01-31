package magazine;

import com.google.common.base.Preconditions;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/** @author danis.tazeev@gmail.com */
@Entity
@Table(name = "artice")
@AttributeOverride(name = "id", column = @Column(name = "article_id"))
class Article extends IdentifiedEntity {
    @Column(name = "name")
    private String name;
    @OneToOne(optional = false, mappedBy = "article")
    private Author author;
    @ManyToOne(optional = false)
    @JoinColumn(name = "magazine_id")
    private Magazine magazine;

    protected Article() {}

    public Article(String name) {
        setName(name);
    }

    public String getName() { return name; }
    public Article setName(String name) {
        Objects.requireNonNull(name, "name");
        Preconditions.checkArgument(name.trim() == name && !name.isEmpty(), "name='%s'", name);
        this.name = name;
        return this;
    }

    public Author getAuthor() { return author; }
    public Article setAuthor(Author author) {
        Objects.requireNonNull(author, "author");
        this.author = author;
        return this;
    }

    public Magazine getMagazine() { return magazine; }
    public Article setMagazine(Magazine magazine) {
        Objects.requireNonNull(magazine, "magazine");
        this.magazine = magazine;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '#' + getId() + ':' + name + " by " + author;
    }
}
