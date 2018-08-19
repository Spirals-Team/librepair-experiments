package ua.epam.spring.hometask.domain;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Yuriy_Tkach
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends DomainObject {

    @XmlElement
    private String firstName;

    @XmlElement
    private String lastName;

    @XmlElement
    private String email;

    //1st approach
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @XmlElement
    private LocalDate birthday;

    @XmlTransient
    private NavigableSet<Ticket> tickets = new TreeSet<>();

    @XmlElement
    private Set<LocalDateTime> luckyEvents = new HashSet<>();

    @XmlElement
    private String password;

    @XmlElement
    private Set<Role> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NavigableSet<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(NavigableSet<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<LocalDateTime> getLuckyEvents() {
        return luckyEvents;
    }

    public void setLuckyEvents(Set<LocalDateTime> luckyEvents) {
        this.luckyEvents = luckyEvents;
    }

    //    @XmlElement
    //2nd approach to turn on java.time support for proper converting to/from XML
//    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getRolesToString() {
        if (roles == null) {
            return "";
        } else {
            return roles
                    .stream()
                    .map(Role::name)
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.joining(","));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
