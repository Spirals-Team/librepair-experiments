package ru.job4j.collections.generic;

/**
 * Класс создания Role.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 05.10.17;
 * @version $Id$
 * @since 0.1
 */
public class Role extends Base {
    /**
     * id Элемента.
     */
    private String id;
    /**
     * Имя.
     */
    private String name;

    /**
     * Getter.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter.
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Конструктор.
     * @param id id.
     * @param name name.
     */
    public Role(String id, String name) {

        this.id = id;
        this.name = name;
    }

    /**
     * Конструктор без name.
     * @param id id.
     */
    public Role(String id) {
        this.id = id;
    }

    /**
     * Getter.
     * @return id.
     */
    @Override
    String getId() {
        return id;
    }

    /**
     *Setter.
     * @param id установка id.
     */
    @Override
    void setId(String id) {
        this.id = id;
    }

    /**
     * Equals по id.
     * @param o o.
     * @return true or false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Role role = (Role) o;

        return id != null ? id.equals(role.id) : role.id == null;
    }

    /**
     * HashCode.
     * @return code.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
