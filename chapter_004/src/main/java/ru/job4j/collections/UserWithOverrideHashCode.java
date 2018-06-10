package ru.job4j.collections;
/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserWithOverrideHashCode extends User {

    public UserWithOverrideHashCode(String name) {
        super(name);
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getChildren();
        return result;
    }
}
