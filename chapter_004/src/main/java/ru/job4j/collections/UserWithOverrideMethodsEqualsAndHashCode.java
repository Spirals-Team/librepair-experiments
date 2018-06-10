package ru.job4j.collections;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserWithOverrideMethodsEqualsAndHashCode extends User {

    public UserWithOverrideMethodsEqualsAndHashCode(String name) {
        super(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getChildren();
        return result;
    }
}
