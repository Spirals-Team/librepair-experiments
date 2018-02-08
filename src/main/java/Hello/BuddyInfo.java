package Hello;

import javax.persistence.*;

@Entity
public class BuddyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String phoneNumber;
    public final static String DEFAULT_NAME = "default";
    public final static String DEFAULT_PHONENUMBER = "";

    //private AddressBook book;

    public BuddyInfo()
    {
        this(DEFAULT_NAME,DEFAULT_PHONENUMBER);
    }

    public BuddyInfo(String name, String phoneNumber)
    {
        this.name = name;
        try {
            Double.parseDouble(phoneNumber);
        }
        catch(NumberFormatException e)
        {
            return;
        }
        this.phoneNumber = phoneNumber;
    }

    public String getName()
    {
        return name;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
