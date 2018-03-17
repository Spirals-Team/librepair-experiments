package  ru.job4j.multithreading.chash;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 15.11.17;
 * @version $Id$
 * @since 0.1
 */
public class User {
    private String name;
    private int version = 0;
    private int id;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "User{"
                + "name='"
                + name
                + '\''
                + ", version="
                + version
                + ", id="
                + id
                + '}';
    }
}
