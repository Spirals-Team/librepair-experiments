package Hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<BuddyInfo> buddys;


    public AddressBook()
    {
        buddys = new ArrayList<BuddyInfo>();
    }
    public void addBuddy(BuddyInfo b)
    {
        buddys.add(b);
    }
    public void removeBudddy(BuddyInfo b)
    {
        buddys.remove(b);
    }
    public int countBuddy()
    {
        return buddys.size();
    }

    private static final Logger log = LoggerFactory.getLogger(AddressBook.class);

    public static void main(String args[])
    {
        /*
        AddressBook a = new AddressBook();
        BuddyInfo b1 = new BuddyInfo("Dan1","61388888888");
        BuddyInfo b2 = new BuddyInfo("Dan2","61399999999");
        a.AddBuddy(b1);
        a.AddBuddy(b2);
        System.out.print("AddressBook Main class Ran");
        */
    }

    public List<BuddyInfo> getBuddys() {
        return buddys;
    }

    public void setBuddys(List<BuddyInfo> addressbook) {
        this.buddys = addressbook;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
