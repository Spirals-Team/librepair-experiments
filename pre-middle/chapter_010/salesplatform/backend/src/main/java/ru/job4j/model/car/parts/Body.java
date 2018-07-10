package ru.job4j.model.car.parts;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class represent Body.
 */
public class Body {

    private Long id;
    private String type;
    private String description;

    public Body() {
    }

    public Body(final String type, final String description) {
        this.type = type;
        this.description = description;
    }

    public Body(final Long id, final String type,
                        final String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Body setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public Body setType(final String type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Body setDescription(final String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body that = (Body) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.type, that.type)
            && Objects.equals(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("type = " + type)
            .add("description = " + description)
            .toString();
    }

//    enum Type {
//        HATCHBACK,
//        SALOON,
//        ESTATE,
//        MPV,
//        COUPE,
//        CONVERTIBLE,
//        OTHER;
//
//        public Type typeFromTitle(final String title) {
//            for (Type type : Type.values()) {
//                if (type.name().equalsIgnoreCase(title)) {
//                    return type;
//                }
//            }
//            throw new IllegalArgumentException(
//                String.format("Unknown Body.Type with Title: %s", title));
//        }
//    }
}
