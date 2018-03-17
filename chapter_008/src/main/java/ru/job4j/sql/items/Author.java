package ru.job4j.sql.items;

/**
 * Автор объявления.
 * @author Hincu Andrei (andreih1981@gmail.com)on 25.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Author {
    public Author() {
    }

    public Author(String name, String url) {

        this.name = name;
        this.url = url;
    }

    private String name;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Author author = (Author) o;

        if (name != null ? !name.equals(author.name) : author.name != null) {
            return false;
        }
        return url != null ? url.equals(author.url) : author.url == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Name = "
                + name
                + ". Url: "
                + url
                + '.';
    }
}
