package ru.job4j.userconvert;
/**
 * User.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class User {
    /*** User id.*/
    private Integer id;
    /*** User name.*/
    private String name;
    /*** User city.*/
    private String city;
    /**
     * Get id.
     * @return id
     */
    public Integer getId() {
        return id;
    }
    /**
     * Constructor.
     * @param id - id
     * @param name - name
     * @param city - city
     */
    public User(Integer id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) {
            return false;
        }
        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        return city != null ? city.equals(user.city) : user.city == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
