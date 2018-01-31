package magazine;

import com.google.common.base.Preconditions;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** @author danis.tazeev@gmail.com */
@Entity
@Table(name = "magazine")
@AttributeOverride(name = "id", column = @Column(name = "magazine_id"))
class Magazine extends IdentifiedEntity {
    @Column(name = "name")
    private String name;
    @ManyToOne(optional = false)
    private Publisher publisher;
    @OneToMany(mappedBy = "magazine")
    private Set<Article> articles = new HashSet<>();

    protected Magazine() {}

    public String getName() { return name; }
    public Magazine setName(String name) {
        Objects.requireNonNull(name, "name");
        Preconditions.checkArgument(name.trim() == name && !name.isEmpty(), "name='%s'", name);
        this.name = name;
        return this;
    }

    public Publisher getPublisher() { return publisher; }
    public Magazine setPublisher(Publisher publisher) {
        Objects.requireNonNull(publisher, "publisher");
        this.publisher = publisher;
        return this;
    }

    public Set<Article> getArticles() { return articles; }
    public Magazine addArticle(Article article) {
        Objects.requireNonNull(article, "article");
        articles.add(article);
        return this;
    }

    @Override
    public String toString() {
        return "M#" + getId() + ':' + name + '@' + publisher;
    }
}
