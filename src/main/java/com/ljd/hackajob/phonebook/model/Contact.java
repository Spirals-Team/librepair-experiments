package com.ljd.hackajob.phonebook.model;

import static com.ljd.hackajob.phonebook.util.Utils.safeEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author leodavison
 *
 */
@Entity(value=Contact.COLLECTION)
public class Contact {
    public static final String COLLECTION = "contacts";

    public static final String FIELD_ID = "id";
    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_LAST_NAME = "lastName";
    public static final String FIELD_PHONENUMBERS = "phoneNumbers";

    public static final String REFERENCE_PHONENUMBERS_TYPE = FIELD_PHONENUMBERS + "." + PhoneNumber.FIELD_TYPE;
    public static final String POSITIONAL_REF_PHONENUMBER = FIELD_PHONENUMBERS + ".$";

    @Id
    private UUID id;

    @NotNull(message="{VER0001}")
    @Pattern(regexp="\\w{1,64}", message="{VER0100}")
    private String firstName;

    @Size(min=1, max=64, message="{VER0101}")
    private String lastName;

    @JsonIgnore
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    private Contact() {
        // required for deserialisation
        firstName = null; //NOSONAR
    }

    private Contact(ContactBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;        
    }

    public UUID getId() {
        return id;
    }

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

    @JsonIgnore
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }
    
    public Map<String, String> getLinks() {
        Map<String, String> links = new HashMap<>();
        if (id != null) {
            links.put(FIELD_PHONENUMBERS, "/api/v1/contacts/" + id.toString() + "/phonenumbers");
        }
        return links;
    }

    public static class ContactBuilder {
        private UUID id;
        private final String firstName;
        private String lastName;

        public ContactBuilder(String firstName) {
            this.firstName = firstName;
        }

        public ContactBuilder(Contact other) {
            this.firstName = other.firstName;
            this.lastName = other.lastName;
        }

        public ContactBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public ContactBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
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

        Contact other = (Contact) obj;

        if (!safeEquals(id, other.id)) {
            return false;
        }

        if (!safeEquals(firstName, other.firstName)) {
            return false;
        }

        return safeEquals(lastName, other.lastName);
    }

    @Override
    public String toString() {
        return "Contact [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }
}
