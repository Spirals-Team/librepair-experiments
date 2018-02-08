package Hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddBuddyController {
    private long id;
    @Autowired
    private AddressBookRepository aRepo;

    @Autowired
    private BuddyInfoRepository bRepo;

    @RequestMapping("/CreateAddressBook")
    public long CreateAddressBook() {
        AddressBook book = new AddressBook();
        id=book.id;
        aRepo.save(book);
        return book.id;
    }

    @RequestMapping("/AddBuddy")
    public void AddBuddy(@RequestParam(value = "name",required = true) String name, @RequestParam(value = "phoneNumber",required = true) String phoneNumber) {
        AddressBook book = aRepo.findOne((long)1);
        BuddyInfo b = new BuddyInfo(name,phoneNumber);
        book.addBuddy(b);
        bRepo.save(b);
        aRepo.save(book);
    }
}
