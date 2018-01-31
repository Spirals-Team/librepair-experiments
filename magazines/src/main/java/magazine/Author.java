package magazine;

import com.google.common.base.Preconditions;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/** @author danis.tazeev@gmail.com */
@Entity
@Table(name = "author")
@AttributeOverride(name = "id", column = @Column(name = "author_id"))
class Author extends IdentifiedEntity {
    @OneToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article;
    @Column(name = "name")
    private String name;

    protected Author() {}

    public Author(String name) {
        setName(name);
    }

    public String getName() { return name; }
    public Author setName(String name) {
        Objects.requireNonNull(name, "name");
        Preconditions.checkArgument(name.trim() == name && !name.isEmpty(), "name='%s'", name);
        this.name = name;
        return this;
    }

    public Article getArticle() { return article; }
    public Author setArticle(Article article) {
        Objects.requireNonNull(article, "article");
        this.article = article;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '#' + getId() + ':' + name;
    }
}
